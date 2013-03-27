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

package org.springframework.ws.ext.bind.jaxb;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBElement;
import javax.xml.bind.Marshaller;
import javax.xml.namespace.QName;

import org.junit.Test;
import org.springframework.ws.ext.bind.jaxb.context7.MyClass;

/**
 * <p></p>
 *
 * @author Grzegorz Grzybek
 */
public class JaxbCollectionsTest {

	@Test
	public void marshalCollectionsRi() throws Exception {
		JAXBContext context = JAXBContext.newInstance(MyClass.class);
		Marshaller m = context.createMarshaller();
		m.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, true);
		MyClass c = new MyClass();
		m.marshal(new JAXBElement<MyClass>(new QName("urn:test", "root"), MyClass.class, c), System.out);
	}

}
