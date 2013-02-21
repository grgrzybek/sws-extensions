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

import javax.jws.WebMethod;
import javax.jws.WebParam;
import javax.jws.WebResult;
import javax.jws.WebService;
import javax.jws.soap.SOAPBinding;
import javax.jws.soap.SOAPBinding.Style;
import javax.jws.soap.SOAPBinding.Use;

/**
 * <p></p>
 *
 * @author Grzegorz Grzybek
 */
@WebService(name = "Port01", targetNamespace = "urn:services:1")
@SOAPBinding(style = Style.RPC, use = Use.LITERAL)
public interface Port01 {

	@WebMethod(operationName = "hello")
	@WebResult(name = "wres", partName = "response", targetNamespace = "urn:results:1")
	// CXF's Java2WSDL uses "name" as a name of accessor element in request
	// message's element's complexType's sequence (WRAPPED), or
	// local names of global elements (BARE)
	// CXF doesn't use "partName" as wsdl:part/@name in WRAPPED (according to
	// JSR-181) - "parameters" is used
	public String hello(@WebParam(name = "wpar1", partName = "request1") String param);

}
