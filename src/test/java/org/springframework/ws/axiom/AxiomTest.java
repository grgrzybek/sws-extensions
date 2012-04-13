/*
 * Copyright 2005-2011 the original author or authors.
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

package org.springframework.ws.axiom;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Iterator;

import org.apache.axiom.om.impl.builder.StAXOMBuilder;
import org.junit.BeforeClass;
import org.junit.Test;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.Resource;
import org.springframework.ws.WebServiceMessage;
import org.springframework.ws.soap.axiom.AxiomSoapMessageFactory;
import org.springframework.ws.transport.TransportConstants;
import org.springframework.ws.transport.TransportInputStream;

import static org.junit.Assert.*;

/**
 * <p></p>
 *
 * @author Grzegorz Grzybek
 */
public class AxiomTest {

	private static Resource[] axis1SoapRequestResources;
	
	@BeforeClass
	public static void before() {
		axis1SoapRequestResources = new Resource[6];
		axis1SoapRequestResources[0] = new ClassPathResource("org/springframework/ws/soap1_1/axis1/doc-enc-req.xml");
		axis1SoapRequestResources[1] = new ClassPathResource("org/springframework/ws/soap1_1/axis1/doc-lit-req.xml");
		axis1SoapRequestResources[2] = new ClassPathResource("org/springframework/ws/soap1_1/axis1/rpc-enc-req.xml");
		axis1SoapRequestResources[3] = new ClassPathResource("org/springframework/ws/soap1_1/axis1/rpc-lit-req.xml");
		axis1SoapRequestResources[4] = new ClassPathResource("org/springframework/ws/soap1_1/axis1/wrapped-enc-req.xml");
		axis1SoapRequestResources[5] = new ClassPathResource("org/springframework/ws/soap1_1/axis1/wrapped-lit-req.xml");
	}
	
	@Test
	public void readXml() throws Exception {
		StAXOMBuilder builder = new StAXOMBuilder(new ClassPathResource("org/springframework/ws/soap1_1/axis1/doc-enc-req.xml").getInputStream());
		builder.getDocument().serialize(System.out);
	}

	@Test
	public void createWebServiceMessages() throws Exception {
		AxiomSoapMessageFactory factory = new AxiomSoapMessageFactory();
		for (Resource res: axis1SoapRequestResources) {
			WebServiceMessage msg = factory.createWebServiceMessage(new MockTransportInputStream("text/xml", res));
			assertNotNull(msg);
		}
	}

	/**
	 * <p></p>
	 *
	 * @author Grzegorz Grzybek
	 */
	private static class MockTransportInputStream extends TransportInputStream {

		private String contentType;
		private Resource resource;
		
		/**
		 * @param contentType
		 * @param resource
		 */
		public MockTransportInputStream(String contentType, Resource resource) {
			this.contentType = contentType;
			this.resource = resource;
		}

		/* (non-Javadoc)
		 * @see org.springframework.ws.transport.TransportInputStream#createInputStream()
		 */
		@Override
		protected InputStream createInputStream() throws IOException {
			return this.resource.getInputStream();
		}

		/* (non-Javadoc)
		 * @see org.springframework.ws.transport.TransportInputStream#getHeaderNames()
		 */
		@Override
		public Iterator<String> getHeaderNames() throws IOException {
			return new ArrayList<String>().iterator();
		}

		/* (non-Javadoc)
		 * @see org.springframework.ws.transport.TransportInputStream#getHeaders(java.lang.String)
		 */
		@Override
		public Iterator<String> getHeaders(String name) throws IOException {
			ArrayList<String> params = new ArrayList<String>();
			if (TransportConstants.HEADER_CONTENT_TYPE.equals(name))
				params.add(this.contentType);
			return params.iterator();
		}
	}

}
