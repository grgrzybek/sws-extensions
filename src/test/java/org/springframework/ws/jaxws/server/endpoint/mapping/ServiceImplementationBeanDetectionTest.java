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

import java.lang.reflect.Proxy;

import javax.jws.WebService;
import javax.xml.namespace.QName;

import org.junit.Before;
import org.junit.Ignore;
import org.junit.Test;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.springframework.context.support.GenericApplicationContext;
import org.springframework.test.util.ReflectionTestUtils;
import org.springframework.util.ClassUtils;
import org.springframework.util.ReflectionUtils;
import org.springframework.ws.jaxws.jsr181.implbean.AbstractServiceImplementationBean;
import org.springframework.ws.jaxws.jsr181.implbean.Enclosing.InnerServiceImplementationBean;
import org.springframework.ws.jaxws.jsr181.implbean.FinalServiceImplementationBean;
import org.springframework.ws.jaxws.jsr181.implbean.ServiceImplementationBean;
import org.springframework.ws.jaxws.jsr181.implbean.ServiceImplementationBeanWithFinalize;
import org.springframework.ws.jaxws.jsr181.implbean.ServiceImplementationBeanWithPublicFinalize;
import org.springframework.ws.jaxws.jsr181.implbean.ServiceImplementationBeanWithoutAnnotation;
import org.springframework.ws.jaxws.jsr181.implbean.ServiceImplementationBeanWithoutDefaultPublicConstructor;
import org.springframework.ws.server.endpoint.MethodEndpoint;

import static org.hamcrest.CoreMatchers.*;

import static org.junit.Assert.*;

/**
 * <p></p>
 * 
 * @author Grzegorz Grzybek
 */
public class ServiceImplementationBeanDetectionTest {

	private JaxWsEndpointMapping mapping;

	@Before
	public void before() {
		this.mapping = new JaxWsEndpointMapping();
	}

	@Test
	public void serviceImplementationBeanLooksFine() {
		QName key = this.mapping.getLookupKeyForMethod(ReflectionUtils.findMethod(ServiceImplementationBean.class, "method"));
		assertThat("Service Implementation Bean should have been detected", key, equalTo(new QName("http://implbean.jsr181.jaxws.ws.springframework.org/",
				"method")));

		GenericApplicationContext gac = new GenericApplicationContext();
		gac.getDefaultListableBeanFactory().registerSingleton("bean1", new ServiceImplementationBean());
		this.mapping.setApplicationContext(gac);
		Object endpoint = ReflectionTestUtils.invokeMethod(this.mapping, "lookupEndpoint", new QName("http://implbean.jsr181.jaxws.ws.springframework.org/",
				"method"));
		assertThat("serviceImplementationBeanMustDefineWebServiceAnnotation", endpoint, notNullValue());
	}

	/**
	 * Detecting CGLIB proxies without interfaces - Service Implementation Bean only
	 * 
	 * @throws Exception
	 */
	@Test
	public void proxiedServiceImplementationBeanWithoutInterfaceMustBeDetected() throws Exception {
		ClassPathXmlApplicationContext ac = new ClassPathXmlApplicationContext("ProxiedWebServiceWithoutInterface-context.xml", this.getClass());

		JaxWsEndpointMapping mapping = ac.getBean(JaxWsEndpointMapping.class);
		MethodEndpoint endpoint = ReflectionTestUtils.invokeMethod(mapping, "lookupEndpoint", new QName("http://implbean.jsr181.jaxws.ws.springframework.org/",
				"method"));
		// is AOP config OK?
		String result = (String) endpoint.invoke();
		assertThat(result, equalTo("proxied hello"));
		assertThat("AOPed endpoint should be detected", endpoint, notNullValue());
		// is the endpoint a CGLIB proxy?
		assertTrue("Service Implementation Bean should be a CGLIB proxy", ClassUtils.isCglibProxy(endpoint.getBean()));
	}

	/**
	 * Detecting {@link Proxy Java proxies} - Service Implementation Bean with {@link WebService @WebService} interface
	 * 
	 * @throws Exception
	 */
	@Test
	public void proxiedServiceImplementationBeanMustBeDetected() throws Exception {
		ClassPathXmlApplicationContext ac = new ClassPathXmlApplicationContext("ProxiedWebService-context.xml", this.getClass());

		JaxWsEndpointMapping mapping = ac.getBean(JaxWsEndpointMapping.class);
		MethodEndpoint endpoint = ReflectionTestUtils.invokeMethod(mapping, "lookupEndpoint", new QName("http://sei.jsr181.jaxws.ws.springframework.org/",
				"method"));
		// is AOP config OK?
		String result = (String) endpoint.invoke();
		assertThat(result, equalTo("proxied hello"));
		assertThat("serviceImplementationBeanMustDefineWebServiceAnnotation", endpoint, notNullValue());
		// is the endpoint a Java proxy?
		assertTrue("Service Implementation Bean should be a proxy", Proxy.isProxyClass(endpoint.getBean().getClass()));
	}

	/**
	 * Detecting {@link Proxy Java proxies} - Service Implementation Bean with {@link WebService @WebService} interface
	 * 
	 * @throws Exception
	 */
	@Test
	@Ignore("Bean may be a target to different proxies created by different ProxyFactoryBeans - we don't know what proxy to choose as an endpoint")
	public void traditionallyProxiedServiceImplementationBeanMustBeDetected() throws Exception {
		ClassPathXmlApplicationContext ac = new ClassPathXmlApplicationContext("TraditionallyProxiedWebService-context.xml", this.getClass());

		JaxWsEndpointMapping mapping = ac.getBean(JaxWsEndpointMapping.class);
		MethodEndpoint endpoint = ReflectionTestUtils.invokeMethod(mapping, "lookupEndpoint", new QName("http://sei.jsr181.jaxws.ws.springframework.org/",
				"method"));
		// is AOP config OK?
		String result = (String) endpoint.invoke();
		assertThat(result, equalTo("traditionally proxied hello"));
		assertThat("serviceImplementationBeanMustDefineWebServiceAnnotation", endpoint, notNullValue());
		// is the endpoint a Java proxy?
		assertTrue("Service Implementation Bean should be a proxy", Proxy.isProxyClass(ac.getBean((String) endpoint.getBean()).getClass()));
	}

	@Test
	public void serviceImplementationBeanMustBeOuterClass() {
		QName key = this.mapping.getLookupKeyForMethod(ReflectionUtils.findMethod(InnerServiceImplementationBean.class, "method"));
		assertThat("serviceImplementationBeanMustBeOuterClass", key, nullValue());
	}

	@Test
	public void serviceImplementationBeanMustBeOuterPublicClass() {
		QName key = this.mapping.getLookupKeyForMethod(ReflectionUtils.findMethod(OuterNonPublicServiceImplementationBean.class, "method"));
		assertThat("serviceImplementationBeanMustBeOuterPublicClass", key, nullValue());
	}

	@Test
	public void serviceImplementationBeanMustNotBeFinal() {
		QName key = this.mapping.getLookupKeyForMethod(ReflectionUtils.findMethod(FinalServiceImplementationBean.class, "method"));
		assertThat("serviceImplementationBeanMustNotBeFinal", key, nullValue());
	}

	@Test
	public void serviceImplementationBeanMustNotBeAbstract() {
		QName key = this.mapping.getLookupKeyForMethod(ReflectionUtils.findMethod(AbstractServiceImplementationBean.class, "method"));
		assertThat("serviceImplementationBeanMustNotBeAbstract", key, nullValue());
	}

	@Test
	public void serviceImplementationBeanMustHaveDefaultPublicConstructor() {
		QName key = this.mapping.getLookupKeyForMethod(ReflectionUtils.findMethod(ServiceImplementationBeanWithoutDefaultPublicConstructor.class, "method"));
		assertThat("serviceImplementationBeanMustHaveDefaultPublicConstructor", key, nullValue());
	}

	@Test
	public void serviceImplementationBeanMustNotDefineFinalize() {
		QName key = this.mapping.getLookupKeyForMethod(ReflectionUtils.findMethod(ServiceImplementationBeanWithFinalize.class, "method"));
		assertThat("serviceImplementationBeanMustNotDefineFinalize", key, nullValue());
		key = this.mapping.getLookupKeyForMethod(ReflectionUtils.findMethod(ServiceImplementationBeanWithPublicFinalize.class, "method"));
		assertThat("serviceImplementationBeanMustNotDefineFinalize", key, nullValue());
	}

	@Test
	public void serviceImplementationBeanMustDefineWebServiceAnnotation() {
		GenericApplicationContext gac = new GenericApplicationContext();
		gac.getDefaultListableBeanFactory().registerSingleton("bean1", new ServiceImplementationBeanWithoutAnnotation());
		this.mapping.setApplicationContext(gac);
		Object endpoint = ReflectionTestUtils.invokeMethod(this.mapping, "lookupEndpoint", new QName("http://implbean.jsr181.jaxws.ws.springframework.org/",
				"method"));
		assertThat("serviceImplementationBeanMustDefineWebServiceAnnotation", endpoint, nullValue());
	}

	@Test
	public void serviceImplementationBeanMayReferenceSEI() {
		/*
		The implementation bean MAY reference a service endpoint interface by using
		the @WebService.endpointInterface annotation. If the implementation bean
		references a service endpoint interface, it MUST implement all the methods on
		the service endpoint interface. If the implementation bean references a service
		endpoint interface, that service endpoint interface is used to determine the abstract
		WSDL contract (portType and bindings). In this case, the service implementation
		bean MUST NOT include any JSR-181 annotations other than @WebService
		and @HandlerChain. In addition, the @WebService annotation MUST NOT
		include the name annotation element. More information on the
		@WebService.endpointInterface annotation element may be found in 4.1
		Annotation: javax.jws.WebService.
		 */
	}

	@Test
	public void serviceImplementationMayImplementSEI() {
		/*
		If the implementation bean does not implement a service endpoint interface and
		there are no @WebMethod annotations in the implementation bean (excluding
		@WebMethod annotations used to exclude inherited @WebMethods), all public
		methods other than those inherited from java.lang.Object will be exposed as Web
		Service operations, subject to the inheritance rules specified in Common
		Annotations for the Java Platform [12], section 2.1.
		 */
	}

}
