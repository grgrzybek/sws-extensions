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

import static org.hamcrest.CoreMatchers.*;
import static org.junit.Assert.*;

import java.io.StringWriter;
import java.util.Map;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlNsForm;
import javax.xml.namespace.QName;
import javax.xml.stream.XMLEventFactory;
import javax.xml.stream.XMLEventWriter;
import javax.xml.stream.XMLOutputFactory;
import javax.xml.stream.XMLStreamException;

import org.apache.ws.commons.schema.constants.Constants;
import org.javelin.sws.ext.bind.SweJaxbConstants;
import org.javelin.sws.ext.bind.SweJaxbContextFactory;
import org.javelin.sws.ext.bind.TypedPatternRegistry;
import org.javelin.sws.ext.bind.internal.metadata.PropertyCallback;
import org.javelin.sws.ext.bind.internal.model.context1.MyDate;
import org.javelin.sws.ext.bind.internal.model.context1.MyTime;
import org.joda.time.DateTime;
import org.joda.time.DateTimeZone;
import org.junit.Before;
import org.junit.Test;
import org.springframework.test.util.ReflectionTestUtils;

/**
 * <p></p>
 *
 * @author Grzegorz Grzybek
 */
public class AnalyzeSimpleTypesTest {

	private XMLEventWriter writer;

	private XMLEventFactory eventFactory;

	private StringWriter output;

	@Before
	public void before() throws Exception {
		this.output = new StringWriter();
		this.eventFactory = XMLEventFactory.newFactory();
		this.writer = XMLOutputFactory.newFactory().createXMLEventWriter(this.output);
		this.writer.add(this.eventFactory.createStartElement(new QName("", "v"), null, null));
	}

	@Test
	public void analyzeBuiltInDatatype() throws Exception {
		TypedPatternRegistry context = (TypedPatternRegistry) SweJaxbContextFactory.createContext("", null);

		PropertyCallback<String> pc = new PropertyCallback<String>(context, String.class, SweJaxbConstants.XSD_STRING, XmlAccessType.NONE, XmlNsForm.QUALIFIED,
				XmlNsForm.UNQUALIFIED);
		TypedPattern<String> pattern = pc.analyze();
		assertTrue(pattern.isSimpleType());
		assertThat(pattern.getJavaType(), equalTo(String.class));
		assertThat(pattern.getSchemaType(), equalTo(new QName(Constants.URI_2001_SCHEMA_XSD, "string")));

		@SuppressWarnings("unchecked")
		Map<Class<?>, TypedPattern<?>> patterns = (Map<Class<?>, TypedPattern<?>>) ReflectionTestUtils.getField(context, "patterns");
		TypedPattern<?> pattern2 = patterns.get(String.class);

		assertSame("Primitive and built-in types should be analyzed only at context creation time.", pattern, pattern2);
	}

	@Test
	public void analyzeXsDateTime() throws Exception {
		TypedPatternRegistry context = (TypedPatternRegistry) SweJaxbContextFactory.createContext("", null);
		TypedPattern<DateTime> pattern = context.determineAndCacheXmlPattern(DateTime.class);
		assertNotNull(pattern);
		assertThat(pattern.getJavaType(), equalTo(DateTime.class));
		assertThat(pattern.getSchemaType(), equalTo(new QName(Constants.URI_2001_SCHEMA_XSD, "dateTime")));

		pattern.replayValue(new DateTime().withDate(2000, 1, 1).withTime(10, 20, 30, 40).withZone(DateTimeZone.forID("CET")), this.writer, null);
		assertThat(this.simpleResult(), equalTo("2000-01-01T10:20:30.040+01:00"));

		this.before();
		pattern.replayValue(new DateTime().withDate(2000, 1, 1).withTime(10, 20, 30, 0).withZone(DateTimeZone.forID("CET")), this.writer, null);
		assertThat(this.simpleResult(), equalTo("2000-01-01T10:20:30+01:00"));

		this.before();
		pattern.replayValue(new DateTime().withDate(2000, 1, 1).withTime(10, 20, 30, 433).withZone(DateTimeZone.UTC), this.writer, null);
		assertThat(this.simpleResult(), equalTo("2000-01-01T09:20:30.433Z"));

		this.before();
		pattern.replayValue(new DateTime().withDate(2000, 1, 1).withTime(10, 20, 30, 0).withZone(DateTimeZone.UTC), this.writer, null);
		assertThat(this.simpleResult(), equalTo("2000-01-01T09:20:30Z"));
	}

	@Test
	public void analyzeXsDate() throws Exception {
		TypedPatternRegistry context = (TypedPatternRegistry) SweJaxbContextFactory.createContext("org.javelin.sws.ext.bind.internal.model.context1", null);
		TypedPattern<MyDate> pattern = context.determineAndCacheXmlPattern(MyDate.class);

		pattern.replayValue(new MyDate(new DateTime().withDate(2000, 1, 1).withTime(10, 20, 30, 40).withZone(DateTimeZone.forID("CET"))), this.writer, null);
		assertThat(this.simpleResult(), equalTo("2000-01-01+01:00"));

		this.before();
		pattern.replayValue(new MyDate(new DateTime().withDate(2000, 1, 1).withTime(10, 20, 30, 40).withZone(DateTimeZone.UTC)), this.writer, null);
		assertThat(this.simpleResult(), equalTo("2000-01-01Z"));
	}

	@Test
	public void analyzeXsTime() throws Exception {
		TypedPatternRegistry context = (TypedPatternRegistry) SweJaxbContextFactory.createContext("org.javelin.sws.ext.bind.internal.model.context1", null);
		TypedPattern<MyTime> pattern = context.determineAndCacheXmlPattern(MyTime.class);

		pattern.replayValue(new MyTime(new DateTime().withDate(2000, 1, 1).withTime(10, 20, 30, 40).withZone(DateTimeZone.forID("CET"))), this.writer, null);
		assertThat(this.simpleResult(), equalTo("10:20:30.040"));

		this.before();
		pattern.replayValue(new MyTime(new DateTime().withDate(2000, 1, 1).withTime(10, 20, 30, 0).withZone(DateTimeZone.UTC)), this.writer, null);
		assertThat(this.simpleResult(), equalTo("09:20:30"));
	}

	/**
	 * @return
	 */
	private String simpleResult() {
		this.flush();
		String result = this.output.toString();
		return result.substring(3, result.length() - 4);
	}

	/**
	 * 
	 */
	private void flush() {
		try {
			this.writer.add(this.eventFactory.createEndElement(new QName("", "v"), null));
			this.writer.close();
		}
		catch (XMLStreamException e) {
			throw new RuntimeException(e.getMessage(), e);
		}
	}

}
