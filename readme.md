## Spring Security SAML example

This project demonstrates both IDP initiated and SP initiated SSO flows.

### Modules 

#### Identity-Provider
- Uses Spring Boot 2 identity provider (https://github.com/spring-attic/spring-security-saml/tree/develop-3.0)
- It contains a basic identity provider application with the ability to define custom user attributes. This test IDP can
  be used to test any local SAML service provider implementation.
- Requires JDK11

#### Service-Provider 1
- Uses Spring Boot 2 service provider (https://github.com/spring-attic/spring-security-saml/tree/develop-3.0)
- This is a basic service provider with custom IDP select page and a basic home page.
- Requires JDK11

#### Service-Provider-SB3 - import this into IDE separately as it uses JDK-17+
- Uses Spring Boot 3 service provider with spring-security-saml2 
-  https://docs.spring.io/spring-security/reference/servlet/saml2/login/overview.html#servlet-saml2login-minimaldependencies
- This is a basic service provider with custom IDP select page and a basic home page.
- Requires JDK17+

## How to Test

- All module are Spring Boot applications. Run the applications and open either SP and IDP to initiate the
  authentication. Watch out for the redirection being performed by SAML.

    - http://localhost:8081/this-is-idp

    - http://localhost:8082/this-is-sp

    - http://localhost:8083/this-is-sp2
 
## Look at UserUtils class to generate custom SAML attributes

- Usernames: user002@email, user003@email
- password: pass

## generate a private key with the correct length

openssl genrsa -out pk.key 1024

# generate corresponding public key

openssl pkcs8 -topk8 -inform pem -in pk.key -outform pem -nocrypt -out pk.pem

# optional: create a self-signed certificate

openssl req -new -x509 -key pk.pem -out cert.pem -days 360


# Sample Requests:

### Note these are captured using breakpoints at `org.springframework.security.saml.provider.AbstractHostedProviderService#fromXml` in Identity-Provider module

### Auth request from SP

```xml
<?xml version="1.0" encoding="UTF-8"?>
<saml2p:AuthnRequest xmlns:saml2p="urn:oasis:names:tc:SAML:2.0:protocol"
                     AssertionConsumerServiceURL="http://localhost:8083/this-is-sp2/saml/sso/second-service-provider"
                     Destination="http://localhost:8081/this-is-idp/saml/idp/SSO/alias/my-identity-provider-app"
                     ForceAuthn="false" ID="ARQa06118f-16d5-404b-b083-3aca21c29988" IsPassive="false"
                     IssueInstant="2025-01-11T21:04:10.864Z"
                     ProtocolBinding="urn:oasis:names:tc:SAML:2.0:bindings:HTTP-POST" Version="2.0">
    <saml2:Issuer xmlns:saml2="urn:oasis:names:tc:SAML:2.0:assertion">second-service-provider</saml2:Issuer>
</saml2p:AuthnRequest>
```



### Login Response from IDP

```xml
<?xml version="1.0" encoding="UTF-8"?>
<saml2p:Response xmlns:saml2p="urn:oasis:names:tc:SAML:2.0:protocol"
                 Destination="http://localhost:8083/this-is-sp2/saml/sso/second-service-provider"
                 ID="RPe859c5a1-2f30-4a19-a416-e4b7281f6fe5" InResponseTo="ARQc681f84-9cab-44cc-8476-3b0c273d5a28"
                 IssueInstant="2025-01-11T21:09:45.667Z" Version="2.0">
    <saml2:Issuer xmlns:saml2="urn:oasis:names:tc:SAML:2.0:assertion">my-identity-provider-app</saml2:Issuer>
    <ds:Signature xmlns:ds="http://www.w3.org/2000/09/xmldsig#">
        <ds:SignedInfo>
            <ds:CanonicalizationMethod Algorithm="http://www.w3.org/2001/10/xml-exc-c14n#"/>
            <ds:SignatureMethod Algorithm="http://www.w3.org/2001/04/xmldsig-more#rsa-sha256"/>
            <ds:Reference URI="#RPe859c5a1-2f30-4a19-a416-e4b7281f6fe5">
                <ds:Transforms>
                    <ds:Transform Algorithm="http://www.w3.org/2000/09/xmldsig#enveloped-signature"/>
                    <ds:Transform Algorithm="http://www.w3.org/2001/10/xml-exc-c14n#"/>
                </ds:Transforms>
                <ds:DigestMethod Algorithm="http://www.w3.org/2001/04/xmlenc#sha256"/>
                <ds:DigestValue>4nOd8Ypzy9rf2JD0qj4i0j4PUxK7rT92B1tl9b+tlNU=</ds:DigestValue>
            </ds:Reference>
        </ds:SignedInfo>
        <ds:SignatureValue>
            KW/RO9j9qAn3kvhDM6MBPBJXyftEzaxyfp9EZSlzsdhKo6RqcDIFMC9vxhqvoSeKw3I8EME/Uq++&#13;
            Kja3uldRV8YhorYKJFcM4Uen7bckJioVExNZZGUJHuDWjAtHeuXGJ0lk+veCzoOrGed3k0bQ6zx0&#13;
            +jRlFXJIdr+aUnDSHMY=
        </ds:SignatureValue>
        <ds:KeyInfo>
            <ds:X509Data>
                <ds:X509Certificate>MIIChTCCAe4CCQDo0wjPUK8sMDANBgkqhkiG9w0BAQsFADCBhjELMAkGA1UEBhMCVVMxEzARBgNV
                    BAgMCldhc2hpbmd0b24xEjAQBgNVBAcMCVZhbmNvdXZlcjEdMBsGA1UECgwUU3ByaW5nIFNlY3Vy
                    aXR5IFNBTUwxDDAKBgNVBAsMA2lkcDEhMB8GA1UEAwwYaWRwLnNwcmluZy5zZWN1cml0eS5zYW1s
                    MB4XDTE4MDUxNDE0NTUyMVoXDTI4MDUxMTE0NTUyMVowgYYxCzAJBgNVBAYTAlVTMRMwEQYDVQQI
                    DApXYXNoaW5ndG9uMRIwEAYDVQQHDAlWYW5jb3V2ZXIxHTAbBgNVBAoMFFNwcmluZyBTZWN1cml0
                    eSBTQU1MMQwwCgYDVQQLDANpZHAxITAfBgNVBAMMGGlkcC5zcHJpbmcuc2VjdXJpdHkuc2FtbDCB
                    nzANBgkqhkiG9w0BAQEFAAOBjQAwgYkCgYEA2EuygAucRBWtYifgEH/ErVUive4dZdqo72Bze4Mb
                    kPuTKLrMCLB6IXxt1p5lu+tr0JxOiRO3KFVOO3D0l+j9zOow4g+JdoMQsjSzA6HtL/D9ZjXP6iUx
                    FCYx+qmnVl3X9ipBD/HVKOBlzIqeXTSa5D17uxPQVxK64UDOI3CyY4cCAwEAATANBgkqhkiG9w0B
                    AQsFAAOBgQAj+6b6dlA6SitTfz44LdnFSW9mYaeimwPP8ZtU7/3EJCzLd5eq7N/0kYPNVclZvB45
                    I0UMT77AHWrNyScm56MTcEpSuHhJHAqRAgJKbciCTNsFI928EqiWSmu//w0ASBN3bVa8nv8/rafu
                    utCq3RskTkHVZnbT5Xa6ITEZxSncow==
                </ds:X509Certificate>
            </ds:X509Data>
        </ds:KeyInfo>
    </ds:Signature>
    <saml2p:Status>
        <saml2p:StatusCode Value="urn:oasis:names:tc:SAML:2.0:status:Success"/>
    </saml2p:Status>
    <saml2:Assertion xmlns:saml2="urn:oasis:names:tc:SAML:2.0:assertion" ID="A54fe2992-c0a2-4014-97b5-ff5b32de9c2e"
                     IssueInstant="2025-01-11T21:09:45.666Z" Version="2.0">
        <saml2:Issuer>my-identity-provider-app</saml2:Issuer>
        <ds:Signature xmlns:ds="http://www.w3.org/2000/09/xmldsig#">
            <ds:SignedInfo>
                <ds:CanonicalizationMethod Algorithm="http://www.w3.org/2001/10/xml-exc-c14n#"/>
                <ds:SignatureMethod Algorithm="http://www.w3.org/2001/04/xmldsig-more#rsa-sha256"/>
                <ds:Reference URI="#A54fe2992-c0a2-4014-97b5-ff5b32de9c2e">
                    <ds:Transforms>
                        <ds:Transform Algorithm="http://www.w3.org/2000/09/xmldsig#enveloped-signature"/>
                        <ds:Transform Algorithm="http://www.w3.org/2001/10/xml-exc-c14n#"/>
                    </ds:Transforms>
                    <ds:DigestMethod Algorithm="http://www.w3.org/2001/04/xmlenc#sha256"/>
                    <ds:DigestValue>dZvMj+HJXvN5EiXWzjGBTRPmjHIyIcPl/xPaM0G2Dzo=</ds:DigestValue>
                </ds:Reference>
            </ds:SignedInfo>
            <ds:SignatureValue>
                RN2goZ+bbfw49E1lJ9TktQN59CmNXRkytzJ5WSdiyG/D9LgzSf0MUsDrBKq/znzXBMcat2ErqpTx&#13;
                o9M6RBqoRIE62wy08oD7SN8OwKIBjEgxBlGwNPqe5bCDdQyqNuUiB0vgMTiLpOabwdvvb/UVKnXJ&#13;
                i7+eFu/1s8vB/JOl9wQ=
            </ds:SignatureValue>
            <ds:KeyInfo>
                <ds:X509Data>
                    <ds:X509Certificate>MIIChTCCAe4CCQDo0wjPUK8sMDANBgkqhkiG9w0BAQsFADCBhjELMAkGA1UEBhMCVVMxEzARBgNV
                        BAgMCldhc2hpbmd0b24xEjAQBgNVBAcMCVZhbmNvdXZlcjEdMBsGA1UECgwUU3ByaW5nIFNlY3Vy
                        aXR5IFNBTUwxDDAKBgNVBAsMA2lkcDEhMB8GA1UEAwwYaWRwLnNwcmluZy5zZWN1cml0eS5zYW1s
                        MB4XDTE4MDUxNDE0NTUyMVoXDTI4MDUxMTE0NTUyMVowgYYxCzAJBgNVBAYTAlVTMRMwEQYDVQQI
                        DApXYXNoaW5ndG9uMRIwEAYDVQQHDAlWYW5jb3V2ZXIxHTAbBgNVBAoMFFNwcmluZyBTZWN1cml0
                        eSBTQU1MMQwwCgYDVQQLDANpZHAxITAfBgNVBAMMGGlkcC5zcHJpbmcuc2VjdXJpdHkuc2FtbDCB
                        nzANBgkqhkiG9w0BAQEFAAOBjQAwgYkCgYEA2EuygAucRBWtYifgEH/ErVUive4dZdqo72Bze4Mb
                        kPuTKLrMCLB6IXxt1p5lu+tr0JxOiRO3KFVOO3D0l+j9zOow4g+JdoMQsjSzA6HtL/D9ZjXP6iUx
                        FCYx+qmnVl3X9ipBD/HVKOBlzIqeXTSa5D17uxPQVxK64UDOI3CyY4cCAwEAATANBgkqhkiG9w0B
                        AQsFAAOBgQAj+6b6dlA6SitTfz44LdnFSW9mYaeimwPP8ZtU7/3EJCzLd5eq7N/0kYPNVclZvB45
                        I0UMT77AHWrNyScm56MTcEpSuHhJHAqRAgJKbciCTNsFI928EqiWSmu//w0ASBN3bVa8nv8/rafu
                        utCq3RskTkHVZnbT5Xa6ITEZxSncow==
                    </ds:X509Certificate>
                </ds:X509Data>
            </ds:KeyInfo>
        </ds:Signature>
        <saml2:Subject>
            <saml2:NameID Format="urn:oasis:names:tc:SAML:2.0:nameid-format:persistent"
                          SPNameQualifier="second-service-provider">user002@email
            </saml2:NameID>
            <saml2:SubjectConfirmation Method="urn:oasis:names:tc:SAML:2.0:cm:bearer">
                <saml2:SubjectConfirmationData InResponseTo="ARQc681f84-9cab-44cc-8476-3b0c273d5a28"
                                               NotOnOrAfter="2025-01-11T21:11:45.666Z"
                                               Recipient="http://localhost:8083/this-is-sp2/saml/sso/second-service-provider"/>
            </saml2:SubjectConfirmation>
        </saml2:Subject>
        <saml2:Conditions NotBefore="2025-01-11T21:08:45.666Z" NotOnOrAfter="2025-01-11T21:11:45.666Z">
            <saml2:AudienceRestriction>
                <saml2:Audience>second-service-provider</saml2:Audience>
            </saml2:AudienceRestriction>
        </saml2:Conditions>
        <saml2:AuthnStatement AuthnInstant="2025-01-11T21:09:45.666Z"
                              SessionIndex="IDXad91f552-650a-4d75-95cf-0a058a810c9e"
                              SessionNotOnOrAfter="2025-01-11T21:39:45.666Z">
            <saml2:AuthnContext>
                <saml2:AuthnContextClassRef>urn:oasis:names:tc:SAML:2.0:ac:classes:unspecified
                </saml2:AuthnContextClassRef>
            </saml2:AuthnContext>
        </saml2:AuthnStatement>
        <saml2:AttributeStatement/>
    </saml2:Assertion>
</saml2p:Response>
```


### Logout Request from SP

```xml
<?xml version="1.0" encoding="UTF-8"?>
<saml2p:LogoutRequest xmlns:saml2p="urn:oasis:names:tc:SAML:2.0:protocol"
                      Destination="http://localhost:8081/this-is-idp/saml/idp/logout/alias/my-identity-provider-app"
                      ID="LR5c81696c-2295-42ad-92c6-b91bc5481f97" IssueInstant="2025-01-11T21:08:25.396Z" Version="2.0">
    <saml2:Issuer xmlns:saml2="urn:oasis:names:tc:SAML:2.0:assertion">second-service-provider</saml2:Issuer>
    <saml2:NameID xmlns:saml2="urn:oasis:names:tc:SAML:2.0:assertion">user002@email</saml2:NameID>
    <saml2p:SessionIndex>IDX29d3bed5-1e1b-48c5-8951-b14fc05581dd</saml2p:SessionIndex>
</saml2p:LogoutRequest>

```