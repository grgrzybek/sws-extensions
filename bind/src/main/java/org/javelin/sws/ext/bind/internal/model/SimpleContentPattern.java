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

package org.javelin.sws.ext.bind.internal.model;

import javax.xml.namespace.QName;
import javax.xml.stream.XMLEventReader;
import javax.xml.stream.XMLEventWriter;
import javax.xml.stream.XMLStreamException;
import javax.xml.stream.events.XMLEvent;

import org.javelin.sws.ext.bind.internal.MarshallingContext;
import org.javelin.sws.ext.bind.internal.UnmarshallingContext;

/**
 * <p>SimpleContentPattern generates CHARACTERS {@link XMLEvent}</p>
 * 
 * @author Grzegorz Grzybek
 */
public class SimpleContentPattern<T> extends SimpleTypePattern<T> {

	/**
	 * @param schemaType
	 * @param javaType
	 */
	private SimpleContentPattern(QName schemaType, Class<T> javaType) {
		super(schemaType, javaType);
	}

	/**
	 * <p>Generic-safe constructor.</p>
	 * 
	 * @param schemaType
	 * @param javaType
	 */
	public static <T> SimpleContentPattern<T> newValuePattern(QName schemaType, Class<T> javaType) {
		return new SimpleContentPattern<T>(schemaType, javaType);
	}

	/* (non-Javadoc)
	 * @see org.javelin.sws.ext.bind.internal.model.SimpleTypePattern#replayNonNullString(java.lang.String, javax.xml.stream.XMLEventWriter, org.javelin.sws.ext.bind.internal.MarshallingContext)
	 */
	@Override
	protected void replayNonNullString(String value, XMLEventWriter eventWriter, MarshallingContext context) throws XMLStreamException {
		eventWriter.add(XML_EVENTS_FACTORY.createCharacters(value));
	}

	/* (non-Javadoc)
	 * @see org.javelin.sws.ext.bind.internal.model.SimpleTypePattern#consumeNonNullString(javax.xml.stream.XMLEventReader, org.javelin.sws.ext.bind.internal.UnmarshallingContext)
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
		return super.toString() + " marshalled as CHARACTERS";
	}

}
