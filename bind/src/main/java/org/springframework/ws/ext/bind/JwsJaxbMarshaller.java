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

package org.springframework.ws.ext.bind;

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

import org.springframework.ws.ext.bind.internal.MarshallingContext;
import org.springframework.ws.ext.bind.internal.model.ElementPattern;
import org.springframework.ws.ext.bind.internal.model.XmlEventsPattern;
import org.springframework.ws.ext.bind.internal.stax.IndentingXMLEventWriter;
import org.w3c.dom.Node;
import org.xml.sax.ContentHandler;

/**
 * <p>Marshaller able to convert objects to XML representations. This marshaller can marshal any object, given the object is:<ul>
 * <li>a {@link JAXBElement}</li>
 * <li>an object of class annotated with {@link XmlRootElement} annotations</li>
 * </ul></p>
 *
 * @author Grzegorz Grzybek
 */
public class JwsJaxbMarshaller implements Marshaller, SweJaxbConstants {

	private SweJaxbContext jaxbContext;

	private Map<String, Object> properties = new HashMap<String, Object>();

	private AttachmentMarshaller attachmentMarshaller;

	private Listener listener;

	private Schema schema;

	private Map<Class<?>, XmlAdapter<?, ?>> adapters = new HashMap<Class<?>, XmlAdapter<?, ?>>();

	private ValidationEventHandler validationEventHandler;

	private XMLOutputFactory xmlOutputFactory = XMLOutputFactory.newFactory();

	/* properties-based configuration of marshaller */

	private boolean formatting = false;

	private boolean fragment = false;

	private String encoding = "UTF-8";

	private boolean multiRefEncoding = false;

	private boolean sendTypes = false;

	/**
	 * @param jaxbContext
	 */
	JwsJaxbMarshaller(SweJaxbContext jaxbContext) {
		this.jaxbContext = jaxbContext;
	}

	/*
	 * Marshal operations.
	 * 
	 * One marshal() method can invoke another. The "ultimate" marshal() method should (I think) take XMLEventWriter as an argument.
	 * The other methods construct or acquire proper XMLEventWriter object.
	 * 
	 * XMLEvent added() to XMLEventWriter is at the higher level than invocation of XMLStreamWriter methods. That's why Woodstox passes
	 * XMLStreamWriter to XMLEventWriter. We need bridge classes like e.g., javanet.staxutils.XMLStreamEventWriter (see: http://java.net/projects/stax-utils/pages/Utilities).
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

	/* (non-Javadoc)
	 * @see javax.xml.bind.Marshaller#marshal(java.lang.Object, javax.xml.stream.XMLStreamWriter)
	 */
	@Override
	public void marshal(Object jaxbElement, XMLStreamWriter writer) throws JAXBException {
		this.marshal(jaxbElement, new StAXResult(writer));
	}

	/* (non-Javadoc)
	 * @see javax.xml.bind.Marshaller#marshal(java.lang.Object, javax.xml.stream.XMLEventWriter)
	 */
	@Override
	public void marshal(Object jaxbElement, XMLEventWriter writer) throws JAXBException {
		this.marshal0(jaxbElement, writer);
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
		if (Marshaller.JAXB_FORMATTED_OUTPUT.equals(name))
			this.formatting = (Boolean) value;
		else if (Marshaller.JAXB_FRAGMENT.equals(name))
			this.fragment = (Boolean) value;
		else if (Marshaller.JAXB_ENCODING.equals(name))
			this.encoding = (String) value;
		else if (SweJaxbConstants.SWE_MARSHALLER_PROPERTY_JAXB_MULTIREFS.equals(name))
			this.multiRefEncoding = (Boolean) value;
		else if (SweJaxbConstants.SWE_MARSHALLER_PROPERTY_SEND_TYPES.equals(name))
			this.sendTypes = (Boolean) value;
		else
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
	 * The main marshal method which converts an object into a series of {@link XMLEventWriter XML events} being added to {@link XMLEventWriter}.
	 * 
	 * @param jaxbElement
	 * @param writer
	 */
	private void marshal0(Object jaxbElement, XMLEventWriter writer) throws JAXBException {
		XmlEventsPattern pattern = this.determineXmlPattern(jaxbElement);
		try {
			if (this.formatting)
				writer = new IndentingXMLEventWriter(writer);

			if (!this.fragment)
				writer.add(XmlEventsPattern.XML_EVENTS_FACTORY.createStartDocument(this.encoding, "1.0", true));

			MarshallingContext context = new MarshallingContext();
			context.setRepairingXmlEventWriter((Boolean) this.xmlOutputFactory.getProperty(XMLOutputFactory.IS_REPAIRING_NAMESPACES));
			context.setMultiRefEncoding(this.multiRefEncoding);
			context.setSendTypes(this.sendTypes);

			// go!
			pattern.replay(jaxbElement, writer, context);
			
			if (context.isMultiRefEncoding())
				context.getMultiRefSupport().outputMultiRefs(writer, context);

			if (!this.fragment)
				writer.add(XmlEventsPattern.XML_EVENTS_FACTORY.createEndDocument());
		}
		catch (XMLStreamException e) {
			throw new MarshalException(e);
		}
	}

	/**
	 * Using the class of the object to be marshalled determines a series of XML Events to be generated in order to marshal given object.
	 * Used only at the highest level of marshalling and must result in a pattern which {@link XmlEventsPattern#isElement() is an element}.
	 * 
	 * @param jaxbElement
	 * @return
	 * @throws MarshalException 
	 */
	private XmlEventsPattern determineXmlPattern(Object jaxbElement) throws MarshalException {
		Class<?> clz = jaxbElement.getClass();
		if (jaxbElement instanceof JAXBElement)
			clz = ((JAXBElement<?>) jaxbElement).getDeclaredType();

		// pattern (both for root and non-root classes) must be present in context's mapping metadata
		XmlEventsPattern pattern = this.jaxbContext.patterns.get(clz);

		if (pattern == null)
			throw new MarshalException("Unable to determine XML Events pattern to marshal object of class " + jaxbElement.getClass().getName());

		// if we marshal XmlRootElement, then return proper pattern
		if (this.jaxbContext.rootPatterns.containsKey(clz))
			return this.jaxbContext.rootPatterns.get(clz);

		// if we marshal JAXBElement, we create ElementPattern on demand (without caching it!)
		if (jaxbElement instanceof JAXBElement) {
			// TODO: use ((JAXBElement<?>) jaxbElement).getDeclaredType() or ((JAXBElement<?>) jaxbElement).getValue().getClass()?
			return new ElementPattern(pattern.getSchemaType(), ((JAXBElement<?>) jaxbElement).getDeclaredType(), ((JAXBElement<?>) jaxbElement).getName(), pattern);
		}

		throw new MarshalException("Unable to marshal object of class " + clz.getName()
				+ ". Only JAXBElements and @XmlRootElement annotated classes may be marshalled.");
	}

}
