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

package org.javelin.sws.ext.bind.internal.model;

import javax.xml.namespace.QName;
import javax.xml.stream.XMLEventReader;
import javax.xml.stream.XMLEventWriter;
import javax.xml.stream.XMLStreamException;

import org.javelin.sws.ext.bind.internal.MarshallingContext;
import org.javelin.sws.ext.bind.internal.UnmarshallingContext;

/**
 * <p>A {@link TypedPattern} acting as a temporary mapping of class to a series of XML events. Needed when two classes
 * have properties of one another's class or a class contains the properties of the same class (weird, but possible).</p>
 * 
 * <p>After the final determination of mapped pattern it will be injected into this pattern. MultiRef Encodings (more likely to have
 * interdependent properties) will <b>not</b> result in replaying this pattern, because multiRefs will be used since first occurence.</p>
 *
 * @author Grzegorz Grzybek
 */
public class TemporaryTypedPattern<T> extends TypedPattern<T> {

	private TypedPattern<T> realPattern;

	/**
	 * @param schemaType
	 * @param javaType
	 */
	private TemporaryTypedPattern(QName schemaType, Class<T> javaType) {
		super(schemaType, javaType);
	}

	/**
	 * @param schemaType
	 * @param javaType
	 * @return
	 */
	public static <T> TemporaryTypedPattern<T> newTemporaryTypedPattern(QName schemaType, Class<T> javaType) {
		return new TemporaryTypedPattern<T>(schemaType, javaType);
	}

	/**
	 * @param pattern
	 */
	public void setRealPattern(TypedPattern<T> pattern) {
		this.realPattern = pattern;
	}

	/* (non-Javadoc)
	 * @see org.javelin.sws.ext.bind.internal.model.TypedPattern#isSimpleType()
	 */
	@Override
	public boolean isSimpleType() {
		return this.realPattern.isSimpleType();
	}

	/* (non-Javadoc)
	 * @see org.javelin.sws.ext.bind.internal.model.TypedPattern#replayValue(java.lang.Object, javax.xml.stream.XMLEventWriter, org.javelin.sws.ext.bind.internal.MarshallingContext)
	 */
	@Override
	public void replayValue(T value, XMLEventWriter eventWriter, MarshallingContext context) throws XMLStreamException {
		this.realPattern.replayValue(value, eventWriter, context);
	}

	/* (non-Javadoc)
	 * @see org.javelin.sws.ext.bind.internal.model.TypedPattern#consumeValue(javax.xml.stream.XMLEventReader, org.javelin.sws.ext.bind.internal.UnmarshallingContext)
	 */
	@Override
	public T consumeValue(XMLEventReader eventReader, UnmarshallingContext context) throws XMLStreamException {
		return this.realPattern.consumeValue(eventReader, context);
	}

	/* (non-Javadoc)
	 * @see org.javelin.sws.ext.bind.internal.model.TypedPattern#toString()
	 */
	@Override
	public String toString() {
		return super.toString() + " (a temporary version)";
	}

}
