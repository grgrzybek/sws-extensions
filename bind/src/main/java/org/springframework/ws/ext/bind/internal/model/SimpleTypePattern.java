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

package org.springframework.ws.ext.bind.internal.model;

import org.springframework.core.convert.ConversionService;

/**
 * <p>SimpleTypePattern accepts {@link String} values. The conversion happens elsewhere.</p>
 *
 * @author Grzegorz Grzybek
 */
public interface SimpleTypePattern extends XmlEventsPattern {

	/**
	 * Simple types require conversion service, to be able to convert objects to Strings.
	 * DESIGNFLAW: we may have a matrix of simpleTypePattern/propertyPattern and we have to decide what will be abstract class and what an interface
	 */
	public void setConversionService(ConversionService conversionService);

}
