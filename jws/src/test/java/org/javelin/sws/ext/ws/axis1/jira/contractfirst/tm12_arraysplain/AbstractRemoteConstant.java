/**
 * AbstractRemoteConstant.java
 *
 * This file was auto-generated from WSDL
 * by the Apache Axis 1.4 Apr 22, 2006 (06:55:48 PDT) WSDL2Java emitter.
 */

package org.javelin.sws.ext.ws.axis1.jira.contractfirst.tm12_arraysplain;

@SuppressWarnings("all")
public abstract class AbstractRemoteConstant extends org.javelin.sws.ext.ws.axis1.jira.contractfirst.tm12_arraysplain.AbstractNamedRemoteEntity implements
		java.io.Serializable {
	private java.lang.String description;

	private java.lang.String icon;

	public AbstractRemoteConstant() {
	}

	public AbstractRemoteConstant(java.lang.String id, java.lang.String name, java.lang.String description, java.lang.String icon) {
		super(id, name);
		this.description = description;
		this.icon = icon;
	}

	/**
	 * Gets the description value for this AbstractRemoteConstant.
	 * 
	 * @return description
	 */
	public java.lang.String getDescription() {
		return description;
	}

	/**
	 * Sets the description value for this AbstractRemoteConstant.
	 * 
	 * @param description
	 */
	public void setDescription(java.lang.String description) {
		this.description = description;
	}

	/**
	 * Gets the icon value for this AbstractRemoteConstant.
	 * 
	 * @return icon
	 */
	public java.lang.String getIcon() {
		return icon;
	}

	/**
	 * Sets the icon value for this AbstractRemoteConstant.
	 * 
	 * @param icon
	 */
	public void setIcon(java.lang.String icon) {
		this.icon = icon;
	}

	private java.lang.Object __equalsCalc = null;

	public synchronized boolean equals(java.lang.Object obj) {
		if (!(obj instanceof AbstractRemoteConstant))
			return false;
		AbstractRemoteConstant other = (AbstractRemoteConstant) obj;
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
				&& ((this.icon == null && other.getIcon() == null) || (this.icon != null && this.icon.equals(other.getIcon())));
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
		if (getIcon() != null) {
			_hashCode += getIcon().hashCode();
		}
		__hashCodeCalc = false;
		return _hashCode;
	}

}
