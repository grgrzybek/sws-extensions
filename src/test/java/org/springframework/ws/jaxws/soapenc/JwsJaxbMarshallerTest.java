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

package org.springframework.ws.jaxws.soapenc;

import java.io.StringWriter;

import javax.xml.bind.JAXBContext;

import org.junit.Ignore;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.ws.jaxws.soapenc.context1.MyClass;

/**
 * <p></p>
 *
 * @author Grzegorz Grzybek
 */
public class JwsJaxbMarshallerTest {

	private static Logger log = LoggerFactory.getLogger(JwsJaxbMarshallerTest.class.getName());

	@Test
	public void marshallMyClass1() throws Exception {
		JAXBContext context = JAXBContext.newInstance("org.springframework.ws.jaxws.soapenc.context1");
		StringWriter sw = new StringWriter();
		context.createMarshaller().marshal(new MyClass(), sw);
		log.info(sw.toString());
	}
	
	@Test
	@Ignore
	public void marshallMyClass2() throws Exception {
		JAXBContext context = JAXBContext.newInstance("org.springframework.ws.jaxws.soapenc.context2");
		StringWriter sw = new StringWriter();
		context.createMarshaller().marshal(new org.springframework.ws.jaxws.soapenc.context2.MyClass(), sw);
		log.info(sw.toString());
	}

}
