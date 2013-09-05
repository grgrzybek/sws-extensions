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

import java.util.Iterator;

import javax.xml.XMLConstants;
import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBElement;
import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlValue;
import javax.xml.namespace.NamespaceContext;
import javax.xml.namespace.QName;
import javax.xml.stream.XMLEventFactory;
import javax.xml.stream.XMLEventReader;
import javax.xml.stream.XMLEventWriter;
import javax.xml.stream.XMLOutputFactory;
import javax.xml.stream.XMLStreamException;
import javax.xml.stream.events.Namespace;
import javax.xml.stream.events.StartElement;
import javax.xml.stream.events.XMLEvent;

import org.javelin.sws.ext.bind.internal.MarshallingContext;
import org.javelin.sws.ext.bind.internal.UnmarshallingContext;

/**
 * <p>In Object-XML mapping (JAXB2 in particular), each object may be converted to a series of {@link XMLEvent XML events}. Some of
 * these events (or of these events' properties) are independent of the objects' value (e.g., element QNames or sequence order of child
 * elements), some (usually attribute values and text nodes) have value based on the marshalled objects.</p>
 * 
 * <p>This abstract pattern does <b>not</b> concern any particular XSD Type (or XSD Component in general) or even a Java type.
 * A pattern may be a series of any XML events: comments, PIs, elements, whitespace/characters, etc. There is <b>no relation to XML Schema
 * in this class</b>.  It's just a series of XML Events, which represent some fragment (possibly not even a well-formed one) of XML
 * <b>instance</b>.</p>
 * 
 * <p>The main responsibility of this class is to establish a contract for marshalling (<b>replaying</b> XML events) and unmarshalling
 * (<b>consuming</b> XML events) Java objects to/from a series of XML events. These events are (in first iteration) mapped after StAX events.</p>
 * 
 * <p>The subclasses must specify how to (un)marshal:<ul>
 * <li>Information on XML Elements as specified in {@link XmlRootElement} annotation or {@link JAXBElement} object</li>
 * <li>Java objects (both primitive values and regular objects) - <b>always</b> as part of surrounding XML element</li>
 * <li>Java objects' properties (fields or JavaBean properties) - as simple or complex content of surrounded by XML element</li>
 * </ul></p>
 * 
 * <p>We'll have the following subclasses:<ul>
 * <li>Simple Content Pattern: how to (un)marshal objects which classes represent XSD Simple Types as characters information items</li>
 * <li>Attribute Pattern: how to (un)marshal objects objects which classes represent XSD Simple Types as attribute information items</li>
 * <li>Complex Type Pattern: how to (un)marshal objects which classes represent XSD Complex Types as a sequence of information items which
 * are defined by {@code attribute uses} + {@code content model} properties of XSD Complex Type. This pattern produces attributes, elements
 * and/or character data related to {@link XmlAttribute}, {@link XmlElement} and/or {@link XmlValue} annotations on Java Beans' properties.</li>
 * <li>Element Pattern: how to (un)marshal desired element with nested Simple Content Pattern or Complex Type Pattern.</li>
 * </ul></p>
 * 
 * <p>JAXB2 and XSD distinguishes between:<ul>
 * <li>simple type - just characters information item in XML; primitive object or class with single {@link XmlValue} in Java</li>
 * <li>complex type with simple content - attributes + characters information items in XML; class with single {@link XmlValue} and some {@link XmlAttribute}s in Java</li>
 * <li>complex type with complex content - attributes + element information items in XML (possibly <i>mixed</i>); class without {@link XmlValue} in Java</li>
 * </ul></p>
 * 
 * <p>Instances of subclasses of this pattern are created during initialization of {@link JAXBContext} on the basis of the information provided by
 * JAXB2 annotations - but <b>only</b> within the scope of successfull Object-XML Mapping. The subclasses <b>do not</b> represent XML Schema
 * Components (but they may contain/delegate to objects which represent them) - they won't help to generate proper XSD components by
 * themselves.</p>
 * 
 * @author Grzegorz Grzybek
 */
public abstract class XmlEventsPattern {

	public static final XMLEventFactory XML_EVENTS_FACTORY = XMLEventFactory.newInstance();

	/* Main contract */

	/**
	 * <p>Marshalling - converts any Java object into series of {@link XMLEvent XML events}.</p>
	 * 
	 * @param object or a {@link JAXBElement}
	 * @param eventWriter
	 * @param context
	 * @throws XMLStreamException
	 */
	public abstract void replay(Object object, XMLEventWriter eventWriter, MarshallingContext context) throws XMLStreamException;

	/**
	 * <p>Unmarshalling - converts a series of {@link XMLEvent XML events} into a Java object.</p>
	 * 
	 * @param eventReader
	 * @param context
	 * @return an object or a {@link JAXBElement}
	 * @throws XMLStreamException
	 */
	public abstract Object consume(XMLEventReader eventReader, UnmarshallingContext context) throws XMLStreamException;

	/* Helper methods */

	/**
	 * <p>Converts {@link QName} into {@code prefix:value} using correct prefix found in namespace context or after registering one.</p>
	 * <p>If {@link QName#getNamespaceURI()} is registered in current namespace context, prefix is chosen - preferably {@link QName#getPrefix()}.</p>
	 * 
	 * @param qName.getPrefix()
	 * @param eventWriter
	 * @param namespaceURI
	 * @return
	 */
	protected String safeGetQValue(MarshallingContext context, XMLEventWriter eventWriter, QName qName) throws XMLStreamException {
		// DESIGNFLAW: find a better way to force register namespace - repairing writer doesn't repair QName values, only QName names
		boolean repairingXmlEventWriter = context.isRepairingXmlEventWriter();
		context.setRepairingXmlEventWriter(false);
		String prefix = this.safeRegisterNamespace(context, eventWriter, qName);
		context.setRepairingXmlEventWriter(repairingXmlEventWriter);

		return ("".equals(prefix) ? "" : prefix + ":") + qName.getLocalPart();
	}

	/**
	 * <p>A method which creates an element. The namespace is looked up in {@link NamespaceContext}, proper prefix is used (or generated). The
	 * namespace is then declared (unless we have {@link XMLOutputFactory#IS_REPAIRING_NAMESPACES})</p>
	 * 
	 * @param context
	 * @param eventWriter
	 * @param qName
	 * @return
	 * @throws XMLStreamException
	 */
	protected StartElement safeCreateElement(MarshallingContext context, XMLEventWriter eventWriter, QName qName) throws XMLStreamException {
		Namespace namespace = this.safePrepareNamespace(context, eventWriter, qName, NamespaceRegistration.NO);

		// the namespace MAY NOT be registered yet (but it MAY be)
		StartElement element = XML_EVENTS_FACTORY.createStartElement(namespace.getPrefix(), qName.getNamespaceURI(), qName.getLocalPart(), null, null);
		eventWriter.add(element);

		// register namespace if necessary
		if (!context.isRepairingXmlEventWriter() && eventWriter.getPrefix(namespace.getNamespaceURI()) == null)
			eventWriter.add(namespace);
		
		return element;
	}

	/**
	 * Safely registers namespace (if necessary - {@link XMLEventWriter} may have {@link XMLOutputFactory#IS_REPAIRING_NAMESPACES} set
	 * or the namespace may already be registered) and returns its prefix.
	 * 
	 * @param context
	 * @param eventWriter
	 * @param qName
	 */
	protected String safeRegisterNamespace(MarshallingContext context, XMLEventWriter eventWriter, QName qName) throws XMLStreamException {
		Namespace namespace = this.safePrepareNamespace(context, eventWriter, qName, NamespaceRegistration.IF_NECESSARY);

		return namespace.getPrefix();
	}

	/**
	 * <p>Prepares {@link Namespace} to be added to {@link XMLEventWriter} and possibly declares it in {@link XMLEventWriter} if it's not already
	 * registered.</p>
	 * <p>This method always returns {@link Namespace} representing the {@link QName qName} parameter.</p>
	 * 
	 * @param context
	 * @param eventWriter
	 * @param qName
	 * @return
	 */
	protected Namespace safePrepareNamespace(MarshallingContext context, XMLEventWriter eventWriter, QName qName, NamespaceRegistration register) throws XMLStreamException {
		NamespaceContext nsc = eventWriter.getNamespaceContext();

		boolean prefixFound = false;
		// non null foundPrefix may be qName.getPrefix() or other (from NamespaceContext)
		String bestPrefix = null;
		String namespaceURI = qName.getNamespaceURI();

		Namespace result = null;
		@SuppressWarnings("unchecked")
		Iterator<String> prefixes = nsc.getPrefixes(namespaceURI);
		while (prefixes.hasNext()) {
			prefixFound = true;
			String prefix = prefixes.next();
			bestPrefix = prefix;
			if (prefix.equals(qName.getPrefix()))
				break;
		}

		if (context.isRepairingXmlEventWriter()) {
			// never register, return desired namespace and leave reparation to XMLEventWriter
			if (bestPrefix == null)
				return XML_EVENTS_FACTORY.createNamespace(namespaceURI);
			else
				return XML_EVENTS_FACTORY.createNamespace(bestPrefix, namespaceURI);
		}

		if (register == NamespaceRegistration.IF_DEFAULT_PREFIX && XMLConstants.DEFAULT_NS_PREFIX.equals(bestPrefix)) {
			// force registration of probably already registered namespace under new prefix
			prefixFound = false;
		}

		if (prefixFound) {
			// nothing to register
			return XML_EVENTS_FACTORY.createNamespace(bestPrefix, namespaceURI);
		} else {
			// new namespace, but there may be a conflicting namespace bound to qName.getPrefix()
			bestPrefix = qName.getPrefix();
			while (true) {
				String existingNamespace = nsc.getNamespaceURI(bestPrefix);
				// Woodstox returns XMLConstants.NULL_NS_URI for unbound XMLConstants.DEFAULT_NS_PREFIX - OK
				// Woodstox returns null for unbound non XMLConstants.DEFAULT_NS_PREFIX - wrong - should return XMLConstants.NULL_NS_URI also
				if (XMLConstants.DEFAULT_NS_PREFIX.equals(bestPrefix) && XMLConstants.NULL_NS_URI.equals(existingNamespace)) {
					// no conflict
					break;
				}
				if (existingNamespace == null || existingNamespace.equals(namespaceURI)) {
					// no conflict
					break;
				}
				// TODO: conflict only if the declaration is at the same level! Use SWE_MARSHALLER_REUSE_PREFIXES property to determine the behavior
				bestPrefix = context.newPrefix();
			}
			result = XML_EVENTS_FACTORY.createNamespace(bestPrefix, namespaceURI);
			
			if (register != NamespaceRegistration.NO)
				eventWriter.add(result);
		}
		
		return result;
	}

	/* (non-Javadoc)
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString() {
		return "No particular pattern of XML Events";
	}

}
