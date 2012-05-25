/**
 * Result1_Helper.java
 *
 * This file was auto-generated from WSDL
 * by the Apache Axis 1.4 Apr 22, 2006 (06:55:48 PDT) WSDL2Java emitter.
 */

package org.springframework.ws.axis1.wrapped_lit;

@SuppressWarnings("all")
public class Result1_Helper {
	// Type metadata
	private static org.apache.axis.description.TypeDesc typeDesc = new org.apache.axis.description.TypeDesc(Result1.class, true);

	static {
		typeDesc.setXmlType(new javax.xml.namespace.QName("http://dispatch2.axis1.ws.springframework.org/r1/", "result-1"));
		org.apache.axis.description.ElementDesc elemField = new org.apache.axis.description.ElementDesc();
		elemField.setFieldName("list");
		elemField.setXmlName(new javax.xml.namespace.QName("http://dispatch2.axis1.ws.springframework.org/r1/", "list"));
		elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "decimal"));
		elemField.setNillable(true);
		elemField.setItemQName(new javax.xml.namespace.QName("http://dispatch2.axis1.ws.springframework.org/", "item"));
		typeDesc.addFieldDesc(elemField);
		elemField = new org.apache.axis.description.ElementDesc();
		elemField.setFieldName("param2");
		elemField.setXmlName(new javax.xml.namespace.QName("http://dispatch2.axis1.ws.springframework.org/r1/", "param2"));
		elemField.setXmlType(new javax.xml.namespace.QName("http://dispatch2.axis1.ws.springframework.org/p2/", "param-2"));
		elemField.setNillable(true);
		typeDesc.addFieldDesc(elemField);
		elemField = new org.apache.axis.description.ElementDesc();
		elemField.setFieldName("str");
		elemField.setXmlName(new javax.xml.namespace.QName("http://dispatch2.axis1.ws.springframework.org/r1/", "str"));
		elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
		elemField.setNillable(true);
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
