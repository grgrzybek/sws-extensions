/**
 * Param3.java
 *
 * This file was auto-generated from WSDL
 * by the Apache Axis 1.4 Apr 22, 2006 (06:55:48 PDT) WSDL2Java emitter.
 */

package org.springframework.ws.axis1.case2.contractfirst.rpc_encoded_12_arraysplain;

public class Param3  implements java.io.Serializable {
	private String svalue = "value";

	private Long lvalue = 3L;

    public Param3() {
    }

    public Param3(
           java.lang.Long lvalue,
           java.lang.String svalue) {
           this.lvalue = lvalue;
           this.svalue = svalue;
    }


    /**
     * Gets the lvalue value for this Param3.
     * 
     * @return lvalue
     */
    public java.lang.Long getLvalue() {
        return lvalue;
    }


    /**
     * Sets the lvalue value for this Param3.
     * 
     * @param lvalue
     */
    public void setLvalue(java.lang.Long lvalue) {
        this.lvalue = lvalue;
    }


    /**
     * Gets the svalue value for this Param3.
     * 
     * @return svalue
     */
    public java.lang.String getSvalue() {
        return svalue;
    }


    /**
     * Sets the svalue value for this Param3.
     * 
     * @param svalue
     */
    public void setSvalue(java.lang.String svalue) {
        this.svalue = svalue;
    }

    private java.lang.Object __equalsCalc = null;
    public synchronized boolean equals(java.lang.Object obj) {
        if (!(obj instanceof Param3)) return false;
        Param3 other = (Param3) obj;
        if (obj == null) return false;
        if (this == obj) return true;
        if (__equalsCalc != null) {
            return (__equalsCalc == obj);
        }
        __equalsCalc = obj;
        boolean _equals;
        _equals = true && 
            ((this.lvalue==null && other.getLvalue()==null) || 
             (this.lvalue!=null &&
              this.lvalue.equals(other.getLvalue()))) &&
            ((this.svalue==null && other.getSvalue()==null) || 
             (this.svalue!=null &&
              this.svalue.equals(other.getSvalue())));
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
        if (getLvalue() != null) {
            _hashCode += getLvalue().hashCode();
        }
        if (getSvalue() != null) {
            _hashCode += getSvalue().hashCode();
        }
        __hashCodeCalc = false;
        return _hashCode;
    }

}
