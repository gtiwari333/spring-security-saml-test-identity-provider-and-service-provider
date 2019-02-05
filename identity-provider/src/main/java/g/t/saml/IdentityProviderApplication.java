package g.t.saml;

import g.t.saml.config.AppProperties;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.core.env.Environment;

import java.net.InetAddress;
import java.net.UnknownHostException;

@SpringBootApplication
@EnableConfigurationProperties({AppProperties.class})
@Slf4j
public class IdentityProviderApplication {

    public static void main(String[] args) throws UnknownHostException {
        SpringApplication app = new SpringApplication(IdentityProviderApplication.class);

        Environment env = app.run(args).getEnvironment();
        log.info("Identity Provider: Access URLs:\n----------------------------------------------------------\n\t" +
                        "Local: \t\t\thttp://localhost:{}{}\n" +
                        "----------------------------------------------------------",
                env.getProperty("server.port"), env.getProperty("server.servlet.context-path"),
                InetAddress.getLocalHost().getHostAddress()
        );
    }
}

