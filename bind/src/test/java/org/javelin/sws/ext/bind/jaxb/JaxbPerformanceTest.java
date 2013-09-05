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

import java.io.ByteArrayOutputStream;
import java.io.OutputStream;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBElement;
import javax.xml.bind.Marshaller;
import javax.xml.namespace.QName;
import javax.xml.stream.XMLEventWriter;
import javax.xml.stream.XMLOutputFactory;

import org.javelin.sws.ext.bind.SweJaxbContextFactory;
import org.javelin.sws.ext.bind.jaxb.context8.MyClass;
import org.junit.Test;

/**
 * <p>It's not that bad. For marshalling into {@link OutputStream} RI is ~20% faster, but when marshalling into {@link XMLEventWriter}, we're
 * ~20% faster.</p>
 *
 * @author Grzegorz Grzybek
 */
public class JaxbPerformanceTest {

	@Test
	// (expected = StackOverflowError.class)
	public void marshalCollectionsRi() throws Exception {
		JAXBContext context = JAXBContext.newInstance(MyClass.class);
		Marshaller m = context.createMarshaller();
		m.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, false);
		long ms = System.currentTimeMillis();
		for (int n = 0; n < 1000; n++) {
			MyClass c = new MyClass();
			MyClass root = c;
			for (int i = 0; i < 300; i++) {
				MyClass nc = new MyClass();
				c.setMc(nc);
				c.setAtt("#" + i);
				c = nc;
			}
			m.marshal(new JAXBElement<MyClass>(new QName("urn:test", "root"), MyClass.class, root), XMLOutputFactory.newFactory().createXMLEventWriter(new ByteArrayOutputStream()));
//			m.marshal(new JAXBElement<MyClass>(new QName("urn:test", "root"), MyClass.class, root), new ByteArrayOutputStream());
		}
		System.out.println("RI: " + (((System.currentTimeMillis() - ms))));
	}

	@Test
	public void marshalCollectionsSwe() throws Exception {
		JAXBContext context = SweJaxbContextFactory.createContext(new Class[] { MyClass.class }, null);
		Marshaller m = context.createMarshaller();
		m.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, false);
		long ms = System.currentTimeMillis();
		for (int n = 0; n < 1000; n++) {
			MyClass c = new MyClass();
			MyClass root = c;
			for (int i = 0; i < 300; i++) {
				MyClass nc = new MyClass();
				c.setMc(nc);
				c.setAtt("#" + i);
				c = nc;
			}
			m.marshal(new JAXBElement<MyClass>(new QName("urn:test", "root"), MyClass.class, root), XMLOutputFactory.newFactory().createXMLEventWriter(new ByteArrayOutputStream()));
//			m.marshal(new JAXBElement<MyClass>(new QName("urn:test", "root"), MyClass.class, root), new ByteArrayOutputStream());
		}
		System.out.println("SWE: " + (((System.currentTimeMillis() - ms))));
	}

}
