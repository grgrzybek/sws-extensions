/**
 * RemoteServerInfo.java
 *
 * This file was auto-generated from WSDL
 * by the Apache Axis 1.4 Apr 22, 2006 (06:55:48 PDT) WSDL2Java emitter.
 */

package org.javelin.sws.ext.ws.axis1.jira.contractfirst.tm12_arraysplain;

@SuppressWarnings("all")
public class RemoteServerInfo implements java.io.Serializable {
	private java.lang.String baseUrl;

	private java.util.Calendar buildDate;

	private java.lang.String buildNumber;

	private org.javelin.sws.ext.ws.axis1.jira.contractfirst.tm12_arraysplain.RemoteTimeInfo serverTime;

	private java.lang.String version;

	public RemoteServerInfo() {
	}

	public RemoteServerInfo(java.lang.String baseUrl, java.util.Calendar buildDate, java.lang.String buildNumber,
			org.javelin.sws.ext.ws.axis1.jira.contractfirst.tm12_arraysplain.RemoteTimeInfo serverTime, java.lang.String version) {
		this.baseUrl = baseUrl;
		this.buildDate = buildDate;
		this.buildNumber = buildNumber;
		this.serverTime = serverTime;
		this.version = version;
	}

	/**
	 * Gets the baseUrl value for this RemoteServerInfo.
	 * 
	 * @return baseUrl
	 */
	public java.lang.String getBaseUrl() {
		return baseUrl;
	}

	/**
	 * Sets the baseUrl value for this RemoteServerInfo.
	 * 
	 * @param baseUrl
	 */
	public void setBaseUrl(java.lang.String baseUrl) {
		this.baseUrl = baseUrl;
	}

	/**
	 * Gets the buildDate value for this RemoteServerInfo.
	 * 
	 * @return buildDate
	 */
	public java.util.Calendar getBuildDate() {
		return buildDate;
	}

	/**
	 * Sets the buildDate value for this RemoteServerInfo.
	 * 
	 * @param buildDate
	 */
	public void setBuildDate(java.util.Calendar buildDate) {
		this.buildDate = buildDate;
	}

	/**
	 * Gets the buildNumber value for this RemoteServerInfo.
	 * 
	 * @return buildNumber
	 */
	public java.lang.String getBuildNumber() {
		return buildNumber;
	}

	/**
	 * Sets the buildNumber value for this RemoteServerInfo.
	 * 
	 * @param buildNumber
	 */
	public void setBuildNumber(java.lang.String buildNumber) {
		this.buildNumber = buildNumber;
	}

	/**
	 * Gets the serverTime value for this RemoteServerInfo.
	 * 
	 * @return serverTime
	 */
	public org.javelin.sws.ext.ws.axis1.jira.contractfirst.tm12_arraysplain.RemoteTimeInfo getServerTime() {
		return serverTime;
	}

	/**
	 * Sets the serverTime value for this RemoteServerInfo.
	 * 
	 * @param serverTime
	 */
	public void setServerTime(org.javelin.sws.ext.ws.axis1.jira.contractfirst.tm12_arraysplain.RemoteTimeInfo serverTime) {
		this.serverTime = serverTime;
	}

	/**
	 * Gets the version value for this RemoteServerInfo.
	 * 
	 * @return version
	 */
	public java.lang.String getVersion() {
		return version;
	}

	/**
	 * Sets the version value for this RemoteServerInfo.
	 * 
	 * @param version
	 */
	public void setVersion(java.lang.String version) {
		this.version = version;
	}

	private java.lang.Object __equalsCalc = null;

	public synchronized boolean equals(java.lang.Object obj) {
		if (!(obj instanceof RemoteServerInfo))
			return false;
		RemoteServerInfo other = (RemoteServerInfo) obj;
		if (obj == null)
			return false;
		if (this == obj)
			return true;
		if (__equalsCalc != null) {
			return (__equalsCalc == obj);
		}
		__equalsCalc = obj;
		boolean _equals;
		_equals = true && ((this.baseUrl == null && other.getBaseUrl() == null) || (this.baseUrl != null && this.baseUrl.equals(other.getBaseUrl())))
				&& ((this.buildDate == null && other.getBuildDate() == null) || (this.buildDate != null && this.buildDate.equals(other.getBuildDate())))
				&& ((this.buildNumber == null && other.getBuildNumber() == null) || (this.buildNumber != null && this.buildNumber.equals(other.getBuildNumber())))
				&& ((this.serverTime == null && other.getServerTime() == null) || (this.serverTime != null && this.serverTime.equals(other.getServerTime())))
				&& ((this.version == null && other.getVersion() == null) || (this.version != null && this.version.equals(other.getVersion())));
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
		if (getBaseUrl() != null) {
			_hashCode += getBaseUrl().hashCode();
		}
		if (getBuildDate() != null) {
			_hashCode += getBuildDate().hashCode();
		}
		if (getBuildNumber() != null) {
			_hashCode += getBuildNumber().hashCode();
		}
		if (getServerTime() != null) {
			_hashCode += getServerTime().hashCode();
		}
		if (getVersion() != null) {
			_hashCode += getVersion().hashCode();
		}
		__hashCodeCalc = false;
		return _hashCode;
	}

}
