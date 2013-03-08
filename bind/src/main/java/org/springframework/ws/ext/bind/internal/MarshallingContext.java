/*
 * Copyright 2013 Exence SA
 * Created: 8 mar 2013 10:47:47
 */

package org.springframework.ws.ext.bind.internal;

/**
 * <p>Collects all information and utilities needed during marshalling.</p>
 *
 * @author Grzegorz Grzybek
 */
public class MarshallingContext {

	private boolean repairingXmlEventWriter = false;
	private boolean multiRefEncoding = false;

	/**
	 * @return the repairingXmlEventWriter
	 */
	public boolean isRepairingXmlEventWriter() {
		return this.repairingXmlEventWriter;
	}

	/**
	 * @param repairingXmlEventWriter the repairingXmlEventWriter to set
	 */
	public void setRepairingXmlEventWriter(boolean repairingXmlEventWriter) {
		this.repairingXmlEventWriter = repairingXmlEventWriter;
	}

	/**
	 * @return the multiRefEncoding
	 */
	public boolean isMultiRefEncoding() {
		return this.multiRefEncoding;
	}

	/**
	 * @param multiRefEncoding the multiRefEncoding to set
	 */
	public void setMultiRefEncoding(boolean multiRefEncoding) {
		this.multiRefEncoding = multiRefEncoding;
	}

}
