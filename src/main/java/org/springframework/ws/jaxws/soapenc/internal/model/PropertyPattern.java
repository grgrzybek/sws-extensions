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
import javax.xml.stream.XMLEventWriter;
import javax.xml.stream.XMLStreamException;
import javax.xml.stream.events.XMLEvent;

import org.springframework.beans.BeanWrapper;
import org.springframework.beans.PropertyAccessor;
import org.springframework.beans.PropertyAccessorFactory;

/**
 * <p>A pattern of {@link XMLEvent XML events} which uses single property from the marshalled object.</p>
 *
 * @author Grzegorz Grzybek
 */
public abstract class PropertyPattern implements XmlEventsPattern {

	// DESIGNFLAW: maybe we should use dedicated enum instead of javax.xml.bind.annotation.XmlAccessType
	// DESIGNFLAW: accessType seems to be needed only by ComplexContentPattern
	private XmlAccessType accessType = null;

	private String propertyName;

	/**
	 * @param accessType
	 * @param propertyName
	 */
	public PropertyPattern(XmlAccessType accessType, String propertyName) {
		this.accessType = accessType;
		this.propertyName = propertyName;
	}

	/**
	 * @return the accessType
	 */
	public XmlAccessType getAccessType() {
		return this.accessType;
	}

	/**
	 * @param propertyAccessor
	 * @param eventWriter
	 * @param repairingWriter
	 */
	public void replayProperty(PropertyAccessor propertyAccessor, XMLEventWriter eventWriter, boolean repairingWriter) throws XMLStreamException {
		// dereference a property, wrap it in BeanWrapper and go on
		BeanWrapper nestedWrapper = PropertyAccessorFactory.forBeanPropertyAccess(propertyAccessor.getPropertyValue(this.propertyName));
		this.replay(nestedWrapper, eventWriter, repairingWriter);
	}

}
