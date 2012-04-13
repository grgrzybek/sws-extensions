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

package org.springframework.ws.axis1;

import java.net.URL;

import org.junit.Test;

/**
 * <p></p>
 *
 * @author Grzegorz Grzybek
 */
public class StubsTest extends Axis1IntegrationTests {

	@Test
	public void axis1Clients() throws Exception {
		super.isolatedAxis1Test(new Callback() {
			
			@Override
			public void perform(int port) throws Exception {
//				org.springframework.ws.axis1.doc_enc.EchoEndpointDocument service1 = new org.springframework.ws.axis1.doc_enc.EchoEndpointDocumentServiceLocator().getEchoEndpointDocumentEncoded(new URL("http://localhost:" + port + "/axis1/EchoEndpointDocumentEncoded"));
//				org.springframework.ws.axis1.doc_enc.Result1 res1 = service1.docEnc(new org.springframework.ws.axis1.doc_enc.Param1(), new org.springframework.ws.axis1.doc_enc.Param2());
//				StubsTest.log.info("Result1: {}", res1.toString());

//				org.springframework.ws.axis1.doc_lit.EchoEndpointDocument service2 = new org.springframework.ws.axis1.doc_lit.EchoEndpointDocumentServiceLocator().getEchoEndpointDocumentLiteral(new URL("http://localhost:" + port + "/axis1/EchoEndpointDocumentLiteral"));
//				org.springframework.ws.axis1.doc_lit.Result1 res2 = service2.docLit(new org.springframework.ws.axis1.doc_lit.Param1(), new org.springframework.ws.axis1.doc_lit.Param2());
//				StubsTest.log.info("Result2: {}", res2.toString());

				org.springframework.ws.axis1.rpc_enc.EchoEndpointRPC service3 = new org.springframework.ws.axis1.rpc_enc.EchoEndpointRPCServiceLocator().getEchoEndpointRPCEncoded(new URL("http://localhost:" + port + "/axis1/EchoEndpointRPCEncoded"));
				org.springframework.ws.axis1.rpc_enc.Result1 res3 = service3.rpcEnc(new org.springframework.ws.axis1.rpc_enc.Param1(), new org.springframework.ws.axis1.rpc_enc.Param2());
				StubsTest.log.info("Result3: {}", res3.toString());

//				org.springframework.ws.axis1.rpc_lit.EchoEndpointRPC service4 = new org.springframework.ws.axis1.rpc_lit.EchoEndpointRPCServiceLocator().getEchoEndpointRPCLiteral(new URL("http://localhost:" + port + "/axis1/EchoEndpointRPCLiteral"));
//				org.springframework.ws.axis1.rpc_lit.Result1 res4 = service4.rpcLit(new org.springframework.ws.axis1.rpc_lit.Param1(), new org.springframework.ws.axis1.rpc_lit.Param2());
//				StubsTest.log.info("Result4: {}", res4.toString());

//				org.springframework.ws.axis1.wrapped_enc.EchoEndpointWrapped service5 = new org.springframework.ws.axis1.wrapped_enc.EchoEndpointWrappedServiceLocator().getEchoEndpointWrappedEncoded(new URL("http://localhost:" + port + "/axis1/EchoEndpointWrappedEncoded"));
//				org.springframework.ws.axis1.wrapped_enc.Result1 res5 = service5.wrappedEnc(new WrappedEnc(new org.springframework.ws.axis1.wrapped_enc.Param1(), new org.springframework.ws.axis1.wrapped_enc.Param2())).getWrappedEncReturn();
//				StubsTest.log.info("Result5: {}", res5.toString());

//				org.springframework.ws.axis1.wrapped_lit.EchoEndpointWrapped service6 = new org.springframework.ws.axis1.wrapped_lit.EchoEndpointWrappedServiceLocator().getEchoEndpointWrappedLiteral(new URL("http://localhost:" + port + "/axis1/EchoEndpointWrappedLiteral"));
//				org.springframework.ws.axis1.wrapped_lit.Result1 res6 = service6.wrappedLit(new org.springframework.ws.axis1.wrapped_lit.Param1(), new org.springframework.ws.axis1.wrapped_lit.Param2());
//				StubsTest.log.info("Result6: {}", res6.toString());
			}

		});
	}

}
