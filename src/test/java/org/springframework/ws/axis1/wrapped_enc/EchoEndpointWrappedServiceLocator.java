/**
 * EchoEndpointWrappedServiceLocator.java
 *
 * This file was auto-generated from WSDL
 * by the Apache Axis 1.4 Apr 22, 2006 (06:55:48 PDT) WSDL2Java emitter.
 */

package org.springframework.ws.axis1.wrapped_enc;

@SuppressWarnings("all")
public class EchoEndpointWrappedServiceLocator extends org.apache.axis.client.Service implements org.springframework.ws.axis1.wrapped_enc.EchoEndpointWrappedService {

    public EchoEndpointWrappedServiceLocator() {
    }


    public EchoEndpointWrappedServiceLocator(org.apache.axis.EngineConfiguration config) {
        super(config);
    }

    public EchoEndpointWrappedServiceLocator(java.lang.String wsdlLoc, javax.xml.namespace.QName sName) throws javax.xml.rpc.ServiceException {
        super(wsdlLoc, sName);
    }

    // Use to get a proxy class for EchoEndpointWrappedEncoded
    private java.lang.String EchoEndpointWrappedEncoded_address = "http://localhost:42259/axis1/EchoEndpointWrappedEncoded";

    public java.lang.String getEchoEndpointWrappedEncodedAddress() {
        return EchoEndpointWrappedEncoded_address;
    }

    // The WSDD service name defaults to the port name.
    private java.lang.String EchoEndpointWrappedEncodedWSDDServiceName = "EchoEndpointWrappedEncoded";

    public java.lang.String getEchoEndpointWrappedEncodedWSDDServiceName() {
        return EchoEndpointWrappedEncodedWSDDServiceName;
    }

    public void setEchoEndpointWrappedEncodedWSDDServiceName(java.lang.String name) {
        EchoEndpointWrappedEncodedWSDDServiceName = name;
    }

    public org.springframework.ws.axis1.wrapped_enc.EchoEndpointWrapped getEchoEndpointWrappedEncoded() throws javax.xml.rpc.ServiceException {
       java.net.URL endpoint;
        try {
            endpoint = new java.net.URL(EchoEndpointWrappedEncoded_address);
        }
        catch (java.net.MalformedURLException e) {
            throw new javax.xml.rpc.ServiceException(e);
        }
        return getEchoEndpointWrappedEncoded(endpoint);
    }

    public org.springframework.ws.axis1.wrapped_enc.EchoEndpointWrapped getEchoEndpointWrappedEncoded(java.net.URL portAddress) throws javax.xml.rpc.ServiceException {
        try {
            org.springframework.ws.axis1.wrapped_enc.EchoEndpointWrappedEncodedSoapBindingStub _stub = new org.springframework.ws.axis1.wrapped_enc.EchoEndpointWrappedEncodedSoapBindingStub(portAddress, this);
            _stub.setPortName(getEchoEndpointWrappedEncodedWSDDServiceName());
            return _stub;
        }
        catch (org.apache.axis.AxisFault e) {
            return null;
        }
    }

    public void setEchoEndpointWrappedEncodedEndpointAddress(java.lang.String address) {
        EchoEndpointWrappedEncoded_address = address;
    }

    /**
     * For the given interface, get the stub implementation.
     * If this service has no port for the given interface,
     * then ServiceException is thrown.
     */
    public java.rmi.Remote getPort(Class serviceEndpointInterface) throws javax.xml.rpc.ServiceException {
        try {
            if (org.springframework.ws.axis1.wrapped_enc.EchoEndpointWrapped.class.isAssignableFrom(serviceEndpointInterface)) {
                org.springframework.ws.axis1.wrapped_enc.EchoEndpointWrappedEncodedSoapBindingStub _stub = new org.springframework.ws.axis1.wrapped_enc.EchoEndpointWrappedEncodedSoapBindingStub(new java.net.URL(EchoEndpointWrappedEncoded_address), this);
                _stub.setPortName(getEchoEndpointWrappedEncodedWSDDServiceName());
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
        if ("EchoEndpointWrappedEncoded".equals(inputPortName)) {
            return getEchoEndpointWrappedEncoded();
        }
        else  {
            java.rmi.Remote _stub = getPort(serviceEndpointInterface);
            ((org.apache.axis.client.Stub) _stub).setPortName(portName);
            return _stub;
        }
    }

    public javax.xml.namespace.QName getServiceName() {
        return new javax.xml.namespace.QName("http://dispatch2.axis1.ws.springframework.org/", "EchoEndpointWrappedService");
    }

    private java.util.HashSet ports = null;

    public java.util.Iterator getPorts() {
        if (ports == null) {
            ports = new java.util.HashSet();
            ports.add(new javax.xml.namespace.QName("http://dispatch2.axis1.ws.springframework.org/", "EchoEndpointWrappedEncoded"));
        }
        return ports.iterator();
    }

    /**
    * Set the endpoint address for the specified port name.
    */
    public void setEndpointAddress(java.lang.String portName, java.lang.String address) throws javax.xml.rpc.ServiceException {
        
if ("EchoEndpointWrappedEncoded".equals(portName)) {
            setEchoEndpointWrappedEncodedEndpointAddress(address);
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
