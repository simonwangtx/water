package com.water;

import com.water.service.SensorService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;

import javax.annotation.PostConstruct;

@SpringBootApplication
@EnableScheduling
public class WaterApplication {

//    private final SensorService sensorService;

//    @Autowired
//    public WaterApplication(SensorService sensorService) {
//        this.sensorService = sensorService;
//    }

    public static void main(String[] args) {
		SpringApplication.run(WaterApplication.class, args);
	}

//	@PostConstruct
//	public void init() {
//        sensorService.getWebData();
//    }
}
