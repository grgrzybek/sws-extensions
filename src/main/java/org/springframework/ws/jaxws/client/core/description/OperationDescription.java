/*
 * Copyright 2005-2012 the original author or authors.
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

package org.springframework.ws.jaxws.client.core.description;

import javax.jws.soap.SOAPBinding.ParameterStyle;
import javax.jws.soap.SOAPBinding.Style;
import javax.jws.soap.SOAPBinding.Use;
import javax.xml.namespace.QName;

/**
 * <p>A description of WebService operation. It is looked up by the MethodEndpoint and is used to prepare proper WebService invocation.</p>
 *
 * @author Grzegorz Grzybek
 */
public class OperationDescription {

	/* Pattern of WebService operation: style, use, parameterStyle */

	private Style style = Style.DOCUMENT;

	private Use use = Use.LITERAL;

	private ParameterStyle parameterStyle = ParameterStyle.WRAPPED;

	/* Mapping information - a unique way to identify particular org.springframework.ws.server.endpoint.MethodEndpoint */

	private QName rootName;

	private String soapAction = "";

//	private URI addressingAction = null;

//	private URI addressingTo = null;

	/* Parameters of the operation */

//	private List<ParameterDescription> inputParameters = new LinkedList<ParameterDescription>();

//	private List<ParameterDescription> outputParameters = new LinkedList<ParameterDescription>();

//	private ParameterDescription returnParameter = null;

	/* (non-Javadoc)
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString() {
		return String.format("Operation %s (%s/%s/%s)", rootName.toString(), style, use, parameterStyle);
	}

	/**
	 * @return the style
	 */
	public Style getStyle() {
		return this.style;
	}

	/**
	 * @param style the style to set
	 */
	public void setStyle(Style style) {
		this.style = style;
	}

	/**
	 * @return the use
	 */
	public Use getUse() {
		return this.use;
	}

	/**
	 * @param use the use to set
	 */
	public void setUse(Use use) {
		this.use = use;
	}

	/**
	 * @return the parameterStyle
	 */
	public ParameterStyle getParameterStyle() {
		return this.parameterStyle;
	}

	/**
	 * @param parameterStyle the parameterStyle to set
	 */
	public void setParameterStyle(ParameterStyle parameterStyle) {
		this.parameterStyle = parameterStyle;
	}

	/**
	 * @return the rootName
	 */
	public QName getRootName() {
		return this.rootName;
	}

	/**
	 * @param rootName the rootName to set
	 */
	public void setRootName(QName rootName) {
		this.rootName = rootName;
	}

	/**
	 * @return the soapAction
	 */
	public String getSoapAction() {
		return this.soapAction;
	}

	/**
	 * @param soapAction the soapAction to set
	 */
	public void setSoapAction(String soapAction) {
		this.soapAction = soapAction;
	}

}
