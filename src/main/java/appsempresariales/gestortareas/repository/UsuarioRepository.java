package appsempresariales.gestortareas.repository;

import appsempresariales.gestortareas.model.Usuario;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UsuarioRepository extends MongoRepository<Usuario, String> {
    Usuario findByUsernameAndPassword(String username, String password);
    Usuario findByNombre(String nombre);
}
