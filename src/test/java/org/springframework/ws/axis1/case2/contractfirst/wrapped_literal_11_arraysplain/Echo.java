/**
 * Echo.java
 *
 * This file was auto-generated from WSDL
 * by the Apache Axis 1.4 Apr 22, 2006 (06:55:48 PDT) WSDL2Java emitter.
 */

package org.springframework.ws.axis1.case2.contractfirst.wrapped_literal_11_arraysplain;

public class Echo  implements java.io.Serializable {
    private org.springframework.ws.axis1.case2.contractfirst.wrapped_literal_11_arraysplain.Param1 param;

    private org.springframework.ws.axis1.case2.contractfirst.wrapped_literal_11_arraysplain.Param2 param2;

    public Echo() {
    }

    public Echo(
           org.springframework.ws.axis1.case2.contractfirst.wrapped_literal_11_arraysplain.Param1 param,
           org.springframework.ws.axis1.case2.contractfirst.wrapped_literal_11_arraysplain.Param2 param2) {
           this.param = param;
           this.param2 = param2;
    }


    /**
     * Gets the param value for this Echo.
     * 
     * @return param
     */
    public org.springframework.ws.axis1.case2.contractfirst.wrapped_literal_11_arraysplain.Param1 getParam() {
        return param;
    }


    /**
     * Sets the param value for this Echo.
     * 
     * @param param
     */
    public void setParam(org.springframework.ws.axis1.case2.contractfirst.wrapped_literal_11_arraysplain.Param1 param) {
        this.param = param;
    }


    /**
     * Gets the param2 value for this Echo.
     * 
     * @return param2
     */
    public org.springframework.ws.axis1.case2.contractfirst.wrapped_literal_11_arraysplain.Param2 getParam2() {
        return param2;
    }


    /**
     * Sets the param2 value for this Echo.
     * 
     * @param param2
     */
    public void setParam2(org.springframework.ws.axis1.case2.contractfirst.wrapped_literal_11_arraysplain.Param2 param2) {
        this.param2 = param2;
    }

    private java.lang.Object __equalsCalc = null;
    public synchronized boolean equals(java.lang.Object obj) {
        if (!(obj instanceof Echo)) return false;
        Echo other = (Echo) obj;
        if (obj == null) return false;
        if (this == obj) return true;
        if (__equalsCalc != null) {
            return (__equalsCalc == obj);
        }
        __equalsCalc = obj;
        boolean _equals;
        _equals = true && 
            ((this.param==null && other.getParam()==null) || 
             (this.param!=null &&
              this.param.equals(other.getParam()))) &&
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
        if (getParam() != null) {
            _hashCode += getParam().hashCode();
        }
        if (getParam2() != null) {
            _hashCode += getParam2().hashCode();
        }
        __hashCodeCalc = false;
        return _hashCode;
    }

}
