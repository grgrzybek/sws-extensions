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

import javax.xml.bind.JAXBElement;
import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlElementWrapper;
import javax.xml.bind.annotation.XmlValue;
import javax.xml.namespace.NamespaceContext;
import javax.xml.namespace.QName;
import javax.xml.stream.XMLEventReader;
import javax.xml.stream.XMLEventWriter;
import javax.xml.stream.XMLStreamException;
import javax.xml.stream.events.EndElement;
import javax.xml.stream.events.StartElement;

import org.springframework.ws.ext.bind.internal.MarshallingContext;
import org.springframework.ws.ext.bind.internal.QNames;

/**
 * <p>{@link ElementPattern} wraps:<ul>
 * <li>{@link ContentModelPattern} when it represents non-primitive JavaBean object with properties ({@link XmlElement}, {@link XmlAttribute}, {@link XmlValue}, {@link XmlElementWrapper})</li>
 * <li>{@link ValuePattern} when constructed to marshall {@link JAXBElement}-wrapped primitive type or primitive {@link XmlElement}-annotated properties.</li>
 * </ul></p>
 *
 * @author Grzegorz Grzybek
 */
public class ElementPattern implements XmlEventsPattern {

	private QName elementName;
	private StartElement startElementEvent;
	private EndElement endElementEvent;

	private XmlEventsPattern nestedPattern;

	/**
	 * @param elementName
	 * @param directAccessor
	 * @param propertyName
	 * @param nestedPattern
	 */
	public ElementPattern(QName elementName, XmlEventsPattern nestedPattern) {
		this.elementName = elementName;
		this.nestedPattern = nestedPattern;

		this.startElementEvent = XML_EVENTS_FACTORY.createStartElement(this.elementName, null, null);
		this.endElementEvent = XML_EVENTS_FACTORY.createEndElement(this.elementName, null);
	}

	/* (non-Javadoc)
	 * @see org.springframework.ws.ext.bind.internal.model.XmlEventsPattern#replay(java.lang.Object, javax.xml.stream.XMLEventWriter, org.springframework.ws.ext.bind.internal.MarshallingContext)
	 */
	@Override
	public void replay(Object object, XMLEventWriter eventWriter, MarshallingContext context) throws XMLStreamException {
		// <elementName>
		eventWriter.add(this.startElementEvent);

		NamespaceContext nsc = eventWriter.getNamespaceContext();
		if (!context.isRepairingXmlEventWriter()) {
			if (nsc.getPrefix(this.elementName.getNamespaceURI()) == null) {
				eventWriter.add(XML_EVENTS_FACTORY.createNamespace(this.elementName.getNamespaceURI()));
			}
		}

		boolean isNil = false;
		// TODO: handle non "xsi" prefix for "http://www.w3.org/2001/XMLSchema-instance" namespace 
		if (object instanceof JAXBElement) {
			// is it xsi:nil?
			if (((JAXBElement<?>) object).isNil())
				isNil = true;
			// dereference marshalled value
			object = ((JAXBElement<?>) object).getValue();
		} else if (object == null) {
			isNil = true;
		}

		if (isNil) {
			if (!context.isRepairingXmlEventWriter() && nsc.getPrefix(QNames.XSI_NIL.getNamespaceURI()) == null) {
				eventWriter.add(XML_EVENTS_FACTORY.createNamespace(QNames.XSI_NIL.getPrefix(), QNames.XSI_NIL.getNamespaceURI()));
			}
			eventWriter.add(XML_EVENTS_FACTORY.createAttribute(QNames.XSI_NIL, "true"));
		} else {
			if (context.isMultiRefEncoding()) {
				// choices:
				//  - marshal ValuePattern.INSTANCE pattern "inline" or as @href?
				//  - marshal single references "inline" or as @href to multiRef? - requires deferred marshalling to see wether there will more references
				//    to this value
				//  - add xsi:type?
				
				// the "href" attribute should be unqualified
				// TODO: what about @href attributes inside elements in default, non-empty namespace? Like: <a xmlns="b" href="#1" />...
				
				context.getMultiRefSupport().registerMultiRef(object, eventWriter, this.nestedPattern);
			} else {
				// inline children
				this.nestedPattern.replay(object, eventWriter, context);
			}
		}

		// </elementName>
		eventWriter.add(this.endElementEvent);
	}

	/* (non-Javadoc)
	 * @see org.springframework.ws.ext.bind.internal.model.XmlEventsPattern#consume(javax.xml.stream.XMLEventReader)
	 */
	@Override
	public Object consume(XMLEventReader eventReader) throws XMLStreamException {
		return null;
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

}
