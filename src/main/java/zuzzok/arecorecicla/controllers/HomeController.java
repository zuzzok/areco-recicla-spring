package zuzzok.arecorecicla.controllers;

import org.springframework.security.access.annotation.Secured;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import zuzzok.arecorecicla.configs.UserAuthenticated;

@Controller
public class HomeController {

  @GetMapping("/")
  @Secured({ "ROLE_ADMIN", "ROLE_USER" })
  public String home(Model model, @AuthenticationPrincipal UserAuthenticated usuario) {

    model.addAttribute("usuario", usuario);
    return "index";
  }

}
