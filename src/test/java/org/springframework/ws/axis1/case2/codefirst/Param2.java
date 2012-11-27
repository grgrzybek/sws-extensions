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

import java.math.BigDecimal;
import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;

/**
 * <p></p>
 *
 * @author Grzegorz Grzybek
 */
public class Param2 {

	private BigDecimal bd = new BigDecimal("37.98");

	private String[] list = new String[] { "str1", "str2", "str3" };

	private Param3[] param3Tab = new Param3[] { new Param3(), new Param3(), new Param3() };
	private List<Param3> param3List = new LinkedList<Param3>(Arrays.asList(new Param3[] { new Param3(), new Param3() }));

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
	 * @return the param3Tab
	 */
	public Param3[] getParam3Tab() {
		return this.param3Tab;
	}

	/**
	 * @param param3Tab the param3Tab to set
	 */
	public void setParam3Tab(Param3[] param3Tab) {
		this.param3Tab = param3Tab;
	}

	/**
	 * @return the param3List
	 */
	public List<Param3> getParam3List() {
		return this.param3List;
	}

	/**
	 * @param param3List the param3List to set
	 */
	public void setParam3List(List<Param3> param3List) {
		this.param3List = param3List;
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
