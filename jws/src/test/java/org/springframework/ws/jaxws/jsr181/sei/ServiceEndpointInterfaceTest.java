/*
 * Copyright 2005-2011 the original author or authors.
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

package org.springframework.ws.jaxws.jsr181.sei;

import org.junit.Test;

/**
 * <p>
 * A JSR-181 SEI MUST meet the requirements specified in JAX-WS 2.0 section 3.4
 * with these exceptions
 * </p>
 * 
 * @author Grzegorz Grzybek
 */
public class ServiceEndpointInterfaceTest {

	@Test
	public void seiMustBeOuterPublicInterface() {

	}

	@Test
	public void seiMustIncludeWebServiceAnnotation() {

	}

	@Test
	public void seiMayExtendRemote() {

	}

	@Test
	public void allMethodsAreMappedToWSDLOperations() {

	}

	@Test
	public void methodMayIncludeWebMethodAnnotation() {

	}

	@Test
	public void seiMayIncludeOtherJSR181AnnotationsToControlTheMapping() {

	}

	@Test
	public void seiMustNotIncludePortNameValue() {

	}

	@Test
	public void seiMustNotIncludeServiceNameValue() {

	}

	@Test
	public void seiMustNotIncludeEndpointInterfaceValue() {

	}

}
