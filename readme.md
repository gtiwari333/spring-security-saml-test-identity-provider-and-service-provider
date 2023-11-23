## Spring Security SAML example

This project demonstrates use case of Identity Provider and Service Provider based on Spring SAML extension (version
2.0.0.M30+).

It supports both IDP initiated and SP initiated SSO flow.

This example is taken from the Spring Security SAML extension project.

### Requiremement: JDK 11 or JDK 17 (may not work with 17+)

### Modules

#### Identity Provider

- It contains a basic identity provider application with the ability to define custom user attributes. This test IDP can
  be used to test any local SAML service provider implementation.

#### Service Provider

- This is a basic service provider with custom IDP select page and a basic home page .

## How to Test

- Both module are Spring Boot applications. Run the applications and open either SP and IDP to initiate the
  authentication. Watch out for the redirection being performed by SAML.

    - http://localhost:8081/this-is-idp

    - http://localhost:8082/this-is-sp

## Look at UserUtils class to generate custom SAML attributes

- Usernames: user002@email, user003@email
- password: pass

## generate a private key with the correct length

openssl genrsa -out pk.key 1024

# generate corresponding public key

openssl pkcs8 -topk8 -inform pem -in pk.key -outform pem -nocrypt -out pk.pem

# optional: create a self-signed certificate

openssl req -new -x509 -key pk.pem -out cert.pem -days 360
