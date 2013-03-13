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
import javax.xml.namespace.QName;
import javax.xml.stream.XMLEventReader;
import javax.xml.stream.XMLEventWriter;
import javax.xml.stream.XMLStreamException;

import org.springframework.beans.BeanWrapperImpl;
import org.springframework.beans.PropertyAccessor;
import org.springframework.beans.PropertyAccessorFactory;
import org.springframework.ws.ext.bind.internal.MarshallingContext;
import org.springframework.ws.ext.bind.internal.metadata.PropertyMetadata;

/**
 * <p>This pattern maps bean's properties to content models of XML Schema components.
 * There are mainly complex (sequence, choice, all) or simple content models</p>
 * 
 * <p>ComplexContent is used to marshal an object of class which may have many properties. These properties may be:<ul>
 * <li>{@link XmlAttribute XML attributes}</li>
 * <li>{@link XmlElement XML elements}</li>
 * <li>{@link XmlValue XML value} (only one and may not have other {@link XmlElement XML elements}</li>
 * </ul></p>
 * 
 * <p>This pattern replays the object using nested patterns.</p>
 *
 * @author Grzegorz Grzybek
 */
public class ContentModelPattern extends XmlEventsPattern {

	private List<PropertyMetadata> contentModel = new LinkedList<PropertyMetadata>();

	// optimizations to check wether to create particular PropertyAccessor
	private boolean hasFieldProperties = false;

	/**
	 * <p>Initializes content model with a sequence (XSD's sequence, choice or all) of properties, each related to a given bean property and
	 * mapping to a given {@link XmlEventsPattern}.</p>
	 * 
	 * @param schemaType
	 * @param javaType
	 * @param contentModel
	 */
	public ContentModelPattern(QName schemaType, Class<?> javaType, List<PropertyMetadata> contentModel) {
		super(schemaType, javaType);
		this.contentModel = contentModel;
		for (PropertyMetadata pm : this.contentModel) {
			if (pm.isDirectProperty()) {
				this.hasFieldProperties = true;
				break;
			}
		}
	}

	/* (non-Javadoc)
	 * @see org.springframework.ws.ext.bind.internal.model.XmlEventsPattern#replay(java.lang.Object, javax.xml.stream.XMLEventWriter, org.springframework.ws.ext.bind.internal.MarshallingContext)
	 */
	@Override
	public void replay(Object object, XMLEventWriter eventWriter, MarshallingContext context) throws XMLStreamException {
		// nested patterns will extract configured property (field or getter) from this beanWrapper.
		// in order to minimize a number of PropertyAccessor instances we will create them here and reuse for each direct/child property
		PropertyAccessor directFieldAccessor = null;
		PropertyAccessor beanPropertyAccessor = null;
		if (this.hasFieldProperties) {
			// we'll get to the wrapped instance through DirectFieldAccessor
			// we create it here, because there may be more field properties to be marshalled
			directFieldAccessor = PropertyAccessorFactory.forDirectFieldAccess(object);
		}
		else {
			// if there are no JAXB mapped fields, there should be JAXB mapped properties. Otherwise this would be a very strange JAXB class...
			beanPropertyAccessor = new BeanWrapperImpl(false);
			((BeanWrapperImpl) beanPropertyAccessor).setWrappedInstance(object);
		}

		// each nested pattern relates to some property of the marshalled object
		// this is the main responsibility of this pattern (ContentModelPattern)
		// the order of properties is determined by this.contentModel, not by the order of bean properties
		for (PropertyMetadata pm : this.contentModel) {
			boolean direct = pm.isDirectProperty();
			PropertyAccessor propertyAccessor = direct ? directFieldAccessor : beanPropertyAccessor;
			object = propertyAccessor.getPropertyValue(pm.getPropertyName());
			
			// for multi-ref encoding every property is an element - @XmlValue and @XmlAttribute too!
			if (context.isMultiRefEncoding()) {
				context.getMultiRefSupport().adaptPattern(pm.getPattern(), pm.getPropertyName()).replay(object, eventWriter, context);
			} else {
				pm.getPattern().replay(object, eventWriter, context);
			}
		}
	}

	/* (non-Javadoc)
	 * @see org.springframework.ws.ext.bind.internal.model.XmlEventsPattern#consume(javax.xml.stream.XMLEventReader)
	 */
	@Override
	public Object consume(XMLEventReader eventReader) throws XMLStreamException {
		// TODO Auto-generated method stub
		return null;
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
		return "A sequence of " + this.contentModel.size() + " nested property patterns";
	}

}
