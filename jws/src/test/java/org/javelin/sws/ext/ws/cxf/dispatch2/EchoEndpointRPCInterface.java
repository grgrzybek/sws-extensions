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

import javax.jws.WebParam;
import javax.jws.WebService;
import javax.jws.soap.SOAPBinding;
import javax.jws.soap.SOAPBinding.ParameterStyle;
import javax.jws.soap.SOAPBinding.Style;
import javax.jws.soap.SOAPBinding.Use;

import org.springframework.ws.cxf.dispatch2.model.Param1;
import org.springframework.ws.cxf.dispatch2.model.Param2;
import org.springframework.ws.cxf.dispatch2.model.Result1;

/**
 * <p></p>
 *
 * @author Grzegorz Grzybek
 */
@WebService
public interface EchoEndpointRPCInterface {

	@SOAPBinding(parameterStyle = ParameterStyle.BARE, style = Style.RPC, use = Use.LITERAL)
	public Result1 rpcLitBare(@WebParam Param1 param);

	@SOAPBinding(parameterStyle = ParameterStyle.WRAPPED, style = Style.RPC, use = Use.LITERAL)
	public Result1 rpcLitWrapped(@WebParam Param1 param1, @WebParam Param2 param2);

	@SOAPBinding(parameterStyle = ParameterStyle.BARE, style = Style.RPC, use = Use.ENCODED)
	public Result1 rpcEncBare(@WebParam Param1 param);

	@SOAPBinding(parameterStyle = ParameterStyle.WRAPPED, style = Style.RPC, use = Use.ENCODED)
	public Result1 rpcEncWrapped(@WebParam Param1 param1, @WebParam Param2 param2);

}
