package appsempresariales.gestortareas.controller;

import appsempresariales.gestortareas.model.Tarea;
import appsempresariales.gestortareas.model.Usuario;
import appsempresariales.gestortareas.repository.TareaRepository;
import appsempresariales.gestortareas.repository.UsuarioRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import java.util.List;


@Controller
@RequestMapping("/usuarios")
public class UsuarioController {

    @Autowired
    private UsuarioRepository usuarioRepository;

    @Autowired
    private TareaRepository tareaRepository;

    @GetMapping("/userPage")
    public String userPage(Model model) {
        List<Usuario> usuarios = usuarioRepository.findAll();
        List<Tarea> tareasAVencer = tareaRepository.findTop3ByOrderByFechaFinDesc();
        List<Tarea> tareas = tareaRepository.findAll();
        long totalTareas = tareas.size();
        long tareasCompletadas = tareas.stream().filter(t -> "Completada".equals(t.getEstado())).count();
        long tareasPendientes = tareas.stream().filter(t -> "Pendiente".equals(t.getEstado())).count();

        model.addAttribute("usuarios", usuarios);
        model.addAttribute("tareasAVencer", tareasAVencer);
        model.addAttribute("totalTareas", totalTareas);
        model.addAttribute("tareasCompletadas", tareasCompletadas);
        model.addAttribute("tareasPendientes", tareasPendientes);
        return "UserPage";
    }

    @PostMapping("/agregar")
    public String registrarUsuario(Usuario usuario) {
        usuarioRepository.save(usuario);
        return "redirect:/admins/adminPage";
    }

    @GetMapping("/all")
    public String obtenerUsuarios(Model model) {
        List<Usuario> usuarios = usuarioRepository.findAll();
        model.addAttribute("users", usuarios);
        return "listarUsuarios";
    }

    @GetMapping("/eliminar/{id}")
    public String eliminarUsuario(@PathVariable String id) {
        usuarioRepository.deleteById(id);
        return "redirect:/admins/adminPage";
    }
}