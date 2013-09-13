
all combinations of flags:
 - TM 1.1, TM 1.2
 - arrays plain / arrays wrapped

generates the same classes with two differences:

 - JirasoapserviceV2SoapBindingStub.java: ((org.apache.axis.client.Service)super.service).setTypeMappingVersion("1.2");
 - deploy.wsdd: <parameter name="typeMappingVersion" value="1.2"/>
