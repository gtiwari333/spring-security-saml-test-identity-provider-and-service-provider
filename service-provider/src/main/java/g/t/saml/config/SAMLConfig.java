
package g.t.saml.config;

import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.saml.provider.SamlServerConfiguration;
import org.springframework.security.saml.provider.service.config.SamlServiceProviderServerBeanConfiguration;

@Configuration
public class SAMLConfig extends SamlServiceProviderServerBeanConfiguration {

    private final AppProperties properties;

    public SAMLConfig(@Qualifier("appProperties") AppProperties appProperties) {
        this.properties = appProperties;
    }

    @Override
    protected SamlServerConfiguration getDefaultHostSamlServerConfiguration() {
        return properties;
    }

}
