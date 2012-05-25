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

import javax.xml.ws.Service;

import org.junit.Test;
import org.springframework.remoting.jaxws.JaxWsPortProxyFactoryBean;
import org.springframework.ws.jaxws.matrix.Port01;

import static org.hamcrest.CoreMatchers.*;

import static org.junit.Assert.*;

/**
 * <p></p>
 *
 * @author Grzegorz Grzybek
 */
public class JwsTemplateTest {

	/**
	 * This test shows how proxy for WebService may be created without JAX-WS implementation (using only annotations on Service Endpoint Interface)
	 * 
	 * @throws Exception
	 */
	@Test
	public void simpliestJwsTemplateCreation() throws Exception {
		JwsTemplate<Port01> jws = new JwsTemplate<Port01>(Port01.class);
		jws.setDefaultUri("http://localhost");
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

}
