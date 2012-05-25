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

import org.springframework.beans.factory.FactoryBean;
import org.springframework.ws.client.core.support.WebServiceGatewaySupport;

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
public class JwsTemplate<T> extends WebServiceGatewaySupport implements JwsOperations, FactoryBean<T> {

	private Class<T> webServiceInterface;

	/**
	 * @param webServiceInterface
	 */
	public JwsTemplate(Class<T> webServiceInterface) {
		this.webServiceInterface = webServiceInterface;
	}

	/* (non-Javadoc)
	 * @see org.springframework.ws.jaxws.client.core.JwsOperations#invokeRpcOperation(javax.xml.namespace.QName, java.lang.Object[])
	 */
	@Override
	public Object invokeRpcOperation(QName operationName, Object[] params) {
		// TODO Auto-generated method stub
		return null;
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
		// TODO Auto-generated method stub
		return null;
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
		return false;
	}

	/* (non-Javadoc)
	 * @see org.springframework.ws.client.core.support.WebServiceGatewaySupport#initGateway()
	 */
	@Override
	protected void initGateway() throws Exception {
		super.initGateway();
	}

}
