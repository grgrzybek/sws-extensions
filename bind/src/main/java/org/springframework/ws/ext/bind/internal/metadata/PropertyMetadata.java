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

import org.springframework.ws.ext.bind.internal.model.XmlEventsPattern;

/**
 * <p>Information on what {@link XmlEventsPattern} is configured for a given property (direct or getter) of marshalled/unmarshalled object</p>
 *
 * @author Grzegorz Grzybek
 */
public class PropertyMetadata {

	/** Name of field or bean property */
	private String propertyName;

	/** Is it field ({@code true}) or getter ({@code false} property? */
	private boolean directProperty;

	/** {@link XmlEventsPattern} to which a given property maps. */
	private XmlEventsPattern pattern;

	/**
	 * @param propertyName
	 * @param directProperty
	 */
	public PropertyMetadata(String propertyName, boolean directProperty) {
		this.propertyName = propertyName;
		this.directProperty = directProperty;
	}

	/**
	 * @param propertyName
	 * @param directProperty
	 * @param pattern
	 */
	public PropertyMetadata(String propertyName, boolean directProperty, XmlEventsPattern pattern) {
		this.propertyName = propertyName;
		this.directProperty = directProperty;
		this.pattern = pattern;
	}

	/**
	 * @return the pattern
	 */
	public XmlEventsPattern getPattern() {
		return this.pattern;
	}

	/**
	 * @param pattern the pattern to set
	 */
	public void setPattern(XmlEventsPattern pattern) {
		this.pattern = pattern;
	}

	/**
	 * @return the propertyName
	 */
	public String getPropertyName() {
		return this.propertyName;
	}

	/**
	 * @return the directProperty
	 */
	public boolean isDirectProperty() {
		return this.directProperty;
	}

}
