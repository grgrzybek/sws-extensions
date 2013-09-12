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

package org.javelin.sws.ext.bind;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import javax.xml.stream.XMLStreamException;
import javax.xml.stream.XMLStreamReader;
import javax.xml.transform.Result;
import javax.xml.transform.Source;
import javax.xml.transform.sax.SAXResult;

import org.springframework.oxm.Marshaller;
import org.springframework.oxm.MarshallingFailureException;
import org.springframework.oxm.Unmarshaller;
import org.springframework.oxm.UnmarshallingFailureException;
import org.springframework.oxm.XmlMappingException;
import org.springframework.util.xml.StaxUtils;
import org.xml.sax.ContentHandler;
import org.xml.sax.SAXException;
import org.xml.sax.helpers.AttributesImpl;

/**
 * <p>Spring {@link Marshaller}/{@link Unmarshaller} which handles SOAP 1.1 and SOAP 1.2 specific encodings during XML (de)serialization.</p>
 * <p>Although it may be nice to support JAX-RPC concepts, such as {@code javax.xml.rpc.encoding.SerializationContext} or
 * {@code javax.xml.rpc.encoding.TypeMappingRegistry}, we're not trying to implement JAX-RPC specification (at least for now).</p>
 *
 * @author Grzegorz Grzybek
 */
@SuppressWarnings("unused")
public class SoapEncodingMarshaller implements Marshaller, Unmarshaller {

	/** Default {@link Marshaller} for Soap Encoding URI = {@code ""} or LITERAL encoding */
	private Marshaller defaultMarshaller;
	/** Default {@link Unmarshaller} for Soap Encoding URI = {@code ""} or LITERAL encoding */
	private Unmarshaller defaultUnmarshaller;

	/** Mapping of encoding URIs to {@link Marshaller marshallers} */
	private Map<String, Marshaller> marshallers = new HashMap<String, Marshaller>();
	/** Mapping of encoding URIs to {@link Unmarshaller unmarshallers} */
	private Map<String, Unmarshaller> unmarshallers = new HashMap<String, Unmarshaller>();

	/* (non-Javadoc)
	 * @see org.springframework.oxm.Marshaller#supports(java.lang.Class)
	 */
	@Override
	public boolean supports(Class<?> clazz) {
		return true;
	}

	/* (non-Javadoc)
	 * @see org.springframework.oxm.Marshaller#marshal(java.lang.Object, javax.xml.transform.Result)
	 */
	@Override
	public void marshal(Object graph, Result result) throws IOException, XmlMappingException {
		try {
			ContentHandler contentHandler = ((SAXResult) result).getHandler();
			contentHandler.startElement("", "hello", "hello", new AttributesImpl());
			contentHandler.characters(((String) ((Object[]) graph)[0]).toCharArray(), 0, ((String) ((Object[]) graph)[0]).toCharArray().length);
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

}
