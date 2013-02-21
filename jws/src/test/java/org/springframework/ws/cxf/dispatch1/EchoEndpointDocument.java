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

import javax.jws.WebService;

import org.apache.cxf.feature.Features;

/**
 * <p></p>
 *
 * @author Grzegorz Grzybek
 */
@WebService(endpointInterface = "org.springframework.ws.cxf.dispatch1.EchoEndpointDocumentInterface")
@Features(features = "org.apache.cxf.feature.LoggingFeature")
public class EchoEndpointDocument implements EchoEndpointDocumentInterface {

	/* (non-Javadoc)
	 * @see org.springframework.ws.cxf.dispatch1.EchoEndpointInterface#echo1(java.lang.String)
	 */
	@Override
	public String echo1(String param) {
		return "[" + param + "]";
	}

	/* (non-Javadoc)
	 * @see org.springframework.ws.cxf.dispatch1.EchoEndpointInterface#echo2(java.lang.String)
	 */
	@Override
	public String echo2(String param) {
		return "[" + param + "]";
	}

	/* (non-Javadoc)
	 * @see org.springframework.ws.cxf.dispatch1.EchoEndpointInterface#echo3(java.lang.String)
	 */
	@Override
	public String echo3a(String param) {
		return "[" + param + "]";
	}

	/* (non-Javadoc)
	 * @see org.springframework.ws.cxf.dispatch1.EchoEndpointInterface#echo3b(java.lang.String, java.lang.String)
	 */
	@Override
	public String echo3b(String param1, String param2) {
		return param1 + param2;
	}

	/* (non-Javadoc)
	 * @see org.springframework.ws.cxf.dispatch1.EchoEndpointInterface#echo3e(java.lang.String)
	 */
	@Override
	public String echo3e(String param) {
		return "[" + param + "]";
	}

	/* (non-Javadoc)
	 * @see org.springframework.ws.cxf.dispatch1.EchoEndpointInterface#echo3f(java.lang.String, java.lang.String)
	 */
	@Override
	public String echo3f(String param1, String param2) {
		return param1 + param2;
	}

	/* (non-Javadoc)
	 * @see org.springframework.ws.cxf.dispatch1.EchoEndpointInterface#echo4(int)
	 */
	@Override
	public String echo4(int param) {
		return "[" + param + "]";
	}

}
