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
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.namespace.NamespaceContext;
import javax.xml.namespace.QName;
import javax.xml.stream.XMLEventWriter;
import javax.xml.stream.XMLStreamException;

import org.springframework.ws.jaxws.soapenc.internal.QNames;

/**
 * <p>A pattern which wraps other events with start / end element events. This class knows how to change the wrapping element on
 * the basis of nested pattern. The main case is to emit {@code xsi:nil="true"} when the marshalled object is {@code null}.</p>
 * 
 * <p>Wrapping is the way to decide what XML element to produce while marshalling object which is not JAXBElement or has
 * no {@link XmlRootElement} annotation. The object replayed by nested pattern is always <i>dereferenced</i> from {@link JAXBElement}.</p>
 * 
 * DESIGNFLAW: {@link ElementPattern} is also Wrapped pattern so some code is duplicated here but we can't derive one from another, because {@link ElementPattern} is also {@link PropertyPattern}
 * 
 * @author Grzegorz Grzybek
 */
public class WrappedXmlEventsPattern implements XmlEventsPattern {

	private XmlEventsPattern wrappedPattern = null;

	private QName elementName;

	/**
	 * @param wrappedPattern
	 * @param rootName
	 */
	public WrappedXmlEventsPattern(XmlEventsPattern wrappedPattern, QName rootName) {
		this.wrappedPattern = wrappedPattern;
		this.elementName = rootName;
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

		this.wrappedPattern.replay(object, eventWriter, repairingWriter);

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
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString() {
		return this.elementName + " wrapping " + this.wrappedPattern.toString();
	}

}
