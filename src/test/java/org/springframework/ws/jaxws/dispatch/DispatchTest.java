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

package org.springframework.ws.jaxws.dispatch;

import java.io.IOException;
import java.io.StringReader;

import javax.xml.stream.XMLStreamConstants;
import javax.xml.stream.XMLStreamException;
import javax.xml.stream.XMLStreamReader;
import javax.xml.transform.Source;
import javax.xml.transform.TransformerException;
import javax.xml.transform.stream.StreamSource;

import org.junit.Test;
import org.springframework.util.StringUtils;
import org.springframework.util.xml.StaxUtils;
import org.springframework.ws.client.core.SourceExtractor;
import org.springframework.ws.client.core.WebServiceTemplate;
import org.springframework.ws.jaxws.SwsIntegrationTests;
import org.springframework.ws.soap.axiom.AxiomSoapMessageFactory;

import static org.hamcrest.CoreMatchers.*;

import static org.junit.Assert.*;

/**
 * <p></p>
 *
 * @author Grzegorz Grzybek
 */
public class DispatchTest extends SwsIntegrationTests {

	@Test
	public void documentStyleDispatch() throws Exception {

		super.isolatedSwsTest("DispatchTest", new SwsIntegrationTests.Callback() {

			@Override
			public void perform(int port) {
				WebServiceTemplate wst = new WebServiceTemplate();
				wst.setMessageFactory(new AxiomSoapMessageFactory());
				String result = wst.sendSourceAndReceive("http://localhost:" + port + "/sws/ws", new StreamSource(new StringReader("<method xmlns=\"http://endpoints.dispatch.jaxws.ws.springframework.org/\">hello</method>")),
						new SourceExtractor<String>() {
							@Override
							public String extractData(Source source) throws IOException, TransformerException {
								XMLStreamReader reader = StaxUtils.getXMLStreamReader(source);
								String result = null;
								try {
									while (reader.hasNext()) {
										int ev = reader.next();
										if (ev == XMLStreamConstants.CHARACTERS && StringUtils.hasText(reader.getText())) {
											result = reader.getText();
											break;
										}
									}
									reader.close();
								}
								catch (XMLStreamException e) {
									throw new RuntimeException(e.getMessage(), e);
								}
								return result;
							}
						});
				assertThat(result, equalTo("((hello))"));
			}

		});
	}

}
