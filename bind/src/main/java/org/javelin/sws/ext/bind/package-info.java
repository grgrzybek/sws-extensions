/**
 * <p>This is the implementation of JAXB 2.2 and JAX-RPC 1.1. Of cource within the scope that's justified by sanity - we don't need
 * {@link javax.xml.bind.Binder} support...</p>
 * <p>JAXB 2.2 gives us:<ul>
 * <li>Context, Marshaller, Unmarshaller</li>
 * <li>nice annotations for Java to XSD mapping</li>
 * <li>binding language for XSD to Java mapping - but not now</li>
 * </ul></p>
 * <p>JAX-RPC 1.1 gives us:<ul>
 * <li>SoapEncoding</li>
 * <li>Soap Types</li>
 * <li>Soap MultiRef encoding</li>
 * </ul></p>
 */
package org.javelin.sws.ext.bind;

