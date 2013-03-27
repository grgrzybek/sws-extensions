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

import java.io.File;
import java.io.InputStream;
import java.io.Reader;
import java.net.URL;
import java.util.HashMap;
import java.util.Map;

import javax.xml.bind.JAXBElement;
import javax.xml.bind.JAXBException;
import javax.xml.bind.MarshalException;
import javax.xml.bind.PropertyException;
import javax.xml.bind.UnmarshalException;
import javax.xml.bind.Unmarshaller;
import javax.xml.bind.UnmarshallerHandler;
import javax.xml.bind.ValidationEventHandler;
import javax.xml.bind.annotation.adapters.XmlAdapter;
import javax.xml.bind.attachment.AttachmentUnmarshaller;
import javax.xml.namespace.QName;
import javax.xml.stream.XMLEventReader;
import javax.xml.stream.XMLStreamConstants;
import javax.xml.stream.XMLStreamException;
import javax.xml.stream.XMLStreamReader;
import javax.xml.stream.events.StartElement;
import javax.xml.stream.events.XMLEvent;
import javax.xml.transform.Source;
import javax.xml.validation.Schema;

import org.springframework.ws.ext.bind.internal.UnmarshallingContext;
import org.springframework.ws.ext.bind.internal.model.ElementPattern;
import org.springframework.ws.ext.bind.internal.model.XmlEventsPattern;
import org.w3c.dom.Node;
import org.xml.sax.InputSource;

/**
 * <p></p>
 *
 * @author Grzegorz Grzybek
 */
public class SweJaxbUnmarshaller implements Unmarshaller {

	private SweJaxbContext jaxbContext;

	private Map<String, Object> properties = new HashMap<String, Object>();

	private AttachmentUnmarshaller attachmentUnmarshaller;

	private Listener listener;

	private Schema schema;

	private Map<Class<?>, XmlAdapter<?, ?>> adapters = new HashMap<Class<?>, XmlAdapter<?, ?>>();

	private ValidationEventHandler validationEventHandler;

	private boolean validating;

	/**
	 * @param jaxbContext
	 */
	SweJaxbUnmarshaller(SweJaxbContext jaxbContext) {
		this.jaxbContext = jaxbContext;
	}

	/*
	 * Unmarshal operations.
	 * 
	 * The final unmarshal method will convert a series of XMLEvents into a Java object
	 */

	/* (non-Javadoc)
	 * @see javax.xml.bind.Unmarshaller#unmarshal(java.io.File)
	 */
	@Override
	public Object unmarshal(File f) throws JAXBException {
		// TODO Auto-generated method stub
		return null;
	}

	/* (non-Javadoc)
	 * @see javax.xml.bind.Unmarshaller#unmarshal(java.io.InputStream)
	 */
	@Override
	public Object unmarshal(InputStream is) throws JAXBException {
		// TODO Auto-generated method stub
		return null;
	}

	/* (non-Javadoc)
	 * @see javax.xml.bind.Unmarshaller#unmarshal(java.io.Reader)
	 */
	@Override
	public Object unmarshal(Reader reader) throws JAXBException {
		// TODO Auto-generated method stub
		return null;
	}

	/* (non-Javadoc)
	 * @see javax.xml.bind.Unmarshaller#unmarshal(java.net.URL)
	 */
	@Override
	public Object unmarshal(URL url) throws JAXBException {
		// TODO Auto-generated method stub
		return null;
	}

	/* (non-Javadoc)
	 * @see javax.xml.bind.Unmarshaller#unmarshal(org.xml.sax.InputSource)
	 */
	@Override
	public Object unmarshal(InputSource source) throws JAXBException {
		// TODO Auto-generated method stub
		return null;
	}

	/* (non-Javadoc)
	 * @see javax.xml.bind.Unmarshaller#unmarshal(org.w3c.dom.Node)
	 */
	@Override
	public Object unmarshal(Node node) throws JAXBException {
		// TODO Auto-generated method stub
		return null;
	}

	/* (non-Javadoc)
	 * @see javax.xml.bind.Unmarshaller#unmarshal(org.w3c.dom.Node, java.lang.Class)
	 */
	@Override
	public <T> JAXBElement<T> unmarshal(Node node, Class<T> declaredType) throws JAXBException {
		// TODO Auto-generated method stub
		return null;
	}

	/* (non-Javadoc)
	 * @see javax.xml.bind.Unmarshaller#unmarshal(javax.xml.transform.Source)
	 */
	@Override
	public Object unmarshal(Source source) throws JAXBException {
		// TODO Auto-generated method stub
		return null;
	}

	/* (non-Javadoc)
	 * @see javax.xml.bind.Unmarshaller#unmarshal(javax.xml.transform.Source, java.lang.Class)
	 */
	@Override
	public <T> JAXBElement<T> unmarshal(Source source, Class<T> declaredType) throws JAXBException {
		// TODO Auto-generated method stub
		return null;
	}

	/* (non-Javadoc)
	 * @see javax.xml.bind.Unmarshaller#unmarshal(javax.xml.stream.XMLStreamReader)
	 */
	@Override
	public Object unmarshal(XMLStreamReader reader) throws JAXBException {
		// TODO Auto-generated method stub
		return null;
	}

	/* (non-Javadoc)
	 * @see javax.xml.bind.Unmarshaller#unmarshal(javax.xml.stream.XMLStreamReader, java.lang.Class)
	 */
	@Override
	public <T> JAXBElement<T> unmarshal(XMLStreamReader reader, Class<T> declaredType) throws JAXBException {
		// TODO Auto-generated method stub
		return null;
	}

	/* (non-Javadoc)
	 * @see javax.xml.bind.Unmarshaller#unmarshal(javax.xml.stream.XMLEventReader)
	 */
	@Override
	public Object unmarshal(XMLEventReader reader) throws JAXBException {
		return this.unmarshal(reader, null).getValue();
	}

	/* (non-Javadoc)
	 * @see javax.xml.bind.Unmarshaller#unmarshal(javax.xml.stream.XMLEventReader, java.lang.Class)
	 */
	@Override
	public <T> JAXBElement<T> unmarshal(XMLEventReader reader, Class<T> declaredType) throws JAXBException {
		this.positionReaderAtStartElement(reader);
		return this.unmarshal0(reader, declaredType);
	}

	/* Other operations */

	/* (non-Javadoc)
	 * @see javax.xml.bind.Unmarshaller#getUnmarshallerHandler()
	 */
	@Override
	public UnmarshallerHandler getUnmarshallerHandler() {
		// TODO Auto-generated method stub
		return null;
	}

	/* (non-Javadoc)
	 * @see javax.xml.bind.Unmarshaller#setValidating(boolean)
	 */
	@Override
	public void setValidating(boolean validating) throws JAXBException {
		this.validating = validating;
	}

	/* (non-Javadoc)
	 * @see javax.xml.bind.Unmarshaller#isValidating()
	 */
	@Override
	public boolean isValidating() throws JAXBException {
		return this.validating;
	}

	/* (non-Javadoc)
	 * @see javax.xml.bind.Unmarshaller#setEventHandler(javax.xml.bind.ValidationEventHandler)
	 */
	@Override
	public void setEventHandler(ValidationEventHandler handler) throws JAXBException {
		this.validationEventHandler = handler;
	}

	/* (non-Javadoc)
	 * @see javax.xml.bind.Unmarshaller#getEventHandler()
	 */
	@Override
	public ValidationEventHandler getEventHandler() throws JAXBException {
		return this.validationEventHandler;
	}

	/* (non-Javadoc)
	 * @see javax.xml.bind.Unmarshaller#setProperty(java.lang.String, java.lang.Object)
	 */
	@Override
	public void setProperty(String name, Object value) throws PropertyException {
		this.properties.put(name, value);
	}

	/* (non-Javadoc)
	 * @see javax.xml.bind.Unmarshaller#getProperty(java.lang.String)
	 */
	@Override
	public Object getProperty(String name) throws PropertyException {
		return this.properties.get(name);
	}

	/* (non-Javadoc)
	 * @see javax.xml.bind.Unmarshaller#setSchema(javax.xml.validation.Schema)
	 */
	@Override
	public void setSchema(Schema schema) {
		this.schema = schema;
	}

	/* (non-Javadoc)
	 * @see javax.xml.bind.Unmarshaller#getSchema()
	 */
	@Override
	public Schema getSchema() {
		return this.schema;
	}

	/* (non-Javadoc)
	 * @see javax.xml.bind.Unmarshaller#setAdapter(javax.xml.bind.annotation.adapters.XmlAdapter)
	 */
	@Override
	@SuppressWarnings("rawtypes")
	public void setAdapter(XmlAdapter adapter) {
		throw new UnsupportedOperationException("What class do you want to set this adapter for? Probably not for " + adapter.getClass() + "...");
	}

	/* (non-Javadoc)
	 * @see javax.xml.bind.Unmarshaller#setAdapter(java.lang.Class, javax.xml.bind.annotation.adapters.XmlAdapter)
	 */
	@Override
	@SuppressWarnings("rawtypes")
	public <A extends XmlAdapter> void setAdapter(Class<A> type, A adapter) {
		this.adapters.put(type, adapter);
	}

	/* (non-Javadoc)
	 * @see javax.xml.bind.Unmarshaller#getAdapter(java.lang.Class)
	 */
	@Override
	@SuppressWarnings({ "rawtypes", "unchecked" })
	public <A extends XmlAdapter> A getAdapter(Class<A> type) {
		return (A) this.adapters.get(type);
	}

	/* (non-Javadoc)
	 * @see javax.xml.bind.Unmarshaller#setAttachmentUnmarshaller(javax.xml.bind.attachment.AttachmentUnmarshaller)
	 */
	@Override
	public void setAttachmentUnmarshaller(AttachmentUnmarshaller au) {
		this.attachmentUnmarshaller = au;
	}

	/* (non-Javadoc)
	 * @see javax.xml.bind.Unmarshaller#getAttachmentUnmarshaller()
	 */
	@Override
	public AttachmentUnmarshaller getAttachmentUnmarshaller() {
		return this.attachmentUnmarshaller;
	}

	/* (non-Javadoc)
	 * @see javax.xml.bind.Unmarshaller#setListener(javax.xml.bind.Unmarshaller.Listener)
	 */
	@Override
	public void setListener(Listener listener) {
		this.listener = listener;
	}

	/* (non-Javadoc)
	 * @see javax.xml.bind.Unmarshaller#getListener()
	 */
	@Override
	public Listener getListener() {
		return this.listener;
	}

	/* Internal methods */

	/**
	 * The main unmarshal method.
	 * 
	 * @param jaxbElement
	 * @param writer
	 */
	@SuppressWarnings("unchecked")
	private <T> JAXBElement<T> unmarshal0(XMLEventReader reader, Class<T> declaredType) throws JAXBException {
		try {
			StartElement unmarshalledElement = reader.peek().asStartElement();
			@SuppressWarnings("rawtypes")
			XmlEventsPattern pattern = null;

			if (declaredType == null) {
				// we can unmarshal to @XmlRootElement annotated classes ONLY
				pattern = this.determineRootXmlPattern(unmarshalledElement);
				if (pattern == null)
					throw new UnmarshalException("Can't unmarshall \"" + unmarshalledElement.getName() + "\" element. Ensure there's proper @XmlRootElement annotated class in this unmarshaller's context.");
			} else {
				// we know what class to unmarshal the events into - this class serves as the content model and the events must match this model
				pattern = this.jaxbContext.patterns.get(declaredType);
				if (pattern == null)
					throw new UnmarshalException("Can't unmarshall \"" + unmarshalledElement.getName() + "\" into \"" + declaredType.getName() + "\". There's no mapping of this class to proper content model in this context.");
				
				// we'll wrap it inside ElementPattern which will peel the events off of the start and end elemen events
				pattern = ElementPattern.newElementPattern(pattern.getSchemaType(), declaredType, unmarshalledElement.getName(), pattern);
			}

			UnmarshallingContext context = new UnmarshallingContext();

			// go!
			T result = (T) pattern.consume(reader, context);

			// if (context.isMultiRefEncoding())
			// context.getMultiRefSupport().xyzMultiRefs(writer, context);

			JAXBElement<T> resultingJaxbElement = new JAXBElement<T>(unmarshalledElement.getName(), declaredType, result);
			resultingJaxbElement.setNil(result == null);

			return resultingJaxbElement;
		}
		catch (XMLStreamException e) {
			throw new UnmarshalException(e);
		}
	}

	/**
	 * Skips first events of {@link XMLEventReader} until End-of-stream or {@link XMLStreamConstants#START_ELEMENT} is found
	 * 
	 * @param reader
	 */
	private void positionReaderAtStartElement(XMLEventReader reader) throws UnmarshalException {
		XMLEvent first = null;
		try {
			while (true) {
				if ((first = reader.peek()) == null)
					throw new UnmarshalException("Can't unmarshal empty sequence of XML Events");
				if (first.getEventType() == XMLEvent.START_ELEMENT)
					break;
				first = reader.nextEvent();
			}
		} catch (XMLStreamException e) {
			throw new UnmarshalException(e.getMessage(), e);
		}
	}

	/**
	 * @param jaxbElement
	 * @return
	 * @throws MarshalException
	 */
	private XmlEventsPattern<?> determineRootXmlPattern(StartElement startElementEvent) throws MarshalException {
		QName qName = startElementEvent.getName();
		// TODO: handle xsi:type
		/*Attribute xsiType = */startElementEvent.getAttributeByName(SweJaxbConstants.XSI_TYPE);
		return this.jaxbContext.rootPatternsForQNames.get(qName);
	}

}
