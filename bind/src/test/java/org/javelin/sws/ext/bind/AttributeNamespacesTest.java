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

package org.javelin.sws.ext.bind;

import java.io.StringWriter;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBElement;
import javax.xml.bind.Marshaller;
import javax.xml.namespace.QName;
import javax.xml.stream.XMLEventFactory;
import javax.xml.stream.XMLEventWriter;
import javax.xml.stream.XMLOutputFactory;

import org.javelin.sws.ext.bind.SweJaxbContextFactory;
import org.javelin.sws.ext.bind.internal.stax.IndentingXMLEventWriter;
import org.junit.Test;

/**
 * <p></p>
 *
 * @author Grzegorz Grzybek
 */
public class AttributeNamespacesTest {

	@Test
	public void marshalUnqualifiedAttributes() throws Exception {
		JAXBContext context = SweJaxbContextFactory.createContext("org.javelin.sws.ext.bind.context6a", null);
		org.javelin.sws.ext.bind.context6a.ClassWithAttributes value = new org.javelin.sws.ext.bind.context6a.ClassWithAttributes("test", 42);
		Marshaller m = context.createMarshaller();
		m.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, true);
		m.marshal(new JAXBElement<org.javelin.sws.ext.bind.context6a.ClassWithAttributes>(new QName("urn:test", "root"),
				org.javelin.sws.ext.bind.context6a.ClassWithAttributes.class, value), System.out);
	}

	@Test
	public void elementShouldBeQualifiedIfItsContentModelHasQualifiedAttributes() throws Exception {
		JAXBContext context = SweJaxbContextFactory.createContext("org.javelin.sws.ext.bind.context6b", null);
		org.javelin.sws.ext.bind.context6b.ClassWithAttributes value = new org.javelin.sws.ext.bind.context6b.ClassWithAttributes("test", 42);
		Marshaller m = context.createMarshaller();
		m.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, true);
		m.marshal(new JAXBElement<org.javelin.sws.ext.bind.context6b.ClassWithAttributes>(new QName("urn:test", "root", ""),
				org.javelin.sws.ext.bind.context6b.ClassWithAttributes.class, value), System.out);
	}

	@Test
	public void marshalUnqualifiedAttributesInRepairingWriter() throws Exception {
		JAXBContext context = SweJaxbContextFactory.createContext("org.javelin.sws.ext.bind.context6a", null);
		org.javelin.sws.ext.bind.context6a.ClassWithAttributes value = new org.javelin.sws.ext.bind.context6a.ClassWithAttributes("test", 42);

		StringWriter sw = new StringWriter();
		XMLEventFactory f = XMLEventFactory.newFactory();
		XMLOutputFactory outputFactory = XMLOutputFactory.newFactory();
		outputFactory.setProperty(XMLOutputFactory.IS_REPAIRING_NAMESPACES, true);
		XMLEventWriter writer = outputFactory.createXMLEventWriter(sw);
		writer = new IndentingXMLEventWriter(writer);
		writer.add(f.createStartDocument());
		Marshaller m = context.createMarshaller();
		// the writer is already indenting
		m.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, false);
		m.setProperty(Marshaller.JAXB_FRAGMENT, true);
		m.marshal(new JAXBElement<org.javelin.sws.ext.bind.context6a.ClassWithAttributes>(new QName("urn:test", "root", ""),
				org.javelin.sws.ext.bind.context6a.ClassWithAttributes.class, value), writer);
		writer.add(f.createEndDocument());
		writer.flush();
		writer.close();
		sw.flush();
		sw.close();
		System.out.println(sw.toString());
	}

	@Test
	public void elementShouldBeQualifiedIfItsContentModelHasQualifiedAttributesInRepairingWriter() throws Exception {
		JAXBContext context = SweJaxbContextFactory.createContext("org.javelin.sws.ext.bind.context6b", null);
		org.javelin.sws.ext.bind.context6b.ClassWithAttributes value = new org.javelin.sws.ext.bind.context6b.ClassWithAttributes("test", 42);

		StringWriter sw = new StringWriter();
		XMLEventFactory f = XMLEventFactory.newFactory();
		XMLOutputFactory outputFactory = XMLOutputFactory.newFactory();
		outputFactory.setProperty(XMLOutputFactory.IS_REPAIRING_NAMESPACES, true);
		XMLEventWriter writer = outputFactory.createXMLEventWriter(sw);
		writer = new IndentingXMLEventWriter(writer);
		writer.add(f.createStartDocument());
		Marshaller m = context.createMarshaller();
		// the writer is already indenting
		m.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, false);
		m.setProperty(Marshaller.JAXB_FRAGMENT, true);
		m.marshal(new JAXBElement<org.javelin.sws.ext.bind.context6b.ClassWithAttributes>(new QName("urn:test", "root", ""),
				org.javelin.sws.ext.bind.context6b.ClassWithAttributes.class, value), writer);
		writer.add(f.createEndDocument());
		writer.flush();
		writer.close();
		sw.flush();
		sw.close();
		System.out.println(sw.toString());
	}

}
