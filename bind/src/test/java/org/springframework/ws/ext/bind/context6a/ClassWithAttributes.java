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

package org.springframework.ws.ext.bind.context6a;

import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlNsForm;

/**
 * <p></p>
 *
 * @author Grzegorz Grzybek
 */
public class ClassWithAttributes {

	@XmlAttribute
	// TODO: handle declaration of @form attribute of xs:attribute declaration
	@org.springframework.ws.ext.bind.annotations.XmlAttribute(form = XmlNsForm.QUALIFIED)
	private String str;

	@XmlAttribute
	private int number;

	/**
	 * @param str
	 * @param number
	 */
	public ClassWithAttributes(String str, int number) {
		this.str = str;
		this.number = number;
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
}
