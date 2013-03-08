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

/**
 * <p>AttributePattern generates ATTRIBUTE {@link XMLEvent}</p>
 *
 * @author Grzegorz Grzybek
 */
public class AttributePattern extends AbstractSimpleTypePattern {

	private QName attributeName;

	/**
	 * @param directAccess
	 * @param propertyName
	 */
	public AttributePattern(QName attributeName) {
		this.attributeName = attributeName;
	}

	/* (non-Javadoc)
	 * @see org.springframework.ws.ext.bind.internal.model.SimpleTypePattern#replayString(java.lang.String, javax.xml.stream.XMLEventWriter)
	 */
	@Override
	protected void replayNonNullString(String value, XMLEventWriter eventWriter) throws XMLStreamException {
		eventWriter.add(XML_EVENTS_FACTORY.createAttribute(this.attributeName, value));
	}

	/* (non-Javadoc)
	 * @see org.springframework.ws.jaxws.soapenc.internal.model.PropertyPattern#toString()
	 */
	@Override
	public String toString() {
		return super.toString() + " marshalled as \"" + this.attributeName + "\" ATTRIBUTE event";
	}

}
