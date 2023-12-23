package g.t.saml.config;

import g.t.saml.filters.CustomIDPInitiatedLoginFilter;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.saml.provider.SamlServerConfiguration;
import org.springframework.security.saml.provider.identity.IdpAuthenticationRequestFilter;
import org.springframework.security.saml.provider.identity.config.SamlIdentityProviderServerBeanConfiguration;

import javax.servlet.Filter;
import java.util.Collection;

/**
 * SamlIdentityProviderServerBeanConfiguration is servlet Filter based api.
 */
@Configuration
@Slf4j
public class SAMLConfig extends SamlIdentityProviderServerBeanConfiguration {


    private final AppProperties config;

    public SAMLConfig(@Qualifier("appProperties") AppProperties config) {
        this.config = config;
    }

    @Override
    protected SamlServerConfiguration getDefaultHostSamlServerConfiguration() {
        return config;
    }

    @Bean
    public UserDetailsService userDetailsService() {
        Collection<IDPUserDetails> allUsers = UserUtils.getAllUserLoginDetails();
        return new IDPInMemoryUserDetailsManager(allUsers);
    }

    @Bean
    public Filter idpInitatedLoginFilter() {
        return new CustomIDPInitiatedLoginFilter(getSamlProvisioning(), samlAssertionStore());
    }


    @Bean
    public Filter idpAuthnRequestFilter() {
        return new IdpAuthenticationRequestFilter(getSamlProvisioning(), samlAssertionStore());
    }
}
