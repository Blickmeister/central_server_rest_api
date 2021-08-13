package uhk.fim.thesis.is_central_server_rest_api.model;

import com.fasterxml.jackson.annotation.JsonFormat;
import org.springframework.format.annotation.DateTimeFormat;

import javax.persistence.*;
import java.util.Date;

/**
 * @author Bc. Ondřej Schneider - FIM UHK
 * @version 1.0
 * @since 2021-04-02
 * Modelová třída či Entita (pro persistentní uložení v rámci Data JPA) pro informace o hybridních klientech
 * (pro potřeby IS i řídící a komunukační funkce)
 */
@Entity
@Table(name = "user")
public class User {
    @Id
    @Column(name = "user_ssid")
    private String ssid;

    @Column(name = "user_latitude")
    private double latitude;

    @Column(name = "user_longitude")
    private double longitude;

    @Column(name = "user_is_online", columnDefinition = "BOOLEAN", nullable = false)
    private boolean isOnline;

    @Column(name = "user_actual_state")
    private String actualState;

    @Column(name = "user_future_state")
    private String futureState;

    @Column(name = "user_first_conn_to_server")
    @DateTimeFormat(pattern = "yyyy-MM-dd'T'HH:mm")
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd'T'HH:mm")
    private Date firstConnectionToServer;

    @Column(name = "user_last_conn_to_server")
    @DateTimeFormat(pattern = "yyyy-MM-dd'T'HH:mm")
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd'T'HH:mm")
    private Date lastConnectionToServer;


    @OneToOne(mappedBy = "user", cascade = CascadeType.ALL)
    private SensorInformation sensorInformation;

    public User(String ssid, double latitude, double longitude, boolean isOnline, String actualState,
                String futureState, Date firstConnectionToServer, Date lastConnectionToServer,
                SensorInformation sensorInformation) {
        this.ssid = ssid;
        this.latitude = latitude;
        this.longitude = longitude;
        this.isOnline = isOnline;
        this.actualState = actualState;
        this.futureState = futureState;
        this.firstConnectionToServer = firstConnectionToServer;
        this.lastConnectionToServer = lastConnectionToServer;
        this.sensorInformation = sensorInformation;
    }

    public User() {}

    public String getSsid() {
        return ssid;
    }

    public double getLatitude() {
        return latitude;
    }

    public double getLongitude() {
        return longitude;
    }

    public boolean getIsOnline() {
        return isOnline;
    }

    public String getActualState() {
        return actualState;
    }

    public String getFutureState() {
        return futureState;
    }

    public Date getFirstConnectionToServer() {
        return firstConnectionToServer;
    }

    public Date getLastConnectionToServer() {
        return lastConnectionToServer;
    }

    public SensorInformation getSensorInformation() {
        return sensorInformation;
    }

    public void setSsid(String ssid) {
        this.ssid = ssid;
    }

    public void setLatitude(double latitude) {
        this.latitude = latitude;
    }

    public void setLongitude(double longitude) {
        this.longitude = longitude;
    }

    public void setIsOnline(boolean online) {
        isOnline = online;
    }

    public void setActualState(String actualState) {
        this.actualState = actualState;
    }

    public void setFutureState(String futureState) {
        this.futureState = futureState;
    }

    public void setFirstConnectionToServer(Date firstConnectionToServer) {
        this.firstConnectionToServer = firstConnectionToServer;
    }

    public void setLastConnectionToServer(Date lastConnectionToServer) {
        this.lastConnectionToServer = lastConnectionToServer;
    }

    public void setSensorInformation(SensorInformation sensorInformation) {
        this.sensorInformation = sensorInformation;
    }
}
