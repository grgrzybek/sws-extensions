/**
 * WrappedEncResponse.java
 *
 * This file was auto-generated from WSDL
 * by the Apache Axis 1.4 Apr 22, 2006 (06:55:48 PDT) WSDL2Java emitter.
 */

package org.springframework.ws.axis1.wrapped_enc;

@SuppressWarnings("all")
public class WrappedEncResponse implements java.io.Serializable {
	private org.springframework.ws.axis1.wrapped_enc.Result1 wrappedEncReturn;

	public WrappedEncResponse() {
	}

	public WrappedEncResponse(org.springframework.ws.axis1.wrapped_enc.Result1 wrappedEncReturn) {
		this.wrappedEncReturn = wrappedEncReturn;
	}

	/**
	 * Gets the wrappedEncReturn value for this WrappedEncResponse.
	 * 
	 * @return wrappedEncReturn
	 */
	public org.springframework.ws.axis1.wrapped_enc.Result1 getWrappedEncReturn() {
		return this.wrappedEncReturn;
	}

	/**
	 * Sets the wrappedEncReturn value for this WrappedEncResponse.
	 * 
	 * @param wrappedEncReturn
	 */
	public void setWrappedEncReturn(org.springframework.ws.axis1.wrapped_enc.Result1 wrappedEncReturn) {
		this.wrappedEncReturn = wrappedEncReturn;
	}

	private java.lang.Object __equalsCalc = null;

	public synchronized boolean equals(java.lang.Object obj) {
		if (!(obj instanceof WrappedEncResponse))
			return false;
		WrappedEncResponse other = (WrappedEncResponse) obj;
		if (obj == null)
			return false;
		if (this == obj)
			return true;
		if (this.__equalsCalc != null) {
			return (this.__equalsCalc == obj);
		}
		this.__equalsCalc = obj;
		boolean _equals;
		_equals = true && ((this.wrappedEncReturn == null && other.getWrappedEncReturn() == null) || (this.wrappedEncReturn != null && this.wrappedEncReturn
				.equals(other.getWrappedEncReturn())));
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
		if (this.getWrappedEncReturn() != null) {
			_hashCode += this.getWrappedEncReturn().hashCode();
		}
		this.__hashCodeCalc = false;
		return _hashCode;
	}

}
