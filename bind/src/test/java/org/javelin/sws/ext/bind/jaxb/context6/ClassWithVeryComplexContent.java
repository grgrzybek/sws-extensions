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

package org.javelin.sws.ext.bind.jaxb.context6;

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
public class ClassWithVeryComplexContent {

	@XmlAttribute(name = "str-1")
	private String str;

	@XmlElement(name = "custom-element", namespace = "urn:inside:2")
	private String inside;
	
	@XmlElement(name = "custom-element2", namespace = "urn:inside:3")
	private ClassWithComplexContent inside2;

	/**
	 * 
	 */
	public ClassWithVeryComplexContent() {
	}

	/**
	 * @param str
	 * @param inside
	 * @param inside2
	 */
	public ClassWithVeryComplexContent(String str, String inside, ClassWithComplexContent inside2) {
		this.str = str;
		this.inside = inside;
		this.inside2 = inside2;
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

	/**
	 * @return the inside2
	 */
	public ClassWithComplexContent getInside2() {
		return this.inside2;
	}

	/**
	 * @param inside2 the inside2 to set
	 */
	public void setInside2(ClassWithComplexContent inside2) {
		this.inside2 = inside2;
	}

}
