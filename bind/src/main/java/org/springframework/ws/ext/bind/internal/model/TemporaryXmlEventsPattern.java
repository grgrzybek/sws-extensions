/*
 * Copyright 2013 Exence SA
 * Created: 8 mar 2013 13:52:51
 */

package org.springframework.ws.ext.bind.internal.model;

import javax.xml.stream.XMLEventReader;
import javax.xml.stream.XMLEventWriter;
import javax.xml.stream.XMLStreamException;

import org.springframework.ws.ext.bind.internal.MarshallingContext;

/**
 * <p>An {@link XmlEventsPattern} acting as a temporary mapping of class to a series of XML events. Needed when two classes
 * have properties of one another's class.</p>
 * <p>After the final determination of mapped pattern it will be injected into this pattern. MultiRef Encodings (more likely to have
 * interdependent properties) will <b>not</b> result in replaying this pattern, because multiRefs will be used since first occurence!</p>
 *
 * @author Grzegorz Grzybek
 */
public class TemporaryXmlEventsPattern implements XmlEventsPattern {

	private ContentModelPattern realMapping;

	/* (non-Javadoc)
	 * @see org.springframework.ws.ext.bind.internal.model.XmlEventsPattern#replay(java.lang.Object, javax.xml.stream.XMLEventWriter, org.springframework.ws.ext.bind.internal.MarshallingContext)
	 */
	@Override
	public void replay(Object object, XMLEventWriter eventWriter, MarshallingContext context) throws XMLStreamException {
		this.realMapping.replay(object, eventWriter, context);
	}

	/* (non-Javadoc)
	 * @see org.springframework.ws.ext.bind.internal.model.XmlEventsPattern#consume(javax.xml.stream.XMLEventReader)
	 */
	@Override
	public Object consume(XMLEventReader eventReader) throws XMLStreamException {
		return this.realMapping.consume(eventReader);
	}

	/* (non-Javadoc)
	 * @see org.springframework.ws.ext.bind.internal.model.XmlEventsPattern#isSimpleType()
	 */
	@Override
	public boolean isSimpleType() {
		return this.realMapping.isSimpleType();
	}

	/**
	 * @param mapping
	 */
	public void setRealPattern(ContentModelPattern mapping) {
		this.realMapping = mapping;
	}

}
