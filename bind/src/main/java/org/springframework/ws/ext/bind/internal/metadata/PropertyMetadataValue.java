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

package org.springframework.ws.ext.bind.internal.metadata;

import org.springframework.beans.PropertyValue;

/**
 * <p>TODO: documentation</p>
 *
 * @author Grzegorz Grzybek
 */
public class PropertyMetadataValue extends PropertyValue {

	private static final long serialVersionUID = -6048289089282459772L;

	private PropertyMetadata metadata;

	/**
	 * @param original
	 */
	public PropertyMetadataValue(PropertyValue original, PropertyMetadata metadata) {
		super(original);
		this.metadata = metadata;
	}

	/**
	 * @return the metadata
	 */
	public PropertyMetadata getMetadata() {
		return this.metadata;
	}

}
