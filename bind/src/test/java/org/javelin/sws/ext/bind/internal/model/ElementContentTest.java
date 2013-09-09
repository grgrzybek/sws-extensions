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

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBElement;
import javax.xml.namespace.QName;

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
		context.createMarshaller().marshal(new JAXBElement<TypeWithElementWrapper>(new QName("", "root"), TypeWithElementWrapper.class, new TypeWithElementWrapper()), System.out);
	}

}
