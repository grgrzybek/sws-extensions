/*
 * Copyright 2005-2012 the original author or authors.
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

package org.springframework.ws.axis1.serialization;

import java.io.PrintWriter;

import javax.xml.namespace.QName;

import org.apache.axis.encoding.SerializationContext;
import org.apache.axis.encoding.ser.BeanDeserializerFactory;
import org.apache.axis.encoding.ser.BeanSerializerFactory;
import org.junit.Test;
import org.springframework.ws.axis1.dispatch2.model.Param1;
import org.springframework.ws.axis1.dispatch2.model.Param2;
import org.springframework.ws.axis1.dispatch2.model.Param3;

/**
 * <p></p>
 *
 * @author Grzegorz Grzybek
 */
public class SerializationTest {

	@Test
	public void serializeGraph() throws Exception {
		Param1 p1 = new Param1();
		PrintWriter pw = new PrintWriter(System.out);
		SerializationContext sc = new SerializationContext(pw);
		sc.setDoMultiRefs(true);
		sc.setPretty(true);
		sc.getTypeMapping().register(Param1.class, new QName("urn:test:1", "param1"), new BeanSerializerFactory(Param1.class, new QName("urn:test:1", "param1")), new BeanDeserializerFactory(Param1.class, new QName("urn:test:1", "param1")));
		sc.getTypeMapping().register(Param2.class, new QName("urn:test:1", "param2"), new BeanSerializerFactory(Param2.class, new QName("urn:test:1", "param2")), new BeanDeserializerFactory(Param2.class, new QName("urn:test:1", "param2")));
		sc.getTypeMapping().register(Param3.class, new QName("urn:test:1", "param3"), new BeanSerializerFactory(Param3.class, new QName("urn:test:1", "param3")), new BeanDeserializerFactory(Param3.class, new QName("urn:test:1", "param3")));
		sc.serialize(new QName("urn:test:1", "param1"), null, p1);
		sc.outputMultiRefs();
		pw.flush();
	}
	
	@Test
	public void serializeSimple() throws Exception {
		PrintWriter pw = new PrintWriter(System.out);
		SerializationContext sc = new SerializationContext(pw);
		sc.setDoMultiRefs(true);
		sc.setPretty(true);
		sc.serialize(new QName("urn:test:1", "param1"), null, "hello!");
		sc.outputMultiRefs();
		pw.flush();
	}

}
