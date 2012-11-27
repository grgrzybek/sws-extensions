
### "case 2" is an Axis1 testing scenario where:

1. We start from simple, unannotated Java class with a method accepting and returning complex Java classes using arrays and collections
2. We generate different WSDLs using org.apache.axis.wsdl.Java2WSDL (see [The Matrix](#matrix)) There are also `*_Helper` classes generated (they contain Axis1/JAX-RPC Metadata)
3. We put `*_Helper` classes (they don't compile) to `codefirst` package (we'll only compare them with appropriate classes generated later)
4. We won't use the generated `deploy.wsdd` files, because they contain references to initial classes without Axis1 customizations (`*_Helper`)
5. It turns out we only directly use WSDLs generated from Java2WSDL.
6. From these WSDLs we generate client stubs and server skeletons using org.apache.axis.wsdl.WSDL2Java - this is important, because the server endpoints won't run the initial class as an implementation - rather the generated one
7. After generating 24 combinations ([document, rpc, wrapped]X[literal, encoded]X[1.1, 1.2]X[wrapped arrays, plain arrays]), we compare it with Helpers and `deploy.wsdd` generated using Java2WSDL - they're the same (*arrays plain* version for literal, all versions for encoded)
8. We modify generated classes, so the default constructor will produce objects with real values (not nulls)
9. We create a WSDD deployment with all deploy.wsdd files merged. We use `deploy.wsdd` files from WSDL2Java, not from Java2WSDL.
10. We create requests with clients and send them to server deployed using generated deploy.wsdd (and configured {http://xml.apache.org/axis/wsdd/}service)
