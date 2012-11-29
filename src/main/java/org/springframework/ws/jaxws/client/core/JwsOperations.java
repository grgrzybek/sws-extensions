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

package org.springframework.ws.jaxws.client.core;

import javax.xml.namespace.QName;

import org.springframework.oxm.XmlMappingException;
import org.springframework.ws.client.WebServiceClientException;

/**
 * <p>Commons operations for Web Services.</p>
 * <p>WS specs distinguish between rpc and document style:<ul>
 * <li>RPC - the message sent represents method invocation with (Q)Name and arguments</li>
 * <li>Document - the message sent represents single or (which is very ugly) multiple documents. This style has
 * (without special care) problems with proper endpoint mapping.</li>
 * <li>"Wrapped" - it's a variation of "document" style, where there's only one "document" which maps to operation and its direct children
 * map to operation arguments.</li>
 * </ul></p>
 * <p>This interface describes concepts related to web service's <b>style</b> and doesn't say anything about <b>use</b> (encoded, literal).
 * The latter concepts are handled by (un)marshalling/e(de)ncoding mechanisms elsewhere.</p>
 *
 * @author Grzegorz Grzybek
 */
public interface JwsOperations {

	/**
	 * In RPC-style operations, all arguments are passed as children of single, SOAP Body element which represents invoked operation
	 * 
	 * @param operationName
	 * @param params
	 * @return
	 */
	public Object invokeRpcOperation(QName operationName, Object[] params) throws XmlMappingException, WebServiceClientException;

	/**
	 * <p>In Document-style operations, all arguments are passed as direct children of SOAP Body.</p>
	 * <p>Of course passing more than one document-style argument is against common-sense, but SOAP and WSDL allows it</p>
	 * 
	 * @param params
	 * @return
	 */
	public Object invokeDocumentOperation(Object[] params) throws XmlMappingException, WebServiceClientException;

	/**
	 * <p>Wrapper style is special kind of document style invocation. There's only one body child which represents
	 * invoked operation and its children are "unwrapped" as the parameters of the operation.</p>
	 * 
	 * @param wrapperElementName
	 * @param params
	 * @return
	 */
	public Object invokeDocumentWrappedOperation(QName wrapperElementName, Object[] params) throws XmlMappingException, WebServiceClientException;

}
