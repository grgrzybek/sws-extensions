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

import java.beans.BeanInfo;
import java.beans.Introspector;
import java.beans.PropertyDescriptor;
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
import javax.xml.bind.annotation.XmlNsForm;
import javax.xml.bind.annotation.XmlTransient;
import javax.xml.bind.annotation.XmlValue;
import javax.xml.namespace.QName;

import org.javelin.sws.ext.bind.SweJaxbContext;
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
public class PropertyCallback<T> implements FieldCallback {

	private static Logger log = LoggerFactory.getLogger(PropertyCallback.class.getName());

	/* Initialization data */
	private String namespace;

	private Class<T> declaringClass;

	private XmlAccessType accessType;

	@SuppressWarnings("unused")
	private XmlNsForm elementFormDefault;

	private XmlNsForm attributeFormDefault;

	private SweJaxbContext context;

	/* Post-analysis data */

	// list of properties mapped to XML Attributes
	private final List<PropertyMetadata<T, ?>> childAttributeMetadata = new LinkedList<PropertyMetadata<T, ?>>();

	// list of properties mapped to XML Elements (or text of mixed content). non-empty = complex content
	private final List<PropertyMetadata<T, ?>> childElementMetadata = new LinkedList<PropertyMetadata<T, ?>>();

	// at most one property mapped to simple characters content
	private PropertyMetadata<T, ?> valueMetadata = null;

	/**
	 * @param namespace
	 * @param attributeFormDefault 
	 * @param elementFormDefault 
	 */
	public PropertyCallback(SweJaxbContext context, Class<T> declaringClass, String namespace, XmlAccessType accessType, XmlNsForm elementFormDefault, XmlNsForm attributeFormDefault) {
		this.context = context;
		this.declaringClass = declaringClass;
		this.namespace = namespace;
		this.accessType = accessType;
		this.elementFormDefault = elementFormDefault;
		this.attributeFormDefault = attributeFormDefault;
	}

	/**
	 * <p>Reads class' metadata and returns a {@link XmlEventsPattern pattern of XML events} which may be used to marshal
	 * an object of the analyzed class.<?p>
	 * 
	 * @param cl
	 * @param typeName
	 * @return
	 */
	public TypedPattern<T> analyze(Class<T> cl, QName typeName) {
		log.trace("Analyzing {} class with {} type name", cl.getName(), typeName);

		// analyze fields
		ReflectionUtils.doWithFields(cl, this, new FieldFilter() {
			@Override
			public boolean matches(Field field) {
				return !Modifier.isStatic(field.getModifiers());
			}
		});
		// analyze get/set methods
		try {
			BeanInfo beanInfo = Introspector.getBeanInfo(cl);
			PropertyDescriptor[] propertyDescriptors = beanInfo.getPropertyDescriptors();
			for (PropertyDescriptor pd : propertyDescriptors) {
				if (pd.getReadMethod() != null && pd.getWriteMethod() != null) {
					// like MethodCallback, but for a pair of methods
					this.doWith(pd.getReadMethod(), pd.getWriteMethod());
				}
			}
		}
		catch (Exception e) {
			throw new RuntimeException("TODO: " + e.getMessage(), e);
		}

		TypedPattern<T> result = null;

		if (this.valueMetadata != null && this.childElementMetadata.size() == 0 && this.childAttributeMetadata.size() == 0) {
			// we have a special case, where Java class becomes simpleType:
			//  - formatting the analyzed class is really formatting the value
			//  - the type information of the analyzed class is not changed!
			log.trace("Changing {} class' pattern to SimpleContentPattern", cl.getName());

			SimpleContentPattern<T> valuePattern = SimpleContentPattern.newValuePattern(typeName, cl);
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

			result = ComplexTypePattern.newContentModelPattern(typeName, cl, this.childAttributeMetadata);
		}

		log.trace("-> Class {} was mapped to {} with {} XSD type", cl.getName(), result, typeName);
		return result;
	}

	/* (non-Javadoc)
	 * @see org.springframework.util.ReflectionUtils.MethodCallback#doWith(java.lang.reflect.Method)
	 */
	public void doWith(Method getter, Method setter) throws IllegalArgumentException, IllegalAccessException {
		if (log.isTraceEnabled())
			log.trace(" - Analyzing pair of methods: {}.{}/{}.{}", getter.getDeclaringClass().getSimpleName(), getter.getName(), setter.getDeclaringClass().getSimpleName(), setter.getName());

		// TODO: properly handle class hierarchies
		String propertyName = StringUtils.uncapitalize(getter.getName().substring(3));
		// metadata for getter/setter
		PropertyMetadata<T, ?> metadata = PropertyMetadata.newPropertyMetadata(this.declaringClass, getter.getReturnType(), propertyName, false);
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
		PropertyMetadata<T, ?> metadata = PropertyMetadata.newPropertyMetadata(this.declaringClass, field.getType(), fieldName, true);
		ReflectionUtils.makeAccessible(field);
		metadata.setField(field);
		this.doWithPropertySafe(metadata);
	}

	/**
	 * @param field
	 * @param class1
	 */
	private <P> void doWithPropertySafe(PropertyMetadata<T, P> metadata) throws IllegalArgumentException, IllegalAccessException {

		AnnotatedElement property = metadata.getProperty();

		if (AnnotationUtils.getAnnotation(property, XmlTransient.class) != null)
			return;

		// a pattern for property's class
		TypedPattern<P> pattern = this.context.determineXmlPattern(metadata.getPropertyClass());

		// is it value?
		XmlValue xmlValue = AnnotationUtils.getAnnotation(property, XmlValue.class);
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
		XmlAttribute xmlAttribute = AnnotationUtils.getAnnotation(property, XmlAttribute.class);
		if (xmlAttribute != null) {
			String namespace = XMLConstants.NULL_NS_URI;
			if (this.attributeFormDefault == XmlNsForm.QUALIFIED) {
				// the attribute MUST have namespace
				namespace = "##default".equals(xmlAttribute.namespace()) ? this.namespace : xmlAttribute.namespace();
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
				log.trace("-- @XmlAttribute property \"{}\" of type {} mapped to {} attribute {}", metadata.getPropertyName(), pattern.getJavaType().getName(), attributeQName, pattern.toString());

			metadata.setPattern(AttributePattern.newAttributePattern(attributeQName, (SimpleContentPattern<P>) pattern));
			this.childAttributeMetadata.add(metadata);
			return;
		}

		// is it an element?
		XmlElement xmlElement = AnnotationUtils.getAnnotation(property, XmlElement.class);

		// field is also an element when told so using XmlAccessorType
		boolean isElement = false;

		if (property instanceof Field) {
			if (this.accessType == XmlAccessType.FIELD)
				isElement = true;
			else if (this.accessType == XmlAccessType.PUBLIC_MEMBER && Modifier.isPublic(((Field) property).getModifiers()))
				isElement = true;
		}

		if (xmlElement != null || isElement) {
			String namespace = xmlElement == null || "##default".equals(xmlElement.namespace()) ? this.namespace : xmlElement.namespace();
			String name = xmlElement == null || "##default".equals(xmlElement.name()) ? metadata.getPropertyName() : xmlElement.name();
			QName elementQName = new QName(namespace, name);

			if (log.isTraceEnabled())
				log.trace("-- @XmlElement property \"{}\" of type {} mapped to {} element with {}", metadata.getPropertyName(), pattern.getJavaType().getName(), elementQName, pattern.toString());

			metadata.setPattern(ElementPattern.newElementPattern(elementQName, pattern));
			this.childElementMetadata.add(metadata);
			return;
		}
	}
}
