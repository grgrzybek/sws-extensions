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
import java.text.ParseException;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Locale;
import java.util.Map;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Marshaller;
import javax.xml.bind.Unmarshaller;
import javax.xml.bind.Validator;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlSchema;
import javax.xml.bind.annotation.XmlTransient;
import javax.xml.bind.annotation.XmlValue;
import javax.xml.namespace.QName;
import javax.xml.stream.events.XMLEvent;

import org.springframework.core.annotation.AnnotationUtils;
import org.springframework.core.convert.ConversionService;
import org.springframework.core.convert.converter.Converter;
import org.springframework.format.Formatter;
import org.springframework.format.support.FormattingConversionService;
import org.springframework.util.ReflectionUtils;
import org.springframework.util.ReflectionUtils.FieldCallback;
import org.springframework.util.ReflectionUtils.FieldFilter;
import org.springframework.util.ReflectionUtils.MethodCallback;
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
 * <p></p>
 *
 * @author Grzegorz Grzybek
 */
@SuppressWarnings("deprecation")
public class SweJaxbContext extends JAXBContext {

	/**
	 * Mapping of Java classes to OXM metadata. This metadata is about how Java class maps to a given XML Schema type (simple or complex).
	 * This mapping doesn't deal with XML Schema elements - these are determined for each marshal operation, not at the creation time.
	 */
	Map<Class<?>, XmlEventsPattern> patterns = new LinkedHashMap<Class<?>, XmlEventsPattern>();

	/**
	 * Mapping of XML Schema types (simple or complex) to OXM metadata.
	 */
	Map<QName, XmlEventsPattern> xsdPatterns = new LinkedHashMap<QName, XmlEventsPattern>();

	/**
	 * Mapping of {@link XmlRootElement} annotated classes.
	 */
	Map<Class<?>, ElementPattern> rootPatterns = new LinkedHashMap<Class<?>, ElementPattern>();

	/**
	 * Metadata of packages
	 */
	Map<String, PackageMetadata> package2meta = new HashMap<String, PackageMetadata>();

	/**
	 * Conversion service. To be accessed by marshallers/unmarshallers created by this context.
	 */
	FormattingConversionService formattingConversionService = null;

	/**
	 * <p>Main initiailzation method of {@link JAXBContext}</p>
	 * <p>This method:<ul>
	 * <li>constructs class2Patterns information</li>
	 * <li>constructs {@link ConversionService} and registers default {@link Converter converters} and/or {@link Formatter formatters}</li>
	 * <li></li>
	 * <li></li>
	 * <li></li>
	 * <li></li>
	 * </ul></p>
	 * 
	 * @param classesToBeBound
	 * @param properties
	 */
	SweJaxbContext(Class<?>[] classesToBeBound, Map<String, ?> properties) {

		this.initializeConversionService();

		this.initializeBuiltInMappings();

		for (Class<?> cl : classesToBeBound) {
			this.patterns.put(cl, this.determineXmlPattern(cl));
		}
	}

	/* (non-Javadoc)
	 * @see javax.xml.bind.JAXBContext#createUnmarshaller()
	 */
	@Override
	public Unmarshaller createUnmarshaller() throws JAXBException {
		return new JwsJaxbUnmarshaller(this);
	}

	/* (non-Javadoc)
	 * @see javax.xml.bind.JAXBContext#createMarshaller()
	 */
	@Override
	public Marshaller createMarshaller() throws JAXBException {
		return new JwsJaxbMarshaller(this);
	}

	/* (non-Javadoc)
	 * @see javax.xml.bind.JAXBContext#createValidator()
	 */
	@Override
	public Validator createValidator() throws JAXBException {
		throw new UnsupportedOperationException("Not implemented in " + this.getClass().getName());
	}

	/* Internal methods */

	/**
	 * 
	 */
	private void initializeConversionService() {
		this.formattingConversionService = new FormattingConversionService();
	}

	/**
	 * <p>Built-in mappings are configured for classes convertible to {@link String}</p>
	 * <p>See:<ul>
	 * <li><a href="http://www.w3.org/TR/xmlschema-2/#built-in-datatypes">http://www.w3.org/TR/xmlschema-2/#built-in-datatypes</a></li>
	 * <li>JAXB 2.2, section 6.2.2.</li>
	 * </ul></p>
	 */
	private void initializeBuiltInMappings() {
		// see: com.sun.xml.bind.v2.model.impl.RuntimeBuiltinLeafInfoImpl<T> and inner
		// com.sun.xml.bind.v2.model.impl.RuntimeBuiltinLeafInfoImpl.StringImpl<T> implementations

		// we have two places where XSD -> Java mapping is defined:
		// - JAX-RPC 1.1 section 4.2.1 Simple Types
		// - JAXB 2 section 6.2.2 Atomic Datatype

		// XML Schema (1.0) Part 2: Datatypes Second Edition, or/and
		// W3C XML Schema Definition Language (XSD) 1.1 Part 2: Datatypes

		// 3.2 Special Built-in Datatypes (#special-datatypes)
		// 3.2.1 anySimpleType (#anySimpleType)
		// 3.2.2 anyAtomicType (#anyAtomicType)

		// 3.3 Primitive Datatypes (#built-in-primitive-datatypes)
//		ValuePattern pattern = null;

		// 3.3.1 string (#string)
		this.patterns.put(String.class, new ValuePattern(SweJaxbConstants.XSD_STRING, java.lang.String.class));

		// 3.3.2 boolean (#boolean)
		this.patterns.put(Boolean.class, ValuePattern.INSTANCE);
		this.formattingConversionService.addFormatterForFieldType(Boolean.class, new Formatter<Boolean>() {
			@Override
			public String print(Boolean object, Locale locale) {
				return Boolean.toString(object);
			}
			@Override
			public Boolean parse(String text, Locale locale) throws ParseException {
				return Boolean.parseBoolean(text);
			}
		});

		// 3.3.3 decimal (#decimal)
		// 3.3.4 float (#float)
		// 3.3.5 double (#double)
		// 3.3.6 duration (#duration)
		// 3.3.7 dateTime (#dateTime)
		// 3.3.8 time (#time)
		// 3.3.9 date (#date)
		// 3.3.10 gYearMonth (#gYearMonth)
		// 3.3.11 gYear (#gYear)
		// 3.3.12 gMonthDay (#gMonthDay)
		// 3.3.13 gDay (#gDay)
		// 3.3.14 gMonth (#gMonth)
		// 3.3.15 hexBinary (#hexBinary)
		// 3.3.16 base64Binary (#base64Binary)
		// 3.3.17 anyURI (#anyURI)
		// 3.3.18 QName (#QName)
		// 3.3.19 NOTATION (#NOTATION)

		// 3.4 Other Built-in Datatypes (#ordinary-built-ins)

		// 3.4.1 normalizedString (#normalizedString)
		// 3.4.2 token (#token)
		// 3.4.3 language (#language)
		// 3.4.4 NMTOKEN (#NMTOKEN)
		// 3.4.5 NMTOKENS (#NMTOKENS)
		// 3.4.6 Name (#Name)
		// 3.4.7 NCName (#NCName)
		// 3.4.8 ID (#ID)
		// 3.4.9 IDREF (#IDREF)
		// 3.4.10 IDREFS (#IDREFS)
		// 3.4.11 ENTITY (#ENTITY)
		// 3.4.12 ENTITIES (#ENTITIES)
		// 3.4.13 integer (#integer)
		// 3.4.14 nonPositiveInteger (#nonPositiveInteger)
		// 3.4.15 negativeInteger (#negativeInteger)
		// 3.4.16 long (#long)
		// 3.4.17 int (#int)
		// 3.4.18 short (#short)
		// 3.4.19 byte (#byte)
		// 3.4.20 nonNegativeInteger (#nonNegativeInteger)
		// 3.4.21 unsignedLong (#unsignedLong)
		// 3.4.22 unsignedInt (#unsignedInt)
		// 3.4.23 unsignedShort (#unsignedShort)
		// 3.4.24 unsignedByte (#unsignedByte)
		// 3.4.25 positiveInteger (#positiveInteger)
		// 3.4.26 yearMonthDuration (#yearMonthDuration)
		// 3.4.27 dayTimeDuration (#dayTimeDuration)
		// 3.4.28 dateTimeStamp (#dateTimeStamp)

		this.patterns.put(Byte.class, ValuePattern.INSTANCE);
		this.formattingConversionService.addFormatterForFieldType(Byte.class, new Formatter<Byte>() {
			@Override
			public String print(Byte object, Locale locale) {
				return Byte.toString(object);
			}

			@Override
			public Byte parse(String text, Locale locale) throws ParseException {
				return Byte.parseByte(text);
			}
		});
		
		this.patterns.put(Short.class, ValuePattern.INSTANCE);
		this.formattingConversionService.addFormatterForFieldType(Short.class, new Formatter<Short>() {
			@Override
			public String print(Short object, Locale locale) {
				return Short.toString(object);
			}

			@Override
			public Short parse(String text, Locale locale) throws ParseException {
				return Short.parseShort(text);
			}
		});
		
		this.patterns.put(Integer.class, ValuePattern.INSTANCE);
		this.formattingConversionService.addFormatterForFieldType(Integer.class, new Formatter<Integer>() {
			@Override
			public String print(Integer object, Locale locale) {
				return Integer.toString(object);
			}

			@Override
			public Integer parse(String text, Locale locale) throws ParseException {
				return Integer.parseInt(text);
			}
		});
		
		this.patterns.put(Long.class, ValuePattern.INSTANCE);
		this.formattingConversionService.addFormatterForFieldType(Long.class, new Formatter<Long>() {
			@Override
			public String print(Long object, Locale locale) {
				return Long.toString(object);
			}

			@Override
			public Long parse(String text, Locale locale) throws ParseException {
				return Long.parseLong(text);
			}
		});
		
		this.patterns.put(Float.class, ValuePattern.INSTANCE);
		this.formattingConversionService.addFormatterForFieldType(Float.class, new Formatter<Float>() {
			@Override
			public String print(Float object, Locale locale) {
				return Float.toString(object);
			}

			@Override
			public Float parse(String text, Locale locale) throws ParseException {
				return Float.parseFloat(text);
			}
		});
		
		this.patterns.put(Double.class, ValuePattern.INSTANCE);
		this.formattingConversionService.addFormatterForFieldType(Double.class, new Formatter<Double>() {
			@Override
			public String print(Double object, Locale locale) {
				return Double.toString(object);
			}

			@Override
			public Double parse(String text, Locale locale) throws ParseException {
				return Double.parseDouble(text);
			}
		});

		// conversion service
		for (XmlEventsPattern vp: this.patterns.values())
			((ValuePattern)vp).setConversionService(this.formattingConversionService);

		// other simple types
		// JAXB2:
		/*
			class [B
			class java.awt.Image
			class java.io.File
			class java.lang.Character
			class java.lang.Class
			class java.lang.Void
			class java.math.BigDecimal
			class java.math.BigInteger
			class java.net.URI
			class java.net.URL
			class java.util.Calendar
			class java.util.Date
			class java.util.GregorianCalendar
			class java.util.UUID
			class javax.activation.DataHandler
			class javax.xml.datatype.Duration
			class javax.xml.datatype.XMLGregorianCalendar
			class javax.xml.namespace.QName
			interface javax.xml.transform.Source
		 */
	}

	/**
	 * <p>Converts a class and it's metadata into a pattern of static and dynamic {@link XMLEvent XML events}.</p>
	 * <p>A class which is to be known by this context should be directly convertible to a series of XML events. What is not mandatory here is
	 * the root element of the marshalled object.</p>
	 * <p>The produced pattern is <b>not</b> automatically cached in this context's metadata.</p>
	 * 
	 * @param cl
	 * @return
	 */
	private XmlEventsPattern determineXmlPattern(Class<?> cl) {
		if (this.patterns.containsKey(cl))
			return this.patterns.get(cl);

		// default
		XmlAccessType xmlAccessType = XmlAccessType.PUBLIC_MEMBER;
		String namespace = NamespaceUtils.packageNameToNamespace(cl.getPackage());

		// on package
		if (!this.package2meta.containsKey(cl.getPackage().getName())) {
			// initialize package metadata
			PackageMetadata md = new PackageMetadata();
			// determine package-level default accessor type
			XmlAccessorType xmlAccessorType = AnnotationUtils.getAnnotation(cl.getPackage(), XmlAccessorType.class);
			XmlSchema xmlSchema = AnnotationUtils.getAnnotation(cl.getPackage(), XmlSchema.class);

			xmlAccessType = xmlAccessorType == null || xmlAccessorType.value() == null ? xmlAccessType : xmlAccessorType.value();
			namespace = xmlSchema == null || xmlSchema.namespace() == null ? namespace : xmlSchema.namespace();
			md.setXmlAccessType(xmlAccessType);
			md.setNamespace(namespace);

			this.package2meta.put(cl.getPackage().getName(), md);
		}

		// on class
		XmlAccessorType xmlAccessorType = AnnotationUtils.findAnnotation(cl, XmlAccessorType.class);
		if (xmlAccessorType != null) {
			xmlAccessType = xmlAccessorType.value();
		}

		// before stepping into the class we'll add DeferredXmlPattern to the mapping to be able to analyze cross-dependent classes
		// TODO: determine QName for TemporaryXmlEventsPattern
		TemporaryXmlEventsPattern txp = new TemporaryXmlEventsPattern(null, cl);
		this.patterns.put(cl, txp);
		ContentModelPattern mapping = new PropertyCallback(namespace, xmlAccessType).analyze(cl);
		txp.setRealPattern(mapping);

		XmlRootElement xmlRootElement = AnnotationUtils.findAnnotation(cl, XmlRootElement.class);
		if (xmlRootElement != null) {
			// we may produce WrappedEventsPattern now, if the class is annotated with XmlRootElement
			// TODO: determine QName for ElementPattern
			this.rootPatterns.put(cl, new ElementPattern(null, cl, new QName(xmlRootElement.namespace(), xmlRootElement.name()), mapping));
		}

		return mapping;
	}

	/**
	 * @return the formattingConversionService
	 */
	public FormattingConversionService getFormattingConversionService() {
		return this.formattingConversionService;
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

		private String namespace;

		private XmlAccessType accessType;

		/**
		 * @param namespace
		 */
		public PropertyCallback(String namespace, XmlAccessType accessType) {
			this.namespace = namespace;
			this.accessType = accessType;
		}

		/**
		 * Reads class' metadata and returns a {@link XmlEventsPattern pattern of XML events} which may be used to marshal an object of this class.
		 * 
		 * @param cl
		 * @return
		 */
		public ContentModelPattern analyze(Class<?> cl) {
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
			return new ContentModelPattern(null, cl, this.childAttributePatterns);

			// if (this.childAttributePatterns.size() == 0 && this.childPatterns.size() == 1 && this.childPatterns.get(0).isSimpleType()) {
			// return this.childPatterns.get(0);
			// }
			// else {
			// // nested properties (except "class") - elements and/or attributes or attributes+@XmlValue
			// // attributes always first
			// }
		}

		/* (non-Javadoc)
		 * @see org.springframework.util.ReflectionUtils.MethodCallback#doWith(java.lang.reflect.Method)
		 */
		@Override
		public void doWith(Method method) throws IllegalArgumentException, IllegalAccessException {
		}

		/* (non-Javadoc)
		 * @see org.springframework.util.ReflectionUtils.FieldCallback#doWith(java.lang.reflect.Field)
		 */
		@Override
		public void doWith(Field field) throws IllegalArgumentException, IllegalAccessException {
			String fieldName = field.getName();
			// metadata for a field
			PropertyMetadata metadata = new PropertyMetadata(fieldName, true);

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
				ValuePattern valuePattern = new ValuePattern(null, field.getType());
				valuePattern.setConversionService(SweJaxbContext.this.formattingConversionService);
				metadata.setPattern(valuePattern);
				this.childPatterns.add(metadata);
				return;
			}

			// is it an attribute?
			XmlAttribute xmlAttribute = AnnotationUtils.getAnnotation(field, XmlAttribute.class);
			if (xmlAttribute != null) {
				String namespace = "##default".equals(xmlAttribute.namespace()) ? this.namespace : xmlAttribute.namespace();
				String name = "##default".equals(xmlAttribute.name()) ? fieldName : xmlAttribute.name();
				// TODO: determine QName for AttributePattern
				AttributePattern attributePattern = new AttributePattern(null, field.getType(), new QName(namespace, name));
				attributePattern.setConversionService(SweJaxbContext.this.formattingConversionService);
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
				// TODO: determine QName for ElementPattern
				XmlEventsPattern elementPattern = new ElementPattern(null, field.getType(), new QName(namespace, name), SweJaxbContext.this.determineXmlPattern(field.getType()));
				metadata.setPattern(elementPattern);
				this.childPatterns.add(metadata);
				return;
			}
		}
	}

}
