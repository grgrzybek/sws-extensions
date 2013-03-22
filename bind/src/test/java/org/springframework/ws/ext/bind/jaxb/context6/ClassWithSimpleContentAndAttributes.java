/*
 * Copyright 2005-2013 the original author or authors.
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

package org.springframework.ws.ext.bind.jaxb.context6;

import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlValue;

/**
 * <p></p>
 *
 * @author Grzegorz Grzybek
 */
public class ClassWithSimpleContentAndAttributes {

	@XmlAttribute
	private String str;

	@XmlAttribute
	private int number;

	@XmlValue
	private String inside;

	/**
	 * @param str
	 * @param number
	 * @param inside
	 */
	public ClassWithSimpleContentAndAttributes(String str, int number, String inside) {
		this.str = str;
		this.number = number;
		this.inside = inside;
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

	/**
	 * @return the number
	 */
	public int getNumber() {
		return this.number;
	}

	/**
	 * @param number the number to set
	 */
	public void setNumber(int number) {
		this.number = number;
	}

	/**
	 * @return the inside
	 */
	public String getInside() {
		return this.inside;
	}

	/**
	 * @param inside the inside to set
	 */
	public void setInside(String inside) {
		this.inside = inside;
	}

}
