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

import java.io.File;
import java.io.InputStream;
import java.io.Reader;
import java.net.URL;

import javax.xml.bind.JAXBElement;
import javax.xml.bind.JAXBException;
import javax.xml.bind.PropertyException;
import javax.xml.bind.Unmarshaller;
import javax.xml.bind.UnmarshallerHandler;
import javax.xml.bind.ValidationEventHandler;
import javax.xml.bind.annotation.adapters.XmlAdapter;
import javax.xml.bind.attachment.AttachmentUnmarshaller;
import javax.xml.stream.XMLEventReader;
import javax.xml.stream.XMLStreamReader;
import javax.xml.transform.Source;
import javax.xml.validation.Schema;

import org.w3c.dom.Node;
import org.xml.sax.InputSource;

/**
 * <p></p>
 *
 * @author Grzegorz Grzybek
 */
public class JwsJaxbUnmarshaller implements Unmarshaller {

	/**
	 * @param jwsJaxbContext
	 */
	JwsJaxbUnmarshaller(JwsJaxbContext jwsJaxbContext) {
		// TODO Auto-generated constructor stub
	}

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
		// TODO Auto-generated method stub
		return null;
	}

	/* (non-Javadoc)
	 * @see javax.xml.bind.Unmarshaller#unmarshal(javax.xml.stream.XMLEventReader, java.lang.Class)
	 */
	@Override
	public <T> JAXBElement<T> unmarshal(XMLEventReader reader, Class<T> declaredType) throws JAXBException {
		// TODO Auto-generated method stub
		return null;
	}

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
		// TODO Auto-generated method stub

	}

	/* (non-Javadoc)
	 * @see javax.xml.bind.Unmarshaller#isValidating()
	 */
	@Override
	public boolean isValidating() throws JAXBException {
		// TODO Auto-generated method stub
		return false;
	}

	/* (non-Javadoc)
	 * @see javax.xml.bind.Unmarshaller#setEventHandler(javax.xml.bind.ValidationEventHandler)
	 */
	@Override
	public void setEventHandler(ValidationEventHandler handler) throws JAXBException {
		// TODO Auto-generated method stub

	}

	/* (non-Javadoc)
	 * @see javax.xml.bind.Unmarshaller#getEventHandler()
	 */
	@Override
	public ValidationEventHandler getEventHandler() throws JAXBException {
		// TODO Auto-generated method stub
		return null;
	}

	/* (non-Javadoc)
	 * @see javax.xml.bind.Unmarshaller#setProperty(java.lang.String, java.lang.Object)
	 */
	@Override
	public void setProperty(String name, Object value) throws PropertyException {
		// TODO Auto-generated method stub

	}

	/* (non-Javadoc)
	 * @see javax.xml.bind.Unmarshaller#getProperty(java.lang.String)
	 */
	@Override
	public Object getProperty(String name) throws PropertyException {
		// TODO Auto-generated method stub
		return null;
	}

	/* (non-Javadoc)
	 * @see javax.xml.bind.Unmarshaller#setSchema(javax.xml.validation.Schema)
	 */
	@Override
	public void setSchema(Schema schema) {
		// TODO Auto-generated method stub

	}

	/* (non-Javadoc)
	 * @see javax.xml.bind.Unmarshaller#getSchema()
	 */
	@Override
	public Schema getSchema() {
		// TODO Auto-generated method stub
		return null;
	}

	/* (non-Javadoc)
	 * @see javax.xml.bind.Unmarshaller#setAdapter(javax.xml.bind.annotation.adapters.XmlAdapter)
	 */
	@Override
	public void setAdapter(XmlAdapter adapter) {
		// TODO Auto-generated method stub

	}

	/* (non-Javadoc)
	 * @see javax.xml.bind.Unmarshaller#setAdapter(java.lang.Class, javax.xml.bind.annotation.adapters.XmlAdapter)
	 */
	@Override
	public <A extends XmlAdapter> void setAdapter(Class<A> type, A adapter) {
		// TODO Auto-generated method stub

	}

	/* (non-Javadoc)
	 * @see javax.xml.bind.Unmarshaller#getAdapter(java.lang.Class)
	 */
	@Override
	public <A extends XmlAdapter> A getAdapter(Class<A> type) {
		// TODO Auto-generated method stub
		return null;
	}

	/* (non-Javadoc)
	 * @see javax.xml.bind.Unmarshaller#setAttachmentUnmarshaller(javax.xml.bind.attachment.AttachmentUnmarshaller)
	 */
	@Override
	public void setAttachmentUnmarshaller(AttachmentUnmarshaller au) {
		// TODO Auto-generated method stub

	}

	/* (non-Javadoc)
	 * @see javax.xml.bind.Unmarshaller#getAttachmentUnmarshaller()
	 */
	@Override
	public AttachmentUnmarshaller getAttachmentUnmarshaller() {
		// TODO Auto-generated method stub
		return null;
	}

	/* (non-Javadoc)
	 * @see javax.xml.bind.Unmarshaller#setListener(javax.xml.bind.Unmarshaller.Listener)
	 */
	@Override
	public void setListener(Listener listener) {
		// TODO Auto-generated method stub

	}

	/* (non-Javadoc)
	 * @see javax.xml.bind.Unmarshaller#getListener()
	 */
	@Override
	public Listener getListener() {
		// TODO Auto-generated method stub
		return null;
	}

}
