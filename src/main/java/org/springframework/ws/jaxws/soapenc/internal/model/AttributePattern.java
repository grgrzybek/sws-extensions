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

import javax.xml.namespace.QName;
import javax.xml.stream.XMLEventWriter;
import javax.xml.stream.XMLStreamException;

import org.springframework.core.convert.ConversionService;

/**
 * <p></p>
 *
 * @author Grzegorz Grzybek
 */
public class AttributePattern extends PropertyPattern implements SimpleTypePattern {

	private QName attributeName;
	private ConversionService conversionService;

	/**
	 * @param directAccess
	 * @param propertyName
	 */
	public AttributePattern(QName attributeName, boolean directProperty, String propertyName) {
		super(directProperty, propertyName);
		this.attributeName = attributeName;
	}

	/* (non-Javadoc)
	 * @see org.springframework.ws.jaxws.soapenc.internal.model.SimpleTypePattern#setConversionService(org.springframework.core.convert.ConversionService)
	 */
	@Override
	public void setConversionService(ConversionService conversionService) {
		this.conversionService = conversionService;
	}

	/* (non-Javadoc)
	 * @see org.springframework.ws.jaxws.soapenc.internal.model.XmlEventsPattern#replay(java.lang.Object, javax.xml.stream.XMLEventWriter, boolean)
	 */
	@Override
	public void replay(Object object, XMLEventWriter eventWriter, boolean repairingWriter) throws XMLStreamException {
		if (object != null) {
			eventWriter.add(XML_EVENTS_FACTORY.createAttribute(this.attributeName, this.conversionService.convert(object, String.class)));
		}
	}

	/* (non-Javadoc)
	 * @see org.springframework.ws.jaxws.soapenc.internal.model.XmlEventsPattern#isElement()
	 */
	@Override
	public boolean isElement() {
		return false;
	}

	/* (non-Javadoc)
	 * @see org.springframework.ws.jaxws.soapenc.internal.model.XmlEventsPattern#isSimpleType()
	 */
	@Override
	public boolean isSimpleType() {
		return true;
	}

	/* (non-Javadoc)
	 * @see org.springframework.ws.jaxws.soapenc.internal.model.PropertyPattern#toString()
	 */
	@Override
	public String toString() {
		return super.toString() + " and " + this.attributeName + " ATTRIBUTE";
	}

}
