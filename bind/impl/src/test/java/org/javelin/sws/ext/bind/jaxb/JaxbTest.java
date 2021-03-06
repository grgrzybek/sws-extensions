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

package org.javelin.sws.ext.bind.jaxb;

import java.io.IOException;
import java.io.StringReader;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBElement;
import javax.xml.bind.Marshaller;
import javax.xml.bind.SchemaOutputResolver;
import javax.xml.namespace.QName;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.stream.XMLEventReader;
import javax.xml.stream.XMLInputFactory;
import javax.xml.stream.events.Attribute;
import javax.xml.stream.events.StartElement;
import javax.xml.transform.Result;
import javax.xml.transform.dom.DOMResult;
import javax.xml.transform.stream.StreamSource;

import org.javelin.sws.ext.bind.jaxb.context0.MyProperty1;
import org.javelin.sws.ext.bind.jaxb.context0.MyProperty2;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.core.io.ClassPathResource;
import org.w3c.dom.Document;
import org.w3c.dom.NamedNodeMap;
import org.xml.sax.InputSource;

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
		JAXBContext ctx = JAXBContext.newInstance(org.javelin.sws.ext.bind.jaxb.context2.MyClassJ2.class,
				org.javelin.sws.ext.bind.jaxb.context3a.MyClassJ3.class);

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
		JAXBContext.newInstance(org.javelin.sws.ext.bind.jaxb.context3a.MyClassJ3.class, org.javelin.sws.ext.bind.jaxb.context3b.MyClassJ3.class);
	}

	@Test
	public void unmarshalSameQNames() throws Exception {
		JAXBContext ctx = JAXBContext.newInstance(org.javelin.sws.ext.bind.jaxb.context1.MyClassJ1.class,
				org.javelin.sws.ext.bind.jaxb.context2.MyClassJ2.class);
		Marshaller m = ctx.createMarshaller();
		m.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, Boolean.TRUE);

		log.info("context1");
		org.javelin.sws.ext.bind.jaxb.context1.MyClassJ1 mc1 = new org.javelin.sws.ext.bind.jaxb.context1.MyClassJ1();
		mc1.setP(new MyProperty1());
		m.marshal(new JAXBElement<org.javelin.sws.ext.bind.jaxb.context1.MyClassJ1>(new QName("a", "a"),
				org.javelin.sws.ext.bind.jaxb.context1.MyClassJ1.class, mc1), System.out);
		log.info("context2");
		org.javelin.sws.ext.bind.jaxb.context2.MyClassJ2 mc2 = new org.javelin.sws.ext.bind.jaxb.context2.MyClassJ2();
		mc2.setP(new MyProperty2());
		m.marshal(new JAXBElement<org.javelin.sws.ext.bind.jaxb.context2.MyClassJ2>(new QName("a", "a"),
				org.javelin.sws.ext.bind.jaxb.context2.MyClassJ2.class, mc2), System.out);

		Object object = ctx.createUnmarshaller().unmarshal(
				new StreamSource(new StringReader("<?xml version=\"1.0\" encoding=\"UTF-8\" standalone=\"yes\"?>\r\n"
						+ "<ns2:a xmlns=\"urn:x\" xmlns:ns2=\"a\">\r\n" + "    <p/>\r\n" + "</ns2:a>")), org.javelin.sws.ext.bind.jaxb.context2.MyClassJ2.class);
		log.info("Class: {}", ((JAXBElement<?>) object).getValue().getClass());
	}

	@Test(expected = IllegalAnnotationsException.class)
	public void marshalMixed() throws Exception {
		JAXBContext ctx = JAXBContext.newInstance(org.javelin.sws.ext.bind.jaxb.context4.MyClassJ4.class);
		Marshaller m = ctx.createMarshaller();
		m.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, Boolean.TRUE);

		org.javelin.sws.ext.bind.jaxb.context4.MyClassJ4 mc4 = new org.javelin.sws.ext.bind.jaxb.context4.MyClassJ4();
		m.marshal(new JAXBElement<org.javelin.sws.ext.bind.jaxb.context4.MyClassJ4>(new QName("a", "a"),
				org.javelin.sws.ext.bind.jaxb.context4.MyClassJ4.class, mc4), System.out);
	}

	@Test(expected = IllegalAnnotationsException.class)
	public void marshalIllegalProperties() throws Exception {
		JAXBContext ctx = JAXBContext.newInstance(org.javelin.sws.ext.bind.jaxb.context5.MyClassJ5_1.class);
		Marshaller m = ctx.createMarshaller();
		m.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, Boolean.TRUE);

		org.javelin.sws.ext.bind.jaxb.context5.MyClassJ5_1 mc5 = new org.javelin.sws.ext.bind.jaxb.context5.MyClassJ5_1();
		mc5.setC(String.class);
		m.marshal(new JAXBElement<org.javelin.sws.ext.bind.jaxb.context5.MyClassJ5_1>(new QName("a", "a"),
				org.javelin.sws.ext.bind.jaxb.context5.MyClassJ5_1.class, mc5), System.out);
	}

	@Test
	public void marshalIllegalName() throws Exception {
		JAXBContext ctx = JAXBContext.newInstance(org.javelin.sws.ext.bind.jaxb.context1.MyClassJ1.class);
		Marshaller m = ctx.createMarshaller();
		m.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, Boolean.TRUE);

		// and I thought it's illegal...
		m.marshal(new JAXBElement<String>(new QName("a", "a a"), String.class, "hello"), System.out);
	}

	@Test
	public void namespacesOfAttributes() throws Exception {
		XMLEventReader reader = XMLInputFactory.newFactory().createXMLEventReader(new ClassPathResource("org/javelin/sws/ext/bind/jaxb/context6/1.xml").getInputStream());
		reader.nextEvent();
		StartElement start = reader.nextEvent().asStartElement();
		for (Iterator<?> it = start.getAttributes(); it.hasNext(); ) {
			System.out.println(((Attribute)it.next()).getName());
		}
		System.out.println("---");
		reader = XMLInputFactory.newFactory().createXMLEventReader(new ClassPathResource("org/javelin/sws/ext/bind/jaxb/context6/2.xml").getInputStream());
		reader.nextEvent();
		start = reader.nextEvent().asStartElement();
		for (Iterator<?> it = start.getAttributes(); it.hasNext(); ) {
			System.out.println(((Attribute)it.next()).getName());
		}
		
		System.out.println("---");
		DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();
		dbf.setNamespaceAware(true);
		Document doc1 = dbf.newDocumentBuilder().parse(new InputSource(new ClassPathResource("org/javelin/sws/ext/bind/jaxb/context6/1.xml").getInputStream()));
		NamedNodeMap attributes1 = doc1.getDocumentElement().getAttributes();
		for (int i=0; i<attributes1.getLength(); i++)
			System.out.println(((org.w3c.dom.Attr)attributes1.item(i)).getName() + " (" + ((org.w3c.dom.Attr)attributes1.item(i)).getNamespaceURI() + ")");

		System.out.println("---");
		doc1 = dbf.newDocumentBuilder().parse(new InputSource(new ClassPathResource("org/javelin/sws/ext/bind/jaxb/context6/2.xml").getInputStream()));
		attributes1 = doc1.getDocumentElement().getAttributes();
		for (int i=0; i<attributes1.getLength(); i++)
			System.out.println(((org.w3c.dom.Attr)attributes1.item(i)).getName() + " (" + ((org.w3c.dom.Attr)attributes1.item(i)).getNamespaceURI() + ")");
		
		System.out.println("---");
		doc1 = dbf.newDocumentBuilder().parse(new InputSource(new ClassPathResource("org/javelin/sws/ext/bind/jaxb/context6/3.xml").getInputStream()));
		attributes1 = doc1.getDocumentElement().getAttributes();
		for (int i=0; i<attributes1.getLength(); i++)
			System.out.println(((org.w3c.dom.Attr)attributes1.item(i)).getName() + " (" + ((org.w3c.dom.Attr)attributes1.item(i)).getNamespaceURI() + ")");
	}

}
