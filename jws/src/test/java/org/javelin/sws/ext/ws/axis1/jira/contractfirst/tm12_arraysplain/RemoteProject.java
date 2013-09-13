/**
 * RemoteProject.java
 *
 * This file was auto-generated from WSDL
 * by the Apache Axis 1.4 Apr 22, 2006 (06:55:48 PDT) WSDL2Java emitter.
 */

package org.javelin.sws.ext.ws.axis1.jira.contractfirst.tm12_arraysplain;

@SuppressWarnings("all")
public class RemoteProject extends org.javelin.sws.ext.ws.axis1.jira.contractfirst.tm12_arraysplain.AbstractNamedRemoteEntity implements java.io.Serializable {
	private java.lang.String description;

	private org.javelin.sws.ext.ws.axis1.jira.contractfirst.tm12_arraysplain.RemoteScheme issueSecurityScheme;

	private java.lang.String key;

	private java.lang.String lead;

	private org.javelin.sws.ext.ws.axis1.jira.contractfirst.tm12_arraysplain.RemoteScheme notificationScheme;

	private org.javelin.sws.ext.ws.axis1.jira.contractfirst.tm12_arraysplain.RemotePermissionScheme permissionScheme;

	private java.lang.String projectUrl;

	private java.lang.String url;

	public RemoteProject() {
	}

	public RemoteProject(java.lang.String id, java.lang.String name, java.lang.String description,
			org.javelin.sws.ext.ws.axis1.jira.contractfirst.tm12_arraysplain.RemoteScheme issueSecurityScheme, java.lang.String key, java.lang.String lead,
			org.javelin.sws.ext.ws.axis1.jira.contractfirst.tm12_arraysplain.RemoteScheme notificationScheme,
			org.javelin.sws.ext.ws.axis1.jira.contractfirst.tm12_arraysplain.RemotePermissionScheme permissionScheme, java.lang.String projectUrl,
			java.lang.String url) {
		super(id, name);
		this.description = description;
		this.issueSecurityScheme = issueSecurityScheme;
		this.key = key;
		this.lead = lead;
		this.notificationScheme = notificationScheme;
		this.permissionScheme = permissionScheme;
		this.projectUrl = projectUrl;
		this.url = url;
	}

	/**
	 * Gets the description value for this RemoteProject.
	 * 
	 * @return description
	 */
	public java.lang.String getDescription() {
		return description;
	}

	/**
	 * Sets the description value for this RemoteProject.
	 * 
	 * @param description
	 */
	public void setDescription(java.lang.String description) {
		this.description = description;
	}

	/**
	 * Gets the issueSecurityScheme value for this RemoteProject.
	 * 
	 * @return issueSecurityScheme
	 */
	public org.javelin.sws.ext.ws.axis1.jira.contractfirst.tm12_arraysplain.RemoteScheme getIssueSecurityScheme() {
		return issueSecurityScheme;
	}

	/**
	 * Sets the issueSecurityScheme value for this RemoteProject.
	 * 
	 * @param issueSecurityScheme
	 */
	public void setIssueSecurityScheme(org.javelin.sws.ext.ws.axis1.jira.contractfirst.tm12_arraysplain.RemoteScheme issueSecurityScheme) {
		this.issueSecurityScheme = issueSecurityScheme;
	}

	/**
	 * Gets the key value for this RemoteProject.
	 * 
	 * @return key
	 */
	public java.lang.String getKey() {
		return key;
	}

	/**
	 * Sets the key value for this RemoteProject.
	 * 
	 * @param key
	 */
	public void setKey(java.lang.String key) {
		this.key = key;
	}

	/**
	 * Gets the lead value for this RemoteProject.
	 * 
	 * @return lead
	 */
	public java.lang.String getLead() {
		return lead;
	}

	/**
	 * Sets the lead value for this RemoteProject.
	 * 
	 * @param lead
	 */
	public void setLead(java.lang.String lead) {
		this.lead = lead;
	}

	/**
	 * Gets the notificationScheme value for this RemoteProject.
	 * 
	 * @return notificationScheme
	 */
	public org.javelin.sws.ext.ws.axis1.jira.contractfirst.tm12_arraysplain.RemoteScheme getNotificationScheme() {
		return notificationScheme;
	}

	/**
	 * Sets the notificationScheme value for this RemoteProject.
	 * 
	 * @param notificationScheme
	 */
	public void setNotificationScheme(org.javelin.sws.ext.ws.axis1.jira.contractfirst.tm12_arraysplain.RemoteScheme notificationScheme) {
		this.notificationScheme = notificationScheme;
	}

	/**
	 * Gets the permissionScheme value for this RemoteProject.
	 * 
	 * @return permissionScheme
	 */
	public org.javelin.sws.ext.ws.axis1.jira.contractfirst.tm12_arraysplain.RemotePermissionScheme getPermissionScheme() {
		return permissionScheme;
	}

	/**
	 * Sets the permissionScheme value for this RemoteProject.
	 * 
	 * @param permissionScheme
	 */
	public void setPermissionScheme(org.javelin.sws.ext.ws.axis1.jira.contractfirst.tm12_arraysplain.RemotePermissionScheme permissionScheme) {
		this.permissionScheme = permissionScheme;
	}

	/**
	 * Gets the projectUrl value for this RemoteProject.
	 * 
	 * @return projectUrl
	 */
	public java.lang.String getProjectUrl() {
		return projectUrl;
	}

	/**
	 * Sets the projectUrl value for this RemoteProject.
	 * 
	 * @param projectUrl
	 */
	public void setProjectUrl(java.lang.String projectUrl) {
		this.projectUrl = projectUrl;
	}

	/**
	 * Gets the url value for this RemoteProject.
	 * 
	 * @return url
	 */
	public java.lang.String getUrl() {
		return url;
	}

	/**
	 * Sets the url value for this RemoteProject.
	 * 
	 * @param url
	 */
	public void setUrl(java.lang.String url) {
		this.url = url;
	}

	private java.lang.Object __equalsCalc = null;

	public synchronized boolean equals(java.lang.Object obj) {
		if (!(obj instanceof RemoteProject))
			return false;
		RemoteProject other = (RemoteProject) obj;
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
				&& ((this.description == null && other.getDescription() == null) || (this.description != null && this.description.equals(other.getDescription())))
				&& ((this.issueSecurityScheme == null && other.getIssueSecurityScheme() == null) || (this.issueSecurityScheme != null && this.issueSecurityScheme
						.equals(other.getIssueSecurityScheme())))
				&& ((this.key == null && other.getKey() == null) || (this.key != null && this.key.equals(other.getKey())))
				&& ((this.lead == null && other.getLead() == null) || (this.lead != null && this.lead.equals(other.getLead())))
				&& ((this.notificationScheme == null && other.getNotificationScheme() == null) || (this.notificationScheme != null && this.notificationScheme
						.equals(other.getNotificationScheme())))
				&& ((this.permissionScheme == null && other.getPermissionScheme() == null) || (this.permissionScheme != null && this.permissionScheme.equals(other
						.getPermissionScheme())))
				&& ((this.projectUrl == null && other.getProjectUrl() == null) || (this.projectUrl != null && this.projectUrl.equals(other.getProjectUrl())))
				&& ((this.url == null && other.getUrl() == null) || (this.url != null && this.url.equals(other.getUrl())));
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
		if (getDescription() != null) {
			_hashCode += getDescription().hashCode();
		}
		if (getIssueSecurityScheme() != null) {
			_hashCode += getIssueSecurityScheme().hashCode();
		}
		if (getKey() != null) {
			_hashCode += getKey().hashCode();
		}
		if (getLead() != null) {
			_hashCode += getLead().hashCode();
		}
		if (getNotificationScheme() != null) {
			_hashCode += getNotificationScheme().hashCode();
		}
		if (getPermissionScheme() != null) {
			_hashCode += getPermissionScheme().hashCode();
		}
		if (getProjectUrl() != null) {
			_hashCode += getProjectUrl().hashCode();
		}
		if (getUrl() != null) {
			_hashCode += getUrl().hashCode();
		}
		__hashCodeCalc = false;
		return _hashCode;
	}

}
