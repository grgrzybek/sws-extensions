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

/**
 * JSR-181: Service Endpoint Interface may be referenced by Service Implementation Bean
 * and is used to separate implementation from contract definition.
 * It's however quite silly that JSR-181 doesn't says anything about <em>implementing</em>
 * a SEI - it only says about <em>referencing</em> it with {@link javax.jws.WebService#endpointInterface()}.
 */
package org.springframework.ws.jaxws.jsr181.sei;

