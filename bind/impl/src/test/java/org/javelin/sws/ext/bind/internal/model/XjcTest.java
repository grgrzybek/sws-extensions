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

package org.javelin.sws.ext.bind.internal.model;

import java.io.File;

import org.junit.Ignore;
import org.junit.Test;

import com.sun.tools.xjc.XJCFacade;

/**
 * <p></p>
 *
 * @author Grzegorz Grzybek
 */
public class XjcTest {

	@Test
	@Ignore
	public void generateClassesByRi() throws Throwable {
		File dir = new File(System.getProperty("java.io.tmpdir") + "/sws-extensions-bind");
		dir.mkdirs();
		String tmpDir = dir.getCanonicalPath();
		String schema = "src/test/resources/org/javelin/sws/ext/bind/internal/model/generated.xsd";
		XJCFacade.main(new String[] {
				"-d", tmpDir,
				"-no-header",
				"-encoding", "UTF-8",
				"-enableIntrospection",
				"-verbose",
				schema
		});
	}

}
