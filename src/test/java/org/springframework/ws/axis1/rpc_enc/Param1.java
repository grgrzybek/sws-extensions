/**
 * Param1.java
 *
 * This file was auto-generated from WSDL
 * by the Apache Axis 1.4 Apr 22, 2006 (06:55:48 PDT) WSDL2Java emitter.
 */

package org.springframework.ws.axis1.rpc_enc;

@SuppressWarnings("all")
public class Param1 implements java.io.Serializable {
	private java.lang.Byte[] list = new Byte[] { (byte) 0x24, (byte) 0x9A };

	private java.lang.Long number = 37L;

	private org.springframework.ws.axis1.rpc_enc.Param2 param2 = new Param2();

	private org.springframework.ws.axis1.rpc_enc.Param3[] param3List = new Param3[] { new Param3(), new Param3(), new Param3() };

	private java.lang.String string = "str (String)";

	public Param1() {
	}

	public Param1(java.lang.Byte[] list, java.lang.Long number, org.springframework.ws.axis1.rpc_enc.Param2 param2,
			org.springframework.ws.axis1.rpc_enc.Param3[] param3List, java.lang.String string) {
		this.list = list;
		this.number = number;
		this.param2 = param2;
		this.param3List = param3List;
		this.string = string;
	}

	/**
	 * Gets the list value for this Param1.
	 * 
	 * @return list
	 */
	public java.lang.Byte[] getList() {
		return this.list;
	}

	/**
	 * Sets the list value for this Param1.
	 * 
	 * @param list
	 */
	public void setList(java.lang.Byte[] list) {
		this.list = list;
	}

	/**
	 * Gets the number value for this Param1.
	 * 
	 * @return number
	 */
	public java.lang.Long getNumber() {
		return this.number;
	}

	/**
	 * Sets the number value for this Param1.
	 * 
	 * @param number
	 */
	public void setNumber(java.lang.Long number) {
		this.number = number;
	}

	/**
	 * Gets the param2 value for this Param1.
	 * 
	 * @return param2
	 */
	public org.springframework.ws.axis1.rpc_enc.Param2 getParam2() {
		return this.param2;
	}

	/**
	 * Sets the param2 value for this Param1.
	 * 
	 * @param param2
	 */
	public void setParam2(org.springframework.ws.axis1.rpc_enc.Param2 param2) {
		this.param2 = param2;
	}

	/**
	 * Gets the param3List value for this Param1.
	 * 
	 * @return param3List
	 */
	public org.springframework.ws.axis1.rpc_enc.Param3[] getParam3List() {
		return this.param3List;
	}

	/**
	 * Sets the param3List value for this Param1.
	 * 
	 * @param param3List
	 */
	public void setParam3List(org.springframework.ws.axis1.rpc_enc.Param3[] param3List) {
		this.param3List = param3List;
	}

	/**
	 * Gets the string value for this Param1.
	 * 
	 * @return string
	 */
	public java.lang.String getString() {
		return this.string;
	}

	/**
	 * Sets the string value for this Param1.
	 * 
	 * @param string
	 */
	public void setString(java.lang.String string) {
		this.string = string;
	}

	private java.lang.Object __equalsCalc = null;

	public synchronized boolean equals(java.lang.Object obj) {
		if (!(obj instanceof Param1))
			return false;
		Param1 other = (Param1) obj;
		if (obj == null)
			return false;
		if (this == obj)
			return true;
		if (this.__equalsCalc != null) {
			return (this.__equalsCalc == obj);
		}
		this.__equalsCalc = obj;
		boolean _equals;
		_equals = true
				&& ((this.list == null && other.getList() == null) || (this.list != null && java.util.Arrays.equals(this.list, other.getList())))
				&& ((this.number == null && other.getNumber() == null) || (this.number != null && this.number.equals(other.getNumber())))
				&& ((this.param2 == null && other.getParam2() == null) || (this.param2 != null && this.param2.equals(other.getParam2())))
				&& ((this.param3List == null && other.getParam3List() == null) || (this.param3List != null && java.util.Arrays.equals(this.param3List,
						other.getParam3List())))
				&& ((this.string == null && other.getString() == null) || (this.string != null && this.string.equals(other.getString())));
		this.__equalsCalc = null;
		return _equals;
	}

	private boolean __hashCodeCalc = false;

	public synchronized int hashCode() {
		if (this.__hashCodeCalc) {
			return 0;
		}
		this.__hashCodeCalc = true;
		int _hashCode = 1;
		if (this.getList() != null) {
			for (int i = 0; i < java.lang.reflect.Array.getLength(this.getList()); i++) {
				java.lang.Object obj = java.lang.reflect.Array.get(this.getList(), i);
				if (obj != null && !obj.getClass().isArray()) {
					_hashCode += obj.hashCode();
				}
			}
		}
		if (this.getNumber() != null) {
			_hashCode += this.getNumber().hashCode();
		}
		if (this.getParam2() != null) {
			_hashCode += this.getParam2().hashCode();
		}
		if (this.getParam3List() != null) {
			for (int i = 0; i < java.lang.reflect.Array.getLength(this.getParam3List()); i++) {
				java.lang.Object obj = java.lang.reflect.Array.get(this.getParam3List(), i);
				if (obj != null && !obj.getClass().isArray()) {
					_hashCode += obj.hashCode();
				}
			}
		}
		if (this.getString() != null) {
			_hashCode += this.getString().hashCode();
		}
		this.__hashCodeCalc = false;
		return _hashCode;
	}

}
