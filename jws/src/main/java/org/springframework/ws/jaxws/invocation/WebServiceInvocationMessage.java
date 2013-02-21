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

package org.springframework.ws.jaxws.invocation;

import org.springframework.ws.WebServiceMessage;
import org.springframework.ws.jaxws.client.core.description.OperationDescription;
import org.springframework.ws.soap.SoapMessage;

/**
 * <p>A specialized (by aggregation/delegation/use) version of {@link WebServiceMessage} more aligned with WSDL 1.1 concepts such as RPC invocation.</p>
 * <p>Spring-WS promotes contract-first Web Services, where each request/response is generally an XML document. In practice it's good to know that some
 * <i>document exchanges</i> are really a kind of RPC.</p>
 *
 * @author Grzegorz Grzybek
 */
public class WebServiceInvocationMessage {

	private SoapMessage soapMessage;
	@SuppressWarnings("unused")
	private WebServiceInvocationParameter[] parameters;
	
	private OperationDescription operationDescription;

	/**
	 * @param operationDescription
	 */
	public WebServiceInvocationMessage(OperationDescription operationDescription) {
		this.operationDescription = operationDescription;
	}

	/**
	 * @return the soapMessage
	 */
	public SoapMessage getSoapMessage() {
		return this.soapMessage;
	}

	/**
	 * @return the operationDescription
	 */
	public OperationDescription getOperationDescription() {
		return this.operationDescription;
	}

}
