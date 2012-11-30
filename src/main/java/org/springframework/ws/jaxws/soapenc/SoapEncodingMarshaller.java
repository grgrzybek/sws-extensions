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
import java.lang.reflect.Type;

import javax.xml.stream.XMLStreamException;
import javax.xml.stream.XMLStreamReader;
import javax.xml.transform.Result;
import javax.xml.transform.Source;
import javax.xml.transform.sax.SAXResult;

import org.springframework.oxm.GenericMarshaller;
import org.springframework.oxm.GenericUnmarshaller;
import org.springframework.oxm.MarshallingFailureException;
import org.springframework.oxm.UnmarshallingFailureException;
import org.springframework.oxm.XmlMappingException;
import org.springframework.oxm.mime.MimeContainer;
import org.springframework.oxm.mime.MimeMarshaller;
import org.springframework.oxm.mime.MimeUnmarshaller;
import org.springframework.util.xml.StaxUtils;
import org.xml.sax.ContentHandler;
import org.xml.sax.SAXException;
import org.xml.sax.helpers.AttributesImpl;

/**
 * <p>Marshaller which handles SOAP 1.1 and SOAP 1.2 specific encodings during XML (de)serialization.</p>
 * <p>Although it may be nice to support JAX-RPC concepts, such as {@code javax.xml.rpc.encoding.SerializationContext} or
 * {@code javax.xml.rpc.encoding.TypeMappingRegistry}, we're not trying to implement JAX-RPC specification (at least now).</p>
 *
 * @author Grzegorz Grzybek
 */
public class SoapEncodingMarshaller implements MimeMarshaller, MimeUnmarshaller, GenericMarshaller, GenericUnmarshaller {

	/* (non-Javadoc)
	 * @see org.springframework.oxm.Marshaller#supports(java.lang.Class)
	 */
	@Override
	public boolean supports(Class<?> clazz) {
		// TODO Auto-generated method stub
		return true;
	}

	/* (non-Javadoc)
	 * @see org.springframework.oxm.Marshaller#marshal(java.lang.Object, javax.xml.transform.Result)
	 */
	@Override
	public void marshal(Object graph, Result result) throws IOException, XmlMappingException {
		try {
			ContentHandler contentHandler = ((SAXResult)result).getHandler();
			contentHandler.startElement("", "hello", "hello", new AttributesImpl());
			contentHandler.characters(((String)((Object[])graph)[0]).toCharArray(), 0, ((String)((Object[])graph)[0]).toCharArray().length);
			contentHandler.endElement("", "hello", "hello");
		}
		catch (SAXException e) {
			throw new MarshallingFailureException(e.getMessage(), e);
		}
	}

	/* (non-Javadoc)
	 * @see org.springframework.oxm.Unmarshaller#unmarshal(javax.xml.transform.Source)
	 */
	@Override
	public Object unmarshal(Source source) throws IOException, XmlMappingException {
		try {
			XMLStreamReader streamReader = StaxUtils.getXMLStreamReader(source);
			streamReader.next();
			return streamReader.getElementText();
		}
		catch (XMLStreamException e) {
			throw new UnmarshallingFailureException(e.getMessage(), e);
		}
	}

	/* (non-Javadoc)
	 * @see org.springframework.oxm.GenericMarshaller#supports(java.lang.reflect.Type)
	 */
	@Override
	public boolean supports(Type genericType) {
		return true;
	}

	/* (non-Javadoc)
	 * @see org.springframework.oxm.mime.MimeUnmarshaller#unmarshal(javax.xml.transform.Source, org.springframework.oxm.mime.MimeContainer)
	 */
	@Override
	public Object unmarshal(Source source, MimeContainer mimeContainer) throws XmlMappingException, IOException {
		return this.unmarshal(source);
	}

	/* (non-Javadoc)
	 * @see org.springframework.oxm.mime.MimeMarshaller#marshal(java.lang.Object, javax.xml.transform.Result, org.springframework.oxm.mime.MimeContainer)
	 */
	@Override
	public void marshal(Object graph, Result result, MimeContainer mimeContainer) throws XmlMappingException, IOException {
		this.marshal(graph, result);
	}

}
