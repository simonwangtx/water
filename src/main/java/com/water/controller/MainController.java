package com.water.controller;

import com.water.entity.Sensor;
import com.water.service.MainService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Controller
public class MainController {

    private final MainService mainService;

    @Autowired
    public MainController(MainService mainService) {
        this.mainService = mainService;
    }

//    @RequestMapping("/getHomeData")
//    public @ResponseBody
//    List<Sensor> findAll() {
//        return mainService.findAll();
//    }

    @RequestMapping("/getHomeData")
    public @ResponseBody
    Map<String, List<Sensor>> findAllBySensorId() {
        List<Sensor> sensorList = mainService.findAll();
        Map<String, List<Sensor>> map = new HashMap<>();
        map.put("sensorList", sensorList);
        return map;
    }

    @RequestMapping("/home")
    public @ResponseBody
    ModelAndView getIndex() {
        ModelAndView modelAndView = new ModelAndView();
        modelAndView.addObject("user", "Simon");
        modelAndView.setViewName("welcome");
        return modelAndView;
    }
}
