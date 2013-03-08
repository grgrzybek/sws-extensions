/*
 * Copyright 2013 Exence SA
 * Created: 21 lut 2013 06:31:32
 */

package org.springframework.ws.ext.utils;

import org.junit.Test;

import static org.hamcrest.CoreMatchers.*;

import static org.junit.Assert.*;

/**
 * <p></p>
 *
 * @author Grzegorz Grzybek
 */
public class NamespaceUtilsTest {

	@Test
	public void package1() {
		assertThat(NamespaceUtils.packageNameToNamespace(this.getClass().getPackage()), equalTo("http://utils.ext.ws.springframework.org/"));
	}

}
