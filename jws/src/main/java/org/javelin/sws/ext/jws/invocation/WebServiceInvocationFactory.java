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

import org.springframework.ws.WebServiceMessageFactory;
import org.springframework.ws.jaxws.client.core.description.OperationDescription;
import org.springframework.ws.soap.SoapElement;
import org.springframework.ws.soap.SoapMessage;
import org.springframework.ws.soap.SoapMessageFactory;

/**
 * <p>A class (without any particular sub-implementation) complementing {@link WebServiceMessageFactory}, which adds concepts related to Web Services invocations such
 * as operations and parameters.</p>
 * <p>Spring-WS core is built around the concept of contract-first Web Services treated as document sending/receiving entities. This interface makes it easier to
 * model such concepts of WSDL 1.1 as RPC invocations or widely-used concept of <i>wrapped</i> document style Web Services.</p>
 * <p>We directly allow accessing Spring-WS {@link WebServiceMessageFactory} with {@link #getSoapMessageFactory()} and not by inheritance. This way we
 * don't have to implement all methods for available implementations (Axiom, Saaj). This factory <b>is not</b>a {@link WebServiceMessageFactory}, it only uses it.</p>
 *
 * @author Grzegorz Grzybek
 */
public class WebServiceInvocationFactory {

	private SoapMessageFactory webServiceMessageFactory;

	/**
	 * Creates {@link WebServiceInvocationFactory} which will use the passed {@link SoapMessageFactory} to populate exchanged {@link SoapMessage}
	 * 
	 * @param webServiceMessageFactory
	 */
	public WebServiceInvocationFactory(SoapMessageFactory webServiceMessageFactory) {
		this.webServiceMessageFactory = webServiceMessageFactory;
	}

	/**
	 * Returns underlying {@link WebServiceMessageFactory}
	 * 
	 * @return
	 */
	public SoapMessageFactory getSoapMessageFactory() {
		return this.webServiceMessageFactory;
	}

	/**
	 * Creates DOCUMENT style {@link WebServiceInvocationMessage}
	 * 
	 * @param operationDescription 
	 * @return
	 */
	public WebServiceInvocationMessage createInvocation(OperationDescription operationDescription) {
		return null;
	}

	/**
	 * Creates WRAPPED variant of DOCUMENT style {@link WebServiceInvocationMessage}
	 * 
	 * @param operationDescription 
	 * @return
	 */
	public DocumentWrapper createDocumentWrapper(OperationDescription operationDescription) {
		return null;
	}

	/**
	 * Creates RPC style {@link WebServiceInvocationMessage}
	 * 
	 * @param operationDescription 
	 * @return
	 */
	public RpcOperation createRpcOperation(OperationDescription operationDescription) {
		return null;
	}

	/**
	 * 
	 * @return
	 */
	public SoapElement[] createRpcOperationAccessors() {
		return new SoapElement[0];
	}

}
