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

package org.springframework.ws.jaxws.soapenc;

import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.OutputStream;
import java.io.Writer;
import java.util.HashMap;
import java.util.Map;

import javax.xml.bind.JAXBElement;
import javax.xml.bind.JAXBException;
import javax.xml.bind.MarshalException;
import javax.xml.bind.Marshaller;
import javax.xml.bind.PropertyException;
import javax.xml.bind.ValidationEventHandler;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.adapters.XmlAdapter;
import javax.xml.bind.attachment.AttachmentMarshaller;
import javax.xml.stream.XMLEventWriter;
import javax.xml.stream.XMLOutputFactory;
import javax.xml.stream.XMLStreamException;
import javax.xml.stream.XMLStreamWriter;
import javax.xml.transform.Result;
import javax.xml.transform.dom.DOMResult;
import javax.xml.transform.sax.SAXResult;
import javax.xml.transform.stax.StAXResult;
import javax.xml.transform.stream.StreamResult;
import javax.xml.validation.Schema;

import org.springframework.beans.BeanWrapper;
import org.springframework.beans.PropertyAccessorFactory;
import org.springframework.ws.jaxws.soapenc.internal.model.WrappedXmlEventsPattern;
import org.springframework.ws.jaxws.soapenc.internal.model.XmlEventsPattern;
import org.w3c.dom.Node;
import org.xml.sax.ContentHandler;

/**
 * <p>Marshaller able to convert objects to XML representations. This marshaller can marshall any object, given the object is:<ul>
 * <li>a {@link JAXBElement}</li>
 * <li>an object of class annotated with {@link XmlRootElement} annotations</li>
 * </ul></p>
 *
 * @author Grzegorz Grzybek
 */
public class JwsJaxbMarshaller implements Marshaller {

	private JwsJaxbContext jaxbContext;

	private Map<String, Object> properties;

	private AttachmentMarshaller attachmentMarshaller;

	private Listener listener;

	private Schema schema;

	private Map<Class<?>, XmlAdapter<?, ?>> adapters = new HashMap<Class<?>, XmlAdapter<?, ?>>();

	private ValidationEventHandler validationEventHandler;

	private XMLOutputFactory xmlOutputFactory = XMLOutputFactory.newFactory();

	/**
	 * @param jaxbContext
	 */
	JwsJaxbMarshaller(JwsJaxbContext jaxbContext) {
		this.jaxbContext = jaxbContext;
	}

	/*
	 * Marshall operations.
	 * 
	 * One marshal() method can invoke another. The "ultimate" marshal() method should (I think) take XMLStreamWriter as an argument.
	 * The other methods construct proper XMLStreamWriter object (for "lower-level" objects) or acquire XMLStreamWriter (from "higher-level" objects
	 * such as XMLEventWriter).
	 * 
	 * XMLEvent added() to XMLEventWriter is at the higher level than invocation of XMLStreamWriter methods. That's why Woodstox passes
	 * XMLStreamWriter to XMLEventWriter. We need a class like javanet.staxutils.XMLStreamEventWriter (see: http://java.net/projects/stax-utils/pages/Utilities).
	 * Some cases are provided in org.springframework.util.xml.StaxUtils - but not all of them.
	 */

	/* (non-Javadoc)
	 * @see javax.xml.bind.Marshaller#marshal(java.lang.Object, javax.xml.transform.Result)
	 */
	@Override
	public void marshal(Object jaxbElement, Result result) throws JAXBException {
		try {
			XMLEventWriter writer = this.xmlOutputFactory.createXMLEventWriter(result);
			this.marshal(jaxbElement, writer);
			writer.flush();
		}
		catch (XMLStreamException e) {
			throw new JAXBException(e.getMessage(), e);
		}
	}

	/* (non-Javadoc)
	 * @see javax.xml.bind.Marshaller#marshal(java.lang.Object, java.io.OutputStream)
	 */
	@Override
	public void marshal(Object jaxbElement, OutputStream os) throws JAXBException {
		this.marshal(jaxbElement, new StreamResult(os));
	}

	/* (non-Javadoc)
	 * @see javax.xml.bind.Marshaller#marshal(java.lang.Object, java.io.File)
	 */
	@Override
	public void marshal(Object jaxbElement, File output) throws JAXBException {
		try {
			this.marshal(jaxbElement, new BufferedOutputStream(new FileOutputStream(output)));
		}
		catch (FileNotFoundException e) {
			throw new JAXBException(e);
		}
	}

	/* (non-Javadoc)
	 * @see javax.xml.bind.Marshaller#marshal(java.lang.Object, java.io.Writer)
	 */
	@Override
	public void marshal(Object jaxbElement, Writer writer) throws JAXBException {
		this.marshal(jaxbElement, new StreamResult(writer));
	}

	/* (non-Javadoc)
	 * @see javax.xml.bind.Marshaller#marshal(java.lang.Object, org.xml.sax.ContentHandler)
	 */
	@Override
	public void marshal(Object jaxbElement, ContentHandler handler) throws JAXBException {
		this.marshal(jaxbElement, new SAXResult(handler));
	}

	/* (non-Javadoc)
	 * @see javax.xml.bind.Marshaller#marshal(java.lang.Object, org.w3c.dom.Node)
	 */
	@Override
	public void marshal(Object jaxbElement, Node node) throws JAXBException {
		this.marshal(jaxbElement, new DOMResult(node));
	}

	/**
	/* (non-Javadoc)
	 * @see javax.xml.bind.Marshaller#marshal(java.lang.Object, javax.xml.stream.XMLStreamWriter)
	 */
	@Override
	public void marshal(Object jaxbElement, XMLStreamWriter writer) throws JAXBException {
		this.marshal(jaxbElement, new StAXResult(writer));
	}

	/**
	 * The main marshal method.
	 * 
	 * @see javax.xml.bind.Marshaller#marshal(java.lang.Object, javax.xml.stream.XMLEventWriter)
	 */
	@Override
	public void marshal(Object jaxbElement, XMLEventWriter writer) throws JAXBException {
		XmlEventsPattern pattern = this.determineMarshallingPattern(jaxbElement);
		try {
			// for convenience we will use PropertyAccessor
			// DESIGNFLAW: we allow to wrap JAXBElement inside BeanWrapper
			BeanWrapper wrapper = PropertyAccessorFactory.forBeanPropertyAccess(jaxbElement);
			pattern.replay(wrapper, writer, (Boolean) this.xmlOutputFactory.getProperty(XMLOutputFactory.IS_REPAIRING_NAMESPACES));
		}
		catch (XMLStreamException e) {
			throw new MarshalException(e);
		}
	}

	/* Other operations */

	/* (non-Javadoc)
	 * @see javax.xml.bind.Marshaller#getNode(java.lang.Object)
	 */
	@Override
	public Node getNode(Object contentTree) throws JAXBException {
		throw new UnsupportedOperationException("Why would anyone want to do this (at least now)?");
	}

	/* (non-Javadoc)
	 * @see javax.xml.bind.Marshaller#setProperty(java.lang.String, java.lang.Object)
	 */
	@Override
	public void setProperty(String name, Object value) throws PropertyException {
		this.properties.put(name, value);
	}

	/* (non-Javadoc)
	 * @see javax.xml.bind.Marshaller#getProperty(java.lang.String)
	 */
	@Override
	public Object getProperty(String name) throws PropertyException {
		return this.properties.get(name);
	}

	/* (non-Javadoc)
	 * @see javax.xml.bind.Marshaller#setEventHandler(javax.xml.bind.ValidationEventHandler)
	 */
	@Override
	public void setEventHandler(ValidationEventHandler handler) throws JAXBException {
		this.validationEventHandler = handler;
	}

	/* (non-Javadoc)
	 * @see javax.xml.bind.Marshaller#getEventHandler()
	 */
	@Override
	public ValidationEventHandler getEventHandler() throws JAXBException {
		return this.validationEventHandler;
	}

	/* (non-Javadoc)
	 * @see javax.xml.bind.Marshaller#setAdapter(javax.xml.bind.annotation.adapters.XmlAdapter)
	 */
	@Override
	@SuppressWarnings("rawtypes")
	public void setAdapter(XmlAdapter adapter) {
		throw new UnsupportedOperationException("What class do you want to set this adapter for? Probably not for " + adapter.getClass() + "...");
	}

	/* (non-Javadoc)
	 * @see javax.xml.bind.Marshaller#setAdapter(java.lang.Class, javax.xml.bind.annotation.adapters.XmlAdapter)
	 */
	@Override
	@SuppressWarnings("rawtypes")
	public <A extends XmlAdapter> void setAdapter(Class<A> type, A adapter) {
		this.adapters.put(type, adapter);
	}

	/* (non-Javadoc)
	 * @see javax.xml.bind.Marshaller#getAdapter(java.lang.Class)
	 */
	@Override
	@SuppressWarnings({ "rawtypes", "unchecked" })
	public <A extends XmlAdapter> A getAdapter(Class<A> type) {
		return (A) this.adapters.get(type);
	}

	/* (non-Javadoc)
	 * @see javax.xml.bind.Marshaller#setAttachmentMarshaller(javax.xml.bind.attachment.AttachmentMarshaller)
	 */
	@Override
	public void setAttachmentMarshaller(AttachmentMarshaller am) {
		this.attachmentMarshaller = am;
	}

	/* (non-Javadoc)
	 * @see javax.xml.bind.Marshaller#getAttachmentMarshaller()
	 */
	@Override
	public AttachmentMarshaller getAttachmentMarshaller() {
		return this.attachmentMarshaller;
	}

	/* (non-Javadoc)
	 * @see javax.xml.bind.Marshaller#setSchema(javax.xml.validation.Schema)
	 */
	@Override
	public void setSchema(Schema schema) {
		this.schema = schema;
	}

	/* (non-Javadoc)
	 * @see javax.xml.bind.Marshaller#getSchema()
	 */
	@Override
	public Schema getSchema() {
		return this.schema;
	}

	/* (non-Javadoc)
	 * @see javax.xml.bind.Marshaller#setListener(javax.xml.bind.Marshaller.Listener)
	 */
	@Override
	public void setListener(Listener listener) {
		this.listener = listener;
	}

	/* (non-Javadoc)
	 * @see javax.xml.bind.Marshaller#getListener()
	 */
	@Override
	public Listener getListener() {
		return this.listener;
	}

	/* Internal methods */

	/**
	 * Using the class of the object to be marshalled determines a series of XML Events to be generated in order to marshal given object.
	 * Used only at the highest level of marshalling and must result in a pattern which {@link XmlEventsPattern#isElement() is an element}.
	 * 
	 * @param jaxbElement
	 * @return
	 * @throws MarshalException 
	 */
	private XmlEventsPattern determineMarshallingPattern(Object jaxbElement) throws MarshalException {
		Class<?> clz = jaxbElement.getClass();
		if (jaxbElement instanceof JAXBElement)
			clz  = ((JAXBElement<?>) jaxbElement).getDeclaredType();

		// pattern (both for root and non-root classes) must be present in class2meta
		XmlEventsPattern xmlEventsPattern = this.jaxbContext.class2meta.get(clz);

		if (xmlEventsPattern == null)
			throw new MarshalException("Unable to determine XML Events pattern to marshall object of class " + jaxbElement.getClass().getName());

		if (jaxbElement instanceof JAXBElement) {
			// we can determine how to marshall the value of the object (pattern) AND what XML element to create (info from JAXBElement)
			return new WrappedXmlEventsPattern(xmlEventsPattern, ((JAXBElement<?>) jaxbElement).getName());
		} else if (this.jaxbContext.class2rootPatterns.containsKey(clz)) {
			// we're marshalling @XmlRootElement
			return this.jaxbContext.class2rootPatterns.get(clz);
		}

		throw new MarshalException("Unable to marshall object of class " + clz.getName() + ". Only JAXBElements and @XmlRootElement annotated classes may be marshalled.");
	}

}
