/*
 * Copyright 2005-2012 the original author or authors.
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

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.Reader;
import java.io.Writer;

import javax.xml.stream.XMLEventReader;
import javax.xml.stream.XMLEventWriter;
import javax.xml.stream.XMLStreamReader;
import javax.xml.stream.XMLStreamWriter;

import org.springframework.oxm.XmlMappingException;
import org.springframework.oxm.support.AbstractMarshaller;
import org.w3c.dom.Node;
import org.xml.sax.ContentHandler;
import org.xml.sax.InputSource;
import org.xml.sax.XMLReader;
import org.xml.sax.ext.LexicalHandler;

/**
 * <p>Marshaller which handles SOAP 1.1 and SOAP 1.2 specific encodings during XML (de)serialization.</p>
 * <p>Although it may be nice to support JAX-RPC concepts, such as {@code javax.xml.rpc.encoding.SerializationContext} or
 * {@code javax.xml.rpc.encoding.TypeMappingRegistry}, we're not trying to implement JAX-RPC specification (at least now).</p>
 *
 * @author Grzegorz Grzybek
 */
public class SoapEncodingMarshaller extends AbstractMarshaller {

	/* (non-Javadoc)
	 * @see org.springframework.oxm.Marshaller#supports(java.lang.Class)
	 */
	@Override
	public boolean supports(Class<?> clazz) {
		// TODO Auto-generated method stub
		return false;
	}

	/* (non-Javadoc)
	 * @see org.springframework.oxm.support.AbstractMarshaller#marshalDomNode(java.lang.Object, org.w3c.dom.Node)
	 */
	@Override
	protected void marshalDomNode(Object graph, Node node) throws XmlMappingException {
		// TODO Auto-generated method stub

	}

	/* (non-Javadoc)
	 * @see org.springframework.oxm.support.AbstractMarshaller#marshalXmlEventWriter(java.lang.Object, javax.xml.stream.XMLEventWriter)
	 */
	@Override
	protected void marshalXmlEventWriter(Object graph, XMLEventWriter eventWriter) throws XmlMappingException {
		// TODO Auto-generated method stub

	}

	/* (non-Javadoc)
	 * @see org.springframework.oxm.support.AbstractMarshaller#marshalXmlStreamWriter(java.lang.Object, javax.xml.stream.XMLStreamWriter)
	 */
	@Override
	protected void marshalXmlStreamWriter(Object graph, XMLStreamWriter streamWriter) throws XmlMappingException {
		// TODO Auto-generated method stub

	}

	/* (non-Javadoc)
	 * @see org.springframework.oxm.support.AbstractMarshaller#marshalOutputStream(java.lang.Object, java.io.OutputStream)
	 */
	@Override
	protected void marshalOutputStream(Object graph, OutputStream outputStream) throws XmlMappingException, IOException {
		// TODO Auto-generated method stub

	}

	/* (non-Javadoc)
	 * @see org.springframework.oxm.support.AbstractMarshaller#marshalSaxHandlers(java.lang.Object, org.xml.sax.ContentHandler, org.xml.sax.ext.LexicalHandler)
	 */
	@Override
	protected void marshalSaxHandlers(Object graph, ContentHandler contentHandler, LexicalHandler lexicalHandler) throws XmlMappingException {
		// TODO Auto-generated method stub

	}

	/* (non-Javadoc)
	 * @see org.springframework.oxm.support.AbstractMarshaller#marshalWriter(java.lang.Object, java.io.Writer)
	 */
	@Override
	protected void marshalWriter(Object graph, Writer writer) throws XmlMappingException, IOException {
		// TODO Auto-generated method stub

	}

	/* (non-Javadoc)
	 * @see org.springframework.oxm.support.AbstractMarshaller#unmarshalDomNode(org.w3c.dom.Node)
	 */
	@Override
	protected Object unmarshalDomNode(Node node) throws XmlMappingException {
		// TODO Auto-generated method stub
		return null;
	}

	/* (non-Javadoc)
	 * @see org.springframework.oxm.support.AbstractMarshaller#unmarshalXmlEventReader(javax.xml.stream.XMLEventReader)
	 */
	@Override
	protected Object unmarshalXmlEventReader(XMLEventReader eventReader) throws XmlMappingException {
		// TODO Auto-generated method stub
		return null;
	}

	/* (non-Javadoc)
	 * @see org.springframework.oxm.support.AbstractMarshaller#unmarshalXmlStreamReader(javax.xml.stream.XMLStreamReader)
	 */
	@Override
	protected Object unmarshalXmlStreamReader(XMLStreamReader streamReader) throws XmlMappingException {
		// TODO Auto-generated method stub
		return null;
	}

	/* (non-Javadoc)
	 * @see org.springframework.oxm.support.AbstractMarshaller#unmarshalInputStream(java.io.InputStream)
	 */
	@Override
	protected Object unmarshalInputStream(InputStream inputStream) throws XmlMappingException, IOException {
		// TODO Auto-generated method stub
		return null;
	}

	/* (non-Javadoc)
	 * @see org.springframework.oxm.support.AbstractMarshaller#unmarshalReader(java.io.Reader)
	 */
	@Override
	protected Object unmarshalReader(Reader reader) throws XmlMappingException, IOException {
		// TODO Auto-generated method stub
		return null;
	}

	/* (non-Javadoc)
	 * @see org.springframework.oxm.support.AbstractMarshaller#unmarshalSaxReader(org.xml.sax.XMLReader, org.xml.sax.InputSource)
	 */
	@Override
	protected Object unmarshalSaxReader(XMLReader xmlReader, InputSource inputSource) throws XmlMappingException, IOException {
		// TODO Auto-generated method stub
		return null;
	}

}
