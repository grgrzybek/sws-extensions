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

import java.util.Map;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBElement;
import javax.xml.bind.JAXBException;
import javax.xml.namespace.QName;

import org.junit.Test;
import org.springframework.test.util.ReflectionTestUtils;
import org.springframework.ws.ext.bind.internal.model.XmlEventsPattern;

import static org.junit.Assert.*;

/**
 * <p></p>
 *
 * @author Grzegorz Grzybek
 */
public class JwsJaxbContextFactoryTest {

	@Test(expected = JAXBException.class)
	public void illegalPackageForJaxbRi() throws Exception {
		JAXBContext.newInstance("a.b.c");
	}

	@Test
	public void nonExistingPackage() throws Exception {
		JwsJaxbContextFactory.createContext("a.b.c", null);
	}

	@Test
	public void noPackage() throws Exception {
		JAXBContext ctx = JwsJaxbContextFactory.createContext("", null);
		// but we may marshall objects of built-in classes - XSD simple types
		ctx.createMarshaller().marshal(new JAXBElement<String>(new QName("urn:test", "str"), String.class, "content"), System.out);
	}
	
	@Test
	public void nestedPackageNotScanned() throws Exception {
		JAXBContext ctx = JwsJaxbContextFactory.createContext("org.springframework.ws.ext.bind.context4", null);
		@SuppressWarnings("unchecked")
		Map<String, XmlEventsPattern> patterns = (Map<String, XmlEventsPattern>) ReflectionTestUtils.getField(ctx, "patterns");
		assertTrue(patterns.containsKey(org.springframework.ws.ext.bind.context4.MyClass1.class));
		assertFalse(patterns.containsKey(org.springframework.ws.ext.bind.context4.nested.MyClass1.class));
		// not package-scanned but analyzed as a MyClass1 property as XmlAccessType.FIELD
		assertTrue(patterns.containsKey(org.springframework.ws.ext.bind.context4.nested.MyClass2.class));
	}

}
