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
import javax.xml.stream.events.Characters;

import org.springframework.beans.BeanWrapper;
import org.springframework.core.convert.converter.Converter;

/**
 * <p>Simple, non-nillable type always marshalls as single {@link Characters} event. Value to marshal is obtained using proper {@link Converter}.</p>
 *
 * @author Grzegorz Grzybek
 */
public class ValuePattern implements XmlEventsPattern {

	@SuppressWarnings("unused")
	private QName schemaType;

	/**
	 * 
	 */
	public ValuePattern() {
	}

	/**
	 * @param schemaType
	 */
	public ValuePattern(QName schemaType) {
		this.schemaType = schemaType;
	}

	/**
	 * Null objects will be handled by wrapping pattern - e.g., by emitting xsi:nil="true" attribute
	 * 
	 * @see org.springframework.ws.jaxws.soapenc.internal.model.XmlEventsPattern#replay(org.springframework.beans.BeanWrapper, javax.xml.stream.XMLEventWriter, boolean)
	 */
	@Override
	public void replay(BeanWrapper beanWrapper, XMLEventWriter eventWriter, boolean repairingWriter) throws XMLStreamException {
		if (beanWrapper.getWrappedInstance() != null) {
			String value = beanWrapper.convertIfNecessary(beanWrapper.getWrappedInstance(), String.class);
			eventWriter.add(XML_EVENTS_FACTORY.createCharacters(value));
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
	public boolean isSimpleValue() {
		return true;
	}

}
