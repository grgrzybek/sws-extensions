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

import javax.xml.XMLConstants;
import javax.xml.namespace.QName;
import javax.xml.stream.XMLEventReader;
import javax.xml.stream.XMLEventWriter;
import javax.xml.stream.XMLStreamException;
import javax.xml.stream.events.Attribute;
import javax.xml.stream.events.Namespace;
import javax.xml.stream.events.XMLEvent;

import org.javelin.sws.ext.bind.internal.MarshallingContext;
import org.javelin.sws.ext.bind.internal.UnmarshallingContext;

/**
 * <p>AttributePattern generates ATTRIBUTE {@link XMLEvent}</p>
 * 
 * @author Grzegorz Grzybek
 */
public class AttributePattern<T> extends SimpleTypePattern<T> {

	private QName attributeName;

	/**
	 * @param schemaType
	 * @param javaType
	 */
	private AttributePattern(QName attributeName, QName schemaType, Class<T> javaType) {
		super(schemaType, javaType);
		this.attributeName = attributeName;
	}
	
	/**
	 * <p>Generic-safe constructor.</p>
	 * 
	 * <p>The signature knows that this class should delegate to, not derive from {@link SimpleTypePattern}.</p>
	 * 
	 * @param schemaType
	 * @param javaType
	 */
	public static <T, P> AttributePattern<T> newAttributePattern(QName attributeName, SimpleTypePattern<T> simpleTypePattern) {
		AttributePattern<T> ap = new AttributePattern<T>(attributeName, simpleTypePattern.getSchemaType(), simpleTypePattern.getJavaType());
		ap.setFormatter(simpleTypePattern.getFormatter());
		return ap;
	}

	/* (non-Javadoc)
	 * @see org.javelin.sws.ext.bind.internal.model.SimpleTypePattern#replayNonNullString(java.lang.String, javax.xml.stream.XMLEventWriter, org.javelin.sws.ext.bind.internal.MarshallingContext)
	 */
	@Override
	protected void replayNonNullString(String value, XMLEventWriter eventWriter, MarshallingContext context) throws XMLStreamException {
		if (!XMLConstants.NULL_NS_URI.equals(this.attributeName.getNamespaceURI())) {
			// we MUST create second namespace declaration if the current one has default ("") prefix, otherwise the attribute will have absent
			// namespace!
			// see: http://www.w3.org/TR/xml-names11/#defaulting - "The namespace name for an unprefixed attribute name always has no value."
			String prefix = this.safeRegisterNamespace(context, eventWriter, this.attributeName);
			if (XMLConstants.DEFAULT_NS_PREFIX.equals(prefix)) {
				Namespace namespace = this.safePrepareNamespace(context, eventWriter, new QName(this.attributeName.getNamespaceURI(), this.attributeName.getLocalPart(), context.newPrefix()), NamespaceRegistration.IF_DEFAULT_PREFIX);
				prefix = namespace.getPrefix();
			}
			eventWriter.add(XML_EVENTS_FACTORY.createAttribute(prefix, this.attributeName.getNamespaceURI(), this.attributeName.getLocalPart(), value));
		} else {
			// attribute is unqalified and has no namespace
			eventWriter.add(XML_EVENTS_FACTORY.createAttribute(this.attributeName.getLocalPart(), value));
		}
	}

	/* (non-Javadoc)
	 * @see org.javelin.sws.ext.bind.internal.model.SimpleTypePattern#consumeNonNullString(javax.xml.stream.XMLEventReader, org.javelin.sws.ext.bind.internal.UnmarshallingContext)
	 */
	@Override
	protected String consumeNonNullString(XMLEventReader eventReader, UnmarshallingContext context) throws XMLStreamException {
		return ((Attribute)eventReader.nextEvent()).getValue();
	}

	/* (non-Javadoc)
	 * @see org.springframework.ws.jaxws.soapenc.internal.model.PropertyPattern#toString()
	 */
	@Override
	public String toString() {
		return super.toString() + " marshalled as \"" + this.attributeName + "\" ATTRIBUTE";
	}

	/**
	 * @return the attributeName
	 */
	public QName getAttributeName() {
		return this.attributeName;
	}

}
