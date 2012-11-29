/**
 * Param1.java
 *
 * This file was auto-generated from WSDL
 * by the Apache Axis 1.4 Apr 22, 2006 (06:55:48 PDT) WSDL2Java emitter.
 */

package org.springframework.ws.axis1.case2.contractfirst.document_literal_12_arrayswrapped;

public class Param1  implements java.io.Serializable {
   private ArrayOf_xsd_byte list = new ArrayOf_xsd_byte(new byte[] { (byte) 0x24, (byte) 0x9A });

   private ArrayOfParam3 param3List = new ArrayOfParam3(new Param3[] { new Param3(), new Param3(), new Param3() });

 	private String string = "str (String)";

 	private Long number = 37L;

 	private Param2 param2 = new Param2();

    public Param1() {
    }

    public Param1(
           org.springframework.ws.axis1.case2.contractfirst.document_literal_12_arrayswrapped.ArrayOf_xsd_byte list,
           java.lang.Long number,
           org.springframework.ws.axis1.case2.contractfirst.document_literal_12_arrayswrapped.Param2 param2,
           org.springframework.ws.axis1.case2.contractfirst.document_literal_12_arrayswrapped.ArrayOfParam3 param3List,
           java.lang.String string) {
           this.list = list;
           this.number = number;
           this.param2 = param2;
           this.param3List = param3List;
           this.string = string;
    }


    /**
     * Gets the list value for this Param1.
     * 
     * @return list
     */
    public org.springframework.ws.axis1.case2.contractfirst.document_literal_12_arrayswrapped.ArrayOf_xsd_byte getList() {
        return list;
    }


    /**
     * Sets the list value for this Param1.
     * 
     * @param list
     */
    public void setList(org.springframework.ws.axis1.case2.contractfirst.document_literal_12_arrayswrapped.ArrayOf_xsd_byte list) {
        this.list = list;
    }


    /**
     * Gets the number value for this Param1.
     * 
     * @return number
     */
    public java.lang.Long getNumber() {
        return number;
    }


    /**
     * Sets the number value for this Param1.
     * 
     * @param number
     */
    public void setNumber(java.lang.Long number) {
        this.number = number;
    }


    /**
     * Gets the param2 value for this Param1.
     * 
     * @return param2
     */
    public org.springframework.ws.axis1.case2.contractfirst.document_literal_12_arrayswrapped.Param2 getParam2() {
        return param2;
    }


    /**
     * Sets the param2 value for this Param1.
     * 
     * @param param2
     */
    public void setParam2(org.springframework.ws.axis1.case2.contractfirst.document_literal_12_arrayswrapped.Param2 param2) {
        this.param2 = param2;
    }


    /**
     * Gets the param3List value for this Param1.
     * 
     * @return param3List
     */
    public org.springframework.ws.axis1.case2.contractfirst.document_literal_12_arrayswrapped.ArrayOfParam3 getParam3List() {
        return param3List;
    }


    /**
     * Sets the param3List value for this Param1.
     * 
     * @param param3List
     */
    public void setParam3List(org.springframework.ws.axis1.case2.contractfirst.document_literal_12_arrayswrapped.ArrayOfParam3 param3List) {
        this.param3List = param3List;
    }


    /**
     * Gets the string value for this Param1.
     * 
     * @return string
     */
    public java.lang.String getString() {
        return string;
    }


    /**
     * Sets the string value for this Param1.
     * 
     * @param string
     */
    public void setString(java.lang.String string) {
        this.string = string;
    }

    private java.lang.Object __equalsCalc = null;
    public synchronized boolean equals(java.lang.Object obj) {
        if (!(obj instanceof Param1)) return false;
        Param1 other = (Param1) obj;
        if (obj == null) return false;
        if (this == obj) return true;
        if (__equalsCalc != null) {
            return (__equalsCalc == obj);
        }
        __equalsCalc = obj;
        boolean _equals;
        _equals = true && 
            ((this.list==null && other.getList()==null) || 
             (this.list!=null &&
              this.list.equals(other.getList()))) &&
            ((this.number==null && other.getNumber()==null) || 
             (this.number!=null &&
              this.number.equals(other.getNumber()))) &&
            ((this.param2==null && other.getParam2()==null) || 
             (this.param2!=null &&
              this.param2.equals(other.getParam2()))) &&
            ((this.param3List==null && other.getParam3List()==null) || 
             (this.param3List!=null &&
              this.param3List.equals(other.getParam3List()))) &&
            ((this.string==null && other.getString()==null) || 
             (this.string!=null &&
              this.string.equals(other.getString())));
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
        if (getList() != null) {
            _hashCode += getList().hashCode();
        }
        if (getNumber() != null) {
            _hashCode += getNumber().hashCode();
        }
        if (getParam2() != null) {
            _hashCode += getParam2().hashCode();
        }
        if (getParam3List() != null) {
            _hashCode += getParam3List().hashCode();
        }
        if (getString() != null) {
            _hashCode += getString().hashCode();
        }
        __hashCodeCalc = false;
        return _hashCode;
    }

}
