/**
 * RemotePriority.java
 *
 * This file was auto-generated from WSDL
 * by the Apache Axis 1.4 Apr 22, 2006 (06:55:48 PDT) WSDL2Java emitter.
 */

package org.javelin.sws.ext.ws.axis1.jira.contractfirst.tm12_arraysplain;

@SuppressWarnings("all")
public class RemotePriority extends org.javelin.sws.ext.ws.axis1.jira.contractfirst.tm12_arraysplain.AbstractRemoteConstant implements java.io.Serializable {
	private java.lang.String color;

	public RemotePriority() {
	}

	public RemotePriority(java.lang.String id, java.lang.String name, java.lang.String description, java.lang.String icon, java.lang.String color) {
		super(id, name, description, icon);
		this.color = color;
	}

	/**
	 * Gets the color value for this RemotePriority.
	 * 
	 * @return color
	 */
	public java.lang.String getColor() {
		return color;
	}

	/**
	 * Sets the color value for this RemotePriority.
	 * 
	 * @param color
	 */
	public void setColor(java.lang.String color) {
		this.color = color;
	}

	private java.lang.Object __equalsCalc = null;

	public synchronized boolean equals(java.lang.Object obj) {
		if (!(obj instanceof RemotePriority))
			return false;
		RemotePriority other = (RemotePriority) obj;
		if (obj == null)
			return false;
		if (this == obj)
			return true;
		if (__equalsCalc != null) {
			return (__equalsCalc == obj);
		}
		__equalsCalc = obj;
		boolean _equals;
		_equals = super.equals(obj) && ((this.color == null && other.getColor() == null) || (this.color != null && this.color.equals(other.getColor())));
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
		if (getColor() != null) {
			_hashCode += getColor().hashCode();
		}
		__hashCodeCalc = false;
		return _hashCode;
	}

}
