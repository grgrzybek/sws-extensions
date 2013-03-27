/*
 * Copyright 2005-2012 the original author or authors.
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

package org.springframework.ws.ext.bind;

import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.lang.reflect.Modifier;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import javax.xml.XMLConstants;
import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Marshaller;
import javax.xml.bind.Unmarshaller;
import javax.xml.bind.Validator;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlNsForm;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlSchema;
import javax.xml.bind.annotation.XmlTransient;
import javax.xml.bind.annotation.XmlType;
import javax.xml.bind.annotation.XmlValue;
import javax.xml.namespace.QName;
import javax.xml.stream.events.XMLEvent;

import org.springframework.core.annotation.AnnotationUtils;
import org.springframework.util.ReflectionUtils;
import org.springframework.util.ReflectionUtils.FieldCallback;
import org.springframework.util.ReflectionUtils.FieldFilter;
import org.springframework.util.ReflectionUtils.MethodCallback;
import org.springframework.ws.ext.bind.internal.BuiltInMappings;
import org.springframework.ws.ext.bind.internal.metadata.PackageMetadata;
import org.springframework.ws.ext.bind.internal.metadata.PropertyMetadata;
import org.springframework.ws.ext.bind.internal.model.AttributePattern;
import org.springframework.ws.ext.bind.internal.model.ContentModelPattern;
import org.springframework.ws.ext.bind.internal.model.ElementPattern;
import org.springframework.ws.ext.bind.internal.model.TemporaryXmlEventsPattern;
import org.springframework.ws.ext.bind.internal.model.ValuePattern;
import org.springframework.ws.ext.bind.internal.model.XmlEventsPattern;
import org.springframework.ws.ext.utils.NamespaceUtils;

/**
 * <p>Lightweight implementation of {@link JAXBContext}. Almost everything what JAXB-RI does is implemented using Spring features
 * (metadata scanning, handling of JavaBeans, conversion, formatting, etc.)</p>
 *
 * @author Grzegorz Grzybek
 */
@SuppressWarnings("deprecation")
public class SweJaxbContext extends JAXBContext {

	/**
	 * Mapping of Java classes to OXM metadata. This metadata is about how Java class maps to a given XML Schema type (simple or complex).
	 * This mapping doesn't deal with XML Schema elements - these are determined for each marshal operation, not at the creation time.
	 */
	Map<Class<?>, XmlEventsPattern<?>> patterns = new LinkedHashMap<Class<?>, XmlEventsPattern<?>>();

	// /**
	// * Mapping of XML Schema types (simple or complex) to OXM metadata.
	// */
	// private Map<QName, XmlEventsPattern> xsdPatterns = new LinkedHashMap<QName, XmlEventsPattern>();

	/**
	 * Mapping of {@link XmlRootElement} annotated classes.
	 */
	Map<Class<?>, ElementPattern<?>> rootPatterns = new LinkedHashMap<Class<?>, ElementPattern<?>>();

	/**
	 * Mapping of QNames of root elements to patterns. It's just another keyset of {@code rootPatterns}
	 */
	Map<QName, ElementPattern<?>> rootPatternsForQNames = new LinkedHashMap<QName, ElementPattern<?>>();

	/**
	 * Metadata of packages
	 */
	private Map<String, PackageMetadata> package2meta = new HashMap<String, PackageMetadata>();

//	/**
//	 * A map of cached metadata for faster version of {@link DirectFieldAccessor}s
//	 */
//	private Map<Class<?>, Map<String, Field>> directFieldsMap = new HashMap<Class<?>, Map<String, Field>>();

//	/**
//	 * Conversion service. To be accessed by marshallers/unmarshallers created by this context.
//	 */
//	private FormattingConversionService formattingConversionService = null;

	/**
	 * <p>Main initiailzation method of {@link JAXBContext} - isn't it clean?</p>
	 * 
	 * @param classesToBeBound
	 * @param properties
	 */
	SweJaxbContext(Class<?>[] classesToBeBound, Map<String, ?> properties) {
		this.initializeConversionService();

		// built-in
		BuiltInMappings.initialize(this.patterns/*, this.formattingConversionService*/);

		// external
		for (Class<?> cl : classesToBeBound) {
			this.patterns.put(cl, this.determineXmlPattern(cl));
		}
	}

	/* (non-Javadoc)
	 * @see javax.xml.bind.JAXBContext#createUnmarshaller()
	 */
	@Override
	public Unmarshaller createUnmarshaller() throws JAXBException {
		return new SweJaxbUnmarshaller(this);
	}

	/* (non-Javadoc)
	 * @see javax.xml.bind.JAXBContext#createMarshaller()
	 */
	@Override
	public Marshaller createMarshaller() throws JAXBException {
		return new SweJaxbMarshaller(this);
	}

	/* (non-Javadoc)
	 * @see javax.xml.bind.JAXBContext#createValidator()
	 */
	@Override
	public Validator createValidator() throws JAXBException {
		throw new UnsupportedOperationException("Not implemented in " + this.getClass().getName());
	}

	/* Internal (non JAXB) methods */

	/**
	 * 
	 */
	private void initializeConversionService() {
//		this.formattingConversionService = new FormattingConversionService();
	}

//	/**
//	 * @return the formattingConversionService
//	 */
//	public FormattingConversionService getFormattingConversionService() {
//		return this.formattingConversionService;
//	}

	/**
	 * <p>Converts a class and it's metadata into a pattern of static and dynamic {@link XMLEvent XML events}.</p>
	 * <p>A class which is to be known by this context should be directly convertible to a series of XML events. What is not mandatory here is
	 * the root element of the marshalled object.</p>
	 * <p>The produced pattern is <b>not</b> automatically cached in this context's metadata - that's the job of invoker.</p>
	 * 
	 * @param cl
	 * @return
	 */
	private <T> XmlEventsPattern<T> determineXmlPattern(Class<T> cl) {
		if (this.patterns.containsKey(cl)) {
			@SuppressWarnings("unchecked")
			XmlEventsPattern<T> pattern = (XmlEventsPattern<T>) this.patterns.get(cl);
			return pattern;
		}

		// default
		XmlAccessType xmlAccessType = XmlAccessType.PUBLIC_MEMBER;
		String namespace = NamespaceUtils.packageNameToNamespace(cl.getPackage());
		XmlNsForm elementFormDefault = XmlNsForm.UNQUALIFIED; // (XmlNsForm.UNSET = "unqualified" from XML Schema point of view)
		XmlNsForm attributeFormDefault = XmlNsForm.UNQUALIFIED; // (XmlNsForm.UNSET = "unqualified" from XML Schema point of view)

		// on package
		if (!this.package2meta.containsKey(cl.getPackage().getName())) {
			// initialize package metadata
			PackageMetadata md = new PackageMetadata();
			// determine package-level default accessor type
			XmlAccessorType xmlAccessorType = AnnotationUtils.getAnnotation(cl.getPackage(), XmlAccessorType.class);
			XmlSchema xmlSchema = AnnotationUtils.getAnnotation(cl.getPackage(), XmlSchema.class);

			xmlAccessType = xmlAccessorType == null || xmlAccessorType.value() == null ? xmlAccessType : xmlAccessorType.value();
			namespace = xmlSchema == null || xmlSchema.namespace() == null ? namespace : xmlSchema.namespace();
			elementFormDefault = xmlSchema == null || xmlSchema.elementFormDefault() == null ? elementFormDefault : xmlSchema.elementFormDefault();
			attributeFormDefault = xmlSchema == null || xmlSchema.attributeFormDefault() == null ? attributeFormDefault : xmlSchema.attributeFormDefault();
			md.setXmlAccessType(xmlAccessType);
			md.setNamespace(namespace);
			md.setElementFormDefault(elementFormDefault);
			md.setAttributeFormDefault(attributeFormDefault);

			this.package2meta.put(cl.getPackage().getName(), md);
		}
		else {
			PackageMetadata md = this.package2meta.get(cl.getPackage().getName());
			xmlAccessType = md.getXmlAccessType();
			namespace = md.getNamespace();
			elementFormDefault = md.getElementFormDefault();
			attributeFormDefault = md.getAttributeFormDefault();
		}

		// on class
		XmlAccessorType xmlAccessorType = AnnotationUtils.findAnnotation(cl, XmlAccessorType.class);
		XmlType xmlType = AnnotationUtils.getAnnotation(cl, XmlType.class);
		if (xmlAccessorType != null)
			xmlAccessType = xmlAccessorType.value();
		if (xmlType != null)
			namespace = "##default".equals(xmlType.namespace()) ? namespace : xmlType.namespace();

		// before stepping into the class we'll add DeferredXmlPattern to the mapping to be able to analyze cross-dependent classes
		// TODO: determine QName for TemporaryXmlEventsPattern
		TemporaryXmlEventsPattern<T> txp = TemporaryXmlEventsPattern.newTemporaryXmlEventsPattern(null, cl);
		this.patterns.put(cl, txp);
		PropertyCallback pc = new PropertyCallback(namespace, xmlAccessType, elementFormDefault, attributeFormDefault);
		ContentModelPattern<T> mapping = pc.analyze(cl);
		txp.setRealPattern(mapping);
//
//		// the map of (already accessible) fields is cached once here
//		this.directFieldsMap.put(cl, pc.getFieldMap());

		XmlRootElement xmlRootElement = AnnotationUtils.findAnnotation(cl, XmlRootElement.class);
		if (xmlRootElement != null) {
			// we may produce WrappedEventsPattern now, if the class is annotated with XmlRootElement
			// TODO: determine QName for ElementPattern
			QName rootQName = new QName(xmlRootElement.namespace(), xmlRootElement.name());
			ElementPattern<?> pattern = ElementPattern.newElementPattern(new QName(namespace, cl.getSimpleName()), cl, rootQName, mapping);
			this.rootPatterns.put(cl, pattern);
			this.rootPatternsForQNames.put(rootQName, pattern);

		}

		return mapping;
	}

	/**
	 * <p>This class analyzes bean and field properties of class and produces {@link XmlEventsPattern} representing this class in terms of OXM.</p>
	 * 
	 * <p>Every class maps to a sequence (in XSD specialized as sequence, choice or all) of patterns for each class' property. The nested patterns
	 * are of three categories:<ul>
	 * <li>XML element</li>
	 * <li>XML attribute</li>
	 * <li>XML value</li>
	 * </ul>The pattern for analyzed class is <b>always</b> a {@link ContentModelPattern}.</p>
	 *
	 * @author Grzegorz Grzybek
	 */
	private class PropertyCallback implements FieldCallback, MethodCallback {

		private final List<PropertyMetadata> childAttributePatterns = new LinkedList<PropertyMetadata>();

		private final List<PropertyMetadata> childPatterns = new LinkedList<PropertyMetadata>();
		
//		/** Metadata used by faster {@link DirectFieldAccessor}s */
//		private final Map<String, Field> fieldMap = new HashMap<String, Field>();

		private String namespace;

		private XmlAccessType accessType;

		@SuppressWarnings("unused")
		private XmlNsForm elementFormDefault;

		private XmlNsForm attributeFormDefault;

		/**
		 * @param namespace
		 * @param attributeFormDefault 
		 * @param elementFormDefault 
		 */
		public PropertyCallback(String namespace, XmlAccessType accessType, XmlNsForm elementFormDefault, XmlNsForm attributeFormDefault) {
			this.namespace = namespace;
			this.accessType = accessType;
			this.elementFormDefault = elementFormDefault;
			this.attributeFormDefault = attributeFormDefault;
		}

		/**
		 * Reads class' metadata and returns a {@link XmlEventsPattern pattern of XML events} which may be used to marshal an object of this class.
		 * 
		 * @param cl
		 * @return
		 */
		public <T> ContentModelPattern<T> analyze(Class<T> cl) {
			ReflectionUtils.doWithFields(cl, this, new FieldFilter() {
				@Override
				public boolean matches(Field field) {
					return !Modifier.isStatic(field.getModifiers());
				}
			});
			ReflectionUtils.doWithMethods(cl, this);

			// @XmlAttributes first. then @XmlElements + @XmlValue
			this.childAttributePatterns.addAll(this.childPatterns);

			// TODO: determine QName for ContentModelPattern
			ContentModelPattern<T> contentModelPattern = ContentModelPattern.newContentModelPattern(new QName(this.namespace, cl.getSimpleName()), cl, this.childAttributePatterns);
			return contentModelPattern;
		}

		/* (non-Javadoc)
		 * @see org.springframework.util.ReflectionUtils.MethodCallback#doWith(java.lang.reflect.Method)
		 */
		@Override
		public void doWith(Method method) throws IllegalArgumentException, IllegalAccessException {
			// TODO: analyze bean properties for XSD Content Model
		}

		/* (non-Javadoc)
		 * @see org.springframework.util.ReflectionUtils.FieldCallback#doWith(java.lang.reflect.Field)
		 */
		@Override
		public void doWith(Field field) throws IllegalArgumentException, IllegalAccessException {
			String fieldName = field.getName();
			// metadata for a field
			PropertyMetadata metadata = new PropertyMetadata(fieldName, true);
			ReflectionUtils.makeAccessible(field);
			metadata.setField(field);

			if (AnnotationUtils.getAnnotation(field, XmlTransient.class) != null)
				return;

			// is it value?
			XmlValue xmlValue = AnnotationUtils.getAnnotation(field, XmlValue.class);
			if (xmlValue != null) {
				// we (may) have simple type
				// a simpleType contains only one @XmlValue annotation. In terms of XML Schema - it has simpleContent (and possibly attributes)
				// field's class should be convertible to java.lang.String
				// this class should have no child @XmlElements
				// TODO: make possible to handle properties which are classes with @XmlValue property (possibly nested)
				// TODO: determine QName for ValuePattern
				ValuePattern<?> valuePattern = ValuePattern.newValuePattern(null, field.getType());
//				valuePattern.setConversionService(SweJaxbContext.this.formattingConversionService);
				metadata.setPattern(valuePattern);
				this.childPatterns.add(metadata);
				return;
			}

			// is it an attribute?
			XmlAttribute xmlAttribute = AnnotationUtils.getAnnotation(field, XmlAttribute.class);
			if (xmlAttribute != null) {
				String namespace = XMLConstants.NULL_NS_URI;
				if (this.attributeFormDefault == XmlNsForm.QUALIFIED) {
					// the attribute MUST have namespace
					namespace = "##default".equals(xmlAttribute.namespace()) ? this.namespace : xmlAttribute.namespace();
				}
				else {
					// the attribute MAY have namespace
					// TODO: handle org.springframework.ws.ext.bind.annotations.XmlAttribute
					if (!"##default".equals(xmlAttribute.namespace()))
						namespace = xmlAttribute.namespace();
				}
				String name = "##default".equals(xmlAttribute.name()) ? fieldName : xmlAttribute.name();
				// TODO: must be ValuePattern for simple type. Check it
				XmlEventsPattern<?> attrValuePattern = SweJaxbContext.this.determineXmlPattern(field.getType());
				AttributePattern<?> attributePattern = AttributePattern.newAttributePattern(attrValuePattern.getSchemaType(), field.getType(), new QName(namespace, name));
//				attributePattern.setConversionService(SweJaxbContext.this.formattingConversionService);
				metadata.setPattern(attributePattern);
				this.childAttributePatterns.add(metadata);
				return;
			}

			// is it an element?
			XmlElement xmlElement = AnnotationUtils.getAnnotation(field, XmlElement.class);
			// field is also an element when told so using XmlAccessorType
			boolean isElement = false;

			if (this.accessType == XmlAccessType.FIELD)
				isElement = true;
			else if (this.accessType == XmlAccessType.PUBLIC_MEMBER && Modifier.isPublic(field.getModifiers()))
				isElement = true;

			if (xmlElement != null || isElement) {
				String namespace = xmlElement == null || "##default".equals(xmlElement.namespace()) ? this.namespace : xmlElement.namespace();
				String name = xmlElement == null || "##default".equals(xmlElement.name()) ? fieldName : xmlElement.name();
				// TODO: determine QName for ElementPattern - use @XmlType
				@SuppressWarnings("rawtypes")
				XmlEventsPattern nestedPattern = SweJaxbContext.this.determineXmlPattern(field.getType());
				@SuppressWarnings("unchecked")
				XmlEventsPattern<?> elementPattern = ElementPattern.newElementPattern(nestedPattern.getSchemaType(), field.getType(), new QName(namespace, name), nestedPattern);
				metadata.setPattern(elementPattern);
				this.childPatterns.add(metadata);
				return;
			}
		}
	}

}
