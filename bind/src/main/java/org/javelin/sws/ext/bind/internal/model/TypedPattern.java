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

import org.javelin.sws.ext.bind.internal.MarshallingContext;
import org.javelin.sws.ext.bind.internal.UnmarshallingContext;

/**
 * <p>A pattern for (un)marshalling object of {@code T} Java Class from/to XML fragments which may be described by XSD type (simple
 * or complex)</p>
 * 
 * <p>This class <b>is not</b> a representation of XSD Type Definition Component (and does not have all its properties) - it only knows a XSD Type
 * <b>name</b> (marshalled sometimes as {@code xsi:type} attribute).</p>
 * 
 * @author Grzegorz Grzybek
 * @param <T> A Java type which relates to XSD Type Definition Component
 */
public abstract class TypedPattern<T> extends XmlEventsPattern {

	/** {@code target namespace} + {@code name} property of type definition schema component describing this pattern */
	private QName schemaType;

	/** Java type that matches XSD type */
	private Class<T> javaType;

	/**
	 * @param schemaType
	 * @param javaType
	 */
	protected TypedPattern(QName schemaType, Class<T> javaType) {
		this.schemaType = schemaType;
		this.javaType = javaType;
	}

	/**
	 * <p>Checks whether this pattern relates to a XML Schema Type derived from {@code xsd:anySimpleType} or not.</p>
	 * 
	 * @return {@code true} if it relates to simple type or {@code false} if it relates to complex type (with either simple or complex content)
	 */
	public abstract boolean isSimpleType();

	/* (non-Javadoc)
	 * @see org.javelin.sws.ext.bind.internal.model.XmlEventsPattern#replay(java.lang.Object, javax.xml.stream.XMLEventWriter, org.javelin.sws.ext.bind.internal.MarshallingContext)
	 */
	@Override
	public final void replay(Object object, XMLEventWriter eventWriter, MarshallingContext context) throws XMLStreamException {
		// justified @SuppressWarning, as we translate from untyped base method (which may take JAXBElements or objects of
		// @XMLRootElement annotated classes)
		@SuppressWarnings("unchecked")
		T value = (T) object;

		this.replayValue(value, eventWriter, context);
	}

	/**
	 * <p>Coverts a value which map to XSD Type Definition Component into a series of XML Events. It does <b>not</b> produce XML elements (just as
	 * XML element does not equal XSD type). This method is usually invoked while marshalling a Java object as a <b>part</b> of producing XML
	 * element.</p>
	 * 
	 * @param value
	 * @param eventWriter
	 * @param context
	 * @throws XMLStreamException
	 */
	public abstract void replayValue(T value, XMLEventWriter eventWriter, MarshallingContext context) throws XMLStreamException;

	/* (non-Javadoc)
	 * @see org.javelin.sws.ext.bind.internal.model.XmlEventsPattern#consume(javax.xml.stream.XMLEventReader, org.javelin.sws.ext.bind.internal.UnmarshallingContext)
	 */
	@Override
	public final Object consume(XMLEventReader eventReader, UnmarshallingContext context) throws XMLStreamException {
		return this.consumeValue(eventReader, context);
	}

	/**
	 * <p>Converts a series if XML events into Java value</p>
	 * 
	 * @param eventReader
	 * @param context
	 * @return
	 * @throws XMLStreamException
	 */
	public abstract T consumeValue(XMLEventReader eventReader, UnmarshallingContext context) throws XMLStreamException;

	/**
	 * Returns a XSD Type Definition Component of this pattern
	 * 
	 * @return the schemaType
	 */
	public QName getSchemaType() {
		return this.schemaType;
	}

	/**
	 * Returns a Java type of objects handled by this pattern
	 * 
	 * @return the javaType
	 */
	public Class<T> getJavaType() {
		return this.javaType;
	}

	/* (non-Javadoc)
	 * @see org.javelin.sws.ext.bind.internal.model.XmlEventsPattern#toString()
	 */
	@Override
	public String toString() {
		return "A \"" + this.getSchemaType() + "\" type pattern";
	}

}
