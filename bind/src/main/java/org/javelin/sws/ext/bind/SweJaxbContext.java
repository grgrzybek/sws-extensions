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

package org.javelin.sws.ext.bind;

import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.Map;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Marshaller;
import javax.xml.bind.Unmarshaller;
import javax.xml.bind.Validator;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlSchema;
import javax.xml.bind.annotation.XmlType;
import javax.xml.namespace.QName;

import org.apache.ws.commons.schema.XmlSchemaType;
import org.javelin.sws.ext.bind.internal.BuiltInMappings;
import org.javelin.sws.ext.bind.internal.metadata.JaxbMetadata;
import org.javelin.sws.ext.bind.internal.metadata.PropertyCallback;
import org.javelin.sws.ext.bind.internal.model.ElementPattern;
import org.javelin.sws.ext.bind.internal.model.TemporaryTypedPattern;
import org.javelin.sws.ext.bind.internal.model.TypedPattern;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.core.annotation.AnnotationUtils;

/**
 * <p>Lightweight implementation of {@link JAXBContext}. Almost everything what JAXB-RI does is implemented using Spring features
 * (metadata scanning, handling of JavaBeans, conversion, formatting, etc.)</p>
 * 
 * <p>Construction of {@link JAXBContext} is the process of <i>lifting up</i> (in Category theory terms using <i>functor</i>) the Category
 * of Java classes into the Category of what we call <i>XML Patterns</i>. In Java Category, Java classes are transformed (morphed) into Java objects;
 * in <i>XML Patterns</i> Category, we morph between XML Patterns and Java objects. Of course this analogy needs more clarification!</p>
 * 
 * <p>The problem with {@link JAXBContext} is that it also provides the method {@link JAXBContext#generateSchema(javax.xml.bind.SchemaOutputResolver)}
 * which is <b>not</b> an OXM functionality! This single method brings second (ang huge!) responsibility to {@link JAXBContext}. It is no longer
 * a recipe for changing Java objects into XML and vice versa. The <i>Patterns</i> need also be aware of the meta-information on the XML
 * fragments they produce/consume. We'll use what's best for this - Apache WS XmlSchema library.</p>
 * 
 * <p>Having said that, this {@link JAXBContext} need to analyze the managed classes with JAXB annotations in order to:<ul>
 * <li>create recipes for marshalling/unmarshalling (the only relation to (TODO: check) XSD is with {@code xsi:type} attributes and XSD type <b>names</b>)</li>
 * <li>create metadata describing as many properties of XML Schema Components as possible with what JAXB annotations (and maybe new, future JAXB3 annotations?) provide</li>
 * </ul></p>
 *
 * @author Grzegorz Grzybek
 */
@SuppressWarnings("deprecation")
public class SweJaxbContext extends JAXBContext implements TypedPatternRegistry {

	private static Logger log = LoggerFactory.getLogger(SweJaxbContext.class.getName());

	/**
	 * Mapping of Java classes to OXM metadata. This metadata is about how Java class maps to a given XML Schema type (simple or complex)
	 * This mapping doesn't deal with XML Schema elements - these are determined for each marshal operation, not at the context creation time.
	 */
	Map<Class<?>, TypedPattern<?>> patterns = new LinkedHashMap<Class<?>, TypedPattern<?>>();

	/**
	 * Mapping of XSD type QNames to {@link TypedPattern}s. This is useful for finding patterns for Java properties annotated
	 * with {@link XmlSchemaType} annotation.
	 */
	Map<QName, TypedPattern<?>> patternsForTypeQNames = new LinkedHashMap<QName, TypedPattern<?>>();

	/**
	 * Mapping of {@link XmlRootElement} annotated classes to element patterns.
	 */
	Map<Class<?>, ElementPattern<?>> rootPatterns = new LinkedHashMap<Class<?>, ElementPattern<?>>();

	/**
	 * Mapping of QNames of root elements to element patterns. It's just another keyset of {@code rootPatterns}.
	 */
	Map<QName, ElementPattern<?>> rootPatternsForQNames = new LinkedHashMap<QName, ElementPattern<?>>();

	/**
	 * Metadata of packages
	 */
	private Map<String, JaxbMetadata> package2meta = new HashMap<String, JaxbMetadata>();

	/**
	 * <p>Main initiailzation method of {@link JAXBContext} - isn't it clean?</p>
	 * 
	 * @param classesToBeBound
	 * @param properties
	 */
	SweJaxbContext(Class<?>[] classesToBeBound, Map<String, ?> properties) {
		// built-in types
		BuiltInMappings.initialize(this.patterns, this.patternsForTypeQNames);

		// external types
		for (Class<?> cl : classesToBeBound) {
			TypedPattern<?> pattern = this.determineXmlPattern(cl);
			this.patterns.put(pattern.getJavaType(), pattern);
			this.patternsForTypeQNames.put(pattern.getSchemaType(), pattern);
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

	/* TypedPatternRegistry */

	/* (non-Javadoc)
	 * @see org.javelin.sws.ext.bind.TypedPatternRegistry#hasPatternForClass(java.lang.Class)
	 */
	@Override
	public boolean hasPatternForClass(Class<?> clazz) {
		return this.patterns.containsKey(clazz) && !(this.patterns.get(clazz) instanceof TemporaryTypedPattern);
	}

	/* (non-Javadoc)
	 * @see org.javelin.sws.ext.bind.TypedPatternRegistry#findTypedPattern(javax.xml.namespace.QName, java.lang.Class)
	 */
	@Override
	@SuppressWarnings("unchecked")
	public <T> TypedPattern<T> findTypedPattern(QName typeName, Class<T> clazz) {
		return (TypedPattern<T>) this.patternsForTypeQNames.get(typeName);
	}

	/* (non-Javadoc)
	 * @see org.javelin.sws.ext.bind.TypedPatternRegistry#determineXmlPattern(java.lang.Class)
	 */
	@Override
	public <T> TypedPattern<T> determineXmlPattern(Class<T> cl) {
		if (this.patterns.containsKey(cl)) {
			@SuppressWarnings("unchecked")
			TypedPattern<T> pattern = (TypedPattern<T>) this.patterns.get(cl);
			return pattern;
		}

		log.trace("Mapping {} class to TypedPattern", cl.getName());

		// defaults
		JaxbMetadata meta = this.package2meta.get(cl.getPackage().getName());

		// package customization - cached
		if (!this.package2meta.containsKey(cl.getPackage().getName())) {
			meta = JaxbMetadata.defaultForPackage(cl.getPackage());
			// determine package-level default accessor type
			XmlAccessorType xmlAccessorType = AnnotationUtils.getAnnotation(cl.getPackage(), XmlAccessorType.class);
			XmlSchema xmlSchema = AnnotationUtils.getAnnotation(cl.getPackage(), XmlSchema.class);

			if (xmlAccessorType != null)
				meta.setXmlAccessType(xmlAccessorType.value());
			if (xmlSchema != null) {
				meta.setNamespace(xmlSchema.namespace());
				meta.setElementForm(xmlSchema.elementFormDefault());
				meta.setAttributeForm(xmlSchema.attributeFormDefault());
			}

			this.package2meta.put(cl.getPackage().getName(), meta);
		}

		// class customization - not cached
		XmlAccessorType xmlAccessorType = AnnotationUtils.findAnnotation(cl, XmlAccessorType.class);
		XmlType xmlType = AnnotationUtils.getAnnotation(cl, XmlType.class);

		XmlAccessType xmlAccessType = xmlAccessorType == null ? meta.getXmlAccessType() : xmlAccessorType.value();
		String namespace = xmlType == null || "##default".equals(xmlType.namespace()) ? meta.getNamespace() : xmlType.namespace();
		QName typeName = new QName(namespace, xmlType == null || "##default".equals(xmlType.name()) ? cl.getSimpleName() : xmlType.name());

		// before stepping into the class we'll add TemporaryTypedPattern to the mapping to be able to analyze cross-dependent classes
		TemporaryTypedPattern<T> txp = TemporaryTypedPattern.newTemporaryTypedPattern(typeName, cl);
		this.patterns.put(cl, txp);

		// this is where the magic happens
		PropertyCallback<T> pc = new PropertyCallback<T>(this, cl, typeName, xmlAccessType, meta.getElementForm(), meta.getAttributeForm());
		TypedPattern<T> mapping = pc.analyze();
		txp.setRealPattern(mapping);

		// cache known global elements
		XmlRootElement xmlRootElement = AnnotationUtils.findAnnotation(cl, XmlRootElement.class);
		if (xmlRootElement != null) {
			String rootElementNamespace = "##default".equals(xmlRootElement.namespace()) ? meta.getNamespace() : xmlRootElement.namespace();
			String rootElementName = "##default".equals(xmlRootElement.name()) ? cl.getSimpleName() : xmlRootElement.name();
			QName rootQName = new QName(rootElementNamespace, rootElementName);

			ElementPattern<?> pattern = ElementPattern.newElementPattern(rootQName, mapping);
			this.rootPatterns.put(cl, pattern);
			this.rootPatternsForQNames.put(rootQName, pattern);
		}

		return mapping;
	}
}
