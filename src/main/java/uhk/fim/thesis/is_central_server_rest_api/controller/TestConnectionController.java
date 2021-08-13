package uhk.fim.thesis.is_central_server_rest_api.controller;

import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import uhk.fim.thesis.is_central_server_rest_api.service.CurrentTimeService;

import java.sql.Timestamp;
import java.util.concurrent.TimeUnit;

/**
 * @author Bc. Ondřej Schneider - FIM UHK
 * @version 1.0
 * @since 2021-04-02
 * Controller (API) pro účely vyhodnocení datové propustnosti mezi centrálním serverem a hybridním klientem
 */
@RestController
@RequestMapping("/connection")
public class TestConnectionController {

    @RequestMapping(value = "/test", method = RequestMethod.POST, consumes = MediaType.TEXT_PLAIN_VALUE)
    public String testConnection(@RequestBody String req) {
        // aktuální časové razítko pro výpočet upload
        // je třeba odečíst čas potřebný pro zpracování metody getCurrentTime()
        // chceme získat skutečný aktuální čas při obdržení datagramu od klienta
        long startTime = System.nanoTime();
        Timestamp currentTime = CurrentTimeService.getCurrentTimeFromNTPServer();
        long endTime = System.nanoTime();
        long durationInNano = endTime - startTime;
        long duration = TimeUnit.NANOSECONDS.toMillis(durationInNano);
        long milis = currentTime.getTime();
        Timestamp currentTimeAfterReceive = new Timestamp(milis - duration);

        req = req + "/" + currentTimeAfterReceive.toString();
        // čas bez odečtu pro odeslání datagramu ze serveru (pro výpočet download)
        req = req + "/" + currentTime.toString();
        System.out.println(req);

        return req;
    }
}
