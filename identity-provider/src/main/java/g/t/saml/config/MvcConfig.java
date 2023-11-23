package g.t.saml.config;

import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.Ordered;

@Configuration
public class MvcConfig {

    @Bean
    public FilterRegistrationBean<ReqLogFilter> loggingFilter() {
        var registrationBean = new FilterRegistrationBean<ReqLogFilter>();

        registrationBean.setFilter(new ReqLogFilter());
        registrationBean.setOrder((Ordered.HIGHEST_PRECEDENCE));

        return registrationBean;
    }

}
