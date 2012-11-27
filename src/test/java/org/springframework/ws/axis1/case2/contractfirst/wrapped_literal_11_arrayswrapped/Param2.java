/**
 * Param2.java
 *
 * This file was auto-generated from WSDL
 * by the Apache Axis 1.4 Apr 22, 2006 (06:55:48 PDT) WSDL2Java emitter.
 */

package org.springframework.ws.axis1.case2.contractfirst.wrapped_literal_11_arrayswrapped;

import java.math.BigDecimal;

public class Param2  implements java.io.Serializable {
	private ArrayOf_xsd_string list = new ArrayOf_xsd_string(new String[] { "str1", "str2", "str3" });

	private ArrayOf_xsd_anyType param3List = new ArrayOf_xsd_anyType(new Object[] { new Param3(), new Param3() });

	private ArrayOfParam3 param3Tab = new ArrayOfParam3(new Param3[] { new Param3(), new Param3(), new Param3() });

	private BigDecimal bd = new BigDecimal("37.98");

	private Integer number = 44;

    public Param2() {
    }

    public Param2(
           java.math.BigDecimal bd,
           org.springframework.ws.axis1.case2.contractfirst.wrapped_literal_11_arrayswrapped.ArrayOf_xsd_string list,
           java.lang.Integer number,
           org.springframework.ws.axis1.case2.contractfirst.wrapped_literal_11_arrayswrapped.ArrayOf_xsd_anyType param3List,
           org.springframework.ws.axis1.case2.contractfirst.wrapped_literal_11_arrayswrapped.ArrayOfParam3 param3Tab) {
           this.bd = bd;
           this.list = list;
           this.number = number;
           this.param3List = param3List;
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
    public org.springframework.ws.axis1.case2.contractfirst.wrapped_literal_11_arrayswrapped.ArrayOf_xsd_string getList() {
        return list;
    }


    /**
     * Sets the list value for this Param2.
     * 
     * @param list
     */
    public void setList(org.springframework.ws.axis1.case2.contractfirst.wrapped_literal_11_arrayswrapped.ArrayOf_xsd_string list) {
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
     * Gets the param3List value for this Param2.
     * 
     * @return param3List
     */
    public org.springframework.ws.axis1.case2.contractfirst.wrapped_literal_11_arrayswrapped.ArrayOf_xsd_anyType getParam3List() {
        return param3List;
    }


    /**
     * Sets the param3List value for this Param2.
     * 
     * @param param3List
     */
    public void setParam3List(org.springframework.ws.axis1.case2.contractfirst.wrapped_literal_11_arrayswrapped.ArrayOf_xsd_anyType param3List) {
        this.param3List = param3List;
    }


    /**
     * Gets the param3Tab value for this Param2.
     * 
     * @return param3Tab
     */
    public org.springframework.ws.axis1.case2.contractfirst.wrapped_literal_11_arrayswrapped.ArrayOfParam3 getParam3Tab() {
        return param3Tab;
    }


    /**
     * Sets the param3Tab value for this Param2.
     * 
     * @param param3Tab
     */
    public void setParam3Tab(org.springframework.ws.axis1.case2.contractfirst.wrapped_literal_11_arrayswrapped.ArrayOfParam3 param3Tab) {
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
              this.list.equals(other.getList()))) &&
            ((this.number==null && other.getNumber()==null) || 
             (this.number!=null &&
              this.number.equals(other.getNumber()))) &&
            ((this.param3List==null && other.getParam3List()==null) || 
             (this.param3List!=null &&
              this.param3List.equals(other.getParam3List()))) &&
            ((this.param3Tab==null && other.getParam3Tab()==null) || 
             (this.param3Tab!=null &&
              this.param3Tab.equals(other.getParam3Tab())));
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
            _hashCode += getList().hashCode();
        }
        if (getNumber() != null) {
            _hashCode += getNumber().hashCode();
        }
        if (getParam3List() != null) {
            _hashCode += getParam3List().hashCode();
        }
        if (getParam3Tab() != null) {
            _hashCode += getParam3Tab().hashCode();
        }
        __hashCodeCalc = false;
        return _hashCode;
    }

}
