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

package org.springframework.ws.ext.bind;

import java.util.HashMap;
import java.util.Map;

import javax.xml.bind.JAXBContext;

import org.junit.Test;
import org.springframework.test.util.ReflectionTestUtils;
import org.springframework.ws.ext.bind.internal.model.XmlEventsPattern;

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

		context = JAXBContext.newInstance(new Class<?>[] { String.class }, new HashMap<String, Object>());
		assertThat(context.getClass().getName(), equalTo(com.sun.xml.bind.v2.runtime.JAXBContextImpl.class.getName()));
	}

	@Test
	public void useJwsImplementation() throws Exception {
		JAXBContext context = JAXBContext.newInstance(org.springframework.ws.ext.bind.context1.MyClass1.class);
		assertThat(context.getClass().getName(), equalTo("org.springframework.ws.ext.bind.JwsJaxbContext"));

		context = JAXBContext.newInstance(org.springframework.ws.ext.bind.context1.MyClass1.class.getPackage().getName());
		assertThat(context.getClass().getName(), equalTo("org.springframework.ws.ext.bind.JwsJaxbContext"));

		context = JAXBContext.newInstance(new Class<?>[] { org.springframework.ws.ext.bind.context1.MyClass1.class }, new HashMap<String, Object>());
		assertThat(context.getClass().getName(), equalTo("org.springframework.ws.ext.bind.JwsJaxbContext"));

		context = JAXBContext.newInstance(org.springframework.ws.ext.bind.context1.MyClass1.class.getPackage().getName(), this.getClass().getClassLoader());
		assertThat(context.getClass().getName(), equalTo("org.springframework.ws.ext.bind.JwsJaxbContext"));

		context = JAXBContext.newInstance(org.springframework.ws.ext.bind.context1.MyClass1.class.getPackage().getName(), this.getClass().getClassLoader(),
				new HashMap<String, Object>());
		assertThat(context.getClass().getName(), equalTo("org.springframework.ws.ext.bind.JwsJaxbContext"));
	}

	@Test
	public void useBothJwsAndDefaultImplementationInTheSamePackage() throws Exception {
		// this way we can only get one (default or configured on different levels of properties) implementation - we're using
		// javax.xml.bind.ContextFinder for this and we can't change its implementation...
		JAXBContext context = JAXBContext.newInstance(org.springframework.ws.ext.bind.jaxb.context2.MyClassJ2.class);
		assertThat(context.getClass().getName(), equalTo(com.sun.xml.bind.v2.runtime.JAXBContextImpl.class.getName()));

		// but no one will forbid us to call this method :)
		context = JwsJaxbContextFactory.createContext("org.springframework.ws.jaxws.soapenc.context2", null);
		assertThat(context.getClass().getName(), equalTo("org.springframework.ws.ext.bind.JwsJaxbContext"));
	}

	@Test
	public void scanClassesOfPackage() throws Exception {
		JAXBContext context = JAXBContext.newInstance(org.springframework.ws.ext.bind.context1.MyClass1.class.getPackage().getName());
		@SuppressWarnings("unchecked")
		Map<Class<?>, Object> mapping = (Map<Class<?>, Object>) ReflectionTestUtils.getField(context, "patterns");
		assertTrue(mapping.size() > 0);
	}
	
	@Test
	public void nestedPackageNotScanned() throws Exception {
		JAXBContext ctx = JwsJaxbContextFactory.createContext("org.springframework.ws.ext.bind.context4", null);
		@SuppressWarnings("unchecked")
		Map<Class<?>, XmlEventsPattern> patterns = (Map<Class<?>, XmlEventsPattern>) ReflectionTestUtils.getField(ctx, "patterns");
		assertTrue(patterns.containsKey(org.springframework.ws.ext.bind.context4.MyClass1.class));
		assertFalse(patterns.containsKey(org.springframework.ws.ext.bind.context4.nested.MyClass1.class));
		// not package-scanned but analyzed as a MyClass1 property as XmlAccessType.FIELD
		assertTrue(patterns.containsKey(org.springframework.ws.ext.bind.context4.nested.MyClass2.class));
	}
}
