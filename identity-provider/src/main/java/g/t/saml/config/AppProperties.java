package g.t.saml.config;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.saml.provider.SamlServerConfiguration;

@ConfigurationProperties(prefix = "saml2")
@Configuration
public class AppProperties extends SamlServerConfiguration {

}
