/**
 * TwoParams.java
 *
 * This file was auto-generated from WSDL
 * by the Apache Axis 1.4 Apr 22, 2006 (06:55:48 PDT) WSDL2Java emitter.
 */

package org.springframework.ws.axis1.case1.codefirst.wrapped_encoded_12;

public class TwoParams  implements java.io.Serializable {
    private java.lang.String param1;

    private java.lang.String param2;

    public TwoParams() {
    }

    public TwoParams(
           java.lang.String param1,
           java.lang.String param2) {
           this.param1 = param1;
           this.param2 = param2;
    }


    /**
     * Gets the param1 value for this TwoParams.
     * 
     * @return param1
     */
    public java.lang.String getParam1() {
        return param1;
    }


    /**
     * Sets the param1 value for this TwoParams.
     * 
     * @param param1
     */
    public void setParam1(java.lang.String param1) {
        this.param1 = param1;
    }


    /**
     * Gets the param2 value for this TwoParams.
     * 
     * @return param2
     */
    public java.lang.String getParam2() {
        return param2;
    }


    /**
     * Sets the param2 value for this TwoParams.
     * 
     * @param param2
     */
    public void setParam2(java.lang.String param2) {
        this.param2 = param2;
    }

    private java.lang.Object __equalsCalc = null;
    public synchronized boolean equals(java.lang.Object obj) {
        if (!(obj instanceof TwoParams)) return false;
        TwoParams other = (TwoParams) obj;
        if (obj == null) return false;
        if (this == obj) return true;
        if (__equalsCalc != null) {
            return (__equalsCalc == obj);
        }
        __equalsCalc = obj;
        boolean _equals;
        _equals = true && 
            ((this.param1==null && other.getParam1()==null) || 
             (this.param1!=null &&
              this.param1.equals(other.getParam1()))) &&
            ((this.param2==null && other.getParam2()==null) || 
             (this.param2!=null &&
              this.param2.equals(other.getParam2())));
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
        if (getParam1() != null) {
            _hashCode += getParam1().hashCode();
        }
        if (getParam2() != null) {
            _hashCode += getParam2().hashCode();
        }
        __hashCodeCalc = false;
        return _hashCode;
    }

}
