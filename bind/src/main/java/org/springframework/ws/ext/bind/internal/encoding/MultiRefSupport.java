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

package org.springframework.ws.ext.bind.internal.encoding;

import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlValue;
import javax.xml.stream.XMLEventWriter;
import javax.xml.stream.XMLStreamException;

import org.springframework.ws.ext.bind.internal.MarshallingContext;
import org.springframework.ws.ext.bind.internal.model.AbstractSimpleTypePattern;
import org.springframework.ws.ext.bind.internal.model.ContentModelPattern;
import org.springframework.ws.ext.bind.internal.model.ElementPattern;
import org.springframework.ws.ext.bind.internal.model.ValuePattern;
import org.springframework.ws.ext.bind.internal.model.XmlEventsPattern;

/**
 * <p>An attempt to extend JAXB2 model (marshalling, unmarshalling), with this project's concept of {@link XmlEventsPattern} with
 * the support of <a href="http://www.w3.org/TR/2000/NOTE-SOAP-20000508/#_Toc478383512">Soap 1.1, Section 5 Encoding</a></p>
 * 
 * <p>The most important thing is that multi-ref marshalled object is <b>not</b> a well-formed XML - it has no single root value. Multi-ref
 * encoding must occur inside other elements - RPC params for RPC style or SOAP-ENV:Body for Document style WebServices.</p>
 * 
 * <p>These are the types of multi-ref encoded XML values:<ul>
 * <li>Document-Encoded (weird, but possible):<pre>
 * &lt;soapenv:Body>
 *    &lt;param1 href="#id0" />
 *    &lt;param2 href="#id1" />
 *    ... (other params)
 *    &lt;multiRef id="id0" ...
 *    ... (other multi-refs)
 * </pre></li>
 * 
 * <li>RPC-Encoded (old-style, JAX-RPC):<pre>
 * &lt;soapenv:Body>
 *    &lt;ns1:operationName soapenv:encodingStyle="http://schemas.xmlsoap.org/soap/encoding/">
 *       &lt;param1 href="#id0" />
 *       &lt;param2 href="#id1" />
 *       ... (other params)
 *    &lt;/ns1:operationName>
 *    &lt;multiRef id="id0" ...
 *    ... (other multi-refs)
 * </pre></li>
 * 
 * <li>"Wrapped"-Encoded (like Document-Encoded, but with artificial SOAP-ENV:Body child representing the operation):<pre>
 * &lt;soapenv:Body>
 *    &lt;ns1:elementAsOperation href="#id0" />
 *    &lt;multiRef id="id0" soapenc:root="0" soapenv:encodingStyle="http://schemas.xmlsoap.org/soap/encoding/" xmlns:soapenc="http://schemas.xmlsoap.org/soap/encoding/">
 *       &lt;param1 href="#id1" />
 *       &lt;param2 href="#id2" />
 *       ... (other params - just properties (accessors) of artificial root element)
 *    &lt;/multiRef>
 *    &lt;multiRef id="id1" ...
 *    ... (other multi-refs)
 * </pre></li>
 * </ul></p>
 * 
 * <p>When dealing with multi-ref marshalling in general (outside the WebServices context), the process may have the following properties:<ul>
 * <li>Marshal an element with {@code href} attribute to {@code multiRef} element with {@code id} attribute</li>
 * <li>No non-multiRef (or an element with {@code href} attribute) element has child elements</li>
 * <li>Only {@code multiRef} elements have children.</li>
 * <li>Simple type {@code multiRef} elements have single text child</li>
 * <li>Complex type {@code multiRef} elements have element children</li>
 * <li>{@link XmlAttribute} or {@link XmlValue}-annotated bean properties should always be marshalled as if they were elements (!)</li>
 * </ul></p>
 * 
 * <p>Non-multiRef marshalling will result in {@link StackOverflowError} for interdependent classes and something like this, when
 * a class contains reference to itself:<pre>
 * &lt;?xml version='1.0' encoding='UTF-8'?>
 * &lt;r:root-wrapper-for-multirefs xmlns:r="urn:test:1">
 *   &lt;r:root xmlns:r="urn:test">
 *     &lt;other xmlns="http://context3.bind.ext.ws.springframework.org/">
 *       &lt;other>
 *         &lt;other>
 *           &lt;other>
 *             &lt;other>
 *               &lt;other>
 *                 &lt;other>
 *                   &lt;other>
 *                     &lt;other>
 *                     ...
 * </pre></p>
 * <p>For multi-ref encoding we'll get:<pre>
 * &lt;?xml version='1.0' encoding='UTF-8'?>
 * &lt;r:root-wrapper-for-multirefs xmlns:r="urn:test:1">
 *   &lt;r:root xmlns:r="urn:test" href="#id0"/>
 *   &lt;mr id="id0">
 *     &lt;other xmlns="http://context3.bind.ext.ws.springframework.org/" href="#id0"/>
 *   &lt;/mr>
 * &lt;/r:root-wrapper-for-multirefs>
 * </pre></p>
 *
 * @author Grzegorz Grzybek
 */
public interface MultiRefSupport {

	/**
	 * <p>Called during the process of marshalling of the nestedPattern of {@link ElementPattern}.</p>
	 * 
	 * <p>See: <a href="http://www.w3.org/TR/2000/NOTE-SOAP-20000508/#_Toc478383513">5.1 Rules for Encoding Types in XML</a></p>
	 * 
	 * <p>It's easiest for the ElementPattern to always marshal nestedPattern as multiRefValue, but there may be special cases:<ol>
	 * <li>{@link ValuePattern} may be marshalled inline, because <i>special rules allow them to be represented efficiently for common cases</i>. An accessor to a string or byte-array value MAY have an attribute named "id" and of type "ID" per the XML Specification [7]. If so, all other accessors to the same value are encoded as empty elements having a local, unqualified attribute named "href" and of type "uri-reference" per the XML Schema Specification [11], with a "href" attribute value of a URI fragment identifier referencing the single element containing the value. </li>
	 * <li>A multi-reference value MUST be represented as the content of an independent element. A single-reference value SHOULD not be (but MAY be).</li>
	 * <li>A multi-reference simple or compound value is encoded as an independent element containing a local, unqualified attribute named "id" and of type "ID" per the XML Specification [7]. Each accessor to this value is an empty element having a local, unqualified attribute named "href" and of type "uri-reference" per the XML Schema Specification [11], with a "href" attribute value of a URI fragment identifier referencing the corresponding independent element.</li>
	 * </ol></p>
	 * 
	 * @param object
	 * @param eventWriter
	 * @param nestedPattern
	 */
	public void registerMultiRef(Object object, XMLEventWriter eventWriter, XmlEventsPattern nestedPattern) throws XMLStreamException;

	/**
	 * @param eventWriter
	 * @param context
	 * @throws XMLStreamException
	 */
	public void outputMultiRefs(XMLEventWriter eventWriter, MarshallingContext context) throws XMLStreamException;

	/**
	 * <p>MultiRef encoding doesn't marshal any property of complex type as attributes or characters - {@link ContentModelPattern}
	 * uses this method to convert {@link AbstractSimpleTypePattern} to {@link ElementPattern}.</p>
	 * 
	 * <p>Soap 1.1: Accessors whose names are local to their containing types have unqualified element names; all others have qualified names</p>
	 * 
	 * @param pattern
	 * @param accessorName
	 * @return
	 */
	public XmlEventsPattern adaptPattern(XmlEventsPattern pattern, String accessorName);
}
