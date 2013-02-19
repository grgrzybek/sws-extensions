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

package org.springframework.ws.jaxws.soapenc.internal.model;

import java.util.LinkedList;
import java.util.List;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.stream.XMLEventWriter;
import javax.xml.stream.XMLStreamException;

import org.springframework.beans.BeanWrapper;
import org.springframework.beans.PropertyAccessor;
import org.springframework.beans.PropertyAccessorFactory;

/**
 * <p>Non-wrapped sequence of element and/or attribute events.</p>
 *
 * @author Grzegorz Grzybek
 */
public class ComplexContentPattern implements XmlEventsPattern {

	private List<XmlEventsPattern> nestedPatterns = new LinkedList<XmlEventsPattern>();

	// optimizations to check wether to create particular PropertyAccessor
	private boolean hasFieldProperties = false;

	/**
	 * @param childPatterns
	 */
	public ComplexContentPattern(List<XmlEventsPattern> childPatterns) {
		this.nestedPatterns = childPatterns;
		for (XmlEventsPattern pattern : this.nestedPatterns) {
			if (pattern instanceof PropertyPattern) {
				if (((PropertyPattern) pattern).getAccessType() == XmlAccessType.FIELD) {
					this.hasFieldProperties = true;
					break;
				}
			}
		}
	}

	/* (non-Javadoc)
	 * @see org.springframework.ws.jaxws.soapenc.internal.model.XmlEventsPattern#replay(org.springframework.beans.BeanWrapper, javax.xml.stream.XMLEventWriter, boolean)
	 */
	@Override
	public void replay(BeanWrapper beanWrapper, XMLEventWriter eventWriter, boolean repairingWriter) throws XMLStreamException {
		// nested patterns will extract configured property (field or getter) from this beanWrapper.
		// in order to minimize a number of PropertyAccessor instances we will create them here and reuse for each direct/child property
		PropertyAccessor directAccessor = null;
		if (this.hasFieldProperties) {
			// we'll get to the wrapped instance through DirectFieldAccessor
			// we create it here, because there may be more field properties to be marshalled
			directAccessor = PropertyAccessorFactory.forDirectFieldAccess(beanWrapper.getWrappedInstance());
		}

		for (XmlEventsPattern pattern : this.nestedPatterns) {
			if (pattern instanceof PropertyPattern) {
				boolean direct = ((PropertyPattern) pattern).getAccessType() == XmlAccessType.FIELD;
				((PropertyPattern) pattern).replayProperty(direct ? directAccessor : directAccessor, eventWriter, repairingWriter);
			} else {
				pattern.replay(beanWrapper, eventWriter, repairingWriter);
			}
		}
	}

	/* (non-Javadoc)
	 * @see org.springframework.ws.jaxws.soapenc.internal.model.XmlEventsPattern#isElement()
	 */
	@Override
	public boolean isElement() {
		return false;
	}

	/* (non-Javadoc)
	 * @see org.springframework.ws.jaxws.soapenc.internal.model.XmlEventsPattern#isSimpleValue()
	 */
	@Override
	public boolean isSimpleValue() {
		return false;
	}

}
