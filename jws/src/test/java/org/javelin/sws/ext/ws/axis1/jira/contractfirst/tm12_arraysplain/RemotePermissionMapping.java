/**
 * RemotePermissionMapping.java
 *
 * This file was auto-generated from WSDL
 * by the Apache Axis 1.4 Apr 22, 2006 (06:55:48 PDT) WSDL2Java emitter.
 */

package org.javelin.sws.ext.ws.axis1.jira.contractfirst.tm12_arraysplain;

@SuppressWarnings("all")
public class RemotePermissionMapping implements java.io.Serializable {
	private org.javelin.sws.ext.ws.axis1.jira.contractfirst.tm12_arraysplain.RemotePermission permission;

	private org.javelin.sws.ext.ws.axis1.jira.contractfirst.tm12_arraysplain.RemoteEntity[] remoteEntities;

	public RemotePermissionMapping() {
	}

	public RemotePermissionMapping(org.javelin.sws.ext.ws.axis1.jira.contractfirst.tm12_arraysplain.RemotePermission permission,
			org.javelin.sws.ext.ws.axis1.jira.contractfirst.tm12_arraysplain.RemoteEntity[] remoteEntities) {
		this.permission = permission;
		this.remoteEntities = remoteEntities;
	}

	/**
	 * Gets the permission value for this RemotePermissionMapping.
	 * 
	 * @return permission
	 */
	public org.javelin.sws.ext.ws.axis1.jira.contractfirst.tm12_arraysplain.RemotePermission getPermission() {
		return permission;
	}

	/**
	 * Sets the permission value for this RemotePermissionMapping.
	 * 
	 * @param permission
	 */
	public void setPermission(org.javelin.sws.ext.ws.axis1.jira.contractfirst.tm12_arraysplain.RemotePermission permission) {
		this.permission = permission;
	}

	/**
	 * Gets the remoteEntities value for this RemotePermissionMapping.
	 * 
	 * @return remoteEntities
	 */
	public org.javelin.sws.ext.ws.axis1.jira.contractfirst.tm12_arraysplain.RemoteEntity[] getRemoteEntities() {
		return remoteEntities;
	}

	/**
	 * Sets the remoteEntities value for this RemotePermissionMapping.
	 * 
	 * @param remoteEntities
	 */
	public void setRemoteEntities(org.javelin.sws.ext.ws.axis1.jira.contractfirst.tm12_arraysplain.RemoteEntity[] remoteEntities) {
		this.remoteEntities = remoteEntities;
	}

	private java.lang.Object __equalsCalc = null;

	public synchronized boolean equals(java.lang.Object obj) {
		if (!(obj instanceof RemotePermissionMapping))
			return false;
		RemotePermissionMapping other = (RemotePermissionMapping) obj;
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
				&& ((this.permission == null && other.getPermission() == null) || (this.permission != null && this.permission.equals(other.getPermission())))
				&& ((this.remoteEntities == null && other.getRemoteEntities() == null) || (this.remoteEntities != null && java.util.Arrays.equals(
						this.remoteEntities, other.getRemoteEntities())));
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
		if (getPermission() != null) {
			_hashCode += getPermission().hashCode();
		}
		if (getRemoteEntities() != null) {
			for (int i = 0; i < java.lang.reflect.Array.getLength(getRemoteEntities()); i++) {
				java.lang.Object obj = java.lang.reflect.Array.get(getRemoteEntities(), i);
				if (obj != null && !obj.getClass().isArray()) {
					_hashCode += obj.hashCode();
				}
			}
		}
		__hashCodeCalc = false;
		return _hashCode;
	}

}
