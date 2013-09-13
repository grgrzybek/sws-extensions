/**
 * RemoteConfiguration.java
 *
 * This file was auto-generated from WSDL
 * by the Apache Axis 1.4 Apr 22, 2006 (06:55:48 PDT) WSDL2Java emitter.
 */

package org.javelin.sws.ext.ws.axis1.jira.contractfirst.tm12_arraysplain;

@SuppressWarnings("all")
public class RemoteConfiguration implements java.io.Serializable {
	private boolean allowAttachments;

	private boolean allowExternalUserManagment;

	private boolean allowIssueLinking;

	private boolean allowSubTasks;

	private boolean allowTimeTracking;

	private boolean allowUnassignedIssues;

	private boolean allowVoting;

	private boolean allowWatching;

	private int timeTrackingDaysPerWeek;

	private int timeTrackingHoursPerDay;

	public RemoteConfiguration() {
	}

	public RemoteConfiguration(boolean allowAttachments, boolean allowExternalUserManagment, boolean allowIssueLinking, boolean allowSubTasks,
			boolean allowTimeTracking, boolean allowUnassignedIssues, boolean allowVoting, boolean allowWatching, int timeTrackingDaysPerWeek,
			int timeTrackingHoursPerDay) {
		this.allowAttachments = allowAttachments;
		this.allowExternalUserManagment = allowExternalUserManagment;
		this.allowIssueLinking = allowIssueLinking;
		this.allowSubTasks = allowSubTasks;
		this.allowTimeTracking = allowTimeTracking;
		this.allowUnassignedIssues = allowUnassignedIssues;
		this.allowVoting = allowVoting;
		this.allowWatching = allowWatching;
		this.timeTrackingDaysPerWeek = timeTrackingDaysPerWeek;
		this.timeTrackingHoursPerDay = timeTrackingHoursPerDay;
	}

	/**
	 * Gets the allowAttachments value for this RemoteConfiguration.
	 * 
	 * @return allowAttachments
	 */
	public boolean isAllowAttachments() {
		return allowAttachments;
	}

	/**
	 * Sets the allowAttachments value for this RemoteConfiguration.
	 * 
	 * @param allowAttachments
	 */
	public void setAllowAttachments(boolean allowAttachments) {
		this.allowAttachments = allowAttachments;
	}

	/**
	 * Gets the allowExternalUserManagment value for this RemoteConfiguration.
	 * 
	 * @return allowExternalUserManagment
	 */
	public boolean isAllowExternalUserManagment() {
		return allowExternalUserManagment;
	}

	/**
	 * Sets the allowExternalUserManagment value for this RemoteConfiguration.
	 * 
	 * @param allowExternalUserManagment
	 */
	public void setAllowExternalUserManagment(boolean allowExternalUserManagment) {
		this.allowExternalUserManagment = allowExternalUserManagment;
	}

	/**
	 * Gets the allowIssueLinking value for this RemoteConfiguration.
	 * 
	 * @return allowIssueLinking
	 */
	public boolean isAllowIssueLinking() {
		return allowIssueLinking;
	}

	/**
	 * Sets the allowIssueLinking value for this RemoteConfiguration.
	 * 
	 * @param allowIssueLinking
	 */
	public void setAllowIssueLinking(boolean allowIssueLinking) {
		this.allowIssueLinking = allowIssueLinking;
	}

	/**
	 * Gets the allowSubTasks value for this RemoteConfiguration.
	 * 
	 * @return allowSubTasks
	 */
	public boolean isAllowSubTasks() {
		return allowSubTasks;
	}

	/**
	 * Sets the allowSubTasks value for this RemoteConfiguration.
	 * 
	 * @param allowSubTasks
	 */
	public void setAllowSubTasks(boolean allowSubTasks) {
		this.allowSubTasks = allowSubTasks;
	}

	/**
	 * Gets the allowTimeTracking value for this RemoteConfiguration.
	 * 
	 * @return allowTimeTracking
	 */
	public boolean isAllowTimeTracking() {
		return allowTimeTracking;
	}

	/**
	 * Sets the allowTimeTracking value for this RemoteConfiguration.
	 * 
	 * @param allowTimeTracking
	 */
	public void setAllowTimeTracking(boolean allowTimeTracking) {
		this.allowTimeTracking = allowTimeTracking;
	}

	/**
	 * Gets the allowUnassignedIssues value for this RemoteConfiguration.
	 * 
	 * @return allowUnassignedIssues
	 */
	public boolean isAllowUnassignedIssues() {
		return allowUnassignedIssues;
	}

	/**
	 * Sets the allowUnassignedIssues value for this RemoteConfiguration.
	 * 
	 * @param allowUnassignedIssues
	 */
	public void setAllowUnassignedIssues(boolean allowUnassignedIssues) {
		this.allowUnassignedIssues = allowUnassignedIssues;
	}

	/**
	 * Gets the allowVoting value for this RemoteConfiguration.
	 * 
	 * @return allowVoting
	 */
	public boolean isAllowVoting() {
		return allowVoting;
	}

	/**
	 * Sets the allowVoting value for this RemoteConfiguration.
	 * 
	 * @param allowVoting
	 */
	public void setAllowVoting(boolean allowVoting) {
		this.allowVoting = allowVoting;
	}

	/**
	 * Gets the allowWatching value for this RemoteConfiguration.
	 * 
	 * @return allowWatching
	 */
	public boolean isAllowWatching() {
		return allowWatching;
	}

	/**
	 * Sets the allowWatching value for this RemoteConfiguration.
	 * 
	 * @param allowWatching
	 */
	public void setAllowWatching(boolean allowWatching) {
		this.allowWatching = allowWatching;
	}

	/**
	 * Gets the timeTrackingDaysPerWeek value for this RemoteConfiguration.
	 * 
	 * @return timeTrackingDaysPerWeek
	 */
	public int getTimeTrackingDaysPerWeek() {
		return timeTrackingDaysPerWeek;
	}

	/**
	 * Sets the timeTrackingDaysPerWeek value for this RemoteConfiguration.
	 * 
	 * @param timeTrackingDaysPerWeek
	 */
	public void setTimeTrackingDaysPerWeek(int timeTrackingDaysPerWeek) {
		this.timeTrackingDaysPerWeek = timeTrackingDaysPerWeek;
	}

	/**
	 * Gets the timeTrackingHoursPerDay value for this RemoteConfiguration.
	 * 
	 * @return timeTrackingHoursPerDay
	 */
	public int getTimeTrackingHoursPerDay() {
		return timeTrackingHoursPerDay;
	}

	/**
	 * Sets the timeTrackingHoursPerDay value for this RemoteConfiguration.
	 * 
	 * @param timeTrackingHoursPerDay
	 */
	public void setTimeTrackingHoursPerDay(int timeTrackingHoursPerDay) {
		this.timeTrackingHoursPerDay = timeTrackingHoursPerDay;
	}

	private java.lang.Object __equalsCalc = null;

	public synchronized boolean equals(java.lang.Object obj) {
		if (!(obj instanceof RemoteConfiguration))
			return false;
		RemoteConfiguration other = (RemoteConfiguration) obj;
		if (obj == null)
			return false;
		if (this == obj)
			return true;
		if (__equalsCalc != null) {
			return (__equalsCalc == obj);
		}
		__equalsCalc = obj;
		boolean _equals;
		_equals = true && this.allowAttachments == other.isAllowAttachments() && this.allowExternalUserManagment == other.isAllowExternalUserManagment()
				&& this.allowIssueLinking == other.isAllowIssueLinking() && this.allowSubTasks == other.isAllowSubTasks()
				&& this.allowTimeTracking == other.isAllowTimeTracking() && this.allowUnassignedIssues == other.isAllowUnassignedIssues()
				&& this.allowVoting == other.isAllowVoting() && this.allowWatching == other.isAllowWatching()
				&& this.timeTrackingDaysPerWeek == other.getTimeTrackingDaysPerWeek() && this.timeTrackingHoursPerDay == other.getTimeTrackingHoursPerDay();
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
		_hashCode += (isAllowAttachments() ? Boolean.TRUE : Boolean.FALSE).hashCode();
		_hashCode += (isAllowExternalUserManagment() ? Boolean.TRUE : Boolean.FALSE).hashCode();
		_hashCode += (isAllowIssueLinking() ? Boolean.TRUE : Boolean.FALSE).hashCode();
		_hashCode += (isAllowSubTasks() ? Boolean.TRUE : Boolean.FALSE).hashCode();
		_hashCode += (isAllowTimeTracking() ? Boolean.TRUE : Boolean.FALSE).hashCode();
		_hashCode += (isAllowUnassignedIssues() ? Boolean.TRUE : Boolean.FALSE).hashCode();
		_hashCode += (isAllowVoting() ? Boolean.TRUE : Boolean.FALSE).hashCode();
		_hashCode += (isAllowWatching() ? Boolean.TRUE : Boolean.FALSE).hashCode();
		_hashCode += getTimeTrackingDaysPerWeek();
		_hashCode += getTimeTrackingHoursPerDay();
		__hashCodeCalc = false;
		return _hashCode;
	}

}
