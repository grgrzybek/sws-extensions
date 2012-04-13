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

package org.springframework.ws.axis1.dispatch2.model;

import java.math.BigDecimal;

/**
 * <p></p>
 *
 * @author Grzegorz Grzybek
 */
public class Result1 {

	private BigDecimal[] list = new BigDecimal[] { new BigDecimal("890.123123"), new BigDecimal("0.12") };

	private Param2 param2 = new Param2();

	private String str = "part of result";

	/**
	 * @return the list
	 */
	public BigDecimal[] getList() {
		return this.list;
	}

	/**
	 * @param list the list to set
	 */
	public void setList(BigDecimal[] list) {
		this.list = list;
	}

	/**
	 * @return the param2
	 */
	public Param2 getParam2() {
		return this.param2;
	}

	/**
	 * @param param2 the param2 to set
	 */
	public void setParam2(Param2 param2) {
		this.param2 = param2;
	}

	/**
	 * @return the str
	 */
	public String getStr() {
		return this.str;
	}

	/**
	 * @param str the str to set
	 */
	public void setStr(String str) {
		this.str = str;
	}

}
