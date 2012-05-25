/*
 * Copyright 2005-2012 the original author or authors.
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

package org.springframework.ws.jaxws.server.endpoint.adapter.method;

import javax.jws.soap.SOAPBinding.Use;
import javax.xml.namespace.QName;
import javax.xml.stream.XMLStreamConstants;
import javax.xml.stream.XMLStreamException;
import javax.xml.stream.XMLStreamReader;
import javax.xml.stream.XMLStreamWriter;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.core.MethodParameter;
import org.springframework.oxm.Marshaller;
import org.springframework.oxm.Unmarshaller;
import org.springframework.util.StringUtils;
import org.springframework.util.xml.StaxUtils;
import org.springframework.ws.WebServiceMessage;
import org.springframework.ws.context.MessageContext;
import org.springframework.ws.jaxws.server.endpoint.support.NamespaceUtils;
import org.springframework.ws.server.endpoint.adapter.method.MarshallingPayloadMethodProcessor;
import org.springframework.ws.server.endpoint.adapter.method.MethodArgumentResolver;
import org.springframework.ws.server.endpoint.adapter.method.MethodReturnValueHandler;
import org.springframework.ws.stream.StreamingPayload;
import org.springframework.ws.stream.StreamingWebServiceMessage;

/**
 * <p>It's almost like {@link MarshallingPayloadMethodProcessor}, but it should use more than one {@link Marshaller}/{@link Unmarshaller} in case of
 * different (SOAP) encodings.</p>
 * <p>When {@link MethodParameter} relates to endpoints/endpoint methods using {@link Use#ENCODED} <i>use</i>, multi-ref aware marshaller/unmarshaller
 * should be used. In case of {@link Use#LITERAL} <i>use</i> we could delegate to other marshaller (e.g., castor) (I think so).</p>
 *
 * @author Grzegorz Grzybek
 */
public class JaxWsPayloadMethodProcessor implements MethodArgumentResolver, MethodReturnValueHandler {

	private static Logger log = LoggerFactory.getLogger(JaxWsPayloadMethodProcessor.class.getName());

	/* (non-Javadoc)
	 * @see org.springframework.ws.server.endpoint.adapter.method.MethodArgumentResolver#supportsParameter(org.springframework.core.MethodParameter)
	 */
	@Override
	public boolean supportsParameter(MethodParameter parameter) {
		return true;
	}

	/* (non-Javadoc)
	 * @see org.springframework.ws.server.endpoint.adapter.method.MethodArgumentResolver#resolveArgument(org.springframework.ws.context.MessageContext, org.springframework.core.MethodParameter)
	 */
	@Override
	public Object resolveArgument(MessageContext messageContext, MethodParameter parameter) throws Exception {
		// it's not as easy as in
		// o.s.w.s.e.a.method.MarshallingPayloadMethodProcessor.resolveArgument(MessageContext,
		// MethodParameter)
		// here we're invoked several times (according to method's parameter
		// count)
		// maybe we could do it once and store result in messageContext?
		// here's the place to perform something similar to Axis1's
		// deserialization (DeserializationContext) of StAX events (or SAX
		// source).
		String val = null;
		if (StaxUtils.isStaxSource(messageContext.getRequest().getPayloadSource())) {
			XMLStreamReader reader = StaxUtils.getXMLStreamReader(messageContext.getRequest().getPayloadSource());
			while (reader.hasNext()) {
				int ev = reader.next();
				if (ev == XMLStreamConstants.CHARACTERS) {
					val = reader.getText();
					if (StringUtils.hasText(val))
						break;
				}
			}
		}
		return val;
	}

	/* (non-Javadoc)
	 * @see org.springframework.ws.server.endpoint.adapter.method.MethodReturnValueHandler#supportsReturnType(org.springframework.core.MethodParameter)
	 */
	@Override
	public boolean supportsReturnType(MethodParameter returnType) {
		return true;
	}

	/* (non-Javadoc)
	 * @see org.springframework.ws.server.endpoint.adapter.method.MethodReturnValueHandler#handleReturnValue(org.springframework.ws.context.MessageContext, org.springframework.core.MethodParameter, java.lang.Object)
	 */
	@Override
	public void handleReturnValue(MessageContext messageContext, final MethodParameter returnType, final Object returnValue) throws Exception {
		log.info("return value: {}", returnValue);
		WebServiceMessage response = messageContext.getResponse();
		if (response instanceof StreamingWebServiceMessage) {
			// Axiom message is streaming
			StreamingWebServiceMessage streamingResponse = (StreamingWebServiceMessage) response;

			streamingResponse.setStreamingPayload(new StreamingPayload() {
				@Override
				public void writeTo(XMLStreamWriter streamWriter) throws XMLStreamException {
					streamWriter.writeStartElement("x", returnType.getMethod().getName() + "Result",
							NamespaceUtils.packageNameToNamespace(returnType.getDeclaringClass().getPackage()));
					streamWriter.writeNamespace("x", NamespaceUtils.packageNameToNamespace(returnType.getDeclaringClass().getPackage()));
					streamWriter.writeCharacters((String) returnValue);
					streamWriter.writeEndElement();
				}

				@Override
				public QName getName() {
					return new QName(NamespaceUtils.packageNameToNamespace(returnType.getDeclaringClass().getPackage()), returnType.getMethod().getName() + "Result");
				}
			});
		}
	}

}
