/*
 * Copyright 2013 Exence SA
 * Created: 6 mar 2013 12:26:20
 */

package org.springframework.ws.ext.bind.internal.stax;

import java.io.StringWriter;

import javax.xml.namespace.QName;
import javax.xml.stream.XMLEventFactory;
import javax.xml.stream.XMLOutputFactory;

import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.TestWatcher;
import org.junit.runner.Description;

import static org.hamcrest.CoreMatchers.*;

import static org.junit.Assert.*;

/**
 * <p>Using: Woodstox</p>
 *
 * @author Grzegorz Grzybek
 */
public class IndentingXMLEventWriterTest {

	private XMLEventFactory EF = XMLEventFactory.newFactory();
	private IndentingXMLEventWriter writer;
	private StringWriter sw;

	@Rule
	public TestWatcher watchman = new TestWatcher() {
		@Override
		protected void starting(Description description) {
			System.out.println("=================== " + description.getMethodName() + " ===================");
		}
	};

	@Before
	public void before() throws Exception {
		this.sw = new StringWriter();
		this.writer = new IndentingXMLEventWriter(XMLOutputFactory.newFactory("com.ctc.wstx.stax.WstxOutputFactory", this.getClass().getClassLoader())
				.createXMLEventWriter(this.sw));
	}

	@Test
	public void simpleEvents() throws Exception {
		String xml =
				"<?xml version='1.0' encoding='UTF-8'?>\n" +
				"<a>\n" +
				"\t<a>test</a>\n" +
				"</a>\n";

		this.writer.add(this.EF.createStartDocument("UTF-8"));
		this.writer.add(this.EF.createStartElement(new QName("urn:test", "a"), null, null));
		this.writer.add(this.EF.createStartElement(new QName("urn:test", "a"), null, null));
		this.writer.add(this.EF.createCharacters("test"));
		this.writer.add(this.EF.createEndElement(new QName("urn:test", "a"), null));
		this.writer.add(this.EF.createEndElement(new QName("urn:test", "a"), null));
		this.writer.add(this.EF.createEndDocument());
		assertThat(this.sw.toString(), equalTo(xml));
		System.out.print(this.sw.toString());
	}

	@Test
	public void emptyElement() throws Exception {
		String xml =
				"<?xml version='1.0' encoding='UTF-8'?>\n" + 
				"<a>\n" +
				"--<a/>\n" +
				"</a>\n";

		this.writer.setIndentationString("--"); // !!!
		this.writer.add(this.EF.createStartDocument("UTF-8"));
		this.writer.add(this.EF.createStartElement(new QName("urn:test", "a"), null, null));
		this.writer.add(this.EF.createStartElement(new QName("urn:test", "a"), null, null));
		this.writer.add(this.EF.createEndElement(new QName("urn:test", "a"), null));
		this.writer.add(this.EF.createEndElement(new QName("urn:test", "a"), null));
		this.writer.add(this.EF.createEndDocument());
		assertThat(this.sw.toString(), equalTo(xml));
		System.out.print(this.sw.toString());
	}
	
	@Test
	public void twoElements() throws Exception {
		String xml =
				"<?xml version='1.0' encoding='UTF-8'?>\n" + 
						"<a>\n" +
						"--<a1>x</a1>\n" +
						"--<a2/>\n" +
						"</a>\n";
		
		this.writer.setIndentationString("--"); // !!!
		this.writer.add(this.EF.createStartDocument("UTF-8"));
		this.writer.add(this.EF.createStartElement(new QName("urn:test", "a"), null, null));
		this.writer.add(this.EF.createStartElement(new QName("urn:test", "a1"), null, null));
		this.writer.add(this.EF.createCharacters("x"));
		this.writer.add(this.EF.createEndElement(new QName("urn:test", "a1"), null));
		this.writer.add(this.EF.createStartElement(new QName("urn:test", "a2"), null, null));
		this.writer.add(this.EF.createEndElement(new QName("urn:test", "a2"), null));
		this.writer.add(this.EF.createEndElement(new QName("urn:test", "a"), null));
		this.writer.add(this.EF.createEndDocument());
		assertThat(this.sw.toString(), equalTo(xml));
		System.out.print(this.sw.toString());
	}
	
	@Test
	public void threeLevels() throws Exception {
		String xml =
				"<?xml version='1.0' encoding='UTF-8'?>\n" + 
						"<a>\n" +
						" · <b1/>\n" +
						" · <b1 id=\"#1\">\n" +
						" ·  · <c/>\n" +
						" · </b1>\n" +
						"</a>\n";
		
		this.writer.setIndentationString(" · ");
		this.writer.add(this.EF.createStartDocument("UTF-8"));
		this.writer.add(this.EF.createStartElement(new QName("urn:test", "a"), null, null));
		this.writer.add(this.EF.createStartElement(new QName("urn:test", "b1"), null, null));
		this.writer.add(this.EF.createEndElement(new QName("urn:test", "b1"), null));
		this.writer.add(this.EF.createStartElement(new QName("urn:test", "b1"), null, null));
		this.writer.add(this.EF.createAttribute(new QName("urn:test", "id"), "#1"));
		this.writer.add(this.EF.createStartElement(new QName("urn:test", "c"), null, null));
		this.writer.add(this.EF.createEndElement(new QName("urn:test", "c"), null));
		this.writer.add(this.EF.createEndElement(new QName("urn:test", "b1"), null));
		this.writer.add(this.EF.createEndElement(new QName("urn:test", "a"), null));
		this.writer.add(this.EF.createEndDocument());
		assertThat(this.sw.toString(), equalTo(xml));
		System.out.print(this.sw.toString());
	}

	@Test
	public void aMixOfEvents() throws Exception {
		String xml =
				"<?xml version='1.0' encoding='UTF-8'?>\n" + 
				"	   <!--comment-->\n" + 
				"	   <?php phpinfo();?>\n" + 
				"<a><?php phpinfo();?>\n" + 
				"	   <!--comment-->\n" + 
				"	<b>	   <?php phpinfo();?>\n" + 
				"		<c><?php phpinfo();?>\n" + 
				"		</c>\n" + 
				"<!--comment-->\n" + 
				"	</b>\n" + 
				"	   <?php phpinfo();?>\n" + 
				"</a>\n" + 
				"	   <?php phpinfo();?>\n" + 
				"	   <!--comment-->\n" + 
				"	   <?php phpinfo();?>\n" + 
				"	   ";

		this.writer.add(this.EF.createStartDocument("UTF-8"));
		this.writer.add(this.EF.createCharacters("\t   "));
		this.writer.add(this.EF.createComment("comment"));
		this.writer.add(this.EF.createCharacters("\t   "));
		this.writer.add(this.EF.createProcessingInstruction("php", "phpinfo();"));
		this.writer.add(this.EF.createStartElement(new QName("urn:test", "a"), null, null));
		this.writer.add(this.EF.createProcessingInstruction("php", "phpinfo();"));
		this.writer.add(this.EF.createCharacters("\t   "));
		this.writer.add(this.EF.createComment("comment"));
		this.writer.add(this.EF.createStartElement(new QName("urn:test", "b"), null, null));
		this.writer.add(this.EF.createCharacters("\t   "));
		this.writer.add(this.EF.createProcessingInstruction("php", "phpinfo();"));
		this.writer.add(this.EF.createStartElement(new QName("urn:test", "c"), null, null));
		this.writer.add(this.EF.createProcessingInstruction("php", "phpinfo();"));
		this.writer.add(this.EF.createEndElement(new QName("urn:test", "c"), null));
		this.writer.add(this.EF.createComment("comment"));
		this.writer.add(this.EF.createEndElement(new QName("urn:test", "b"), null));
		this.writer.add(this.EF.createCharacters("\t   "));
		this.writer.add(this.EF.createProcessingInstruction("php", "phpinfo();"));
		this.writer.add(this.EF.createEndElement(new QName("urn:test", "a"), null));
		this.writer.add(this.EF.createCharacters("\t   "));
		this.writer.add(this.EF.createProcessingInstruction("php", "phpinfo();"));
		this.writer.add(this.EF.createCharacters("\t   "));
		this.writer.add(this.EF.createComment("comment"));
		this.writer.add(this.EF.createCharacters("\t   "));
		this.writer.add(this.EF.createProcessingInstruction("php", "phpinfo();"));
		this.writer.add(this.EF.createCharacters("\t   "));
		this.writer.add(this.EF.createEndDocument());
		System.out.println(this.sw.toString());
		assertThat(this.sw.toString(), equalTo(xml));
	}

}
