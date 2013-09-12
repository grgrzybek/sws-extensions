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

package org.javelin.sws.ext.bind;

import javax.xml.XMLConstants;
import javax.xml.namespace.QName;

import org.apache.ws.commons.schema.constants.Constants;

/**
 * <p></p>
 *
 * @author Grzegorz Grzybek
 */
public interface SweJaxbConstants {

	// JAXB Context properties

	static final String SWE_CONTEXT_PROPERTY_PREFIX = "swe.jaxb";

	/**
	 * <p>If the value of this property is true, we try to be as much JAXB compliant as its ... reasonable. Otherwise we try to be pragmatic.</p>
	 * <p>This setting affects:<ul>
	 * <li>package scanning: Do we use ObjectFactory/@XmlRegistry/jaxb.index (compliant) or just all package's classes (pragmatic)?</li>
	 * </ul></p>
	 */
	public static final String SWE_CONTEXT_PROPERTY_STRICT_JAXB = SWE_CONTEXT_PROPERTY_PREFIX + ".strictJaxbCompliance";

	// JAXB Marshaller properties

	// QNames

	public static final QName XSI_TYPE = new QName(XMLConstants.W3C_XML_SCHEMA_INSTANCE_NS_URI, "type", "xsi");
	public static final QName XSI_NIL = new QName(XMLConstants.W3C_XML_SCHEMA_INSTANCE_NS_URI, "nil", "xsi");

	/**
	 * The name of the property used to specify whether or not we're doing multiRef encoding (Soap 1.1 Section 5 Encoding) during marshalling.
	 * This should be detected by Unmarshaller however.
	 */
	public static final String SWE_MARSHALLER_PROPERTY_JAXB_MULTIREFS = SWE_CONTEXT_PROPERTY_PREFIX + ".marshaller.multirefs";

	/**
	 * <p>The name of the property used to specify whether or not we add {@code xsi:type="QName"} were it MUST NOT be explicit.</p>
	 * <p>{@code xsi:type="QName"} should be explicit e.g., in case of using derived type.</p>
	 */
	public static final String SWE_MARSHALLER_PROPERTY_SEND_TYPES = SWE_CONTEXT_PROPERTY_PREFIX + ".marshaller.types";

	/**
	 * TODO: generate new prefix for conflicting namespace or redefine namespace?
	 */
	public static final String SWE_MARSHALLER_REUSE_PREFIXES = SWE_CONTEXT_PROPERTY_PREFIX + ".marshaller.prefixes.reuse";

	// JAXB Unmarshaller properties

	// XMLSchema 1.1 types
	// like org.apache.ws.commons.schema.constants.Constants but with explicit "xs" prefix for http://www.w3.org/2001/XMLSchema namespace

	static final String XSD_PREFIX = "xs";

	// 3.3 Primitive Datatypes (#built-in-primitive-datatypes)

	// 3.3.1 string (#string)
	public static final QName XSD_STRING = new QName(Constants.URI_2001_SCHEMA_XSD, "string", XSD_PREFIX);
	// 3.3.2 boolean (#boolean)
	public static final QName XSD_BOOLEAN = new QName(Constants.URI_2001_SCHEMA_XSD, "boolean", XSD_PREFIX);
	// 3.3.3 decimal (#decimal)
	public static final QName XSD_DECIMAL = new QName(Constants.URI_2001_SCHEMA_XSD, "decimal", XSD_PREFIX);
	// 3.3.4 float (#float)
	public static final QName XSD_FLOAT = new QName(Constants.URI_2001_SCHEMA_XSD, "float", XSD_PREFIX);
	// 3.3.5 double (#double)
	public static final QName XSD_DOUBLE = new QName(Constants.URI_2001_SCHEMA_XSD, "double", XSD_PREFIX);
	// 3.3.6 duration (#duration)
	// 3.3.7 dateTime (#dateTime)
	public static final QName XSD_DATETIME = new QName(Constants.URI_2001_SCHEMA_XSD, "dateTime", XSD_PREFIX);
	// 3.3.8 time (#time)
	public static final QName XSD_TIME = new QName(Constants.URI_2001_SCHEMA_XSD, "time", XSD_PREFIX);
	// 3.3.9 date (#date)
	public static final QName XSD_DATE = new QName(Constants.URI_2001_SCHEMA_XSD, "date", XSD_PREFIX);
	// 3.3.10 gYearMonth (#gYearMonth)
	public static final QName XSD_GYEARMONTH = new QName(Constants.URI_2001_SCHEMA_XSD, "gYearMonth", XSD_PREFIX);
	// 3.3.11 gYear (#gYear)
	public static final QName XSD_GYEAR = new QName(Constants.URI_2001_SCHEMA_XSD, "gYear", XSD_PREFIX);
	// 3.3.12 gMonthDay (#gMonthDay)
	public static final QName XSD_GMONTHDAY = new QName(Constants.URI_2001_SCHEMA_XSD, "gMonthDay", XSD_PREFIX);
	// 3.3.13 gDay (#gDay)
	public static final QName XSD_GDAY = new QName(Constants.URI_2001_SCHEMA_XSD, "gDay", XSD_PREFIX);
	// 3.3.14 gMonth (#gMonth)
	public static final QName XSD_GMONTH = new QName(Constants.URI_2001_SCHEMA_XSD, "gMonth", XSD_PREFIX);
	// 3.3.15 hexBinary (#hexBinary)
	// 3.3.16 base64Binary (#base64Binary)
	// 3.3.17 anyURI (#anyURI)
	// 3.3.18 QName (#QName)
	// 3.3.19 NOTATION (#NOTATION)

	// 3.4 Other Built-in Datatypes (#ordinary-built-ins)

	// 3.4.1 normalizedString (#normalizedString)
	// 3.4.2 token (#token)
	// 3.4.3 language (#language)
	// 3.4.4 NMTOKEN (#NMTOKEN)
	// 3.4.5 NMTOKENS (#NMTOKENS)
	// 3.4.6 Name (#Name)
	// 3.4.7 NCName (#NCName)
	// 3.4.8 ID (#ID)
	// 3.4.9 IDREF (#IDREF)
	// 3.4.10 IDREFS (#IDREFS)
	// 3.4.11 ENTITY (#ENTITY)
	// 3.4.12 ENTITIES (#ENTITIES)
	// 3.4.13 integer (#integer)
	public static final QName XSD_INTEGER = new QName(Constants.URI_2001_SCHEMA_XSD, "integer", XSD_PREFIX);
	// 3.4.14 nonPositiveInteger (#nonPositiveInteger)
	// 3.4.15 negativeInteger (#negativeInteger)
	// 3.4.16 long (#long)
	public static final QName XSD_LONG = new QName(Constants.URI_2001_SCHEMA_XSD, "long", XSD_PREFIX);
	// 3.4.17 int (#int)
	public static final QName XSD_INT = new QName(Constants.URI_2001_SCHEMA_XSD, "int", XSD_PREFIX);
	// 3.4.18 short (#short)
	public static final QName XSD_SHORT = new QName(Constants.URI_2001_SCHEMA_XSD, "short", XSD_PREFIX);
	// 3.4.19 byte (#byte)
	public static final QName XSD_BYTE = new QName(Constants.URI_2001_SCHEMA_XSD, "byte", XSD_PREFIX);
	// 3.4.20 nonNegativeInteger (#nonNegativeInteger)
	// 3.4.21 unsignedLong (#unsignedLong)
	// 3.4.22 unsignedInt (#unsignedInt)
	// 3.4.23 unsignedShort (#unsignedShort)
	// 3.4.24 unsignedByte (#unsignedByte)
	// 3.4.25 positiveInteger (#positiveInteger)
	// 3.4.26 yearMonthDuration (#yearMonthDuration)
	// 3.4.27 dayTimeDuration (#dayTimeDuration)
	// 3.4.28 dateTimeStamp (#dateTimeStamp)

}
