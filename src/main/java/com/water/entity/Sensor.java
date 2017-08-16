package com.water.entity;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import javax.persistence.*;

@Entity
@JsonIgnoreProperties(ignoreUnknown = true)
@Table(name = "sensor")
public class Sensor {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    private Long sensorId;

    private Double rainGage;

    private Double Lon;

    private Double Lat;

    private String address;

    Sensor() {
    }

    ;
}
