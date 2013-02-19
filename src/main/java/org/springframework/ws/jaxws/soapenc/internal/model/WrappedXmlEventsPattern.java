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

import javax.xml.bind.JAXBElement;
import javax.xml.namespace.NamespaceContext;
import javax.xml.namespace.QName;
import javax.xml.stream.XMLEventWriter;
import javax.xml.stream.XMLStreamException;
import javax.xml.stream.events.StartElement;
import javax.xml.stream.events.XMLEvent;

import org.springframework.beans.BeanWrapper;
import org.springframework.beans.PropertyAccessorFactory;
import org.springframework.ws.jaxws.soapenc.internal.QNames;

/**
 * <p>A pattern which wraps other events with start / end element events.</p>
 *
 * @author Grzegorz Grzybek
 */
public class WrappedXmlEventsPattern implements XmlEventsPattern {

	private XmlEventsPattern wrappedPattern = null;

	private StartElement startElementEvent = null;

	private XMLEvent endElementEvent = null;

	/**
	 * @param wrappedPattern
	 * @param rootName
	 */
	public WrappedXmlEventsPattern(XmlEventsPattern wrappedPattern, QName rootName) {
		this.wrappedPattern = wrappedPattern;
		this.startElementEvent = XML_EVENTS_FACTORY.createStartElement(rootName, null, null);
		this.endElementEvent = XML_EVENTS_FACTORY.createEndElement(rootName, null);
	}

	/* (non-Javadoc)
	 * @see org.springframework.ws.jaxws.soapenc.internal.model.XmlEventsPattern#replay(org.springframework.beans.BeanWrapper, javax.xml.stream.XMLEventWriter, boolean)
	 */
	@Override
	public void replay(BeanWrapper beanWrapper, XMLEventWriter eventWriter, boolean repairingWriter) throws XMLStreamException {
		eventWriter.add(this.startElementEvent);
		QName qName = this.startElementEvent.getName();

		Object object = beanWrapper.getWrappedInstance();
		
		NamespaceContext nsc = eventWriter.getNamespaceContext();
		if (!repairingWriter && nsc.getPrefix(qName.getNamespaceURI()) == null) {
			eventWriter.add(XML_EVENTS_FACTORY.createNamespace(qName.getNamespaceURI()));
		}

		if (object instanceof JAXBElement) {
			if (((JAXBElement<?>) object).isNil()) {
				if (!repairingWriter && nsc.getPrefix(QNames.XSI_NIL.getNamespaceURI()) == null) {
					eventWriter.add(XML_EVENTS_FACTORY.createNamespace(QNames.XSI_NIL.getPrefix(), QNames.XSI_NIL.getNamespaceURI()));
				}
				eventWriter.add(XML_EVENTS_FACTORY.createAttribute(QNames.XSI_NIL, "true"));
			}
			// dereference marshalled value
			beanWrapper = PropertyAccessorFactory.forBeanPropertyAccess(((JAXBElement<?>) object).getValue());
		}

		if (this.wrappedPattern != null)
			this.wrappedPattern.replay(beanWrapper, eventWriter, repairingWriter);

		eventWriter.add(this.endElementEvent);
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
