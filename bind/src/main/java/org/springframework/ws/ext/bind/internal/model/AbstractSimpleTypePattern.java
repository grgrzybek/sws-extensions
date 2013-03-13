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

import org.springframework.core.convert.ConversionService;
import org.springframework.ws.ext.bind.internal.MarshallingContext;

/**
 * <p>{@link AbstractSimpleTypePattern} uses {@link ConversionService} to deal only with {@link String} values.</p>
 * <p>It is a representation of XML Schema (see: "2.2.1.2 Simple Type Definition") {@code xs:anySimpleType}.</p>
 *
 * @author Grzegorz Grzybek
 */
public abstract class AbstractSimpleTypePattern extends XmlEventsPattern {

	private ConversionService conversionService;

	/**
	 * @param schemaType
	 * @param javaType
	 */
	protected AbstractSimpleTypePattern(QName schemaType, Class<?> javaType) {
		super(schemaType, javaType);
	}

	/* (non-Javadoc)
	 * @see org.springframework.ws.ext.bind.internal.model.XmlEventsPattern#replay(java.lang.Object, javax.xml.stream.XMLEventWriter, org.springframework.ws.ext.bind.internal.MarshallingContext)
	 */
	@Override
	public void replay(Object object, XMLEventWriter eventWriter, MarshallingContext context) throws XMLStreamException {
		if (object != null)
			this.replayNonNullString(this.conversionService.convert(object, String.class), eventWriter);
	}

	/**
	 * Derived classes create proper {@link XMLEvent} and marshal single {@link String} value
	 * 
	 * @param value
	 * @param eventWriter
	 */
	protected abstract void replayNonNullString(String value, XMLEventWriter eventWriter) throws XMLStreamException;

	/* (non-Javadoc)
	 * @see org.springframework.ws.ext.bind.internal.model.XmlEventsPattern#consume(javax.xml.stream.XMLEventReader)
	 */
	@Override
	public Object consume(XMLEventReader eventReader) throws XMLStreamException {
		return null;
	}

	/* (non-Javadoc)
	 * @see org.springframework.ws.ext.bind.internal.model.XmlEventsPattern#isSimpleType()
	 */
	@Override
	public boolean isSimpleType() {
		return true;
	}

	/**
	 * {@link AbstractSimpleTypePattern} uses {@link ConversionService} to marshal only {@link String} values
	 * 
	 * @param conversionService
	 */
	public void setConversionService(ConversionService conversionService) {
		this.conversionService = conversionService;
	}

	/* (non-Javadoc)
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString() {
		return "Simple Type";
	}

}
