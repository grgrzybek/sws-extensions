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

package org.javelin.sws.ext.bind.internal.model;

import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlElementWrapper;
import javax.xml.bind.annotation.XmlTransient;
import javax.xml.bind.annotation.XmlValue;
import javax.xml.namespace.QName;
import javax.xml.stream.XMLEventReader;
import javax.xml.stream.XMLEventWriter;
import javax.xml.stream.XMLStreamConstants;
import javax.xml.stream.XMLStreamException;
import javax.xml.stream.events.Attribute;
import javax.xml.stream.events.StartElement;
import javax.xml.stream.events.XMLEvent;

import org.javelin.sws.ext.bind.SweJaxbContext;
import org.javelin.sws.ext.bind.internal.MarshallingContext;
import org.javelin.sws.ext.bind.internal.UnmarshallingContext;
import org.javelin.sws.ext.bind.internal.metadata.PropertyMetadata;
import org.javelin.sws.ext.bind.internal.metadata.PropertyMetadataValue;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.BeanWrapperImpl;
import org.springframework.beans.PropertyAccessor;
import org.springframework.beans.PropertyAccessorFactory;
import org.springframework.beans.PropertyValue;

/**
 * <p>A pattern for (un)marshalling object of {@code T} Java Class from/to XML fragments which may be described by XSD complex type
 * with either simple or complex content</p>
 * 
 * <p>While constructing {@link JAXBContext}, while analyzing the mapped class, each field and/or JavaBean accessor, when not {@link XmlTransient}
 * adds to the shape of XML fragment produced by marshal operation of the class instance. This pattern represent both the {@code content type}
 * and {@code attribute uses} properties of XSD Complex Type.</p>
 * 
 * <p>If there are no {@link XmlElement}/{@link XmlElementWrapper} annotated properties and there's single {@link XmlValue} property, this
 * class produces/consumes simple content (with optional attributes). If, additionally, there are no {@link XmlAttribute} annotated properties, the
 * analyzed Java class is considered a simple type and {@link SweJaxbContext} treats it almost as built-in or primitive XSD simple type.</p>
 * 
 * @author Grzegorz Grzybek
 * @param <T> A Java type which relates to XSD Complex Type Definition
 */
public class ComplexTypePattern<T> extends TypedPattern<T> {

	/** TODO: ensure that attributes come first in the content model */
	private List<PropertyMetadata<T, ?>> attributesAndComplexContent = new LinkedList<PropertyMetadata<T, ?>>();

	/** TODO: ensure that there's at most one simpleContent PropertyMetadata and (if present) there are no elements */
	private PropertyMetadata<T, ?> simpleContent = null;

	// for unmarshaller we may encounter nested (in relation to content model) attributes, elements and text
	private Map<QName, PropertyMetadata<T, ?>> attributes = new HashMap<QName, PropertyMetadata<T, ?>>();

	private Map<QName, PropertyMetadata<T, ?>> elements = new HashMap<QName, PropertyMetadata<T, ?>>();

	// optimization to check wether to create particular PropertyAccessor
	private boolean hasFieldProperties = false;

	/**
	 * <p>Initializes content model with a sequence (XSD's sequence, choice or all) of properties, each related to a given bean property and
	 * mapping to a given {@link XmlEventsPattern}.</p>
	 * 
	 * @param schemaType
	 * @param javaType
	 * @param contentModel TODO: think of better name - it's more than content model because it may contain attributes and simple content property metadata
	 */
	private ComplexTypePattern(QName schemaType, Class<T> javaType, List<PropertyMetadata<T, ?>> contentModel) {
		super(schemaType, javaType);
		this.attributesAndComplexContent = contentModel;

		for (PropertyMetadata<T, ?> pm : contentModel) {
			if (pm.isDirectProperty())
				this.hasFieldProperties = true;
			if (pm.getPattern() instanceof AttributePattern) {
				this.attributes.put(((AttributePattern<?>) pm.getPattern()).getAttributeName(), pm);
			}
			else if (pm.getPattern() instanceof ElementPattern) {
				this.elements.put(((ElementPattern<?>) pm.getPattern()).getElementName(), pm);
			}
			else if (pm.getPattern() instanceof SimpleContentPattern) {
				// TODO: check whethere there's only one SimpleContentPattern
				this.simpleContent = pm;
			}
		}
	}

	/**
	 * @param schemaType
	 * @param javaType
	 * @param contentModel
	 * @return
	 */
	public static <T> ComplexTypePattern<T> newContentModelPattern(QName schemaType, Class<T> javaType, List<PropertyMetadata<T, ?>> contentModel) {
		return new ComplexTypePattern<T>(schemaType, javaType, contentModel);
	}

	/* (non-Javadoc)
	 * @see org.javelin.sws.ext.bind.internal.model.TypedPattern#replayValue(java.lang.Object, javax.xml.stream.XMLEventWriter, org.javelin.sws.ext.bind.internal.MarshallingContext)
	 */
	@Override
	public void replayValue(T value, XMLEventWriter eventWriter, MarshallingContext context) throws XMLStreamException {
		// nested patterns will extract configured property (field or getter) from this beanWrapper.
		// in order to minimize a number of PropertyAccessor instances we will create them here and reuse for each direct/child property
		//		FastDirectFieldAccessor directFieldAccessor = null;
		//		PropertyAccessor beanPropertyAccessor = null;
		//		if (this.hasFieldProperties) {
		//			// we'll get to the wrapped instance through DirectFieldAccessor
		//			// we create it here, because there may be more field properties to be marshalled
		//			directFieldAccessor = new FastDirectFieldAccessor(this.contentModelDirectFieldMap, object);
		//		}
		//		else {
		// if there are no JAXB mapped fields, there should be JAXB mapped properties. Otherwise this would be a very strange JAXB class...
		//			beanPropertyAccessor = new BeanWrapperImpl(false);
		//			((BeanWrapperImpl) beanPropertyAccessor).setWrappedInstance(object);
		//		}

		// each nested pattern is related to some property of the marshalled object
		// this is the main responsibility of this pattern (ComplexTypePattern)
		// the order of properties is determined by this.contentModel, not by the order of bean properties
		for (PropertyMetadata<T, ?> pm : this.attributesAndComplexContent) {
			// here object is never JAXBElement
			Object v = pm.getValue(value);

			// for multi-ref encoding every property is an element - @XmlValue and @XmlAttribute too!
			if (context.isMultiRefEncoding()) {
				// TODO: not value?
				context.getMultiRefSupport().adaptPattern(pm.getPattern(), pm.getPropertyName()).replay(v, eventWriter, context);
			}
			else {
				pm.getPattern().replay(v, eventWriter, context);
			}
		}
	}

	/* (non-Javadoc)
	 * @see org.javelin.sws.ext.bind.internal.model.TypedPattern#consumeValue(javax.xml.stream.XMLEventReader, org.javelin.sws.ext.bind.internal.UnmarshallingContext)
	 */
	@SuppressWarnings("unchecked")
	@Override
	public T consumeValue(XMLEventReader eventReader, UnmarshallingContext context) throws XMLStreamException {
		// first create an object to be filled (using PropertyAccessors - direct or bean) according to the content model
		Object object = BeanUtils.instantiate(this.getJavaType());

		// prepare accessor(s)
		PropertyAccessor directFieldAccessor = null;
		PropertyAccessor beanPropertyAccessor = null;
		if (this.hasFieldProperties) {
			directFieldAccessor = PropertyAccessorFactory.forDirectFieldAccess(object);
		}
		else {
			// TODO: create bean accessor also when there are direct field properties - in case of get/set not related to fields
			// if there are no JAXB mapped fields, there should be JAXB mapped properties. Otherwise this would be a very strange JAXB class...
			beanPropertyAccessor = new BeanWrapperImpl(false);
			((BeanWrapperImpl) beanPropertyAccessor).setWrappedInstance(object);
		}

		// the order is dictated by incoming events, not by the mode
		// TODO: create a property to enable strict unmarshalling - dictated by content model
		// only this (ContentModel) pattern iterates over XML Events
		XMLEvent event = null;
		PropertyMetadataValue<T, ?> pmv = null;

		// this loop will only handle first level of start elements and only single end element
		// deeper levels will be handled by nested patterns
		while (true) {
			boolean end = false;
			event = eventReader.peek();
			pmv = null;

			switch (event.getEventType()) {
			case XMLStreamConstants.ATTRIBUTE:
				pmv = this.consumeNestedAttribute(eventReader, context);
				break;
			case XMLStreamConstants.CDATA:
			case XMLStreamConstants.CHARACTERS:
				// TODO: XMLEvent.ENTITY_REFERENCE?
				if (this.simpleContent != null) {
					pmv = this.consumeSimpleContent(eventReader, context);
					break;
				}
			case XMLStreamConstants.COMMENT:
			case XMLStreamConstants.DTD:
			case XMLStreamConstants.SPACE:
			case XMLStreamConstants.ENTITY_DECLARATION:
			case XMLStreamConstants.NOTATION_DECLARATION:
			case XMLStreamConstants.PROCESSING_INSTRUCTION:
				eventReader.nextEvent();
				break;
			case XMLStreamConstants.ENTITY_REFERENCE:
				// TODO: XMLEvent.ENTITY_REFERENCE?
				eventReader.nextEvent();
				break;
			case XMLStreamConstants.START_DOCUMENT:
				// strange
				break;
			case XMLStreamConstants.START_ELEMENT:
				pmv = this.consumeNestedElement(eventReader, context);
				break;
			case XMLStreamConstants.END_ELEMENT:
				// TODO: in mixed content there will be more than one end element it this content model's level
			case XMLStreamConstants.END_DOCUMENT:
				end = true;
				break;
			}

			if (end)
				break;

			if (pmv != null) {
				boolean direct = pmv.getMetadata().isDirectProperty();
				PropertyAccessor propertyAccessor = direct ? directFieldAccessor : beanPropertyAccessor;

				propertyAccessor.setPropertyValue(pmv.getOriginalPropertyValue());
			}
		}

		return (T) object;
	}

	/**
	 * Called when nested {@link XMLStreamConstants#START_ELEMENT} is found in the consumed content model
	 * 
	 * @param eventReader
	 * @param context
	 * @return
	 * @throws XMLStreamException
	 */
	private PropertyMetadataValue<T, ?> consumeNestedElement(XMLEventReader eventReader, UnmarshallingContext context) throws XMLStreamException {
		StartElement startElement = eventReader.peek().asStartElement();
		QName qName = startElement.getName();

		return this.consumeNestedProperty(this.elements.get(qName), eventReader, context);
	}

	/**
	 * @param eventReader
	 * @param context
	 * @return
	 */
	private PropertyMetadataValue<T, ?> consumeNestedAttribute(XMLEventReader eventReader, UnmarshallingContext context) throws XMLStreamException {
		Attribute attr = (Attribute) eventReader.peek();
		QName qName = attr.getName();

		return this.consumeNestedProperty(this.attributes.get(qName), eventReader, context);
	}

	/**
	 * @param eventReader
	 * @param context
	 * @return
	 */
	private PropertyMetadataValue<T, ?> consumeSimpleContent(XMLEventReader eventReader, UnmarshallingContext context) throws XMLStreamException {
		// TODO: properly handle simple content
		return this.consumeNestedProperty(this.simpleContent, eventReader, context);
	}

	/**
	 * @param propertyMetadata
	 * @return
	 */
	private PropertyMetadataValue<T, ?> consumeNestedProperty(PropertyMetadata<T, ?> propertyMetadata, XMLEventReader eventReader, UnmarshallingContext context)
			throws XMLStreamException {
		Object propertyValue = propertyMetadata.getPattern().consume(eventReader, context);
		return PropertyMetadataValue.newPropertyMetadataValue(new PropertyValue(propertyMetadata.getPropertyName(), propertyValue), propertyMetadata);
	}

	/* (non-Javadoc)
	 * @see org.springframework.ws.jaxws.soapenc.internal.model.XmlEventsPattern#isSimpleType()
	 */
	@Override
	public boolean isSimpleType() {
		return false;
	}

	/* (non-Javadoc)
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString() {
		return "A sequence of " + this.attributesAndComplexContent.size() + " nested property patterns";
	}

}
