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

package org.springframework.ws.axis1.case2;

import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.FileReader;
import java.lang.reflect.Constructor;
import java.net.URL;
import java.util.LinkedList;
import java.util.List;

import org.apache.axis.EngineConfiguration;
import org.apache.axis.configuration.FileProvider;
import org.apache.axis.wsdl.Java2WSDL;
import org.apache.axis.wsdl.WSDL2Java;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.test.util.ReflectionTestUtils;
import org.springframework.util.ClassUtils;
import org.springframework.util.FileCopyUtils;
import org.springframework.util.StringUtils;
import org.springframework.ws.axis1.Axis1IntegrationTests;
import org.springframework.ws.axis1.case2.codefirst.EchoEndpoint;

/**
 * <p></p>
 *
 * @author Grzegorz Grzybek
 */
public class Case2Test extends Axis1IntegrationTests {

	private static Logger log = LoggerFactory.getLogger(Case2Test.class.getName());

	@Test
	public void axis1WebServices() throws Exception {

		super.isolatedAxis1Test(new Callback() {
			@Override
			public void perform(int port) throws Exception {

				try {
					// we take WSDD files generated by WSDL2Java, not from Java2WSDL, as the former have proper package names
					log.info("start");
					for (String style: new String[] { "DOCUMENT", "RPC", "WRAPPED" }) {
						for (String use: new String[] { "LITERAL", "ENCODED" }) {
							for (String tmVersion: new String[] { "1.1", "1.2" }) {
								for (boolean wrappedArrays: new boolean[] { false, true }) {
									try {
										if ("WRAPPED".equals(style) && "ENCODED".equals(use)) {
											echoWrappedEncoded(port, style, use, tmVersion, wrappedArrays);
										} else {
											echo(port, style, use, tmVersion, wrappedArrays);
										}
									}
									catch (Exception e) {
										log.warn(e.getMessage());
									}
								}
							}
						}
					}
				}
				catch (Exception e) {
					log.error(e.getMessage(), e);
				}
			}
		});

	}
	
	/**
	 * @param style
	 * @param use
	 * @param tmVersion
	 * @param wrapArrays
	 */
	public static void echo(int port, String style, String use, String tmVersion, boolean wrapArrays) throws Exception {
		String packageFragment = String.format("%s_%s_%s_arrays%s", style.toLowerCase(), use.toLowerCase(), tmVersion.replace(".", ""), (wrapArrays ? "wrapped" : "plain"));
		String locatorMethod = String.format("getEchoEndpoint%s%s%s", StringUtils.capitalize(style.toLowerCase()), StringUtils.capitalize(use.toLowerCase()), tmVersion.replace(".", ""));
		Constructor<?> ctr = ClassUtils.getConstructorIfAvailable(ClassUtils.forName("org.springframework.ws.axis1.case2.contractfirst." + packageFragment + ".EchoEndpointServiceLocator", Case2Test.class.getClassLoader()), EngineConfiguration.class);
		Constructor<?> param1Ctr = ClassUtils.getConstructorIfAvailable(ClassUtils.forName("org.springframework.ws.axis1.case2.contractfirst." + packageFragment + ".Param1", Case2Test.class.getClassLoader()));
		Constructor<?> param2Ctr = ClassUtils.getConstructorIfAvailable(ClassUtils.forName("org.springframework.ws.axis1.case2.contractfirst." + packageFragment + ".Param2", Case2Test.class.getClassLoader()));

		String deployment = FileCopyUtils.copyToString(new FileReader("src/test/java/org/springframework/ws/axis1/case2/contractfirst/" + packageFragment + "/deploy.wsdd"));
		deployment = deployment.replace("<!-- Services from EchoEndpointService WSDL service -->",
				"<globalConfiguration>\n" + 
				"	<parameter name='sendXsiTypes' value='false' />\n" + 
				"	<!--parameter name='sendMultiRefs' value='true' /-->\n" + 
				"	<!--parameter name='axis.sendMinimizedElements' value='true' /-->\n" + 
				"	<requestFlow>\n" + 
				"		<handler type='log' />\n" + 
				"	</requestFlow>\n" + 
				"	<responseFlow>\n" + 
				"		<handler type='log' />\n" + 
				"	</responseFlow>\n" + 
				"</globalConfiguration>\n" + 
				"\n" + 
				"<handler name='log' type='java:org.apache.axis.handlers.LogHandler'>\n" + 
				"	<parameter name='LogHandler.fileName' value='target/axis1.log' />\n" + 
				"</handler>\n" + 
				"\n" + 
				"<transport name='http' pivot='java:org.apache.axis.transport.http.HTTPSender' />"
				);
		FileProvider fileProvider = new FileProvider(new ByteArrayInputStream(deployment.getBytes()));

		Object locator = ctr.newInstance(fileProvider);
		Object client = ReflectionTestUtils.invokeMethod(locator, locatorMethod, new URL("http://localhost:" + port + "/axis1/" + locatorMethod.substring(3)));
		ReflectionTestUtils.invokeMethod(client, "echo", param1Ctr.newInstance(), param2Ctr.newInstance());
	}
	
	/**
	 * @param style
	 * @param use
	 * @param tmVersion
	 * @param wrapArrays
	 */
	public static void echoWrappedEncoded(int port, String style, String use, String tmVersion, boolean wrapArrays) throws Exception {
		String packageFragment = String.format("%s_%s_%s_arrays%s", style.toLowerCase(), use.toLowerCase(), tmVersion.replace(".", ""), (wrapArrays ? "wrapped" : "plain"));
		String locatorMethod = String.format("getEchoEndpoint%s%s%s", StringUtils.capitalize(style.toLowerCase()), StringUtils.capitalize(use.toLowerCase()), tmVersion.replace(".", ""));
		Constructor<?> ctr = ClassUtils.getConstructorIfAvailable(ClassUtils.forName("org.springframework.ws.axis1.case2.contractfirst." + packageFragment + ".EchoEndpointServiceLocator", Case2Test.class.getClassLoader()), EngineConfiguration.class);
		Class<?> param1Class = ClassUtils.forName("org.springframework.ws.axis1.case2.contractfirst." + packageFragment + ".Param1", Case2Test.class.getClassLoader());
		Class<?> param2Class = ClassUtils.forName("org.springframework.ws.axis1.case2.contractfirst." + packageFragment + ".Param2", Case2Test.class.getClassLoader());
		Constructor<?> paramCtr = ClassUtils.getConstructorIfAvailable(ClassUtils.forName("org.springframework.ws.axis1.case2.contractfirst." + packageFragment + ".Echo", Case2Test.class.getClassLoader()), param1Class, param2Class);
		
		String deployment = FileCopyUtils.copyToString(new FileReader("src/test/java/org/springframework/ws/axis1/case2/contractfirst/" + packageFragment + "/deploy.wsdd"));
		deployment = deployment.replace("<!-- Services from EchoEndpointService WSDL service -->",
				"<globalConfiguration>\n" + 
						"	<parameter name='sendXsiTypes' value='true' />\n" + 
						"	<!--parameter name='sendMultiRefs' value='true' /-->\n" + 
						"	<parameter name='axis.sendMinimizedElements' value='true' />\n" + 
						"	<requestFlow>\n" + 
						"		<handler type='log' />\n" + 
						"	</requestFlow>\n" + 
						"	<responseFlow>\n" + 
						"		<handler type='log' />\n" + 
						"	</responseFlow>\n" + 
						"</globalConfiguration>\n" + 
						"\n" + 
						"<handler name='log' type='java:org.apache.axis.handlers.LogHandler'>\n" + 
						"	<parameter name='LogHandler.fileName' value='target/axis1.log' />\n" + 
						"</handler>\n" + 
						"\n" + 
						"<transport name='http' pivot='java:org.apache.axis.transport.http.HTTPSender' />"
				);
		FileProvider fileProvider = new FileProvider(new ByteArrayInputStream(deployment.getBytes()));
		
		Object locator = ctr.newInstance(fileProvider);
		Object client = ReflectionTestUtils.invokeMethod(locator, locatorMethod, new URL("http://localhost:" + port + "/axis1/" + locatorMethod.substring(3)));
		ReflectionTestUtils.invokeMethod(client, "echo", paramCtr.newInstance(param1Class.newInstance(), param2Class.newInstance()));
	}

	/**
	 * Generating all default artifacts
	 * 
	 * @param args
	 * @throws Exception
	 */
	public static void main(String[] args) throws Exception {
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
//		wsdl2java("DOCUMENT", "LITERAL", "1.1", false);
//		wsdl2java("DOCUMENT", "LITERAL", "1.1", true);
//		wsdl2java("DOCUMENT", "LITERAL", "1.2", false);
//		wsdl2java("DOCUMENT", "LITERAL", "1.2", true);
//		wsdl2java("DOCUMENT", "ENCODED", "1.1", false);
//		wsdl2java("DOCUMENT", "ENCODED", "1.1", true);
//		wsdl2java("DOCUMENT", "ENCODED", "1.2", false);
//		wsdl2java("DOCUMENT", "ENCODED", "1.2", true);
//		wsdl2java("RPC", "LITERAL", "1.1", false);
//		wsdl2java("RPC", "LITERAL", "1.1", true);
//		wsdl2java("RPC", "LITERAL", "1.2", false);
//		wsdl2java("RPC", "LITERAL", "1.2", true);
//		wsdl2java("RPC", "ENCODED", "1.1", false);
//		wsdl2java("RPC", "ENCODED", "1.1", true);
//		wsdl2java("RPC", "ENCODED", "1.2", false);
//		wsdl2java("RPC", "ENCODED", "1.2", true);
//		wsdl2java("WRAPPED", "LITERAL", "1.1", false);
//		wsdl2java("WRAPPED", "LITERAL", "1.1", true);
//		wsdl2java("WRAPPED", "LITERAL", "1.2", false);
//		wsdl2java("WRAPPED", "LITERAL", "1.2", true);
//		wsdl2java("WRAPPED", "ENCODED", "1.1", false);
//		wsdl2java("WRAPPED", "ENCODED", "1.1", true);
//		wsdl2java("WRAPPED", "ENCODED", "1.2", false);
//		wsdl2java("WRAPPED", "ENCODED", "1.2", true);
	}

	/**
	 * @param style
	 * @param use
	 * @param tmVersion
	 */
	@SuppressWarnings("unused")
	private static void wsdl2java(String style, String use, String tmVersion, boolean wrapArrays) {
		List<String> params = new LinkedList<String>();
		params.add("-o");
		params.add("src/test/java");
		params.add("-s");
		params.add("-p");
		params.add("org.springframework.ws.axis1.case2.contractfirst." + style.toLowerCase() + "_" + use.toLowerCase() + "_" + tmVersion.replace(".", "") + "_" + (wrapArrays ? "arrayswrapped" : "arraysplain"));
		params.add("-H");
		if (wrapArrays)
			params.add("-w");
		params.add("src/test/resources/org/springframework/ws/axis1/case2/codefirst/" + style.toLowerCase() + "_" + use.toLowerCase() +"/myservice-" + tmVersion + ".wsdl");
		WSDL2Java.main(params.toArray(new String[0]));
	}

	/**
	 * @param style
	 * @param use
	 * @param tmVersion
	 */
	@SuppressWarnings("unused")
	private static void java2wsdl(String style, String use, String tmVersion) {
		new File("src/test/resources/" + EchoEndpoint.class.getPackage().getName().replace(".", "/") + "/" + (style.toLowerCase() + "_" + use.toLowerCase())).mkdirs();
		// we use org.springframework.ws.axis1.case2.codefirst.EchoEndpoint - it is a POJO, although this class is not used as real server implementation. We must use
		// the generated versions, because of "*_Helper" classes containing Axis1 meta data.
		Java2WSDL.main(new String[] {
				EchoEndpoint.class.getName(),
				"-l",
				"http://axis1.org/ws/services/EchoEndpoint" + (StringUtils.capitalize(style.toLowerCase()) + (StringUtils.capitalize(use.toLowerCase())) + tmVersion.replace(".", "")),
				"-o", "src/test/resources/" + EchoEndpoint.class.getPackage().getName().replace(".", "/") + "/" + (style.toLowerCase() + "_" + use.toLowerCase()) + "/myservice-" + tmVersion + ".wsdl",
				"-A", "OPERATION",
				"-y", style,
				"-u", use,
				"-T", tmVersion,
				"-d"
		});
	}

}