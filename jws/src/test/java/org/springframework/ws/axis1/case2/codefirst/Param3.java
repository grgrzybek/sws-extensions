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

package org.springframework.ws.axis1.case2.codefirst;

/**
 * <p></p>
 *
 * @author Grzegorz Grzybek
 */
public class Param3 {

	private String svalue = "value";

	private Long lvalue = 3L;

	/**
	 * @return the svalue
	 */
	public String getSvalue() {
		return this.svalue;
	}

	/**
	 * @param svalue the svalue to set
	 */
	public void setSvalue(String svalue) {
		this.svalue = svalue;
	}

	/**
	 * @return the lvalue
	 */
	public Long getLvalue() {
		return this.lvalue;
	}

	/**
	 * @param lvalue the lvalue to set
	 */
	public void setLvalue(Long lvalue) {
		this.lvalue = lvalue;
	}

}
