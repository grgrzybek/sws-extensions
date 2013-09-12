/*
 * Copyright 2005-2013 the original author or authors.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package org.javelin.sws.ext.bind.internal;

import org.javelin.sws.ext.bind.internal.encoding.DefaultMultiRefSupport;
import org.javelin.sws.ext.bind.internal.encoding.MultiRefSupport;

/**
 * <p>Collects all information and utilities needed during marshalling.</p>
 *
 * @author Grzegorz Grzybek
 */
public class MarshallingContext {

	private boolean repairingXmlEventWriter = false;
	private boolean multiRefEncoding = false;
	private MultiRefSupport multiRefSupport = null;
	private boolean sendTypes;

	private int prefixCounter = 0;

	/**
	 * <p>Returns new, unused prefix. It may be used to register new or exising namespace under non-conflicting prefix.</p>
	 * <p>This method is <b>not</b> thread-safe</p>
	 * 
	 * @return
	 */
	public String newPrefix() {
		return "ns" + (++this.prefixCounter);
	}

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
		if (this.multiRefEncoding)
			this.multiRefSupport = new DefaultMultiRefSupport();
	}

	/**
	 * @return the multiRefSupport
	 */
	public MultiRefSupport getMultiRefSupport() {
		return this.multiRefSupport;
	}

	/**
	 * @param sendTypes the sendTypes to set
	 */
	public void setSendTypes(boolean sendTypes) {
		this.sendTypes = sendTypes;
	}

	/**
	 * @return the sendTypes
	 */
	public boolean isSendTypes() {
		return this.sendTypes;
	}

}
