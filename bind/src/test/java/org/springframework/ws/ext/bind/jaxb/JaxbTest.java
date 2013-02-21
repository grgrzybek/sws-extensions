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

package org.springframework.ws.ext.bind.jaxb;

import java.io.IOException;
import java.io.StringReader;
import java.util.LinkedList;
import java.util.List;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBElement;
import javax.xml.bind.Marshaller;
import javax.xml.bind.SchemaOutputResolver;
import javax.xml.namespace.QName;
import javax.xml.transform.Result;
import javax.xml.transform.dom.DOMResult;
import javax.xml.transform.stream.StreamSource;

import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.ws.ext.bind.jaxb.context0.MyProperty1;
import org.springframework.ws.ext.bind.jaxb.context0.MyProperty2;

import com.sun.xml.bind.v2.runtime.IllegalAnnotationsException;

/**
 * <p></p>
 *
 * @author Grzegorz Grzybek
 */
public class JaxbTest {

	private static Logger log = LoggerFactory.getLogger(JaxbTest.class.getName());

	@Test
	public void generateXmlSchema() throws Exception {
		JAXBContext ctx = JAXBContext.newInstance(org.springframework.ws.ext.bind.jaxb.context2.MyClass.class,
				org.springframework.ws.ext.bind.jaxb.context3a.MyClass.class);

		final List<DOMResult> results = new LinkedList<DOMResult>();

		ctx.generateSchema(new SchemaOutputResolver() {
			@Override
			public Result createOutput(String namespaceUri, String suggestedFileName) throws IOException {
				log.info("file: {}, namespace: {}", suggestedFileName, namespaceUri);
				DOMResult result = new DOMResult();
				results.add(result);
				result.setSystemId(suggestedFileName);
				return result;
			}
		});

		for (DOMResult dr : results) {
			log.info("=== {} ===", dr.getSystemId());
			javax.xml.transform.TransformerFactory
					.newInstance()
					.newTransformer()
					.transform(new javax.xml.transform.dom.DOMSource(dr.getNode()), new javax.xml.transform.stream.StreamResult(new java.io.PrintWriter(System.out)));
		}
	}

	@Test(expected = IllegalAnnotationsException.class)
	public void conflicts() throws Exception {
		JAXBContext.newInstance(org.springframework.ws.ext.bind.jaxb.context3a.MyClass.class, org.springframework.ws.ext.bind.jaxb.context3b.MyClass.class);
	}

	@Test
	public void unmarshallSameQNames() throws Exception {
		JAXBContext ctx = JAXBContext.newInstance(org.springframework.ws.ext.bind.jaxb.context1.MyClass.class,
				org.springframework.ws.ext.bind.jaxb.context2.MyClass.class);
		Marshaller m = ctx.createMarshaller();
		m.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, Boolean.TRUE);

		log.info("context1");
		org.springframework.ws.ext.bind.jaxb.context1.MyClass mc1 = new org.springframework.ws.ext.bind.jaxb.context1.MyClass();
		mc1.setP(new MyProperty1());
		m.marshal(new JAXBElement<org.springframework.ws.ext.bind.jaxb.context1.MyClass>(new QName("a", "a"),
				org.springframework.ws.ext.bind.jaxb.context1.MyClass.class, mc1), System.out);
		log.info("context2");
		org.springframework.ws.ext.bind.jaxb.context2.MyClass mc2 = new org.springframework.ws.ext.bind.jaxb.context2.MyClass();
		mc2.setP(new MyProperty2());
		m.marshal(new JAXBElement<org.springframework.ws.ext.bind.jaxb.context2.MyClass>(new QName("a", "a"),
				org.springframework.ws.ext.bind.jaxb.context2.MyClass.class, mc2), System.out);

		Object object = ctx.createUnmarshaller().unmarshal(
				new StreamSource(new StringReader("<?xml version=\"1.0\" encoding=\"UTF-8\" standalone=\"yes\"?>\r\n"
						+ "<ns2:a xmlns=\"urn:x\" xmlns:ns2=\"a\">\r\n" + "    <p/>\r\n" + "</ns2:a>")), org.springframework.ws.ext.bind.jaxb.context2.MyClass.class);
		log.info("Class: {}", ((JAXBElement<?>) object).getValue().getClass());
	}

	@Test(expected = IllegalAnnotationsException.class)
	public void marshalMixed() throws Exception {
		JAXBContext ctx = JAXBContext.newInstance(org.springframework.ws.ext.bind.jaxb.context4.MyClass.class);
		Marshaller m = ctx.createMarshaller();
		m.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, Boolean.TRUE);

		org.springframework.ws.ext.bind.jaxb.context4.MyClass mc4 = new org.springframework.ws.ext.bind.jaxb.context4.MyClass();
		m.marshal(new JAXBElement<org.springframework.ws.ext.bind.jaxb.context4.MyClass>(new QName("a", "a"),
				org.springframework.ws.ext.bind.jaxb.context4.MyClass.class, mc4), System.out);
	}

	@Test
	public void marshalIllegalProperties() throws Exception {
		JAXBContext ctx = JAXBContext.newInstance(org.springframework.ws.ext.bind.jaxb.context5.MyClass.class);
		Marshaller m = ctx.createMarshaller();
		m.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, Boolean.TRUE);

		org.springframework.ws.ext.bind.jaxb.context5.MyClass mc5 = new org.springframework.ws.ext.bind.jaxb.context5.MyClass();
		mc5.setC(String.class);
		m.marshal(new JAXBElement<org.springframework.ws.ext.bind.jaxb.context5.MyClass>(new QName("a", "a"),
				org.springframework.ws.ext.bind.jaxb.context5.MyClass.class, mc5), System.out);
	}

	@Test
	public void marshalIllegalName() throws Exception {
		JAXBContext ctx = JAXBContext.newInstance(org.springframework.ws.ext.bind.jaxb.context1.MyClass.class);
		Marshaller m = ctx.createMarshaller();
		m.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, Boolean.TRUE);

		// and I thought it's illegal...
		m.marshal(new JAXBElement<String>(new QName("a", "a a"), String.class, "hello"), System.out);
	}

}
