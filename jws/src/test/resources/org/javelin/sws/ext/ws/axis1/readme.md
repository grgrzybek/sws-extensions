
### "case X" is an Axis1 testing scenario where:

1. We start from simple, unannotated Java class with few methods accepting and returning some types
2. We generate different WSDLs using org.apache.axis.wsdl.Java2WSDL (see [The Matrix](#matrix)) There are also `*_Helper` classes generated (they contain Axis1/JAX-RPC Metadata)
3. We put `*_Helper` classes (they don't compile) to `codefirst` package (we'll only compare them with appropriate classes generated later)
4. We won't use the generated `deploy.wsdd` files, because they contain references to initial classes without Axis1 customizations (`*_Helper`)
5. It turns out we only directly use WSDLs generated from Java2WSDL.
6. From these WSDLs we generate client stubs and server skeletons using org.apache.axis.wsdl.WSDL2Java - this is important, because the server endpoints won't run the initial class as an implementation - rather the generated one
7. After generating 24 combinations ([document, rpc, wrapped]X[literal, encoded]X[1.1, 1.2]X[wrapped arrays, plain arrays]), we compare it with Helpers and `deploy.wsdd` generated using Java2WSDL - they're the same (*arrays plain* version for literal, all versions for encoded)
8. We modify generated classes, so the default constructor will produce objects with real values (not nulls)
9. We create a WSDD deployment with all deploy.wsdd files merged. We use `deploy.wsdd` files from WSDL2Java, not from Java2WSDL.
10. We create requests with clients and send them to server deployed using generated deploy.wsdd (and configured {http://xml.apache.org/axis/wsdd/}service)

Results:

* The class is `org.springframework.ws.axis1.caseX.codefirst.EchoEndpoint`in `src/test/java`
* WSDLs and `(un)deploy.wsdd` files are generated to `src/test/resources/org/springframework/ws/axis1/caseX/codefirst` using all (*the matrix*) combinations of parameters
* For *literal* use, there's no difference between 1.1 and 1.2 type mapping version - they both don't use `soapenc` types
* For "case 1" there's no difference between *plain* and *wrapped* arrays
* For *encoded* use, there's no difference between *plain* and *wrapped* arrays
* both for type mapping version = `1.1` and `1.2`, `<parameter name="typeMappingVersion" value="1.2"/>` is generated
* for WRAPPED/ENCODED, additional classes are generated (wrappers)
* type mapping version `1.2` (soap encoding) is the default (although org.apache.axis.wsdl.Java2WSDL doc says different)
* generated WSDLs are used to generate *client stubs* and *server skeletons* in `org.springframework.ws.axis1.caseX.contractfirst` packages in `src/test/java`
* `deploy.wsdd` files, used to configure runtime clients have additional configuration added (LogHandler, sendXsiTypes, sendMultiRefs)
* after running the test, generated SOAP messages are stored in `src/test/resources/org/springframework/ws/axis1/caseX/contractfirst/soap_11/[dont]sendXsiTypes`
* for *literal* use, xsi types are never sent
* for *encoded* use, xsi types are always sent (at least for complex Types. operation parameters of simple types respect sendXsiTypes option)

### <a id="matrix"></a>The Matrix ###

Our goal is to see how Axis1 generates different kind of artifacts:

* WSDL: from Java class and combination of Java2WSDL parameters (-y, -u, -T)
* Java classes: and Java configuration (org.apache.axis.description.OperationDesc) from WSDL (-W, -T, -H, -w)
* SOAP messages: from Java classes and client-config.wsdd (sendXsiTypes, sendMultiRefs, ...)

<table>
	<tr><th>-y</th><th>-u</th><th>-T</th><th>info</th></tr>
	<tr><td>DOCUMENT</td><td>LITERAL</td><td>1.1</td><td></td></tr>
	<tr><td>DOCUMENT</td><td>LITERAL</td><td>1.2</td><td></td></tr>
	<tr><td>DOCUMENT</td><td>ENCODED</td><td>1.1</td><td></td></tr>
	<tr><td>DOCUMENT</td><td>ENCODED</td><td>1.2</td><td></td></tr>
	<tr><td>RPC</td><td>LITERAL</td><td>1.1</td><td></td></tr>
	<tr><td>RPC</td><td>LITERAL</td><td>1.2</td><td></td></tr>
	<tr><td>RPC</td><td>ENCODED</td><td>1.1</td><td></td></tr>
	<tr><td>RPC</td><td>ENCODED</td><td>1.2</td><td></td></tr>
	<tr><td>WRAPPED</td><td>LITERAL</td><td>1.1</td><td></td></tr>
	<tr><td>WRAPPED</td><td>LITERAL</td><td>1.2</td><td></td></tr>
	<tr><td>WRAPPED</td><td>ENCODED</td><td>1.1</td><td></td></tr>
	<tr><td>WRAPPED</td><td>ENCODED</td><td>1.2</td><td></td></tr>
</table>
