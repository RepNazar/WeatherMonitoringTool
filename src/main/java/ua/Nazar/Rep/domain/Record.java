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

    /**
     * @return Author name
     */
    public String getAuthorName() {
        return author != null ? author.getUsername() : "<none>";
    }

    /**
     *
     */
    public Record() {
    }

    /**
     * @param date
     * @param temperature
     * @param windSpeed
     * @param windAngle
     * @param pressure
     * @param user
     */
    public Record(String date, String temperature, String windSpeed, String windAngle, String pressure, User user) {
        this.date = date;
        this.temperature = temperature;
        this.windSpeed = windSpeed;
        this.windAngle = windAngle;
        this.pressure = pressure;
        this.author = user;
    }

    /**
     * @return id
     */
    public Long getId() {
        return id;
    }

    /**
     * @param id
     */
    public void setId(Long id) {
        this.id = id;
    }

    /**
     * @return date
     */
    public String getDate() {
        return date;
    }

    /**
     * @param date
     */
    public void setDate(String date) {
        this.date = date;
    }

    /**
     * @return temperature
     */
    public String getTemperature() {
        return temperature;
    }

    /**
     * @param temperature
     */
    public void setTemperature(String temperature) {
        this.temperature = temperature;
    }

    /**
     * @return windSpeed
     */
    public String getWindSpeed() {
        return windSpeed;
    }

    /**
     * @param windSpeed
     */
    public void setWindSpeed(String windSpeed) {
        this.windSpeed = windSpeed;
    }

    /**
     * @return windAngle
     */
    public String getWindAngle() {
        return windAngle;
    }

    /**
     * @param windAngle
     */
    public void setWindAngle(String windAngle) {
        this.windAngle = windAngle;
    }

    /**
     * @return pressure
     */
    public String getPressure() {
        return pressure;
    }

    /**
     * @param pressure
     */
    public void setPressure(String pressure) {
        this.pressure = pressure;
    }

    /**
     * @return author
     */
    public User getAuthor() {
        return author;
    }

    /**
     * @param author
     */
    public void setAuthor(User author) {
        this.author = author;
    }
}
