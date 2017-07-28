package com.water.service;

import com.water.entity.RegularData;
import com.water.entity.RegularData15;
import com.water.entity.RegularData60;
import com.water.entity.SensorData;
import com.water.global.Global;
import com.water.repository.RegularDataRepository;
import com.water.repository.RegularDataRepository15;
import com.water.repository.RegularDataRepository60;
import com.water.repository.SensorDataRepository;
import com.water.utils.ComFuncs;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;

@Service
public class RegularService {

    private final SensorDataRepository sensorDataRepository;

    private final List<CrudRepository> regularDataRepositoryList =
            new ArrayList<>();


    @Autowired
    public RegularService(SensorDataRepository sensorDataRepository,
                          RegularDataRepository regularDataRepository,
                          RegularDataRepository15 regularDataRepository15,
                          RegularDataRepository60 regularDataRepository60) {
        this.sensorDataRepository = sensorDataRepository;
        regularDataRepositoryList.add(regularDataRepository);
        regularDataRepositoryList.add(regularDataRepository15);
        regularDataRepositoryList.add(regularDataRepository60);
    }

    @PostConstruct
    public void postConstruct() {
        if (Global.regular)
            generateRegularData();
    }

    private double getVelocity(double lastValue, double nowValue) {
        double v = (nowValue - lastValue) * 12;
        return v < 0 || v > 2000 ? 0 : v;
    }

    private void generateRegularData() {
        List<RegularData> regularDataList = new ArrayList<>();
        List<RegularData15> regularData15List = new ArrayList<>();
        List<RegularData60> regularData60List = new ArrayList<>();

        for (int t = 1; t < Global.timeInterval.length; t++) {
            long timeInterval = Global.timeInterval[t];
            for (int i = 0; i < Global.sensorId.length; i++) {
                List<SensorData> sensorDataList =
                        sensorDataRepository.findAllBySensorId((long) Global.sensorId[i]);
                if (sensorDataList.isEmpty()) return;
                long startMilli = ComFuncs.getTimeMilli("2017-07-01 00:00:00");
                Timestamp startStamp = new Timestamp(startMilli);
                Timestamp endStamp = new Timestamp(startMilli + timeInterval);
                double valueRecord = 0;
                double lastValueRecord = sensorDataList.get(0).getRainGage();
                boolean blank = true;
                int j = 0;
                while (j < sensorDataList.size()) {
                    SensorData sensorData = sensorDataList.get(j);
                    if (sensorData.getTime().before(startStamp)) {
                        lastValueRecord = sensorData.getRainGage();
                        j++;
                        continue;
                    }
                    if (sensorData.getTime().after(endStamp)) {
                        if (!blank) {
                            if (t == 0) {
                                regularDataList.add(new RegularData(sensorData.getSensorId(),
                                        endStamp, valueRecord, getVelocity(lastValueRecord, valueRecord)));
                            } else if (t == 1) {
                                regularData15List.add(new RegularData15(sensorData.getSensorId(),
                                        endStamp, valueRecord, getVelocity(lastValueRecord, valueRecord)));
                            } else if (t == 2) {
                                regularData60List.add(new RegularData60(sensorData.getSensorId(),
                                        endStamp, valueRecord, getVelocity(lastValueRecord, valueRecord)));
                            }
                            lastValueRecord = valueRecord;
                        } else {
                            if (t == 0) {
                                regularDataList.add(new RegularData(sensorData.getSensorId(),
                                        endStamp, valueRecord, 0));
                            } else if (t == 1) {
                                regularData15List.add(new RegularData15(sensorData.getSensorId(),
                                        endStamp, valueRecord, 0));
                            } else if (t == 2) {
                                regularData60List.add(new RegularData60(sensorData.getSensorId(),
                                        endStamp, valueRecord, 0));
                            }
                        }
                        startStamp = endStamp;
                        endStamp = new Timestamp(endStamp.getTime() + timeInterval);
                        blank = true;
                    } else {
                        valueRecord = sensorData.getRainGage();
                        blank = false;
                        j++;
                    }
                }
                ComFuncs.printLog(getClass(), "Time interval is : " + (int) (timeInterval / 60000));
                ComFuncs.printLog(getClass(),
                        "Start inserting data from sensorId : " + Global.sensorId[i] + "...");
                if (t == 0) {
                    regularDataRepositoryList.get(t).save(regularDataList);
                } else if (t == 1) {
                    regularDataRepositoryList.get(t).save(regularData15List);
                } else if (t == 2) {
                    regularDataRepositoryList.get(t).save(regularData60List);
                }
                ComFuncs.printLog(getClass(), "Successfully finished!");
            }
        }
    }
}
