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

import java.io.StringWriter;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBElement;
import javax.xml.bind.JAXBException;
import javax.xml.bind.MarshalException;
import javax.xml.bind.Marshaller;
import javax.xml.namespace.QName;
import javax.xml.stream.XMLEventFactory;
import javax.xml.stream.XMLEventWriter;
import javax.xml.stream.XMLOutputFactory;

import org.codehaus.stax2.XMLOutputFactory2;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.ws.ext.bind.context1.ClassWithAttributes;
import org.springframework.ws.ext.bind.context1.ClassWithComplexContent;
import org.springframework.ws.ext.bind.context1.ClassWithSimpleContentAndAttributes;
import org.springframework.ws.ext.bind.context1.ClassWithVeryComplexContent;
import org.springframework.ws.ext.bind.context1.MyClass1;
import org.springframework.ws.ext.bind.context2.MyClass2;
import org.springframework.ws.ext.bind.internal.stax.IndentingXMLEventWriter;

/**
 * <p></p>
 *
 * @author Grzegorz Grzybek
 */
public class JwsJaxbMarshallerTest {

	private static Logger log = LoggerFactory.getLogger(JwsJaxbMarshallerTest.class.getName());

	@Test(expected = JAXBException.class)
	public void marshallMyClass1() throws Exception {
		JAXBContext context = JAXBContext.newInstance("org.springframework.ws.ext.bind.context1");
		StringWriter sw = new StringWriter();
		context.createMarshaller().marshal(new MyClass1(), sw);
		log.info(sw.toString());
	}

	@Test(expected = JAXBException.class)
	public void marshallMyClass2() throws Exception {
		JAXBContext context = JAXBContext.newInstance("org.springframework.ws.ext.bind.context2");
		StringWriter sw = new StringWriter();
		context.createMarshaller().marshal(new MyClass2(), sw);
		log.info(sw.toString());
	}

	@Test
	public void marshallJAXBElementWithMyClass2() throws Exception {
		JAXBContext context = JAXBContext.newInstance("org.springframework.ws.ext.bind.context2");
		StringWriter sw = new StringWriter();
		MyClass2 mc2 = new MyClass2();
		context.createMarshaller().marshal(new JAXBElement<MyClass2>(new QName("urn:test", "mc"), MyClass2.class, mc2), sw);
		log.info(sw.toString());
	}

	@Test(expected = MarshalException.class)
	public void cantMarshallPrimitive() throws Exception {
		JAXBContext context = JAXBContext.newInstance("org.springframework.ws.ext.bind.context1");
		StringWriter sw = new StringWriter();
		context.createMarshaller().marshal((short) 1, sw);
	}

	@Test
	public void marshallPrimitivesInJAXBElement() throws Exception {
		JAXBContext context = JAXBContext.newInstance("org.springframework.ws.ext.bind.context1");

		log.info("non-null boolean");
		StringWriter sw = new StringWriter();
		context.createMarshaller().marshal(new JAXBElement<Boolean>(new QName("urn:test", "boolean"), Boolean.class, true), sw);
		log.info(sw.toString());
		log.info("null boolean");
		sw = new StringWriter();
		context.createMarshaller().marshal(new JAXBElement<Boolean>(new QName("urn:test", "boolean"), Boolean.class, null), sw);
		log.info(sw.toString());

		log.info("non-null byte");
		sw = new StringWriter();
		context.createMarshaller().marshal(new JAXBElement<Byte>(new QName("urn:test", "byte"), Byte.class, (byte) 1), sw);
		log.info(sw.toString());
		log.info("null byte");
		sw = new StringWriter();
		context.createMarshaller().marshal(new JAXBElement<Byte>(new QName("urn:test", "byte"), Byte.class, null), sw);
		log.info(sw.toString());

		log.info("non-null short");
		sw = new StringWriter();
		context.createMarshaller().marshal(new JAXBElement<Short>(new QName("urn:test", "short"), Short.class, (short) 1), sw);
		log.info(sw.toString());
		log.info("null short");
		sw = new StringWriter();
		context.createMarshaller().marshal(new JAXBElement<Short>(new QName("urn:test", "short"), Short.class, null), sw);
		log.info(sw.toString());

		log.info("non-null string");
		sw = new StringWriter();
		context.createMarshaller().marshal(new JAXBElement<String>(new QName("urn:test", "string"), String.class, "test"), sw);
		log.info(sw.toString());
		log.info("null string");
		sw = new StringWriter();
		context.createMarshaller().marshal(new JAXBElement<String>(new QName("urn:test", "string"), String.class, null), sw);
		log.info(sw.toString());

		log.info("non-null long");
		sw = new StringWriter();
		context.createMarshaller().marshal(new JAXBElement<Long>(new QName("urn:test", "long"), Long.class, 1L), sw);
		log.info(sw.toString());
		log.info("null long");
		sw = new StringWriter();
		context.createMarshaller().marshal(new JAXBElement<Long>(new QName("urn:test", "long"), Long.class, null), sw);
		log.info(sw.toString());

		log.info("non-null float");
		sw = new StringWriter();
		context.createMarshaller().marshal(new JAXBElement<Float>(new QName("urn:test", "float"), Float.class, 1f), sw);
		log.info(sw.toString());
		log.info("null float");
		sw = new StringWriter();
		context.createMarshaller().marshal(new JAXBElement<Float>(new QName("urn:test", "float"), Float.class, null), sw);
		log.info(sw.toString());

		log.info("non-null double");
		sw = new StringWriter();
		context.createMarshaller().marshal(new JAXBElement<Double>(new QName("urn:test", "double"), Double.class, 1d), sw);
		log.info(sw.toString());
		log.info("null double");
		sw = new StringWriter();
		context.createMarshaller().marshal(new JAXBElement<Double>(new QName("urn:test", "double"), Double.class, null), sw);
		log.info(sw.toString());
	}

	@Test
	public void marshallPrimitivesInJAXBElementInExternalWriter() throws Exception {
		JAXBContext context = JAXBContext.newInstance("org.springframework.ws.ext.bind.context1");
		StringWriter sw = new StringWriter();
		XMLEventFactory f = XMLEventFactory.newFactory();
		XMLOutputFactory outputFactory = XMLOutputFactory.newFactory();
		outputFactory.setProperty(XMLOutputFactory2.P_AUTOMATIC_EMPTY_ELEMENTS, true);
		outputFactory.setProperty(XMLOutputFactory.IS_REPAIRING_NAMESPACES, false);
		XMLEventWriter writer = outputFactory.createXMLEventWriter(sw);
		writer = new IndentingXMLEventWriter(writer);
		writer.setPrefix("x", "y");
		writer.add(f.createStartDocument("UTF-16LE", "1.0"));
		writer.add(f.createStartElement(new QName("urn:x", "root"), null, null));
		// writer.add(f.createNamespace("urn:x"));
		// writer.add(f.createNamespace("t", "urn:test"));
		// writer.add(f.createNamespace("xsi", QNames.XSI_NIL.getNamespaceURI()));
		Marshaller m = context.createMarshaller();
		// the writer is already indenting
		m.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, false);
		m.setProperty(Marshaller.JAXB_FRAGMENT, true);
		m.marshal(new JAXBElement<Short>(new QName("urn:test", "short"), Short.class, (short) 1), writer);
		m.marshal(new JAXBElement<Short>(new QName("urn:test", "short"), Short.class, null), writer);
		writer.add(f.createEndElement(new QName("urn:x", "root"), null));
		writer.add(f.createEndDocument());
		writer.flush();
		writer.close();
		sw.flush();
		sw.close();
		log.info("\n===>\n{}<===", sw.toString());
	}

	@Test
	public void marshallAttributes() throws Exception {
		JAXBContext context = JAXBContext.newInstance("org.springframework.ws.ext.bind.context1");
		ClassWithAttributes value = new ClassWithAttributes("test", 42);
		Marshaller m = context.createMarshaller();
		m.marshal(new JAXBElement<ClassWithAttributes>(new QName("urn:test", "root"), ClassWithAttributes.class, value), System.out);
	}

	@Test
	public void marshallComplexContent() throws Exception {
		JAXBContext context = JAXBContext.newInstance("org.springframework.ws.ext.bind.context1");
		ClassWithComplexContent value = new ClassWithComplexContent("test", 42, "inside");
		Marshaller m = context.createMarshaller();
		m.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, true);
		m.marshal(new JAXBElement<ClassWithComplexContent>(new QName("urn:test", "root"), ClassWithComplexContent.class, value), System.out);
	}
	
	@Test
	public void marshallVeryComplexContent() throws Exception {
		JAXBContext context = JAXBContext.newInstance("org.springframework.ws.ext.bind.context1");
		ClassWithVeryComplexContent value = new ClassWithVeryComplexContent("test", "str", new ClassWithComplexContent("test", 42, "inside"));
		Marshaller m = context.createMarshaller();
		m.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, true);
		m.marshal(new JAXBElement<ClassWithVeryComplexContent>(new QName("urn:test", "root"), ClassWithVeryComplexContent.class, value), System.out);
	}

	@Test
	public void marshallSimpleContentAndAttributes() throws Exception {
		JAXBContext context = JAXBContext.newInstance("org.springframework.ws.ext.bind.context1");
		ClassWithSimpleContentAndAttributes value = new ClassWithSimpleContentAndAttributes("test", 42, "inside");
		Marshaller m = context.createMarshaller();
		m.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, true);
		m.marshal(new JAXBElement<ClassWithSimpleContentAndAttributes>(new QName("urn:test", "root"), ClassWithSimpleContentAndAttributes.class, value),
				System.out);
	}
}
