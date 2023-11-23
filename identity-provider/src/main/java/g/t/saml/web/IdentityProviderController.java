package g.t.saml.web;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@Slf4j
public class IdentityProviderController {
    @RequestMapping(value = {"/"})
    public String selectProvider() {
        log.info("Sample IDP Application - Select an SP to log into!");
        //SelectServiceProviderFilter will handle this request
        return "redirect:/saml/idp/select";
    }

}
