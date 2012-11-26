/**
 * OneParamResponse.java
 *
 * This file was auto-generated from WSDL
 * by the Apache Axis 1.4 Apr 22, 2006 (06:55:48 PDT) WSDL2Java emitter.
 */

package org.springframework.ws.axis1.case1.codefirst.wrapped_encoded_11;

public class OneParamResponse implements java.io.Serializable {
	/**
	* 
	*/
	private static final long serialVersionUID = 6510775239601195781L;

	private java.lang.String oneParamReturn;

	public OneParamResponse() {
	}

	public OneParamResponse(java.lang.String oneParamReturn) {
		this.oneParamReturn = oneParamReturn;
	}

	/**
	 * Gets the oneParamReturn value for this OneParamResponse.
	 * 
	 * @return oneParamReturn
	 */
	public java.lang.String getOneParamReturn() {
		return this.oneParamReturn;
	}

	/**
	 * Sets the oneParamReturn value for this OneParamResponse.
	 * 
	 * @param oneParamReturn
	 */
	public void setOneParamReturn(java.lang.String oneParamReturn) {
		this.oneParamReturn = oneParamReturn;
	}

	private java.lang.Object __equalsCalc = null;

	@Override
	public synchronized boolean equals(java.lang.Object obj) {
		if (!(obj instanceof OneParamResponse))
			return false;
		OneParamResponse other = (OneParamResponse) obj;
		if (obj == null)
			return false;
		if (this == obj)
			return true;
		if (this.__equalsCalc != null) {
			return (this.__equalsCalc == obj);
		}
		this.__equalsCalc = obj;
		boolean _equals;
		_equals = true && ((this.oneParamReturn == null && other.getOneParamReturn() == null) || (this.oneParamReturn != null && this.oneParamReturn.equals(other
				.getOneParamReturn())));
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
		if (this.getOneParamReturn() != null) {
			_hashCode += this.getOneParamReturn().hashCode();
		}
		this.__hashCodeCalc = false;
		return _hashCode;
	}

}
