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

import java.lang.reflect.Method;

import javax.jws.WebMethod;
import javax.jws.WebService;
import javax.jws.soap.SOAPBinding;
import javax.xml.namespace.QName;

import org.springframework.core.annotation.AnnotationUtils;
import org.springframework.util.Assert;
import org.springframework.util.StringUtils;
import org.springframework.ws.jaxws.client.core.description.OperationDescription;
import org.springframework.ws.jaxws.server.endpoint.support.NamespaceUtils;

/**
 * <p></p>
 *
 * @author Grzegorz Grzybek
 */
public abstract class JwsUtils {

	/**
	 * Using JSR181 annotations, produces {@link OperationDescription} of WebService invocation
	 * 
	 * @param method
	 * @return
	 */
	public static OperationDescription describeMethod(Method method) {
		OperationDescription description = new OperationDescription();

		// javax.jws.WebService - at interface level
		WebService webService = AnnotationUtils.findAnnotation(method.getDeclaringClass(), WebService.class);
		Assert.notNull(webService, "The Service Endpoint Interface should be annotated with @java.jws.WebService");
		String targetNamespace = webService.targetNamespace();
		if (!StringUtils.hasText(targetNamespace))
			targetNamespace = NamespaceUtils.packageNameToNamespace(method.getDeclaringClass().getPackage());
		description.setRootName(new QName(targetNamespace, method.getName()));

		// javax.jws.soap.SOAPBinding - at interface level or method level
		SOAPBinding soapBinding = AnnotationUtils.findAnnotation(method, SOAPBinding.class);
		if (soapBinding == null)
			soapBinding = AnnotationUtils.findAnnotation(method.getDeclaringClass(), SOAPBinding.class);
		if (soapBinding != null) {
			description.setStyle(soapBinding.style());
			description.setUse(soapBinding.use());
			description.setParameterStyle(soapBinding.parameterStyle());
		}

		// javax.jws.WebMethod - at method level
		WebMethod webMethod = AnnotationUtils.findAnnotation(method, WebMethod.class);
		if (webMethod != null) {
			description.setSoapAction(webMethod.action());
		}
		
		return description;
	}

}
