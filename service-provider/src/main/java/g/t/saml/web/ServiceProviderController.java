package g.t.saml.web;

import g.t.saml.config.AppProperties;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.client.RestTemplate;

import java.util.Map;

import static java.util.stream.Collectors.toMap;

@Controller
@RequiredArgsConstructor
@Slf4j
public class ServiceProviderController {

    final AppProperties appProperties;

    @GetMapping("/idps")
    public String listIDPs(Model model) {

        Map<String, String> collect = appProperties.getServiceProvider().getProviders().stream()
                .collect(toMap(e -> e.getAlias(), e -> e.getMetadata()));

        model.addAttribute("idps", collect);
        return "idp-list";
    }

    @RequestMapping(value = {"/", "/index", "/logged-in"})
    public String home() {
        log.info("Sample SP Application - You are logged in!");
        return "logged-in";
    }

}
