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

import java.io.IOException;

import javax.xml.namespace.QName;
import javax.xml.transform.TransformerException;

import org.aopalliance.intercept.MethodInterceptor;
import org.aopalliance.intercept.MethodInvocation;
import org.springframework.aop.framework.ProxyFactory;
import org.springframework.beans.factory.FactoryBean;
import org.springframework.util.Assert;
import org.springframework.ws.WebServiceMessage;
import org.springframework.ws.WebServiceMessageFactory;
import org.springframework.ws.client.core.WebServiceMessageCallback;
import org.springframework.ws.client.core.WebServiceMessageExtractor;
import org.springframework.ws.client.core.support.WebServiceGatewaySupport;
import org.springframework.ws.support.MarshallingUtils;

/**
 * <p>Client class for WebServices acces which allows for other than document-based access to external web services, particularily:
 * <ul>
 * <li>RPC access with q-named operations and parameters</li>
 * <li><i>multi-parameters</i> access for exotic (when soap:Body contains more than one child), document-literal and RPC-literal access</li> 
 * </ul></p>
 * <p>This class may seem to be modelled after client APIs of JAX-WS and JAX-RPC, but it really uses more pragmatic approach.</p>
 * <p>This class must be configured using existing, JWS/JAX-WS annotated interface which will set proper mapping and marshalling configuration.</p>
 *
 * @author Grzegorz Grzybek
 */
public class JwsTemplate<T> extends WebServiceGatewaySupport implements JwsOperations, FactoryBean<T>, MethodInterceptor {

	private Class<T> webServiceInterface;

	private T proxy;

	/**
	 * @param webServiceInterface
	 */
	public JwsTemplate(Class<T> webServiceInterface) {
		this.webServiceInterface = webServiceInterface;
	}

	/**
	 * @param webServiceInterface
	 * @param messageFactory
	 */
	public JwsTemplate(Class<T> webServiceInterface, WebServiceMessageFactory messageFactory) {
		super(messageFactory);
		this.webServiceInterface = webServiceInterface;
	}

	/* (non-Javadoc)
	 * @see org.springframework.ws.jaxws.client.core.JwsOperations#invokeRpcOperation(javax.xml.namespace.QName, java.lang.Object[])
	 */
	@Override
	public Object invokeRpcOperation(QName operationName, final Object[] params) {
		WebServiceMessage request = this.getMessageFactory().createWebServiceMessage();
		try {
			MarshallingUtils.marshal(this.getMarshaller(), params, request);
		}
		catch (IOException e) {
			throw new RuntimeException(e.getMessage(), e);
		}
		return this.getWebServiceTemplate().sendAndReceive(new WebServiceMessageCallback() {
			@Override
			public void doWithMessage(WebServiceMessage message) throws IOException, TransformerException {
				MarshallingUtils.marshal(JwsTemplate.this.getMarshaller(), params, message);
			}
		}, new WebServiceMessageExtractor<T>() {
			@SuppressWarnings("unchecked")
			@Override
			public T extractData(WebServiceMessage message) throws IOException, TransformerException {
				return (T) MarshallingUtils.unmarshal(JwsTemplate.this.getUnmarshaller(), message);
			}
		});
	}

	/* (non-Javadoc)
	 * @see org.springframework.ws.jaxws.client.core.JwsOperations#invokeDocumentOperation(java.lang.Object[])
	 */
	@Override
	public Object invokeDocumentOperation(Object[] params) {
		// TODO Auto-generated method stub
		return null;
	}

	/* (non-Javadoc)
	 * @see org.springframework.ws.jaxws.client.core.JwsOperations#invokeDocumentWrappedOperation(javax.xml.namespace.QName, java.lang.Object[])
	 */
	@Override
	public Object invokeDocumentWrappedOperation(QName wrapperName, Object[] params) {
		// TODO Auto-generated method stub
		return null;
	}

	/* (non-Javadoc)
	 * @see org.springframework.beans.factory.FactoryBean#getObject()
	 */
	@Override
	public T getObject() throws Exception {
		return this.proxy;
	}

	/* (non-Javadoc)
	 * @see org.springframework.beans.factory.FactoryBean#getObjectType()
	 */
	@Override
	public Class<?> getObjectType() {
		return this.webServiceInterface;
	}

	/* (non-Javadoc)
	 * @see org.springframework.beans.factory.FactoryBean#isSingleton()
	 */
	@Override
	public boolean isSingleton() {
		return true;
	}

	/* (non-Javadoc)
	 * @see org.springframework.ws.client.core.support.WebServiceGatewaySupport#initGateway()
	 */
	@SuppressWarnings("unchecked")
	@Override
	protected void initGateway() throws Exception {
		// actual initialization of WebService proxy
		Assert.notNull(this.webServiceInterface, "WebService interface should be set");

		ProxyFactory pf = new ProxyFactory();
		pf.setInterfaces(new Class[] { this.webServiceInterface });
		pf.addAdvice(this);
		this.proxy = (T) pf.getProxy();
	}

	/* (non-Javadoc)
	 * @see org.aopalliance.intercept.MethodInterceptor#invoke(org.aopalliance.intercept.MethodInvocation)
	 */
	@Override
	public Object invoke(MethodInvocation invocation) throws Throwable {
		// this template knows what kind of webservice we have to deal with - what
		// style and use.
		// invocation contains method and arguments - we need to determine proper
		// operation QName and parameters
		// the conversion into WebService message elements is the role of
		// this.invoke*Operation() methods
		return this.invokeRpcOperation(new QName("", invocation.getMethod().getName()), invocation.getArguments());
	}

}
