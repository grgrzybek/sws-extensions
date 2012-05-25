/**
 * EchoEndpointDocumentServiceLocator.java
 *
 * This file was auto-generated from WSDL
 * by the Apache Axis 1.4 Apr 22, 2006 (06:55:48 PDT) WSDL2Java emitter.
 */

package org.springframework.ws.axis1.doc_lit;

@SuppressWarnings("all")
public class EchoEndpointDocumentServiceLocator extends org.apache.axis.client.Service implements
		org.springframework.ws.axis1.doc_lit.EchoEndpointDocumentService {

	public EchoEndpointDocumentServiceLocator() {
	}

	public EchoEndpointDocumentServiceLocator(org.apache.axis.EngineConfiguration config) {
		super(config);
	}

	public EchoEndpointDocumentServiceLocator(java.lang.String wsdlLoc, javax.xml.namespace.QName sName) throws javax.xml.rpc.ServiceException {
		super(wsdlLoc, sName);
	}

	// Use to get a proxy class for EchoEndpointDocumentLiteral
	private java.lang.String EchoEndpointDocumentLiteral_address = "http://localhost:29078/axis1/EchoEndpointDocumentLiteral";

	public java.lang.String getEchoEndpointDocumentLiteralAddress() {
		return this.EchoEndpointDocumentLiteral_address;
	}

	// The WSDD service name defaults to the port name.
	private java.lang.String EchoEndpointDocumentLiteralWSDDServiceName = "EchoEndpointDocumentLiteral";

	public java.lang.String getEchoEndpointDocumentLiteralWSDDServiceName() {
		return this.EchoEndpointDocumentLiteralWSDDServiceName;
	}

	public void setEchoEndpointDocumentLiteralWSDDServiceName(java.lang.String name) {
		this.EchoEndpointDocumentLiteralWSDDServiceName = name;
	}

	public org.springframework.ws.axis1.doc_lit.EchoEndpointDocument getEchoEndpointDocumentLiteral() throws javax.xml.rpc.ServiceException {
		java.net.URL endpoint;
		try {
			endpoint = new java.net.URL(this.EchoEndpointDocumentLiteral_address);
		}
		catch (java.net.MalformedURLException e) {
			throw new javax.xml.rpc.ServiceException(e);
		}
		return this.getEchoEndpointDocumentLiteral(endpoint);
	}

	public org.springframework.ws.axis1.doc_lit.EchoEndpointDocument getEchoEndpointDocumentLiteral(java.net.URL portAddress)
			throws javax.xml.rpc.ServiceException {
		try {
			org.springframework.ws.axis1.doc_lit.EchoEndpointDocumentLiteralSoapBindingStub _stub = new org.springframework.ws.axis1.doc_lit.EchoEndpointDocumentLiteralSoapBindingStub(
					portAddress, this);
			_stub.setPortName(this.getEchoEndpointDocumentLiteralWSDDServiceName());
			return _stub;
		}
		catch (org.apache.axis.AxisFault e) {
			return null;
		}
	}

	public void setEchoEndpointDocumentLiteralEndpointAddress(java.lang.String address) {
		this.EchoEndpointDocumentLiteral_address = address;
	}

	/**
	 * For the given interface, get the stub implementation.
	 * If this service has no port for the given interface,
	 * then ServiceException is thrown.
	 */
	public java.rmi.Remote getPort(Class serviceEndpointInterface) throws javax.xml.rpc.ServiceException {
		try {
			if (org.springframework.ws.axis1.doc_lit.EchoEndpointDocument.class.isAssignableFrom(serviceEndpointInterface)) {
				org.springframework.ws.axis1.doc_lit.EchoEndpointDocumentLiteralSoapBindingStub _stub = new org.springframework.ws.axis1.doc_lit.EchoEndpointDocumentLiteralSoapBindingStub(
						new java.net.URL(this.EchoEndpointDocumentLiteral_address), this);
				_stub.setPortName(this.getEchoEndpointDocumentLiteralWSDDServiceName());
				return _stub;
			}
		}
		catch (java.lang.Throwable t) {
			throw new javax.xml.rpc.ServiceException(t);
		}
		throw new javax.xml.rpc.ServiceException("There is no stub implementation for the interface:  "
				+ (serviceEndpointInterface == null ? "null" : serviceEndpointInterface.getName()));
	}

	/**
	 * For the given interface, get the stub implementation.
	 * If this service has no port for the given interface,
	 * then ServiceException is thrown.
	 */
	public java.rmi.Remote getPort(javax.xml.namespace.QName portName, Class serviceEndpointInterface) throws javax.xml.rpc.ServiceException {
		if (portName == null) {
			return this.getPort(serviceEndpointInterface);
		}
		java.lang.String inputPortName = portName.getLocalPart();
		if ("EchoEndpointDocumentLiteral".equals(inputPortName)) {
			return this.getEchoEndpointDocumentLiteral();
		}
		else {
			java.rmi.Remote _stub = this.getPort(serviceEndpointInterface);
			((org.apache.axis.client.Stub) _stub).setPortName(portName);
			return _stub;
		}
	}

	public javax.xml.namespace.QName getServiceName() {
		return new javax.xml.namespace.QName("http://dispatch2.axis1.ws.springframework.org/", "EchoEndpointDocumentService");
	}

	private java.util.HashSet ports = null;

	public java.util.Iterator getPorts() {
		if (this.ports == null) {
			this.ports = new java.util.HashSet();
			this.ports.add(new javax.xml.namespace.QName("http://dispatch2.axis1.ws.springframework.org/", "EchoEndpointDocumentLiteral"));
		}
		return this.ports.iterator();
	}

	/**
	* Set the endpoint address for the specified port name.
	*/
	public void setEndpointAddress(java.lang.String portName, java.lang.String address) throws javax.xml.rpc.ServiceException {

		if ("EchoEndpointDocumentLiteral".equals(portName)) {
			this.setEchoEndpointDocumentLiteralEndpointAddress(address);
		}
		else { // Unknown Port Name
			throw new javax.xml.rpc.ServiceException(" Cannot set Endpoint Address for Unknown Port" + portName);
		}
	}

	/**
	* Set the endpoint address for the specified port name.
	*/
	public void setEndpointAddress(javax.xml.namespace.QName portName, java.lang.String address) throws javax.xml.rpc.ServiceException {
		this.setEndpointAddress(portName.getLocalPart(), address);
	}

}
