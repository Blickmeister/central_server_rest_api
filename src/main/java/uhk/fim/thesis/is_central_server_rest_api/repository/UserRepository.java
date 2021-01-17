package uhk.fim.thesis.is_central_server_rest_api.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import uhk.fim.thesis.is_central_server_rest_api.model.User;

@Repository
public interface UserRepository extends JpaRepository<User, String> {
}
