/*
 * Copyright 2005-2013 the original author or authors.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package org.javelin.sws.ext.bind.internal.metadata;

import java.lang.annotation.Annotation;
import java.lang.reflect.AnnotatedElement;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.lang.reflect.Modifier;
import java.util.LinkedList;
import java.util.List;

import javax.xml.XMLConstants;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlElementWrapper;
import javax.xml.bind.annotation.XmlNsForm;
import javax.xml.bind.annotation.XmlSchemaType;
import javax.xml.bind.annotation.XmlTransient;
import javax.xml.bind.annotation.XmlValue;
import javax.xml.namespace.QName;

import org.javelin.sws.ext.bind.TypedPatternRegistry;
import org.javelin.sws.ext.bind.internal.model.AttributePattern;
import org.javelin.sws.ext.bind.internal.model.ComplexTypePattern;
import org.javelin.sws.ext.bind.internal.model.ElementPattern;
import org.javelin.sws.ext.bind.internal.model.SimpleContentPattern;
import org.javelin.sws.ext.bind.internal.model.TypedPattern;
import org.javelin.sws.ext.bind.internal.model.XmlEventsPattern;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.core.annotation.AnnotationUtils;
import org.springframework.util.ReflectionUtils;
import org.springframework.util.ReflectionUtils.FieldCallback;
import org.springframework.util.ReflectionUtils.FieldFilter;
import org.springframework.util.ReflectionUtils.MethodCallback;
import org.springframework.util.ReflectionUtils.MethodFilter;
import org.springframework.util.StringUtils;

/**
 * <p>This class analyzes bean and field properties of JAXB-bound class and produces a {@link TypedPattern}
 * representing this class in terms of OXM.</p>
 * 
 * <p>Every class instance maps to a sequence of patterns for each class' property. The nested patterns
 * are of three categories:<ul>
 * <li>XML element</li>
 * <li>XML attribute</li>
 * <li>XML value</li>
 * </ul>The pattern for analyzed class is <b>always</b> a {@link ComplexTypePattern} but in one special case (only singe {@link XmlValue}
 * annotated property), the {@link #analyze(Class, QName)} method may return {@link SimpleContentPattern}.</p>
 * 
 * <p>TODO: ensure that built-in types, primitives, exceptions, etc., are <b>not</b> analyzed</p>
 *
 * @author Grzegorz Grzybek
 * @param <T> a type of bean containing analyzed properties
 */
public class PropertyCallback<T> implements FieldCallback, MethodCallback {

	private static Logger log = LoggerFactory.getLogger(PropertyCallback.class.getName());

	/* Initialization data */

	private Class<T> clazz;

	private QName typeName;

	private XmlAccessType accessType;

	private XmlNsForm elementFormDefault;

	private XmlNsForm attributeFormDefault;

	private TypedPatternRegistry patternRegistry;

	/* Post-analysis data */

	// list of properties mapped to XML Attributes
	private final List<PropertyMetadata<T, ?>> childAttributeMetadata = new LinkedList<PropertyMetadata<T, ?>>();

	// list of properties mapped to XML Elements (or text of mixed content). non-empty = complex content
	private final List<PropertyMetadata<T, ?>> childElementMetadata = new LinkedList<PropertyMetadata<T, ?>>();

	// at most one property mapped to simple characters content. non-null means there must be no childElementMetadata
	private PropertyMetadata<T, ?> valueMetadata = null;

	/**
	 * @param patternRegistry
	 * @param clazz
	 * @param typeName
	 * @param accessType
	 * @param elementFormDefault
	 * @param attributeFormDefault
	 */
	public PropertyCallback(TypedPatternRegistry patternRegistry, Class<T> clazz, QName typeName, XmlAccessType accessType, XmlNsForm elementFormDefault,
			XmlNsForm attributeFormDefault) {
		this.patternRegistry = patternRegistry;
		this.clazz = clazz;
		this.typeName = typeName;
		this.accessType = accessType;
		this.elementFormDefault = elementFormDefault;
		this.attributeFormDefault = attributeFormDefault;
	}

	/**
	 * <p>Reads class' metadata and returns a {@link XmlEventsPattern pattern of XML events} which may be used to marshal
	 * an object of the analyzed class.<?p>
	 * 
	 * @return
	 */
	public TypedPattern<T> analyze() {
		TypedPattern<T> result = this.patternRegistry.findPatternByClass(this.clazz);

		if (result != null)
			return result;

		log.trace("Analyzing {} class with {} type name", this.clazz.getName(), this.typeName);

		// analyze fields
		ReflectionUtils.doWithFields(this.clazz, this, new FieldFilter() {
			@Override
			public boolean matches(Field field) {
				return !Modifier.isStatic(field.getModifiers());
			}
		});

		// analyze get/set methods - even private ones!
		ReflectionUtils.doWithMethods(this.clazz, this, new MethodFilter() {
			@Override
			public boolean matches(Method method) {
				boolean match = true;
				// is it getter?
				match &= method.getName().startsWith("get");
				match &= method.getParameterTypes().length == 0;
				match &= method.getReturnType() != Void.class;
				// is there a setter?
				if (match) {
					Method setter = ReflectionUtils.findMethod(clazz, method.getName().replaceFirst("^get", "set"), method.getReturnType());
					// TODO: maybe allow non-void returning setters as Spring-Framework already does? Now: yes
					match = setter != null;
				}
				
				return match;
			}
		});

		if (this.valueMetadata != null && this.childElementMetadata.size() == 0 && this.childAttributeMetadata.size() == 0) {
			// we have a special case, where Java class becomes simpleType:
			//  - formatting the analyzed class is really formatting the value
			//  - the type information of the analyzed class is not changed!
			log.trace("Changing {} class' pattern to SimpleContentPattern", this.clazz.getName());

			SimpleContentPattern<T> valuePattern = SimpleContentPattern.newValuePattern(this.typeName, this.clazz);
			SimpleContentPattern<?> simpleTypePattern = (SimpleContentPattern<?>) this.valueMetadata.getPattern();
			valuePattern.setFormatter(PeelingFormatter.newPeelingFormatter(this.valueMetadata, simpleTypePattern.getFormatter()));
			result = valuePattern;
		}
		else {
			if (this.valueMetadata != null && this.childElementMetadata.size() > 0) {
				throw new RuntimeException("TODO: can't mix @XmlValue and @XmlElements");
			}

			// we have complex type (possibly with simpleContent, when there's @XmlValue + one or more @XmlAttributes)
			// @XmlAttributes first. then @XmlElements
			this.childAttributeMetadata.addAll(this.childElementMetadata);
			if (this.valueMetadata != null)
				this.childAttributeMetadata.add(this.valueMetadata);

			result = ComplexTypePattern.newContentModelPattern(this.typeName, this.clazz, this.childAttributeMetadata);
		}

		if (log.isTraceEnabled())
			log.trace("-> Class {} was mapped to {} with {} XSD type", this.clazz.getName(), result, this.typeName);

		return result;
	}

	/**
	 * Like {@link MethodCallback#doWith(Method)}, but for two methods.
	 * 
	 * @see org.springframework.util.ReflectionUtils.MethodCallback#doWith(java.lang.reflect.Method)
	 */
	@Override
	public void doWith(Method getter) throws IllegalArgumentException, IllegalAccessException {
		Method setter = ReflectionUtils.findMethod(clazz, getter.getName().replaceFirst("^get", "set"), getter.getReturnType());

		if (log.isTraceEnabled())
			log.trace(" - Analyzing pair of methods: {}.{}/{}.{}", getter.getDeclaringClass().getSimpleName(), getter.getName(), setter.getDeclaringClass()
					.getSimpleName(), setter.getName());

		// TODO: properly handle class hierarchies
		String propertyName = StringUtils.uncapitalize(getter.getName().substring(3));
		// metadata for getter/setter
		PropertyMetadata<T, ?> metadata = PropertyMetadata.newPropertyMetadata(this.clazz, getter.getReturnType(), propertyName, PropertyKind.BEAN);
		metadata.setAccessorMethods(getter, setter);
		this.doWithPropertySafe(metadata);
	}

	/* (non-Javadoc)
	 * @see org.springframework.util.ReflectionUtils.FieldCallback#doWith(java.lang.reflect.Field)
	 */
	@Override
	public void doWith(Field field) throws IllegalArgumentException, IllegalAccessException {
		log.trace("- Analyzing field: {}.{}", field.getDeclaringClass().getSimpleName(), field.getName());
		String fieldName = field.getName();
		// metadata for a field
		PropertyMetadata<T, ?> metadata = PropertyMetadata.newPropertyMetadata(this.clazz, field.getType(), fieldName, PropertyKind.FIELD);
		ReflectionUtils.makeAccessible(field);
		metadata.setField(field);
		this.doWithPropertySafe(metadata);
	}

	/**
	 * @param field
	 * @param class1
	 */
	private <P> void doWithPropertySafe(PropertyMetadata<T, P> metadata) throws IllegalArgumentException, IllegalAccessException {

		AnnotatedElement[] accessors = metadata.getAccessors();

		if (this.findJaxbAnnotation(accessors, XmlTransient.class) != null)
			return;

		XmlSchemaType xmlSchemaType = this.findJaxbAnnotation(accessors, XmlSchemaType.class);
		// a pattern for property's class - force creating
		TypedPattern<P> pattern = null;
		if (xmlSchemaType != null) {
			// the schema type determines the pattern - if it is not present in the registry, it will be after determining it on the basis of Java class
			// of the property
			QName typeName = new QName(xmlSchemaType.namespace(), xmlSchemaType.name());
			pattern = this.patternRegistry.findPatternByType(typeName, metadata.getPropertyClass());
			if (log.isTraceEnabled() && pattern != null)
				log.trace("-- @XmlSchemaType points to {}", pattern.toString());
		}

		if (pattern == null)
			pattern = this.patternRegistry.determineAndCacheXmlPattern(metadata.getPropertyClass());

		// is it value?
		XmlValue xmlValue = this.findJaxbAnnotation(accessors, XmlValue.class);
		if (xmlValue != null) {
			// the field's class must be a simpleType, i.e., a type convertible to String, which is either:
			//  - a type registered in org.javelin.sws.ext.bind.internal.BuiltInMappings.initialize()
			//  - a type which has only one property annotated with @XmlValue
			// a type with one @XmlValue property + non-zero @XmlAttribute properties is complex type with simple content, not a simple type
			if (!(pattern.isSimpleType() && pattern instanceof SimpleContentPattern))
				throw new RuntimeException("TODO: should be simpleType");

			if (log.isTraceEnabled())
				log.trace("-- @XmlValue property \"{}\" of type {} mapped to {}", metadata.getPropertyName(), pattern.getJavaType().getName(), pattern.toString());

			metadata.setPattern(pattern);
			if (this.valueMetadata != null)
				throw new RuntimeException("TODO: Only one @XmlValue allowed!");

			this.valueMetadata = metadata;
			return;
		}

		// is it an attribute?
		XmlAttribute xmlAttribute = this.findJaxbAnnotation(accessors, XmlAttribute.class);
		if (xmlAttribute != null) {
			String namespace = XMLConstants.NULL_NS_URI;
			if (this.attributeFormDefault == XmlNsForm.QUALIFIED) {
				// the attribute MUST have namespace
				namespace = "##default".equals(xmlAttribute.namespace()) ? this.typeName.getNamespaceURI() : xmlAttribute.namespace();
			}
			else {
				// the attribute MAY have namespace
				// TODO: handle org.javelin.sws.ext.bind.annotations.XmlAttribute
				if (!"##default".equals(xmlAttribute.namespace()))
					namespace = xmlAttribute.namespace();
			}
			String name = "##default".equals(xmlAttribute.name()) ? metadata.getPropertyName() : xmlAttribute.name();

			if (!(pattern.isSimpleType() && pattern instanceof SimpleContentPattern))
				throw new RuntimeException("TODO: should be simpleType");

			QName attributeQName = new QName(namespace, name);

			if (log.isTraceEnabled())
				log.trace("-- @XmlAttribute property \"{}\" of type {} mapped to {} attribute {}", metadata.getPropertyName(), pattern.getJavaType().getName(),
						attributeQName, pattern.toString());

			metadata.setPattern(AttributePattern.newAttributePattern(attributeQName, (SimpleContentPattern<P>) pattern));
			this.childAttributeMetadata.add(metadata);
			return;
		}

		// is it an element?
		XmlElement xmlElement = this.findJaxbAnnotation(accessors, XmlElement.class);

		// field is also an element when told so using XmlAccessorType
		boolean isElement = false;

		if (accessors[0] instanceof Field) {
			if (this.accessType == XmlAccessType.FIELD)
				isElement = true;
			else if (this.accessType == XmlAccessType.PUBLIC_MEMBER && Modifier.isPublic(((Field) accessors[0]).getModifiers()))
				isElement = true;
		} else if (accessors[0] instanceof Method) {
			if (this.accessType == XmlAccessType.PROPERTY)
				isElement = true;
			else if (this.accessType == XmlAccessType.PUBLIC_MEMBER && Modifier.isPublic(((Method) accessors[0]).getModifiers())) {
				// TODO: what if getter is private and setter is public?
				isElement = true;
			}
		}

		if (xmlElement != null || isElement) {
			String namespace = XMLConstants.NULL_NS_URI;
			if (this.elementFormDefault == XmlNsForm.QUALIFIED) {
				// the element MUST have namespace
				namespace = xmlElement == null || "##default".equals(xmlElement.namespace()) ? this.typeName.getNamespaceURI() : xmlElement.namespace();
			}
			else {
				// the element MAY have namespace
				if (xmlElement != null && !"##default".equals(xmlElement.namespace()))
					namespace = xmlElement.namespace();
			}
			String name = xmlElement == null || "##default".equals(xmlElement.name()) ? metadata.getPropertyName() : xmlElement.name();
			QName elementQName = new QName(namespace, name);

			if (log.isTraceEnabled())
				log.trace("-- @XmlElement property \"{}\" of type {} mapped to {} element with {}", metadata.getPropertyName(), pattern.getJavaType().getName(),
						elementQName, pattern.toString());

			ElementPattern<P> elementPattern = ElementPattern.newElementPattern(elementQName, pattern);
			XmlElementWrapper xmlElementWrapper = this.findJaxbAnnotation(accessors, XmlElementWrapper.class);
			if (xmlElementWrapper != null) {
				if (!"##default".equals(xmlElementWrapper.namespace()))
					namespace = xmlElementWrapper.namespace();
				name = !"##default".equals(xmlElementWrapper.name()) ? xmlElementWrapper.name() : metadata.getPropertyName();

				// XmlElementWrapper creates (in XSD Category) a new complex, anonymous, nested (inside element declaration) type
				// DESIGNFLAW: XmlElementWrapper works, but not as clean as it should
				PropertyMetadata<P, ?> md = PropertyMetadata.newPropertyMetadata(pattern.getJavaType(), Object.class, "", PropertyKind.PASSTHROUGH);
				md.setPattern(elementPattern);
				ComplexTypePattern<P> newAnonymousType = ComplexTypePattern.newContentModelPattern(null, pattern.getJavaType(), md);
				// TODO: Handle @XmlElementWrapper for collection properties
				// TODO: JAXB2 doesn't allow this, but maybe it's a good idea to be able to wrap non-collection properties also?
				// TODO: maybe it's a good idea to create @XmlElementWrappers class (aggregating multime @XmlElementWrapper annotations?)
				elementPattern = ElementPattern.newElementPattern(new QName(namespace, name), newAnonymousType);
			}

			metadata.setPattern(elementPattern);
			this.childElementMetadata.add(metadata);
			return;
		}
	}

	/**
	 * @param accessors
	 * @return
	 */
	private <A extends Annotation> A findJaxbAnnotation(AnnotatedElement[] accessors, Class<A> ann) {
		A annotation = null;
		for (AnnotatedElement ae: accessors) {
			// TODO: throw an exception when JAXB annotation is on both getter and setter
			if ((annotation = AnnotationUtils.getAnnotation(ae, ann)) != null)
				return annotation;
		}
		return null;
	}

}
