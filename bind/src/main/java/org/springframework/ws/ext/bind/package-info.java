/**
 * <p>This package contains logic for marshalling/unmarshalling of SOAP Messages which conform to SOAP (1.1 and 1.2) Encoding rules.
 * Marshalling/unmarshalling of standard XML documents is handled by standard Spring OXM marshallers/unmarshallers.</p>
 *
 * <p>Code in this packaged is slightly modeled after org.apache.axis.message and org.apache.axis.encoding packages of Axis1.</p>
 * 
 * <p>Axis1 implements SOAP Encoding in the spirit of JAX-RPC - i.e., it is custom marshalling framework, which in JAX-WS was replaced by JAXB2.
 * JAXB2 brought some standard concepts and releaved JAX-WS from its own definition of OXM mapping. JAXB2 has excellent annotations - maybe we could use
 * them in SOAP Encoding Marshallers (just a thought...)?</p>
 */
package org.springframework.ws.ext.bind;

