/**
 * TwoParamsResponse.java
 *
 * This file was auto-generated from WSDL
 * by the Apache Axis 1.4 Apr 22, 2006 (06:55:48 PDT) WSDL2Java emitter.
 */

package org.springframework.ws.axis1.case1.codefirst.wrapped_encoded_11;

public class TwoParamsResponse implements java.io.Serializable {
	/**
	* 
	*/
	private static final long serialVersionUID = 192213589675084792L;

	private java.lang.String twoParamsReturn;

	public TwoParamsResponse() {
	}

	public TwoParamsResponse(java.lang.String twoParamsReturn) {
		this.twoParamsReturn = twoParamsReturn;
	}

	/**
	 * Gets the twoParamsReturn value for this TwoParamsResponse.
	 * 
	 * @return twoParamsReturn
	 */
	public java.lang.String getTwoParamsReturn() {
		return this.twoParamsReturn;
	}

	/**
	 * Sets the twoParamsReturn value for this TwoParamsResponse.
	 * 
	 * @param twoParamsReturn
	 */
	public void setTwoParamsReturn(java.lang.String twoParamsReturn) {
		this.twoParamsReturn = twoParamsReturn;
	}

	private java.lang.Object __equalsCalc = null;

	@Override
	public synchronized boolean equals(java.lang.Object obj) {
		if (!(obj instanceof TwoParamsResponse))
			return false;
		TwoParamsResponse other = (TwoParamsResponse) obj;
		if (obj == null)
			return false;
		if (this == obj)
			return true;
		if (this.__equalsCalc != null) {
			return (this.__equalsCalc == obj);
		}
		this.__equalsCalc = obj;
		boolean _equals;
		_equals = true && ((this.twoParamsReturn == null && other.getTwoParamsReturn() == null) || (this.twoParamsReturn != null && this.twoParamsReturn
				.equals(other.getTwoParamsReturn())));
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
		if (this.getTwoParamsReturn() != null) {
			_hashCode += this.getTwoParamsReturn().hashCode();
		}
		this.__hashCodeCalc = false;
		return _hashCode;
	}

}
