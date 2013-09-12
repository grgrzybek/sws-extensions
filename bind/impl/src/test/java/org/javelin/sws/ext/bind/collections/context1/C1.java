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

package org.javelin.sws.ext.bind.collections.context1;

import java.util.Arrays;
import java.util.List;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlElementWrapper;

/**
 * <p></p>
 *
 * @author Grzegorz Grzybek
 */
@XmlAccessorType(XmlAccessType.NONE)
public class C1 {

	@XmlElement
	public List<String> l1 = Arrays.asList("el1", "el2", "el3");

	private List<Integer> l2 = Arrays.asList(1, 3, 6);

	private List<Integer> l3 = Arrays.asList(2, 5, 77);

	@XmlElement
	private C2 c2 = new C2();
	
	@XmlElement
	private C3 c3 = new C3();

	/**
	 * @return the l2
	 */
	@XmlElement
	public List<Integer> getL2() {
		return this.l2;
	}

	/**
	 * @return the l3
	 */
	@XmlElementWrapper(name = "l3-wrapper", namespace = "w")
	@XmlElement
	public List<Integer> getL3() {
		return this.l3;
	}

}
