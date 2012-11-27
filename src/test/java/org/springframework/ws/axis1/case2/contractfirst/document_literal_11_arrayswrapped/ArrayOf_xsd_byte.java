/**
 * ArrayOf_xsd_byte.java
 *
 * This file was auto-generated from WSDL
 * by the Apache Axis 1.4 Apr 22, 2006 (06:55:48 PDT) WSDL2Java emitter.
 */

package org.springframework.ws.axis1.case2.contractfirst.document_literal_11_arrayswrapped;

public class ArrayOf_xsd_byte  implements java.io.Serializable {
    private byte[] item;

    public ArrayOf_xsd_byte() {
    }

    public ArrayOf_xsd_byte(
           byte[] item) {
           this.item = item;
    }


    /**
     * Gets the item value for this ArrayOf_xsd_byte.
     * 
     * @return item
     */
    public byte[] getItem() {
        return item;
    }


    /**
     * Sets the item value for this ArrayOf_xsd_byte.
     * 
     * @param item
     */
    public void setItem(byte[] item) {
        this.item = item;
    }

    public byte getItem(int i) {
        return this.item[i];
    }

    public void setItem(int i, byte _value) {
        this.item[i] = _value;
    }

    private java.lang.Object __equalsCalc = null;
    public synchronized boolean equals(java.lang.Object obj) {
        if (!(obj instanceof ArrayOf_xsd_byte)) return false;
        ArrayOf_xsd_byte other = (ArrayOf_xsd_byte) obj;
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
