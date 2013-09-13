/**
 * RemoteFieldValue.java
 *
 * This file was auto-generated from WSDL
 * by the Apache Axis 1.4 Apr 22, 2006 (06:55:48 PDT) WSDL2Java emitter.
 */

package org.javelin.sws.ext.ws.axis1.jira.contractfirst.tm12_arraysplain;

@SuppressWarnings("all")
public class RemoteFieldValue implements java.io.Serializable {
	private java.lang.String id;

	private java.lang.String[] values;

	public RemoteFieldValue() {
	}

	public RemoteFieldValue(java.lang.String id, java.lang.String[] values) {
		this.id = id;
		this.values = values;
	}

	/**
	 * Gets the id value for this RemoteFieldValue.
	 * 
	 * @return id
	 */
	public java.lang.String getId() {
		return id;
	}

	/**
	 * Sets the id value for this RemoteFieldValue.
	 * 
	 * @param id
	 */
	public void setId(java.lang.String id) {
		this.id = id;
	}

	/**
	 * Gets the values value for this RemoteFieldValue.
	 * 
	 * @return values
	 */
	public java.lang.String[] getValues() {
		return values;
	}

	/**
	 * Sets the values value for this RemoteFieldValue.
	 * 
	 * @param values
	 */
	public void setValues(java.lang.String[] values) {
		this.values = values;
	}

	private java.lang.Object __equalsCalc = null;

	public synchronized boolean equals(java.lang.Object obj) {
		if (!(obj instanceof RemoteFieldValue))
			return false;
		RemoteFieldValue other = (RemoteFieldValue) obj;
		if (obj == null)
			return false;
		if (this == obj)
			return true;
		if (__equalsCalc != null) {
			return (__equalsCalc == obj);
		}
		__equalsCalc = obj;
		boolean _equals;
		_equals = true && ((this.id == null && other.getId() == null) || (this.id != null && this.id.equals(other.getId())))
				&& ((this.values == null && other.getValues() == null) || (this.values != null && java.util.Arrays.equals(this.values, other.getValues())));
		__equalsCalc = null;
		return _equals;
	}

	private boolean __hashCodeCalc = false;

	public synchronized int hashCode() {
		if (__hashCodeCalc) {
			return 0;
		}
		__hashCodeCalc = true;
		int _hashCode = 1;
		if (getId() != null) {
			_hashCode += getId().hashCode();
		}
		if (getValues() != null) {
			for (int i = 0; i < java.lang.reflect.Array.getLength(getValues()); i++) {
				java.lang.Object obj = java.lang.reflect.Array.get(getValues(), i);
				if (obj != null && !obj.getClass().isArray()) {
					_hashCode += obj.hashCode();
				}
			}
		}
		__hashCodeCalc = false;
		return _hashCode;
	}

}
