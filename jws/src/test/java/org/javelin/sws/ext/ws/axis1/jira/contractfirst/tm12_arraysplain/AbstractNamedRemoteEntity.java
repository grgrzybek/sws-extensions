/**
 * AbstractNamedRemoteEntity.java
 *
 * This file was auto-generated from WSDL
 * by the Apache Axis 1.4 Apr 22, 2006 (06:55:48 PDT) WSDL2Java emitter.
 */

package org.javelin.sws.ext.ws.axis1.jira.contractfirst.tm12_arraysplain;

@SuppressWarnings("all")
public abstract class AbstractNamedRemoteEntity extends org.javelin.sws.ext.ws.axis1.jira.contractfirst.tm12_arraysplain.AbstractRemoteEntity implements
		java.io.Serializable {
	private java.lang.String name;

	public AbstractNamedRemoteEntity() {
	}

	public AbstractNamedRemoteEntity(java.lang.String id, java.lang.String name) {
		super(id);
		this.name = name;
	}

	/**
	 * Gets the name value for this AbstractNamedRemoteEntity.
	 * 
	 * @return name
	 */
	public java.lang.String getName() {
		return name;
	}

	/**
	 * Sets the name value for this AbstractNamedRemoteEntity.
	 * 
	 * @param name
	 */
	public void setName(java.lang.String name) {
		this.name = name;
	}

	private java.lang.Object __equalsCalc = null;

	public synchronized boolean equals(java.lang.Object obj) {
		if (!(obj instanceof AbstractNamedRemoteEntity))
			return false;
		AbstractNamedRemoteEntity other = (AbstractNamedRemoteEntity) obj;
		if (obj == null)
			return false;
		if (this == obj)
			return true;
		if (__equalsCalc != null) {
			return (__equalsCalc == obj);
		}
		__equalsCalc = obj;
		boolean _equals;
		_equals = super.equals(obj) && ((this.name == null && other.getName() == null) || (this.name != null && this.name.equals(other.getName())));
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
		if (getName() != null) {
			_hashCode += getName().hashCode();
		}
		__hashCodeCalc = false;
		return _hashCode;
	}

}
