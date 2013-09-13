/**
 * RemoteIssueType.java
 *
 * This file was auto-generated from WSDL
 * by the Apache Axis 1.4 Apr 22, 2006 (06:55:48 PDT) WSDL2Java emitter.
 */

package org.javelin.sws.ext.ws.axis1.jira.contractfirst.tm12_arraysplain;

@SuppressWarnings("all")
public class RemoteIssueType extends org.javelin.sws.ext.ws.axis1.jira.contractfirst.tm12_arraysplain.AbstractRemoteConstant implements java.io.Serializable {
	private boolean subTask;

	public RemoteIssueType() {
	}

	public RemoteIssueType(java.lang.String id, java.lang.String name, java.lang.String description, java.lang.String icon, boolean subTask) {
		super(id, name, description, icon);
		this.subTask = subTask;
	}

	/**
	 * Gets the subTask value for this RemoteIssueType.
	 * 
	 * @return subTask
	 */
	public boolean isSubTask() {
		return subTask;
	}

	/**
	 * Sets the subTask value for this RemoteIssueType.
	 * 
	 * @param subTask
	 */
	public void setSubTask(boolean subTask) {
		this.subTask = subTask;
	}

	private java.lang.Object __equalsCalc = null;

	public synchronized boolean equals(java.lang.Object obj) {
		if (!(obj instanceof RemoteIssueType))
			return false;
		RemoteIssueType other = (RemoteIssueType) obj;
		if (obj == null)
			return false;
		if (this == obj)
			return true;
		if (__equalsCalc != null) {
			return (__equalsCalc == obj);
		}
		__equalsCalc = obj;
		boolean _equals;
		_equals = super.equals(obj) && this.subTask == other.isSubTask();
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
		_hashCode += (isSubTask() ? Boolean.TRUE : Boolean.FALSE).hashCode();
		__hashCodeCalc = false;
		return _hashCode;
	}

}
