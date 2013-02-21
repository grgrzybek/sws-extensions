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
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.Map;

import javax.jws.WebService;
import javax.jws.soap.SOAPBinding.ParameterStyle;
import javax.jws.soap.SOAPBinding.Style;
import javax.xml.transform.TransformerException;

import org.aopalliance.intercept.MethodInterceptor;
import org.aopalliance.intercept.MethodInvocation;
import org.springframework.aop.framework.ProxyFactory;
import org.springframework.beans.factory.FactoryBean;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.core.annotation.AnnotationUtils;
import org.springframework.oxm.XmlMappingException;
import org.springframework.util.Assert;
import org.springframework.util.ClassUtils;
import org.springframework.util.ReflectionUtils;
import org.springframework.util.ReflectionUtils.MethodCallback;
import org.springframework.ws.WebServiceMessage;
import org.springframework.ws.client.WebServiceClientException;
import org.springframework.ws.client.core.WebServiceMessageCallback;
import org.springframework.ws.client.core.WebServiceMessageExtractor;
import org.springframework.ws.client.core.WebServiceTemplate;
import org.springframework.ws.ext.bind.SoapEncodingMarshaller;
import org.springframework.ws.jaxws.client.core.description.OperationDescription;
import org.springframework.ws.jaxws.invocation.DocumentWrapper;
import org.springframework.ws.jaxws.invocation.RpcOperation;
import org.springframework.ws.jaxws.invocation.WebServiceInvocationMessage;
import org.springframework.ws.jaxws.invocation.WebServiceInvocationSupport;
import org.springframework.ws.soap.axiom.AxiomSoapMessageFactory;
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
 * TODO: Maybe separate Template concept from FactoryBean creating strongly-typed client Proxy
 *
 * @author Grzegorz Grzybek
 */
public class JwsTemplate<T> extends WebServiceInvocationSupport implements JwsOperations, FactoryBean<T>, MethodInterceptor, InitializingBean {

	/** JSR181 annotated interface */
	private Class<T> webServiceInterface;

	/** Strongly-typed proxy */
	private T proxy;

	/** One-size fits-all (un)marshaller for every possible Soap Encoding or Literal use */
	private SoapEncodingMarshaller marshaller;

	/**
	 * Mapping of Java invocation metadata to WebService invocation metadata.
	 * TODO: Maybe a part of client ProxyFactory only?
	 */
	private Map<Method, OperationDescription> java2ws = new LinkedHashMap<Method, OperationDescription>();

	/**
	 * @param webServiceTemplate
	 * @param webServiceInterface
	 */
	public JwsTemplate(Class<T> webServiceInterface) {
		// without strategy configuration, we just create default WebServiceTemplate
		super(new WebServiceTemplate(new AxiomSoapMessageFactory()));
		this.webServiceInterface = webServiceInterface;
	}

	/**
	 * @param webServiceInterface The {@link WebService}-annotated interface, which will be used to determine proper manner of invoking the related web service.
	 * @param webServiceTemplate
	 */
	public JwsTemplate(Class<T> webServiceInterface, WebServiceTemplate webServiceTemplate) {
		super(webServiceTemplate);
		this.webServiceInterface = webServiceInterface;
	}

	/* (non-Javadoc)
	 * @see org.springframework.ws.jaxws.client.core.JwsOperations#invokeRpcOperation(javax.xml.namespace.QName, java.lang.Object[])
	 */
	@Override
	public Object invokeRpcOperation(RpcOperation rpcOperation, final Object[] params) throws XmlMappingException, WebServiceClientException {
		WebServiceMessage request = this.getInvocationFactory().getSoapMessageFactory().createWebServiceMessage();

		// TODO: detect SoapEncodingMarshaller and other marshallers
		try {
			MarshallingUtils.marshal(this.marshaller, params, request);
		}
		catch (IOException e) {
			throw new RuntimeException(e.getMessage(), e);
		}
		return this.getWebServiceTemplate().sendAndReceive(new WebServiceMessageCallback() {
			@Override
			public void doWithMessage(WebServiceMessage message) throws IOException, TransformerException {
				MarshallingUtils.marshal(JwsTemplate.this.marshaller, params, message);
			}
		}, new WebServiceMessageExtractor<T>() {
			@SuppressWarnings("unchecked")
			@Override
			public T extractData(WebServiceMessage message) throws IOException, TransformerException {
				return (T) MarshallingUtils.unmarshal(JwsTemplate.this.marshaller, message);
			}
		});
	}

	/* (non-Javadoc)
	 * @see org.springframework.ws.jaxws.client.core.JwsOperations#invokeDocumentOperation(org.springframework.ws.jaxws.invocation.WebServiceInvocationMessage, java.lang.Object[])
	 */
	@Override
	public Object invokeDocumentOperation(WebServiceInvocationMessage webServiceInvocation, Object[] params) throws XmlMappingException,
			WebServiceClientException {
		// TODO Auto-generated method stub
		return null;
	}

	/* (non-Javadoc)
	 * @see org.springframework.ws.jaxws.client.core.JwsOperations#invokeDocumentWrappedOperation(org.springframework.ws.jaxws.invocation.DocumentWrapper, java.lang.Object[])
	 */
	@Override
	public Object invokeDocumentWrappedOperation(DocumentWrapper documentWrapper, Object[] params) throws XmlMappingException, WebServiceClientException {
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
	 * @see org.springframework.beans.factory.InitializingBean#afterPropertiesSet()
	 */
	@SuppressWarnings("unchecked")
	@Override
	public void afterPropertiesSet() throws Exception {
		Assert.notNull(this.webServiceInterface, "Service Endpoint Interface should be set");
		Assert.isTrue(this.webServiceInterface.isInterface(), "Service Endpoint Interface should be a Java interface");

		WebService webService = AnnotationUtils.findAnnotation(this.webServiceInterface, WebService.class);
		Assert.isTrue(webService != null, "Service Endpoint Interface should be annotated with @javax.jws.WebService annotation");

		// According to WS-I Basic Profile 1.2 R2304, all operation names in single portType (@WebService interface) must be unique
		final Map<String, Method> operationNames = new HashMap<String, Method>();
		ReflectionUtils.doWithMethods(this.webServiceInterface, new MethodCallback() {
			@Override
			public void doWith(Method method) throws IllegalArgumentException, IllegalAccessException {
				// a method may or may not be annotated with @javax.jws.WebMethod.
				// We use the most "specific" (strange term for interface hierarchy...) method
				method = ClassUtils.getMostSpecificMethod(method, JwsTemplate.this.webServiceInterface);
				if (operationNames.containsKey(method.getName()) && !method.equals(operationNames.get(method.getName()))) {
					throw new IllegalArgumentException(String.format("Cannot use %s method, as %s was already described. Port type operations must have distinct names (WS-I BP 1.2, R2304)", method.toString(), operationNames.get(method.getName())));
				}
				operationNames.put(method.getName(), method);
				OperationDescription description = JwsUtils.describeMethod(method);
				JwsTemplate.this.java2ws.put(method, description);
			}
		});
		// this annotation configures particular operation of portType (interface)
		// WebMethod webMethod = AnnotationUtils.findAnnotation(method, WebMethod.class);

		// we discover all @WebMethods and create proper Java -> WebService mapping
		//TODO

		ProxyFactory pf = new ProxyFactory();
		pf.setInterfaces(new Class[] { this.webServiceInterface });
		pf.addAdvice(this);
		this.proxy = (T) pf.getProxy();
	}

	/**
	 * <p>This method performs the same role as client-side generated Stubs for classic JAX-RPC.</p>
	 * <p>For example in Axis1, client-side generated WS operation:<ul>
	 * <li>creates javax.xml.rpc.Call</li>
	 * <li>configures it using generated org.apache.axis.description.OperationDesc/org.apache.axis.description.ParameterDesc (style, QNames, encoding, parameters)</li>
	 * <li>configures it using properties, selects correct endpoint (SOAP Action), adds SOAP Headers and MIME attachments</li>
	 * <li>calls javax.xml.rpc.Call.invoke(Object[])</li>
	 * <li>extracts theresult</li>
	 * </ul></p>
	 * <p>We perform only <i>Web Service operation layer</i> activities here:<ul>
	 * <li>Chose OperationDescription initialized from JSR-181 metadata</li>
	 * <li>Determine Operation style</li>
	 * <li>Call proper {@link JwsOperations JWS Operation} which will handle (or delegate) consecutive activities to (<i>Web Service data layer</i>)</li>
	 * </ul></p>
	 * 
	 * @see org.aopalliance.intercept.MethodInterceptor#invoke(org.aopalliance.intercept.MethodInvocation)
	 */
	@Override
	public Object invoke(MethodInvocation invocation) throws Throwable {
		// this template knows what kind of webservice we have to deal with - what style and use.
		// invocation contains method and arguments - we need to determine proper operation QName and parameters
		// the conversion into WebService message elements is the role of this.invoke*Operation() methods
		OperationDescription operation = this.java2ws.get(invocation.getMethod());
		
		if (operation == null || operation.getStyle() == null) {
			throw new IllegalArgumentException(String.format("Unknown WebService operation for Java method \"%s\"", invocation.getMethod().toString()));
		}

		if (operation.getStyle() == Style.RPC) {
			RpcOperation rpcOperation = this.getInvocationFactory().createRpcOperation(operation);
			return this.invokeRpcOperation(rpcOperation, invocation.getArguments());
		} else {
			if (operation.getParameterStyle() == ParameterStyle.WRAPPED) {
				DocumentWrapper documentWrapper = this.getInvocationFactory().createDocumentWrapper(operation);
				return this.invokeDocumentWrappedOperation(documentWrapper, invocation.getArguments());
			} else {
				WebServiceInvocationMessage wsInvocation = this.getInvocationFactory().createInvocation(operation);
				return this.invokeDocumentOperation(wsInvocation, invocation.getArguments());
			}
		}
	}

	/**
	 * @return the marshaller
	 */
	public SoapEncodingMarshaller getMarshaller() {
		return this.marshaller;
	}

	/**
	 * @param marshaller the marshaller to set
	 */
	public void setSoapEncodingMarshaller(SoapEncodingMarshaller marshaller) {
		this.marshaller = marshaller;
	}

}
