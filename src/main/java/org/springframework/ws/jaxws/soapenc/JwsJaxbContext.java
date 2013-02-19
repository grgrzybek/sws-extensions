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

package org.springframework.ws.jaxws.soapenc;

import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.lang.reflect.Modifier;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.LinkedList;
import java.util.List;
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
import org.springframework.util.ReflectionUtils;
import org.springframework.util.ReflectionUtils.FieldCallback;
import org.springframework.util.ReflectionUtils.FieldFilter;
import org.springframework.util.ReflectionUtils.MethodCallback;
import org.springframework.ws.jaxws.server.endpoint.support.NamespaceUtils;
import org.springframework.ws.jaxws.soapenc.internal.QNames;
import org.springframework.ws.jaxws.soapenc.internal.metadata.PackageMetadata;
import org.springframework.ws.jaxws.soapenc.internal.model.AttributePattern;
import org.springframework.ws.jaxws.soapenc.internal.model.ComplexContentPattern;
import org.springframework.ws.jaxws.soapenc.internal.model.ElementPattern;
import org.springframework.ws.jaxws.soapenc.internal.model.ValuePattern;
import org.springframework.ws.jaxws.soapenc.internal.model.WrappedXmlEventsPattern;
import org.springframework.ws.jaxws.soapenc.internal.model.XmlEventsPattern;

/**
 * <p></p>
 *
 * @author Grzegorz Grzybek
 */
@SuppressWarnings("deprecation")
public class JwsJaxbContext extends JAXBContext {

	public static final String PROPERTY_PREFIX = JwsJaxbContext.class.getPackage().getName();

	/**
	 * <p>If the value of this property is true, we try to be as much JAXB compliant as its ... reasonable. Otherwise we try to be pragmatic.</p>
	 * <p>This setting affects:<ul>
	 * <li>package scanning: Do we use ObjectFactory/@XmlRegistry/jaxb.index (compliant) or just all package's classes (pragmatic)?</li>
	 * </ul></p>
	 */
	public static final String PROPERTY_STRICT_JAXB = PROPERTY_PREFIX + ".strictJaxbCompliance";

	/**
	 * Mapping of Java classes to OXM metadata. This metadata is about how Java class maps to a given XML Schema type (simple or complex).
	 * This mapping doesn't deal with XML Schema elements - these are determined for each marshall operation, not at the creation time.
	 */
	Map<Class<?>, XmlEventsPattern> class2meta = new LinkedHashMap<Class<?>, XmlEventsPattern>();

	Map<Class<?>, WrappedXmlEventsPattern> class2rootPatterns = new LinkedHashMap<Class<?>, WrappedXmlEventsPattern>();

	/**
	 * Metadata of packages
	 */
	Map<String, PackageMetadata> package2meta = new HashMap<String, PackageMetadata>();

	/**
	 * @param classesToBeBound
	 * @param properties
	 */
	JwsJaxbContext(Class<?>[] classesToBeBound, Map<String, ?> properties) {
		this.initializeBuiltInMappings();

		for (Class<?> cl : classesToBeBound) {
			this.class2meta.put(cl, this.determineXmlPattern(cl));
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

	/**
	 * <p>See:<ul>
	 * <li><a href="http://www.w3.org/TR/xmlschema-2/#built-in-datatypes">http://www.w3.org/TR/xmlschema-2/#built-in-datatypes</a></li>
	 * <li>JAXB 2.2, section 6.2.2.</li>
	 * </ul></p>
	 */
	private void initializeBuiltInMappings() {
		// primitives
		// class2meta.put(boolean.class, new SimpleNonNillableType(QNames.SIMPLETYPE_BOOLEAN));
		// class2meta.put(byte.class, new SimpleNonNillableType(QNames.SIMPLETYPE_BYTE));
		// class2meta.put(short.class, new SimpleNonNillableType(QNames.SIMPLETYPE_SHORT));
		// class2meta.put(int.class, new SimpleNonNillableType(QNames.SIMPLETYPE_INT));
		// class2meta.put(long.class, new SimpleNonNillableType(QNames.SIMPLETYPE_LONG));
		// class2meta.put(float.class, new SimpleNonNillableType(QNames.SIMPLETYPE_FLOAT));
		// class2meta.put(double.class, new SimpleNonNillableType(QNames.SIMPLETYPE_DOUBLE));
		// wrappers
		this.class2meta.put(Boolean.class, new ValuePattern(QNames.SIMPLETYPE_BOOLEAN));
		this.class2meta.put(Byte.class, new ValuePattern(QNames.SIMPLETYPE_BYTE));
		this.class2meta.put(Short.class, new ValuePattern(QNames.SIMPLETYPE_SHORT));
		this.class2meta.put(Integer.class, new ValuePattern(QNames.SIMPLETYPE_INT));
		this.class2meta.put(Long.class, new ValuePattern(QNames.SIMPLETYPE_LONG));
		this.class2meta.put(Float.class, new ValuePattern(QNames.SIMPLETYPE_FLOAT));
		this.class2meta.put(Double.class, new ValuePattern(QNames.SIMPLETYPE_DOUBLE));
		// other simple types
		// JAXB2:
		/*
			class [B
			class java.awt.Image
			class java.io.File
			class java.lang.Boolean
			class java.lang.Byte
			class java.lang.Character
			class java.lang.Class
			class java.lang.Double
			class java.lang.Float
			class java.lang.Integer
			class java.lang.Long
			class java.lang.Short
			class java.lang.String
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
	 * 
	 * @param cl
	 * @return
	 */
	private XmlEventsPattern determineXmlPattern(Class<?> cl) {
		if (this.class2meta.containsKey(cl))
			return this.class2meta.get(cl);

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

		// first pass: we have two possibilites:
		// - complexType/sequence of elements + attributes = each property determines "nested pattern" (possibly many levels deep)
		// - simpleType with only one @XmlValue = single property maps to SimpleNillableType

		XmlEventsPattern mapping = new PropertyCallback(namespace, xmlAccessType).analyze(cl);

		XmlRootElement xmlRootElement = AnnotationUtils.findAnnotation(cl, XmlRootElement.class);
		if (xmlRootElement != null) {
			// we may produce WrappedEventsPattern now, if the class is annotated with XmlRootElement
			// only such classes will map to WrappedXmlEventsPattern in class2meta
			this.class2rootPatterns.put(cl, new WrappedXmlEventsPattern(mapping, new QName(xmlRootElement.namespace(), xmlRootElement.name())));
		}

		return mapping;
	}

	/**
	 * <p></p>
	 *
	 * @author Grzegorz Grzybek
	 */
	private class PropertyCallback implements FieldCallback, MethodCallback {

		private final List<XmlEventsPattern> childAttributePatterns = new LinkedList<XmlEventsPattern>();

		private final List<XmlEventsPattern> childPatterns = new LinkedList<XmlEventsPattern>();

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
		public XmlEventsPattern analyze(Class<?> cl) {
			ReflectionUtils.doWithFields(cl, this, new FieldFilter() {
				@Override
				public boolean matches(Field field) {
					return !Modifier.isStatic(field.getModifiers());
				}
			});
			ReflectionUtils.doWithMethods(cl, this);

			if (this.childAttributePatterns.size() == 0 && this.childPatterns.size() == 1 && this.childPatterns.get(0).isSimpleValue()) {
				return this.childPatterns.get(0);
			}
			else {
				// nested properties (except "class") - elements and/or attributes or attributes+@XmlValue
				// attributes always first
				this.childAttributePatterns.addAll(this.childPatterns);
				return new ComplexContentPattern(this.childPatterns);
			}
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

			if (AnnotationUtils.getAnnotation(field, XmlTransient.class) != null)
				return;

			// is it value?
			XmlValue xmlValue = AnnotationUtils.getAnnotation(field, XmlValue.class);
			if (xmlValue != null) {
				// we (may) have simple type
				// a simpleType contains only one @XmlValue annotation. In terms of XML Schema - it contains simpleContent
				// field's class should be simple!
				this.childPatterns.add(new ValuePattern());
				return;
			}

			// is it an attribute?
			XmlAttribute xmlAttribute = AnnotationUtils.getAnnotation(field, XmlAttribute.class);
			if (xmlAttribute != null) {
				String namespace = "##default".equals(xmlAttribute.namespace()) ? this.namespace : xmlAttribute.namespace();
				String name = "##default".equals(xmlAttribute.name()) ? fieldName : xmlAttribute.name();
				AttributePattern attributePattern = new AttributePattern(new QName(namespace, name), XmlAccessType.FIELD, fieldName);
				this.childPatterns.add(attributePattern);
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
				ElementPattern wrappedPattern = new ElementPattern(new QName(namespace, name), XmlAccessType.FIELD, fieldName, JwsJaxbContext.this.determineXmlPattern(field.getType()));
				this.childPatterns.add(wrappedPattern);
				return;
			}
		}
	}

}
