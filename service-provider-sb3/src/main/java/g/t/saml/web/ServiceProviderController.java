package g.t.saml.web;

//import g.t.saml.config.AppProperties;

import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.autoconfigure.security.saml2.Saml2RelyingPartyProperties;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.saml2.provider.service.authentication.Saml2AuthenticatedPrincipal;
import org.springframework.security.saml2.provider.service.registration.RelyingPartyRegistration;
import org.springframework.security.saml2.provider.service.registration.RelyingPartyRegistrationRepository;
import org.springframework.security.saml2.provider.service.web.authentication.Saml2AuthenticationRequestResolver;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import static java.util.stream.Collectors.toList;

@Controller
@RequiredArgsConstructor
@Slf4j
public class ServiceProviderController {

    final Saml2RelyingPartyProperties saml2RelyingPartyProperties;
    final RelyingPartyRegistrationRepository idpRepo;

    @GetMapping("/idps")
    public String listIDPs(Model model) {
        List<Provider> idpUrlMap = getIdentityProviderUrlMap();
        model.addAttribute("idps", idpUrlMap);
        return "idp-list";
    }

    @RequestMapping(value = {"/", "/index", "/logged-in"})
    public String home(@AuthenticationPrincipal Saml2AuthenticatedPrincipal principal) {
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
    public String selectIdp(Model model) {
        log.info("Selecting idp to login");
        List<Provider> idpUrlMap = getIdentityProviderUrlMap();
        model.addAttribute("idps", idpUrlMap);
        return "select-provider";
    }

    private List<Provider> getIdentityProviderUrlMap() {
        List<Provider> idps = new ArrayList<>();
        if (idpRepo instanceof Iterable) {
            Iterable<RelyingPartyRegistration> repo = (Iterable<RelyingPartyRegistration>) idpRepo;
            repo.forEach((p) -> idps.add(new Provider(p.getRegistrationId(), Saml2AuthenticationRequestResolver.DEFAULT_AUTHENTICATION_REQUEST_URI.replace("{registrationId}", p.getRegistrationId()))));
        }
        return idps;
    }

    record Provider(String linkText, String redirect) {
    }
}
