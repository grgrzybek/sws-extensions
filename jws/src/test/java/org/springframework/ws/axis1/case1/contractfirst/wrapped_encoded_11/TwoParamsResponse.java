/**
 * TwoParamsResponse.java
 *
 * This file was auto-generated from WSDL
 * by the Apache Axis 1.4 Apr 22, 2006 (06:55:48 PDT) WSDL2Java emitter.
 */

package org.springframework.ws.axis1.case1.contractfirst.wrapped_encoded_11;

public class TwoParamsResponse  implements java.io.Serializable {
    private java.lang.String twoParamsReturn;

    public TwoParamsResponse() {
    }

    public TwoParamsResponse(
           java.lang.String twoParamsReturn) {
           this.twoParamsReturn = twoParamsReturn;
    }


    /**
     * Gets the twoParamsReturn value for this TwoParamsResponse.
     * 
     * @return twoParamsReturn
     */
    public java.lang.String getTwoParamsReturn() {
        return twoParamsReturn;
    }


    /**
     * Sets the twoParamsReturn value for this TwoParamsResponse.
     * 
     * @param twoParamsReturn
     */
    public void setTwoParamsReturn(java.lang.String twoParamsReturn) {
        this.twoParamsReturn = twoParamsReturn;
    }

    private java.lang.Object __equalsCalc = null;
    public synchronized boolean equals(java.lang.Object obj) {
        if (!(obj instanceof TwoParamsResponse)) return false;
        TwoParamsResponse other = (TwoParamsResponse) obj;
        if (obj == null) return false;
        if (this == obj) return true;
        if (__equalsCalc != null) {
            return (__equalsCalc == obj);
        }
        __equalsCalc = obj;
        boolean _equals;
        _equals = true && 
            ((this.twoParamsReturn==null && other.getTwoParamsReturn()==null) || 
             (this.twoParamsReturn!=null &&
              this.twoParamsReturn.equals(other.getTwoParamsReturn())));
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
        if (getTwoParamsReturn() != null) {
            _hashCode += getTwoParamsReturn().hashCode();
        }
        __hashCodeCalc = false;
        return _hashCode;
    }

}
