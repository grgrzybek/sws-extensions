/**
 * Param3.java
 *
 * This file was auto-generated from WSDL
 * by the Apache Axis 1.4 Apr 22, 2006 (06:55:48 PDT) WSDL2Java emitter.
 */

package org.springframework.ws.axis1.rpc_lit;

@SuppressWarnings("all")
public class Param3 implements java.io.Serializable {
	private java.lang.String value = "value";

	public Param3() {
	}

	public Param3(java.lang.String value) {
		this.value = value;
	}

	/**
	 * Gets the value value for this Param3.
	 * 
	 * @return value
	 */
	public java.lang.String getValue() {
		return this.value;
	}

	/**
	 * Sets the value value for this Param3.
	 * 
	 * @param value
	 */
	public void setValue(java.lang.String value) {
		this.value = value;
	}

	private java.lang.Object __equalsCalc = null;

	public synchronized boolean equals(java.lang.Object obj) {
		if (!(obj instanceof Param3))
			return false;
		Param3 other = (Param3) obj;
		if (obj == null)
			return false;
		if (this == obj)
			return true;
		if (this.__equalsCalc != null) {
			return (this.__equalsCalc == obj);
		}
		this.__equalsCalc = obj;
		boolean _equals;
		_equals = true && ((this.value == null && other.getValue() == null) || (this.value != null && this.value.equals(other.getValue())));
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
		if (this.getValue() != null) {
			_hashCode += this.getValue().hashCode();
		}
		this.__hashCodeCalc = false;
		return _hashCode;
	}

}
