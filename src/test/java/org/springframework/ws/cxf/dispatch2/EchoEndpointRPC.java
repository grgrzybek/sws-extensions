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

import javax.jws.WebService;

import org.apache.cxf.feature.Features;
import org.springframework.ws.cxf.dispatch2.model.Param1;
import org.springframework.ws.cxf.dispatch2.model.Param2;
import org.springframework.ws.cxf.dispatch2.model.Result1;

/**
 * <p></p>
 *
 * @author Grzegorz Grzybek
 */
@WebService(endpointInterface = "org.springframework.ws.cxf.dispatch2.EchoEndpointRPCInterface")
@Features(features = "org.apache.cxf.feature.LoggingFeature")
public class EchoEndpointRPC implements EchoEndpointRPCInterface {

	/* (non-Javadoc)
	 * @see org.springframework.ws.cxf.dispatch2.EchoEndpointRPCInterface#echo3c(org.springframework.ws.cxf.dispatch2.model.Param1)
	 */
	@Override
	public Result1 rpcLitBare(Param1 param) {
		return new Result1();
	}

	/* (non-Javadoc)
	 * @see org.springframework.ws.cxf.dispatch2.EchoEndpointRPCInterface#echo3d(org.springframework.ws.cxf.dispatch2.model.Param1, org.springframework.ws.cxf.dispatch2.model.Param2)
	 */
	@Override
	public Result1 rpcLitWrapped(Param1 param1, Param2 param2) {
		return new Result1();
	}

	/* (non-Javadoc)
	 * @see org.springframework.ws.cxf.dispatch2.EchoEndpointRPCInterface#echo3g(org.springframework.ws.cxf.dispatch2.model.Param1)
	 */
	@Override
	public Result1 rpcEncBare(Param1 param) {
		return new Result1();
	}

	/* (non-Javadoc)
	 * @see org.springframework.ws.cxf.dispatch2.EchoEndpointRPCInterface#echo3h(org.springframework.ws.cxf.dispatch2.model.Param1, org.springframework.ws.cxf.dispatch2.model.Param2)
	 */
	@Override
	public Result1 rpcEncWrapped(Param1 param1, Param2 param2) {
		return new Result1();
	}

}
