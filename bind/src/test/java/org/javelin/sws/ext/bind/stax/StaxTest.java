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

package org.javelin.sws.ext.bind.stax;

import javax.xml.namespace.QName;
import javax.xml.stream.XMLEventFactory;
import javax.xml.stream.XMLEventWriter;
import javax.xml.stream.XMLOutputFactory;
import javax.xml.stream.XMLStreamWriter;
import javax.xml.transform.stax.StAXResult;

import org.codehaus.stax2.XMLOutputFactory2;
import org.junit.Test;

/**
 * <p></p>
 *
 * @author Grzegorz Grzybek
 */
public class StaxTest {

	@Test
	public void outputWoodstoxProperties() throws Exception {
		XMLOutputFactory outputFactory = XMLOutputFactory.newFactory();
		XMLEventFactory eventFactory = XMLEventFactory.newFactory();
		outputFactory.setProperty(XMLOutputFactory.IS_REPAIRING_NAMESPACES, true);
		outputFactory.setProperty(XMLOutputFactory2.P_AUTOMATIC_NS_PREFIX, "NS");
		XMLEventWriter writer = outputFactory.createXMLEventWriter(System.out);
		writer.add(eventFactory.createStartDocument("UTF-8", "1.0", true));
		writer.add(eventFactory.createStartElement(new QName("urn:1", "root", "x"), null, null));
		writer.add(eventFactory.createNamespace("y", "urn:2"));
		writer.add(eventFactory.createStartElement(new QName("urn:2", "elem", "y"), null, null));
		writer.add(eventFactory.createEndElement(new QName("urn:2", "elem"), null));
		writer.add(eventFactory.createEndElement(new QName("urn:1", "root"), null));
		writer.add(eventFactory.createEndDocument());
	}

	@Test
	public void randomEvents() throws Exception {
		XMLStreamWriter writer = XMLOutputFactory.newFactory().createXMLStreamWriter(System.out, "UTF-8");
		writer.writeStartElement("x");
		writer.writeNamespace("p", "urn:test");
		writer.close();
	}

	@Test(expected = IllegalArgumentException.class)
	public void streamToEventDoesntWork() throws Exception {
		XMLStreamWriter writer = XMLOutputFactory.newFactory().createXMLStreamWriter(
				new StAXResult(XMLOutputFactory.newFactory().createXMLEventWriter(System.out, "UTF-8")));
		writer.writeStartElement("x");
		writer.writeNamespace("p", "urn:test");
		writer.close();
	}

}
