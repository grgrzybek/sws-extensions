/**
 * EchoEndpointServiceLocator.java
 *
 * This file was auto-generated from WSDL
 * by the Apache Axis 1.4 Apr 22, 2006 (06:55:48 PDT) WSDL2Java emitter.
 */

package org.springframework.ws.axis1.case2.contractfirst.wrapped_literal_11_arraysplain;

public class EchoEndpointServiceLocator extends org.apache.axis.client.Service implements org.springframework.ws.axis1.case2.contractfirst.wrapped_literal_11_arraysplain.EchoEndpointService {

    public EchoEndpointServiceLocator() {
    }


    public EchoEndpointServiceLocator(org.apache.axis.EngineConfiguration config) {
        super(config);
    }

    public EchoEndpointServiceLocator(java.lang.String wsdlLoc, javax.xml.namespace.QName sName) throws javax.xml.rpc.ServiceException {
        super(wsdlLoc, sName);
    }

    // Use to get a proxy class for EchoEndpointWrappedLiteral11
    private java.lang.String EchoEndpointWrappedLiteral11_address = "http://axis1.org/ws/services/EchoEndpointWrappedLiteral11";

    public java.lang.String getEchoEndpointWrappedLiteral11Address() {
        return EchoEndpointWrappedLiteral11_address;
    }

    // The WSDD service name defaults to the port name.
    private java.lang.String EchoEndpointWrappedLiteral11WSDDServiceName = "EchoEndpointWrappedLiteral11";

    public java.lang.String getEchoEndpointWrappedLiteral11WSDDServiceName() {
        return EchoEndpointWrappedLiteral11WSDDServiceName;
    }

    public void setEchoEndpointWrappedLiteral11WSDDServiceName(java.lang.String name) {
        EchoEndpointWrappedLiteral11WSDDServiceName = name;
    }

    public org.springframework.ws.axis1.case2.contractfirst.wrapped_literal_11_arraysplain.EchoEndpoint getEchoEndpointWrappedLiteral11() throws javax.xml.rpc.ServiceException {
       java.net.URL endpoint;
        try {
            endpoint = new java.net.URL(EchoEndpointWrappedLiteral11_address);
        }
        catch (java.net.MalformedURLException e) {
            throw new javax.xml.rpc.ServiceException(e);
        }
        return getEchoEndpointWrappedLiteral11(endpoint);
    }

    public org.springframework.ws.axis1.case2.contractfirst.wrapped_literal_11_arraysplain.EchoEndpoint getEchoEndpointWrappedLiteral11(java.net.URL portAddress) throws javax.xml.rpc.ServiceException {
        try {
            org.springframework.ws.axis1.case2.contractfirst.wrapped_literal_11_arraysplain.EchoEndpointWrappedLiteral11SoapBindingStub _stub = new org.springframework.ws.axis1.case2.contractfirst.wrapped_literal_11_arraysplain.EchoEndpointWrappedLiteral11SoapBindingStub(portAddress, this);
            _stub.setPortName(getEchoEndpointWrappedLiteral11WSDDServiceName());
            return _stub;
        }
        catch (org.apache.axis.AxisFault e) {
            return null;
        }
    }

    public void setEchoEndpointWrappedLiteral11EndpointAddress(java.lang.String address) {
        EchoEndpointWrappedLiteral11_address = address;
    }

    /**
     * For the given interface, get the stub implementation.
     * If this service has no port for the given interface,
     * then ServiceException is thrown.
     */
    public java.rmi.Remote getPort(Class serviceEndpointInterface) throws javax.xml.rpc.ServiceException {
        try {
            if (org.springframework.ws.axis1.case2.contractfirst.wrapped_literal_11_arraysplain.EchoEndpoint.class.isAssignableFrom(serviceEndpointInterface)) {
                org.springframework.ws.axis1.case2.contractfirst.wrapped_literal_11_arraysplain.EchoEndpointWrappedLiteral11SoapBindingStub _stub = new org.springframework.ws.axis1.case2.contractfirst.wrapped_literal_11_arraysplain.EchoEndpointWrappedLiteral11SoapBindingStub(new java.net.URL(EchoEndpointWrappedLiteral11_address), this);
                _stub.setPortName(getEchoEndpointWrappedLiteral11WSDDServiceName());
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
        if ("EchoEndpointWrappedLiteral11".equals(inputPortName)) {
            return getEchoEndpointWrappedLiteral11();
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
            ports.add(new javax.xml.namespace.QName("http://codefirst.case2.axis1.ws.springframework.org", "EchoEndpointWrappedLiteral11"));
        }
        return ports.iterator();
    }

    /**
    * Set the endpoint address for the specified port name.
    */
    public void setEndpointAddress(java.lang.String portName, java.lang.String address) throws javax.xml.rpc.ServiceException {
        
if ("EchoEndpointWrappedLiteral11".equals(portName)) {
            setEchoEndpointWrappedLiteral11EndpointAddress(address);
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
