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

import org.springframework.ws.axis1.case2.model.Param1;
import org.springframework.ws.axis1.case2.model.Param2;
import org.springframework.ws.axis1.case2.model.Result1;

/**
 * <p></p>
 *
 * @author Grzegorz Grzybek
 */
public class EchoEndpointWrapped {

	public Result1 wrappedLit(Param1 param, Param2 param2) {
		return new Result1();
	}

	public Result1 wrappedEnc(Param1 param1, Param2 param2) {
		return new Result1();
	}

}
