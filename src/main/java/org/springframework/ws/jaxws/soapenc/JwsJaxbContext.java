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

package org.springframework.ws.jaxws.soapenc;

import java.util.LinkedHashMap;
import java.util.Map;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Marshaller;
import javax.xml.bind.Unmarshaller;
import javax.xml.bind.Validator;

/**
 * <p></p>
 *
 * @author Grzegorz Grzybek
 */
@SuppressWarnings("deprecation")
public class JwsJaxbContext extends JAXBContext {

	public static final String PROPERTY_PREFIX = JwsJaxbContext.class.getPackage().getName();

	/**
	 * <p>If the value of this property is true, we try to be as much JAXB compliant as its ... reasonable. Otherwise we try to be pragmatic.</p>
	 * <p>This setting affects:<ul>
	 * <li>package scanning: Do we use ObjectFactory+@XmlRegistry and/or jaxb.index?</li>
	 * </ul></p>
	 */
	public static final String PROPERTY_STRICT_JAXB = PROPERTY_PREFIX + ".strictJaxbCompliance";

	/**
	 * Mapping of Java classes to JaxbMetadata
	 */
	private Map<Class<?>, Object> class2meta = new LinkedHashMap<Class<?>, Object>();

	/**
	 * @param classesToBeBound
	 * @param properties
	 */
	JwsJaxbContext(Class<?>[] classesToBeBound, Map<String, ?> properties) {
		for (Class<?> cl : classesToBeBound) {
			this.class2meta.put(cl, cl);
		}
	}

	/* (non-Javadoc)
	 * @see javax.xml.bind.JAXBContext#createUnmarshaller()
	 */
	@Override
	public Unmarshaller createUnmarshaller() throws JAXBException {
		return new JwsJaxbUnmarshaller(this);
	}

	/* (non-Javadoc)
	 * @see javax.xml.bind.JAXBContext#createMarshaller()
	 */
	@Override
	public Marshaller createMarshaller() throws JAXBException {
		return new JwsJaxbMarshaller(this);
	}

	/* (non-Javadoc)
	 * @see javax.xml.bind.JAXBContext#createValidator()
	 */
	@Override
	public Validator createValidator() throws JAXBException {
		throw new UnsupportedOperationException("Not implemented in " + this.getClass().getName());
	}

}
