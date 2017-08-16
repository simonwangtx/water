package com.water.controller;

import com.water.entity.RegularData;
import com.water.service.RegularDataService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@Controller
public class RegularController {

    private final RegularDataService regularDataService;

    @Value("${welcome.message}")
    private String message;

    @Autowired
    public RegularController(RegularDataService regularDataService) {
        this.regularDataService = regularDataService;
    }

    @RequestMapping("/index")
    public String welcome(Map<String, Object> model) {
        model.put("message", this.message);
        return "welcome";
    }

    @RequestMapping("/greeting")
    public String greeting(@RequestParam(value = "name", required = false, defaultValue = "World")
                                   String name, Model model) {
        model.addAttribute("name", name);
        return "greeting";
    }

    @RequestMapping("/demo")
    public String demo(Map<String, Object> model) {
        model.put("message", this.message);
        return "demo";
    }


    @GetMapping(path = "/normal/regular/{sensorId}")
    public @ResponseBody
    List<RegularData> getRegularDataBySensorId(@PathVariable Long sensorId) {
        return regularDataService.findIntensityBySensorId(sensorId);
    }
}
