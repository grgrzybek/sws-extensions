
/**
 * <p>Package modeled after "org.apache.axis.description". The classes here gather all annotation (Spring-WS, JSR-181) configuration and enclose it
 * to use at runtime. This information is used by client proxies to map Java invocations (method name, parameters) to Web Services invocation
 * (style, use, QNames, RPC params, ...)</p>
 * <p>Axis1 as JAX-RPC implementation uses its own marshalling framework, so its "description" package has more responsibilities. We can delegate:<ul>
 * <li>type mapping to pluggable marshallers</li>
 * <li>type discover to Spring core</li>
 * <li>service description to Spring-WS core</li>
 * </ul></p>
 * <p>The source of description data are JSR181 annotations. (For Axis1 it is EngineConfiguration - WSDD file).</p>
 */
package org.springframework.ws.jaxws.client.core.description;

