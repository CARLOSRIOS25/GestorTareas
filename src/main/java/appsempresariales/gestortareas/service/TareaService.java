package appsempresariales.gestortareas.service;

import appsempresariales.gestortareas.model.Tarea;
import appsempresariales.gestortareas.repository.TareaRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Comparator;
import java.util.List;

@Service
public class TareaService {

    @Autowired
    private TareaRepository tareaRepository;

    public void completarTarea(String id) {
        Tarea tarea = tareaRepository.findById(id).orElseThrow(() -> new IllegalArgumentException("Tarea no encontrada"));
        tarea.setEstado("Completada");
        tareaRepository.save(tarea);
    }

    public void completarTodasLasTareas() {
        List<Tarea> tareasPendientes = tareaRepository.findByEstado("Pendiente");
        for (Tarea tarea : tareasPendientes) {
            tarea.setEstado("Completada");
            tareaRepository.save(tarea);
        }
    }


}
