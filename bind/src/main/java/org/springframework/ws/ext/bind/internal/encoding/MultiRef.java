/*
 * Copyright 2013 Exence SA
 * Created: 8 mar 2013 13:03:02
 */

package org.springframework.ws.ext.bind.internal.encoding;

import org.springframework.ws.ext.bind.internal.model.XmlEventsPattern;

/**
 * <p></p>
 *
 * @author Grzegorz Grzybek
 */
public class MultiRef {

	private int id;
	private int systemIdentity;
	private Object value;
	private XmlEventsPattern pattern;
	private boolean done = false;

	/**
	 * @param value
	 */
	public MultiRef(Object value) {
		this.systemIdentity = System.identityHashCode(value);
		this.value = value;
	}

	/**
	 * @return the systemIdentity
	 */
	public int getSystemIdentity() {
		return this.systemIdentity;
	}

	/**
	 * @return the value
	 */
	public Object getValue() {
		return this.value;
	}

	/**
	 * @param id
	 */
	public void setId(int id) {
		this.id = id;
	}

	/**
	 * @return the id
	 */
	public int getId() {
		return this.id;
	}

	/**
	 * @return the pattern
	 */
	public XmlEventsPattern getPattern() {
		return this.pattern;
	}

	/**
	 * @param pattern the pattern to set
	 */
	public void setPattern(XmlEventsPattern pattern) {
		this.pattern = pattern;
	}

	/**
	 * @return the done
	 */
	public boolean isDone() {
		return this.done;
	}

	/**
	 * @param done the done to set
	 */
	public void setDone(boolean done) {
		this.done = done;
	}

	/* (non-Javadoc)
	 * @see java.lang.Object#hashCode()
	 */
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + this.systemIdentity;
		return result;
	}

	/* (non-Javadoc)
	 * @see java.lang.Object#equals(java.lang.Object)
	 */
	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		MultiRef other = (MultiRef) obj;
		if (this.systemIdentity != other.systemIdentity)
			return false;
		return true;
	}

}
