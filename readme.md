## Spring Security SAML example 

This project demonstrates use case of Identity Provider and Service Provider based on Spring SAML extension (version 2.0.0.M30+).

It supports both IDP initiated and SP initiated SSO flow.

This example is taken from the Spring Security SAML extension project.

### Modules

#### Identity Provider

- It contains a basic identity provider application with the ability to define custom user attributes. This test IDP can be used to test any local SAML service provider implementation.

#### Service Provider

- This is a basic service provider with custom IDP select page and a basic home page .

## How to Test

- Both module are Spring Boot applications. Run the applications and open either SP and IDP to initiate the authentication. Watch out for the redirection being performed by SAML. 

    - http://localhost:8081/this-is-idp
    
    - http://localhost:8082/this-is-sp
 
