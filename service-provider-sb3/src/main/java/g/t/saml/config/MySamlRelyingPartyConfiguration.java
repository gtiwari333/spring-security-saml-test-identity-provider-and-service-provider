package g.t.saml.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.web.SecurityFilterChain;

import static org.springframework.security.config.Customizer.withDefaults;

@Configuration(proxyBeanMethods = false)
public class MySamlRelyingPartyConfiguration {
    private static final String[] AUTH_WHITELIST = {
            "/swagger-resources/**",
            "/swagger-ui/**",
            "/swagger-ui.html",
            "/v3/api-docs/**",
            "/h2-console/**",
            "/webjars/**",
            "/favicon.ico",
            "/static/**",
            "/signup/**",
            "/error/**",
            "/public/**",
            "/saml/**",
    };
    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http.authorizeHttpRequests(ah -> ah
                .requestMatchers(HttpMethod.OPTIONS, "/**").permitAll()
                .requestMatchers(AUTH_WHITELIST).permitAll()
                .anyRequest().authenticated()); //this one will catch the rest patterns

        http.saml2Login(saml2 -> saml2.loginProcessingUrl("/saml/sso/second-service-provider"));
        http.saml2Logout(saml2 -> saml2
                .logoutRequest(req -> req.logoutUrl("/saml/sp/logout"))
                .logoutResponse(resp -> resp.logoutUrl("/saml/sp/logout-response")));
        http.formLogin(login -> login.loginPage("/saml/sp/select"));
        http.saml2Metadata(saml2 -> saml2.metadataUrl("/saml/metadata"));
        return http.build();
    }

}
