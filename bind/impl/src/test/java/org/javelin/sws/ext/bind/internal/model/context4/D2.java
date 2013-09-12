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

package org.javelin.sws.ext.bind.internal.model.context4;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;

/**
 * <p></p>
 *
 * @author Grzegorz Grzybek
 */
@XmlAccessorType(XmlAccessType.PROPERTY)
@SuppressWarnings("unused")
public class D2 extends D1 {

	public String fd1 = "fd1";

	private String fd2 = "fd2";

	@XmlElement
	public String fd3 = "fd3";

	@XmlElement
	private String fd4 = "fd4";

	public String getP1() {
		return "getP1()";
	}

	public void setP1(String p1) {
	}

	private String getP2() {
		return "getP2()";
	}

	private void setP2(String p2) {
	}

	@XmlElement
	public String getP3() {
		return "getP3()";
	}

	public void setP3(String p3) {
	}

	private String getP4() {
		return "getP4()";
	}

	@XmlElement
	private void setP4(String p4) {
	}

	public void setP5(String p5) {
	}

	public void getP6(String p6) {
	}

	private void setP7(String p7) {
	}

	private void getP8(String p8) {
	}

}
