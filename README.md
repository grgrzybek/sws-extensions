# Spring-WS JAX-WS #

This project's goal is to include JSR-181, JAX-RPC and JAX-WS2 programming
model in Spring-WS based WebServices.


## Soap Encoding ##

We can now do this:

    <?xml version='1.0' encoding='UTF-8'?>
    <r:root-wrapper-for-multirefs xmlns:r="urn:test:1">
      <r:root xmlns:r="urn:test" href="#id0"/>
      <mr id="id0">
        <c3 xmlns="http://context3.bind.ext.ws.springframework.org/" href="#id1"/>
      </mr>
      <mr id="id1">
        <c2 xmlns="http://context3.bind.ext.ws.springframework.org/" href="#id0"/>
      </mr>
    </r:root-wrapper-for-multirefs>


# Subprojects #

* Spring-Ws-Policy
* Spring-Ws-ReliableMessaging
* Spring-Ws-Gateway
* Spring-Ws-VTD
