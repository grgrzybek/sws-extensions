/**
 * OneParamResponse.java
 *
 * This file was auto-generated from WSDL
 * by the Apache Axis 1.4 Apr 22, 2006 (06:55:48 PDT) WSDL2Java emitter.
 */

package org.springframework.ws.axis1.case1.contractfirst.wrapped_encoded_11;

public class OneParamResponse  implements java.io.Serializable {
    private java.lang.String oneParamReturn;

    public OneParamResponse() {
    }

    public OneParamResponse(
           java.lang.String oneParamReturn) {
           this.oneParamReturn = oneParamReturn;
    }


    /**
     * Gets the oneParamReturn value for this OneParamResponse.
     * 
     * @return oneParamReturn
     */
    public java.lang.String getOneParamReturn() {
        return oneParamReturn;
    }


    /**
     * Sets the oneParamReturn value for this OneParamResponse.
     * 
     * @param oneParamReturn
     */
    public void setOneParamReturn(java.lang.String oneParamReturn) {
        this.oneParamReturn = oneParamReturn;
    }

    private java.lang.Object __equalsCalc = null;
    public synchronized boolean equals(java.lang.Object obj) {
        if (!(obj instanceof OneParamResponse)) return false;
        OneParamResponse other = (OneParamResponse) obj;
        if (obj == null) return false;
        if (this == obj) return true;
        if (__equalsCalc != null) {
            return (__equalsCalc == obj);
        }
        __equalsCalc = obj;
        boolean _equals;
        _equals = true && 
            ((this.oneParamReturn==null && other.getOneParamReturn()==null) || 
             (this.oneParamReturn!=null &&
              this.oneParamReturn.equals(other.getOneParamReturn())));
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
        if (getOneParamReturn() != null) {
            _hashCode += getOneParamReturn().hashCode();
        }
        __hashCodeCalc = false;
        return _hashCode;
    }

}
