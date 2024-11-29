package appsempresariales.gestortareas.model;

import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Document(collection = "usuarios")
public class Usuario {
    @Id
    private String id;
    @NotBlank
    private String username;
    @NotBlank
    private String password;
    private String nombre;
    private String apellido;
    private String correo;
    private String telefono;
    @NotBlank
    private Boolean admin;

    public String getRole() {
        return admin ? "ADMIN" : "USER";
    }
}
