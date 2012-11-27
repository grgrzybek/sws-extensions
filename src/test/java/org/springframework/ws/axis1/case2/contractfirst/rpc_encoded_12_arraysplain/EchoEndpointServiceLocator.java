/**
 * EchoEndpointServiceLocator.java
 *
 * This file was auto-generated from WSDL
 * by the Apache Axis 1.4 Apr 22, 2006 (06:55:48 PDT) WSDL2Java emitter.
 */

package org.springframework.ws.axis1.case2.contractfirst.rpc_encoded_12_arraysplain;

public class EchoEndpointServiceLocator extends org.apache.axis.client.Service implements org.springframework.ws.axis1.case2.contractfirst.rpc_encoded_12_arraysplain.EchoEndpointService {

    public EchoEndpointServiceLocator() {
    }


    public EchoEndpointServiceLocator(org.apache.axis.EngineConfiguration config) {
        super(config);
    }

    public EchoEndpointServiceLocator(java.lang.String wsdlLoc, javax.xml.namespace.QName sName) throws javax.xml.rpc.ServiceException {
        super(wsdlLoc, sName);
    }

    // Use to get a proxy class for EchoEndpointRpcEncoded12
    private java.lang.String EchoEndpointRpcEncoded12_address = "http://axis1.org/ws/services/EchoEndpointRpcEncoded12";

    public java.lang.String getEchoEndpointRpcEncoded12Address() {
        return EchoEndpointRpcEncoded12_address;
    }

    // The WSDD service name defaults to the port name.
    private java.lang.String EchoEndpointRpcEncoded12WSDDServiceName = "EchoEndpointRpcEncoded12";

    public java.lang.String getEchoEndpointRpcEncoded12WSDDServiceName() {
        return EchoEndpointRpcEncoded12WSDDServiceName;
    }

    public void setEchoEndpointRpcEncoded12WSDDServiceName(java.lang.String name) {
        EchoEndpointRpcEncoded12WSDDServiceName = name;
    }

    public org.springframework.ws.axis1.case2.contractfirst.rpc_encoded_12_arraysplain.EchoEndpoint getEchoEndpointRpcEncoded12() throws javax.xml.rpc.ServiceException {
       java.net.URL endpoint;
        try {
            endpoint = new java.net.URL(EchoEndpointRpcEncoded12_address);
        }
        catch (java.net.MalformedURLException e) {
            throw new javax.xml.rpc.ServiceException(e);
        }
        return getEchoEndpointRpcEncoded12(endpoint);
    }

    public org.springframework.ws.axis1.case2.contractfirst.rpc_encoded_12_arraysplain.EchoEndpoint getEchoEndpointRpcEncoded12(java.net.URL portAddress) throws javax.xml.rpc.ServiceException {
        try {
            org.springframework.ws.axis1.case2.contractfirst.rpc_encoded_12_arraysplain.EchoEndpointRpcEncoded12SoapBindingStub _stub = new org.springframework.ws.axis1.case2.contractfirst.rpc_encoded_12_arraysplain.EchoEndpointRpcEncoded12SoapBindingStub(portAddress, this);
            _stub.setPortName(getEchoEndpointRpcEncoded12WSDDServiceName());
            return _stub;
        }
        catch (org.apache.axis.AxisFault e) {
            return null;
        }
    }

    public void setEchoEndpointRpcEncoded12EndpointAddress(java.lang.String address) {
        EchoEndpointRpcEncoded12_address = address;
    }

    /**
     * For the given interface, get the stub implementation.
     * If this service has no port for the given interface,
     * then ServiceException is thrown.
     */
    public java.rmi.Remote getPort(Class serviceEndpointInterface) throws javax.xml.rpc.ServiceException {
        try {
            if (org.springframework.ws.axis1.case2.contractfirst.rpc_encoded_12_arraysplain.EchoEndpoint.class.isAssignableFrom(serviceEndpointInterface)) {
                org.springframework.ws.axis1.case2.contractfirst.rpc_encoded_12_arraysplain.EchoEndpointRpcEncoded12SoapBindingStub _stub = new org.springframework.ws.axis1.case2.contractfirst.rpc_encoded_12_arraysplain.EchoEndpointRpcEncoded12SoapBindingStub(new java.net.URL(EchoEndpointRpcEncoded12_address), this);
                _stub.setPortName(getEchoEndpointRpcEncoded12WSDDServiceName());
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
        if ("EchoEndpointRpcEncoded12".equals(inputPortName)) {
            return getEchoEndpointRpcEncoded12();
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
            ports.add(new javax.xml.namespace.QName("http://codefirst.case2.axis1.ws.springframework.org", "EchoEndpointRpcEncoded12"));
        }
        return ports.iterator();
    }

    /**
    * Set the endpoint address for the specified port name.
    */
    public void setEndpointAddress(java.lang.String portName, java.lang.String address) throws javax.xml.rpc.ServiceException {
        
if ("EchoEndpointRpcEncoded12".equals(portName)) {
            setEchoEndpointRpcEncoded12EndpointAddress(address);
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
