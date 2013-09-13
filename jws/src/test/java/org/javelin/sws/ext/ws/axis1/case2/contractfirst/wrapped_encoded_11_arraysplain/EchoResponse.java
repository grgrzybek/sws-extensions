/**
 * EchoResponse.java
 *
 * This file was auto-generated from WSDL
 * by the Apache Axis 1.4 Apr 22, 2006 (06:55:48 PDT) WSDL2Java emitter.
 */

package org.springframework.ws.axis1.case2.contractfirst.wrapped_encoded_11_arraysplain;

public class EchoResponse  implements java.io.Serializable {
    private org.springframework.ws.axis1.case2.contractfirst.wrapped_encoded_11_arraysplain.Result1 echoReturn;

    public EchoResponse() {
    }

    public EchoResponse(
           org.springframework.ws.axis1.case2.contractfirst.wrapped_encoded_11_arraysplain.Result1 echoReturn) {
           this.echoReturn = echoReturn;
    }


    /**
     * Gets the echoReturn value for this EchoResponse.
     * 
     * @return echoReturn
     */
    public org.springframework.ws.axis1.case2.contractfirst.wrapped_encoded_11_arraysplain.Result1 getEchoReturn() {
        return echoReturn;
    }


    /**
     * Sets the echoReturn value for this EchoResponse.
     * 
     * @param echoReturn
     */
    public void setEchoReturn(org.springframework.ws.axis1.case2.contractfirst.wrapped_encoded_11_arraysplain.Result1 echoReturn) {
        this.echoReturn = echoReturn;
    }

    private java.lang.Object __equalsCalc = null;
    public synchronized boolean equals(java.lang.Object obj) {
        if (!(obj instanceof EchoResponse)) return false;
        EchoResponse other = (EchoResponse) obj;
        if (obj == null) return false;
        if (this == obj) return true;
        if (__equalsCalc != null) {
            return (__equalsCalc == obj);
        }
        __equalsCalc = obj;
        boolean _equals;
        _equals = true && 
            ((this.echoReturn==null && other.getEchoReturn()==null) || 
             (this.echoReturn!=null &&
              this.echoReturn.equals(other.getEchoReturn())));
        __equalsCalc = null;
        return _equals;
    }

    private boolean __hashCodeCalc = false;
    public synchronized int hashCode() {
        if (__hashCodeCalc) {
            return 0;
        }
        __hashCodeCalc = true;
        int _hashCode = 1;
        if (getEchoReturn() != null) {
            _hashCode += getEchoReturn().hashCode();
        }
        __hashCodeCalc = false;
        return _hashCode;
    }

}
