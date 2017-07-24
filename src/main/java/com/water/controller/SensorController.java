package com.water.controller;

import com.water.entity.SensorData;
import com.water.repository.SensorDataRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

/**
 * Created by guaguagua-mac on 7/15/17.
 */

@Controller
public class SensorController {

    @Autowired
    private SensorDataRepository sensorDataRepository;

    @GetMapping(path = "/test/add/{info}")
    public @ResponseBody String showInfo(@PathVariable String info) {
        return info;
    }

    @GetMapping(path = "/test/show")
    public @ResponseBody String showInfo() {
        return "Welcome!";
    }

    @PostMapping(path="/add")
    public @ResponseBody String addNewSensorData(@RequestBody SensorData sensorData) {
        sensorDataRepository.save(sensorData);
        return "Saved";
    }
}
