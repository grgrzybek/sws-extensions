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

package org.javelin.sws.ext.bind.context5a;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlType;
import javax.xml.bind.annotation.XmlValue;

/**
 * <p></p>
 *
 * @author Grzegorz Grzybek
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "ASD", namespace = "urn:x")
public class D1 {

	@XmlAttribute
	private long att;

	@XmlValue
	private D2 d2;

	/**
	 * @return the d2
	 */
	public D2 getD2() {
		return this.d2;
	}

	/**
	 * @param d2 the d2 to set
	 */
	public void setD2(D2 d2) {
		this.d2 = d2;
	}

	/**
	 * @return the att
	 */
	public long getAtt() {
		return this.att;
	}

	/**
	 * @param att the att to set
	 */
	public void setAtt(long att) {
		this.att = att;
	}

}
