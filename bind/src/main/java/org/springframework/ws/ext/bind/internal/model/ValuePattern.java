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

import javax.xml.namespace.QName;
import javax.xml.stream.XMLEventWriter;
import javax.xml.stream.XMLStreamException;
import javax.xml.stream.events.XMLEvent;

/**
 * <p>ValuePattern generates CHARACTERS {@link XMLEvent}</p>
 *
 * @author Grzegorz Grzybek
 */
public class ValuePattern extends AbstractSimpleTypePattern {

	public static final ValuePattern INSTANCE = new ValuePattern(null, null);

	/**
	 * @param schemaType
	 * @param javaType
	 */
	public ValuePattern(QName schemaType, Class<?> javaType) {
		super(schemaType, javaType);
	}

	/* (non-Javadoc)
	 * @see org.springframework.ws.ext.bind.internal.model.SimpleTypePattern#replayNonNullString(java.lang.String, javax.xml.stream.XMLEventWriter)
	 */
	@Override
	protected void replayNonNullString(String value, XMLEventWriter eventWriter) throws XMLStreamException {
		eventWriter.add(XML_EVENTS_FACTORY.createCharacters(value));
	}

	/* (non-Javadoc)
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString() {
		return super.toString() + " marshalled as CHARACTERS event";
	}

}
