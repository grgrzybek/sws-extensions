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

import java.io.IOException;
import java.util.LinkedList;
import java.util.List;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBElement;
import javax.xml.bind.SchemaOutputResolver;
import javax.xml.namespace.QName;
import javax.xml.transform.Result;
import javax.xml.transform.dom.DOMResult;

import org.javelin.sws.ext.bind.SweJaxbContextFactory;
import org.javelin.sws.ext.bind.internal.model.context2.TypeWithElement;
import org.javelin.sws.ext.bind.internal.model.context2.TypeWithElementWrapper;
import org.junit.Test;

/**
 * <p>Test related to (un)marshalling objects as content model with element information item children</p>
 *
 * @author Grzegorz Grzybek
 */
public class ElementContentTest {

	@Test
	public void marshalWithoutWrapper() throws Exception {
		JAXBContext context = SweJaxbContextFactory.createContext("org.javelin.sws.ext.bind.internal.model.context2", null);
		context.createMarshaller().marshal(new JAXBElement<TypeWithElement>(new QName("", "root"), TypeWithElement.class, new TypeWithElement()), System.out);
	}
	
	@Test
	public void marshalWithWrapper() throws Exception {
		JAXBContext context = SweJaxbContextFactory.createContext("org.javelin.sws.ext.bind.internal.model.context2", null);
		context.createMarshaller().marshal(new JAXBElement<TypeWithElementWrapper>(new QName("xx", "root"), TypeWithElementWrapper.class, new TypeWithElementWrapper()), System.out);
	}
	
	@Test
	public void marshalWithoutWrapperRi() throws Exception {
		JAXBContext context = JAXBContext.newInstance("org.javelin.sws.ext.bind.internal.model.context2");

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
//		context.createMarshaller().marshal(new JAXBElement<TypeWithElement>(new QName("", "root"), TypeWithElement.class, new TypeWithElement()), System.out);
	}
	
	@Test
	public void marshalWithWrapperRi() throws Exception {
		JAXBContext context = JAXBContext.newInstance("org.javelin.sws.ext.bind.internal.model.context2");
		context.createMarshaller().marshal(new JAXBElement<TypeWithElementWrapper>(new QName("", "root"), TypeWithElementWrapper.class, new TypeWithElementWrapper()), System.out);
	}

}
