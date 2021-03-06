/*
 * Copyright 2005-2012 the original author or authors.
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

package org.springframework.ws.jaxws.matrix;

import javax.jws.WebService;

/**
 * <p></p>
 *
 * @author Grzegorz Grzybek
 */
@WebService
public interface Port03 {

	public String hello1(String param);
	public String hello2(String param1, String param2);
	public String hello3(String param1, String param2) throws IllegalArgumentException;

}
