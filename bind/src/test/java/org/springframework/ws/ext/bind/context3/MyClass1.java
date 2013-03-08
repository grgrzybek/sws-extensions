/*
 * Copyright 2013 Exence SA
 * Created: 8 mar 2013 13:43:40
 */

package org.springframework.ws.ext.bind.context3;

import javax.xml.bind.annotation.XmlElement;

/**
 * <p></p>
 *
 * @author Grzegorz Grzybek
 */
public class MyClass1 {

	@XmlElement
	private MyClass1 other;

	/**
	 * @return the other
	 */
	public MyClass1 getOther() {
		return this.other;
	}

	/**
	 * @param other the other to set
	 */
	public void setOther(MyClass1 other) {
		this.other = other;
	}

}
