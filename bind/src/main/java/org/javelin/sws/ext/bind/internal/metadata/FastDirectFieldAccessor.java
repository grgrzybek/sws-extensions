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

import java.lang.reflect.Field;
import java.util.Map;

import org.springframework.beans.BeansException;
import org.springframework.beans.DirectFieldAccessor;
import org.springframework.beans.PropertyAccessor;
import org.springframework.beans.PropertyEditorRegistry;
import org.springframework.beans.PropertyEditorRegistrySupport;
import org.springframework.beans.PropertyValue;
import org.springframework.beans.PropertyValues;
import org.springframework.beans.TypeConverter;
import org.springframework.core.convert.TypeDescriptor;
import org.springframework.util.ReflectionUtils;

/**
 * <p>A modified version of {@link DirectFieldAccessor} without {@link PropertyEditorRegistrySupport}. It's also neither
 * a {@link PropertyEditorRegistry} nor {@link TypeConverter}.</p>
 *
 * @author Grzegorz Grzybek
 */
public class FastDirectFieldAccessor implements PropertyAccessor {

	private Map<String, Field> contentModelDirectFieldMap;
	private Object object;

	/**
	 * @param contentModelDirectFieldMap
	 * @param object
	 */
	public FastDirectFieldAccessor(Map<String, Field> contentModelDirectFieldMap, Object object) {
		this.contentModelDirectFieldMap = contentModelDirectFieldMap;
		this.object = object;
	}

	/* (non-Javadoc)
	 * @see org.springframework.beans.PropertyAccessor#isReadableProperty(java.lang.String)
	 */
	@Override
	public boolean isReadableProperty(String propertyName) {
		return this.contentModelDirectFieldMap.containsKey(propertyName);
	}

	/* (non-Javadoc)
	 * @see org.springframework.beans.PropertyAccessor#isWritableProperty(java.lang.String)
	 */
	@Override
	public boolean isWritableProperty(String propertyName) {
		return this.contentModelDirectFieldMap.containsKey(propertyName);
	}

	/* (non-Javadoc)
	 * @see org.springframework.beans.PropertyAccessor#getPropertyType(java.lang.String)
	 */
	@Override
	public Class<?> getPropertyType(String propertyName) throws BeansException {
		return this.isReadableProperty(propertyName) ? this.contentModelDirectFieldMap.get(propertyName).getType() : null;
	}

	/* (non-Javadoc)
	 * @see org.springframework.beans.PropertyAccessor#getPropertyTypeDescriptor(java.lang.String)
	 */
	@Override
	public TypeDescriptor getPropertyTypeDescriptor(String propertyName) throws BeansException {
		throw new UnsupportedOperationException();
	}

	/* (non-Javadoc)
	 * @see org.springframework.beans.PropertyAccessor#getPropertyValue(java.lang.String)
	 */
	@Override
	public Object getPropertyValue(String propertyName) throws BeansException {
		return ReflectionUtils.getField(this.contentModelDirectFieldMap.get(propertyName), this.object);
	}

	/* (non-Javadoc)
	 * @see org.springframework.beans.PropertyAccessor#setPropertyValue(java.lang.String, java.lang.Object)
	 */
	@Override
	public void setPropertyValue(String propertyName, Object value) throws BeansException {
		ReflectionUtils.setField(this.contentModelDirectFieldMap.get(propertyName), this.object, value);
	}

	/* (non-Javadoc)
	 * @see org.springframework.beans.PropertyAccessor#setPropertyValue(org.springframework.beans.PropertyValue)
	 */
	@Override
	public void setPropertyValue(PropertyValue pv) throws BeansException {
		ReflectionUtils.setField(this.contentModelDirectFieldMap.get(pv.getName()), this.object, pv.getValue());
	}

	/* (non-Javadoc)
	 * @see org.springframework.beans.PropertyAccessor#setPropertyValues(java.util.Map)
	 */
	@Override
	public void setPropertyValues(Map<?, ?> map) throws BeansException {
		throw new UnsupportedOperationException();
	}

	/* (non-Javadoc)
	 * @see org.springframework.beans.PropertyAccessor#setPropertyValues(org.springframework.beans.PropertyValues)
	 */
	@Override
	public void setPropertyValues(PropertyValues pvs) throws BeansException {
		throw new UnsupportedOperationException();
	}

	/* (non-Javadoc)
	 * @see org.springframework.beans.PropertyAccessor#setPropertyValues(org.springframework.beans.PropertyValues, boolean)
	 */
	@Override
	public void setPropertyValues(PropertyValues pvs, boolean ignoreUnknown) throws BeansException {
		throw new UnsupportedOperationException();
	}

	/* (non-Javadoc)
	 * @see org.springframework.beans.PropertyAccessor#setPropertyValues(org.springframework.beans.PropertyValues, boolean, boolean)
	 */
	@Override
	public void setPropertyValues(PropertyValues pvs, boolean ignoreUnknown, boolean ignoreInvalid) throws BeansException {
		throw new UnsupportedOperationException();
	}

}
