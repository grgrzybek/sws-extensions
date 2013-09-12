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
import java.util.LinkedList;
import java.util.List;

import javax.xml.bind.JAXBElement;
import javax.xml.namespace.QName;
import javax.xml.stream.XMLEventReader;
import javax.xml.stream.XMLEventWriter;
import javax.xml.stream.XMLStreamConstants;
import javax.xml.stream.XMLStreamException;
import javax.xml.stream.events.Attribute;
import javax.xml.stream.events.StartElement;
import javax.xml.stream.events.XMLEvent;

import org.javelin.sws.ext.bind.SweJaxbConstants;
import org.javelin.sws.ext.bind.internal.MarshallingContext;
import org.javelin.sws.ext.bind.internal.UnmarshallingContext;
import org.javelin.sws.ext.bind.internal.stax.AttributesAwareXMLEventReader;

/**
 * <p>This class represents all <a href="http://www.w3.org/TR/xmlschema-1/#cElement_Declarations">Element Declarations</a>
 * mapped to Java representation.</p>
 * 
 * <p>XSD Element Declaration is an instantiation of XSD Type Definition Component - it has {@link QName} and type. In Java category the related
 * (contained) type is an object of {@link TypedPattern} class. The same Java value (e.g., particular String or MyType)
 * may be marshalled in different XML Elements (this is what {@link JAXBElement} is used for)</p>
 * 
 * <p>XSD Element Declaration is constrained by XSD Type Definition Component<ul>
 * <li>no type means empty element</li>
 * <li>simpleType constrains only character information item children of element. attributes and child elements information items are empty</li>
 * <li>complexType constrains:<ul>
 * <li>attribute information items of element by providing set of Attribute Declarations, and</li>
 * <li>element and character information item children of element by providing a
 * <a href="http://www.w3.org/TR/xmlschema-1/#key-contentModel">Content Model Particle</a></li>
 * </ul></li>
 * </ul></p>
 * 
 * <p>So basically an element pattern is a wrapper which has a specific {@link QName name}. The content (attrbutes, element and/or
 * character <i>children information items</i>) is handled by the nested type definition component pattern.</p>
 * 
 * @author Grzegorz Grzybek
 * @param <T> A Java type which relates to XSD Type Definition Component this element has as {@code type definition} XSD property
 */
public class ElementPattern<T> extends XmlEventsPattern {

	private QName elementName;

	private TypedPattern<T> nestedPattern;

	/**
	 * @param schemaType
	 * @param javaType
	 * @param elementName
	 * @param nestedPattern
	 */
	private ElementPattern(QName elementName, TypedPattern<T> nestedPattern) {
		this.elementName = elementName;
		this.nestedPattern = nestedPattern;
	}

	/**
	 * @param elementName
	 * @param nestedPattern
	 * @return
	 */
	public static <T> ElementPattern<T> newElementPattern(QName elementName, TypedPattern<T> nestedPattern) {
		return new ElementPattern<T>(elementName, nestedPattern);
	}

	/* (non-Javadoc)
	 * @see org.javelin.sws.ext.bind.internal.model.XmlEventsPattern#replay(java.lang.Object, javax.xml.stream.XMLEventWriter, org.javelin.sws.ext.bind.internal.MarshallingContext)
	 */
	@Override
	public void replay(Object object, XMLEventWriter eventWriter, MarshallingContext context) throws XMLStreamException {
		// <elementName>
		StartElement startElement = this.safeCreateElement(context, eventWriter, this.elementName);

		boolean isNil = false;
		if (object instanceof JAXBElement) {
			// is it xsi:nil?
			if (((JAXBElement<?>) object).isNil())
				isNil = true;
			// dereference marshalled value
			object = ((JAXBElement<?>) object).getValue();
		}
		else if (object == null) {
			isNil = true;
		}

		// TODO: defer writing xsi:type for multiRefs
		// TODO: move safeGetQValue() and safeRegisterNamespace() out of this hierarchy into helper class
		if (context.isSendTypes()/* || TODO: must send xsi:type anyway*/) {
			String qValue = this.safeGetQValue(context, eventWriter, this.nestedPattern.getSchemaType());
			String prefix = this.safeRegisterNamespace(context, eventWriter, SweJaxbConstants.XSI_TYPE);
			eventWriter.add(XML_EVENTS_FACTORY.createAttribute(prefix, SweJaxbConstants.XSI_TYPE.getNamespaceURI(), SweJaxbConstants.XSI_TYPE.getLocalPart(), qValue));
		}

		if (isNil) {
			String prefix = this.safeRegisterNamespace(context, eventWriter, SweJaxbConstants.XSI_NIL);
			eventWriter.add(XML_EVENTS_FACTORY.createAttribute(prefix, SweJaxbConstants.XSI_NIL.getNamespaceURI(), SweJaxbConstants.XSI_NIL.getLocalPart(), "true"));
		}
		else {
			if (context.isMultiRefEncoding()) {
				// choices:
				// - marshal SimpleContentPattern.INSTANCE pattern "inline" or as @href?
				// - marshal single references "inline" or as @href to multiRef? - requires deferred marshalling to see wether there will more references
				// to this value
				// - add xsi:type?

				// the "href" attribute should be unqualified
				// TODO: what about @href attributes inside elements in default, non-empty namespace? Like: <a xmlns="b" href="#1" />...

				context.getMultiRefSupport().registerMultiRef(object, eventWriter, this.nestedPattern);
			}
			else {
				// inline children/attribute information items
				this.nestedPattern.replay(object, eventWriter, context);
			}
		}

		// </elementName>
		eventWriter.add(XML_EVENTS_FACTORY.createEndElement(startElement.getName(), null));
	}

	/* (non-Javadoc)
	 * @see org.javelin.sws.ext.bind.internal.model.XmlEventsPattern#consume(javax.xml.stream.XMLEventReader, org.javelin.sws.ext.bind.internal.UnmarshallingContext)
	 */
	@Override
	public T consume(XMLEventReader eventReader, UnmarshallingContext context) throws XMLStreamException {
		// just skip element's START_ELEMENT event
		StartElement startElement = eventReader.nextEvent().asStartElement();

		// StartElement may contain attributes - these are NOT available as separate events in eventReader.nextEvent()!
		Iterator<?> attributes = startElement.getAttributes();
		List<Attribute> attrList = new LinkedList<Attribute>();
		while (attributes.hasNext()) {
			Attribute a = (Attribute) attributes.next();
			attrList.add(a);
		}

		T value = this.nestedPattern.consumeValue(new AttributesAwareXMLEventReader(eventReader, attrList), context);

		// skip element's END_ELEMENT event
		while (eventReader.peek() != null) {
			XMLEvent ev = eventReader.nextEvent();
			if (ev.getEventType() == XMLStreamConstants.END_ELEMENT)
				break;
		}

		return value;
	}

	/* (non-Javadoc)
	 * @see org.springframework.ws.jaxws.soapenc.internal.model.PropertyPattern#toString()
	 */
	@Override
	public String toString() {
		return "Pattern marshalled inside \"" + this.elementName + "\" ELEMENT event";
	}

	/**
	 * @return the elementName
	 */
	public QName getElementName() {
		return this.elementName;
	}

}
