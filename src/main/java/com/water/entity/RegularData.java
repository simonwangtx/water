package com.water.entity;


import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import javax.persistence.*;
import java.sql.Timestamp;

@Entity
@JsonIgnoreProperties(ignoreUnknown = true)
@Table(name = "regular_data_5")
public class RegularData extends RegularDataBase {

    public RegularData(Long sensorId, Timestamp time, double valueRecord, double intensity) {
        this.sensorId = sensorId;
        this.time = time;
        this.valueRecord = valueRecord;
        this.intensity = intensity;
    }

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    private Long sensorId;

    private Timestamp time;

    private double valueRecord;

    private double intensity;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Timestamp getTime() {
        return time;
    }

    public void setTime(Timestamp time) {
        this.time = time;
    }

    public double getValueRecord() {
        return valueRecord;
    }

    public void setValueRecord(double valueRecord) {
        this.valueRecord = valueRecord;
    }

    public double getIntensity() {
        return intensity;
    }

    public void setIntensity(double intensity) {
        this.intensity = intensity;
    }

    public Long getSensorId() {
        return sensorId;
    }

    public void setSensorId(Long sensorId) {
        this.sensorId = sensorId;
    }
}
