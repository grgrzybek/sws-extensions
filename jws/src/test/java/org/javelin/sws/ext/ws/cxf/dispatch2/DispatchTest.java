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

package org.springframework.ws.cxf.dispatch2;

import org.junit.Test;
import org.springframework.ws.cxf.CxfIntegrationTests;
import org.springframework.ws.cxf.dispatch2.model.Param1;
import org.springframework.ws.cxf.dispatch2.model.Param2;

/**
 * <p></p>
 *
 * @author Grzegorz Grzybek
 */
public class DispatchTest extends CxfIntegrationTests {

	@Test
	public void documentStyleDispatch() throws Exception {

		super.isolatedCxfTest("DispatchTest", new CxfIntegrationTests.Callback<EchoEndpointDocumentInterface>() {

			@Override
			public void perform(EchoEndpointDocumentInterface service) {
				service.docLitBare(new Param1());
				service.docLitWrapped(new Param1(), new Param2());
				service.docEncBare(new Param1());
				service.docEncWrapped(new Param1(), new Param2());
			}

		}, EchoEndpointDocumentInterface.class);
	}

	@Test
	public void rpcStyleDispatch() throws Exception {

		super.isolatedCxfTest("DispatchTest", new CxfIntegrationTests.Callback<EchoEndpointRPCInterface>() {

			@Override
			public void perform(EchoEndpointRPCInterface service) {
				service.rpcLitBare(new Param1());
				service.rpcLitWrapped(new Param1(), new Param2());
				service.rpcEncBare(new Param1());
				service.rpcEncWrapped(new Param1(), new Param2());
			}

		}, EchoEndpointRPCInterface.class);
	}

}
