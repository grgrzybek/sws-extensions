/**
 * RemoteAuthenticationException.java
 *
 * This file was auto-generated from WSDL
 * by the Apache Axis 1.4 Apr 22, 2006 (06:55:48 PDT) WSDL2Java emitter.
 */

package org.javelin.sws.ext.ws.axis1.jira.contractfirst.tm12_arraysplain;

@SuppressWarnings("all")
public class RemoteAuthenticationException extends org.javelin.sws.ext.ws.axis1.jira.contractfirst.tm12_arraysplain.RemoteException implements
		java.io.Serializable {
	public RemoteAuthenticationException() {
	}

	private java.lang.Object __equalsCalc = null;

	public synchronized boolean equals(java.lang.Object obj) {
		if (!(obj instanceof RemoteAuthenticationException))
			return false;
		RemoteAuthenticationException other = (RemoteAuthenticationException) obj;
		if (obj == null)
			return false;
		if (this == obj)
			return true;
		if (__equalsCalc != null) {
			return (__equalsCalc == obj);
		}
		__equalsCalc = obj;
		boolean _equals;
		_equals = super.equals(obj);
		__equalsCalc = null;
		return _equals;
	}

	private boolean __hashCodeCalc = false;

	public synchronized int hashCode() {
		if (__hashCodeCalc) {
			return 0;
		}
		__hashCodeCalc = true;
		int _hashCode = super.hashCode();
		__hashCodeCalc = false;
		return _hashCode;
	}

	/**
	 * Writes the exception data to the faultDetails
	 */
	public void writeDetails(javax.xml.namespace.QName qname, org.apache.axis.encoding.SerializationContext context) throws java.io.IOException {
		context.serialize(qname, null, this);
	}
}
