## Spring Security SAML example

This project demonstrates both IDP initiated and SP initiated SSO flows.

### Modules

#### Identity Provider
- Uses Spring Boot 2 identity provider (https://github.com/spring-attic/spring-security-saml/tree/develop-3.0)
- It contains a basic identity provider application with the ability to define custom user attributes. This test IDP can
  be used to test any local SAML service provider implementation.
- Requires JDK11

#### Service Provider
- Uses Spring Boot 2 service provider (https://github.com/spring-attic/spring-security-saml/tree/develop-3.0)
- This is a basic service provider with custom IDP select page and a basic home page .
- Requires JDK11

#### Service Provider SB3
- Uses Spring Boot 3 service provider with spring-security-saml2 
-  https://docs.spring.io/spring-security/reference/servlet/saml2/login/overview.html#servlet-saml2login-minimaldependencies
- This is a basic service provider with custom IDP select page and a basic home page .
- Requires JDK17+

## How to Test

- Both module are Spring Boot applications. Run the applications and open either SP and IDP to initiate the
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
