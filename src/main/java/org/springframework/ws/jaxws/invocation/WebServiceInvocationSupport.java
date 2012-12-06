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

import javax.xml.transform.Source;

import org.springframework.ws.client.core.WebServiceTemplate;
import org.springframework.ws.soap.SoapMessageFactory;
import org.springframework.ws.soap.axiom.AxiomSoapMessageFactory;

/**
 * <p></p>
 *
 * @author Grzegorz Grzybek
 */
public abstract class WebServiceInvocationSupport {

	/** This is the lowest layer - we just send the {@link Source} created at Jws layer */
	private WebServiceTemplate webServiceTemplate;
	/** A helper class to create objects from Jws layer */
	private WebServiceInvocationFactory invocationFactory;

	/**
	 * 
	 */
	protected WebServiceInvocationSupport() {
		this(new WebServiceTemplate(new AxiomSoapMessageFactory()));
	}

	/**
	 * Creates a WebService acessor using {@link WebServiceTemplate} which must be initialized with {@link SoapMessageFactory}
	 * 
	 * @param webServiceTemplate
	 */
	protected WebServiceInvocationSupport(WebServiceTemplate webServiceTemplate) {
		this.webServiceTemplate = webServiceTemplate;
		this.invocationFactory = new WebServiceInvocationFactory((SoapMessageFactory) this.webServiceTemplate.getMessageFactory());
	}

	/**
	 * @return the invocationFactory
	 */
	public WebServiceInvocationFactory getInvocationFactory() {
		return this.invocationFactory;
	}

	/**
	 * @return the webServiceTemplate
	 */
	public WebServiceTemplate getWebServiceTemplate() {
		return this.webServiceTemplate;
	}

}
