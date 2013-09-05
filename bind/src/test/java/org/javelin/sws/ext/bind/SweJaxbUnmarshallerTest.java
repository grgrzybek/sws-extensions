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

import java.io.InputStream;
import java.util.HashMap;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBElement;
import javax.xml.bind.UnmarshalException;
import javax.xml.bind.Unmarshaller;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.stream.XMLInputFactory;

import org.javelin.sws.ext.bind.context1.ClassWithVeryComplexContent;
import org.javelin.sws.ext.bind.jaxb.context6.SingleRootElement;
import org.junit.Test;
import org.springframework.core.io.ClassPathResource;

import com.sun.xml.bind.v2.ContextFactory;

import static org.hamcrest.CoreMatchers.*;

import static org.junit.Assert.*;

/**
 * <p></p>
 *
 * @author Grzegorz Grzybek
 */
public class SweJaxbUnmarshallerTest {

	/**
	 * RI can unmarshal only to {@link XmlRootElement} annotated classes or to classes passed as a second argument to {@link Unmarshaller#unmarshal(javax.xml.stream.XMLEventReader, Class)}
	 * 
	 * @throws Exception
	 */
	@Test(expected = UnmarshalException.class)
	public void unmarshalVeryComplexContentRI() throws Exception {
		JAXBContext context = ContextFactory.createContext(new Class[] { org.javelin.sws.ext.bind.jaxb.context6.ClassWithVeryComplexContent.class,
				SingleRootElement.class }, new HashMap<String, Object>());
		Unmarshaller um = context.createUnmarshaller();
		InputStream inputStream = new ClassPathResource("very-complex-content-01.xml", this.getClass()).getInputStream();
		// will throw javax.xml.bind.UnmarshalException: unexpected element (uri:"urn:test", local:"root"). Expected elements are <{y}x"
		um.unmarshal(XMLInputFactory.newFactory().createXMLEventReader(inputStream));
	}

	@Test
	public void unmarshalVeryComplexContentExplicitRI() throws Exception {
		JAXBContext context = ContextFactory.createContext(new Class[] { org.javelin.sws.ext.bind.jaxb.context6.ClassWithVeryComplexContent.class,
				SingleRootElement.class }, new HashMap<String, Object>());
		Unmarshaller um = context.createUnmarshaller();
		InputStream inputStream = new ClassPathResource("very-complex-content-01.xml", this.getClass()).getInputStream();
		// will throw javax.xml.bind.UnmarshalException: unexpected element (uri:"urn:test", local:"root"). Expected elements are <{y}x"
		JAXBElement<org.javelin.sws.ext.bind.jaxb.context6.ClassWithVeryComplexContent> je = um.unmarshal(XMLInputFactory.newFactory()
				.createXMLEventReader(inputStream), org.javelin.sws.ext.bind.jaxb.context6.ClassWithVeryComplexContent.class);
		org.javelin.sws.ext.bind.jaxb.context6.ClassWithVeryComplexContent ob = je.getValue();
		assertThat(ob.getStr(), equalTo("test-1"));
		assertThat(ob.getInside(), equalTo("str"));
		assertThat(ob.getInside2().getNumber(), equalTo(42));
		assertThat(ob.getInside2().getStr(), equalTo("test-2"));
		assertThat(ob.getInside2().getInside(), equalTo("inside"));
	}

	@Test(expected = UnmarshalException.class)
	public void unmarshalVeryComplexContent() throws Exception {
		JAXBContext context = JAXBContext.newInstance("org.javelin.sws.ext.bind.context1");
		// ClassWithVeryComplexContent value = new ClassWithVeryComplexContent("test", "str", new ClassWithComplexContent("test", 42, "inside"));
		Unmarshaller um = context.createUnmarshaller();
		InputStream inputStream = new ClassPathResource("very-complex-content-01.xml", this.getClass()).getInputStream();
		um.unmarshal(XMLInputFactory.newFactory().createXMLEventReader(inputStream));
	}
	
	@Test
	public void unmarshalVeryComplexContentExplicit() throws Exception {
		JAXBContext context = JAXBContext.newInstance("org.javelin.sws.ext.bind.context1");
		// ClassWithVeryComplexContent value = new ClassWithVeryComplexContent("test", "str", new ClassWithComplexContent("test", 42, "inside"));
		Unmarshaller um = context.createUnmarshaller();
		InputStream inputStream = new ClassPathResource("very-complex-content-01.xml", this.getClass()).getInputStream();
		JAXBElement<ClassWithVeryComplexContent> je = um.unmarshal(XMLInputFactory.newFactory().createXMLEventReader(inputStream), ClassWithVeryComplexContent.class);
		assertTrue(je.getDeclaredType() == ClassWithVeryComplexContent.class);
		assertFalse("Should not be nil", je.isNil());
		assertTrue(je.getValue() instanceof ClassWithVeryComplexContent);
		ClassWithVeryComplexContent ob = (ClassWithVeryComplexContent) je.getValue();
		assertThat(ob.getStr(), equalTo("test-1"));
		assertThat(ob.getInside(), equalTo("str"));
		assertThat(ob.getInside2().getNumber(), equalTo(42));
		assertThat(ob.getInside2().getStr(), equalTo("test-2"));
		assertThat(ob.getInside2().getInside(), equalTo("inside"));
	}

}
