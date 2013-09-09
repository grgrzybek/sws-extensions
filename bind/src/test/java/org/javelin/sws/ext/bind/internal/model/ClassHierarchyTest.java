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

import static org.junit.Assert.*;

import java.util.Map;

import org.javelin.sws.ext.bind.SweJaxbContextFactory;
import org.javelin.sws.ext.bind.TypedPatternRegistry;
import org.javelin.sws.ext.bind.internal.model.context3.A;
import org.javelin.sws.ext.bind.internal.model.context3.B2;
import org.javelin.sws.ext.bind.internal.model.context3.nested.B1;
import org.javelin.sws.ext.bind.internal.model.context3.nested.B3;
import org.junit.Test;
import org.springframework.test.util.ReflectionTestUtils;

/**
 * <p></p>
 *
 * @author Grzegorz Grzybek
 */
public class ClassHierarchyTest {

	@Test
	@SuppressWarnings("unchecked")
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

}
