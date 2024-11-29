package appsempresariales.gestortareas.controller;

import appsempresariales.gestortareas.model.Tarea;
import appsempresariales.gestortareas.model.Usuario;
import appsempresariales.gestortareas.repository.TareaRepository;
import appsempresariales.gestortareas.repository.UsuarioRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;

@Controller
@RequestMapping("/admins")
public class AdminController {

    @Autowired
    private UsuarioRepository usuarioRepository;

    @Autowired
    private TareaRepository tareaRepository;

    @GetMapping("/adminPage")
    public String adminPage(Model model) {
        List<Usuario> usuarios = usuarioRepository.findAll();
        model.addAttribute("usuarios", usuarios);
        List<Tarea> tareas = tareaRepository.findByEstado("Pendiente");
        model.addAttribute("tareas", tareas);
        return "AdminPage";
    }

}
