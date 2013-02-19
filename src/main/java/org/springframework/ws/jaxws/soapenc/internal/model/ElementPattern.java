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

package org.springframework.ws.jaxws.soapenc.internal.model;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.namespace.NamespaceContext;
import javax.xml.namespace.QName;
import javax.xml.stream.XMLEventWriter;
import javax.xml.stream.XMLStreamException;

import org.springframework.beans.BeanWrapper;

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
	 * @param accessType
	 * @param propertyName
	 * @param nestedPattern
	 */
	public ElementPattern(QName elementName, XmlAccessType accessType, String propertyName, XmlEventsPattern nestedPattern) {
		super(accessType, propertyName);
		this.elementName = elementName;
		this.nestedPattern = nestedPattern;
	}

	/* (non-Javadoc)
	 * @see org.springframework.ws.jaxws.soapenc.internal.model.XmlEventsPattern#replay(org.springframework.beans.BeanWrapper, javax.xml.stream.XMLEventWriter, boolean)
	 */
	@Override
	public void replay(BeanWrapper beanWrapper, XMLEventWriter eventWriter, boolean repairingWriter) throws XMLStreamException {
		eventWriter.add(XML_EVENTS_FACTORY.createStartElement(elementName, null, null));
		if (!repairingWriter) {
			NamespaceContext nsc = eventWriter.getNamespaceContext();
			if (nsc.getPrefix(elementName.getNamespaceURI()) == null) {
				eventWriter.add(XML_EVENTS_FACTORY.createNamespace(elementName.getNamespaceURI()));
			}
		}
		this.nestedPattern.replay(beanWrapper, eventWriter, repairingWriter);
		eventWriter.add(XML_EVENTS_FACTORY.createEndElement(elementName, null));
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
	public boolean isSimpleValue() {
		return false;
	}

}
