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

import java.text.ParseException;
import java.util.Locale;

import javax.xml.namespace.QName;
import javax.xml.stream.XMLEventReader;
import javax.xml.stream.XMLEventWriter;
import javax.xml.stream.XMLStreamException;
import javax.xml.stream.events.XMLEvent;

import org.springframework.core.convert.ConversionService;
import org.springframework.format.Formatter;
import org.springframework.ws.ext.bind.internal.MarshallingContext;
import org.springframework.ws.ext.bind.internal.UnmarshallingContext;

/**
 * <p>{@link AbstractSimpleTypePattern} uses {@link ConversionService} to deal only with {@link String} values.</p>
 * <p>It is a representation of XML Schema (see: "2.2.1.2 Simple Type Definition") {@code xs:anySimpleType}.</p>
 *
 * @author Grzegorz Grzybek
 */
public abstract class AbstractSimpleTypePattern<T> extends XmlEventsPattern<T> {

//	private ConversionService conversionService;
	private Formatter<T> formatter;

	/**
	 * @param schemaType
	 * @param javaType
	 */
	protected AbstractSimpleTypePattern(QName schemaType, Class<T> javaType) {
		super(schemaType, javaType);
	}

	/* (non-Javadoc)
	 * @see org.springframework.ws.ext.bind.internal.model.XmlEventsPattern#replay(java.lang.Object, javax.xml.stream.XMLEventWriter, org.springframework.ws.ext.bind.internal.MarshallingContext)
	 */
	@Override
	public void replay(Object object, XMLEventWriter eventWriter, MarshallingContext context) throws XMLStreamException {
		if (object != null) {
			@SuppressWarnings("unchecked")
			String value = this.formatter == null ? object.toString() : this.formatter.print((T) object, Locale.getDefault());
			this.replayNonNullString(value, eventWriter, context);
		}
	}

	/**
	 * Derived classes create proper {@link XMLEvent} and marshal single {@link String} value
	 * 
	 * @param value
	 * @param eventWriter
	 */
	protected abstract void replayNonNullString(String value, XMLEventWriter eventWriter, MarshallingContext context) throws XMLStreamException;

	/* (non-Javadoc)
	 * @see org.springframework.ws.ext.bind.internal.model.XmlEventsPattern#consume(javax.xml.stream.XMLEventReader, org.springframework.ws.ext.bind.internal.UnmarshallingContext)
	 */
	@SuppressWarnings("unchecked")
	@Override
	public T consume(XMLEventReader eventReader, UnmarshallingContext context) throws XMLStreamException {
		String value = this.consumeNonNullString(eventReader, context);
		if (this.formatter == null)
			return (T) value;
		try {
			return this.formatter.parse(value, Locale.getDefault());
		}
		catch (ParseException e) {
			throw new XMLStreamException(e.getMessage(), e);
		}
	}

	/**
	 * @param eventReader
	 * @param context
	 * @return
	 */
	protected abstract String consumeNonNullString(XMLEventReader eventReader, UnmarshallingContext context) throws XMLStreamException;

	/* (non-Javadoc)
	 * @see org.springframework.ws.ext.bind.internal.model.XmlEventsPattern#isSimpleType()
	 */
	@Override
	public boolean isSimpleType() {
		return true;
	}

//	/**
//	 * {@link AbstractSimpleTypePattern} uses {@link ConversionService} to marshal only {@link String} values
//	 * 
//	 * @param conversionService
//	 */
//	public void setConversionService(ConversionService conversionService) {
//		this.conversionService = conversionService;
//	}

	/**
	 * @param formatter the formatter to set
	 */
	public void setFormatter(Formatter<T> formatter) {
		this.formatter = formatter;
	}

	/* (non-Javadoc)
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString() {
		return "Simple Type";
	}

}
