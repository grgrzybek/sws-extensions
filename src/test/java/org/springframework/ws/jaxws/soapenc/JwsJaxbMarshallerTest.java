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

package org.springframework.ws.jaxws.soapenc;

import java.io.StringWriter;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBElement;
import javax.xml.bind.JAXBException;
import javax.xml.bind.MarshalException;
import javax.xml.namespace.QName;
import javax.xml.stream.XMLEventFactory;
import javax.xml.stream.XMLEventWriter;
import javax.xml.stream.XMLOutputFactory;

import org.codehaus.stax2.XMLOutputFactory2;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.ws.jaxws.soapenc.context1.ClassWithAttributes;
import org.springframework.ws.jaxws.soapenc.context1.ClassWithComplexContent;
import org.springframework.ws.jaxws.soapenc.context1.MyClass;

/**
 * <p></p>
 *
 * @author Grzegorz Grzybek
 */
public class JwsJaxbMarshallerTest {

	private static Logger log = LoggerFactory.getLogger(JwsJaxbMarshallerTest.class.getName());

	@Test
	public void marshallMyClass1() throws Exception {
		JAXBContext context = JAXBContext.newInstance("org.springframework.ws.jaxws.soapenc.context1");
		StringWriter sw = new StringWriter();
		context.createMarshaller().marshal(new MyClass(), sw);
		log.info(sw.toString());
	}

	@Test(expected = JAXBException.class)
	public void marshallMyClass2() throws Exception {
		JAXBContext context = JAXBContext.newInstance("org.springframework.ws.jaxws.soapenc.context2");
		StringWriter sw = new StringWriter();
		context.createMarshaller().marshal(new org.springframework.ws.jaxws.soapenc.jaxb.context2.MyClass(), sw);
		log.info(sw.toString());
	}

	@Test(expected = MarshalException.class)
	public void cantMarshallPrimitive() throws Exception {
		JAXBContext context = JAXBContext.newInstance("org.springframework.ws.jaxws.soapenc.context1");
		StringWriter sw = new StringWriter();
		context.createMarshaller().marshal((short) 1, sw);
	}

	@Test
	public void marshallPrimitivesInJAXBElement() throws Exception {
		JAXBContext context = JAXBContext.newInstance("org.springframework.ws.jaxws.soapenc.context1");
		log.info("non-null short");
		StringWriter sw = new StringWriter();
		context.createMarshaller().marshal(new JAXBElement<Short>(new QName("urn:test", "short"), Short.class, (short) 1), sw);
		log.info(sw.toString());
		log.info("null short");
		sw = new StringWriter();
		context.createMarshaller().marshal(new JAXBElement<Short>(new QName("urn:test", "short"), Short.class, null), sw);
		log.info(sw.toString());
	}

	@Test
	public void marshallPrimitivesInJAXBElementInExternalWriter() throws Exception {
		JAXBContext context = JAXBContext.newInstance("org.springframework.ws.jaxws.soapenc.context1");
		StringWriter sw = new StringWriter();
		XMLEventFactory f = XMLEventFactory.newFactory();
		XMLOutputFactory outputFactory = XMLOutputFactory.newFactory();
		outputFactory.setProperty(XMLOutputFactory2.P_AUTOMATIC_EMPTY_ELEMENTS, true);
		outputFactory.setProperty(XMLOutputFactory.IS_REPAIRING_NAMESPACES, false);
		XMLEventWriter writer = outputFactory.createXMLEventWriter(sw);
		writer.setPrefix("x", "y");
		writer.add(f.createStartDocument());
		writer.add(f.createStartElement(new QName("urn:x", "root"), null, null));
		// writer.add(f.createNamespace("urn:x"));
		// writer.add(f.createNamespace("t", "urn:test"));
		// writer.add(f.createNamespace("xsi", QNames.XSI_NIL.getNamespaceURI()));
		context.createMarshaller().marshal(new JAXBElement<Short>(new QName("urn:test", "short"), Short.class, (short) 1), writer);
		context.createMarshaller().marshal(new JAXBElement<Short>(new QName("urn:test", "short"), Short.class, null), writer);
		writer.add(f.createEndElement(new QName("urn:x", "root"), null));
		writer.add(f.createEndDocument());
		writer.flush();
		writer.close();
		sw.flush();
		sw.close();
		log.info(sw.toString());
	}

	@Test
	public void marshallAttributes() throws Exception {
		JAXBContext context = JAXBContext.newInstance("org.springframework.ws.jaxws.soapenc.context1");
		ClassWithAttributes value = new ClassWithAttributes("test", 42);
		context.createMarshaller().marshal(new JAXBElement<ClassWithAttributes>(new QName("urn:test", "root"), ClassWithAttributes.class, value), System.out);
	}
	
	@Test
	public void marshallComplexContent() throws Exception {
		JAXBContext context = JAXBContext.newInstance("org.springframework.ws.jaxws.soapenc.context1");
		ClassWithComplexContent value = new ClassWithComplexContent("test", 42, "inside");
		context.createMarshaller().marshal(new JAXBElement<ClassWithComplexContent>(new QName("urn:test", "root"), ClassWithComplexContent.class, value), System.out);
	}
}
