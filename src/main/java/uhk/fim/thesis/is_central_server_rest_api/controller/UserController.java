package uhk.fim.thesis.is_central_server_rest_api.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;
import uhk.fim.thesis.is_central_server_rest_api.model.SensorInformation;
import uhk.fim.thesis.is_central_server_rest_api.model.User;
import uhk.fim.thesis.is_central_server_rest_api.repository.SensorInformationRepository;
import uhk.fim.thesis.is_central_server_rest_api.repository.UserRepository;
import uhk.fim.thesis.is_central_server_rest_api.service.CurrentTimeService;

import java.util.*;

/**
 * @author Bc. Ondřej Schneider - FIM UHK
 * @version 1.0
 * @since 2021-04-02
 * Controller (API) pro informace o hybridních klientech a to jak v rámci řídících funkcí, tak v rámci funkcí IS
 * Některé funkce jsou vynechány (server má pouze naznačit další možnosti využití konceptu hybridního klienta):
 * zasílání zpráv ohledně tloustnutí klienta do podoby P2P serveru;
 * metoda pro změnu offline/online stavu po uplynutí určitého časového úseku
 * -> řešení těchto funkcí jsou naznačeny v textu diplomové práce
 */
@RestController
@RequestMapping("/users")
public class UserController {

    private UserRepository userRepository;
    private SensorInformationRepository sensorInformationRepository;

    @Autowired
    public UserController(UserRepository userRepository, SensorInformationRepository sensorInformationRepository) {
        this.userRepository = userRepository;
        this.sensorInformationRepository = sensorInformationRepository;
    }

    // endpoint pro získání seznamu klientů
    @RequestMapping(value = "/all", method = RequestMethod.GET)
    public List<User> getUsers() {
        return userRepository.findAll();
    }

    // endpoint pro synchronizaci seznamu klientů s tlustými klienty při jejich hubnutí
    @RequestMapping(value = "/send", method = RequestMethod.POST)
    public @ResponseBody
    ResponseEntity<?> synchronizeUsers(@RequestBody List<User> users) {
        // synchronizace seznamu klientů zaslaných od klienta po zhubnutí se seznamem v DB na serveru
        try {
            for (User us : users) {
                // vytažení klienta z DB dle SSID (pokud existuje)
                Optional<User> userInDB = userRepository.findById(us.getSsid());
                if (userInDB.isPresent()) {
                    // pokud klient je již v DB -> update záznamu
                    updateUser(userInDB, us);
                } else {
                    // pokud není v DB -> vytvoření klienta
                    userRepository.save(us);
                }
            }
            return ResponseEntity.ok("Synchronizace se serverem proběhla úspěšně");
        } catch (Exception ex) {
            throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, "Při synchronizaci seznamu klientů " +
                    "se serverem došlo k chybě");
        }
    }

    // endpoint pro update kontextu daného klienta
    @RequestMapping(value = "/update", method = RequestMethod.PUT)
    public ResponseEntity<?> updateUser(@RequestBody User userToUpdate) {
        Optional<User> userInDB = userRepository.findById(userToUpdate.getSsid());
        // klient již musí být při updatu v DB
        if (userInDB.isPresent()) {
            // přepsání posledního času spojení se serverem
            Date now = CurrentTimeService.getCurrentTime();
            userToUpdate.setLastConnectionToServer(now);
            // update klienta
            updateUser(userInDB, userToUpdate);
            return ResponseEntity.ok("Klient úspěšně aktualizován");
        } else {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Neexistující klient");
        }
    }

    // endpoint pro vytvoření (registraci) klienta v systému
    @RequestMapping(value = "/create", method = RequestMethod.POST)
    public ResponseEntity<?> createUser(@RequestBody User userToCreate) {
        // délka ID v případě nezískání IMEI u klienta
        int LENGTH_OF_EMPTY_ID = 2;
        // vygenerování ID bude-li IMEI od klienta null
        String userID = userToCreate.getSsid();
        if (userID.length() <= LENGTH_OF_EMPTY_ID) {
            // vygenerování náhodného čísla (není moc pravděpodobné -> primitivní generování)
            Random random = new Random();
            int randomNumber = random.nextInt(10000);
            userID += randomNumber;
            userToCreate.setSsid(userID);
        }
        Date now = CurrentTimeService.getCurrentTime();
        // nastavení času prvního a posledního připojení k serveru
        userToCreate.setFirstConnectionToServer(now);
        userToCreate.setLastConnectionToServer(now);
        System.out.println("bool: " + userToCreate.getIsOnline());
        userRepository.save(userToCreate);
        return ResponseEntity.ok("Nový klient vytvořen");
    }

    // metoda pro aktualizaci klienta
    private void updateUser(Optional<User> userInDB, User userToUpdate) {
        // jediné hodnoty, které se mohou na klientovi změnit jsou: hodnoty senzorů, poloha a
        // aktuální stav (pro využití do budoucna)
        User userUpdated = userInDB.get(); // isPresent již v nadřazené metodě
        userUpdated.setActualState(userToUpdate.getActualState());
        // přepsání polohy pouze pokud není 0
        if (userToUpdate.getLatitude() != 0 && userToUpdate.getLongitude() != 0) {
            userUpdated.setLatitude(userToUpdate.getLatitude());
            userUpdated.setLongitude(userToUpdate.getLongitude());
        }
        // přepsání hodnot ze senzorů klienta (jsou-li zaslány)
        if (userToUpdate.getSensorInformation() != null) {
            // v případě, že již nějaká data ze senzorů klient obsahuje
            if (userUpdated.getSensorInformation() != null) {
                double temperature = userToUpdate.getSensorInformation().getTemperature();
                double pressure = userToUpdate.getSensorInformation().getPressure();
                userUpdated.getSensorInformation().setTemperature(temperature);
                userUpdated.getSensorInformation().setPressure(pressure);
            } else {
                userToUpdate.getSensorInformation().setUser(userInDB.get());
		        sensorInformationRepository.save(userToUpdate.getSensorInformation());
                userUpdated.setSensorInformation(userToUpdate.getSensorInformation());
            }
        }
        userRepository.save(userUpdated);
    }
}
