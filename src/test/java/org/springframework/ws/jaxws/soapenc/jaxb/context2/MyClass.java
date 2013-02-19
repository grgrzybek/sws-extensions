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

package org.springframework.ws.jaxws.soapenc.jaxb.context2;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;

import org.springframework.ws.jaxws.soapenc.jaxb.context0.MyProperty2;

/**
 * <p></p>
 *
 * @author Grzegorz Grzybek
 */
@XmlAccessorType(XmlAccessType.FIELD)
public class MyClass {

	@XmlElement(name = "p", namespace = "urn:x")
	private MyProperty2 p;

	/**
	 * @return the p
	 */
	public MyProperty2 getP() {
		return this.p;
	}

	/**
	 * @param p the p to set
	 */
	public void setP(MyProperty2 p) {
		this.p = p;
	}

}
