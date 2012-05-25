/**
 * Param2.java
 *
 * This file was auto-generated from WSDL
 * by the Apache Axis 1.4 Apr 22, 2006 (06:55:48 PDT) WSDL2Java emitter.
 */

package org.springframework.ws.axis1.doc_lit;

import java.math.BigDecimal;

@SuppressWarnings("all")
public class Param2 implements java.io.Serializable {
	private java.math.BigDecimal bd = new BigDecimal("37.98");

	private java.lang.String[] list = new String[] { "str1", "str2", "str3" };

	private java.lang.Integer number = 44;

	private org.springframework.ws.axis1.doc_lit.Param3[] param3List = new Param3[] { new Param3(), new Param3(), new Param3() };

	public Param2() {
	}

	public Param2(java.math.BigDecimal bd, java.lang.String[] list, java.lang.Integer number, org.springframework.ws.axis1.doc_lit.Param3[] param3List) {
		this.bd = bd;
		this.list = list;
		this.number = number;
		this.param3List = param3List;
	}

	/**
	 * Gets the bd value for this Param2.
	 * 
	 * @return bd
	 */
	public java.math.BigDecimal getBd() {
		return this.bd;
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
		return this.list;
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
		return this.number;
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
	public org.springframework.ws.axis1.doc_lit.Param3[] getParam3List() {
		return this.param3List;
	}

	/**
	 * Sets the param3List value for this Param2.
	 * 
	 * @param param3List
	 */
	public void setParam3List(org.springframework.ws.axis1.doc_lit.Param3[] param3List) {
		this.param3List = param3List;
	}

	private java.lang.Object __equalsCalc = null;

	public synchronized boolean equals(java.lang.Object obj) {
		if (!(obj instanceof Param2))
			return false;
		Param2 other = (Param2) obj;
		if (obj == null)
			return false;
		if (this == obj)
			return true;
		if (this.__equalsCalc != null) {
			return (this.__equalsCalc == obj);
		}
		this.__equalsCalc = obj;
		boolean _equals;
		_equals = true
				&& ((this.bd == null && other.getBd() == null) || (this.bd != null && this.bd.equals(other.getBd())))
				&& ((this.list == null && other.getList() == null) || (this.list != null && java.util.Arrays.equals(this.list, other.getList())))
				&& ((this.number == null && other.getNumber() == null) || (this.number != null && this.number.equals(other.getNumber())))
				&& ((this.param3List == null && other.getParam3List() == null) || (this.param3List != null && java.util.Arrays.equals(this.param3List,
						other.getParam3List())));
		this.__equalsCalc = null;
		return _equals;
	}

	private boolean __hashCodeCalc = false;

	public synchronized int hashCode() {
		if (this.__hashCodeCalc) {
			return 0;
		}
		this.__hashCodeCalc = true;
		int _hashCode = 1;
		if (this.getBd() != null) {
			_hashCode += this.getBd().hashCode();
		}
		if (this.getList() != null) {
			for (int i = 0; i < java.lang.reflect.Array.getLength(this.getList()); i++) {
				java.lang.Object obj = java.lang.reflect.Array.get(this.getList(), i);
				if (obj != null && !obj.getClass().isArray()) {
					_hashCode += obj.hashCode();
				}
			}
		}
		if (this.getNumber() != null) {
			_hashCode += this.getNumber().hashCode();
		}
		if (this.getParam3List() != null) {
			for (int i = 0; i < java.lang.reflect.Array.getLength(this.getParam3List()); i++) {
				java.lang.Object obj = java.lang.reflect.Array.get(this.getParam3List(), i);
				if (obj != null && !obj.getClass().isArray()) {
					_hashCode += obj.hashCode();
				}
			}
		}
		this.__hashCodeCalc = false;
		return _hashCode;
	}

}
