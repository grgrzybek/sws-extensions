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

package org.springframework.ws.cxf.dispatch1;

import org.junit.Test;
import org.springframework.ws.cxf.CxfIntegrationTests;

import static org.hamcrest.CoreMatchers.*;

import static org.junit.Assert.*;

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
				assertThat(service.echo1("test1"), is("[test1]"));
				assertThat(service.echo2("test2"), is("[test2]"));
				assertThat(service.echo3a("test3a"), is("[test3a]"));
				assertThat(service.echo3b("t3b1", "t3b2"), is("t3b1t3b2"));
				assertThat(service.echo3e("test3e"), is("[test3e]"));
				assertThat(service.echo3f("t3f1", "t3f2"), is("t3f1t3f2"));
				assertThat(service.echo4(4), is("[4]"));
			}

		}, EchoEndpointDocumentInterface.class);
	}

	@Test
	public void rpcStyleDispatch() throws Exception {

		super.isolatedCxfTest("DispatchTest", new CxfIntegrationTests.Callback<EchoEndpointRPCInterface>() {

			@Override
			public void perform(EchoEndpointRPCInterface service) {
				assertThat(service.echo3c("test3c"), is("[test3c]"));
				assertThat(service.echo3d("t3d1", "t3d2"), is("t3d1t3d2"));
				assertThat(service.echo3g("test3g"), is("[test3g]"));
				assertThat(service.echo3h("t3h1", "t3h2"), is("t3h1t3h2"));
			}

		}, EchoEndpointRPCInterface.class);
	}

}
