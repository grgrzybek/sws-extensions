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
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.springframework.util.Assert;
import org.springframework.util.StringUtils;

/**
 * <p></p>
 *
 * @author Grzegorz Grzybek
 */
public abstract class NamespaceUtils {

	private static Set<String> noDomainComponents = new HashSet<String>();
	
	static {
		noDomainComponents.add("www");
	}

	private NamespaceUtils() {
	}

	/**
	 * Converts package name to URL for namespace according to JAXB/JAX-WS conventions
	 * 
	 * @param pkg
	 * @return
	 */
	public static String packageNameToNamespace(Package pkg) {
		return packageNameToNamespace(pkg, 0);
	}

	/**
	 * Converts package name to URL for namespace according to JAXB/JAX-WS conventions with separate domain and path fragments
	 * 
	 * @param pkg
	 * @param domainComponentCount number of package components to be converted into domain part of URL. If zero than entire package will be a domain
	 * @return
	 */
	public static String packageNameToNamespace(Package pkg, int domainComponentCount) {
		Assert.notNull(pkg, "Package should not be null");
		Assert.isTrue(domainComponentCount != 1, "The domain part should not consist of one component. It may be zero or more than 1.");

		List<String> elements = new ArrayList<String>(Arrays.asList(pkg.getName().split("\\.")));
		if (domainComponentCount > 0) {
			List<String> domain = elements.subList(0, domainComponentCount);
			List<String> path = elements.subList(domainComponentCount, elements.size());
			Collections.reverse(domain);
			return "http://" + StringUtils.collectionToDelimitedString(domain, ".") + "/" + StringUtils.collectionToDelimitedString(path, "/");
		} else {
			Collections.reverse(elements);
			return "http://" + StringUtils.collectionToDelimitedString(elements, ".") + "/";
		}
	}
}
