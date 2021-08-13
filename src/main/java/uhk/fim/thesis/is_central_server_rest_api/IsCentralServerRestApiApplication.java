package uhk.fim.thesis.is_central_server_rest_api;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;

/**
 * @author Bc. Ondřej Schneider - FIM UHK
 * @version 1.0
 * @since 2021-04-02
 * Spouštěcí třída centrálního serveru, respektive Spring boot aplikace
 */
@SpringBootApplication
@EnableScheduling
public class IsCentralServerRestApiApplication {

    public static void main(String[] args) {
        SpringApplication.run(IsCentralServerRestApiApplication.class, args);
    }

}
