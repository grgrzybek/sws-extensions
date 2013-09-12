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

import java.text.ParseException;
import java.util.Locale;

import javax.xml.bind.annotation.XmlValue;
import javax.xml.namespace.QName;
import javax.xml.stream.XMLEventReader;
import javax.xml.stream.XMLEventWriter;
import javax.xml.stream.XMLStreamException;
import javax.xml.stream.events.XMLEvent;

import org.javelin.sws.ext.bind.internal.MarshallingContext;
import org.javelin.sws.ext.bind.internal.UnmarshallingContext;
import org.springframework.format.Formatter;

/**
 * <p>A pattern for (un)marshalling object of {@code T} Java Class from/to XML fragments which may be described by XSD simple type </p>
 * 
 * <p>This class is used to (un)marshal object which are convertible from/to Strings using defined {@link Formatter}.</p>
 * 
 * <p>XSD simple type may be used to constrain:<ul>
 * <li>character information item children of element information items</li>
 * <li>values of attribute information items</li>
 * </ul></p>
 * 
 * <p>Derived classes decide what the real XML pattern of Java values of simple XSD type is:<ul>
 * <li>simple content of elements</li>
 * <li>attribute</li>
 * </ul></p>
 *
 * @author Grzegorz Grzybek
 * @param <T>
 */
public abstract class SimpleTypePattern<T> extends TypedPattern<T> {

	private Formatter<T> formatter;

	/**
	 * @param schemaType
	 * @param javaType
	 */
	protected SimpleTypePattern(QName schemaType, Class<T> javaType) {
		super(schemaType, javaType);
	}

	/* (non-Javadoc)
	 * @see org.javelin.sws.ext.bind.internal.model.TypedPattern#replayValue(java.lang.Object, javax.xml.stream.XMLEventWriter, org.javelin.sws.ext.bind.internal.MarshallingContext)
	 */
	@Override
	public void replayValue(T value, XMLEventWriter eventWriter, MarshallingContext context) throws XMLStreamException {
		if (value != null) {
			String sValue = this.formatter == null ? (String)value : this.formatter.print(value, Locale.getDefault());
			this.replayNonNullString(sValue, eventWriter, context);
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
	 * @see org.javelin.sws.ext.bind.internal.model.TypedPattern#consumeValue(javax.xml.stream.XMLEventReader, org.javelin.sws.ext.bind.internal.UnmarshallingContext)
	 */
	@Override
	public T consumeValue(XMLEventReader eventReader, UnmarshallingContext context) throws XMLStreamException {
		String value = this.consumeNonNullString(eventReader, context);
		if (this.formatter == null) {
			@SuppressWarnings("unchecked")
			T v = (T)value;
			return v;
		}
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
	 * @see org.javelin.sws.ext.bind.internal.model.XmlEventsPattern#isSimpleType()
	 */
	@Override
	public final boolean isSimpleType() {
		/* always */ return true;
	}

	/**
	 * @param formatter the formatter to set
	 */
	public void setFormatter(Formatter<T> formatter) {
		this.formatter = formatter;
	}

	/**
	 * {@link SimpleContentPattern} allows to get it's formatter to <i>pull</i> it up to the containing pattern which uses {@link XmlValue}
	 * 
	 * @return
	 */
	public Formatter<T> getFormatter() {
		return this.formatter;
	}

	/* (non-Javadoc)
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString() {
		return "A \"" + this.getSchemaType() + "\" simple type pattern";
	}

}
