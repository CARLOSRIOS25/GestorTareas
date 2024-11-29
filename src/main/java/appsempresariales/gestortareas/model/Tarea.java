package appsempresariales.gestortareas.model;

import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.DBRef;
import org.springframework.data.mongodb.core.mapping.Document;
import java.time.LocalDate;


@Getter
@Setter
@NoArgsConstructor@AllArgsConstructor
@Document(collection = "tareas")
public class Tarea {
    @Id
    private String id;
    private String nombre;
    @NotBlank
    private String descripcion;
    private Prioridad prioridad;
    private String fechaCreacion = LocalDate.now().toString();
    private Categoria categoria;
    private String fechaFin;
    private String estado = "Pendiente";
    @DBRef
    private Usuario usuarioAsignado;

    public String getPrioridad() {
        return prioridad.toString();
    }

    public String getCategoria() {
        return categoria.toString();
    }
}
