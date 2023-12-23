package g.t.saml;

import g.t.saml.config.AppProperties;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.jmx.JmxAutoConfiguration;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.core.env.Environment;

@SpringBootApplication(exclude = JmxAutoConfiguration.class)
@EnableConfigurationProperties({AppProperties.class})
@Slf4j
public class ServiceProviderApplication {

    public static void main(String[] args) {

        SpringApplication app = new SpringApplication(ServiceProviderApplication.class);

        Environment env = app.run(args).getEnvironment();
        log.info("Service Provider: Access URLs:\n----------------------------------------------------------\n\t" +
                        "Local: \t\t\thttp://localhost:{}{}\n" +
                        "----------------------------------------------------------",
                env.getProperty("server.port"), env.getProperty("server.servlet.context-path")
        );

    }

}
