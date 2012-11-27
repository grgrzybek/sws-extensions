/*
 * Copyright 2005-2011 the original author or authors.
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

package org.springframework.ws.axis1;

import java.io.File;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;
import java.util.concurrent.TimeUnit;

import org.apache.axis.transport.http.AxisServlet;
import org.eclipse.jetty.server.Server;
import org.eclipse.jetty.servlet.ServletContextHandler;
import org.eclipse.jetty.servlet.ServletHolder;
import org.eclipse.jetty.util.resource.FileResource;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.springframework.web.context.WebApplicationContext;
import org.springframework.web.context.support.GenericWebApplicationContext;
import org.springframework.ws.transport.support.FreePortScanner;

/**
 * <p>Base class for managing Axis1 based tests with embedded Jetty server</p>
 * 
 * @author Grzegorz Grzybek
 */
public class Axis1IntegrationTests {

	protected static Logger log = LoggerFactory.getLogger(Axis1IntegrationTests.class.getName());

	protected Server jetty;

	protected ServletContextHandler jettyContext;

	private int port;

	/**
	 * Running test Jetty server with a specified TCP port
	 * 
	 * @param port
	 * @param contextLocation
	 * @param servletMapping
	 * @return
	 * @throws Exception
	 */
	public int startJettyWithAxis1Servlet(int port, String contextLocation, String servletMapping) {
		this.port = port == -1 ? FreePortScanner.getFreePort() : port;
		log.info("Listening at TCP:{}", this.port);
		this.jetty = new Server(this.port);
		this.jettyContext = new ServletContextHandler(this.jetty, "/");

		// parent, webcontext-wide Spring context
		ClassPathXmlApplicationContext parent = new ClassPathXmlApplicationContext(contextLocation, Axis1IntegrationTests.class);

		// parent, webcontext-wide Spring web context
		GenericWebApplicationContext wac = new GenericWebApplicationContext();
		wac.setParent(parent);
		wac.setServletContext(this.jettyContext.getServletContext());

		this.jettyContext.getServletContext().setAttribute(WebApplicationContext.ROOT_WEB_APPLICATION_CONTEXT_ATTRIBUTE, wac);
		wac.refresh();

		// Axis1 - configured with parent ClassPathXmlApplicationContext
		AxisServlet axis1 = new AxisServlet();
		ServletHolder servlet = new ServletHolder(axis1);
		servlet.setInitParameter("axis.servicesPath", "/axis1/");
		this.jettyContext.addServlet(servlet, servletMapping);
		try {
			String base = this.getClass().getPackage().getName().replace('.', '/');
			this.jettyContext.setBaseResource(new FileResource(new File("src/test/resources/" + base).toURI().toURL()));
			this.jetty.start();
		}
		catch (Exception e) {
			throw new RuntimeException(e.getMessage(), e);
		}

		return this.port;
	}

	/**
	 * Running test Jetty server with a random TCP port, location of Spring
	 * context and
	 * 
	 * @param contextLocation
	 * @param servletMapping
	 * @return
	 * @throws Exception
	 */
	public int startJettyWithAxis1Servlet(String contextLocation) {
		return this.startJettyWithAxis1Servlet(-1, contextLocation, "/axis1/*");
	}

	/**
	 * <p></p>
	 *
	 * @author Grzegorz Grzybek
	 */
	public interface Callback {

		public void perform(int port) throws Exception;

	}

	/**
	 * @param contextName
	 * @param callback
	 */
	public <T> void isolatedAxis1Test(final Callback callback) {
		// Server's context
		final int port = this.startJettyWithAxis1Servlet("DispatchTest-context.xml");

		ExecutorService executor = Executors.newSingleThreadExecutor();
		Future<?> result = executor.submit(new Runnable() {
			@Override
			public void run() {
				try {
					callback.perform(port);
				}
				catch (Exception e) {
					log.error(e.getMessage(), e);
					throw new RuntimeException(e.getMessage(), e);
				}
			}
		});
		try {
			result.get(1, TimeUnit.DAYS);
		}
		catch (Exception e) {
			throw new RuntimeException(e.getMessage(), e);
		}
	}

}
