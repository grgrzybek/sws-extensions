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
import java.util.HashMap;

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
import org.junit.Ignore;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.ws.ext.bind.context1.ClassWithComplexContent;
import org.springframework.ws.ext.bind.context1.ClassWithSimpleContentAndAttributes;
import org.springframework.ws.ext.bind.context1.ClassWithVeryComplexContent;
import org.springframework.ws.ext.bind.context1.MyClass1;
import org.springframework.ws.ext.bind.context2.MyClass2;
import org.springframework.ws.ext.bind.context5.C1;
import org.springframework.ws.ext.bind.context5.C2;
import org.springframework.ws.ext.bind.internal.stax.IndentingXMLEventWriter;
import org.springframework.ws.ext.bind.jaxb.context6.SingleRootElement;

import com.sun.xml.bind.marshaller.NamespacePrefixMapper;
import com.sun.xml.bind.v2.ContextFactory;

/**
 * <p></p>
 *
 * @author Grzegorz Grzybek
 */
public class SweJaxbMarshallerTest {

	private static Logger log = LoggerFactory.getLogger(SweJaxbMarshallerTest.class.getName());

	@Test(expected = JAXBException.class)
	public void marshalMyClass1() throws Exception {
		JAXBContext context = JAXBContext.newInstance("org.springframework.ws.ext.bind.context1");
		StringWriter sw = new StringWriter();
		context.createMarshaller().marshal(new MyClass1(), sw);
		log.info(sw.toString());
	}

	@Test
	public void marshalJAXBElementWithMyClass2() throws Exception {
		JAXBContext context = JAXBContext.newInstance("org.springframework.ws.ext.bind.context2");
		StringWriter sw = new StringWriter();
		MyClass2 mc2 = new MyClass2();
		context.createMarshaller().marshal(new JAXBElement<MyClass2>(new QName("urn:test", "mc"), MyClass2.class, mc2), sw);
		log.info(sw.toString());
	}

	@Test(expected = MarshalException.class)
	public void cantMarshalPrimitive() throws Exception {
		JAXBContext context = JAXBContext.newInstance("org.springframework.ws.ext.bind.context1");
		StringWriter sw = new StringWriter();
		context.createMarshaller().marshal("x", sw);
	}

	@Test
	public void marshalPrimitivesInJAXBElement() throws Exception {
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
	public void marshalPrimitivesInJAXBElementInExternalWriter() throws Exception {
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
	public void marshalComplexContent() throws Exception {
		JAXBContext context = JAXBContext.newInstance("org.springframework.ws.ext.bind.context1");
		ClassWithComplexContent value = new ClassWithComplexContent("test", 42, "inside");
		Marshaller m = context.createMarshaller();
		m.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, true);
		m.marshal(new JAXBElement<ClassWithComplexContent>(new QName("urn:test", "root"), ClassWithComplexContent.class, value), System.out);
	}

	@Test
	public void marshalVeryComplexContent() throws Exception {
		JAXBContext context = JAXBContext.newInstance("org.springframework.ws.ext.bind.context1");
		ClassWithVeryComplexContent value = new ClassWithVeryComplexContent("test-1", "str", new ClassWithComplexContent("test-2", 42, "inside"));
		Marshaller m = context.createMarshaller();
		m.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, true);
		m.marshal(new JAXBElement<ClassWithVeryComplexContent>(new QName("urn:test", "root"), ClassWithVeryComplexContent.class, value), System.out);
	}

	@Test
	public void marshalVeryComplexContentRI() throws Exception {
		JAXBContext context = ContextFactory.createContext(new Class[] { org.springframework.ws.ext.bind.jaxb.context6.ClassWithVeryComplexContent.class,
				SingleRootElement.class }, new HashMap<String, Object>());
		org.springframework.ws.ext.bind.jaxb.context6.ClassWithVeryComplexContent value = new org.springframework.ws.ext.bind.jaxb.context6.ClassWithVeryComplexContent(
				"test", "str", new org.springframework.ws.ext.bind.jaxb.context6.ClassWithComplexContent("test", 42, "inside"));
		Marshaller m = context.createMarshaller();
		m.setProperty("com.sun.xml.bind.namespacePrefixMapper", new NamespacePrefixMapper() {
			@Override
			public String getPreferredPrefix(String namespaceUri, String suggestion, boolean requirePrefix) {
				if ("urn:test".equals(namespaceUri))
					return "";
				else if ("urn:inside:!!!!!!!!".equals(namespaceUri))
					return "aaa";
				else
					return suggestion;
			}
		});
		m.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, true);
		m.marshal(new JAXBElement<org.springframework.ws.ext.bind.jaxb.context6.ClassWithVeryComplexContent>(new QName("urn:test", "root", ""),
				org.springframework.ws.ext.bind.jaxb.context6.ClassWithVeryComplexContent.class, value), System.out);
	}

	@Test
	public void marshalAllXmlnsRI() throws Exception {
		JAXBContext context = ContextFactory.createContext(new Class[] { org.springframework.ws.ext.bind.jaxb.context6.ClassWithVeryComplexContent.class,
				SingleRootElement.class }, new HashMap<String, Object>());
		Marshaller m = context.createMarshaller();
		m.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, true);
		// ha - RI blindly puts all known namespace declarations and outputs this:
		// <ns4:root xmlns="urn:inside:!!!!!!!!" xmlns:ns2="urn:inside:3" xmlns:ns3="y" xmlns:ns4="urn:test">xxx</ns4:root>
		m.marshal(new JAXBElement<String>(new QName("urn:test", "root", ""), String.class, "xxx"), System.out);
	}

	@Test
	public void marshalSimpleContentAndAttributes() throws Exception {
		JAXBContext context = JAXBContext.newInstance("org.springframework.ws.ext.bind.context1");
		ClassWithSimpleContentAndAttributes value = new ClassWithSimpleContentAndAttributes("test", 42, "inside");
		Marshaller m = context.createMarshaller();
		m.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, true);
		m.marshal(new JAXBElement<ClassWithSimpleContentAndAttributes>(new QName("urn:test", "root"), ClassWithSimpleContentAndAttributes.class, value),
				System.out);
	}

	@Test
	@Ignore
	public void marshalNestedSimpleTypes() throws Exception {
		JAXBContext context = SweJaxbContextFactory.createContext("org.springframework.ws.ext.bind.context5", null);
		// TODO: possibly multiply-nested @XmlValues are still simpleType...
		// from XSD point of view, C1 is simpleType
		C1 c1 = new C1();
		c1.setC2(new C2());
		c1.getC2().setValue("test");
		Marshaller m = context.createMarshaller();
		m.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, true);
		m.marshal(new JAXBElement<C1>(new QName("urn:test", "root"), C1.class, c1), System.out);
	}

	@Test
	public void marshalNestedSimpleTypesRi() throws Exception {
		JAXBContext context = JAXBContext.newInstance(C1.class);
		// possibly multiply-nested @XmlValues are still simpleType...
		// from XSD point of view, C1 is simpleType
		C1 c1 = new C1();
		c1.setC2(new C2());
		c1.getC2().setValue("test");
		Marshaller m = context.createMarshaller();
		m.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, true);
		m.marshal(new JAXBElement<C1>(new QName("urn:test", "root"), C1.class, c1), System.out);
	}
}
