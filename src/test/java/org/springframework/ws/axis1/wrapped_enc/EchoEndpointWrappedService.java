/**
 * EchoEndpointWrappedService.java
 *
 * This file was auto-generated from WSDL
 * by the Apache Axis 1.4 Apr 22, 2006 (06:55:48 PDT) WSDL2Java emitter.
 */

package org.springframework.ws.axis1.wrapped_enc;

public interface EchoEndpointWrappedService extends javax.xml.rpc.Service {
	public java.lang.String getEchoEndpointWrappedEncodedAddress();

	public org.springframework.ws.axis1.wrapped_enc.EchoEndpointWrapped getEchoEndpointWrappedEncoded() throws javax.xml.rpc.ServiceException;

	public org.springframework.ws.axis1.wrapped_enc.EchoEndpointWrapped getEchoEndpointWrappedEncoded(java.net.URL portAddress)
			throws javax.xml.rpc.ServiceException;
}
