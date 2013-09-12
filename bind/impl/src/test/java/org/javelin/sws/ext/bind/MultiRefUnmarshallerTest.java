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

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBElement;
import javax.xml.bind.Marshaller;
import javax.xml.namespace.QName;
import javax.xml.stream.XMLEventFactory;
import javax.xml.stream.XMLEventWriter;
import javax.xml.stream.XMLOutputFactory;

import org.codehaus.stax2.XMLOutputFactory2;
import org.javelin.sws.ext.bind.context1.ClassWithComplexContent;
import org.javelin.sws.ext.bind.context1.ClassWithVeryComplexContent;
import org.javelin.sws.ext.bind.context3.MyClass1;
import org.javelin.sws.ext.bind.context3.MyClass2;
import org.javelin.sws.ext.bind.context3.MyClass3;
import org.javelin.sws.ext.bind.internal.stax.IndentingXMLEventWriter;
import org.junit.Before;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * <p></p>
 *
 * @author Grzegorz Grzybek
 */
public class MultiRefUnmarshallerTest {

	private static Logger log = LoggerFactory.getLogger(MultiRefMarshallerTest.class.getName());

	private XMLOutputFactory outputFactory;
//	private XMLInputFactory inputFactory;
	private XMLEventFactory eventFactory;

	@Before
	public void before() throws Exception {
		this.eventFactory = XMLEventFactory.newFactory();
		this.outputFactory = XMLOutputFactory.newFactory();
		this.outputFactory.setProperty(XMLOutputFactory2.P_AUTOMATIC_EMPTY_ELEMENTS, true);
//		this.inputFactory = XMLInputFactory.newFactory();
	}

	@Test
	public void marshalVeryComplexContent() throws Exception {
		JAXBContext context = JAXBContext.newInstance("org.javelin.sws.ext.bind.context1");
		ClassWithVeryComplexContent value = new ClassWithVeryComplexContent("test", "str", new ClassWithComplexContent("test", 42, "inside"));
		Marshaller m = context.createMarshaller();
		m.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, false);
		m.setProperty(Marshaller.JAXB_FRAGMENT, true);
		m.setProperty(SweJaxbConstants.SWE_MARSHALLER_PROPERTY_SEND_TYPES, true);
		
		log.info("===== multi-ref =====");
		m.setProperty(SweJaxbConstants.SWE_MARSHALLER_PROPERTY_JAXB_MULTIREFS, true);

		XMLEventWriter writer = outputFactory.createXMLEventWriter(System.out);
		writer = new IndentingXMLEventWriter(writer);
		((IndentingXMLEventWriter)writer).setIndentationString("  ");
		writer.add(this.eventFactory.createStartDocument("UTF-8", "1.0"));
		writer.add(this.eventFactory.createStartElement(new QName("urn:test:1", "root-wrapper-for-multirefs", "r"), null, null));

		m.marshal(new JAXBElement<ClassWithVeryComplexContent>(new QName("urn:test", "root", "r"), ClassWithVeryComplexContent.class, value), writer);

		writer.add(this.eventFactory.createEndElement(new QName("urn:test:1", "root-wrapper-for-multirefs", "r"), null));
		writer.close();

		log.info("===== no multi-ref =====");
		m.setProperty(SweJaxbConstants.SWE_MARSHALLER_PROPERTY_JAXB_MULTIREFS, false);

		writer = outputFactory.createXMLEventWriter(System.out);
		writer = new IndentingXMLEventWriter(writer);
		((IndentingXMLEventWriter)writer).setIndentationString("  ");
		writer.add(this.eventFactory.createStartDocument("UTF-8", "1.0"));
		writer.add(this.eventFactory.createStartElement(new QName("urn:test:1", "root-wrapper", "r"), null, null));

		m.marshal(new JAXBElement<ClassWithVeryComplexContent>(new QName("urn:test", "root", "r"), ClassWithVeryComplexContent.class, value), writer);

		writer.add(this.eventFactory.createEndElement(new QName("urn:test:1", "root-wrapper", "r"), null));
		writer.close();
	}
	
	@Test
	public void marshalCrossReferences() throws Exception {
		JAXBContext context = JAXBContext.newInstance("org.javelin.sws.ext.bind.context3");
		Marshaller m = context.createMarshaller();
		m.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, false);
		m.setProperty(Marshaller.JAXB_FRAGMENT, true);
		
		log.info("===== no multi-ref 1 =====");
		m.setProperty(SweJaxbConstants.SWE_MARSHALLER_PROPERTY_JAXB_MULTIREFS, false);
		XMLEventWriter writer = outputFactory.createXMLEventWriter(System.out);
		writer = new IndentingXMLEventWriter(writer);
		((IndentingXMLEventWriter)writer).setIndentationString("  ");
		writer.add(this.eventFactory.createStartDocument("UTF-8", "1.0"));
		writer.add(this.eventFactory.createStartElement(new QName("urn:test:1", "root-wrapper-for-multirefs", "r"), null, null));
		
		MyClass1 c1 = new MyClass1();
		// do not try this at home without multiRefs...
		//c1.setOther(c1);
		
		m.marshal(new JAXBElement<MyClass1>(new QName("urn:test", "root", "r"), MyClass1.class, c1), writer);
		
		writer.add(this.eventFactory.createEndElement(new QName("urn:test:1", "root-wrapper-for-multirefs", "r"), null));
		writer.close();
		
		log.info("===== multi-ref 1 =====");
		m.setProperty(SweJaxbConstants.SWE_MARSHALLER_PROPERTY_JAXB_MULTIREFS, true);
		writer = outputFactory.createXMLEventWriter(System.out);
		writer = new IndentingXMLEventWriter(writer);
		((IndentingXMLEventWriter)writer).setIndentationString("  ");
		writer.add(this.eventFactory.createStartDocument("UTF-8", "1.0"));
		writer.add(this.eventFactory.createStartElement(new QName("urn:test:1", "root-wrapper-for-multirefs", "r"), null, null));
		
		c1 = new MyClass1();
		// dodge this!
		c1.setOther(c1);

		m.marshal(new JAXBElement<MyClass1>(new QName("urn:test", "root", "r"), MyClass1.class, c1), writer);
		
		writer.add(this.eventFactory.createEndElement(new QName("urn:test:1", "root-wrapper-for-multirefs", "r"), null));
		writer.close();
		
		log.info("===== multi-ref 2 =====");
		m.setProperty(SweJaxbConstants.SWE_MARSHALLER_PROPERTY_JAXB_MULTIREFS, true);
		writer = outputFactory.createXMLEventWriter(System.out);
		writer = new IndentingXMLEventWriter(writer);
		((IndentingXMLEventWriter)writer).setIndentationString("  ");
		writer.add(this.eventFactory.createStartDocument("UTF-8", "1.0"));
		writer.add(this.eventFactory.createStartElement(new QName("urn:test:1", "root-wrapper-for-multirefs", "r"), null, null));
		
		MyClass2 c2 = new MyClass2();
		MyClass3 c3 = new MyClass3();
		c2.setC3(c3);
		c3.setC2(c2);
		
		m.marshal(new JAXBElement<MyClass2>(new QName("urn:test", "root", "r"), MyClass2.class, c2), writer);
		
		writer.add(this.eventFactory.createEndElement(new QName("urn:test:1", "root-wrapper-for-multirefs", "r"), null));
		writer.close();
	}

}
