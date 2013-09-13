/**
 * RemotePermissionScheme.java
 *
 * This file was auto-generated from WSDL
 * by the Apache Axis 1.4 Apr 22, 2006 (06:55:48 PDT) WSDL2Java emitter.
 */

package org.javelin.sws.ext.ws.axis1.jira.contractfirst.tm12_arraysplain;

@SuppressWarnings("all")
public class RemotePermissionScheme extends org.javelin.sws.ext.ws.axis1.jira.contractfirst.tm12_arraysplain.RemoteScheme implements java.io.Serializable {
	private org.javelin.sws.ext.ws.axis1.jira.contractfirst.tm12_arraysplain.RemotePermissionMapping[] permissionMappings;

	public RemotePermissionScheme() {
	}

	public RemotePermissionScheme(java.lang.String description, java.lang.Long id, java.lang.String name, java.lang.String type,
			org.javelin.sws.ext.ws.axis1.jira.contractfirst.tm12_arraysplain.RemotePermissionMapping[] permissionMappings) {
		super(description, id, name, type);
		this.permissionMappings = permissionMappings;
	}

	/**
	 * Gets the permissionMappings value for this RemotePermissionScheme.
	 * 
	 * @return permissionMappings
	 */
	public org.javelin.sws.ext.ws.axis1.jira.contractfirst.tm12_arraysplain.RemotePermissionMapping[] getPermissionMappings() {
		return permissionMappings;
	}

	/**
	 * Sets the permissionMappings value for this RemotePermissionScheme.
	 * 
	 * @param permissionMappings
	 */
	public void setPermissionMappings(org.javelin.sws.ext.ws.axis1.jira.contractfirst.tm12_arraysplain.RemotePermissionMapping[] permissionMappings) {
		this.permissionMappings = permissionMappings;
	}

	private java.lang.Object __equalsCalc = null;

	public synchronized boolean equals(java.lang.Object obj) {
		if (!(obj instanceof RemotePermissionScheme))
			return false;
		RemotePermissionScheme other = (RemotePermissionScheme) obj;
		if (obj == null)
			return false;
		if (this == obj)
			return true;
		if (__equalsCalc != null) {
			return (__equalsCalc == obj);
		}
		__equalsCalc = obj;
		boolean _equals;
		_equals = super.equals(obj)
				&& ((this.permissionMappings == null && other.getPermissionMappings() == null) || (this.permissionMappings != null && java.util.Arrays.equals(
						this.permissionMappings, other.getPermissionMappings())));
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
		if (getPermissionMappings() != null) {
			for (int i = 0; i < java.lang.reflect.Array.getLength(getPermissionMappings()); i++) {
				java.lang.Object obj = java.lang.reflect.Array.get(getPermissionMappings(), i);
				if (obj != null && !obj.getClass().isArray()) {
					_hashCode += obj.hashCode();
				}
			}
		}
		__hashCodeCalc = false;
		return _hashCode;
	}

}
