package zuzzok.arecorecicla.controllers;

import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

import jakarta.validation.Valid;
import zuzzok.arecorecicla.data.models.Rol;
import zuzzok.arecorecicla.data.models.Usuario;
import zuzzok.arecorecicla.data.repositories.GeneroRepository;
import zuzzok.arecorecicla.data.repositories.RolRepository;
import zuzzok.arecorecicla.data.repositories.UsuarioRepository;
import zuzzok.arecorecicla.data.services.UsuarioService;

@Controller
public class AuthController {

  @Autowired
  UsuarioRepository usuarioRepository;

  @Autowired
  GeneroRepository generoRepository;

  @Autowired
  RolRepository rolRepository;

  @Autowired
  UsuarioService service;

  @GetMapping("/login")
  public String login() {
    return "auth/login";
  }

  @GetMapping("/register")
  public String getRegister(Model model, Usuario usuario) {
    model.addAttribute("generos", generoRepository.findAll());
    return "auth/register";
  }

  @PostMapping("/register")
  public String postRegister(@Valid Usuario usuario, BindingResult result, Model model) {

    Usuario user = usuarioRepository.findByEmail(usuario.getEmail());
    model.addAttribute("generos", generoRepository.findAll());

    if (user != null) {
      result.rejectValue("email", "error.usuario.email", "Intenta con otro email, este ya fue utilizado.");
      return "auth/register";
    }

    if (result.hasErrors()) {
      return "auth/register";
    }

    Rol defaultRol = rolRepository.findByNombre("USUARIO");

    usuario.setRoles(Set.of(defaultRol));
    service.save(usuario);

    return "redirect:/login";
  }

}
