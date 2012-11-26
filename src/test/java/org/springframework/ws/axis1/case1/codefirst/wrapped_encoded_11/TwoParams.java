/**
 * TwoParams.java
 *
 * This file was auto-generated from WSDL
 * by the Apache Axis 1.4 Apr 22, 2006 (06:55:48 PDT) WSDL2Java emitter.
 */

package org.springframework.ws.axis1.case1.codefirst.wrapped_encoded_11;

public class TwoParams implements java.io.Serializable {
	/**
	* 
	*/
	private static final long serialVersionUID = -4246234660775211363L;

	private java.lang.String param1;

	private java.lang.String param2;

	public TwoParams() {
	}

	public TwoParams(java.lang.String param1, java.lang.String param2) {
		this.param1 = param1;
		this.param2 = param2;
	}

	/**
	 * Gets the param1 value for this TwoParams.
	 * 
	 * @return param1
	 */
	public java.lang.String getParam1() {
		return this.param1;
	}

	/**
	 * Sets the param1 value for this TwoParams.
	 * 
	 * @param param1
	 */
	public void setParam1(java.lang.String param1) {
		this.param1 = param1;
	}

	/**
	 * Gets the param2 value for this TwoParams.
	 * 
	 * @return param2
	 */
	public java.lang.String getParam2() {
		return this.param2;
	}

	/**
	 * Sets the param2 value for this TwoParams.
	 * 
	 * @param param2
	 */
	public void setParam2(java.lang.String param2) {
		this.param2 = param2;
	}

	private java.lang.Object __equalsCalc = null;

	@Override
	public synchronized boolean equals(java.lang.Object obj) {
		if (!(obj instanceof TwoParams))
			return false;
		TwoParams other = (TwoParams) obj;
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

	@Override
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
