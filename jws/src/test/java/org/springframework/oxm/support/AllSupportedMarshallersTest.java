/*
 * Copyright 2005-2012 the original author or authors.
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

package org.springframework.oxm.support;

import java.util.HashMap;

import javax.xml.bind.JAXBElement;
import javax.xml.namespace.QName;
import javax.xml.transform.stream.StreamResult;

import org.junit.Test;
import org.springframework.oxm.Marshaller;
import org.springframework.oxm.castor.CastorMarshaller;
import org.springframework.oxm.jaxb.Jaxb2Marshaller;
import org.springframework.oxm.xmlbeans.XmlBeansMarshaller;
import org.springframework.oxm.xstream.XStreamMarshaller;
import org.springframework.ws.cxf.dispatch2.model.Param1;

/**
 * <p></p>
 *
 * @author Grzegorz Grzybek
 */
public class AllSupportedMarshallersTest {

	private Marshaller marshaller;

	@Test
	public void marshallWithJaxb2() throws Exception {
		this.marshaller = new Jaxb2Marshaller();
		((Jaxb2Marshaller)this.marshaller).setClassesToBeBound(Param1.class);
		HashMap<String, Object> properties = new HashMap<String, Object>();
		properties.put(javax.xml.bind.Marshaller.JAXB_FORMATTED_OUTPUT, true);
		((Jaxb2Marshaller)this.marshaller).setMarshallerProperties(properties);
		((Jaxb2Marshaller)this.marshaller).afterPropertiesSet();
		this.marshaller.marshal(new JAXBElement<Param1>(new QName("urn:test", "param1"), Param1.class, new Param1()), new StreamResult(System.out));
	}
	
	@Test
	public void marshallWithCastor() throws Exception {
		this.marshaller = new CastorMarshaller();
		((CastorMarshaller)this.marshaller).afterPropertiesSet();
		this.marshaller.marshal(new Param1(), new StreamResult(System.out));
	}
	
//	@Test
//	public void marshallWithJibx() throws Exception {
//		this.marshaller = new JibxMarshaller();
//		((JibxMarshaller)this.marshaller).setTargetClass(Param1.class);
//		((JibxMarshaller)this.marshaller).afterPropertiesSet();
//		this.marshaller.marshal(new Param1(), new StreamResult(System.out));
//	}
	
	@Test
	public void marshallWithXmlBeans() throws Exception {
		this.marshaller = new XmlBeansMarshaller();
		this.marshaller.marshal(new Param1(), new StreamResult(System.out));
	}
	
	@Test
	public void marshallWithXStream() throws Exception {
		this.marshaller = new XStreamMarshaller();
		((XStreamMarshaller)this.marshaller).afterPropertiesSet();
		this.marshaller.marshal(new Param1(), new StreamResult(System.out));
	}

}
