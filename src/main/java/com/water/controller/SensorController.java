package com.water.controller;

import com.water.entity.SensorData;
import com.water.repository.SensorDataRepository;
import com.water.service.SensorService;
import com.water.utils.ComFuncs;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * Created by guaguagua-mac on 7/15/17.
 */

@Controller
public class SensorController {

    @Autowired
    private SensorDataRepository sensorDataRepository;

    @Autowired
    private SensorService sensorService;

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

    @GetMapping(path = "/test/get/{sensorId}")
    public @ResponseBody
    List<SensorData> getSensorDataBySensorId(@PathVariable Long sensorId) {
        ComFuncs.printLog(getClass(), "In Controller getSensorDataBySensorId(" + sensorId + ")");
        return sensorService.findSensorDataBySensorId(sensorId);
    }
}
