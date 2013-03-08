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

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBElement;
import javax.xml.bind.Marshaller;
import javax.xml.namespace.QName;
import javax.xml.stream.XMLEventFactory;
import javax.xml.stream.XMLEventWriter;
import javax.xml.stream.XMLOutputFactory;

import org.codehaus.stax2.XMLOutputFactory2;
import org.junit.Before;
import org.junit.Test;
import org.springframework.ws.ext.bind.context1.ClassWithComplexContent;
import org.springframework.ws.ext.bind.context1.ClassWithVeryComplexContent;
import org.springframework.ws.ext.bind.internal.stax.IndentingXMLEventWriter;

/**
 * <p></p>
 *
 * @author Grzegorz Grzybek
 */
public class MultiRefMarshallerTest {

	private XMLOutputFactory outputFactory;
	private XMLEventFactory eventFactory;

	@Before
	public void before() throws Exception {
		this.eventFactory = XMLEventFactory.newFactory();
		this.outputFactory = XMLOutputFactory.newFactory();
		this.outputFactory.setProperty(XMLOutputFactory2.P_AUTOMATIC_EMPTY_ELEMENTS, true);
		this.outputFactory.setProperty(XMLOutputFactory.IS_REPAIRING_NAMESPACES, true);
	}

	@Test
	public void marshallVeryComplexContent() throws Exception {
		JAXBContext context = JAXBContext.newInstance("org.springframework.ws.ext.bind.context1");
		ClassWithVeryComplexContent value = new ClassWithVeryComplexContent("test", "str", new ClassWithComplexContent("test", 42, "inside"));
		Marshaller m = context.createMarshaller();
		m.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, false);
		m.setProperty(Marshaller.JAXB_FRAGMENT, true);

		XMLEventWriter writer = outputFactory.createXMLEventWriter(System.out);
		writer = new IndentingXMLEventWriter(writer);
		writer.add(this.eventFactory.createStartDocument("UTF-8", "1.0"));
		writer.add(this.eventFactory.createStartElement(new QName("urn:test:1", "root-wrapper-for-multirefs"), null, null));

		m.marshal(new JAXBElement<ClassWithVeryComplexContent>(new QName("urn:test", "root"), ClassWithVeryComplexContent.class, value), writer);

		writer.add(this.eventFactory.createEndElement(new QName("urn:test:1", "root-wrapper-for-multirefs"), null));
		writer.close();
	}

}
