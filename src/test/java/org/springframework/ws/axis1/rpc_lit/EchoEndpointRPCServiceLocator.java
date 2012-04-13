/**
 * EchoEndpointRPCServiceLocator.java
 *
 * This file was auto-generated from WSDL
 * by the Apache Axis 1.4 Apr 22, 2006 (06:55:48 PDT) WSDL2Java emitter.
 */

package org.springframework.ws.axis1.rpc_lit;

@SuppressWarnings("all")
public class EchoEndpointRPCServiceLocator extends org.apache.axis.client.Service implements org.springframework.ws.axis1.rpc_lit.EchoEndpointRPCService {

    public EchoEndpointRPCServiceLocator() {
    }


    public EchoEndpointRPCServiceLocator(org.apache.axis.EngineConfiguration config) {
        super(config);
    }

    public EchoEndpointRPCServiceLocator(java.lang.String wsdlLoc, javax.xml.namespace.QName sName) throws javax.xml.rpc.ServiceException {
        super(wsdlLoc, sName);
    }

    // Use to get a proxy class for EchoEndpointRPCLiteral
    private java.lang.String EchoEndpointRPCLiteral_address = "http://localhost:42259/axis1/EchoEndpointRPCLiteral";

    public java.lang.String getEchoEndpointRPCLiteralAddress() {
        return EchoEndpointRPCLiteral_address;
    }

    // The WSDD service name defaults to the port name.
    private java.lang.String EchoEndpointRPCLiteralWSDDServiceName = "EchoEndpointRPCLiteral";

    public java.lang.String getEchoEndpointRPCLiteralWSDDServiceName() {
        return EchoEndpointRPCLiteralWSDDServiceName;
    }

    public void setEchoEndpointRPCLiteralWSDDServiceName(java.lang.String name) {
        EchoEndpointRPCLiteralWSDDServiceName = name;
    }

    public org.springframework.ws.axis1.rpc_lit.EchoEndpointRPC getEchoEndpointRPCLiteral() throws javax.xml.rpc.ServiceException {
       java.net.URL endpoint;
        try {
            endpoint = new java.net.URL(EchoEndpointRPCLiteral_address);
        }
        catch (java.net.MalformedURLException e) {
            throw new javax.xml.rpc.ServiceException(e);
        }
        return getEchoEndpointRPCLiteral(endpoint);
    }

    public org.springframework.ws.axis1.rpc_lit.EchoEndpointRPC getEchoEndpointRPCLiteral(java.net.URL portAddress) throws javax.xml.rpc.ServiceException {
        try {
            org.springframework.ws.axis1.rpc_lit.EchoEndpointRPCLiteralSoapBindingStub _stub = new org.springframework.ws.axis1.rpc_lit.EchoEndpointRPCLiteralSoapBindingStub(portAddress, this);
            _stub.setPortName(getEchoEndpointRPCLiteralWSDDServiceName());
            return _stub;
        }
        catch (org.apache.axis.AxisFault e) {
            return null;
        }
    }

    public void setEchoEndpointRPCLiteralEndpointAddress(java.lang.String address) {
        EchoEndpointRPCLiteral_address = address;
    }

    /**
     * For the given interface, get the stub implementation.
     * If this service has no port for the given interface,
     * then ServiceException is thrown.
     */
    public java.rmi.Remote getPort(Class serviceEndpointInterface) throws javax.xml.rpc.ServiceException {
        try {
            if (org.springframework.ws.axis1.rpc_lit.EchoEndpointRPC.class.isAssignableFrom(serviceEndpointInterface)) {
                org.springframework.ws.axis1.rpc_lit.EchoEndpointRPCLiteralSoapBindingStub _stub = new org.springframework.ws.axis1.rpc_lit.EchoEndpointRPCLiteralSoapBindingStub(new java.net.URL(EchoEndpointRPCLiteral_address), this);
                _stub.setPortName(getEchoEndpointRPCLiteralWSDDServiceName());
                return _stub;
            }
        }
        catch (java.lang.Throwable t) {
            throw new javax.xml.rpc.ServiceException(t);
        }
        throw new javax.xml.rpc.ServiceException("There is no stub implementation for the interface:  " + (serviceEndpointInterface == null ? "null" : serviceEndpointInterface.getName()));
    }

    /**
     * For the given interface, get the stub implementation.
     * If this service has no port for the given interface,
     * then ServiceException is thrown.
     */
    public java.rmi.Remote getPort(javax.xml.namespace.QName portName, Class serviceEndpointInterface) throws javax.xml.rpc.ServiceException {
        if (portName == null) {
            return getPort(serviceEndpointInterface);
        }
        java.lang.String inputPortName = portName.getLocalPart();
        if ("EchoEndpointRPCLiteral".equals(inputPortName)) {
            return getEchoEndpointRPCLiteral();
        }
        else  {
            java.rmi.Remote _stub = getPort(serviceEndpointInterface);
            ((org.apache.axis.client.Stub) _stub).setPortName(portName);
            return _stub;
        }
    }

    public javax.xml.namespace.QName getServiceName() {
        return new javax.xml.namespace.QName("http://dispatch2.axis1.ws.springframework.org/", "EchoEndpointRPCService");
    }

    private java.util.HashSet ports = null;

    public java.util.Iterator getPorts() {
        if (ports == null) {
            ports = new java.util.HashSet();
            ports.add(new javax.xml.namespace.QName("http://dispatch2.axis1.ws.springframework.org/", "EchoEndpointRPCLiteral"));
        }
        return ports.iterator();
    }

    /**
    * Set the endpoint address for the specified port name.
    */
    public void setEndpointAddress(java.lang.String portName, java.lang.String address) throws javax.xml.rpc.ServiceException {
        
if ("EchoEndpointRPCLiteral".equals(portName)) {
            setEchoEndpointRPCLiteralEndpointAddress(address);
        }
        else 
{ // Unknown Port Name
            throw new javax.xml.rpc.ServiceException(" Cannot set Endpoint Address for Unknown Port" + portName);
        }
    }

    /**
    * Set the endpoint address for the specified port name.
    */
    public void setEndpointAddress(javax.xml.namespace.QName portName, java.lang.String address) throws javax.xml.rpc.ServiceException {
        setEndpointAddress(portName.getLocalPart(), address);
    }

}
