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

package org.javelin.sws.ext.bind.collections;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBElement;
import javax.xml.bind.Marshaller;
import javax.xml.namespace.QName;

import org.javelin.sws.ext.bind.SweJaxbContextFactory;
import org.javelin.sws.ext.bind.collections.context1.C1;
import org.junit.Test;

/**
 * <p></p>
 *
 * @author Grzegorz Grzybek
 */
public class ListsTest {

	@Test
	public void marshalList() throws Exception {
		System.out.println("RI");
		JAXBContext.newInstance(C1.class).createMarshaller().marshal(new JAXBElement<C1>(new QName("x", "root"), C1.class, new C1()), System.out);
		System.out.println("\nSWE");
		JAXBContext ctx = SweJaxbContextFactory.createContext("org.javelin.sws.ext.bind.collections.context1", null);
		Marshaller m = ctx.createMarshaller();
		m.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, true);
		m.marshal(new JAXBElement<C1>(new QName("x", "root", "p"), C1.class, new C1()), System.out);
	}

}
