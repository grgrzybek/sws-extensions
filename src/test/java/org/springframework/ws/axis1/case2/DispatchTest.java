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

import javax.xml.namespace.QName;
import javax.xml.rpc.Call;

import org.apache.axis.client.Service;
import org.apache.axis.configuration.FileProvider;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.ws.axis1.Axis1IntegrationTests;
import org.springframework.ws.axis1.case2.model.Param1;
import org.springframework.ws.axis1.case2.model.Param2;

/**
 * <p></p>
 *
 * @author Grzegorz Grzybek
 */
public class DispatchTest extends Axis1IntegrationTests {

	private static Logger log = LoggerFactory.getLogger(DispatchTest.class.getName());

	@Test
	public void axis1WebServices() throws Exception {

		super.isolatedAxis1Test(new Callback() {
			@Override
			public void perform(int port) throws Exception {

				FileProvider clientConfig = new FileProvider("src/test/resources/org/springframework/ws/axis1/WEB-INF", "client-config.wsdd");
				Service service = new Service(clientConfig);

				try {
					Call call = service.createCall(new QName("http://dispatch2.axis1.ws.springframework.org/", "EchoEndpointRPCLiteral"), "rpcLit");
					call.setProperty("transport_name", "http");
					call.setTargetEndpointAddress("http://localhost:" + port + "/axis1/EchoEndpointRPCLiteral");
					Object result = call.invoke(new Object[] { new Param1(), new Param2() });
					Axis1IntegrationTests.log.info("Result: {}", result.toString());
					log.info("Result: {}", result);
					System.out.println(result);
				}
				catch (Exception e) {
					log.error(e.getMessage(), e);
				}
			}
		});

	}

}
