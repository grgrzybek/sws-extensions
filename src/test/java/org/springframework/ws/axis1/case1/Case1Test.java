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

package org.springframework.ws.axis1.case1;

import java.net.URL;

import org.apache.axis.configuration.FileProvider;
import org.apache.axis.wsdl.Java2WSDL;
import org.apache.axis.wsdl.WSDL2Java;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.ws.axis1.Axis1IntegrationTests;
import org.springframework.ws.axis1.case1.codefirst.EchoEndpoint;

/**
 * <p></p>
 *
 * @author Grzegorz Grzybek
 */
public class Case1Test extends Axis1IntegrationTests {

	private static Logger log = LoggerFactory.getLogger(Case1Test.class.getName());

	@Test
	public void axis1WebServices() throws Exception {

		super.isolatedAxis1Test(new Callback() {
			@Override
			public void perform(int port) throws Exception {

				try {
					org.springframework.ws.axis1.case1.contractfirst.document_encoded_11.EchoEndpoint documentEncodedEndpoint11 = new org.springframework.ws.axis1.case1.contractfirst.document_encoded_11.EchoEndpointServiceLocator(new FileProvider("src/test/resources/org/springframework/ws/axis1/case1/codefirst/document_encoded", "deploy-1.1.wsdd")).getmyservice(new URL("http://localhost:" + port + "/axis1/EchoEndpointDocumentEncoded11"));
					documentEncodedEndpoint11.oneParam("param");
					documentEncodedEndpoint11.twoParams("param1", "param2");
					org.springframework.ws.axis1.case1.contractfirst.document_encoded_12.EchoEndpoint documentEncodedEndpoint12 = new org.springframework.ws.axis1.case1.contractfirst.document_encoded_12.EchoEndpointServiceLocator(new FileProvider("src/test/resources/org/springframework/ws/axis1/case1/codefirst/document_encoded", "deploy-1.2.wsdd")).getmyservice(new URL("http://localhost:" + port + "/axis1/EchoEndpointDocumentEncoded12"));
					documentEncodedEndpoint12.oneParam("param");
					documentEncodedEndpoint12.twoParams("param1", "param2");

					org.springframework.ws.axis1.case1.contractfirst.document_literal_11.EchoEndpoint documentLiteralEndpoint11 = new org.springframework.ws.axis1.case1.contractfirst.document_literal_11.EchoEndpointServiceLocator(new FileProvider("src/test/resources/org/springframework/ws/axis1/case1/codefirst/document_literal", "deploy-1.1.wsdd")).getmyservice(new URL("http://localhost:" + port + "/axis1/EchoEndpointDocumentLiteral11"));
					documentLiteralEndpoint11.oneParam("param");
					documentLiteralEndpoint11.twoParams("param1", "param2");
					org.springframework.ws.axis1.case1.contractfirst.document_literal_12.EchoEndpoint documentLiteralEndpoint12 = new org.springframework.ws.axis1.case1.contractfirst.document_literal_12.EchoEndpointServiceLocator(new FileProvider("src/test/resources/org/springframework/ws/axis1/case1/codefirst/document_literal", "deploy-1.2.wsdd")).getmyservice(new URL("http://localhost:" + port + "/axis1/EchoEndpointDocumentLiteral12"));
					documentLiteralEndpoint12.oneParam("param");
					documentLiteralEndpoint12.twoParams("param1", "param2");

					org.springframework.ws.axis1.case1.contractfirst.rpc_encoded_11.EchoEndpoint rpcEncodedEndpoint11 = new org.springframework.ws.axis1.case1.contractfirst.rpc_encoded_11.EchoEndpointServiceLocator(new FileProvider("src/test/resources/org/springframework/ws/axis1/case1/codefirst/rpc_encoded", "deploy-1.1.wsdd")).getmyservice(new URL("http://localhost:" + port + "/axis1/EchoEndpointRPCEncoded11"));
					rpcEncodedEndpoint11.oneParam("param");
					rpcEncodedEndpoint11.twoParams("param1", "param2");
					org.springframework.ws.axis1.case1.contractfirst.rpc_encoded_12.EchoEndpoint rpcEncodedEndpoint12 = new org.springframework.ws.axis1.case1.contractfirst.rpc_encoded_12.EchoEndpointServiceLocator(new FileProvider("src/test/resources/org/springframework/ws/axis1/case1/codefirst/rpc_encoded", "deploy-1.2.wsdd")).getmyservice(new URL("http://localhost:" + port + "/axis1/EchoEndpointRPCEncoded12"));
					rpcEncodedEndpoint12.oneParam("param");
					rpcEncodedEndpoint12.twoParams("param1", "param2");
					
					org.springframework.ws.axis1.case1.contractfirst.rpc_literal_11.EchoEndpoint rpcLiteralEndpoint11 = new org.springframework.ws.axis1.case1.contractfirst.rpc_literal_11.EchoEndpointServiceLocator(new FileProvider("src/test/resources/org/springframework/ws/axis1/case1/codefirst/rpc_literal", "deploy-1.1.wsdd")).getmyservice(new URL("http://localhost:" + port + "/axis1/EchoEndpointRPCLiteral11"));
					rpcLiteralEndpoint11.oneParam("param");
					rpcLiteralEndpoint11.twoParams("param1", "param2");
					org.springframework.ws.axis1.case1.contractfirst.rpc_literal_12.EchoEndpoint rpcLiteralEndpoint12 = new org.springframework.ws.axis1.case1.contractfirst.rpc_literal_12.EchoEndpointServiceLocator(new FileProvider("src/test/resources/org/springframework/ws/axis1/case1/codefirst/rpc_literal", "deploy-1.2.wsdd")).getmyservice(new URL("http://localhost:" + port + "/axis1/EchoEndpointRPCLiteral12"));
					rpcLiteralEndpoint12.oneParam("param");
					rpcLiteralEndpoint12.twoParams("param1", "param2");
					
//					org.springframework.ws.axis1.case1.contractfirst.wrapped_encoded_11.EchoEndpoint wrappedEncodedEndpoint11 = new org.springframework.ws.axis1.case1.contractfirst.wrapped_encoded_11.EchoEndpointServiceLocator(new FileProvider("src/test/resources/org/springframework/ws/axis1/case1/codefirst/wrapped_encoded", "deploy-1.1.wsdd")).getmyservice(new URL("http://localhost:" + port + "/axis1/EchoEndpointWrappedEncoded11"));
//					wrappedEncodedEndpoint11.oneParam(new org.springframework.ws.axis1.case1.contractfirst.wrapped_encoded_11.OneParam("param"));
//					wrappedEncodedEndpoint11.twoParams(new org.springframework.ws.axis1.case1.contractfirst.wrapped_encoded_11.TwoParams("param1", "param2"));
//					org.springframework.ws.axis1.case1.contractfirst.wrapped_encoded_12.EchoEndpoint wrappedEncodedEndpoint12 = new org.springframework.ws.axis1.case1.contractfirst.wrapped_encoded_12.EchoEndpointServiceLocator(new FileProvider("src/test/resources/org/springframework/ws/axis1/case1/codefirst/wrapped_encoded", "deploy-1.2.wsdd")).getmyservice(new URL("http://localhost:" + port + "/axis1/EchoEndpointWrappedEncoded12"));
//					wrappedEncodedEndpoint12.oneParam(new org.springframework.ws.axis1.case1.contractfirst.wrapped_encoded_12.OneParam("param"));
//					wrappedEncodedEndpoint12.twoParams(new org.springframework.ws.axis1.case1.contractfirst.wrapped_encoded_12.TwoParams("param1", "param2"));
					
					org.springframework.ws.axis1.case1.contractfirst.wrapped_literal_11.EchoEndpoint wrappedLiteralEndpoint11 = new org.springframework.ws.axis1.case1.contractfirst.wrapped_literal_11.EchoEndpointServiceLocator(new FileProvider("src/test/resources/org/springframework/ws/axis1/case1/codefirst/wrapped_literal", "deploy-1.1.wsdd")).getmyservice(new URL("http://localhost:" + port + "/axis1/EchoEndpointWrappedLiteral11"));
					wrappedLiteralEndpoint11.oneParam("param");
					wrappedLiteralEndpoint11.twoParams("param1", "param2");
					org.springframework.ws.axis1.case1.contractfirst.wrapped_literal_12.EchoEndpoint wrappedLiteralEndpoint12 = new org.springframework.ws.axis1.case1.contractfirst.wrapped_literal_12.EchoEndpointServiceLocator(new FileProvider("src/test/resources/org/springframework/ws/axis1/case1/codefirst/wrapped_literal", "deploy-1.2.wsdd")).getmyservice(new URL("http://localhost:" + port + "/axis1/EchoEndpointWrappedLiteral12"));
					wrappedLiteralEndpoint12.oneParam("param");
					wrappedLiteralEndpoint12.twoParams("param1", "param2");
				}
				catch (Exception e) {
					log.error(e.getMessage(), e);
				}
			}
		});

	}

	/**
	 * Generating all default artifacts
	 * 
	 * @param args
	 * @throws Exception
	 */
	public static void main(String[] args) throws Exception {
//		wsdl2java("DOCUMENT", "LITERAL", "1.1");
//		wsdl2java("DOCUMENT", "LITERAL", "1.2");
//		wsdl2java("DOCUMENT", "ENCODED", "1.1");
//		wsdl2java("DOCUMENT", "ENCODED", "1.2");
//		wsdl2java("RPC", "LITERAL", "1.1");
//		wsdl2java("RPC", "LITERAL", "1.2");
//		wsdl2java("RPC", "ENCODED", "1.1");
//		wsdl2java("RPC", "ENCODED", "1.2");
//		wsdl2java("WRAPPED", "LITERAL", "1.1");
//		wsdl2java("WRAPPED", "LITERAL", "1.2");
//		wsdl2java("WRAPPED", "ENCODED", "1.1");
//		wsdl2java("WRAPPED", "ENCODED", "1.2");
//		java2wsdl("DOCUMENT", "LITERAL", "1.1");
//		java2wsdl("DOCUMENT", "LITERAL", "1.2");
//		java2wsdl("DOCUMENT", "ENCODED", "1.1");
//		java2wsdl("DOCUMENT", "ENCODED", "1.2");
//		java2wsdl("RPC", "LITERAL", "1.1");
//		java2wsdl("RPC", "LITERAL", "1.2");
//		java2wsdl("RPC", "ENCODED", "1.1");
//		java2wsdl("RPC", "ENCODED", "1.2");
//		java2wsdl("WRAPPED", "LITERAL", "1.1");
//		java2wsdl("WRAPPED", "LITERAL", "1.2");
//		java2wsdl("WRAPPED", "ENCODED", "1.1");
//		java2wsdl("WRAPPED", "ENCODED", "1.2");
	}

	/**
	 * @param style
	 * @param use
	 * @param tmVersion
	 */
	@SuppressWarnings("unused")
	private static void wsdl2java(String style, String use, String tmVersion) {
		WSDL2Java.main(new String[] {
				"src/test/resources/org/springframework/ws/axis1/case1/codefirst/" + style.toLowerCase() + "_" + use.toLowerCase() +"/myservice-" + tmVersion + ".wsdl",
				"-o", "src/test/java",
				"-s",
				"-p", "org.springframework.ws.axis1.case1.contractfirst." + style.toLowerCase() + "_" + use.toLowerCase() + "_" + tmVersion.replace(".", "")
		});
	}

	/**
	 * @param style
	 * @param use
	 * @param tmVersion
	 */
	@SuppressWarnings("unused")
	private static void java2wsdl(String style, String use, String tmVersion) {
		Java2WSDL.main(new String[] {
				EchoEndpoint.class.getName(), "-l", "http://axis1.org/ws/services/myservice",
				"-o", "src/test/resources/" + EchoEndpoint.class.getPackage().getName().replace(".", "/") + "/" + (style.toLowerCase() + "_" + use.toLowerCase()) + "/myservice-" + tmVersion + ".wsdl",
				"-A", "OPERATION",
				"-y", style,
				"-u", use,
				"-T", tmVersion,
				"-d"
		});
	}
}
