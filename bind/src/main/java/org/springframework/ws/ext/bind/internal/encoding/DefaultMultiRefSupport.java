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

package org.springframework.ws.ext.bind.internal.encoding;

import java.util.LinkedHashMap;
import java.util.Map;

import javax.xml.namespace.QName;
import javax.xml.stream.XMLEventWriter;
import javax.xml.stream.XMLStreamException;

import org.springframework.ws.ext.bind.internal.MarshallingContext;
import org.springframework.ws.ext.bind.internal.model.AbstractSimpleTypePattern;
import org.springframework.ws.ext.bind.internal.model.ElementPattern;
import org.springframework.ws.ext.bind.internal.model.ValuePattern;
import org.springframework.ws.ext.bind.internal.model.XmlEventsPattern;

/**
 * <p></p>
 *
 * @author Grzegorz Grzybek
 */
public class DefaultMultiRefSupport implements MultiRefSupport {

	private int sequence = 0;
	private Map<Integer, MultiRef> multiRefs = new LinkedHashMap<Integer, MultiRef>();

	/* (non-Javadoc)
	 * @see org.springframework.ws.ext.bind.internal.encoding.MultiRefSupport#registerMultiRef(java.lang.Object, javax.xml.stream.XMLEventWriter, org.springframework.ws.ext.bind.internal.model.XmlEventsPattern)
	 */
	@Override
	public void registerMultiRef(Object object, XMLEventWriter eventWriter, XmlEventsPattern<?> nestedPattern) throws XMLStreamException {
		// object may have been already registered
		MultiRef mr = new MultiRef(object);
		int sysId = mr.getSystemIdentity();
		if (!this.multiRefs.containsKey(sysId)) {
			this.multiRefs.put(sysId, mr);
			mr.setPattern(nestedPattern); // it's the same for all objects, because the containing object is the same object in every registerMultiRef() invocation
			mr.setId(this.sequence++);
		}
		mr = this.multiRefs.get(sysId);
		
		eventWriter.add(XmlEventsPattern.XML_EVENTS_FACTORY.createAttribute("href", "#id" + mr.getId()));
	}

	/* (non-Javadoc)
	 * @see org.springframework.ws.ext.bind.internal.encoding.MultiRefSupport#outputMultiRefs(javax.xml.stream.XMLEventWriter, org.springframework.ws.ext.bind.internal.MarshallingContext)
	 */
	@Override
	public void outputMultiRefs(XMLEventWriter eventWriter, MarshallingContext context) throws XMLStreamException {
		// this process is recursive, because replaying registered multiRefs may result in registering new multiRefs...
		while (true) {
			boolean emptyPass = true;
			for (MultiRef mr: this.multiRefs.values()) {
				if (mr.isDone())
					continue;
				emptyPass = false;
				eventWriter.add(XmlEventsPattern.XML_EVENTS_FACTORY.createStartElement(new QName("", "mr"), null, null));
				eventWriter.add(XmlEventsPattern.XML_EVENTS_FACTORY.createAttribute("id", "id" + mr.getId()));
//				eventWriter.add(XmlEventsPattern.XML_EVENTS_FACTORY.createAttribute(SweJaxbConstants.XSI_TYPE, "id", "id" + mr.getId()));
				
				mr.getPattern().replay(mr.getValue(), eventWriter, context);
				mr.setDone(true);
				eventWriter.add(XmlEventsPattern.XML_EVENTS_FACTORY.createEndElement(new QName("", "mr"), null));
			}
			if (emptyPass)
				break;
		}
	}

	/* (non-Javadoc)
	 * @see org.springframework.ws.ext.bind.internal.encoding.MultiRefSupport#adaptPattern(org.springframework.ws.ext.bind.internal.model.XmlEventsPattern, java.lang.String)
	 */
	@Override
	public <T> XmlEventsPattern<T> adaptPattern(XmlEventsPattern<T> pattern, String accessorName) {
		if (pattern instanceof AbstractSimpleTypePattern) {
			return ElementPattern.newElementPattern(pattern.getSchemaType(), pattern.getJavaType(), new QName("", accessorName), ValuePattern.newValuePattern(pattern.getSchemaType(), pattern.getJavaType()));
		} else {
			return pattern;
		}
	}

}
