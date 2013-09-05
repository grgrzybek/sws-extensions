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

import javax.xml.stream.XMLEventReader;
import javax.xml.stream.XMLEventWriter;
import javax.xml.stream.XMLStreamException;

import org.javelin.sws.ext.bind.internal.MarshallingContext;
import org.javelin.sws.ext.bind.internal.UnmarshallingContext;

/**
 * <p>A Comment XML pattern is to validate the proper design of {@link XmlEventsPattern} class hierarchy. It has no type.</p>
 *
 * @author Grzegorz Grzybek
 */
public class CommentPattern extends XmlEventsPattern {

	/* (non-Javadoc)
	 * @see org.javelin.sws.ext.bind.internal.model.XmlEventsPattern#replay(java.lang.Object, javax.xml.stream.XMLEventWriter, org.javelin.sws.ext.bind.internal.MarshallingContext)
	 */
	@Override
	public void replay(Object object, XMLEventWriter eventWriter, MarshallingContext context) throws XMLStreamException {
		if (object != null)
			eventWriter.add(XML_EVENTS_FACTORY.createComment(object.toString()));
	}

	/* (non-Javadoc)
	 * @see org.javelin.sws.ext.bind.internal.model.XmlEventsPattern#consume(javax.xml.stream.XMLEventReader, org.javelin.sws.ext.bind.internal.UnmarshallingContext)
	 */
	@Override
	public Object consume(XMLEventReader eventReader, UnmarshallingContext context) throws XMLStreamException {
		return null;
	}

	/* (non-Javadoc)
	 * @see org.javelin.sws.ext.bind.internal.model.XmlEventsPattern#toString()
	 */
	@Override
	public String toString() {
		return "An XML comment pattern";
	}
}
