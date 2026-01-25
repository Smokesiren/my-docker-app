package edu.zut.awir.awir1.web.mvc;

import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.oauth2.core.oidc.user.OidcUser;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
@Controller
public class MeController {
    @GetMapping("/me")
    public String me(@AuthenticationPrincipal OidcUser user, Model model) {
        if (user != null) {
            model.addAttribute("name", user.getFullName());
            model.addAttribute("email", user.getEmail());
            model.addAttribute("picture", user.getPicture());
        }
        return "me"; // templates/me.html
    }
}
