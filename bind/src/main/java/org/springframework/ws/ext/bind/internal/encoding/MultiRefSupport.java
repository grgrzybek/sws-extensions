/*
 * Copyright 2013 Exence SA
 * Created: 8 mar 2013 07:08:36
 */

package org.springframework.ws.ext.bind.internal.encoding;

import javax.xml.bind.annotation.XmlAttribute;

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
 * <li>Marshall an element with {@code href} attribute to {@code multiRef} element with {@code id} attribute</li>
 * <li>No non-multiRef (or an element with {@code href} attribute) element has child elements</li>
 * <li>Only {@code multiRef} elements have children.</li>
 * <li>Simple type {@code multiRef} elements have single text child</li>
 * <li>Complex type {@code multiRef} elements have element children</li>
 * <li>{@link XmlAttribute}-annotated properties should be marshalled as elements (!)</li>
 * </ul></p>
 *
 * @author Grzegorz Grzybek
 */
public interface MultiRefSupport {

}
