/**
 * ArrayOfParam3.java
 *
 * This file was auto-generated from WSDL
 * by the Apache Axis 1.4 Apr 22, 2006 (06:55:48 PDT) WSDL2Java emitter.
 */

package org.springframework.ws.axis1.case2.contractfirst.wrapped_literal_11_arrayswrapped;

public class ArrayOfParam3  implements java.io.Serializable {
    private org.springframework.ws.axis1.case2.contractfirst.wrapped_literal_11_arrayswrapped.Param3[] item;

    public ArrayOfParam3() {
    }

    public ArrayOfParam3(
           org.springframework.ws.axis1.case2.contractfirst.wrapped_literal_11_arrayswrapped.Param3[] item) {
           this.item = item;
    }


    /**
     * Gets the item value for this ArrayOfParam3.
     * 
     * @return item
     */
    public org.springframework.ws.axis1.case2.contractfirst.wrapped_literal_11_arrayswrapped.Param3[] getItem() {
        return item;
    }


    /**
     * Sets the item value for this ArrayOfParam3.
     * 
     * @param item
     */
    public void setItem(org.springframework.ws.axis1.case2.contractfirst.wrapped_literal_11_arrayswrapped.Param3[] item) {
        this.item = item;
    }

    public org.springframework.ws.axis1.case2.contractfirst.wrapped_literal_11_arrayswrapped.Param3 getItem(int i) {
        return this.item[i];
    }

    public void setItem(int i, org.springframework.ws.axis1.case2.contractfirst.wrapped_literal_11_arrayswrapped.Param3 _value) {
        this.item[i] = _value;
    }

    private java.lang.Object __equalsCalc = null;
    public synchronized boolean equals(java.lang.Object obj) {
        if (!(obj instanceof ArrayOfParam3)) return false;
        ArrayOfParam3 other = (ArrayOfParam3) obj;
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
