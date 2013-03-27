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
import javax.xml.stream.XMLEventReader;
import javax.xml.stream.XMLEventWriter;
import javax.xml.stream.XMLStreamException;
import javax.xml.stream.events.XMLEvent;

import org.springframework.ws.ext.bind.internal.MarshallingContext;
import org.springframework.ws.ext.bind.internal.UnmarshallingContext;

/**
 * <p>ValuePattern generates CHARACTERS {@link XMLEvent}</p>
 *
 * @author Grzegorz Grzybek
 */
public class ValuePattern<T> extends AbstractSimpleTypePattern<T> {

	public static final ValuePattern<Object> INSTANCE = newValuePattern(null, Object.class);
	
	/**
	 * @param schemaType
	 * @param javaType
	 */
	private ValuePattern(QName schemaType, Class<T> javaType) {
		super(schemaType, javaType);
	}

	/**
	 * @param schemaType
	 * @param javaType
	 */
	public static <T> ValuePattern<T> newValuePattern(QName schemaType, Class<T> javaType) {
		return new ValuePattern<T>(schemaType, javaType);
	}

	/* (non-Javadoc)
	 * @see org.springframework.ws.ext.bind.internal.model.AbstractSimpleTypePattern#replayNonNullString(java.lang.String, javax.xml.stream.XMLEventWriter, org.springframework.ws.ext.bind.internal.MarshallingContext)
	 */
	@Override
	protected void replayNonNullString(String value, XMLEventWriter eventWriter, MarshallingContext context) throws XMLStreamException {
		eventWriter.add(XML_EVENTS_FACTORY.createCharacters(value));
	}

	/* (non-Javadoc)
	 * @see org.springframework.ws.ext.bind.internal.model.AbstractSimpleTypePattern#consumeNonNullString(javax.xml.stream.XMLEventReader, org.springframework.ws.ext.bind.internal.UnmarshallingContext)
	 */
	@Override
	protected String consumeNonNullString(XMLEventReader eventReader, UnmarshallingContext context) throws XMLStreamException {
		return eventReader.nextEvent().asCharacters().getData();
	}

	/* (non-Javadoc)
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString() {
		return super.toString() + " marshalled as CHARACTERS event";
	}

}
