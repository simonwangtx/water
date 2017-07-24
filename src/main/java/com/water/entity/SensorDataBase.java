//package com.water.entity;
//
//
//import com.fasterxml.jackson.annotation.JsonFormat;
//import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
//import com.fasterxml.jackson.annotation.JsonProperty;
//
//import javax.persistence.Entity;
//import javax.persistence.GeneratedValue;
//import javax.persistence.GenerationType;
//import javax.persistence.Id;
//import java.sql.Timestamp;
//
///**
// * Created by guaguagua-mac on 7/15/17.
// */
//
//@Entity
//@JsonIgnoreProperties(ignoreUnknown = true)
//public abstract class SensorDataBase {
//
//    @Id @GeneratedValue(strategy = GenerationType.AUTO)
//    private Long id;
//
//    @JsonProperty("S_ID")
//    private Long sensorId;
//
//    @JsonProperty("T_TIME")
//    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
//    private Timestamp time;
//
//    @JsonProperty("T_DATA")
//    private Double waterLevels;
//
//    SensorDataBase() {
//    }
//
//    public SensorDataBase(Long sensorId, Timestamp time, Double waterLevels) {
//        this.sensorId = sensorId;
//        this.time = time;
//        this.waterLevels = waterLevels;
//    }
//
//    public Long getId() {
//        return id;
//    }
//
//    public void setId(Long id) {
//        this.id = id;
//    }
//
//    public Long getSensorId() {
//        return sensorId;
//    }
//
//    public void setSensorId(Long sensorId) {
//        this.sensorId = sensorId;
//    }
//
//    public Timestamp getTime() {
//        return time;
//    }
//
//    public void setTime(Timestamp time) {
//        this.time = time;
//    }
//
//    public Double getWaterLevels() {
//        return waterLevels;
//    }
//
//    public void setWaterLevels(Double waterLevels) {
//        this.waterLevels = waterLevels;
//    }
//}
