package uhk.fim.thesis.is_central_server_rest_api.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import uhk.fim.thesis.is_central_server_rest_api.service.CurrentTimeService;
import uhk.fim.thesis.is_central_server_rest_api.service.TestConnectionResponse;

import java.sql.Time;
import java.sql.Timestamp;

@RestController
@RequestMapping("/connection")
public class TestConnectionController {

    private TestConnectionResponse testConnectionResponse;

    @Autowired
    public TestConnectionController (TestConnectionResponse testConnectionResponse) {
        this.testConnectionResponse = testConnectionResponse;
    }

    @RequestMapping(value = "/test", method = RequestMethod.POST, consumes = MediaType.TEXT_PLAIN_VALUE)
    public String testConnection(@RequestBody String req) {
        // aktuální časové razítko pro výpočet upload
        // je třeba odečíst čas potřebný pro zpracování metody getCurrentTime()
        // chceme získat skutečný aktuální čas při obdržení datagramu od klienta
        long startTime = System.currentTimeMillis();
        Timestamp currentTime= CurrentTimeService.getCurrentTime();
        long endTime = System.currentTimeMillis();
        long duration = endTime - startTime;
        long milis = currentTime.getTime();
        Timestamp currentTimeAfterReceive = new Timestamp(milis - duration);

        req = req + "/" + currentTimeAfterReceive.toString();
        // čas odeslání datagramu ze serveru
        req = req + "/" + currentTime.toString();
	    System.out.println(req);

        return req;
    }
}
