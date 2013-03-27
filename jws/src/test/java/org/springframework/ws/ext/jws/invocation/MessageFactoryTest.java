/*
 * Copyright 2013 Exence SA
 * Created: 26 mar 2013 12:41:49
 */

package org.springframework.ws.ext.jws.invocation;

import javax.xml.namespace.QName;

import org.junit.Before;
import org.junit.Test;
import org.springframework.ws.ext.jws.description.OperationDescription;
import org.springframework.ws.ext.jws.invocation.WebServiceInvocationFactory;
import org.springframework.ws.ext.jws.invocation.WebServiceInvocationMessage;
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
