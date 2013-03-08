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
public class MyClass2 {

	@XmlElement
	private MyClass3 c3;

	/**
	 * @return the c3
	 */
	public MyClass3 getC3() {
		return this.c3;
	}

	/**
	 * @param c3 the c3 to set
	 */
	public void setC3(MyClass3 c3) {
		this.c3 = c3;
	}
	
}
