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

package org.javelin.sws.ext.bind.simpletypes;

import java.io.StringWriter;

import javax.xml.XMLConstants;
import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBElement;
import javax.xml.bind.Marshaller;
import javax.xml.namespace.QName;
import javax.xml.stream.XMLEventFactory;
import javax.xml.stream.XMLEventWriter;
import javax.xml.stream.XMLOutputFactory;

import org.javelin.sws.ext.bind.SweJaxbConstants;
import org.javelin.sws.ext.bind.SweJaxbContextFactory;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.*;

/**
 * <p></p>
 *
 * @author Grzegorz Grzybek
 */
public class MarshallingStringsAndNamespacesTest {

	private Marshaller m;
	private StringWriter sw;

	@Before
	public void before() throws Exception {
		JAXBContext context = SweJaxbContextFactory.createContext("", null);
		this.m = context.createMarshaller();
		this.m.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, false);
		this.m.setProperty(Marshaller.JAXB_FRAGMENT, true);
		this.m.setProperty(SweJaxbConstants.SWE_MARSHALLER_PROPERTY_SEND_TYPES, true);
		this.sw = new StringWriter();
	}

	@Test
	public void marshalStringAutomaticPrefixesEmptyRootPrefix() throws Exception {
		String result = "<s xmlns=\"urn:test\" xmlns:xs=\"http://www.w3.org/2001/XMLSchema\" xmlns:xsi=\"http://www.w3.org/2001/XMLSchema-instance\" xsi:type=\"xs:string\">s</s>";
		this.m.marshal(new JAXBElement<String>(new QName("urn:test", "s"), String.class, "s"), this.sw);

		System.out.println(this.sw.toString());
		assertEquals(result, this.sw.toString());
	}

	@Test
	public void marshalStringAutomaticPrefixesNonEmptyRootPrefix() throws Exception {
		String result = "<x:s xmlns:x=\"urn:test\" xmlns:xs=\"http://www.w3.org/2001/XMLSchema\" xmlns:xsi=\"http://www.w3.org/2001/XMLSchema-instance\" xsi:type=\"xs:string\">s</x:s>";
		this.m.marshal(new JAXBElement<String>(new QName("urn:test", "s", "x"), String.class, "s"), this.sw);

		System.out.println(this.sw.toString());
		assertEquals(result, this.sw.toString());
	}

	@Test
	public void marshalStringRootElementWithXsPrefix() throws Exception {
		String result = "<xs:s xmlns:xs=\"urn:test\" xmlns:ns1=\"http://www.w3.org/2001/XMLSchema\" xmlns:xsi=\"http://www.w3.org/2001/XMLSchema-instance\" xsi:type=\"ns1:string\">s</xs:s>";
		this.m.marshal(new JAXBElement<String>(new QName("urn:test", "s", "xs"), String.class, "s"), this.sw);

		System.out.println(this.sw.toString());
		assertEquals(result, this.sw.toString());
	}

	@Test
	public void marshalStringRootElementWithXsiPrefix() throws Exception {
		String result = "<xsi:s xmlns:xsi=\"urn:test\" xmlns:xs=\"http://www.w3.org/2001/XMLSchema\" xmlns:ns1=\"http://www.w3.org/2001/XMLSchema-instance\" ns1:type=\"xs:string\">s</xsi:s>";
		this.m.marshal(new JAXBElement<String>(new QName("urn:test", "s", "xsi"), String.class, "s"), this.sw);

		System.out.println(this.sw.toString());
		assertEquals(result, this.sw.toString());
	}

	@Test
	public void marshalNullRootElementWithXsiPrefix() throws Exception {
		String result = "<xsi:s xmlns:xsi=\"urn:test\" xmlns:xs=\"http://www.w3.org/2001/XMLSchema\" xmlns:ns1=\"http://www.w3.org/2001/XMLSchema-instance\" ns1:type=\"xs:string\" ns1:nil=\"true\"/>";
		this.m.marshal(new JAXBElement<String>(new QName("urn:test", "s", "xsi"), String.class, null), this.sw);

		System.out.println(this.sw.toString());
		assertEquals(result, this.sw.toString());
	}

	@Test
	public void marshalStringRootElementWithXsiPrefixXsPrefixAlsoUsed() throws Exception {
		String result = "<xs:root xmlns:xs=\"urn:xs\"><xsi:s xmlns:xsi=\"urn:test\" xmlns:ns1=\"http://www.w3.org/2001/XMLSchema\" xmlns:ns2=\"http://www.w3.org/2001/XMLSchema-instance\" ns2:type=\"ns1:string\">s</xsi:s></xs:root>";
		XMLOutputFactory f = XMLOutputFactory.newFactory();
		f.setProperty(XMLOutputFactory.IS_REPAIRING_NAMESPACES, true);
		XMLEventWriter writer = f.createXMLEventWriter(this.sw);
		XMLEventFactory ef = XMLEventFactory.newFactory();
		writer.add(ef.createStartElement("xs", "urn:xs", "root"));
		this.m.marshal(new JAXBElement<String>(new QName("urn:test", "s", "xsi"), String.class, "s"), writer);
		writer.add(ef.createEndElement("xs", "urn:xs", "root"));
		writer.flush();

		System.out.println(this.sw.toString());
		assertEquals(result, this.sw.toString());
	}

	@Test
	public void marshalStringRootElementWithXsiPrefixXsAsDefaultPrefix() throws Exception {
		String result = "<xs:root xmlns:xs=\"urn:xs\" xmlns=\"http://www.w3.org/2001/XMLSchema\"><xsi:s xmlns:xsi=\"urn:test\" xmlns:ns1=\"http://www.w3.org/2001/XMLSchema-instance\" ns1:type=\"string\">s</xsi:s></xs:root>";
		XMLOutputFactory f = XMLOutputFactory.newFactory();
		f.setProperty(XMLOutputFactory.IS_REPAIRING_NAMESPACES, true);
		XMLEventWriter writer = f.createXMLEventWriter(this.sw);
		XMLEventFactory ef = XMLEventFactory.newFactory();
		writer.add(ef.createStartElement("xs", "urn:xs", "root"));
		writer.add(ef.createNamespace(XMLConstants.W3C_XML_SCHEMA_NS_URI));
		this.m.marshal(new JAXBElement<String>(new QName("urn:test", "s", "xsi"), String.class, "s"), writer);
		writer.add(ef.createEndElement("xs", "urn:xs", "root"));
		writer.flush();

		System.out.println(this.sw.toString());
		assertEquals(result, this.sw.toString());
	}

	@Test
	public void marshalStringRootElementWithXsiPrefixXsAsCustomPrefix() throws Exception {
		String result = "<r:root xmlns:r=\"urn:xs\" xmlns:a=\"http://www.w3.org/2001/XMLSchema\"><xsi:s xmlns:xsi=\"urn:test\" xmlns:ns1=\"http://www.w3.org/2001/XMLSchema-instance\" ns1:type=\"a:string\">s</xsi:s></r:root>";
		XMLOutputFactory f = XMLOutputFactory.newFactory();
		XMLEventWriter writer = f.createXMLEventWriter(this.sw);
		XMLEventFactory ef = XMLEventFactory.newFactory();
		writer.add(ef.createStartElement("r", "urn:xs", "root"));
		writer.add(ef.createNamespace("r", "urn:xs")); // because writer doesn't repair namespaces
		writer.add(ef.createNamespace("a", XMLConstants.W3C_XML_SCHEMA_NS_URI));
		this.m.marshal(new JAXBElement<String>(new QName("urn:test", "s", "xsi"), String.class, "s"), writer);
		writer.add(ef.createEndElement("r", "urn:xs", "root"));
		writer.flush();

		System.out.println(this.sw.toString());
		assertEquals(result, this.sw.toString());
	}

	@Test
	public void marshalStringRootElementWithDefaultConflictingPrefixNonRepairing() throws Exception {
		String result = "<root xmlns=\"urn:1\"><ns1:s xmlns:ns1=\"urn:2\" xmlns:xs=\"http://www.w3.org/2001/XMLSchema\" xmlns:xsi=\"http://www.w3.org/2001/XMLSchema-instance\" xsi:type=\"xs:string\">s</ns1:s></root>";
		XMLEventWriter writer = XMLOutputFactory.newFactory().createXMLEventWriter(this.sw);
		XMLEventFactory ef = XMLEventFactory.newFactory();
		writer.add(ef.createStartElement("", "urn:1", "root"));
		writer.add(ef.createNamespace("urn:1"));
		this.m.marshal(new JAXBElement<String>(new QName("urn:2", "s", ""), String.class, "s"), writer);
		writer.add(ef.createEndElement("", "urn:1", "root"));
		writer.flush();

		System.out.println(this.sw.toString());
		assertEquals(result, this.sw.toString());
	}
	
	@Test
	public void marshalStringRootElementWithNs1PrefixWhichConflictsWithPrefixGeneratorNonRepairing() throws Exception {
		String result = "<ns1:root xmlns:ns1=\"urn:1\"><ns2:s xmlns:ns2=\"urn:2\" xmlns:xs=\"http://www.w3.org/2001/XMLSchema\" xmlns:xsi=\"http://www.w3.org/2001/XMLSchema-instance\" xsi:type=\"xs:string\">s</ns2:s></ns1:root>";
		XMLEventWriter writer = XMLOutputFactory.newFactory().createXMLEventWriter(this.sw);
		XMLEventFactory ef = XMLEventFactory.newFactory();
		writer.add(ef.createStartElement("ns1", "urn:1", "root"));
		writer.add(ef.createNamespace("ns1", "urn:1"));
		this.m.marshal(new JAXBElement<String>(new QName("urn:2", "s", "ns1"), String.class, "s"), writer);
		writer.add(ef.createEndElement("ns1", "urn:1", "root"));
		writer.flush();
		
		System.out.println(this.sw.toString());
		assertEquals(result, this.sw.toString());
	}
	
	@Test
	public void marshalStringRootElementWithDefaultConflictingPrefixRepairing() throws Exception {
		String result = "<root xmlns=\"urn:1\"><ns1:s xmlns:ns1=\"urn:2\" xmlns:xs=\"http://www.w3.org/2001/XMLSchema\" xmlns:xsi=\"http://www.w3.org/2001/XMLSchema-instance\" xsi:type=\"xs:string\">s</ns1:s></root>";
		XMLOutputFactory f = XMLOutputFactory.newFactory();
		f.setProperty(XMLOutputFactory.IS_REPAIRING_NAMESPACES, true);
		XMLEventWriter writer = f.createXMLEventWriter(this.sw);
		XMLEventFactory ef = XMLEventFactory.newFactory();
		writer.add(ef.createStartElement("", "urn:1", "root"));
		this.m.marshal(new JAXBElement<String>(new QName("urn:2", "s", ""), String.class, "s"), writer);
		writer.add(ef.createEndElement("", "urn:1", "root"));
		writer.flush();
		
		System.out.println(this.sw.toString());
		assertEquals(result, this.sw.toString());
	}
	
	@Test
	public void marshalStringRootElementWithNs1PrefixWhichConflictsWithPrefixGeneratorRepairing() throws Exception {
		String result = "<ns1:root xmlns:ns1=\"urn:1\"><ns2:s xmlns:ns2=\"urn:2\" xmlns:xs=\"http://www.w3.org/2001/XMLSchema\" xmlns:xsi=\"http://www.w3.org/2001/XMLSchema-instance\" xsi:type=\"xs:string\">s</ns2:s></ns1:root>";
		XMLOutputFactory f = XMLOutputFactory.newFactory();
		f.setProperty(XMLOutputFactory.IS_REPAIRING_NAMESPACES, true);
		XMLEventWriter writer = f.createXMLEventWriter(this.sw);
		XMLEventFactory ef = XMLEventFactory.newFactory();
		writer.add(ef.createStartElement("ns1", "urn:1", "root"));
		this.m.marshal(new JAXBElement<String>(new QName("urn:2", "s", "ns1"), String.class, "s"), writer);
		writer.add(ef.createEndElement("ns1", "urn:1", "root"));
		writer.flush();
		
		System.out.println(this.sw.toString());
		assertEquals(result, this.sw.toString());
	}

}
