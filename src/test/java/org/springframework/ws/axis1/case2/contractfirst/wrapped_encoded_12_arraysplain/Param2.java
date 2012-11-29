/**
 * Param2.java
 *
 * This file was auto-generated from WSDL
 * by the Apache Axis 1.4 Apr 22, 2006 (06:55:48 PDT) WSDL2Java emitter.
 */

package org.springframework.ws.axis1.case2.contractfirst.wrapped_encoded_12_arraysplain;

import java.math.BigDecimal;

public class Param2  implements java.io.Serializable {
	private BigDecimal bd = new BigDecimal("37.98");

	private String[] list = new String[] { "str1", "str2", "str3" };

	private Param3[] param3Tab = new Param3[] { new Param3(), new Param3(), new Param3() };

	private Integer number = 44;

    public Param2() {
    }

    public Param2(
           java.math.BigDecimal bd,
           java.lang.String[] list,
           java.lang.Integer number,
           org.springframework.ws.axis1.case2.contractfirst.wrapped_encoded_12_arraysplain.Param3[] param3Tab) {
           this.bd = bd;
           this.list = list;
           this.number = number;
           this.param3Tab = param3Tab;
    }


    /**
     * Gets the bd value for this Param2.
     * 
     * @return bd
     */
    public java.math.BigDecimal getBd() {
        return bd;
    }


    /**
     * Sets the bd value for this Param2.
     * 
     * @param bd
     */
    public void setBd(java.math.BigDecimal bd) {
        this.bd = bd;
    }


    /**
     * Gets the list value for this Param2.
     * 
     * @return list
     */
    public java.lang.String[] getList() {
        return list;
    }


    /**
     * Sets the list value for this Param2.
     * 
     * @param list
     */
    public void setList(java.lang.String[] list) {
        this.list = list;
    }


    /**
     * Gets the number value for this Param2.
     * 
     * @return number
     */
    public java.lang.Integer getNumber() {
        return number;
    }


    /**
     * Sets the number value for this Param2.
     * 
     * @param number
     */
    public void setNumber(java.lang.Integer number) {
        this.number = number;
    }


    /**
     * Gets the param3Tab value for this Param2.
     * 
     * @return param3Tab
     */
    public org.springframework.ws.axis1.case2.contractfirst.wrapped_encoded_12_arraysplain.Param3[] getParam3Tab() {
        return param3Tab;
    }


    /**
     * Sets the param3Tab value for this Param2.
     * 
     * @param param3Tab
     */
    public void setParam3Tab(org.springframework.ws.axis1.case2.contractfirst.wrapped_encoded_12_arraysplain.Param3[] param3Tab) {
        this.param3Tab = param3Tab;
    }

    private java.lang.Object __equalsCalc = null;
    public synchronized boolean equals(java.lang.Object obj) {
        if (!(obj instanceof Param2)) return false;
        Param2 other = (Param2) obj;
        if (obj == null) return false;
        if (this == obj) return true;
        if (__equalsCalc != null) {
            return (__equalsCalc == obj);
        }
        __equalsCalc = obj;
        boolean _equals;
        _equals = true && 
            ((this.bd==null && other.getBd()==null) || 
             (this.bd!=null &&
              this.bd.equals(other.getBd()))) &&
            ((this.list==null && other.getList()==null) || 
             (this.list!=null &&
              java.util.Arrays.equals(this.list, other.getList()))) &&
            ((this.number==null && other.getNumber()==null) || 
             (this.number!=null &&
              this.number.equals(other.getNumber()))) &&
            ((this.param3Tab==null && other.getParam3Tab()==null) || 
             (this.param3Tab!=null &&
              java.util.Arrays.equals(this.param3Tab, other.getParam3Tab())));
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
        if (getBd() != null) {
            _hashCode += getBd().hashCode();
        }
        if (getList() != null) {
            for (int i=0;
                 i<java.lang.reflect.Array.getLength(getList());
                 i++) {
                java.lang.Object obj = java.lang.reflect.Array.get(getList(), i);
                if (obj != null &&
                    !obj.getClass().isArray()) {
                    _hashCode += obj.hashCode();
                }
            }
        }
        if (getNumber() != null) {
            _hashCode += getNumber().hashCode();
        }
        if (getParam3Tab() != null) {
            for (int i=0;
                 i<java.lang.reflect.Array.getLength(getParam3Tab());
                 i++) {
                java.lang.Object obj = java.lang.reflect.Array.get(getParam3Tab(), i);
                if (obj != null &&
                    !obj.getClass().isArray()) {
                    _hashCode += obj.hashCode();
                }
            }
        }
        __hashCodeCalc = false;
        return _hashCode;
    }

}
