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

package org.javelin.sws.ext.bind.internal.metadata;

import java.text.ParseException;
import java.util.Locale;

import org.springframework.format.Formatter;

/**
 * <p>A wrapper formatter which prints the object by printing the nested property of that object</p>
 *
 * @author Grzegorz Grzybek
 * @param <T> a type of formatted object
 * @param <P> a type of formatted property of that object
 */
class PeelingFormatter<T, P> implements Formatter<T> {
	private PropertyMetadata<T, P> metadata;

	private Formatter<P> nestedFormatter;

	/**
	 * @param field
	 * @param nested
	 */
	public PeelingFormatter(PropertyMetadata<T, P> metadata, Formatter<P> nestedFormatter) {
		this.metadata = metadata;
		this.nestedFormatter = nestedFormatter;
	}

	/**
	 * @param pm
	 * @param nested
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public static <T, P> PeelingFormatter<T, P> newPeelingFormatter(PropertyMetadata<T, ?> metadata, Formatter<?> nestedFormatter) {
		return new PeelingFormatter<T, P>((PropertyMetadata<T, P>) metadata, (Formatter<P>) nestedFormatter);
	}

	@Override
	public String print(T object, Locale locale) {
		// TODO: object should not be null!

		P value = this.metadata.getValue(object);
		return this.nestedFormatter == null ? value.toString() : this.nestedFormatter.print(value, locale);
	}

	@Override
	public T parse(String text, Locale locale) throws ParseException {
		return null;
	}
}
