/**
 * RemoteCustomFieldValue.java
 *
 * This file was auto-generated from WSDL
 * by the Apache Axis 1.4 Apr 22, 2006 (06:55:48 PDT) WSDL2Java emitter.
 */

package org.javelin.sws.ext.ws.axis1.jira.contractfirst.tm12_arraysplain;

@SuppressWarnings("all")
public class RemoteCustomFieldValue implements java.io.Serializable {
	private java.lang.String customfieldId;

	private java.lang.String key;

	private java.lang.String[] values;

	public RemoteCustomFieldValue() {
	}

	public RemoteCustomFieldValue(java.lang.String customfieldId, java.lang.String key, java.lang.String[] values) {
		this.customfieldId = customfieldId;
		this.key = key;
		this.values = values;
	}

	/**
	 * Gets the customfieldId value for this RemoteCustomFieldValue.
	 * 
	 * @return customfieldId
	 */
	public java.lang.String getCustomfieldId() {
		return customfieldId;
	}

	/**
	 * Sets the customfieldId value for this RemoteCustomFieldValue.
	 * 
	 * @param customfieldId
	 */
	public void setCustomfieldId(java.lang.String customfieldId) {
		this.customfieldId = customfieldId;
	}

	/**
	 * Gets the key value for this RemoteCustomFieldValue.
	 * 
	 * @return key
	 */
	public java.lang.String getKey() {
		return key;
	}

	/**
	 * Sets the key value for this RemoteCustomFieldValue.
	 * 
	 * @param key
	 */
	public void setKey(java.lang.String key) {
		this.key = key;
	}

	/**
	 * Gets the values value for this RemoteCustomFieldValue.
	 * 
	 * @return values
	 */
	public java.lang.String[] getValues() {
		return values;
	}

	/**
	 * Sets the values value for this RemoteCustomFieldValue.
	 * 
	 * @param values
	 */
	public void setValues(java.lang.String[] values) {
		this.values = values;
	}

	private java.lang.Object __equalsCalc = null;

	public synchronized boolean equals(java.lang.Object obj) {
		if (!(obj instanceof RemoteCustomFieldValue))
			return false;
		RemoteCustomFieldValue other = (RemoteCustomFieldValue) obj;
		if (obj == null)
			return false;
		if (this == obj)
			return true;
		if (__equalsCalc != null) {
			return (__equalsCalc == obj);
		}
		__equalsCalc = obj;
		boolean _equals;
		_equals = true
				&& ((this.customfieldId == null && other.getCustomfieldId() == null) || (this.customfieldId != null && this.customfieldId.equals(other
						.getCustomfieldId()))) && ((this.key == null && other.getKey() == null) || (this.key != null && this.key.equals(other.getKey())))
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
		if (getCustomfieldId() != null) {
			_hashCode += getCustomfieldId().hashCode();
		}
		if (getKey() != null) {
			_hashCode += getKey().hashCode();
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
