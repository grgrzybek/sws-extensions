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

package org.springframework.ws.ext.bind.jaxb.context8;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlElement;

/**
 * <p></p>
 *
 * @author Grzegorz Grzybek
 */
@XmlAccessorType(XmlAccessType.FIELD)
public class MyClass {

	@XmlElement(nillable = false)
	private MyClass mc = null;
	@XmlAttribute
	private String att = "x";

	/**
	 * 
	 */
	public MyClass() {
	}

	/**
	 * @param mc
	 * @param att
	 */
	public MyClass(MyClass mc, String att) {
		this.mc = mc;
		this.att = att;
	}

	/**
	 * @return the mc
	 */
	public MyClass getMc() {
		return this.mc;
	}

	/**
	 * @param mc the mc to set
	 */
	public void setMc(MyClass mc) {
		this.mc = mc;
	}

	/**
	 * @return the att
	 */
	public String getAtt() {
		return this.att;
	}

	/**
	 * @param att the att to set
	 */
	public void setAtt(String att) {
		this.att = att;
	}

}
