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
import java.lang.reflect.Method;
import java.util.LinkedHashMap;
import java.util.Map;

import javax.jws.WebService;
import javax.xml.namespace.QName;
import javax.xml.transform.TransformerException;

import org.aopalliance.intercept.MethodInterceptor;
import org.aopalliance.intercept.MethodInvocation;
import org.springframework.aop.framework.ProxyFactory;
import org.springframework.beans.factory.FactoryBean;
import org.springframework.core.annotation.AnnotationUtils;
import org.springframework.oxm.XmlMappingException;
import org.springframework.util.Assert;
import org.springframework.util.ClassUtils;
import org.springframework.util.ReflectionUtils;
import org.springframework.util.ReflectionUtils.MethodCallback;
import org.springframework.ws.WebServiceMessage;
import org.springframework.ws.WebServiceMessageFactory;
import org.springframework.ws.client.WebServiceClientException;
import org.springframework.ws.client.core.WebServiceMessageCallback;
import org.springframework.ws.client.core.WebServiceMessageExtractor;
import org.springframework.ws.client.core.support.WebServiceGatewaySupport;
import org.springframework.ws.jaxws.client.core.description.OperationDescription;
import org.springframework.ws.soap.saaj.SaajSoapMessageFactory;
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
 * TODO: separate Template concept from FactoryBean creating strongly-typed client Proxy (?)
 *
 * @author Grzegorz Grzybek
 */
public class JwsTemplate<T> extends WebServiceGatewaySupport implements JwsOperations, FactoryBean<T>, MethodInterceptor {

	/** JSR181 annotated interface */
	private Class<T> webServiceInterface;

	/** Strongly-typed proxy */
	private T proxy;

	/**
	 * Mapping of Java invocation metadata to WebService invocation metadata.
	 * TODO: Maybe a part of client ProxyFactory only?
	 */
	private Map<Method, OperationDescription> java2ws = new LinkedHashMap<Method, OperationDescription>();

	/**
	 * @param webServiceInterface The {@link WebService}-annotated interface, which will be used to determine proper manner of invoking the related web service.
	 */
	public JwsTemplate(Class<T> webServiceInterface) {
		super();
		this.webServiceInterface = webServiceInterface;
	}

	/**
	 * @param webServiceInterface The {@link WebService}-annotated interface, which will be used to determine proper manner of invoking the related web service.
	 * @param messageFactory The message factory of choice. Default is {@link SaajSoapMessageFactory}
	 */
	public JwsTemplate(Class<T> webServiceInterface, WebServiceMessageFactory messageFactory) {
		super(messageFactory);
		this.webServiceInterface = webServiceInterface;
	}

	/* (non-Javadoc)
	 * @see org.springframework.ws.jaxws.client.core.JwsOperations#invokeRpcOperation(javax.xml.namespace.QName, java.lang.Object[])
	 */
	@Override
	public Object invokeRpcOperation(QName operationName, final Object[] params) throws XmlMappingException, WebServiceClientException {
		WebServiceMessage request = this.getMessageFactory().createWebServiceMessage();

		// TODO: detect SoapEncodingMarshaller and other marshallers
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
	public Object invokeDocumentOperation(Object[] params) throws XmlMappingException, WebServiceClientException {
		// TODO Auto-generated method stub
		return null;
	}

	/* (non-Javadoc)
	 * @see org.springframework.ws.jaxws.client.core.JwsOperations#invokeDocumentWrappedOperation(javax.xml.namespace.QName, java.lang.Object[])
	 */
	@Override
	public Object invokeDocumentWrappedOperation(QName wrapperName, Object[] params) throws XmlMappingException, WebServiceClientException {
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
		Assert.notNull(this.webServiceInterface, "Service Endpoint Interface should be set");
		Assert.isTrue(this.webServiceInterface.isInterface(), "Service Endpoint Interface should be a Java interface");

		WebService webService = AnnotationUtils.findAnnotation(this.webServiceInterface, WebService.class);
		Assert.isTrue(webService != null, "Service Endpoint Interface should be annotated with @javax.jws.WebService annotation");

		ReflectionUtils.doWithMethods(this.webServiceInterface, new MethodCallback() {
			@Override
			public void doWith(Method method) throws IllegalArgumentException, IllegalAccessException {
				// a method may or may not be annotated with @javax.jws.WebMethod.
				// We use the most "specific" (strange term for interface
				// hierarchy...) method
				method = ClassUtils.getMostSpecificMethod(method, JwsTemplate.this.webServiceInterface);
				OperationDescription description = JwsUtils.describeMethod(method);
				JwsTemplate.this.java2ws.put(method, description);
			}
		});
		// this annotation configures particular operation of portType (interface)
		// WebMethod webMethod = AnnotationUtils.findAnnotation(method,
		// WebMethod.class);

		// we discover all @WebMethods and create proper Java -> WebService
		// mapping
		// AnnotationUtils.

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
