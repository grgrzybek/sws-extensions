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

package org.springframework.ws.jaxws.client.core.description;

import javax.jws.WebMethod;
import javax.jws.WebService;
import javax.jws.soap.SOAPBinding;
import javax.jws.soap.SOAPBinding.ParameterStyle;
import javax.jws.soap.SOAPBinding.Style;
import javax.jws.soap.SOAPBinding.Use;
import javax.xml.namespace.QName;

import org.junit.Test;
import org.springframework.util.ClassUtils;
import org.springframework.ws.jaxws.client.core.JwsUtils;

import static org.hamcrest.CoreMatchers.*;

import static org.junit.Assert.*;

/**
 * <p></p>
 *
 * @author Grzegorz Grzybek
 */
public class JwsUtilsTest {

	@Test
	public void defaultOperationDescription() {
		OperationDescription desc = JwsUtils.describeMethod(ClassUtils.getMethod(Port01.class, "echo"));
		assertThat(desc.getStyle(), equalTo(Style.DOCUMENT));
		assertThat(desc.getUse(), equalTo(Use.LITERAL));
		assertThat(desc.getParameterStyle(), equalTo(ParameterStyle.WRAPPED));
		assertThat(desc.getSoapAction(), equalTo(""));
		assertThat(desc.getRootName(), equalTo(new QName("http://description.core.client.jaxws.ws.springframework.org/", "echo")));
	}

	@Test
	public void customizedOperationDescriptionAtInterfaceLevel() {
		OperationDescription desc = JwsUtils.describeMethod(ClassUtils.getMethod(Port02.class, "echo"));
		assertThat(desc.getStyle(), equalTo(Style.RPC));
		assertThat(desc.getUse(), equalTo(Use.ENCODED));
		assertThat(desc.getParameterStyle(), equalTo(ParameterStyle.BARE));
		assertThat(desc.getSoapAction(), equalTo(""));
		assertThat(desc.getRootName(), equalTo(new QName("http://description.core.client.jaxws.ws.springframework.org/", "echo")));
	}

	@Test
	public void customizedOperationDescriptionAtMethodLevel() {
		OperationDescription desc = JwsUtils.describeMethod(ClassUtils.getMethod(Port03.class, "echo"));
		assertThat(desc.getStyle(), equalTo(Style.RPC));
		assertThat(desc.getUse(), equalTo(Use.ENCODED));
		assertThat(desc.getParameterStyle(), equalTo(ParameterStyle.BARE));
		assertThat(desc.getSoapAction(), equalTo(""));
		assertThat(desc.getRootName(), equalTo(new QName("http://description.core.client.jaxws.ws.springframework.org/", "echo")));
	}

	@Test
	public void customizedOperationDescriptionAtClassAndMethodLevel() {
		OperationDescription desc = JwsUtils.describeMethod(ClassUtils.getMethod(Port04.class, "echo"));
		assertThat(desc.getStyle(), equalTo(Style.RPC));
		assertThat(desc.getUse(), equalTo(Use.LITERAL));
		assertThat(desc.getParameterStyle(), equalTo(ParameterStyle.WRAPPED));
		assertThat(desc.getSoapAction(), equalTo(""));
		assertThat(desc.getRootName(), equalTo(new QName("http://description.core.client.jaxws.ws.springframework.org/", "echo")));
	}

	@Test
	public void customizedRootName() {
		OperationDescription desc = JwsUtils.describeMethod(ClassUtils.getMethod(Port05.class, "echo"));
		assertThat(desc.getRootName(), equalTo(new QName("urn:test:1", "echo")));
	}

	@Test
	public void customizedSoapAction() {
		OperationDescription desc = JwsUtils.describeMethod(ClassUtils.getMethod(Port06.class, "echo"));
		assertThat(desc.getSoapAction(), equalTo("port06/echo"));
	}

	@WebService
	private static interface Port01 {
		public void echo();
	}

	@WebService
	@SOAPBinding(style = Style.RPC, use = Use.ENCODED, parameterStyle = ParameterStyle.BARE)
	private static interface Port02 {
		public void echo();
	}

	@WebService
	private static interface Port03 {
		@SOAPBinding(style = Style.RPC, use = Use.ENCODED, parameterStyle = ParameterStyle.BARE)
		public void echo();
	}

	@WebService
	@SOAPBinding(style = Style.RPC, use = Use.ENCODED, parameterStyle = ParameterStyle.BARE)
	private static interface Port04 {
		@SOAPBinding(style = Style.RPC, use = Use.LITERAL, parameterStyle = ParameterStyle.WRAPPED)
		public void echo();
	}

	@WebService(targetNamespace = "urn:test:1")
	private static interface Port05 {
		public void echo();
	}

	@WebService
	private static interface Port06 {
		@WebMethod(action = "port06/echo")
		public void echo();
	}
}
