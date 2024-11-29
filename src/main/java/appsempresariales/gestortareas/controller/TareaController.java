package appsempresariales.gestortareas.controller;

import appsempresariales.gestortareas.model.Categoria;
import appsempresariales.gestortareas.model.Tarea;
import appsempresariales.gestortareas.model.Usuario;
import appsempresariales.gestortareas.repository.TareaRepository;
import appsempresariales.gestortareas.repository.UsuarioRepository;
import appsempresariales.gestortareas.service.TareaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.Comparator;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Controller
@RequestMapping("/tareas")
public class TareaController {
    @Autowired
    private TareaRepository tareaRepository;

    @Autowired
    private TareaService tareaService;

    @Autowired
    private UsuarioRepository usuarioRepository;

    @GetMapping("/page")
    public String tareasPage(Model model) {
        List<Tarea> tareasPendientes = tareaRepository.findByEstado("Pendiente");
        List<Tarea> tareasCompletadas = tareaRepository.findByEstado("Completada");
        model.addAttribute("tareasPendientes", tareasPendientes);
        model.addAttribute("tareasCompletadas", tareasCompletadas);
        return "TareasPage";
    }

    @GetMapping("/fechaFin")
    public String tareasFechaFin(Model model) {
        List<Tarea> tareasPendientes = tareaRepository.findByEstadoOrderByFechaFinAsc("Pendiente");
        List<Tarea> tareasCompletadas = tareaRepository.findByEstadoOrderByFechaFinAsc("Completada");
        model.addAttribute("tareasPendientes", tareasPendientes);
        model.addAttribute("tareasCompletadas", tareasCompletadas);
        return "TareasFechaFinPage";
    }

    @GetMapping("/prioridad")
    public String tareasPrioridad(Model model) {
        List<Tarea> tareasPendientes = tareaRepository.findByEstadoOrderByPrioridadDesc("Pendiente");
        List<Tarea> tareasCompletadas = tareaRepository.findByEstadoOrderByPrioridadDesc("Completada");

        tareasPendientes.sort(Comparator.comparing(t -> {
            switch (t.getPrioridad()) {
                case "ALTA":
                    return 1;
                case "MEDIA":
                    return 2;
                case "BAJA":
                    return 3;
                default:
                    return 4;
            }
        }));
        tareasCompletadas.sort(Comparator.comparing(t -> {
            switch (t.getPrioridad()) {
                case "ALTA":
                    return 1;
                case "MEDIA":
                    return 2;
                case "BAJA":
                    return 3;
                default:
                    return 4;
            }
        }));
        model.addAttribute("tareasPendientes", tareasPendientes);
        model.addAttribute("tareasCompletadas", tareasCompletadas);
        return "TareasPrioridadPage";
    }

    //completar tareas por categoria y crear tareas por usuario
    @GetMapping("/categoria")
    public String TareasCategoria(Model model) {
        List<Tarea> tareasPersonal = tareaRepository.findTareasByCategoria(Categoria.PERSONAL);
        List<Tarea> tareasTrabajo = tareaRepository.findTareasByCategoria(Categoria.TRABAJO);
        List<Tarea> tareasUniversidad = tareaRepository.findTareasByCategoria(Categoria.UNIVERSIDAD);
        List<Tarea> tareasColegio = tareaRepository.findTareasByCategoria(Categoria.COLEGIO);
        List<Tarea> tareasOtro = tareaRepository.findTareasByCategoria(Categoria.OTRO);
        model.addAttribute("tareasPersonal", tareasPersonal);
        model.addAttribute("tareasTrabajo", tareasTrabajo);
        model.addAttribute("tareasUniversidad", tareasUniversidad);
        model.addAttribute("tareasColegio", tareasColegio);
        model.addAttribute("tareasOtro", tareasOtro);
        return "TareasCategoriaPage";
    }

    @GetMapping("/usuario")
    public String tareasUsuario(Model model) {
        List<Usuario> usuarios = usuarioRepository.findAll();
        Map<Usuario, List<Tarea>> tareasPorUsuario = usuarios.stream()
                .collect(Collectors.toMap(
                        usuario -> usuario, usuario -> tareaRepository.findTareasByEstadoAndUsuarioAsignado("Pendiente", usuario)
                ));
        model.addAttribute("tareasPorUsuario", tareasPorUsuario);
        return "TareasUsuarioPage";
    }

    @PostMapping("/crear")
    public String crearTarea(Tarea tarea){
        tareaRepository.save(tarea);
        return "redirect:/tareas/formTarea";
    }

    @PostMapping("/adminCrear")
    public String crearTareaAdmin(Tarea tarea){
        tareaRepository.save(tarea);
        return "redirect:/tareas/formTareaAdmin";
    }

    @GetMapping("/formTarea")
    public String mostrarFormulario(Model model) {
        model.addAttribute("tarea", new Tarea());
        List<Usuario> listaUsuarios = usuarioRepository.findAll();
        model.addAttribute("listaUsuarios", listaUsuarios);
        return "FormularioTareas";
    }

    @GetMapping("/formTareaAdmin")
    public String mostrarFormularioAdmin(Model model) {
        model.addAttribute("tarea", new Tarea());
        List<Usuario> listaUsuarios = usuarioRepository.findAll();
        model.addAttribute("listaUsuarios", listaUsuarios);
        return "FormularioTareasAdmin";
    }

    @GetMapping("/adminCompletar/{id}")
    public String completarTareaAdmin(@PathVariable String id) {
        tareaService.completarTarea(id);
        return "redirect:/admins/adminPage";
    }

    @GetMapping("/adminEliminar/{id}")
    public String eliminarTareaAdmin(@PathVariable String id) {
        tareaRepository.deleteById(id);
        return "redirect:/admins/adminPage";
    }

    @GetMapping("/completar/{id}")
    public String completarTarea(@PathVariable String id) {
        tareaService.completarTarea(id);
        return "redirect:/tareas/page";
    }

    @GetMapping("/completarTodas")
    public String completarTodasLasTareas() {
        tareaService.completarTodasLasTareas();
        return "redirect:/tareas/page";
    }

    @GetMapping("/eliminar/{id}")
    public String eliminarTarea(@PathVariable String id) {
        tareaRepository.deleteById(id);
        return "redirect:/tareas/page";
    }


}
