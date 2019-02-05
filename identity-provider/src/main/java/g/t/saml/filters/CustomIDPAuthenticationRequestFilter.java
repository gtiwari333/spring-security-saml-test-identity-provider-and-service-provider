package g.t.saml.filters;

import org.springframework.http.HttpMethod;
import org.springframework.security.saml.SamlMessageStore;
import org.springframework.security.saml.SamlRequestMatcher;
import org.springframework.security.saml.provider.identity.IdentityProviderService;
import org.springframework.security.saml.provider.provisioning.SamlProviderProvisioning;
import org.springframework.security.saml.saml2.authentication.Assertion;
import org.springframework.security.saml.saml2.authentication.AuthenticationRequest;
import org.springframework.security.saml.saml2.metadata.ServiceProviderMetadata;

import javax.servlet.http.HttpServletRequest;

public class CustomIDPAuthenticationRequestFilter extends CustomIDPInitiatedLoginFilter {

    public CustomIDPAuthenticationRequestFilter(SamlProviderProvisioning<IdentityProviderService> provisioning,
                                                SamlMessageStore<Assertion, HttpServletRequest> assertionStore) {
        this(
                provisioning,
                assertionStore,
                new SamlRequestMatcher(provisioning, "SSO")
        );
    }

    public CustomIDPAuthenticationRequestFilter(SamlProviderProvisioning<IdentityProviderService> provisioning,
                                                SamlMessageStore<Assertion, HttpServletRequest> assertionStore,
                                                SamlRequestMatcher requestMatcher) {
        super(provisioning, assertionStore, requestMatcher);
    }

    @Override
    protected ServiceProviderMetadata getTargetProvider(HttpServletRequest request) {
        IdentityProviderService provider = getProvisioning().getHostedProvider();
        String param = request.getParameter("SAMLRequest");
        AuthenticationRequest authn =
                provider.fromXml(
                        param,
                        true,
                        HttpMethod.GET.name().equalsIgnoreCase(request.getMethod()),
                        AuthenticationRequest.class
                );
        provider.validate(authn);
        return provider.getRemoteProvider(authn);
    }
}
