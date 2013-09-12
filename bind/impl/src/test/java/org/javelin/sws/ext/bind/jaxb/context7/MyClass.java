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

package org.javelin.sws.ext.bind.jaxb.context7;

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
@XmlAccessorType(XmlAccessType.FIELD)
@SuppressWarnings("unused")
public class MyClass {

	private String[] tab1a = new String[] { "s1", "s2", null, "s3" };
	@XmlElement(nillable = false)
	private String[] tab1b = null;
	private List<String> tab2a = Arrays.asList(new String[] { "s1", null, "s2", "s3" });
	private List<String> tab2b = null;

	@XmlElementWrapper
	private String[] tab3 = new String[] { "s1", "s2", null, "s3" };
	@XmlElementWrapper
	private List<String> tab4a = Arrays.asList(new String[] { "s1", null, "s2", "s3" });
	@XmlElementWrapper(nillable = true)
	private List<String> tab4b = null;

}
