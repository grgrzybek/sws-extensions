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

package org.springframework.ws.jaxws;

import java.io.File;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;
import java.util.concurrent.TimeUnit;

import org.eclipse.jetty.server.Server;
import org.eclipse.jetty.servlet.ServletContextHandler;
import org.eclipse.jetty.servlet.ServletHolder;
import org.eclipse.jetty.util.resource.FileResource;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.springframework.web.context.WebApplicationContext;
import org.springframework.web.context.support.GenericWebApplicationContext;
import org.springframework.ws.transport.http.MessageDispatcherServlet;
import org.springframework.ws.transport.support.FreePortScanner;

/**
 * <p>Base class for managing Spring-WS based tests with embedded Jetty server</p>
 * 
 * @author Grzegorz Grzybek
 */
public class SwsIntegrationTests {

	private static Logger log = LoggerFactory.getLogger(SwsIntegrationTests.class.getName());

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
	public int startJettyWithCXFServlet(int port, String contextLocation, String servletMapping) {
		this.port = port == -1 ? FreePortScanner.getFreePort() : port;
		log.info("Listening at TCP:{}", this.port);
		this.jetty = new Server(this.port);
		this.jettyContext = new ServletContextHandler(this.jetty, "/");

		// parent, webcontext-wide Spring context
		ClassPathXmlApplicationContext parent = new ClassPathXmlApplicationContext(contextLocation, this.getClass());

		// parent, webcontext-wide Spring web context
		GenericWebApplicationContext wac = new GenericWebApplicationContext();
		wac.setParent(parent);
		wac.setServletContext(this.jettyContext.getServletContext());

		this.jettyContext.getServletContext().setAttribute(WebApplicationContext.ROOT_WEB_APPLICATION_CONTEXT_ATTRIBUTE, wac);
		wac.refresh();

		// SWS - configured with parent ClassPathXmlApplicationContext
		MessageDispatcherServlet sws = new MessageDispatcherServlet();
		sws.setContextConfigLocation("");
		ServletHolder servlet = new ServletHolder(sws);
		String base = SwsIntegrationTests.class.getPackage().getName().replace('.', '/');
		try {
			this.jettyContext.setBaseResource(new FileResource(new File("src/test/resources/" + base).toURI().toURL()));
			this.jettyContext.addServlet(servlet, servletMapping);
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
	public int startJettyWithCXFServlet(String contextLocation) {
		return this.startJettyWithCXFServlet(-1, contextLocation, "/sws/*");
	}

	/**
	 * <p></p>
	 *
	 * @author Grzegorz Grzybek
	 */
	public interface Callback {

		public void perform(int port);

	}

	/**
	 * @param contextName
	 * @param callback
	 */
	public <T> void isolatedSwsTest(final String contextName, final Callback callback) {
		// Server's context
		final int port = this.startJettyWithCXFServlet(contextName + "-context.xml");

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
