package g.t.saml.config;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.saml.provider.SamlServerConfiguration;

import java.util.Map;

/**
 * Properties specific to Enrollment App.
 * <p>
 * <p>
 * Properties are configured in the application.yml file.
 * </p>
 */
@ConfigurationProperties(prefix = "saml2")
@Configuration
@Data
public class AppProperties extends SamlServerConfiguration {

    private Map<String, String> entityIdToTargetIdMap;
    private String encryptionPrivateKey ;

}
