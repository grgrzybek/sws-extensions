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

package org.javelin.sws.ext.ws.axis1.jira;

import java.io.ByteArrayInputStream;
import java.io.FileReader;
import java.net.URL;
import java.util.LinkedList;
import java.util.List;

import org.apache.axis.configuration.FileProvider;
import org.apache.axis.wsdl.WSDL2Java;
import org.javelin.sws.ext.ws.axis1.Axis1IntegrationTests;
import org.javelin.sws.ext.ws.axis1.jira.contractfirst.tm12_arraysplain.JiraSoapService;
import org.javelin.sws.ext.ws.axis1.jira.contractfirst.tm12_arraysplain.JiraSoapServiceServiceLocator;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.util.FileCopyUtils;

/**
 * <p></p>
 *
 * @author Grzegorz Grzybek
 */
public class JiraTest extends Axis1IntegrationTests {

	private static Logger log = LoggerFactory.getLogger(JiraTest.class.getName());

	@Test
	public void jiraWebService() throws Exception {

		super.isolatedAxis1Test(new Callback() {
			@Override
			public void perform(int port) throws Exception {

				try {
					log.info("start");
					List<String> errors = new LinkedList<String>();
					
					String deployment = FileCopyUtils.copyToString(new FileReader("src/test/java/org/javelin/sws/ext/ws/axis1/jira/contractfirst/tm12_arraysplain/deploy.wsdd"));
					deployment = deployment.replace("<!-- Services from JiraSoapServiceService WSDL service -->",
							"<globalConfiguration>\n"
							+ "	<parameter name='sendXsiTypes' value='true' />\n"
							+ "	<!--parameter name='sendMultiRefs' value='true' /-->\n"
							+ "	<parameter name='axis.sendMinimizedElements' value='true' />\n"
							+ "	<requestFlow>\n"
							+ "		<handler type='log' />\n"
							+ "	</requestFlow>\n"
							+ "	<responseFlow>\n"
							+ "		<handler type='log' />\n"
							+ "	</responseFlow>\n"
							+ "</globalConfiguration>\n"
							+ "\n"
							+ "<handler name='log' type='java:org.apache.axis.handlers.LogHandler'>\n"
							+ "	<parameter name='LogHandler.fileName' value='target/axis1.log' />\n"
							+ "</handler>\n"
							+ "\n"
							+ "<transport name='http' pivot='java:org.apache.axis.transport.http.HTTPSender' />");
					FileProvider fileProvider = new FileProvider(new ByteArrayInputStream(deployment.getBytes()));
					JiraSoapServiceServiceLocator locator = new JiraSoapServiceServiceLocator(fileProvider);
					JiraSoapService service = locator.getJirasoapserviceV2(new URL("http://localhost:" + port + "/axis1/jirasoapservice-v2"));
					/*RemoteIssueType[] types = */service.getIssueTypes(null);
					
					for (String error : errors)
						log.error(error);
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
//		wsdl2java("1.1", false);
//		wsdl2java("1.1", true);
//		wsdl2java("1.2", false);
//		wsdl2java("1.2", true);
	}

	/**
	 * @param style
	 * @param use
	 * @param tmVersion
	 */
	@SuppressWarnings("unused")
	private static void wsdl2java(String tmVersion, boolean wrapArrays) {
		List<String> params = new LinkedList<String>();
		params.add("-o");
		params.add("src/test/java");
		params.add("-s");
		params.add("-p");
		params.add("org.javelin.sws.ext.ws.axis1.jira.contractfirst.tm" + tmVersion.replace(".", "") + "_" + (wrapArrays ? "arrayswrapped" : "arraysplain"));
		params.add("-H");
		if (wrapArrays)
			params.add("-w");
		params.add("-T");
		params.add(tmVersion);
		params.add("src/test/resources/org/javelin/sws/ext/ws/axis1/jira/jirasoapservice-v2.wsdl");
		WSDL2Java.main(params.toArray(new String[0]));
	}

}
