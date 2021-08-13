package uhk.fim.thesis.is_central_server_rest_api.service;

import org.apache.commons.net.ntp.NTPUDPClient;
import org.apache.commons.net.ntp.TimeInfo;

import java.io.IOException;
import java.net.InetAddress;
import java.net.SocketException;
import java.net.UnknownHostException;
import java.sql.Timestamp;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.util.Date;

/**
 * @author Bc. Ondřej Schneider - FIM UHK
 * @version 1.0
 * @since 2021-04-02
 * Služba pro interní potřeby centrálního serveru (pro UserController a TestConnectionController)
 * konkrétně pro získání aktuálního času (ať již využitím NTP serveru či tradiční cestou)
 */
public class CurrentTimeService {

    private static final String TIME_SERVER = "ntp.nic.cz";

    // metoda pro získání aktuálního času z NTP serveru
    public static Timestamp getCurrentTimeFromNTPServer() {
        NTPUDPClient timeClient = new NTPUDPClient();
        timeClient.setDefaultTimeout(4000); // pokud se nepodaří připojit po limitu se přestane snažit
        InetAddress inetAddress;
        TimeInfo timeInfo = null;
        try {
            timeClient.open(); // otevřít socket pro komunikaci
            // pokus o připojení je potřeba opakovat
            for (int i = 0; i < 4; i++) {
                try {
                    inetAddress = InetAddress.getByName(TIME_SERVER); // získání NTP serveru
                    timeInfo = timeClient.getTime(inetAddress); // získání času ze serveru
                } catch (UnknownHostException uhe) {
                    System.out.println("Adresa NTP serveru je neznámá: " + uhe);
                } catch (IOException ioe) {
                    System.out.println("Nepodařilo se získat čas ze serveru: " + ioe);
                }
                if (timeInfo != null) {
                    // získaný čas ze serveru
                    return new Timestamp(timeInfo.getMessage().getTransmitTimeStamp().getTime());
                }
            }
        } catch (SocketException se) {
            System.out.println("Nepodařilo se otevřít socket pro komunikaci s NTP serverem: " + se);
        }

        timeClient.close(); // zavřít socket a ukončit komunikaci
        // pokud se nepodaří získat čas ze serveru je použit systémový čas zařízení
        return new Timestamp(System.currentTimeMillis());
    }

    // metoda pro získání aktuálního času pro potřeby zapsání času připojení k serveru v UserController
    // v tomto případě není nutná sychronizace času u klienta a serveru -> volnější podmínky než u datové propustnosti
    public static Date getCurrentTime() {
        Date nowBadFormat = new Date();
        DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm");
        String nowInString = dateFormat.format(nowBadFormat);
        Date now = null;
        try {
            now = dateFormat.parse(nowInString);
        } catch (ParseException e) {
            System.out.println("Nepodařilo se zapsat čas připojení k serveru: ");
            e.printStackTrace();
        }
        return now;
    }
}
