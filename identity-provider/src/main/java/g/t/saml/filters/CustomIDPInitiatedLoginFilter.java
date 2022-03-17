package g.t.saml.filters;

import g.t.saml.config.UserUtils;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.saml.SamlMessageStore;
import org.springframework.security.saml.SamlRequestMatcher;
import org.springframework.security.saml.provider.identity.IdentityProviderService;
import org.springframework.security.saml.provider.identity.IdpInitiatedLoginFilter;
import org.springframework.security.saml.provider.provisioning.SamlProviderProvisioning;
import org.springframework.security.saml.saml2.authentication.Assertion;
import org.springframework.security.saml.saml2.authentication.AuthenticationRequest;
import org.springframework.security.saml.saml2.metadata.NameId;
import org.springframework.security.saml.saml2.metadata.ServiceProviderMetadata;

import javax.servlet.http.HttpServletRequest;

public class CustomIDPInitiatedLoginFilter extends IdpInitiatedLoginFilter {


    public CustomIDPInitiatedLoginFilter(SamlProviderProvisioning<IdentityProviderService> provisioning,
                                         SamlMessageStore<Assertion, HttpServletRequest> assertionStore) {
        super(provisioning, assertionStore);
    }

    public CustomIDPInitiatedLoginFilter(SamlProviderProvisioning<IdentityProviderService> provisioning,
                                         SamlMessageStore<Assertion, HttpServletRequest> assertionStore, SamlRequestMatcher requestMatcher) {
        super(provisioning, assertionStore, requestMatcher);
    }

    @Override
    protected Assertion getAssertion(Authentication authentication,
                                     AuthenticationRequest authenticationRequest,
                                     IdentityProviderService provider,
                                     ServiceProviderMetadata recipient) {
        Assertion assertion = provider.assertion(recipient, authentication.getName(), NameId.PERSISTENT);
        UserUtils.getCurrentUserDetails(SecurityContextHolder.getContext().getAuthentication())
                .getSamlAttributesToSendToSP()
                .forEach(assertion::addAttribute);
        return assertion;
    }

}
