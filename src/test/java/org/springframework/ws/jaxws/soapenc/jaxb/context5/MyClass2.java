/*
 * Copyright 2005-2012 the original author or authors.
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

package org.springframework.ws.jaxws.soapenc.jaxb.context5;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;

/**
 * <p></p>
 *
 * @author Grzegorz Grzybek
 */
@XmlAccessorType(XmlAccessType.FIELD)
public class MyClass2 {

	@XmlElement(name = "p", namespace = "urn:x")
	private String p;
	
	@XmlElement(name = "v", namespace = "urn:y")
	private String v;

	/**
	 * @return the p
	 */
	public String getP() {
		return this.p;
	}

	/**
	 * @param p the p to set
	 */
	public void setP(String p) {
		this.p = p;
	}

	/**
	 * @return the v
	 */
	public String getV() {
		return this.v;
	}

	/**
	 * @param v the v to set
	 */
	public void setV(String v) {
		this.v = v;
	}

}
