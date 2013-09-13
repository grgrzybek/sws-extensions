/**
 * RemoteUser.java
 *
 * This file was auto-generated from WSDL
 * by the Apache Axis 1.4 Apr 22, 2006 (06:55:48 PDT) WSDL2Java emitter.
 */

package org.javelin.sws.ext.ws.axis1.jira.contractfirst.tm12_arraysplain;

@SuppressWarnings("all")
public class RemoteUser extends org.javelin.sws.ext.ws.axis1.jira.contractfirst.tm12_arraysplain.RemoteEntity implements java.io.Serializable {
	private java.lang.String email;

	private java.lang.String fullname;

	private java.lang.String name;

	public RemoteUser() {
	}

	public RemoteUser(java.lang.String email, java.lang.String fullname, java.lang.String name) {
		this.email = email;
		this.fullname = fullname;
		this.name = name;
	}

	/**
	 * Gets the email value for this RemoteUser.
	 * 
	 * @return email
	 */
	public java.lang.String getEmail() {
		return email;
	}

	/**
	 * Sets the email value for this RemoteUser.
	 * 
	 * @param email
	 */
	public void setEmail(java.lang.String email) {
		this.email = email;
	}

	/**
	 * Gets the fullname value for this RemoteUser.
	 * 
	 * @return fullname
	 */
	public java.lang.String getFullname() {
		return fullname;
	}

	/**
	 * Sets the fullname value for this RemoteUser.
	 * 
	 * @param fullname
	 */
	public void setFullname(java.lang.String fullname) {
		this.fullname = fullname;
	}

	/**
	 * Gets the name value for this RemoteUser.
	 * 
	 * @return name
	 */
	public java.lang.String getName() {
		return name;
	}

	/**
	 * Sets the name value for this RemoteUser.
	 * 
	 * @param name
	 */
	public void setName(java.lang.String name) {
		this.name = name;
	}

	private java.lang.Object __equalsCalc = null;

	public synchronized boolean equals(java.lang.Object obj) {
		if (!(obj instanceof RemoteUser))
			return false;
		RemoteUser other = (RemoteUser) obj;
		if (obj == null)
			return false;
		if (this == obj)
			return true;
		if (__equalsCalc != null) {
			return (__equalsCalc == obj);
		}
		__equalsCalc = obj;
		boolean _equals;
		_equals = super.equals(obj) && ((this.email == null && other.getEmail() == null) || (this.email != null && this.email.equals(other.getEmail())))
				&& ((this.fullname == null && other.getFullname() == null) || (this.fullname != null && this.fullname.equals(other.getFullname())))
				&& ((this.name == null && other.getName() == null) || (this.name != null && this.name.equals(other.getName())));
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
		if (getEmail() != null) {
			_hashCode += getEmail().hashCode();
		}
		if (getFullname() != null) {
			_hashCode += getFullname().hashCode();
		}
		if (getName() != null) {
			_hashCode += getName().hashCode();
		}
		__hashCodeCalc = false;
		return _hashCode;
	}

}
