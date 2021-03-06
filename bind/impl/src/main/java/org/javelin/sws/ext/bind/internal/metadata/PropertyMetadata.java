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
import java.util.Collection;

import javax.xml.bind.JAXBContext;

import org.javelin.sws.ext.bind.internal.model.TypedPattern;
import org.javelin.sws.ext.bind.internal.model.XmlEventsPattern;
import org.springframework.core.GenericCollectionTypeResolver;

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

	private Class<T> declaringClass;

	// this may be a collection or array type!
	private Class<P> propertyClass;
	private Class<?> collectionClass;
	private Class<?> collectionElementType;

	/** {@link TypedPattern} to which a given property maps. */
	private XmlEventsPattern pattern;

	/** Is it field ({@code PropertyKind#FIELD}) or JavaBean ({@code PropertyKind#BEAN} property? */
	private PropertyKind propertyKind;

	/** {@link Field} for direct access */
	private Field field;

	/** {@link Method} for bean property read */
	private Method getter;

	/** {@link Method} for bean property write */
	private Method setter;

	private boolean collectionIsWrapped;

	/**
	 * @param declaringClass
	 * @param propertyClass
	 * @param propertyName
	 * @param propertyKind
	 */
	@SuppressWarnings("unchecked")
	PropertyMetadata(Class<T> declaringClass, Class<?> propertyClass, String propertyName, PropertyKind propertyKind) {
		this.declaringClass = declaringClass;
		this.propertyName = propertyName;
		this.propertyKind = propertyKind;
		// DESIGNFLAW: to many unchecked casts
		this.propertyClass = (Class<P>) propertyClass;
		if (Collection.class.isAssignableFrom(propertyClass)) {
			// we have collection field
			this.collectionClass = propertyClass;
		}
	}

	/**
	 * @param declaringClass
	 * @param propertyClass
	 * @param propertyName
	 * @param propertyKind
	 */
	@SuppressWarnings("unchecked")
	PropertyMetadata(Class<T> declaringClass, Class<?> propertyClass, String propertyName, Field field, PropertyKind propertyKind) {
		this(declaringClass, propertyClass, propertyName, propertyKind);
		this.field = field;
		if (Collection.class.isAssignableFrom(propertyClass)) {
			// we have collection field
			this.collectionElementType = GenericCollectionTypeResolver.getCollectionFieldType(this.field);
			this.propertyClass = (Class<P>) this.collectionElementType;
		}
	}

	/**
	 * @param declaringClass
	 * @param propertyClass
	 * @param propertyName
	 * @param getter
	 * @param setter
	 * @param propertyKind
	 */
	@SuppressWarnings("unchecked")
	PropertyMetadata(Class<T> declaringClass, Class<?> propertyClass, String propertyName, Method getter, Method setter, PropertyKind propertyKind) {
		this(declaringClass, propertyClass, propertyName, propertyKind);
		this.getter = getter;
		// may be null for java.util.Collection!
		this.setter = setter;
		if (Collection.class.isAssignableFrom(propertyClass)) {
			// we have collection field
			this.collectionClass = propertyClass;
			this.collectionElementType = GenericCollectionTypeResolver.getCollectionReturnType(this.getter);
			this.propertyClass = (Class<P>) this.collectionElementType;
		}
	}

	/**
	 * @param declaringClass
	 * @param propertyClass
	 * @param propertyName
	 * @param propertyKind
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public static <T, P> PropertyMetadata<T, P> newPropertyMetadata(Class<T> declaringClass, Class<?> propertyClass, String propertyName,
			Field field, PropertyKind propertyKind) {
		return new PropertyMetadata<T, P>(declaringClass, (Class<P>) propertyClass, propertyName, field, propertyKind);
	}
	
	/**
	 * @param declaringClass
	 * @param propertyClass
	 * @param propertyName
	 * @param getter
	 * @param setter
	 * @param propertyKind
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public static <T, P> PropertyMetadata<T, P> newPropertyMetadata(Class<T> declaringClass, Class<?> propertyClass, String propertyName,
			PropertyKind propertyKind) {
		return new PropertyMetadata<T, P>(declaringClass, (Class<P>) propertyClass, propertyName, propertyKind);
	}
	
	/**
	 * @param declaringClass
	 * @param propertyClass
	 * @param propertyName
	 * @param getter
	 * @param setter
	 * @param propertyKind
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public static <T, P> PropertyMetadata<T, P> newPropertyMetadata(Class<T> declaringClass, Class<?> propertyClass, String propertyName,
			Method getter, Method setter, PropertyKind propertyKind) {
		return new PropertyMetadata<T, P>(declaringClass, (Class<P>) propertyClass, propertyName, getter, setter, propertyKind);
	}

	/**
	 * @return
	 */
	public boolean isCollectionProperty() {
		return this.collectionClass != null && !this.collectionIsWrapped;
	}
	
	/**
	 * @return the collectionClass
	 */
	public Class<?> getCollectionClass() {
		return this.collectionClass;
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
	 * @return the propertyKind
	 */
	public PropertyKind getPropertyKind() {
		return this.propertyKind;
	}

//	/**
//	 * @param field
//	 */
//	public void setField(Field field) {
//		this.field = field;
//	}

//	/**
//	 * @param getter
//	 * @param setter
//	 */
//	public void setAccessorMethods(Method getter, Method setter) {
//		this.getter = getter;
//		this.setter = setter;
//	}

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
			if (this.getPropertyKind() == PropertyKind.FIELD)
				return this.getFieldValue(object);
			else if (this.getPropertyKind() == PropertyKind.BEAN)
				return (P) this.getter.invoke(object);
			else
				/*if (this.getPropertyKind() == PropertyKind.PASSTHROUGH)*/
				return (P) object;
		}
		catch (Exception e) {
			throw new RuntimeException(e.getMessage(), e);
		}
	}

	/**
	 * @param object
	 * @param value
	 */
	public void setValue(T object, Object value) {
		try {
			if (this.getPropertyKind() == PropertyKind.FIELD)
				this.setFieldValue(object, value);
			else if (this.getPropertyKind() == PropertyKind.BEAN)
				this.setter.invoke(object, value);
			else
				/*if (this.getPropertyKind() == PropertyKind.PASSTHROUGH)*/
				;
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
	 * @param object
	 * @param value
	 */
	private void setFieldValue(T object, Object value) {
		try {
			this.field.set(object, value);
		} catch (Exception e) {
			throw new RuntimeException("TODO: " + e.getMessage(), e);
		}
	}

	/**
	 * @return
	 */
	public AnnotatedElement[] getAccessors() {
		switch (this.getPropertyKind()) {
		case FIELD:
			return new AnnotatedElement[] { this.field };
		case BEAN:
			return new AnnotatedElement[] { this.getter, this.setter };
		case PASSTHROUGH:
			return new AnnotatedElement[] { this.declaringClass };
		default:
			return null;
		}
	}

	/**
	 * @param collectionIsWrapped
	 */
	public void setWrappedCollection(boolean collectionIsWrapped) {
		this.collectionIsWrapped = collectionIsWrapped;
	}

}
