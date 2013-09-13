/**
 * RemoteAvatar.java
 *
 * This file was auto-generated from WSDL
 * by the Apache Axis 1.4 Apr 22, 2006 (06:55:48 PDT) WSDL2Java emitter.
 */

package org.javelin.sws.ext.ws.axis1.jira.contractfirst.tm12_arraysplain;

@SuppressWarnings("all")
public class RemoteAvatar implements java.io.Serializable {
	private java.lang.String base64Data;

	private java.lang.String contentType;

	private long id;

	private java.lang.String owner;

	private boolean system;

	private java.lang.String type;

	public RemoteAvatar() {
	}

	public RemoteAvatar(java.lang.String base64Data, java.lang.String contentType, long id, java.lang.String owner, boolean system, java.lang.String type) {
		this.base64Data = base64Data;
		this.contentType = contentType;
		this.id = id;
		this.owner = owner;
		this.system = system;
		this.type = type;
	}

	/**
	 * Gets the base64Data value for this RemoteAvatar.
	 * 
	 * @return base64Data
	 */
	public java.lang.String getBase64Data() {
		return base64Data;
	}

	/**
	 * Sets the base64Data value for this RemoteAvatar.
	 * 
	 * @param base64Data
	 */
	public void setBase64Data(java.lang.String base64Data) {
		this.base64Data = base64Data;
	}

	/**
	 * Gets the contentType value for this RemoteAvatar.
	 * 
	 * @return contentType
	 */
	public java.lang.String getContentType() {
		return contentType;
	}

	/**
	 * Sets the contentType value for this RemoteAvatar.
	 * 
	 * @param contentType
	 */
	public void setContentType(java.lang.String contentType) {
		this.contentType = contentType;
	}

	/**
	 * Gets the id value for this RemoteAvatar.
	 * 
	 * @return id
	 */
	public long getId() {
		return id;
	}

	/**
	 * Sets the id value for this RemoteAvatar.
	 * 
	 * @param id
	 */
	public void setId(long id) {
		this.id = id;
	}

	/**
	 * Gets the owner value for this RemoteAvatar.
	 * 
	 * @return owner
	 */
	public java.lang.String getOwner() {
		return owner;
	}

	/**
	 * Sets the owner value for this RemoteAvatar.
	 * 
	 * @param owner
	 */
	public void setOwner(java.lang.String owner) {
		this.owner = owner;
	}

	/**
	 * Gets the system value for this RemoteAvatar.
	 * 
	 * @return system
	 */
	public boolean isSystem() {
		return system;
	}

	/**
	 * Sets the system value for this RemoteAvatar.
	 * 
	 * @param system
	 */
	public void setSystem(boolean system) {
		this.system = system;
	}

	/**
	 * Gets the type value for this RemoteAvatar.
	 * 
	 * @return type
	 */
	public java.lang.String getType() {
		return type;
	}

	/**
	 * Sets the type value for this RemoteAvatar.
	 * 
	 * @param type
	 */
	public void setType(java.lang.String type) {
		this.type = type;
	}

	private java.lang.Object __equalsCalc = null;

	public synchronized boolean equals(java.lang.Object obj) {
		if (!(obj instanceof RemoteAvatar))
			return false;
		RemoteAvatar other = (RemoteAvatar) obj;
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
				&& ((this.base64Data == null && other.getBase64Data() == null) || (this.base64Data != null && this.base64Data.equals(other.getBase64Data())))
				&& ((this.contentType == null && other.getContentType() == null) || (this.contentType != null && this.contentType.equals(other.getContentType())))
				&& this.id == other.getId() && ((this.owner == null && other.getOwner() == null) || (this.owner != null && this.owner.equals(other.getOwner())))
				&& this.system == other.isSystem() && ((this.type == null && other.getType() == null) || (this.type != null && this.type.equals(other.getType())));
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
		if (getBase64Data() != null) {
			_hashCode += getBase64Data().hashCode();
		}
		if (getContentType() != null) {
			_hashCode += getContentType().hashCode();
		}
		_hashCode += new Long(getId()).hashCode();
		if (getOwner() != null) {
			_hashCode += getOwner().hashCode();
		}
		_hashCode += (isSystem() ? Boolean.TRUE : Boolean.FALSE).hashCode();
		if (getType() != null) {
			_hashCode += getType().hashCode();
		}
		__hashCodeCalc = false;
		return _hashCode;
	}

}
