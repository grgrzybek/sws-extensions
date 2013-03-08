/*
 * Copyright 2005-2011 the original author or authors.
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

package org.springframework.ws.ext.utils;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import org.springframework.util.Assert;
import org.springframework.util.StringUtils;

/**
 * <p></p>
 *
 * @author Grzegorz Grzybek
 */
public abstract class NamespaceUtils {

	private NamespaceUtils() {
	}

	/**
	 * Converts package name to URL for namespace according to JAXB/JAX-WS conventions
	 * 
	 * @param pkg
	 * @return
	 */
	public static String packageNameToNamespace(Package pkg) {
		Assert.notNull(pkg, "Package should not be null");
		List<String> elements = new ArrayList<String>(Arrays.asList(pkg.getName().split("\\.")));
		Collections.reverse(elements);
		return "http://" + StringUtils.collectionToDelimitedString(elements, ".") + "/";
	}

}
