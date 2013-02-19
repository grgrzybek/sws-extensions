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

package org.springframework.ws.jaxws.soapenc.internal.metadata;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlNsForm;

/**
 * <p></p>
 *
 * @author Grzegorz Grzybek
 */
public class PackageMetadata {

	private XmlAccessType xmlAccessType;

	private String namespace;
	private XmlNsForm elementFormDefault = XmlNsForm.UNSET;
	private XmlNsForm attributeFormDefault = XmlNsForm.UNSET;

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
	 * @return the elementFormDefault
	 */
	public XmlNsForm getElementFormDefault() {
		return this.elementFormDefault;
	}

	/**
	 * @param elementFormDefault the elementFormDefault to set
	 */
	public void setElementFormDefault(XmlNsForm elementFormDefault) {
		this.elementFormDefault = elementFormDefault;
	}

	/**
	 * @return the attributeFormDefault
	 */
	public XmlNsForm getAttributeFormDefault() {
		return this.attributeFormDefault;
	}

	/**
	 * @param attributeFormDefault the attributeFormDefault to set
	 */
	public void setAttributeFormDefault(XmlNsForm attributeFormDefault) {
		this.attributeFormDefault = attributeFormDefault;
	}

}
