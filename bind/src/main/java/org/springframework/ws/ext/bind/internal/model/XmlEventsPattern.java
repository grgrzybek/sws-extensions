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

import java.util.Iterator;

import javax.xml.XMLConstants;
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

import org.springframework.ws.ext.bind.internal.MarshallingContext;
import org.springframework.ws.ext.bind.internal.UnmarshallingContext;

/**
 * <p>In Object-XML mapping, each object may be converted to a series of {@link XMLEvent XML events}. Some of these events (or of these
 * events' properties) are independent of the objects value (e.g., element QNames or sequence of child elements), some (usually attribute
 * values and text nodes) have value based on the marshalled objects.</p>
 * 
 * <p>Every pattern concerns a particular XSD Type and Java type</p>
 * 
 * <p>DESIGNFLAW: During unmarshalling operation, a series of {@link XMLEvent XML events} controls Java objects creation. It is necessary
 * (unlike in marshalling) to maintain a state which determines the currently created/unmarshalled object.</p>
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
public abstract class XmlEventsPattern {

	public static final XMLEventFactory XML_EVENTS_FACTORY = XMLEventFactory.newInstance();

	private QName schemaType;
	private Class<?> javaType;

	/**
	 * @param schemaType
	 * @param javaType
	 */
	protected XmlEventsPattern(QName schemaType, Class<?> javaType) {
		this.schemaType = schemaType;
		this.javaType = javaType;
	}

	/**
	 * <p>Marshalling - converts Java object into series of {@link XMLEvent XML events}.</p>
	 * 
	 * @param object
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
	 * @return
	 * @throws XMLStreamException
	 */
	public abstract Object consume(XMLEventReader eventReader, UnmarshallingContext context) throws XMLStreamException;

	/**
	 * <p>Checks whether this pattern relates to a XML Schema Type derived from xsd:anySimpleType and which outputs only objects
	 * convertible to {@link String}</p>
	 * 
	 * @return
	 */
	public abstract boolean isSimpleType();

	/**
	 * @return the schemaType
	 */
	public QName getSchemaType() {
		return this.schemaType;
	}

	/**
	 * @return the javaType
	 */
	public Class<?> getJavaType() {
		return this.javaType;
	}

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

}
