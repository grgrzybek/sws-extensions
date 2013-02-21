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

import java.util.LinkedList;
import java.util.List;

import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlValue;
import javax.xml.stream.XMLEventWriter;
import javax.xml.stream.XMLStreamException;

import org.springframework.beans.PropertyAccessor;
import org.springframework.beans.PropertyAccessorFactory;
import org.springframework.ws.ext.bind.JwsJaxbContext;

/**
 * <p>ComplexContent is used to marshall an object of class which may have many properties. These properties may be:<ul>
 * <li>{@link XmlAttribute XML attributes}</li>
 * <li>{@link XmlElement XML elements}</li>
 * <li>{@link XmlValue XML value} (only one and may not have other {@link XmlElement XML elements}</li>
 * </ul></p>
 * <p>This pattern replays the object using nested patterns and uses {@link PropertyAccessor} to access nested properties of marshalled object.</p>
 *
 * @author Grzegorz Grzybek
 */
public class ComplexContentPattern implements XmlEventsPattern {

	private List<PropertyPattern> nestedPatterns = new LinkedList<PropertyPattern>();

	// optimizations to check wether to create particular PropertyAccessor
	private boolean hasFieldProperties = false;

	// DESIGNFLAW: extract an interface for creating PropertyAccessors
	private JwsJaxbContext jwsJaxbContext = null;

	/**
	 * @param childPatterns
	 */
	public ComplexContentPattern(JwsJaxbContext jwsJaxbContext, List<PropertyPattern> childPatterns) {
		this.jwsJaxbContext = jwsJaxbContext;
		this.nestedPatterns = childPatterns;
		for (XmlEventsPattern pattern : this.nestedPatterns) {
			if (pattern instanceof PropertyPattern) {
				if (((PropertyPattern) pattern).isDirectProperty()) {
					this.hasFieldProperties = true;
					break;
				}
			}
		}
	}

	/* (non-Javadoc)
	 * @see org.springframework.ws.jaxws.soapenc.internal.model.XmlEventsPattern#replay(java.lang.Object, javax.xml.stream.XMLEventWriter, boolean)
	 */
	@Override
	public void replay(Object object, XMLEventWriter eventWriter, boolean repairingWriter) throws XMLStreamException {
		// nested patterns will extract configured property (field or getter) from this beanWrapper.
		// in order to minimize a number of PropertyAccessor instances we will create them here and reuse for each direct/child property
		PropertyAccessor directAccessor = null;
		PropertyAccessor getterAccessor = null;
		if (this.hasFieldProperties) {
			// we'll get to the wrapped instance through DirectFieldAccessor
			// we create it here, because there may be more field properties to be marshalled
			directAccessor = PropertyAccessorFactory.forDirectFieldAccess(object);
		}
		else {
			getterAccessor = this.jwsJaxbContext.createBeanWrapper(object);
		}

		// each nested pattern relates to some property of the marshalled object
		for (PropertyPattern pattern : this.nestedPatterns) {
			boolean direct = pattern.isDirectProperty();
			PropertyAccessor propertyAccessor = direct ? directAccessor : getterAccessor;
			pattern.replayProperty(propertyAccessor, eventWriter, repairingWriter);
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
	 * @see org.springframework.ws.jaxws.soapenc.internal.model.XmlEventsPattern#isSimpleType()
	 */
	@Override
	public boolean isSimpleType() {
		return false;
	}

	/* (non-Javadoc)
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString() {
		return "A sequence of " + this.nestedPatterns.size() + " nested property patterns";
	}

}
