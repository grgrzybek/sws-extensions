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

package org.javelin.sws.ext.bind.context3;

import javax.xml.bind.annotation.XmlElement;

/**
 * <p></p>
 *
 * @author Grzegorz Grzybek
 */
public class MyClass2 {

	@XmlElement
	private MyClass3 c3;

	/**
	 * @return the c3
	 */
	public MyClass3 getC3() {
		return this.c3;
	}

	/**
	 * @param c3 the c3 to set
	 */
	public void setC3(MyClass3 c3) {
		this.c3 = c3;
	}
	
}
