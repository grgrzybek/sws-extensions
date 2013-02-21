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

import javax.xml.namespace.NamespaceContext;
import javax.xml.namespace.QName;
import javax.xml.stream.XMLEventWriter;
import javax.xml.stream.XMLStreamException;

/**
 * <p></p>
 *
 * @author Grzegorz Grzybek
 */
public class ElementPattern extends PropertyPattern implements XmlEventsPattern {

	private QName elementName;
	private XmlEventsPattern nestedPattern;

	/**
	 * @param elementName
	 * @param directAccessor
	 * @param propertyName
	 * @param nestedPattern
	 */
	public ElementPattern(QName elementName, boolean directAccessor, String propertyName, XmlEventsPattern nestedPattern) {
		super(directAccessor, propertyName);
		this.elementName = elementName;
		this.nestedPattern = nestedPattern;
	}

	/* (non-Javadoc)
	 * @see org.springframework.ws.jaxws.soapenc.internal.model.XmlEventsPattern#replay(java.lang.Object, javax.xml.stream.XMLEventWriter, boolean)
	 */
	@Override
	public void replay(Object object, XMLEventWriter eventWriter, boolean repairingWriter) throws XMLStreamException {
		eventWriter.add(XML_EVENTS_FACTORY.createStartElement(this.elementName, null, null));

		NamespaceContext nsc = eventWriter.getNamespaceContext();
		if (!repairingWriter) {
			if (nsc.getPrefix(this.elementName.getNamespaceURI()) == null) {
				eventWriter.add(XML_EVENTS_FACTORY.createNamespace(this.elementName.getNamespaceURI()));
			}
		}

		this.nestedPattern.replay(object, eventWriter, repairingWriter);

		eventWriter.add(XML_EVENTS_FACTORY.createEndElement(this.elementName, null));
	}

	/* (non-Javadoc)
	 * @see org.springframework.ws.jaxws.soapenc.internal.model.XmlEventsPattern#isElement()
	 */
	@Override
	public boolean isElement() {
		return true;
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
		return super.toString() + " and " + this.elementName + " ELEMENT wrapping " + this.nestedPattern.toString();
	}

}
