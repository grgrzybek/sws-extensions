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

package org.springframework.ws.jaxws.annotation;

/**
 * <p>Can we use something from Spring-WS? We can always choose JAX-WS' {@code javax.xml.ws.BindingType} but we'd like not to be tied to JAX-WS API (JWS API only)</p>
 * <p>See also {@code javax.xml.ws.BindingType}</p>
 *
 * @author Grzegorz Grzybek
 */
public @interface BindingType {

	/**
	 * A binding which maps abstract message and invocation into concrete representation
	 * 
	 * @return
	 */
	WsdlBinding value() default WsdlBinding.SOAP_11;

}
