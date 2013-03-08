/*
 * Copyright 2013 Exence SA
 * Created: 8 mar 2013 12:30:01
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
	public void registerMultiRef(Object object, XMLEventWriter eventWriter, XmlEventsPattern nestedPattern) throws XMLStreamException {
		// object may have been already registered
		MultiRef mr = new MultiRef(object);
		int sysId = mr.getSystemIdentity();
		if (!this.multiRefs.containsKey(sysId)) {
			this.multiRefs.put(sysId, mr);
			mr.setPattern(nestedPattern); // it's the same for all objects, because the container is the same object in every registration
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
		// this process is recursive, because replaying multiRefs may create new multiRefs...
		while (true) {
			boolean emptyPass = true;
			for (MultiRef mr: this.multiRefs.values()) {
				if (mr.isDone())
					continue;
				emptyPass = false;
				eventWriter.add(XmlEventsPattern.XML_EVENTS_FACTORY.createStartElement(new QName("", "mr"), null, null));
				eventWriter.add(XmlEventsPattern.XML_EVENTS_FACTORY.createAttribute("id", "id" + mr.getId()));
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
	public XmlEventsPattern adaptPattern(XmlEventsPattern pattern, String accessorName) {
		if (pattern instanceof AbstractSimpleTypePattern) {
			return new ElementPattern(new QName("", accessorName), ValuePattern.INSTANCE);
		} else {
			return pattern;
		}
	}

}
