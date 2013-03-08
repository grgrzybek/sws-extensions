/*
 * Copyright 2013 Exence SA
 * Created: 8 mar 2013 10:52:48
 */

package org.springframework.ws.ext.bind;

/**
 * <p></p>
 *
 * @author Grzegorz Grzybek
 */
public interface JwsJaxbConstants {

	/**
	 * The name of the property used to specify whether or not we're doing multiRef encoding (Soap 1.1 Section 5 Encoding) during marshalling.
	 * This should be detected by Unmarshaller however.
	 */
	public static final String JWS_JAXB_MULTIREFS = "jws.jaxb.multirefs";

}
