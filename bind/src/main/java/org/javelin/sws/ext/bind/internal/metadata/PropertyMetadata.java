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

import java.lang.reflect.AnnotatedElement;
import java.lang.reflect.Field;
import java.lang.reflect.Method;

import javax.xml.bind.JAXBContext;

import org.javelin.sws.ext.bind.internal.model.TypedPattern;
import org.javelin.sws.ext.bind.internal.model.XmlEventsPattern;

/**
 * <p>Information on what {@link XmlEventsPattern} is configured for a given property (direct or getter) of marshalled/unmarshalled object</p>
 * 
 * <p>Objects of this class are created for each property of analyzed (during {@link JAXBContext} initialization) class.</p>
 *
 * @author Grzegorz Grzybek
 * @param <T> a type of the class containing the property
 * @param <P> a type of the property
 */
public class PropertyMetadata<T, P> {

	/** Name of field or bean property */
	private String propertyName;

	@SuppressWarnings("unused")
	private Class<T> declaringClass;

	private Class<P> propertyClass;

	/** {@link TypedPattern} to which a given property maps. */
	private XmlEventsPattern pattern;

	/** Is it field ({@code true}) or JavaBean ({@code false} property? */
	private boolean directProperty;

	/** {@link Field} for direct access */
	private Field field;

	/** {@link Method} for bean property read */
	private Method getter;

	/** {@link Method} for bean property write */
	@SuppressWarnings("unused")
	private Method setter;

	/**
	 * @param propertyName
	 * @param directProperty
	 */
	private PropertyMetadata(Class<T> declaringClass, Class<P> propertyClass, String propertyName, boolean directProperty) {
		this.declaringClass = declaringClass;
		this.propertyClass = propertyClass;
		this.propertyName = propertyName;
		this.directProperty = directProperty;
	}

	/**
	 * @param propertyName
	 * @param directProperty
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public static <T, P> PropertyMetadata<T, P> newPropertyMetadata(Class<T> declaringClass, Class<?> propertyClass, String propertyName, boolean directProperty) {
		return new PropertyMetadata<T, P>(declaringClass, (Class<P>) propertyClass, propertyName, directProperty);
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

	/**
	 * @param field
	 */
	public void setField(Field field) {
		this.field = field;
	}

	/**
	 * @param getter
	 * @param setter
	 */
	public void setAccessorMethods(Method getter, Method setter) {
		this.getter = getter;
		this.setter = setter;
	}

	/**
	 * @return the propertyClass
	 */
	public Class<P> getPropertyClass() {
		return this.propertyClass;
	}

	/**
	 * Gets the value of this property from the passed object
	 * 
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public P getValue(T object) {
		try {
			if (this.isDirectProperty())
				return this.getFieldValue(object);
			else
				return (P) this.getter.invoke(object);
		}
		catch (Exception e) {
			throw new RuntimeException(e.getMessage(), e);
		}
	}

	/**
	 * @param object 
	 * @return
	 */
	@SuppressWarnings("unchecked")
	private P getFieldValue(T object) {
		try {
			return (P) this.field.get(object);
		}
		catch (Exception e) {
			throw new RuntimeException("TODO: " + e.getMessage(), e);
		}
	}

	/**
	 * @return
	 */
	public AnnotatedElement getProperty() {
		return this.isDirectProperty() ? this.field : this.getter;
	}

}
