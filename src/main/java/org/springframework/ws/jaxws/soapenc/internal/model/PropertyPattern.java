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

import javax.xml.stream.XMLEventWriter;
import javax.xml.stream.XMLStreamException;
import javax.xml.stream.events.XMLEvent;

import org.springframework.beans.PropertyAccessor;

/**
 * <p>A pattern of {@link XMLEvent XML events} which uses single property from the marshalled object.</p>
 * <p>This pattern knows what property to extract from the {@link PropertyAccessor} configured earlier in the marshalling process.</p>
 *
 * @author Grzegorz Grzybek
 */
public abstract class PropertyPattern implements XmlEventsPattern {

	private String propertyName;

	/** Is it field ({@code true}) or getter ({@code false} property? */
	private boolean directProperty;

	/**
	 * @param accessType
	 * @param propertyName
	 */
	public PropertyPattern(boolean directProperty, String propertyName) {
		this.directProperty = directProperty;
		this.propertyName = propertyName;
	}

	/**
	 * @return the directProperty
	 */
	public boolean isDirectProperty() {
		return this.directProperty;
	}

	/**
	 * @return the propertyName
	 */
	public String getPropertyName() {
		return this.propertyName;
	}

	/**
	 * @param propertyAccessor
	 * @param eventWriter
	 * @param repairingWriter
	 */
	public void replayProperty(PropertyAccessor propertyAccessor, XMLEventWriter eventWriter, boolean repairingWriter) throws XMLStreamException {
		this.replay(propertyAccessor.getPropertyValue(this.propertyName), eventWriter, repairingWriter);
	}

	/* (non-Javadoc)
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString() {
		return "Property pattern for \"" + this.propertyName + "\" " + (this.directProperty ? "field" : "bean property");
	}

}
