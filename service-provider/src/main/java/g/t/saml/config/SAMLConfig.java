
package g.t.saml.config;

import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.saml.SamlTemplateEngine;
import org.springframework.security.saml.provider.SamlServerConfiguration;
import org.springframework.security.saml.provider.service.SelectIdentityProviderFilter;
import org.springframework.security.saml.provider.service.config.SamlServiceProviderServerBeanConfiguration;
import org.thymeleaf.spring5.SpringTemplateEngine;

import javax.servlet.Filter;

@Configuration
public class SAMLConfig extends SamlServiceProviderServerBeanConfiguration {

    private final AppProperties properties;
    private final SpringTemplateEngine springTemplateEngine;

    public SAMLConfig(@Qualifier("appProperties") AppProperties appProperties, SpringTemplateEngine springTemplateEngine) {
        this.properties = appProperties;
        this.springTemplateEngine = springTemplateEngine;
    }

    @Override
    protected SamlServerConfiguration getDefaultHostSamlServerConfiguration() {
        return properties;
    }

    public Filter spSelectIdentityProviderFilter() {
        SelectIdentityProviderFilter filter = new SelectIdentityProviderFilter(getSamlProvisioning());
        filter.setSelectTemplate("select-provider.html"); //thymeleaf looks in /templates/ folder
        filter.setSamlTemplateEngine(samlTemplateEngine());
        return filter;
    }

    @Override
    public SamlTemplateEngine samlTemplateEngine() {
        return new ThymeleafTemplateEngine(springTemplateEngine);
    }
}
