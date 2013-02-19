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

package org.springframework.ws.jaxws.soapenc.internal;

import javax.xml.XMLConstants;
import javax.xml.namespace.QName;

/**
 * <p></p>
 *
 * @author Grzegorz Grzybek
 */
public interface QNames {

	public static final QName SIMPLETYPE_BOOLEAN = new QName(XMLConstants.W3C_XML_SCHEMA_NS_URI, "boolean");
	public static final QName SIMPLETYPE_BYTE = new QName(XMLConstants.W3C_XML_SCHEMA_NS_URI, "byte");
	public static final QName SIMPLETYPE_SHORT = new QName(XMLConstants.W3C_XML_SCHEMA_NS_URI, "short");
	public static final QName SIMPLETYPE_INT = new QName(XMLConstants.W3C_XML_SCHEMA_NS_URI, "int");
	public static final QName SIMPLETYPE_LONG = new QName(XMLConstants.W3C_XML_SCHEMA_NS_URI, "long");
	public static final QName SIMPLETYPE_FLOAT = new QName(XMLConstants.W3C_XML_SCHEMA_NS_URI, "float");
	public static final QName SIMPLETYPE_DOUBLE = new QName(XMLConstants.W3C_XML_SCHEMA_NS_URI, "double");

	public static final QName XSI_NIL = new QName(XMLConstants.W3C_XML_SCHEMA_INSTANCE_NS_URI, "nil", "xsi");

}
