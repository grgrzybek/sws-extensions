/**
 * RemoteRoleActor.java
 *
 * This file was auto-generated from WSDL
 * by the Apache Axis 1.4 Apr 22, 2006 (06:55:48 PDT) WSDL2Java emitter.
 */

package org.javelin.sws.ext.ws.axis1.jira.contractfirst.tm12_arraysplain;

@SuppressWarnings("all")
public class RemoteRoleActor implements java.io.Serializable {
	private java.lang.String descriptor;

	private java.lang.String parameter;

	private org.javelin.sws.ext.ws.axis1.jira.contractfirst.tm12_arraysplain.RemoteProjectRole projectRole;

	private java.lang.String type;

	private org.javelin.sws.ext.ws.axis1.jira.contractfirst.tm12_arraysplain.RemoteUser[] users;

	public RemoteRoleActor() {
	}

	public RemoteRoleActor(java.lang.String descriptor, java.lang.String parameter,
			org.javelin.sws.ext.ws.axis1.jira.contractfirst.tm12_arraysplain.RemoteProjectRole projectRole, java.lang.String type,
			org.javelin.sws.ext.ws.axis1.jira.contractfirst.tm12_arraysplain.RemoteUser[] users) {
		this.descriptor = descriptor;
		this.parameter = parameter;
		this.projectRole = projectRole;
		this.type = type;
		this.users = users;
	}

	/**
	 * Gets the descriptor value for this RemoteRoleActor.
	 * 
	 * @return descriptor
	 */
	public java.lang.String getDescriptor() {
		return descriptor;
	}

	/**
	 * Sets the descriptor value for this RemoteRoleActor.
	 * 
	 * @param descriptor
	 */
	public void setDescriptor(java.lang.String descriptor) {
		this.descriptor = descriptor;
	}

	/**
	 * Gets the parameter value for this RemoteRoleActor.
	 * 
	 * @return parameter
	 */
	public java.lang.String getParameter() {
		return parameter;
	}

	/**
	 * Sets the parameter value for this RemoteRoleActor.
	 * 
	 * @param parameter
	 */
	public void setParameter(java.lang.String parameter) {
		this.parameter = parameter;
	}

	/**
	 * Gets the projectRole value for this RemoteRoleActor.
	 * 
	 * @return projectRole
	 */
	public org.javelin.sws.ext.ws.axis1.jira.contractfirst.tm12_arraysplain.RemoteProjectRole getProjectRole() {
		return projectRole;
	}

	/**
	 * Sets the projectRole value for this RemoteRoleActor.
	 * 
	 * @param projectRole
	 */
	public void setProjectRole(org.javelin.sws.ext.ws.axis1.jira.contractfirst.tm12_arraysplain.RemoteProjectRole projectRole) {
		this.projectRole = projectRole;
	}

	/**
	 * Gets the type value for this RemoteRoleActor.
	 * 
	 * @return type
	 */
	public java.lang.String getType() {
		return type;
	}

	/**
	 * Sets the type value for this RemoteRoleActor.
	 * 
	 * @param type
	 */
	public void setType(java.lang.String type) {
		this.type = type;
	}

	/**
	 * Gets the users value for this RemoteRoleActor.
	 * 
	 * @return users
	 */
	public org.javelin.sws.ext.ws.axis1.jira.contractfirst.tm12_arraysplain.RemoteUser[] getUsers() {
		return users;
	}

	/**
	 * Sets the users value for this RemoteRoleActor.
	 * 
	 * @param users
	 */
	public void setUsers(org.javelin.sws.ext.ws.axis1.jira.contractfirst.tm12_arraysplain.RemoteUser[] users) {
		this.users = users;
	}

	private java.lang.Object __equalsCalc = null;

	public synchronized boolean equals(java.lang.Object obj) {
		if (!(obj instanceof RemoteRoleActor))
			return false;
		RemoteRoleActor other = (RemoteRoleActor) obj;
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
				&& ((this.descriptor == null && other.getDescriptor() == null) || (this.descriptor != null && this.descriptor.equals(other.getDescriptor())))
				&& ((this.parameter == null && other.getParameter() == null) || (this.parameter != null && this.parameter.equals(other.getParameter())))
				&& ((this.projectRole == null && other.getProjectRole() == null) || (this.projectRole != null && this.projectRole.equals(other.getProjectRole())))
				&& ((this.type == null && other.getType() == null) || (this.type != null && this.type.equals(other.getType())))
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
		if (getDescriptor() != null) {
			_hashCode += getDescriptor().hashCode();
		}
		if (getParameter() != null) {
			_hashCode += getParameter().hashCode();
		}
		if (getProjectRole() != null) {
			_hashCode += getProjectRole().hashCode();
		}
		if (getType() != null) {
			_hashCode += getType().hashCode();
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
