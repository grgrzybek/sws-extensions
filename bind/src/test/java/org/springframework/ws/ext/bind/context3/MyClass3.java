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
public class MyClass3 {

	@XmlElement
	private MyClass2 c2;

	/**
	 * @return the c2
	 */
	public MyClass2 getC2() {
		return this.c2;
	}

	/**
	 * @param c2 the c2 to set
	 */
	public void setC2(MyClass2 c2) {
		this.c2 = c2;
	}

}
