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

package org.springframework.ws.axis1.case1.codefirst;

import org.apache.axis.wsdl.Java2WSDL;

/**
 * <p>This is the basic endpoint, which (using {@link Java2WSDL}) will be a base for different WSDL documents.</p>
 *
 * @author Grzegorz Grzybek
 */
public class EchoEndpoint {

	/**
	 * @param param1
	 * @return
	 */
	public String oneParam(String param1) {
		return "[" + param1 + "]";
	}

	/**
	 * @param param1
	 * @param param2
	 * @return
	 */
	public String twoParams(String param1, String param2) {
		return "[" + param1 + "|" + param2 + "]";
	}

}
