/**
 * RemoteRoleActors.java
 *
 * This file was auto-generated from WSDL
 * by the Apache Axis 1.4 Apr 22, 2006 (06:55:48 PDT) WSDL2Java emitter.
 */

package org.javelin.sws.ext.ws.axis1.jira.contractfirst.tm12_arraysplain;

@SuppressWarnings("all")
public class RemoteRoleActors implements java.io.Serializable {
	private org.javelin.sws.ext.ws.axis1.jira.contractfirst.tm12_arraysplain.RemoteProjectRole projectRole;

	private org.javelin.sws.ext.ws.axis1.jira.contractfirst.tm12_arraysplain.RemoteRoleActor[] roleActors;

	private org.javelin.sws.ext.ws.axis1.jira.contractfirst.tm12_arraysplain.RemoteUser[] users;

	public RemoteRoleActors() {
	}

	public RemoteRoleActors(org.javelin.sws.ext.ws.axis1.jira.contractfirst.tm12_arraysplain.RemoteProjectRole projectRole,
			org.javelin.sws.ext.ws.axis1.jira.contractfirst.tm12_arraysplain.RemoteRoleActor[] roleActors,
			org.javelin.sws.ext.ws.axis1.jira.contractfirst.tm12_arraysplain.RemoteUser[] users) {
		this.projectRole = projectRole;
		this.roleActors = roleActors;
		this.users = users;
	}

	/**
	 * Gets the projectRole value for this RemoteRoleActors.
	 * 
	 * @return projectRole
	 */
	public org.javelin.sws.ext.ws.axis1.jira.contractfirst.tm12_arraysplain.RemoteProjectRole getProjectRole() {
		return projectRole;
	}

	/**
	 * Sets the projectRole value for this RemoteRoleActors.
	 * 
	 * @param projectRole
	 */
	public void setProjectRole(org.javelin.sws.ext.ws.axis1.jira.contractfirst.tm12_arraysplain.RemoteProjectRole projectRole) {
		this.projectRole = projectRole;
	}

	/**
	 * Gets the roleActors value for this RemoteRoleActors.
	 * 
	 * @return roleActors
	 */
	public org.javelin.sws.ext.ws.axis1.jira.contractfirst.tm12_arraysplain.RemoteRoleActor[] getRoleActors() {
		return roleActors;
	}

	/**
	 * Sets the roleActors value for this RemoteRoleActors.
	 * 
	 * @param roleActors
	 */
	public void setRoleActors(org.javelin.sws.ext.ws.axis1.jira.contractfirst.tm12_arraysplain.RemoteRoleActor[] roleActors) {
		this.roleActors = roleActors;
	}

	/**
	 * Gets the users value for this RemoteRoleActors.
	 * 
	 * @return users
	 */
	public org.javelin.sws.ext.ws.axis1.jira.contractfirst.tm12_arraysplain.RemoteUser[] getUsers() {
		return users;
	}

	/**
	 * Sets the users value for this RemoteRoleActors.
	 * 
	 * @param users
	 */
	public void setUsers(org.javelin.sws.ext.ws.axis1.jira.contractfirst.tm12_arraysplain.RemoteUser[] users) {
		this.users = users;
	}

	private java.lang.Object __equalsCalc = null;

	public synchronized boolean equals(java.lang.Object obj) {
		if (!(obj instanceof RemoteRoleActors))
			return false;
		RemoteRoleActors other = (RemoteRoleActors) obj;
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
				&& ((this.projectRole == null && other.getProjectRole() == null) || (this.projectRole != null && this.projectRole.equals(other.getProjectRole())))
				&& ((this.roleActors == null && other.getRoleActors() == null) || (this.roleActors != null && java.util.Arrays.equals(this.roleActors,
						other.getRoleActors())))
				&& ((this.users == null && other.getUsers() == null) || (this.users != null && java.util.Arrays.equals(this.users, other.getUsers())));
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
		if (getProjectRole() != null) {
			_hashCode += getProjectRole().hashCode();
		}
		if (getRoleActors() != null) {
			for (int i = 0; i < java.lang.reflect.Array.getLength(getRoleActors()); i++) {
				java.lang.Object obj = java.lang.reflect.Array.get(getRoleActors(), i);
				if (obj != null && !obj.getClass().isArray()) {
					_hashCode += obj.hashCode();
				}
			}
		}
		if (getUsers() != null) {
			for (int i = 0; i < java.lang.reflect.Array.getLength(getUsers()); i++) {
				java.lang.Object obj = java.lang.reflect.Array.get(getUsers(), i);
				if (obj != null && !obj.getClass().isArray()) {
					_hashCode += obj.hashCode();
				}
			}
		}
		__hashCodeCalc = false;
		return _hashCode;
	}

}
