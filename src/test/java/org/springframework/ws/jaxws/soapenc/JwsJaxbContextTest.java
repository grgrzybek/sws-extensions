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

import java.util.HashMap;

import javax.xml.bind.JAXBContext;

import org.junit.Test;
import org.springframework.ws.jaxws.soapenc.context1.MyClass;

import static org.hamcrest.CoreMatchers.*;

import static org.junit.Assert.*;

/**
 * <p></p>
 *
 * @author Grzegorz Grzybek
 */
public class JwsJaxbContextTest {

	@Test
	public void useDefaultImplementation() throws Exception {
		JAXBContext context = JAXBContext.newInstance(String.class);
		assertThat(context.getClass().getName(), equalTo(com.sun.xml.bind.v2.runtime.JAXBContextImpl.class.getName()));
	}

	@Test
	public void useJwsImplementation() throws Exception {
		JAXBContext context = JAXBContext.newInstance(MyClass.class);
		assertThat(context.getClass().getName(), equalTo("org.springframework.ws.jaxws.soapenc.JwsJaxbContext"));

		context = JAXBContext.newInstance(MyClass.class.getPackage().getName());
		assertThat(context.getClass().getName(), equalTo("org.springframework.ws.jaxws.soapenc.JwsJaxbContext"));

		context = JAXBContext.newInstance(MyClass.class.getPackage().getName(), this.getClass().getClassLoader());
		assertThat(context.getClass().getName(), equalTo("org.springframework.ws.jaxws.soapenc.JwsJaxbContext"));

		context = JAXBContext.newInstance(MyClass.class.getPackage().getName(), this.getClass().getClassLoader(), new HashMap<String, Object>());
		assertThat(context.getClass().getName(), equalTo("org.springframework.ws.jaxws.soapenc.JwsJaxbContext"));
	}

}
