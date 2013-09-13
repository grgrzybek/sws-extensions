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

package org.springframework.ws.cxf;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;
import java.util.concurrent.TimeUnit;
import java.util.logging.Handler;
import java.util.logging.LogManager;

import javax.xml.ws.BindingProvider;

import org.apache.cxf.BusFactory;
import org.apache.cxf.transport.servlet.CXFServlet;
import org.eclipse.jetty.server.Server;
import org.eclipse.jetty.servlet.ServletContextHandler;
import org.eclipse.jetty.servlet.ServletHolder;
import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.slf4j.bridge.SLF4JBridgeHandler;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.springframework.web.context.WebApplicationContext;
import org.springframework.web.context.support.GenericWebApplicationContext;
import org.springframework.ws.transport.support.FreePortScanner;

/**
 * <p>Base class for managing CXF based tests with embedded Jetty server</p>
 * 
 * @author Grzegorz Grzybek
 */
public class CxfIntegrationTests {

	private static Logger log = LoggerFactory.getLogger(CxfIntegrationTests.class.getName());

	protected Server jetty;

	protected ServletContextHandler jettyContext;

	private int port;

	@BeforeClass
	public static void beforeClass() {
		for (Handler h : LogManager.getLogManager().getLogger("").getHandlers())
			LogManager.getLogManager().getLogger("").removeHandler(h);

		SLF4JBridgeHandler.install();
	}

	@AfterClass
	public static void afterClass() {
		SLF4JBridgeHandler.uninstall();
	}

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

		// CXF - configured with parent ClassPathXmlApplicationContext
		CXFServlet cxf = new CXFServlet();
		ServletHolder servlet = new ServletHolder(cxf);
		this.jettyContext.addServlet(servlet, servletMapping);
		try {
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
		return this.startJettyWithCXFServlet(-1, contextLocation, "/cxf/*");
	}

	/**
	 * <p></p>
	 *
	 * @author Grzegorz Grzybek
	 */
	public interface Callback<T> {

		public void perform(T ctx);

	}

	/**
	 * @param contextName
	 * @param callback
	 */
	public <T> void isolatedCxfTest(final String contextName, final Callback<T> callback, final Class<T> interfaceClass) {
		// Server's context
		final int port = this.startJettyWithCXFServlet(contextName + "-context.xml");

		ExecutorService executor = Executors.newSingleThreadExecutor();
		Future<?> result = executor.submit(new Runnable() {
			@Override
			public void run() {
				try {
					BusFactory.setDefaultBus(null);

					// Client's context
					ClassPathXmlApplicationContext testContext = new ClassPathXmlApplicationContext(contextName + "Client-context.xml", callback.getClass());

					T client = testContext.getBean(interfaceClass);
					String address = (String) ((BindingProvider) client).getRequestContext().get(BindingProvider.ENDPOINT_ADDRESS_PROPERTY);
					((BindingProvider) client).getRequestContext().put(BindingProvider.ENDPOINT_ADDRESS_PROPERTY, address.replace("localhost", "localhost:" + port));

					callback.perform(client);
				}
				catch (Exception e) {
					log.error(e.getMessage());
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
