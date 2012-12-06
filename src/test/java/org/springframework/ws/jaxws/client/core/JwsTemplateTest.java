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

package org.springframework.ws.jaxws.client.core;

import java.io.Serializable;
import java.lang.reflect.Method;
import java.util.Map;

import javax.xml.ws.Service;

import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.remoting.jaxws.JaxWsPortProxyFactoryBean;
import org.springframework.test.util.ReflectionTestUtils;
import org.springframework.ws.jaxws.client.core.description.OperationDescription;
import org.springframework.ws.jaxws.matrix.Port01;
import org.springframework.ws.jaxws.matrix.Port02;
import org.springframework.ws.jaxws.matrix.Port03;
import org.springframework.ws.jaxws.matrix.Port04;
import org.springframework.ws.jaxws.soapenc.SoapEncodingMarshaller;
import org.springframework.ws.test.client.MockWebServiceServer;
import org.springframework.xml.transform.StringSource;

import static org.hamcrest.CoreMatchers.*;

import static org.junit.Assert.*;

import static org.springframework.ws.test.client.RequestMatchers.*;
import static org.springframework.ws.test.client.ResponseCreators.*;

/**
 * <p></p>
 *
 * @author Grzegorz Grzybek
 */
public class JwsTemplateTest {

	private static Logger log = LoggerFactory.getLogger(JwsTemplateTest.class.getName());

	@Test(expected = IllegalArgumentException.class)
	public void noWebServiceAnnotation() throws Exception {
		JwsTemplate<Serializable> jws = new JwsTemplate<Serializable>(Serializable.class);
		jws.afterPropertiesSet();
	}
	
	@Test
	public void webServiceAnnotation() throws Exception {
		JwsTemplate<Port01> jws = new JwsTemplate<Port01>(Port01.class);
		jws.afterPropertiesSet();
		assertThat(((Map<?, ?>)ReflectionTestUtils.getField(jws, "java2ws")).size(), equalTo(1));
	}
	
	@Test
	public void portWithParentInterface() throws Exception {
		JwsTemplate<Port02> jws = new JwsTemplate<Port02>(Port02.class);
		jws.afterPropertiesSet();
		assertThat(((Map<?, ?>)ReflectionTestUtils.getField(jws, "java2ws")).size(), equalTo(1));
	}
	
	@Test
	public void portWithThreeMethods() throws Exception {
		JwsTemplate<Port03> jws = new JwsTemplate<Port03>(Port03.class);
		jws.afterPropertiesSet();
		@SuppressWarnings("unchecked")
		Map<Method, OperationDescription> java2ws = (Map<Method, OperationDescription>)ReflectionTestUtils.getField(jws, "java2ws");
		assertThat(java2ws.size(), equalTo(3));
		for (OperationDescription desc: java2ws.values())
			log.info("Description: {}", desc.toString());
	}
	
	@Test(expected = IllegalArgumentException.class)
	public void portWithTwoMethodsWithSameName() throws Exception {
		JwsTemplate<Port04> jws = new JwsTemplate<Port04>(Port04.class);
		jws.afterPropertiesSet();
	}

	/**
	 * This test shows how proxy for WebService may be created without JAX-WS implementation (using only annotations on Service Endpoint Interface)
	 * 
	 * @throws Exception
	 */
	@Test
	public void simpliestJwsTemplateCreation() throws Exception {
		JwsTemplate<Port01> jws = new JwsTemplate<Port01>(Port01.class);
		jws.getWebServiceTemplate().setDefaultUri("http://localhost");
		jws.afterPropertiesSet();

		Port01 proxy = jws.getObject();
		String result = proxy.hello("Hello!");
		assertThat(result, equalTo("Hello!"));
	}

	/**
	 * This test fail if there's no JAX-WS implementation on classpath, as it uses JAX-WS {@link Service#create(javax.xml.namespace.QName)} method.
	 * 
	 * @throws Exception
	 */
	@Test
	public void jaxWsPortProxy() throws Exception {
		JaxWsPortProxyFactoryBean fb = new JaxWsPortProxyFactoryBean();
		fb.setServiceInterface(Port01.class);
		fb.setServiceName("my-service");
		fb.afterPropertiesSet();
		Port01 service1 = fb.createJaxWsService().getPort(Port01.class);
		Port01 service2 = (Port01) fb.getObject();
		assertNotNull(service1);
		assertNotNull(service2);
	}

	@Test
	public void simpliestMessage() throws Exception {
		JwsTemplate<Port01> jws = new JwsTemplate<Port01>(Port01.class);
		jws.getWebServiceTemplate().setDefaultUri("http://localhost");
		SoapEncodingMarshaller marshaller = new SoapEncodingMarshaller();
		jws.setSoapEncodingMarshaller(marshaller);
		jws.afterPropertiesSet();
		MockWebServiceServer server = MockWebServiceServer.createServer(jws.getWebServiceTemplate());

		String request = "<SOAP-ENV:Envelope xmlns:SOAP-ENV=\"http://schemas.xmlsoap.org/soap/envelope/\" xmlns:xsi=\"http://www.w3.org/1999/XMLSchema-instance\">\n" + 
				"	<SOAP-ENV:Body>\n" +
				"		<hello xmlns=\"\">\n" +
				"			Hello!\n" +
				"		</hello>\n" + 
				"	</SOAP-ENV:Body>\n" + 
				"</SOAP-ENV:Envelope>";
		String response = "<SOAP-ENV:Envelope xmlns:SOAP-ENV=\"http://schemas.xmlsoap.org/soap/envelope/\" xmlns:xsi=\"http://www.w3.org/1999/XMLSchema-instance\">\n" + 
				"	<SOAP-ENV:Body>\n" + 
				"		<helloResponse xmlns=\"\">\n" +
				"			Hello!\n" +
				"		</helloResponse>\n" + 
				"	</SOAP-ENV:Body>\n" + 
				"</SOAP-ENV:Envelope>";
		server.expect(soapEnvelope(new StringSource(request))).andRespond(withSoapEnvelope(new StringSource(response)));

		Port01 proxy = jws.getObject();
		String result = proxy.hello("Hello!");
		assertThat(result.trim(), equalTo("Hello!"));

		server.verify();
	}
}
