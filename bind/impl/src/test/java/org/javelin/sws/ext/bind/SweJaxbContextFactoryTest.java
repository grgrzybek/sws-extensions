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

import static org.junit.Assert.*;

import java.io.File;
import java.net.URL;
import java.net.URLClassLoader;
import java.util.Map;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBElement;
import javax.xml.bind.JAXBException;
import javax.xml.namespace.QName;

import org.javelin.sws.ext.bind.internal.model.TypedPattern;
import org.junit.Test;
import org.springframework.test.util.ReflectionTestUtils;
import org.springframework.util.ClassUtils;

/**
 * <p></p>
 *
 * @author Grzegorz Grzybek
 */
public class SweJaxbContextFactoryTest {

	@Test(expected = JAXBException.class)
	public void nonExistingPackageForJaxbRi() throws Exception {
		JAXBContext.newInstance("a.b.c");
	}
	
	@Test
	public void nonExistingPackage() throws Exception {
		SweJaxbContextFactory.createContext("a.b.c", null);
	}

	@Test(expected = JAXBException.class)
	public void defaultPackageForJaxbRi() throws Exception {
		JAXBContext.newInstance("");
	}

	@Test
	public void defaultPackage() throws Exception {
		JAXBContext ctx = SweJaxbContextFactory.createContext("", null);
		// but we may marshal objects of built-in classes - XSD simple types
		ctx.createMarshaller().marshal(new JAXBElement<String>(new QName("urn:test", "str"), String.class, "content"), System.out);
	}

	@Test
	public void emptySetOfClassesForJaxbRi() throws Exception {
		JAXBContext ctx = JAXBContext.newInstance();
		System.out.println(ctx.toString());
		ctx.createMarshaller().marshal(new JAXBElement<String>(new QName("urn:test", "str"), String.class, "content"), System.out);
	}
	
	@Test
	public void emptySetOfClasses() throws Exception {
		JAXBContext ctx = SweJaxbContextFactory.createContext(new Class[] {}, null);
		ctx.createMarshaller().marshal(new JAXBElement<String>(new QName("urn:test", "str"), String.class, "content"), System.out);
	}
	
	@Test
	public void scanExceptionClassForJaxbRi() throws Exception {
		JAXBContext ctx = JAXBContext.newInstance(IllegalArgumentException.class);
		System.out.println(ctx.toString());
		ctx.createMarshaller().marshal(new JAXBElement<IllegalArgumentException>(new QName("urn:test", "e"), IllegalArgumentException.class, new IllegalArgumentException("exception")), System.out);
	}

	@Test
	public void nonDefaultClassLoader() throws Exception {
		// parent-last class loader
		// see org.apache.cocoon.servlet.ParanoidClassLoader
		URLClassLoader cl = new URLClassLoader(new URL[] { new File("target/test-classes").getCanonicalFile().toURI().toURL() }) {
			@Override
			protected synchronized Class<?> loadClass(String name, boolean resolve) throws ClassNotFoundException {
				Class<?> c = this.findLoadedClass(name);
				if (c == null) {
					try {
						c = this.findClass(name);
					}
					catch (ClassNotFoundException e) {
						if (this.getParent() != null) {
							c = this.getParent().loadClass(name);
						}
						else {
							throw e;
						}
					}
				}
				if (resolve)
					this.resolveClass(c);
				return c;
			}
		};

		JAXBContext ctx = SweJaxbContextFactory.createContext("org.javelin.sws.ext.bind.context2", cl);
		@SuppressWarnings("unchecked")
		Map<Class<?>, TypedPattern<?>> patterns = (Map<Class<?>, TypedPattern<?>>) ReflectionTestUtils.getField(ctx, "patterns");
		Class<?> mc = null;
		for (Class<?> c : patterns.keySet()) {
			if (c.getName().equals("org.javelin.sws.ext.bind.context2.MyClass2"))
				mc = c;
		}
		assertSame(mc.getClassLoader(), cl);
		Class<?> c1 = ClassUtils.resolveClassName("org.javelin.sws.ext.bind.context2.MyClass2", this.getClass().getClassLoader());
		assertFalse(patterns.containsKey(c1));
		Class<?> c2 = ClassUtils.resolveClassName("org.javelin.sws.ext.bind.context2.MyClass2", cl);
		assertTrue(patterns.containsKey(c2));
	}

}
