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

package org.springframework.ws.ext.bind.internal.stax;

import java.util.Iterator;
import java.util.List;

import javax.xml.stream.XMLEventReader;
import javax.xml.stream.XMLStreamException;
import javax.xml.stream.events.Attribute;
import javax.xml.stream.events.XMLEvent;

/**
 * <p>An {@link XMLEventReader}, which returns attributes in its {@link XMLEventReader#nextEvent()} and then delegates to the rest of
 * {@link XMLEvent XML events}.</p>
 *
 * @author Grzegorz Grzybek
 */
public class AttributesAwareXMLEventReader implements XMLEventReader {

	private XMLEventReader delegate;

	private Iterator<Attribute> attributeIterator;
	private Attribute currentAttribute = null;

	/**
	 * @param delegate
	 */
	public AttributesAwareXMLEventReader(XMLEventReader delegate, List<Attribute> attributes) {
		this.delegate = delegate;
		this.attributeIterator = attributes.iterator();
		if (this.attributeIterator.hasNext()) {
			this.currentAttribute = this.attributeIterator.next();
		}
	}

	/**
	 * @return
	 * @throws XMLStreamException
	 * @see javax.xml.stream.XMLEventReader#nextEvent()
	 */
	public XMLEvent nextEvent() throws XMLStreamException {
		XMLEvent next = this.currentAttribute;
		if (this.currentAttribute != null) {
			if (this.attributeIterator.hasNext()) {
				this.currentAttribute = this.attributeIterator.next();
			} else {
				this.currentAttribute = null;
			}
		} else {
			next = this.delegate.nextEvent();
		}
		return next;
	}

	/**
	 * @return
	 * @see javax.xml.stream.XMLEventReader#hasNext()
	 */
	public boolean hasNext() {
		return this.delegate.hasNext();
	}

	/**
	 * @return
	 * @throws XMLStreamException
	 * @see javax.xml.stream.XMLEventReader#peek()
	 */
	public XMLEvent peek() throws XMLStreamException {
		return this.currentAttribute != null ? this.currentAttribute : this.delegate.peek();
	}

	/**
	 * @return
	 * @see java.util.Iterator#next()
	 */
	public Object next() {
		return this.delegate.next();
	}

	/**
	 * @return
	 * @throws XMLStreamException
	 * @see javax.xml.stream.XMLEventReader#getElementText()
	 */
	public String getElementText() throws XMLStreamException {
		return this.delegate.getElementText();
	}

	/**
	 * 
	 * @see java.util.Iterator#remove()
	 */
	public void remove() {
		this.delegate.remove();
	}

	/**
	 * @return
	 * @throws XMLStreamException
	 * @see javax.xml.stream.XMLEventReader#nextTag()
	 */
	public XMLEvent nextTag() throws XMLStreamException {
		return this.delegate.nextTag();
	}

	/**
	 * @param name
	 * @return
	 * @throws IllegalArgumentException
	 * @see javax.xml.stream.XMLEventReader#getProperty(java.lang.String)
	 */
	public Object getProperty(String name) throws IllegalArgumentException {
		return this.delegate.getProperty(name);
	}

	/**
	 * @throws XMLStreamException
	 * @see javax.xml.stream.XMLEventReader#close()
	 */
	public void close() throws XMLStreamException {
		this.delegate.close();
	}

}
