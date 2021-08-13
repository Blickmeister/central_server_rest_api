package uhk.fim.thesis.is_central_server_rest_api.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import uhk.fim.thesis.is_central_server_rest_api.model.User;

import java.util.Optional;

/**
 * @author Bc. Ondřej Schneider - FIM UHK
 * @version 1.0
 * @since 2021-04-02
 * Repozitář(v rámci Data JPA) pro přístup k datům týkajících se kontextu hybridního klienta
 * (pro potřeby IS i řídící a komunukační funkce)
 */
@Repository
public interface UserRepository extends JpaRepository<User, String> {
    @Override
    Optional<User> findById(String s);
}
