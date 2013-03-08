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
	
	@Test
	public void package1WithPath() {
		assertThat(NamespaceUtils.packageNameToNamespace(this.getClass().getPackage(), 2), equalTo("http://springframework.org/ws/ext/utils"));
		assertThat(NamespaceUtils.packageNameToNamespace(this.getClass().getPackage(), 3), equalTo("http://ws.springframework.org/ext/utils"));
	}
	
	@Test(expected = IllegalArgumentException.class)
	public void package2WithIllegalDomain() {
		NamespaceUtils.packageNameToNamespace(this.getClass().getPackage(), 1);
	}
	
	@Test
	public void package2() {
		assertThat(NamespaceUtils.packageNameToNamespace(String.class.getPackage()), equalTo("http://lang.java/"));
	}
	
	@Test(expected = IllegalArgumentException.class)
	public void emptyPackage() {
		NamespaceUtils.packageNameToNamespace(Package.getPackage(""));
	}

}
