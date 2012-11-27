/**
 * EchoEndpointServiceLocator.java
 *
 * This file was auto-generated from WSDL
 * by the Apache Axis 1.4 Apr 22, 2006 (06:55:48 PDT) WSDL2Java emitter.
 */

package org.springframework.ws.axis1.case2.contractfirst.wrapped_literal_12_arrayswrapped;

public class EchoEndpointServiceLocator extends org.apache.axis.client.Service implements org.springframework.ws.axis1.case2.contractfirst.wrapped_literal_12_arrayswrapped.EchoEndpointService {

    public EchoEndpointServiceLocator() {
    }


    public EchoEndpointServiceLocator(org.apache.axis.EngineConfiguration config) {
        super(config);
    }

    public EchoEndpointServiceLocator(java.lang.String wsdlLoc, javax.xml.namespace.QName sName) throws javax.xml.rpc.ServiceException {
        super(wsdlLoc, sName);
    }

    // Use to get a proxy class for EchoEndpointWrappedLiteral12
    private java.lang.String EchoEndpointWrappedLiteral12_address = "http://axis1.org/ws/services/EchoEndpointWrappedLiteral12";

    public java.lang.String getEchoEndpointWrappedLiteral12Address() {
        return EchoEndpointWrappedLiteral12_address;
    }

    // The WSDD service name defaults to the port name.
    private java.lang.String EchoEndpointWrappedLiteral12WSDDServiceName = "EchoEndpointWrappedLiteral12";

    public java.lang.String getEchoEndpointWrappedLiteral12WSDDServiceName() {
        return EchoEndpointWrappedLiteral12WSDDServiceName;
    }

    public void setEchoEndpointWrappedLiteral12WSDDServiceName(java.lang.String name) {
        EchoEndpointWrappedLiteral12WSDDServiceName = name;
    }

    public org.springframework.ws.axis1.case2.contractfirst.wrapped_literal_12_arrayswrapped.EchoEndpoint getEchoEndpointWrappedLiteral12() throws javax.xml.rpc.ServiceException {
       java.net.URL endpoint;
        try {
            endpoint = new java.net.URL(EchoEndpointWrappedLiteral12_address);
        }
        catch (java.net.MalformedURLException e) {
            throw new javax.xml.rpc.ServiceException(e);
        }
        return getEchoEndpointWrappedLiteral12(endpoint);
    }

    public org.springframework.ws.axis1.case2.contractfirst.wrapped_literal_12_arrayswrapped.EchoEndpoint getEchoEndpointWrappedLiteral12(java.net.URL portAddress) throws javax.xml.rpc.ServiceException {
        try {
            org.springframework.ws.axis1.case2.contractfirst.wrapped_literal_12_arrayswrapped.EchoEndpointWrappedLiteral12SoapBindingStub _stub = new org.springframework.ws.axis1.case2.contractfirst.wrapped_literal_12_arrayswrapped.EchoEndpointWrappedLiteral12SoapBindingStub(portAddress, this);
            _stub.setPortName(getEchoEndpointWrappedLiteral12WSDDServiceName());
            return _stub;
        }
        catch (org.apache.axis.AxisFault e) {
            return null;
        }
    }

    public void setEchoEndpointWrappedLiteral12EndpointAddress(java.lang.String address) {
        EchoEndpointWrappedLiteral12_address = address;
    }

    /**
     * For the given interface, get the stub implementation.
     * If this service has no port for the given interface,
     * then ServiceException is thrown.
     */
    public java.rmi.Remote getPort(Class serviceEndpointInterface) throws javax.xml.rpc.ServiceException {
        try {
            if (org.springframework.ws.axis1.case2.contractfirst.wrapped_literal_12_arrayswrapped.EchoEndpoint.class.isAssignableFrom(serviceEndpointInterface)) {
                org.springframework.ws.axis1.case2.contractfirst.wrapped_literal_12_arrayswrapped.EchoEndpointWrappedLiteral12SoapBindingStub _stub = new org.springframework.ws.axis1.case2.contractfirst.wrapped_literal_12_arrayswrapped.EchoEndpointWrappedLiteral12SoapBindingStub(new java.net.URL(EchoEndpointWrappedLiteral12_address), this);
                _stub.setPortName(getEchoEndpointWrappedLiteral12WSDDServiceName());
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
        if ("EchoEndpointWrappedLiteral12".equals(inputPortName)) {
            return getEchoEndpointWrappedLiteral12();
        }
        else  {
            java.rmi.Remote _stub = getPort(serviceEndpointInterface);
            ((org.apache.axis.client.Stub) _stub).setPortName(portName);
            return _stub;
        }
    }

    public javax.xml.namespace.QName getServiceName() {
        return new javax.xml.namespace.QName("http://codefirst.case2.axis1.ws.springframework.org", "EchoEndpointService");
    }

    private java.util.HashSet ports = null;

    public java.util.Iterator getPorts() {
        if (ports == null) {
            ports = new java.util.HashSet();
            ports.add(new javax.xml.namespace.QName("http://codefirst.case2.axis1.ws.springframework.org", "EchoEndpointWrappedLiteral12"));
        }
        return ports.iterator();
    }

    /**
    * Set the endpoint address for the specified port name.
    */
    public void setEndpointAddress(java.lang.String portName, java.lang.String address) throws javax.xml.rpc.ServiceException {
        
if ("EchoEndpointWrappedLiteral12".equals(portName)) {
            setEchoEndpointWrappedLiteral12EndpointAddress(address);
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
