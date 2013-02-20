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

package org.springframework.ws.jaxws.soapenc.internal.model;

import javax.xml.bind.MarshalException;
import javax.xml.stream.XMLEventFactory;
import javax.xml.stream.XMLEventWriter;
import javax.xml.stream.XMLStreamException;
import javax.xml.stream.events.XMLEvent;

/**
 * <p>In Object-XML mapping, each object may be converted to a series of {@link XMLEvent XML events}. Some of these events (or of these
 * events' properties) are independent of the objects value (e.g., element QNames or sequence of child elements), some (usually attribute
 * values and text nodes) have value based on the marshalled objects.</p>
 * 
 * <p>During unmarshalling operation, a series of {@link XMLEvent XML events} controls Java objects creation. It is necessary (unlike in
 * marshalling) to maintain a state which determines the currently created/unmarshalled object.</p>
 * 
 * <p>This interface represent a mapping of Java Class to a series of (probably parameterized) {@link XMLEvent XML events}. Marshalling a
 * Java objects may be implemented as a replay of a collection of events.</p>
 * 
 * <p>For unmarshalling, an XmlEventsPattern may be used to determine a <i>loader</i> used to convert events to a series of objects
 * creations / setters invocations.</p>
 * 
 * <p>So XML event may be a two level <i>template</i>:<ul>
 * <li>for marshalling Java objects into XML instances (infosets) - a direct mapping to {@link XMLEvent} objects</li>
 * <li>for creating XML Schema components at runtime to generate XML Schema documents</li>
 * </ul></p>
 *
 * @author Grzegorz Grzybek
 */
public interface XmlEventsPattern {

	public static final XMLEventFactory XML_EVENTS_FACTORY = XMLEventFactory.newInstance();

	/**
	 * <p>Converts Java object into series of {@link XMLEvent XML events}.</p>
	 * 
	 * @param object
	 * @param eventWriter
	 * @param repairingWriter
	 * @throws MarshalException
	 */
	public void replay(Object object, XMLEventWriter eventWriter, boolean repairingWriter) throws XMLStreamException;

	/**
	 * <p>Checks whether this pattern may be used to produce XML Element.</p>
	 * <p>Simple types, comments, PIs, ... are not elements</p>
	 * 
	 * @return
	 */
	public boolean isElement();

	/**
	 * <p>Checks whether this pattern relates to a XML Schema Type derived from xsd:anySimpleType and which outputs only objects
	 * convertible to {@link String}</p>
	 * 
	 * @return
	 */
	public boolean isSimpleType();

}
