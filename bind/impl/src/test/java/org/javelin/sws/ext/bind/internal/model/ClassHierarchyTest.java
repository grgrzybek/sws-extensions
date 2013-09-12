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

package org.javelin.sws.ext.bind.internal.model;

import static org.hamcrest.CoreMatchers.*;
import static org.junit.Assert.*;

import java.io.IOException;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBElement;
import javax.xml.bind.SchemaOutputResolver;
import javax.xml.namespace.QName;
import javax.xml.transform.Result;
import javax.xml.transform.dom.DOMResult;

import org.javelin.sws.ext.bind.SweJaxbContextFactory;
import org.javelin.sws.ext.bind.TypedPatternRegistry;
import org.javelin.sws.ext.bind.internal.metadata.PropertyMetadata;
import org.javelin.sws.ext.bind.internal.model.context3.A;
import org.javelin.sws.ext.bind.internal.model.context3.B2;
import org.javelin.sws.ext.bind.internal.model.context3.nested.B1;
import org.javelin.sws.ext.bind.internal.model.context3.nested.B3;
import org.javelin.sws.ext.bind.internal.model.context4.C1;
import org.javelin.sws.ext.bind.internal.model.context4.C2;
import org.javelin.sws.ext.bind.internal.model.context4.C3;
import org.javelin.sws.ext.bind.internal.model.context4.C4;
import org.javelin.sws.ext.bind.internal.model.context4.D2;
import org.junit.Test;
import org.springframework.test.util.ReflectionTestUtils;

/**
 * <p></p>
 *
 * @author Grzegorz Grzybek
 */
@SuppressWarnings("unchecked")
public class ClassHierarchyTest {

	@Test
	public void handleXmlSeeAlso() throws Exception {
		TypedPatternRegistry context = (TypedPatternRegistry) SweJaxbContextFactory.createContext("org.javelin.sws.ext.bind.internal.model.context3", null);

		Map<Class<?>, TypedPattern<?>> patterns = (Map<Class<?>, TypedPattern<?>>) ReflectionTestUtils.getField(context, "patterns");

		TypedPattern<A> pattern1 = (TypedPattern<A>) patterns.get(A.class);
		TypedPattern<B1> pattern2 = (TypedPattern<B1>) patterns.get(B1.class);
		TypedPattern<B2> pattern3 = (TypedPattern<B2>) patterns.get(B2.class);
		TypedPattern<B3> pattern4 = (TypedPattern<B3>) patterns.get(B2.class);

		assertNotNull(pattern1);
		assertNotNull(pattern2);
		assertNotNull(pattern3);
		assertNotNull(pattern4);
	}

	@Test
	public void handleXmlAccessTypeNONE() throws Exception {
		// f3, f4, p3, p4 - explicitely annotated
		System.out.println("\nC1");
		JAXBContext.newInstance(C1.class).createMarshaller().marshal(new JAXBElement<C1>(new QName("", "r"), C1.class, new C1()), System.out);
		JAXBContext ctx = SweJaxbContextFactory.createContext(new Class[] { C1.class }, null);
		Map<Class<?>, TypedPattern<?>> patterns = (Map<Class<?>, TypedPattern<?>>) ReflectionTestUtils.getField(ctx, "patterns");
		ComplexTypePattern<C1> pattern = (ComplexTypePattern<C1>) patterns.get(C1.class);
		Map<QName, PropertyMetadata<C1, ?>> elements = (Map<QName, PropertyMetadata<C1, ?>>) ReflectionTestUtils.getField(pattern, "elements");
		assertThat(elements.size(), equalTo(4));
		assertTrue(elements.containsKey(new QName("", "f3")));
		assertTrue(elements.containsKey(new QName("", "f4")));
		assertTrue(elements.containsKey(new QName("", "p3")));
		assertTrue(elements.containsKey(new QName("", "p4")));
	}

	@Test
	public void handleXmlAccessTypeField() throws Exception {
		// f1, f2, f3, f4, p3, p4 - fields and annotated properties
		System.out.println("\nC2");
		JAXBContext.newInstance(C2.class).createMarshaller().marshal(new JAXBElement<C2>(new QName("", "r"), C2.class, new C2()), System.out);
		JAXBContext ctx = SweJaxbContextFactory.createContext(new Class[] { C2.class }, null);
		Map<Class<?>, TypedPattern<?>> patterns = (Map<Class<?>, TypedPattern<?>>) ReflectionTestUtils.getField(ctx, "patterns");
		ComplexTypePattern<C2> pattern = (ComplexTypePattern<C2>) patterns.get(C2.class);
		Map<QName, PropertyMetadata<C2, ?>> elements = (Map<QName, PropertyMetadata<C2, ?>>) ReflectionTestUtils.getField(pattern, "elements");
		assertThat(elements.size(), equalTo(6));
		assertTrue(elements.containsKey(new QName("", "f1")));
		assertTrue(elements.containsKey(new QName("", "f2")));
		assertTrue(elements.containsKey(new QName("", "f3")));
		assertTrue(elements.containsKey(new QName("", "f4")));
		assertTrue(elements.containsKey(new QName("", "p3")));
		assertTrue(elements.containsKey(new QName("", "p4")));
	}

	@Test
	public void handleXmlAccessTypeProperty() throws Exception {
		// f3, f4, p1, p2, p3, p4 - properties and annotated fields
		System.out.println("\nC3");
		JAXBContext.newInstance(C3.class).createMarshaller().marshal(new JAXBElement<C3>(new QName("", "r"), C3.class, new C3()), System.out);
		JAXBContext ctx = SweJaxbContextFactory.createContext(new Class[] { C3.class }, null);
		Map<Class<?>, TypedPattern<?>> patterns = (Map<Class<?>, TypedPattern<?>>) ReflectionTestUtils.getField(ctx, "patterns");
		ComplexTypePattern<C3> pattern = (ComplexTypePattern<C3>) patterns.get(C3.class);
		Map<QName, PropertyMetadata<C3, ?>> elements = (Map<QName, PropertyMetadata<C3, ?>>) ReflectionTestUtils.getField(pattern, "elements");
		assertThat(elements.size(), equalTo(6));
		assertTrue(elements.containsKey(new QName("", "f3")));
		assertTrue(elements.containsKey(new QName("", "f4")));
		assertTrue(elements.containsKey(new QName("", "p1")));
		assertTrue(elements.containsKey(new QName("", "p2")));
		assertTrue(elements.containsKey(new QName("", "p3")));
		assertTrue(elements.containsKey(new QName("", "p4")));
	}

	@Test
	public void handleXmlAccessTypePublicMember() throws Exception {
		// f1, f3, f4, p1, p3, p4 - public fields, public properties, annotated fields, annotated properties
		System.out.println("\nC4");
		JAXBContext.newInstance(C4.class).createMarshaller().marshal(new JAXBElement<C4>(new QName("", "r"), C4.class, new C4()), System.out);
		JAXBContext ctx = SweJaxbContextFactory.createContext(new Class[] { C4.class }, null);
		Map<Class<?>, TypedPattern<?>> patterns = (Map<Class<?>, TypedPattern<?>>) ReflectionTestUtils.getField(ctx, "patterns");
		ComplexTypePattern<C4> pattern = (ComplexTypePattern<C4>) patterns.get(C4.class);
		Map<QName, PropertyMetadata<C4, ?>> elements = (Map<QName, PropertyMetadata<C4, ?>>) ReflectionTestUtils.getField(pattern, "elements");
		assertThat(elements.size(), equalTo(6));
		assertTrue(elements.containsKey(new QName("", "f1")));
		assertTrue(elements.containsKey(new QName("", "f3")));
		assertTrue(elements.containsKey(new QName("", "f4")));
		assertTrue(elements.containsKey(new QName("", "p1")));
		assertTrue(elements.containsKey(new QName("", "p3")));
		assertTrue(elements.containsKey(new QName("", "p4")));
	}

	@Test
	public void handleXmlAccessTypePropertyWithBase() throws Exception {
		// f3, f4, p1, p2, p3, p4 - properties and annotated fields
		System.out.println("\nD2");
		JAXBContext context = JAXBContext.newInstance(D2.class);
		context.createMarshaller().marshal(new JAXBElement<D2>(new QName("", "r"), D2.class, new D2()), System.out);
		System.out.println();

		final List<DOMResult> results = new LinkedList<DOMResult>();

		context.generateSchema(new SchemaOutputResolver() {
			@Override
			public Result createOutput(String namespaceUri, String suggestedFileName) throws IOException {
				DOMResult result = new DOMResult();
				results.add(result);
				result.setSystemId(suggestedFileName);
				return result;
			}
		});

		for (DOMResult dr : results) {
			javax.xml.transform.TransformerFactory
					.newInstance()
					.newTransformer()
					.transform(new javax.xml.transform.dom.DOMSource(dr.getNode()), new javax.xml.transform.stream.StreamResult(new java.io.PrintWriter(System.out)));
		}
		
		JAXBContext ctx = SweJaxbContextFactory.createContext(new Class[] { D2.class }, null);
		Map<Class<?>, TypedPattern<?>> patterns = (Map<Class<?>, TypedPattern<?>>) ReflectionTestUtils.getField(ctx, "patterns");
		ComplexTypePattern<D2> pattern = (ComplexTypePattern<D2>) patterns.get(D2.class);
		Map<QName, PropertyMetadata<D2, ?>> elements = (Map<QName, PropertyMetadata<D2, ?>>) ReflectionTestUtils.getField(pattern, "elements");
		assertThat(elements.size(), equalTo(8));
		assertTrue(elements.containsKey(new QName("", "f3")));
		assertTrue(elements.containsKey(new QName("", "f4")));
		assertTrue(elements.containsKey(new QName("", "fd3")));
		assertTrue(elements.containsKey(new QName("", "fd4")));
		assertTrue(elements.containsKey(new QName("", "p1")));
		assertTrue(elements.containsKey(new QName("", "p2")));
		assertTrue(elements.containsKey(new QName("", "p3")));
		assertTrue(elements.containsKey(new QName("", "p4")));
	}

}
