package ua.Nazar.Rep.domain;

import org.hibernate.validator.constraints.Length;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;

@Entity
@Table(name = "monitoring_history")
public class Record {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @NotBlank(message = "Date cannot be empty")
    @Length(max = 255, message = "Date too long")
    private String date;

    @NotBlank(message = "Temperature cannot be empty")
    @Length(max = 255, message = "Temperature too long")
    private String temperature;

    @NotBlank(message = "Wind Speed cannot be empty")
    @Length(max = 255, message = "Wind Speed too long")
    private String windSpeed;

    @NotBlank(message = "Wind Angle cannot be empty")
    @Length(max = 255, message = "Wind Angle too long")
    private String windAngle;

    @NotBlank(message = "Pressure cannot be empty")
    @Length(max = 255, message = "Pressure too long")
    private String pressure;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "user_id")
    private User author;

    public String getAuthorName() {
        return author != null ? author.getUsername() : "<none>";
    }

    public Record() {
    }

    public Record(String date, String temperature, String windSpeed, String windAngle, String pressure, User user) {
        this.date = date;
        this.temperature = temperature;
        this.windSpeed = windSpeed;
        this.windAngle = windAngle;
        this.pressure = pressure;
        this.author = user;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getTemperature() {
        return temperature;
    }

    public void setTemperature(String temperature) {
        this.temperature = temperature;
    }

    public String getWindSpeed() {
        return windSpeed;
    }

    public void setWindSpeed(String windSpeed) {
        this.windSpeed = windSpeed;
    }

    public String getWindAngle() {
        return windAngle;
    }

    public void setWindAngle(String windAngle) {
        this.windAngle = windAngle;
    }

    public String getPressure() {
        return pressure;
    }

    public void setPressure(String pressure) {
        this.pressure = pressure;
    }

    public User getAuthor() {
        return author;
    }

    public void setAuthor(User author) {
        this.author = author;
    }
}
