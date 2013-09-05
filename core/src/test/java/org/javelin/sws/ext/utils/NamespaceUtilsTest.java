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

package org.javelin.sws.ext.utils;

import org.junit.Test;

import static org.hamcrest.CoreMatchers.*;

import static org.junit.Assert.*;

/**
 * <p></p>
 *
 * @author Grzegorz Grzybek
 */
public class NamespaceUtilsTest {

	@Test
	public void package1() {
		assertThat(NamespaceUtils.packageNameToNamespace(this.getClass().getPackage()), equalTo("http://utils.ext.sws.javelin.org/"));
	}

	@Test
	public void package1WithPath() {
		assertThat(NamespaceUtils.packageNameToNamespace(this.getClass().getPackage(), 2), equalTo("http://javelin.org/sws/ext/utils"));
		assertThat(NamespaceUtils.packageNameToNamespace(this.getClass().getPackage(), 3), equalTo("http://sws.javelin.org/ext/utils"));
	}

	@Test(expected = IllegalArgumentException.class)
	public void package2WithIllegalDomain() {
		NamespaceUtils.packageNameToNamespace(this.getClass().getPackage(), 1);
	}

	@Test
	public void package2() {
		assertThat(NamespaceUtils.packageNameToNamespace(String.class.getPackage()), equalTo("http://lang.java/"));
	}

	@Test(expected = IllegalArgumentException.class)
	public void emptyPackage() {
		NamespaceUtils.packageNameToNamespace(Package.getPackage(""));
	}

}
