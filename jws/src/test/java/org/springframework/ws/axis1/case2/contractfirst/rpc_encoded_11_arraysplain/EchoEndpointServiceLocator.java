/**
 * EchoEndpointServiceLocator.java
 *
 * This file was auto-generated from WSDL
 * by the Apache Axis 1.4 Apr 22, 2006 (06:55:48 PDT) WSDL2Java emitter.
 */

package org.springframework.ws.axis1.case2.contractfirst.rpc_encoded_11_arraysplain;

public class EchoEndpointServiceLocator extends org.apache.axis.client.Service implements org.springframework.ws.axis1.case2.contractfirst.rpc_encoded_11_arraysplain.EchoEndpointService {

    public EchoEndpointServiceLocator() {
    }


    public EchoEndpointServiceLocator(org.apache.axis.EngineConfiguration config) {
        super(config);
    }

    public EchoEndpointServiceLocator(java.lang.String wsdlLoc, javax.xml.namespace.QName sName) throws javax.xml.rpc.ServiceException {
        super(wsdlLoc, sName);
    }

    // Use to get a proxy class for EchoEndpointRpcEncoded11
    private java.lang.String EchoEndpointRpcEncoded11_address = "http://axis1.org/ws/services/EchoEndpointRpcEncoded11";

    public java.lang.String getEchoEndpointRpcEncoded11Address() {
        return EchoEndpointRpcEncoded11_address;
    }

    // The WSDD service name defaults to the port name.
    private java.lang.String EchoEndpointRpcEncoded11WSDDServiceName = "EchoEndpointRpcEncoded11";

    public java.lang.String getEchoEndpointRpcEncoded11WSDDServiceName() {
        return EchoEndpointRpcEncoded11WSDDServiceName;
    }

    public void setEchoEndpointRpcEncoded11WSDDServiceName(java.lang.String name) {
        EchoEndpointRpcEncoded11WSDDServiceName = name;
    }

    public org.springframework.ws.axis1.case2.contractfirst.rpc_encoded_11_arraysplain.EchoEndpoint getEchoEndpointRpcEncoded11() throws javax.xml.rpc.ServiceException {
       java.net.URL endpoint;
        try {
            endpoint = new java.net.URL(EchoEndpointRpcEncoded11_address);
        }
        catch (java.net.MalformedURLException e) {
            throw new javax.xml.rpc.ServiceException(e);
        }
        return getEchoEndpointRpcEncoded11(endpoint);
    }

    public org.springframework.ws.axis1.case2.contractfirst.rpc_encoded_11_arraysplain.EchoEndpoint getEchoEndpointRpcEncoded11(java.net.URL portAddress) throws javax.xml.rpc.ServiceException {
        try {
            org.springframework.ws.axis1.case2.contractfirst.rpc_encoded_11_arraysplain.EchoEndpointRpcEncoded11SoapBindingStub _stub = new org.springframework.ws.axis1.case2.contractfirst.rpc_encoded_11_arraysplain.EchoEndpointRpcEncoded11SoapBindingStub(portAddress, this);
            _stub.setPortName(getEchoEndpointRpcEncoded11WSDDServiceName());
            return _stub;
        }
        catch (org.apache.axis.AxisFault e) {
            return null;
        }
    }

    public void setEchoEndpointRpcEncoded11EndpointAddress(java.lang.String address) {
        EchoEndpointRpcEncoded11_address = address;
    }

    /**
     * For the given interface, get the stub implementation.
     * If this service has no port for the given interface,
     * then ServiceException is thrown.
     */
    public java.rmi.Remote getPort(Class serviceEndpointInterface) throws javax.xml.rpc.ServiceException {
        try {
            if (org.springframework.ws.axis1.case2.contractfirst.rpc_encoded_11_arraysplain.EchoEndpoint.class.isAssignableFrom(serviceEndpointInterface)) {
                org.springframework.ws.axis1.case2.contractfirst.rpc_encoded_11_arraysplain.EchoEndpointRpcEncoded11SoapBindingStub _stub = new org.springframework.ws.axis1.case2.contractfirst.rpc_encoded_11_arraysplain.EchoEndpointRpcEncoded11SoapBindingStub(new java.net.URL(EchoEndpointRpcEncoded11_address), this);
                _stub.setPortName(getEchoEndpointRpcEncoded11WSDDServiceName());
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
        if ("EchoEndpointRpcEncoded11".equals(inputPortName)) {
            return getEchoEndpointRpcEncoded11();
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
            ports.add(new javax.xml.namespace.QName("http://codefirst.case2.axis1.ws.springframework.org", "EchoEndpointRpcEncoded11"));
        }
        return ports.iterator();
    }

    /**
    * Set the endpoint address for the specified port name.
    */
    public void setEndpointAddress(java.lang.String portName, java.lang.String address) throws javax.xml.rpc.ServiceException {
        
if ("EchoEndpointRpcEncoded11".equals(portName)) {
            setEchoEndpointRpcEncoded11EndpointAddress(address);
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
