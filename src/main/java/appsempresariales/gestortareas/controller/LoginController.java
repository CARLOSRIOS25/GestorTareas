package appsempresariales.gestortareas.controller;

import appsempresariales.gestortareas.model.Usuario;
import appsempresariales.gestortareas.service.UsuarioService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
public class LoginController {

    @Autowired
    private UsuarioService usuarioService;

    @GetMapping("/loginPage")
    public String loginPage() {
        return "LoginPage";
    }

    @PostMapping("/login")
    public String login(@RequestParam("username") String username, @RequestParam("password") String password) {
        Usuario usuario = usuarioService.authenticateUser(username, password);
        if (usuario != null && usuario.getAdmin()) {
            return "redirect:/admins/adminPage";
        } else if(usuario != null) {
            return "redirect:/usuarios/userPage";
        }else {
            return "Pa";
        }
    }

}
