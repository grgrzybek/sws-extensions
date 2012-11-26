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

package org.springframework.ws.axis1.case2.model;

import java.math.BigDecimal;

/**
 * <p></p>
 *
 * @author Grzegorz Grzybek
 */
public class Param2 {

	private BigDecimal bd = new BigDecimal("37.98");

	private String[] list = new String[] { "str1", "str2", "str3" };

	private Param3[] param3list = new Param3[] { new Param3(), new Param3(), new Param3() };

	private Integer number = 44;

	/**
	 * @return the bd
	 */
	public BigDecimal getBd() {
		return this.bd;
	}

	/**
	 * @param bd the bd to set
	 */
	public void setBd(BigDecimal bd) {
		this.bd = bd;
	}

	/**
	 * @return the list
	 */
	public String[] getList() {
		return this.list;
	}

	/**
	 * @param list the list to set
	 */
	public void setList(String[] list) {
		this.list = list;
	}

	/**
	 * @return the param3list
	 */
	public Param3[] getParam3list() {
		return this.param3list;
	}

	/**
	 * @param param3list the param3list to set
	 */
	public void setParam3list(Param3[] param3list) {
		this.param3list = param3list;
	}

	/**
	 * @return the number
	 */
	public Integer getNumber() {
		return this.number;
	}

	/**
	 * @param number the number to set
	 */
	public void setNumber(Integer number) {
		this.number = number;
	}

}
