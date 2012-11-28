
### "case 1" is an Axis1 testing scenario where:

1. We start from simple, unannotated Java class with two methods accepting and returning Strings
2. We generate different WSDLs using org.apache.axis.wsdl.Java2WSDL (see [The Matrix](#matrix))
3. From these WSDLs we generate client stubs using org.apache.axis.wsdl.WSDL2Java
4. We create a WSDD deployment with all deploy.wsdd files merged
5. We create requests with clients and send them to server deployed using generated deploy.wsdd (and configured {http://xml.apache.org/axis/wsdd/}service)

Information:

* The class is `org.springframework.ws.axis1.case1.codefirst.EchoEndpoint`in `src/test/java`
* WSDLs and `(un)deploy.wsdd` files are generated to `src/test/resources/org/springframework/ws/axis1/case1/codefirst` using all (*the matrix*) combinations of parameters
* For *literal* use, there's no difference between 1.1 and 1.2 type mapping version - they both don't use `soapenc` types
* For "case 1" there's no difference between *plain* and *wrapped* arrays
* both for type mapping version = `1.1` and `1.2`, `<parameter name="typeMappingVersion" value="1.2"/>` is generated
* for WRAPPED/ENCODED, additional classes are generated (wrappers)
* type mapping version `1.2` (soap encoding) is the default (although org.apache.axis.wsdl.Java2WSDL doc says different)
* generated WSDLs are used to generate *client stubs* and *server skeletons* in `org.springframework.ws.axis1.case1.contractfirst` packages in `src/test/java`
* `deploy.wsdd` files, used to configure runtime clients have additional configuration added (LogHandler, sendXsiTypes, sendMultiRefs)
* after running the test, generated SOAP messages are stored in `src/test/resources/org/springframework/ws/axis1/case1/contractfirst/soap_11`

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
