/**
 * WrappedEncResponse.java
 *
 * This file was auto-generated from WSDL
 * by the Apache Axis 1.4 Apr 22, 2006 (06:55:48 PDT) WSDL2Java emitter.
 */

package org.springframework.ws.axis1.wrapped_enc;

@SuppressWarnings("all")
public class WrappedEncResponse  implements java.io.Serializable {
    private org.springframework.ws.axis1.wrapped_enc.Result1 wrappedEncReturn;

    public WrappedEncResponse() {
    }

    public WrappedEncResponse(
           org.springframework.ws.axis1.wrapped_enc.Result1 wrappedEncReturn) {
           this.wrappedEncReturn = wrappedEncReturn;
    }


    /**
     * Gets the wrappedEncReturn value for this WrappedEncResponse.
     * 
     * @return wrappedEncReturn
     */
    public org.springframework.ws.axis1.wrapped_enc.Result1 getWrappedEncReturn() {
        return wrappedEncReturn;
    }


    /**
     * Sets the wrappedEncReturn value for this WrappedEncResponse.
     * 
     * @param wrappedEncReturn
     */
    public void setWrappedEncReturn(org.springframework.ws.axis1.wrapped_enc.Result1 wrappedEncReturn) {
        this.wrappedEncReturn = wrappedEncReturn;
    }

    private java.lang.Object __equalsCalc = null;
    public synchronized boolean equals(java.lang.Object obj) {
        if (!(obj instanceof WrappedEncResponse)) return false;
        WrappedEncResponse other = (WrappedEncResponse) obj;
        if (obj == null) return false;
        if (this == obj) return true;
        if (__equalsCalc != null) {
            return (__equalsCalc == obj);
        }
        __equalsCalc = obj;
        boolean _equals;
        _equals = true && 
            ((this.wrappedEncReturn==null && other.getWrappedEncReturn()==null) || 
             (this.wrappedEncReturn!=null &&
              this.wrappedEncReturn.equals(other.getWrappedEncReturn())));
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
        if (getWrappedEncReturn() != null) {
            _hashCode += getWrappedEncReturn().hashCode();
        }
        __hashCodeCalc = false;
        return _hashCode;
    }

}
