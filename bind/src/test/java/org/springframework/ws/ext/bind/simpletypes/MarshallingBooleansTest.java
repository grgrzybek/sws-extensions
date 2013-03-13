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

package org.springframework.ws.ext.bind.simpletypes;

import java.io.StringWriter;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBElement;
import javax.xml.bind.Marshaller;
import javax.xml.namespace.QName;

import org.junit.Before;
import org.junit.Test;
import org.springframework.ws.ext.bind.SweJaxbConstants;
import org.springframework.ws.ext.bind.SweJaxbContextFactory;

import static org.junit.Assert.*;

/**
 * <p></p>
 *
 * @author Grzegorz Grzybek
 */
public class MarshallingBooleansTest {

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
	public void marshalTrue() throws Exception {
		String result = "<s xmlns=\"urn:test\" xmlns:xs=\"http://www.w3.org/2001/XMLSchema\" xmlns:xsi=\"http://www.w3.org/2001/XMLSchema-instance\" xsi:type=\"xs:boolean\">true</s>";
		this.m.marshal(new JAXBElement<Boolean>(new QName("urn:test", "s"), Boolean.class, true), this.sw);

		System.out.println(this.sw.toString());
		assertEquals(result, this.sw.toString());
	}
	
	@Test
	public void marshalFalse() throws Exception {
		String result = "<s xmlns=\"urn:test\" xmlns:xs=\"http://www.w3.org/2001/XMLSchema\" xmlns:xsi=\"http://www.w3.org/2001/XMLSchema-instance\" xsi:type=\"xs:boolean\">false</s>";
		this.m.marshal(new JAXBElement<Boolean>(new QName("urn:test", "s"), Boolean.class, false), this.sw);
		
		System.out.println(this.sw.toString());
		assertEquals(result, this.sw.toString());
	}
	
	@Test
	public void marshalNull() throws Exception {
		String result = "<s xmlns=\"urn:test\" xmlns:xs=\"http://www.w3.org/2001/XMLSchema\" xmlns:xsi=\"http://www.w3.org/2001/XMLSchema-instance\" xsi:type=\"xs:boolean\" xsi:nil=\"true\"/>";
		this.m.marshal(new JAXBElement<Boolean>(new QName("urn:test", "s"), Boolean.class, null), this.sw);
		
		System.out.println(this.sw.toString());
		assertEquals(result, this.sw.toString());
	}

}
