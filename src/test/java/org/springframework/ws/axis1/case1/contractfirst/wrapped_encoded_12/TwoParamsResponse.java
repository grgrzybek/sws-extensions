/**
 * TwoParamsResponse.java
 *
 * This file was auto-generated from WSDL
 * by the Apache Axis 1.4 Apr 22, 2006 (06:55:48 PDT) WSDL2Java emitter.
 */

package org.springframework.ws.axis1.case1.contractfirst.wrapped_encoded_12;

public class TwoParamsResponse implements java.io.Serializable {
	/**
	* 
	*/
	private static final long serialVersionUID = -140926094772102987L;

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

	// Type metadata
	private static org.apache.axis.description.TypeDesc typeDesc = new org.apache.axis.description.TypeDesc(TwoParamsResponse.class, true);

	static {
		typeDesc.setXmlType(new javax.xml.namespace.QName("http://codefirst.case1.axis1.ws.springframework.org", ">twoParamsResponse"));
		org.apache.axis.description.ElementDesc elemField = new org.apache.axis.description.ElementDesc();
		elemField.setFieldName("twoParamsReturn");
		elemField.setXmlName(new javax.xml.namespace.QName("http://codefirst.case1.axis1.ws.springframework.org", "twoParamsReturn"));
		elemField.setXmlType(new javax.xml.namespace.QName("http://schemas.xmlsoap.org/soap/encoding/", "string"));
		elemField.setNillable(false);
		typeDesc.addFieldDesc(elemField);
	}

	/**
	 * Return type metadata object
	 */
	public static org.apache.axis.description.TypeDesc getTypeDesc() {
		return typeDesc;
	}

	/**
	 * Get Custom Serializer
	 */
	public static org.apache.axis.encoding.Serializer getSerializer(java.lang.String mechType, java.lang.Class _javaType, javax.xml.namespace.QName _xmlType) {
		return new org.apache.axis.encoding.ser.BeanSerializer(_javaType, _xmlType, typeDesc);
	}

	/**
	 * Get Custom Deserializer
	 */
	public static org.apache.axis.encoding.Deserializer getDeserializer(java.lang.String mechType, java.lang.Class _javaType, javax.xml.namespace.QName _xmlType) {
		return new org.apache.axis.encoding.ser.BeanDeserializer(_javaType, _xmlType, typeDesc);
	}

}
