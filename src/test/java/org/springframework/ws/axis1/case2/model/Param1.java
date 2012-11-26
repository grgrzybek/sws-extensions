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

/**
 * <p></p>
 *
 * @author Grzegorz Grzybek
 */
public class Param1 {

	private String string = "str (String)";

	private Long number = 37L;

	private Param2 param2 = new Param2();

	private Byte[] list = new Byte[] { (byte) 0x24, (byte) 0x9A };

	private Param3[] param3list = new Param3[] { new Param3(), new Param3(), new Param3() };

	/**
	 * @return the string
	 */
	public String getString() {
		return this.string;
	}

	/**
	 * @param string the string to set
	 */
	public void setString(String string) {
		this.string = string;
	}

	/**
	 * @return the number
	 */
	public Long getNumber() {
		return this.number;
	}

	/**
	 * @param number the number to set
	 */
	public void setNumber(Long number) {
		this.number = number;
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
	 * @return the list
	 */
	public Byte[] getList() {
		return this.list;
	}

	/**
	 * @param list the list to set
	 */
	public void setList(Byte[] list) {
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

}
