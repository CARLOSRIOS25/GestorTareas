package appsempresariales.gestortareas.repository;

import appsempresariales.gestortareas.model.Categoria;
import appsempresariales.gestortareas.model.Tarea;
import appsempresariales.gestortareas.model.Usuario;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface TareaRepository extends MongoRepository<Tarea, String> {
    List<Tarea> findByEstado(String estado);

    List<Tarea> findTop3ByOrderByFechaFinDesc();

    @Query("{ 'estado': ?0 }")
    List<Tarea> findByEstadoOrderByPrioridadDesc(String estado);

    List<Tarea> findByEstadoOrderByFechaFinAsc(String estado);

    List<Tarea> findTareasByCategoria(Categoria categoria);

    List<Tarea> findTareasByEstadoAndUsuarioAsignado(String pendiente, Usuario usuario);
}
