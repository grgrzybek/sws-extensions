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

package org.javelin.sws.ext.jws.invocation;

import javax.xml.namespace.QName;

import org.javelin.sws.ext.jws.description.OperationDescription;
import org.junit.Before;
import org.junit.Test;
import org.springframework.ws.soap.SoapMessage;
import org.springframework.ws.soap.SoapMessageFactory;
import org.springframework.ws.soap.axiom.AxiomSoapMessageFactory;

/**
 * <p></p>
 *
 * @author Grzegorz Grzybek
 */
public class MessageFactoryTest {

	private SoapMessageFactory soapMessageFactory;

	private WebServiceInvocationFactory invocationFactory;

	@Before
	public void before() throws Exception {
		this.soapMessageFactory = new AxiomSoapMessageFactory();
		((AxiomSoapMessageFactory) this.soapMessageFactory).setPayloadCaching(false);
		this.invocationFactory = new WebServiceInvocationFactory(this.soapMessageFactory);
	}

	@Test
	public void createEmptyMessage() throws Exception {
		SoapMessage message = this.soapMessageFactory.createWebServiceMessage();
		message.writeTo(System.out);
	}

	@Test
	public void createDocumentLiteralMessage() throws Exception {
		OperationDescription op = new OperationDescription();
		WebServiceInvocationMessage message = this.invocationFactory.createInvocation(op);
		message.getSoapMessage().writeTo(System.out);
	}

	@Test
	public void createDocumentWrapper() throws Exception {
		OperationDescription op = new OperationDescription();
		op.setRootName(new QName("urn:test", "document"));
		WebServiceInvocationMessage message = this.invocationFactory.createInvocation(op);
		message.getSoapMessage().writeTo(System.out);
	}

	@Test
	public void createRpcOperation() throws Exception {
		OperationDescription op = new OperationDescription();
		op.setRootName(new QName("urn:test", "document"));
		WebServiceInvocationMessage message = this.invocationFactory.createInvocation(op);
		message.getSoapMessage().writeTo(System.out);
	}

}
