/**
 * RemoteProjectRoleActors.java
 *
 * This file was auto-generated from WSDL
 * by the Apache Axis 1.4 Apr 22, 2006 (06:55:48 PDT) WSDL2Java emitter.
 */

package org.javelin.sws.ext.ws.axis1.jira.contractfirst.tm12_arraysplain;

@SuppressWarnings("all")
public class RemoteProjectRoleActors extends org.javelin.sws.ext.ws.axis1.jira.contractfirst.tm12_arraysplain.RemoteRoleActors implements java.io.Serializable {
	private org.javelin.sws.ext.ws.axis1.jira.contractfirst.tm12_arraysplain.RemoteProject project;

	public RemoteProjectRoleActors() {
	}

	public RemoteProjectRoleActors(org.javelin.sws.ext.ws.axis1.jira.contractfirst.tm12_arraysplain.RemoteProjectRole projectRole,
			org.javelin.sws.ext.ws.axis1.jira.contractfirst.tm12_arraysplain.RemoteRoleActor[] roleActors,
			org.javelin.sws.ext.ws.axis1.jira.contractfirst.tm12_arraysplain.RemoteUser[] users,
			org.javelin.sws.ext.ws.axis1.jira.contractfirst.tm12_arraysplain.RemoteProject project) {
		super(projectRole, roleActors, users);
		this.project = project;
	}

	/**
	 * Gets the project value for this RemoteProjectRoleActors.
	 * 
	 * @return project
	 */
	public org.javelin.sws.ext.ws.axis1.jira.contractfirst.tm12_arraysplain.RemoteProject getProject() {
		return project;
	}

	/**
	 * Sets the project value for this RemoteProjectRoleActors.
	 * 
	 * @param project
	 */
	public void setProject(org.javelin.sws.ext.ws.axis1.jira.contractfirst.tm12_arraysplain.RemoteProject project) {
		this.project = project;
	}

	private java.lang.Object __equalsCalc = null;

	public synchronized boolean equals(java.lang.Object obj) {
		if (!(obj instanceof RemoteProjectRoleActors))
			return false;
		RemoteProjectRoleActors other = (RemoteProjectRoleActors) obj;
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
				&& ((this.project == null && other.getProject() == null) || (this.project != null && this.project.equals(other.getProject())));
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
		if (getProject() != null) {
			_hashCode += getProject().hashCode();
		}
		__hashCodeCalc = false;
		return _hashCode;
	}

}
