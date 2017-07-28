package com.water.service;

import com.water.entity.RegularData;
import com.water.entity.SensorData;
import com.water.global.Global;
import com.water.repository.RegularDataRepository;
import com.water.repository.SensorDataRepository;
import com.water.utils.ComFuncs;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;

@Service
public class RegularService {

    private final SensorDataRepository sensorDataRepository;

    private final RegularDataRepository regularDataRepository;

    @Autowired
    public RegularService(SensorDataRepository sensorDataRepository, RegularDataRepository regularDataRepository) {
        this.sensorDataRepository = sensorDataRepository;
        this.regularDataRepository = regularDataRepository;
    }

    @PostConstruct
    public void postConstruct() {
        generateRegularData();
    }

    private double getVelocity(double lastValue, double nowValue) {
        double v = (nowValue - lastValue) * 12;
        return v < 0 || v > 2000 ? 0 : v;
    }

    private void generateRegularData() {
        for (int i = 0; i < Global.sensorId.length; i++) {
            List<SensorData> sensorDataList =
                    sensorDataRepository.findAllBySensorId((long) Global.sensorId[i]);
            if (sensorDataList.isEmpty()) return;
            List<RegularData> regularDataList = new ArrayList<>();
            long startMilli = ComFuncs.getTimeMilli("2017-07-01 00:00:00");
            Timestamp startStamp = new Timestamp(startMilli);
            Timestamp endStamp = new Timestamp(startMilli + 5 * 60 * 1000);
            double valueRecord = 0;
            double lastValueRecord = sensorDataList.get(0).getRainGage();
            boolean blank = true;
            int j = 0;
            while (j < sensorDataList.size()) {
                ComFuncs.printLog(getClass(), "j : " + j);
                SensorData sensorData = sensorDataList.get(j);
                if (sensorData.getTime().before(startStamp)) {
                    lastValueRecord = sensorData.getRainGage();
                    j++;
                    continue;
                }
                if (sensorData.getTime().after(endStamp)) {
                    if (!blank) {
                        regularDataList.add(new RegularData(sensorData.getSensorId(),
                                endStamp, valueRecord, getVelocity(lastValueRecord, valueRecord)));
                        lastValueRecord = valueRecord;
                    } else {
                        regularDataList.add(new RegularData(sensorData.getSensorId(),
                                endStamp, valueRecord, 0));
                    }
                    startStamp = endStamp;
                    endStamp = new Timestamp(endStamp.getTime() + 5 * 60 * 1000);
                    blank = true;
                } else {
                    valueRecord = sensorData.getRainGage();
                    blank = false;
                    j++;
                }
            }
            ComFuncs.printLog(getClass(), "Start sensorId : " + Global.sensorId[i]);
            regularDataRepository.save(regularDataList);
            ComFuncs.printLog(getClass(), "End sensorId : " + Global.sensorId[i]);
        }
    }
}
