package com.water.entity;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

import javax.persistence.*;
import java.sql.Timestamp;
import java.util.UUID;

/**
 * Created by guaguagua-mac on 7/15/17.
 */

@Entity
@JsonIgnoreProperties(ignoreUnknown = true)
@Table(name = "sensor_data")
public class SensorData {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    private String uuid = UUID.randomUUID().toString();

    @JsonProperty("S_ID")
    private Long sensorId;

    @JsonProperty("T_TIME")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private Timestamp time;

    @JsonProperty("T_DATA")
    private Double rainGage;

    private int isValid = 1;

    SensorData() {
    }

    public SensorData(Long sensorId, Timestamp time, Double rainGage) {
        this.sensorId = sensorId;
        this.time = time;
        this.rainGage = rainGage;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getSensorId() {
        return sensorId;
    }

    public void setSensorId(Long sensorId) {
        this.sensorId = sensorId;
    }

    public Timestamp getTime() {
        return time;
    }

    public void setTime(Timestamp time) {
        this.time = time;
    }

    public Double getRainGage() {
        return rainGage;
    }

    public void setRainGage(Double rainGage) {
        this.rainGage = rainGage;
    }

    public int getIsValid() {
        return isValid;
    }

    public void setIsValid(int isValid) {
        this.isValid = isValid;
    }

    @Override
    public String toString() {
        return sensorId + " " + isValid + " " + rainGage + " " + time;
    }
}
