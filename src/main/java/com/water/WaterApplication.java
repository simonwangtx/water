package com.water;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;

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
