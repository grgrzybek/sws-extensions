/*
 * Copyright 2005-2011 the original author or authors.
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

package org.springframework.ws.jaxws.server.endpoint.mapping;

import java.lang.annotation.Annotation;
import java.lang.reflect.Method;
import java.lang.reflect.Modifier;

import javax.jws.WebMethod;
import javax.jws.WebService;
import javax.xml.namespace.QName;
import javax.xml.transform.TransformerFactory;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.aop.framework.Advised;
import org.springframework.core.annotation.AnnotationUtils;
import org.springframework.util.ClassUtils;
import org.springframework.ws.context.MessageContext;
import org.springframework.ws.jaxws.server.endpoint.support.NamespaceUtils;
import org.springframework.ws.server.EndpointMapping;
import org.springframework.ws.server.endpoint.mapping.AbstractAnnotationMethodEndpointMapping;
import org.springframework.ws.server.endpoint.support.PayloadRootUtils;

/**
 * <p>{@link EndpointMapping} that maps classes conforming to JSR-181 specification.</p>
 * <p>In simpliest form a method may be mapped to {@link QName}, but to be able to handle non WS-I conformant SOAP messages
 * (e.g., containing more than one soap body children), there's a need for something different (may it be {@link QName}[]?)</p>
 *
 * @author Grzegorz Grzybek
 */
public class JaxWsEndpointMapping extends AbstractAnnotationMethodEndpointMapping<QName> {

	private static Logger log = LoggerFactory.getLogger(JaxWsEndpointMapping.class.getName());

	private static TransformerFactory transformerFactory;
	static {
		transformerFactory = TransformerFactory.newInstance();
	}

	/**
	 * Returns {@link WebService @WebService} class
	 * 
	 * @see org.springframework.ws.server.endpoint.mapping.AbstractAnnotationMethodEndpointMapping#getEndpointAnnotationType()
	 */
	@Override
	protected Class<? extends Annotation> getEndpointAnnotationType() {
		return WebService.class;
	}

	/* (non-Javadoc)
	 * @see org.springframework.ws.server.endpoint.mapping.AbstractMethodEndpointMapping#getLookupKeyForMessage(org.springframework.ws.context.MessageContext)
	 */
	@Override
	protected QName getLookupKeyForMessage(MessageContext messageContext) throws Exception {
		return PayloadRootUtils.getPayloadRootQName(messageContext.getRequest().getPayloadSource(), transformerFactory);
	}

	/* (non-Javadoc)
	 * @see org.springframework.ws.server.endpoint.mapping.AbstractMethodEndpointMapping#getLookupKeyForMethod(java.lang.reflect.Method)
	 */
	@Override
	protected QName getLookupKeyForMethod(Method method) {
		Class<?> serviceImplementationBeanClass = method.getDeclaringClass();
		if (serviceImplementationBeanClass.isAssignableFrom(Advised.class))
			return null;

		if (serviceImplementationBeanClass.isInterface()) {
			// probably one of proxy interfaces - check only if it is @WebService
		}

		log.trace("Determining QName for {}.{}", serviceImplementationBeanClass.getName(), method.getName());
		int modifiers = serviceImplementationBeanClass.getModifiers();

		if (!Modifier.isInterface(modifiers) && (modifiers & (Modifier.ABSTRACT | Modifier.FINAL)) != 0) {
			log.warn("Can't register {}.{}. The implementation bean MUST NOT be final and MUST NOT be abstract.", method.getDeclaringClass().getName(),
					method.getName());
			return null;
		}
		if (serviceImplementationBeanClass.getDeclaringClass() != null || !Modifier.isPublic(modifiers)) {
			log.warn("Can't register {}.{}. The implementation bean MUST be an outer public class.", method.getDeclaringClass().getName(), method.getName());
			return null;
		}

		if (!Modifier.isInterface(modifiers) && !ClassUtils.hasConstructor(serviceImplementationBeanClass)) {
			log.warn("Can't register {}.{}. The implementation bean MUST have a default public constructor.", method.getDeclaringClass().getName(),
					method.getName());
			return null;
		}
		try {
			if (serviceImplementationBeanClass.getDeclaredMethod("finalize") != null) {
				log.warn("Can't register {}.{}. The implementation bean MUST NOT define a finalize() method.", method.getDeclaringClass().getName(),
						method.getName());
				return null;
			}
		}
		catch (NoSuchMethodException e) {
		}

		// this annotation will be used to determine JWS-compliant endpoint
		// characteristic
		/*WebService webService = */AnnotationUtils.findAnnotation(serviceImplementationBeanClass, WebService.class);
		// this annotation configures particular operation of portType (interface)
		/*WebMethod webMethod = */AnnotationUtils.findAnnotation(method, WebMethod.class);

		String namespace = NamespaceUtils.packageNameToNamespace(serviceImplementationBeanClass.getPackage());
		return new QName(namespace, method.getName());
	}

}
