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

package org.springframework.ws.jaxws.matrix;

import org.junit.Test;
import org.springframework.util.ClassUtils;

import static org.junit.Assert.*;

import static org.hamcrest.CoreMatchers.*;

/**
 * <p></p>
 *
 * @author Grzegorz Grzybek
 */
public class ParentInterfacesTest {

	@Test
	public void invokeOverridenInterfaceMethod() {
		Port02 port02 = new Port02() {
			@Override
			public String hello(String param) {
				return "[" + param + "]";
			}
		};
		assertThat(port02.hello("x"), equalTo("[x]"));
	}
	
	@Test
	public void invokeOverridenInterfaceMethodThroughParent() throws SecurityException, NoSuchMethodException {
		Port02 port02 = new Port02() {
			@Override
			public String hello(String param) {
				return "[" + param + "]";
			}
		};
		assertThat(((ParentPort02)port02).hello("x"), equalTo("[x]"));

		// not the same method
		assertThat(port02.getClass().getMethod("hello", String.class), not(equalTo(Port02.class.getMethod("hello", String.class))));
		assertThat(ParentPort02.class.getMethod("hello", String.class), not(equalTo(Port02.class.getMethod("hello", String.class))));
		// but:
		assertThat(port02.getClass().getMethod("hello", String.class), equalTo(ClassUtils.getMostSpecificMethod(ParentPort02.class.getMethod("hello", String.class), port02.getClass())));
	}

}
