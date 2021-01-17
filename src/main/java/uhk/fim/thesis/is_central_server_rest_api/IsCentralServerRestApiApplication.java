package uhk.fim.thesis.is_central_server_rest_api;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;

@SpringBootApplication
@EnableScheduling
public class IsCentralServerRestApiApplication {

    public static void main(String[] args) {
        SpringApplication.run(IsCentralServerRestApiApplication.class, args);
    }

}
