/*
 * Copyright 2013 Exence SA
 * Created: 6 mar 2013 11:54:14
 */

package org.springframework.ws.ext.bind.internal.stax;

import javax.xml.namespace.NamespaceContext;
import javax.xml.stream.XMLEventFactory;
import javax.xml.stream.XMLEventReader;
import javax.xml.stream.XMLEventWriter;
import javax.xml.stream.XMLStreamConstants;
import javax.xml.stream.XMLStreamException;
import javax.xml.stream.events.XMLEvent;

/**
 * <p></p>
 *
 * @author Grzegorz Grzybek
 */
public class IndentingXMLEventWriter implements XMLEventWriter {

	private static final XMLEventFactory factory = XMLEventFactory.newFactory();

	private XMLEventWriter wrappedWriter;
	private int depth = 0;

	/* Always true until we add END_ELEMENT - from then on new lines will be created after END_ELEMENT */
	private boolean newLineBeforeStartElement = true;
	private boolean newLineBeforeEndElement = false;

	private String indentationString = "\t";

	/**
	 * @param wrappedWriter
	 */
	public IndentingXMLEventWriter(XMLEventWriter wrappedWriter) {
		this.wrappedWriter = wrappedWriter;
	}

	/**
	 * @throws XMLStreamException
	 * @see javax.xml.stream.XMLEventWriter#close()
	 */
	@Override
	public void close() throws XMLStreamException {
		this.wrappedWriter.close();
	}

	/**
	 * @param event
	 * @throws XMLStreamException
	 * @see javax.xml.stream.XMLEventWriter#add(javax.xml.stream.events.XMLEvent)
	 */
	@Override
	public void add(XMLEvent event) throws XMLStreamException {
		switch (event.getEventType()) {
		case XMLStreamConstants.START_ELEMENT:
			if (this.newLineBeforeStartElement)
				this.wrappedWriter.add(factory.createCharacters("\n"));
			// end element tags will be in the same line, unless there will be comments or PIs
			this.newLineBeforeEndElement = false;
			possiblyIndent();
			this.wrappedWriter.add(event);
			this.depth++;
			break;
		case XMLStreamConstants.END_ELEMENT:
			this.depth--;
			this.newLineBeforeStartElement = false;
			if (this.newLineBeforeEndElement) {
				this.wrappedWriter.add(factory.createCharacters("\n"));
				possiblyIndent();
			}
			this.wrappedWriter.add(event);
			// always
			this.wrappedWriter.add(factory.createCharacters("\n"));
			break;
		case XMLStreamConstants.COMMENT:
		case XMLStreamConstants.PROCESSING_INSTRUCTION:
			this.newLineBeforeEndElement = true;
			this.wrappedWriter.add(event);
			break;
		default:
			this.wrappedWriter.add(event);
		}
	}

	/**
	 * Indent if necessary
	 * 
	 * @throws XMLStreamException
	 */
	private void possiblyIndent() throws XMLStreamException {
		if (this.depth > 0) {
			StringBuffer sb = new StringBuffer();
			for (int i = 0; i < this.depth; i++)
				sb.append(this.indentationString);
			this.wrappedWriter.add(factory.createCharacters(sb.toString()));
		}
	}

	/**
	 * @param reader
	 * @throws XMLStreamException
	 * @see javax.xml.stream.XMLEventWriter#add(javax.xml.stream.XMLEventReader)
	 */
	@Override
	public void add(XMLEventReader reader) throws XMLStreamException {
		this.wrappedWriter.add(reader);
	}

	/**
	 * @param uri
	 * @return
	 * @throws XMLStreamException
	 * @see javax.xml.stream.XMLEventWriter#getPrefix(java.lang.String)
	 */
	@Override
	public String getPrefix(String uri) throws XMLStreamException {
		return this.wrappedWriter.getPrefix(uri);
	}

	/**
	 * @param prefix
	 * @param uri
	 * @throws XMLStreamException
	 * @see javax.xml.stream.XMLEventWriter#setPrefix(java.lang.String, java.lang.String)
	 */
	@Override
	public void setPrefix(String prefix, String uri) throws XMLStreamException {
		this.wrappedWriter.setPrefix(prefix, uri);
	}

	/**
	 * @param uri
	 * @throws XMLStreamException
	 * @see javax.xml.stream.XMLEventWriter#setDefaultNamespace(java.lang.String)
	 */
	@Override
	public void setDefaultNamespace(String uri) throws XMLStreamException {
		this.wrappedWriter.setDefaultNamespace(uri);
	}

	/**
	 * @param context
	 * @throws XMLStreamException
	 * @see javax.xml.stream.XMLEventWriter#setNamespaceContext(javax.xml.namespace.NamespaceContext)
	 */
	@Override
	public void setNamespaceContext(NamespaceContext context) throws XMLStreamException {
		this.wrappedWriter.setNamespaceContext(context);
	}

	/**
	 * @return
	 * @see javax.xml.stream.XMLEventWriter#getNamespaceContext()
	 */
	@Override
	public NamespaceContext getNamespaceContext() {
		return this.wrappedWriter.getNamespaceContext();
	}

	/**
	 * @throws XMLStreamException
	 * @see javax.xml.stream.XMLEventWriter#flush()
	 */
	@Override
	public void flush() throws XMLStreamException {
		this.wrappedWriter.flush();
	}

	/**
	 * @param indentationString
	 */
	public void setIndentationString(String indentationString) {
		this.indentationString = indentationString;
	}

}
