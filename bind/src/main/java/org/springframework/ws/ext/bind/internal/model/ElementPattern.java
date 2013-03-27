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

package org.springframework.ws.ext.bind.internal.model;

import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;

import javax.xml.bind.JAXBElement;
import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlElementWrapper;
import javax.xml.bind.annotation.XmlValue;
import javax.xml.namespace.QName;
import javax.xml.stream.XMLEventReader;
import javax.xml.stream.XMLEventWriter;
import javax.xml.stream.XMLStreamException;
import javax.xml.stream.events.Attribute;
import javax.xml.stream.events.StartElement;
import javax.xml.stream.events.XMLEvent;

import org.springframework.ws.ext.bind.internal.MarshallingContext;
import org.springframework.ws.ext.bind.internal.QNames;
import org.springframework.ws.ext.bind.internal.UnmarshallingContext;
import org.springframework.ws.ext.bind.internal.stax.AttributesAwareXMLEventReader;

/**
 * <p>{@link ElementPattern} wraps:<ul>
 * <li>{@link ContentModelPattern} when it represents non-primitive JavaBean object with properties ({@link XmlElement}, {@link XmlAttribute}, {@link XmlValue}, {@link XmlElementWrapper})</li>
 * <li>{@link ValuePattern} when constructed to marshal {@link JAXBElement}-wrapped primitive type or primitive {@link XmlElement}-annotated properties.</li>
 * </ul></p>
 * 
 * @author Grzegorz Grzybek
 */
public class ElementPattern<T> extends XmlEventsPattern<T> {

	private QName elementName;

	private XmlEventsPattern<T> nestedPattern;

	/**
	 * @param schemaType
	 * @param javaType
	 * @param elementName
	 * @param nestedPattern
	 */
	private ElementPattern(QName schemaType, Class<T> javaType, QName elementName, XmlEventsPattern<T> nestedPattern) {
		super(schemaType, javaType);
		this.elementName = elementName;
		this.nestedPattern = nestedPattern;
	}
	
	/**
	 * DESIGNFLAW: very ugly usage of type arguments!
	 * @param schemaType
	 * @param javaType
	 * @param elementName
	 * @param nestedPattern
	 * @return
	 */
	public static <T> ElementPattern<T> newElementPattern(QName schemaType, Class<T> javaType, QName elementName, XmlEventsPattern<T> nestedPattern) {
		return new ElementPattern<T>(schemaType, javaType, elementName, nestedPattern);
	}

	/* (non-Javadoc)
	 * @see org.springframework.ws.ext.bind.internal.model.XmlEventsPattern#replay(java.lang.Object, javax.xml.stream.XMLEventWriter, org.springframework.ws.ext.bind.internal.MarshallingContext)
	 */
	@Override
	public void replay(Object object, XMLEventWriter eventWriter, MarshallingContext context) throws XMLStreamException {
		// <elementName>
		StartElement startElement = this.safeCreateElement(context, eventWriter, this.elementName);

		boolean isNil = false;
		if (object instanceof JAXBElement) {
			// is it xsi:nil?
			if (((JAXBElement<?>) object).isNil())
				isNil = true;
			// dereference marshalled value
			object = ((JAXBElement<?>) object).getValue();
		}
		else if (object == null) {
			isNil = true;
		}

		// TODO: defer writing xsi:type for multiRefs
		// TODO: move safeGetQValue() and safeRegisterNamespace() out of this hierarchy into helper class
		if (context.isSendTypes()/* TODO: || must send xsi:type anyway*/) {
			String qValue = this.safeGetQValue(context, eventWriter, this.getSchemaType());
			String prefix = this.safeRegisterNamespace(context, eventWriter, QNames.XSI_TYPE);
			eventWriter.add(XML_EVENTS_FACTORY.createAttribute(prefix, QNames.XSI_TYPE.getNamespaceURI(), QNames.XSI_TYPE.getLocalPart(), qValue));
		}

		if (isNil) {
			String prefix = this.safeRegisterNamespace(context, eventWriter, QNames.XSI_NIL);
			eventWriter.add(XML_EVENTS_FACTORY.createAttribute(prefix, QNames.XSI_NIL.getNamespaceURI(), QNames.XSI_NIL.getLocalPart(), "true"));
		}
		else {
			if (context.isMultiRefEncoding()) {
				// choices:
				// - marshal ValuePattern.INSTANCE pattern "inline" or as @href?
				// - marshal single references "inline" or as @href to multiRef? - requires deferred marshalling to see wether there will more references
				// to this value
				// - add xsi:type?

				// the "href" attribute should be unqualified
				// TODO: what about @href attributes inside elements in default, non-empty namespace? Like: <a xmlns="b" href="#1" />...

				context.getMultiRefSupport().registerMultiRef(object, eventWriter, this.nestedPattern);
			}
			else {
				// inline children
				this.nestedPattern.replay(object, eventWriter, context);
			}
		}

		// </elementName>
		eventWriter.add(XML_EVENTS_FACTORY.createEndElement(startElement.getName(), null));
	}

	/* (non-Javadoc)
	 * @see org.springframework.ws.ext.bind.internal.model.XmlEventsPattern#consume(javax.xml.stream.XMLEventReader, org.springframework.ws.ext.bind.internal.UnmarshallingContext)
	 */
	@Override
	public Object consume(XMLEventReader eventReader, UnmarshallingContext context) throws XMLStreamException {
		// just skip element's START_ELEMENT event
		StartElement startElement = eventReader.nextEvent().asStartElement();
		
		// StartElement may contain attributes - these are NOT availabe as separate events in eventReader.nextEvent()!
		Iterator<?> attributes = startElement.getAttributes();
		List<Attribute> attrList = new LinkedList<Attribute>();
		while (attributes.hasNext()) {
			Attribute a = (Attribute) attributes.next();
			attrList.add(a);
		}

		Object value = this.nestedPattern.consume(new AttributesAwareXMLEventReader(eventReader, attrList), context);

		// skip element's END_ELEMENT event
		while (eventReader.peek() != null) {
			XMLEvent ev = eventReader.nextEvent();
			if (ev.getEventType() == XMLEvent.END_ELEMENT)
				break;
		}

		return value;
	}

	/* (non-Javadoc)
	 * @see org.springframework.ws.jaxws.soapenc.internal.model.XmlEventsPattern#isSimpleType()
	 */
	@Override
	public boolean isSimpleType() {
		return false;
	}

	/* (non-Javadoc)
	 * @see org.springframework.ws.jaxws.soapenc.internal.model.PropertyPattern#toString()
	 */
	@Override
	public String toString() {
		return "Pattern marshalled inside \"" + this.elementName + "\" ELEMENT event";
	}

	/**
	 * @return the elementName
	 */
	public QName getElementName() {
		return this.elementName;
	}

}
