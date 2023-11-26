package g.t.saml.web;

//import g.t.saml.config.AppProperties;

import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.autoconfigure.security.saml2.Saml2RelyingPartyProperties;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.saml2.provider.service.authentication.Saml2AuthenticatedPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;
import java.util.Map;

import static java.util.stream.Collectors.toList;

@Controller
@RequiredArgsConstructor
@Slf4j
public class ServiceProviderController {

    final Saml2RelyingPartyProperties saml2RelyingPartyProperties;

    @GetMapping("/idps")
    public String listIDPs(Model model, @AuthenticationPrincipal Saml2AuthenticatedPrincipal principal) {

        List<String> collect = saml2RelyingPartyProperties.getRegistration().values().stream()
                .map(r -> r.getEntityId() + " > " + r.getAssertingparty().getEntityId()) //IDP
                .collect(toList());

        model.addAttribute("idps", collect);
        return "idp-list";
    }

    @RequestMapping(value = {"/", "/index", "/logged-in"})
    public String home() {
        log.info("Sample SP Application - You are logged in!");
        return "logged-in";
    }

    @RequestMapping(value = {"/saml/sp/logout-response"})
    public String loggedOut() {
        log.info("Logged out");
        return "logout-response";
    }

    @RequestMapping(value = {"/saml/sp/logout"})
    public String logoutHandle(HttpServletRequest req) {
        System.out.println(req.getParameter("RelayState"));
        log.info("Logged out");
        return "redirect:/saml/sp/select";
    }

    @RequestMapping(value = {"/saml/sp/select"})
    public String selectIdp() {
        log.info("Selecting idp to login");
//        List<String> collect = saml2RelyingPartyProperties.getRegistration().values().stream()
//                .map(r -> new Provider(r.getAssertingparty().getEntityId(), r.getAssertingparty())) //IDP
//                .collect(toList());
        return "select-provider";
    }

    record Provider(String linkText, String redirect) {
    }
}
