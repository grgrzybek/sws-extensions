/**
 * Result1.java
 *
 * This file was auto-generated from WSDL
 * by the Apache Axis 1.4 Apr 22, 2006 (06:55:48 PDT) WSDL2Java emitter.
 */

package org.springframework.ws.axis1.wrapped_enc;

import java.math.BigDecimal;

@SuppressWarnings("all")
public class Result1 implements java.io.Serializable {
	private java.math.BigDecimal[] list = new BigDecimal[] { new BigDecimal("890.123123"), new BigDecimal("0.12") };

	private org.springframework.ws.axis1.wrapped_enc.Param2 param2 = new Param2();

	private java.lang.String str = "part of result";

	public Result1() {
	}

	public Result1(java.math.BigDecimal[] list, org.springframework.ws.axis1.wrapped_enc.Param2 param2, java.lang.String str) {
		this.list = list;
		this.param2 = param2;
		this.str = str;
	}

	/**
	 * Gets the list value for this Result1.
	 * 
	 * @return list
	 */
	public java.math.BigDecimal[] getList() {
		return this.list;
	}

	/**
	 * Sets the list value for this Result1.
	 * 
	 * @param list
	 */
	public void setList(java.math.BigDecimal[] list) {
		this.list = list;
	}

	/**
	 * Gets the param2 value for this Result1.
	 * 
	 * @return param2
	 */
	public org.springframework.ws.axis1.wrapped_enc.Param2 getParam2() {
		return this.param2;
	}

	/**
	 * Sets the param2 value for this Result1.
	 * 
	 * @param param2
	 */
	public void setParam2(org.springframework.ws.axis1.wrapped_enc.Param2 param2) {
		this.param2 = param2;
	}

	/**
	 * Gets the str value for this Result1.
	 * 
	 * @return str
	 */
	public java.lang.String getStr() {
		return this.str;
	}

	/**
	 * Sets the str value for this Result1.
	 * 
	 * @param str
	 */
	public void setStr(java.lang.String str) {
		this.str = str;
	}

	private java.lang.Object __equalsCalc = null;

	public synchronized boolean equals(java.lang.Object obj) {
		if (!(obj instanceof Result1))
			return false;
		Result1 other = (Result1) obj;
		if (obj == null)
			return false;
		if (this == obj)
			return true;
		if (this.__equalsCalc != null) {
			return (this.__equalsCalc == obj);
		}
		this.__equalsCalc = obj;
		boolean _equals;
		_equals = true && ((this.list == null && other.getList() == null) || (this.list != null && java.util.Arrays.equals(this.list, other.getList())))
				&& ((this.param2 == null && other.getParam2() == null) || (this.param2 != null && this.param2.equals(other.getParam2())))
				&& ((this.str == null && other.getStr() == null) || (this.str != null && this.str.equals(other.getStr())));
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
		if (this.getList() != null) {
			for (int i = 0; i < java.lang.reflect.Array.getLength(this.getList()); i++) {
				java.lang.Object obj = java.lang.reflect.Array.get(this.getList(), i);
				if (obj != null && !obj.getClass().isArray()) {
					_hashCode += obj.hashCode();
				}
			}
		}
		if (this.getParam2() != null) {
			_hashCode += this.getParam2().hashCode();
		}
		if (this.getStr() != null) {
			_hashCode += this.getStr().hashCode();
		}
		this.__hashCodeCalc = false;
		return _hashCode;
	}

}
