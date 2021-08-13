package uhk.fim.thesis.is_central_server_rest_api.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import uhk.fim.thesis.is_central_server_rest_api.model.SensorInformation;

@Repository
public interface SensorInformationRepository extends JpaRepository<SensorInformation, String> {
}
