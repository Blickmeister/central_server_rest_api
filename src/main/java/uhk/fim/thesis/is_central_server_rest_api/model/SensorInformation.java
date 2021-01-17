package uhk.fim.thesis.is_central_server_rest_api.model;

import com.fasterxml.jackson.annotation.JsonIgnore;

import javax.persistence.*;

@Entity
@Table(name = "sensor_information")
public class SensorInformation {

    @Id
    @Column(name = "sensor_information_user_id")
    private String ssid;

    @Column(name = "sensor_information_temperature")
    private double temperature;

    @Column(name = "sensor_information_pressure")
    private double pressure;

    @OneToOne
    @MapsId
    @JsonIgnore
    private User user;

    public SensorInformation(String ssid, double temperature, double pressure, User user) {
        this.ssid = ssid;
        this.temperature = temperature;
        this.pressure = pressure;
        this.user = user;
    }

    public SensorInformation() {}

    public String getId() {
        return ssid;
    }

    public double getTemperature() {
        return temperature;
    }

    public double getPressure() {
        return pressure;
    }

    public User getUser() {
        return user;
    }

    public void setId(String id) {
        this.ssid = id;
    }

    public void setTemperature(double temperature) {
        this.temperature = temperature;
    }

    public void setPressure(double pressure) {
        this.pressure = pressure;
    }

    public void setUser(User user) {
        this.user = user;
    }
}
