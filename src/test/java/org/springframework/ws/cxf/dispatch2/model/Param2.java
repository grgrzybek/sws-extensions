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

package org.springframework.ws.cxf.dispatch2.model;

import java.math.BigDecimal;
import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;

import javax.xml.bind.annotation.XmlType;

import org.springframework.ws.axis1.dispatch2.model.Param3;

/**
 * <p></p>
 *
 * @author Grzegorz Grzybek
 */
@XmlType(propOrder = {
		"bd",
		"list",
		"param3list",
		"number"
})
public class Param2 {

	private BigDecimal bd = new BigDecimal("37.98");

	private List<String> list = new LinkedList<String>(Arrays.asList("str1", "str2", "str3"));

	private List<Param3> param3list = new LinkedList<Param3>(Arrays.asList(new Param3(), new Param3(), new Param3()));

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
	public List<String> getList() {
		return this.list;
	}

	/**
	 * @param list the list to set
	 */
	public void setList(List<String> list) {
		this.list = list;
	}

	/**
	 * @return the param3list
	 */
	public List<Param3> getParam3list() {
		return this.param3list;
	}

	/**
	 * @param param3list the param3list to set
	 */
	public void setParam3list(List<Param3> param3list) {
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
