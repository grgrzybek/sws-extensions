/**
 * TwoParams.java
 *
 * This file was auto-generated from WSDL
 * by the Apache Axis 1.4 Apr 22, 2006 (06:55:48 PDT) WSDL2Java emitter.
 */

package org.springframework.ws.axis1.case1.contractfirst.wrapped_encoded_11;

public class TwoParams implements java.io.Serializable {
	/**
	* 
	*/
	private static final long serialVersionUID = -8895200291690683788L;

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

	// Type metadata
	private static org.apache.axis.description.TypeDesc typeDesc = new org.apache.axis.description.TypeDesc(TwoParams.class, true);

	static {
		typeDesc.setXmlType(new javax.xml.namespace.QName("http://codefirst.case1.axis1.ws.springframework.org", ">twoParams"));
		org.apache.axis.description.ElementDesc elemField = new org.apache.axis.description.ElementDesc();
		elemField.setFieldName("param1");
		elemField.setXmlName(new javax.xml.namespace.QName("http://codefirst.case1.axis1.ws.springframework.org", "param1"));
		elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
		elemField.setNillable(false);
		typeDesc.addFieldDesc(elemField);
		elemField = new org.apache.axis.description.ElementDesc();
		elemField.setFieldName("param2");
		elemField.setXmlName(new javax.xml.namespace.QName("http://codefirst.case1.axis1.ws.springframework.org", "param2"));
		elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
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
