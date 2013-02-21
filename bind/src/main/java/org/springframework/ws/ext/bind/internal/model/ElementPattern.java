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
import javax.xml.namespace.NamespaceContext;
import javax.xml.namespace.QName;
import javax.xml.stream.XMLEventReader;
import javax.xml.stream.XMLEventWriter;
import javax.xml.stream.XMLStreamException;
import javax.xml.stream.events.EndElement;
import javax.xml.stream.events.StartElement;

import org.springframework.ws.ext.bind.internal.QNames;

/**
 * <p>{@link ElementPattern} always wraps a {@link ContentModelPattern}</p>
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
	 * @see org.springframework.ws.jaxws.soapenc.internal.model.XmlEventsPattern#replay(java.lang.Object, javax.xml.stream.XMLEventWriter, boolean)
	 */
	@Override
	public void replay(Object object, XMLEventWriter eventWriter, boolean repairingWriter) throws XMLStreamException {
		eventWriter.add(this.startElementEvent);

		NamespaceContext nsc = eventWriter.getNamespaceContext();
		if (!repairingWriter) {
			if (nsc.getPrefix(this.elementName.getNamespaceURI()) == null) {
				eventWriter.add(XML_EVENTS_FACTORY.createNamespace(this.elementName.getNamespaceURI()));
			}
		}

		if (object instanceof JAXBElement) {
			// is it xsi:nil?
			if (((JAXBElement<?>) object).isNil()) {
				if (!repairingWriter && nsc.getPrefix(QNames.XSI_NIL.getNamespaceURI()) == null) {
					eventWriter.add(XML_EVENTS_FACTORY.createNamespace(QNames.XSI_NIL.getPrefix(), QNames.XSI_NIL.getNamespaceURI()));
				}
				eventWriter.add(XML_EVENTS_FACTORY.createAttribute(QNames.XSI_NIL, "true"));
			}
			// dereference marshalled value
			object = ((JAXBElement<?>) object).getValue();
		}

		this.nestedPattern.replay(object, eventWriter, repairingWriter);

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
