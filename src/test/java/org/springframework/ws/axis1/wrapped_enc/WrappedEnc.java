/**
 * WrappedEnc.java
 *
 * This file was auto-generated from WSDL
 * by the Apache Axis 1.4 Apr 22, 2006 (06:55:48 PDT) WSDL2Java emitter.
 */

package org.springframework.ws.axis1.wrapped_enc;

@SuppressWarnings("all")
public class WrappedEnc implements java.io.Serializable {
	private org.springframework.ws.axis1.wrapped_enc.Param1 param1;

	private org.springframework.ws.axis1.wrapped_enc.Param2 param2;

	public WrappedEnc() {
	}

	public WrappedEnc(org.springframework.ws.axis1.wrapped_enc.Param1 param1, org.springframework.ws.axis1.wrapped_enc.Param2 param2) {
		this.param1 = param1;
		this.param2 = param2;
	}

	/**
	 * Gets the param1 value for this WrappedEnc.
	 * 
	 * @return param1
	 */
	public org.springframework.ws.axis1.wrapped_enc.Param1 getParam1() {
		return this.param1;
	}

	/**
	 * Sets the param1 value for this WrappedEnc.
	 * 
	 * @param param1
	 */
	public void setParam1(org.springframework.ws.axis1.wrapped_enc.Param1 param1) {
		this.param1 = param1;
	}

	/**
	 * Gets the param2 value for this WrappedEnc.
	 * 
	 * @return param2
	 */
	public org.springframework.ws.axis1.wrapped_enc.Param2 getParam2() {
		return this.param2;
	}

	/**
	 * Sets the param2 value for this WrappedEnc.
	 * 
	 * @param param2
	 */
	public void setParam2(org.springframework.ws.axis1.wrapped_enc.Param2 param2) {
		this.param2 = param2;
	}

	private java.lang.Object __equalsCalc = null;

	public synchronized boolean equals(java.lang.Object obj) {
		if (!(obj instanceof WrappedEnc))
			return false;
		WrappedEnc other = (WrappedEnc) obj;
		if (obj == null)
			return false;
		if (this == obj)
			return true;
		if (this.__equalsCalc != null) {
			return (this.__equalsCalc == obj);
		}
		this.__equalsCalc = obj;
		boolean _equals;
		_equals = true && ((this.param1 == null && other.getParam1() == null) || (this.param1 != null && this.param1.equals(other.getParam1())))
				&& ((this.param2 == null && other.getParam2() == null) || (this.param2 != null && this.param2.equals(other.getParam2())));
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
		if (this.getParam1() != null) {
			_hashCode += this.getParam1().hashCode();
		}
		if (this.getParam2() != null) {
			_hashCode += this.getParam2().hashCode();
		}
		this.__hashCodeCalc = false;
		return _hashCode;
	}

}
