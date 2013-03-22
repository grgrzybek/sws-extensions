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

import org.springframework.ws.ext.bind.internal.MarshallingContext;
import org.springframework.ws.ext.bind.internal.UnmarshallingContext;

/**
 * <p>An {@link XmlEventsPattern} acting as a temporary mapping of class to a series of XML events. Needed when two classes
 * have properties of one another's class.</p>
 * <p>After the final determination of mapped pattern it will be injected into this pattern. MultiRef Encodings (more likely to have
 * interdependent properties) will <b>not</b> result in replaying this pattern, because multiRefs will be used since first occurence.</p>
 *
 * @author Grzegorz Grzybek
 */
public class TemporaryXmlEventsPattern extends XmlEventsPattern {

	private ContentModelPattern realMapping;

	/**
	 * @param schemaType
	 * @param javaType
	 */
	public TemporaryXmlEventsPattern(QName schemaType, Class<?> javaType) {
		super(schemaType, javaType);
	}

	/* (non-Javadoc)
	 * @see org.springframework.ws.ext.bind.internal.model.XmlEventsPattern#replay(java.lang.Object, javax.xml.stream.XMLEventWriter, org.springframework.ws.ext.bind.internal.MarshallingContext)
	 */
	@Override
	public void replay(Object object, XMLEventWriter eventWriter, MarshallingContext context) throws XMLStreamException {
		this.realMapping.replay(object, eventWriter, context);
	}

	/* (non-Javadoc)
	 * @see org.springframework.ws.ext.bind.internal.model.XmlEventsPattern#consume(javax.xml.stream.XMLEventReader, org.springframework.ws.ext.bind.internal.UnmarshallingContext)
	 */
	@Override
	public Object consume(XMLEventReader eventReader, UnmarshallingContext context) throws XMLStreamException {
		return this.realMapping.consume(eventReader, context);
	}

	/* (non-Javadoc)
	 * @see org.springframework.ws.ext.bind.internal.model.XmlEventsPattern#isSimpleType()
	 */
	@Override
	public boolean isSimpleType() {
		return this.realMapping.isSimpleType();
	}

	/**
	 * @param mapping
	 */
	public void setRealPattern(ContentModelPattern mapping) {
		this.realMapping = mapping;
	}

}
