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

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlNsForm;

import org.javelin.sws.ext.utils.NamespaceUtils;

/**
 * <p>Aggregation of metadata from JAXB annotations, which may be present at different (assumed, package, class) levels</p>
 *
 * @author Grzegorz Grzybek
 */
public class JaxbMetadata {

	private String namespace;
	private XmlAccessType xmlAccessType;
	private XmlNsForm elementForm = XmlNsForm.UNSET;
	private XmlNsForm attributeForm = XmlNsForm.UNSET;

	/**
	 * Creates new {@link JaxbMetadata} with default parameters for customizing JAXB-bound classes
	 * 
	 * @return
	 */
	public static JaxbMetadata defaultForPackage(Package pkg) {
		JaxbMetadata meta = new JaxbMetadata();
		meta.setNamespace(NamespaceUtils.packageNameToNamespace(pkg));
		meta.setXmlAccessType(XmlAccessType.PUBLIC_MEMBER);
		meta.setElementForm(XmlNsForm.UNQUALIFIED); // (XmlNsForm.UNSET = "unqualified" from XML Schema point of view)
		meta.setAttributeForm(XmlNsForm.UNQUALIFIED); // (XmlNsForm.UNSET = "unqualified" from XML Schema point of view)
		
		return meta;
	}

	/**
	 * @return the namespace
	 */
	public String getNamespace() {
		return this.namespace;
	}

	/**
	 * @param namespace the namespace to set
	 */
	public void setNamespace(String namespace) {
		this.namespace = namespace;
	}
	
	/**
	 * @return the xmlAccessType
	 */
	public XmlAccessType getXmlAccessType() {
		return this.xmlAccessType;
	}
	
	/**
	 * @param xmlAccessType the xmlAccessType to set
	 */
	public void setXmlAccessType(XmlAccessType xmlAccessType) {
		this.xmlAccessType = xmlAccessType;
	}

	/**
	 * @return the elementForm
	 */
	public XmlNsForm getElementForm() {
		return this.elementForm;
	}

	/**
	 * @param elementForm the elementForm to set
	 */
	public void setElementForm(XmlNsForm elementForm) {
		this.elementForm = elementForm;
	}

	/**
	 * @return the attributeForm
	 */
	public XmlNsForm getAttributeForm() {
		return this.attributeForm;
	}

	/**
	 * @param attributeForm the attributeForm to set
	 */
	public void setAttributeForm(XmlNsForm attributeForm) {
		this.attributeForm = attributeForm;
	}

}
