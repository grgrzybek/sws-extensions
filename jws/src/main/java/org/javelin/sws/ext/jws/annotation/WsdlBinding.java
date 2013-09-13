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
 * <p>An enumeration of different WSDL Bindings as defined in:<ul>
 * <li>WSDL 1.1 chapter 3: SOAP 1.1 <a href="http://www.w3.org/TR/wsdl#_soap-b">http://www.w3.org/TR/wsdl#_soap-b</a></li>
 * <li>WSDL 1.1 chapter 4: HTTP GET and POST: <a href="http://www.w3.org/TR/wsdl#_http">http://www.w3.org/TR/wsdl#_http</a></li>
 * <li>WSDL 1.1 chapter 5: MIME: <a href="http://www.w3.org/TR/wsdl#_Toc492291084">http://www.w3.org/TR/wsdl#_Toc492291084</a></li>
 * <li>WSDL 1.1 SOAP 1.2 Binding: <a href="http://www.w3.org/Submission/wsdl11soap12/#soap12binding">http://www.w3.org/Submission/wsdl11soap12/#soap12binding</a></li>
 * </ul></p>
 * <p>A binding (See <a href="http://www.w3.org/TR/wsdl#_language">WSDL 1.1, section 2.1.3</a>) refers to the process associating protocol
 * or data format information with an abstract entity like a message, operation, or portType.</p>
 *
 * @author Grzegorz Grzybek
 */
public enum WsdlBinding {

	/**
	 * WSDL 1.1 chapter 3: SOAP 1.1 <a href="http://www.w3.org/TR/wsdl#_soap-b">http://www.w3.org/TR/wsdl#_soap-b</a>
	 */
	SOAP_11("SOAP 1.1", "http://schemas.xmlsoap.org/wsdl/soap/"),
	/**
	 * WSDL 1.1 SOAP 1.2 Binding: <a href="http://www.w3.org/Submission/wsdl11soap12/#soap12binding">http://www.w3.org/Submission/wsdl11soap12/#soap12binding</a>
	 */
	SOAP_12("SOAP 1.2", "http://schemas.xmlsoap.org/wsdl/soap12/"),
	/**
	 * WSDL 1.1 chapter 4: HTTP GET and POST: <a href="http://www.w3.org/TR/wsdl#_http">http://www.w3.org/TR/wsdl#_http</a>
	 */
	HTTP("HTTP", "http://schemas.xmlsoap.org/wsdl/http/"),
	/**
	 * WSDL 1.1 chapter 5: MIME: <a href="http://www.w3.org/TR/wsdl#_Toc492291084">http://www.w3.org/TR/wsdl#_Toc492291084</a>
	 */
	MIME("MIME", "http://schemas.xmlsoap.org/wsdl/mime/");

	private String bindingName;

	private String bindingNamespace;

	/**
	 * @param bindingName
	 */
	private WsdlBinding(String bindingName, String bindingNamespace) {
		this.bindingName = bindingName;
		this.bindingNamespace = bindingNamespace;
	}

	/**
	 * @return the bindingName
	 */
	public String getBindingName() {
		return this.bindingName;
	}

	/**
	 * @return the bindingNamespace
	 */
	public String getBindingNamespace() {
		return this.bindingNamespace;
	}

}
