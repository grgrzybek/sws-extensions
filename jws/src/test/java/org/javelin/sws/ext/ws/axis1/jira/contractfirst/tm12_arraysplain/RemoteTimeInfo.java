/**
 * RemoteTimeInfo.java
 *
 * This file was auto-generated from WSDL
 * by the Apache Axis 1.4 Apr 22, 2006 (06:55:48 PDT) WSDL2Java emitter.
 */

package org.javelin.sws.ext.ws.axis1.jira.contractfirst.tm12_arraysplain;

@SuppressWarnings("all")
public class RemoteTimeInfo implements java.io.Serializable {
	private java.lang.String serverTime;

	private java.lang.String timeZoneId;

	public RemoteTimeInfo() {
	}

	public RemoteTimeInfo(java.lang.String serverTime, java.lang.String timeZoneId) {
		this.serverTime = serverTime;
		this.timeZoneId = timeZoneId;
	}

	/**
	 * Gets the serverTime value for this RemoteTimeInfo.
	 * 
	 * @return serverTime
	 */
	public java.lang.String getServerTime() {
		return serverTime;
	}

	/**
	 * Sets the serverTime value for this RemoteTimeInfo.
	 * 
	 * @param serverTime
	 */
	public void setServerTime(java.lang.String serverTime) {
		this.serverTime = serverTime;
	}

	/**
	 * Gets the timeZoneId value for this RemoteTimeInfo.
	 * 
	 * @return timeZoneId
	 */
	public java.lang.String getTimeZoneId() {
		return timeZoneId;
	}

	/**
	 * Sets the timeZoneId value for this RemoteTimeInfo.
	 * 
	 * @param timeZoneId
	 */
	public void setTimeZoneId(java.lang.String timeZoneId) {
		this.timeZoneId = timeZoneId;
	}

	private java.lang.Object __equalsCalc = null;

	public synchronized boolean equals(java.lang.Object obj) {
		if (!(obj instanceof RemoteTimeInfo))
			return false;
		RemoteTimeInfo other = (RemoteTimeInfo) obj;
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
				&& ((this.serverTime == null && other.getServerTime() == null) || (this.serverTime != null && this.serverTime.equals(other.getServerTime())))
				&& ((this.timeZoneId == null && other.getTimeZoneId() == null) || (this.timeZoneId != null && this.timeZoneId.equals(other.getTimeZoneId())));
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
		if (getServerTime() != null) {
			_hashCode += getServerTime().hashCode();
		}
		if (getTimeZoneId() != null) {
			_hashCode += getTimeZoneId().hashCode();
		}
		__hashCodeCalc = false;
		return _hashCode;
	}

}
