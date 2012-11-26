/**
 * OneParam.java
 *
 * This file was auto-generated from WSDL
 * by the Apache Axis 1.4 Apr 22, 2006 (06:55:48 PDT) WSDL2Java emitter.
 */

package org.springframework.ws.axis1.case1.contractfirst.wrapped_encoded_12;

public class OneParam implements java.io.Serializable {
	/**
	* 
	*/
	private static final long serialVersionUID = -4034040801748930890L;

	private java.lang.String param1;

	public OneParam() {
	}

	public OneParam(java.lang.String param1) {
		this.param1 = param1;
	}

	/**
	 * Gets the param1 value for this OneParam.
	 * 
	 * @return param1
	 */
	public java.lang.String getParam1() {
		return this.param1;
	}

	/**
	 * Sets the param1 value for this OneParam.
	 * 
	 * @param param1
	 */
	public void setParam1(java.lang.String param1) {
		this.param1 = param1;
	}

	private java.lang.Object __equalsCalc = null;

	@Override
	public synchronized boolean equals(java.lang.Object obj) {
		if (!(obj instanceof OneParam))
			return false;
		OneParam other = (OneParam) obj;
		if (obj == null)
			return false;
		if (this == obj)
			return true;
		if (this.__equalsCalc != null) {
			return (this.__equalsCalc == obj);
		}
		this.__equalsCalc = obj;
		boolean _equals;
		_equals = true && ((this.param1 == null && other.getParam1() == null) || (this.param1 != null && this.param1.equals(other.getParam1())));
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
		this.__hashCodeCalc = false;
		return _hashCode;
	}

	// Type metadata
	private static org.apache.axis.description.TypeDesc typeDesc = new org.apache.axis.description.TypeDesc(OneParam.class, true);

	static {
		typeDesc.setXmlType(new javax.xml.namespace.QName("http://codefirst.case1.axis1.ws.springframework.org", ">oneParam"));
		org.apache.axis.description.ElementDesc elemField = new org.apache.axis.description.ElementDesc();
		elemField.setFieldName("param1");
		elemField.setXmlName(new javax.xml.namespace.QName("http://codefirst.case1.axis1.ws.springframework.org", "param1"));
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
