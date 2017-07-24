package com.water;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.water.entity.SensorData;
import com.water.repository.SensorDataRepository;
import com.water.utils.ComFuncs;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

@Service
public class TestClass {

    @Autowired
    SensorDataRepository sensorDataRepository;
    List<SensorData> previousSensorList = new ArrayList<>();
    private int n = 5;

    @Scheduled(fixedRate = 500000)
    public void BindTaskMethod() {
        getWebData(6000);
        //getWebData(6010);
    }


    private boolean isValid(List<SensorData> sensorDatas) {
        double ratio = 0.1;
        int mid = sensorDatas.size() / 2 + 1;
        double avg = 0;
        for (SensorData sensorData: sensorDatas) {
            avg += sensorData.getRainGage();
        }
        avg /= sensorDatas.size();
        return !(sensorDatas.get(mid).getRainGage() > avg * (1 + ratio))
                && !(sensorDatas.get(mid).getRainGage() < avg * (1 - ratio));
    }


    private void getWebData(int sId) {
        if (sId == 0) sId = 6000;
        ObjectMapper objectMapper = new ObjectMapper();
        List<SensorData> dataList = new ArrayList<>();
        try {
            URL url = new URL("http://70.128.162.109/datawise/dataAPI/retrievedata.php?format=json&bydate=back&days=30&tstart=&tend=&cid=1&bytype=sensor&sid="
                    + sId);
            JsonNode root = objectMapper.readTree(url);
            JsonNode results = root.path("results");
            for(JsonNode result : results) {
                dataList.add(objectMapper.treeToValue(result, SensorData.class));
            }

//            dataList.clear();
//
//            int halfSize = n / 2;
//            dataList.sort(Comparator.comparing(SensorData::getTime));
//            for (int i = 0; i < dataList.size() - halfSize; i++) {
//                if (i < halfSize && previousSensorList.size() >= halfSize) {
//                    List<SensorData> subList = new ArrayList<>();
//                    subList.addAll(previousSensorList.subList(
//                            previousSensorList.size() - halfSize + i, previousSensorList.size() - 1)
//                    );
//                    subList.addAll(dataList.subList(0, i + halfSize));
//                    dataList.get(i).setIsValid(isValid(subList) ? 1 : 0);
//                } else {
//                    dataList.get(i).setIsValid(
//                            isValid(dataList.subList(i - halfSize, i + halfSize)) ? 1 : 0);
//                }
//            }
//            previousSensorList.clear();
//            previousSensorList.addAll(dataList);
            sensorDataRepository.save(dataList);
//            dataList.clear();
        } catch (IOException e) {
            e.printStackTrace();
            ComFuncs.printError(e.toString());
        }

        for (SensorData sensorData: dataList) {
            System.out.println(sensorData.getTime());
        }

//        Car car = objectMapper.readValue(url, Car.class);
    }
}
