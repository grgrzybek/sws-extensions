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

package org.javelin.sws.ext.bind;

import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.namespace.QName;
import javax.xml.stream.events.XMLEvent;

import org.javelin.sws.ext.bind.internal.model.TypedPattern;

/**
 * <p></p>
 *
 * @author Grzegorz Grzybek
 */
public interface TypedPatternRegistry {

	/**
	 * <p>Finds a representation of a class and it's (JAXB2) metadata as a <i>pattern</i> of static and dynamic {@link XMLEvent XML events}. If no
	 * pattern is found, {@code null} is returned</p>
	 * 
	 * @param clazz
	 * @return
	 */
	public <T> TypedPattern<T> findPatternByClass(Class<T> clazz);

	/**
	 * <p>Get a representation of a XSD type as a <i>pattern</i> of static and dynamic {@link XMLEvent XML events}. It returns {@code null}
	 * if such mapping is not (yet) present.</p>
	 * 
	 * @param typeName
	 * @param cl parameter for generic type deduction
	 * @return
	 */
	public <T> TypedPattern<T> findPatternByType(QName typeName, Class<T> cl);

	/**
	 * <p>Get a representation of a class and it's (JAXB2) metadata as a <i>pattern</i> of static and dynamic {@link XMLEvent XML events}.</p>
	 * 
	 * <p>A class which is to be known by the registry should be directly convertible to a series of XML events. What is not mandatory here is
	 * the root element of the marshalled object.</p>
	 * 
	 * <p>The produced pattern is automatically cached in the registry. Also the metadata for {@link XmlRootElement} annotated classes is cached.</p>
	 * 
	 * <p>DESIGNFLAW: this should not be a part of this interface</p>
	 * 
	 * @param cl
	 * @return
	 */
	public <T> TypedPattern<T> determineAndCacheXmlPattern(Class<T> cl);

}
