/**
 * ArrayOf_xsd_decimal.java
 *
 * This file was auto-generated from WSDL
 * by the Apache Axis 1.4 Apr 22, 2006 (06:55:48 PDT) WSDL2Java emitter.
 */

package org.springframework.ws.axis1.case2.contractfirst.wrapped_literal_11_arrayswrapped;

public class ArrayOf_xsd_decimal  implements java.io.Serializable {
    private java.math.BigDecimal[] item;

    public ArrayOf_xsd_decimal() {
    }

    public ArrayOf_xsd_decimal(
           java.math.BigDecimal[] item) {
           this.item = item;
    }


    /**
     * Gets the item value for this ArrayOf_xsd_decimal.
     * 
     * @return item
     */
    public java.math.BigDecimal[] getItem() {
        return item;
    }


    /**
     * Sets the item value for this ArrayOf_xsd_decimal.
     * 
     * @param item
     */
    public void setItem(java.math.BigDecimal[] item) {
        this.item = item;
    }

    public java.math.BigDecimal getItem(int i) {
        return this.item[i];
    }

    public void setItem(int i, java.math.BigDecimal _value) {
        this.item[i] = _value;
    }

    private java.lang.Object __equalsCalc = null;
    public synchronized boolean equals(java.lang.Object obj) {
        if (!(obj instanceof ArrayOf_xsd_decimal)) return false;
        ArrayOf_xsd_decimal other = (ArrayOf_xsd_decimal) obj;
        if (obj == null) return false;
        if (this == obj) return true;
        if (__equalsCalc != null) {
            return (__equalsCalc == obj);
        }
        __equalsCalc = obj;
        boolean _equals;
        _equals = true && 
            ((this.item==null && other.getItem()==null) || 
             (this.item!=null &&
              java.util.Arrays.equals(this.item, other.getItem())));
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
        if (getItem() != null) {
            for (int i=0;
                 i<java.lang.reflect.Array.getLength(getItem());
                 i++) {
                java.lang.Object obj = java.lang.reflect.Array.get(getItem(), i);
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
