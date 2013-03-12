
For ASMX (Add Web Reference), I've used WSDLs from /spring-ws-extensions/jws/src/test/resources/org/springframework/ws/axis1/case[12]/codefirst/*.

 - wsdl.exe can't process "wrapped" encoded style, it ends with:
      Error: The element attribute is not allowed on encoded message parts. The erroneous part is named 'parameters' in message 'twoParamsRequest'.
   (and it's quite right - encoded should reference types, not elements).
 - type mapping 1.1 (soapenc types) and 1.2 (xsd types) are not distinguishable
