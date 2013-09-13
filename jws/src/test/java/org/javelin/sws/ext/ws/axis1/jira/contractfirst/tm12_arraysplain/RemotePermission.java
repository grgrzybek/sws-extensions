/**
 * RemotePermission.java
 *
 * This file was auto-generated from WSDL
 * by the Apache Axis 1.4 Apr 22, 2006 (06:55:48 PDT) WSDL2Java emitter.
 */

package org.javelin.sws.ext.ws.axis1.jira.contractfirst.tm12_arraysplain;

@SuppressWarnings("all")
public class RemotePermission implements java.io.Serializable {
	private java.lang.String name;

	private java.lang.Long permission;

	public RemotePermission() {
	}

	public RemotePermission(java.lang.String name, java.lang.Long permission) {
		this.name = name;
		this.permission = permission;
	}

	/**
	 * Gets the name value for this RemotePermission.
	 * 
	 * @return name
	 */
	public java.lang.String getName() {
		return name;
	}

	/**
	 * Sets the name value for this RemotePermission.
	 * 
	 * @param name
	 */
	public void setName(java.lang.String name) {
		this.name = name;
	}

	/**
	 * Gets the permission value for this RemotePermission.
	 * 
	 * @return permission
	 */
	public java.lang.Long getPermission() {
		return permission;
	}

	/**
	 * Sets the permission value for this RemotePermission.
	 * 
	 * @param permission
	 */
	public void setPermission(java.lang.Long permission) {
		this.permission = permission;
	}

	private java.lang.Object __equalsCalc = null;

	public synchronized boolean equals(java.lang.Object obj) {
		if (!(obj instanceof RemotePermission))
			return false;
		RemotePermission other = (RemotePermission) obj;
		if (obj == null)
			return false;
		if (this == obj)
			return true;
		if (__equalsCalc != null) {
			return (__equalsCalc == obj);
		}
		__equalsCalc = obj;
		boolean _equals;
		_equals = true && ((this.name == null && other.getName() == null) || (this.name != null && this.name.equals(other.getName())))
				&& ((this.permission == null && other.getPermission() == null) || (this.permission != null && this.permission.equals(other.getPermission())));
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
		if (getName() != null) {
			_hashCode += getName().hashCode();
		}
		if (getPermission() != null) {
			_hashCode += getPermission().hashCode();
		}
		__hashCodeCalc = false;
		return _hashCode;
	}

}
