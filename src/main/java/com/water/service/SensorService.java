package com.water.service;

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
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

/**
 * Created by guaguagua-mac on 7/15/17.
 */

@Service
public class SensorService {

    @Autowired
    SensorDataRepository sensorDataRepository;
    List<SensorData> previousDataList = new ArrayList<>();
    private int n = 5;
    private int[] sensorId = new int[] {
            6000, 6010, 6020, 6030, 6040, 6050, 6060, 6070, 6080, 6100, 6110, 6120, 6130,
            6140, 6150, 6160, 6170, 6190, 6306, 6500, 6520, 6610, 6630, 6650, 6690
    };

    @Scheduled(fixedRate = 900000)
    public void BindTaskMethod() {
        for (int i = 0; i < sensorId.length; i++)
            getWebData(sensorId[i]);
        //getWebData(6010);
    }

//    public boolean isValid(List<SensorData> l1, int st1, int end1, List<SensorData> l2, int st2, int end2) {
//        if (end1 - st1 + 1 + end2 -st2 + 1 !)
//    }

    private List<SensorData> getFilteredData(List<SensorData> sensorDataList, Timestamp lastTime) {
        return sensorDataList.stream().filter(
                sensorData -> sensorData.getTime().after(lastTime)).collect(Collectors.toList());
    }

    private boolean isValid(List<SensorData> sensorDatas) {
        double ratio = 1;
        int mid = sensorDatas.size() / 2;
        int inValidNum = 0;
        double avg = 0;
        for (SensorData sensorData: sensorDatas) {

            // Only use the valid data to calculate avg
            if (sensorData.getIsValid() == 0)
                inValidNum ++;
            else
                avg += sensorData.getRainGage();
        }
        if (sensorDatas.size() - inValidNum != 0)
            avg /= (sensorDatas.size() - inValidNum);
        else
            return false;
        return !(sensorDatas.get(mid).getRainGage() > avg * (1 + ratio))
                && !(sensorDatas.get(mid).getRainGage() < avg * (1 - ratio));
    }


    private void getWebData(int sId) {
        if (sId == 0) sId = 6000;
        ObjectMapper objectMapper = new ObjectMapper();
        List<SensorData> dataList = new ArrayList<>();
        previousDataList.clear();
        try {

            //Get data from url, then save it into dataList;
            URL url = new URL("http://70.128.162.109/datawise/dataAPI/retrievedata.php?format=json&bydate=back&days=30&tstart=&tend=&cid=1&bytype=sensor&sid="
                    + sId);
            JsonNode root = objectMapper.readTree(url);
            JsonNode results = root.path("results");
            for(JsonNode result : results) {
                dataList.add(objectMapper.treeToValue(result, SensorData.class));
            }

            /* If we have previous data that didn't be stored into database,
               then add them to the current dataList. */
            if (previousDataList.size() > n / 2) {
                dataList.addAll(0, previousDataList.subList(
                        previousDataList.size() - 1 - n / 2, previousDataList.size() - 1)
                );
            }

            /* Procedure of check the valid status of each sensor rain gage */
            int halfSize = n / 2;
            dataList.sort(Comparator.comparing(SensorData::getTime)); // Sort dataList in ascend
            for (int i = 0; i < dataList.size() - halfSize; i++) {

                // If the current data is in the [0..n/2] interval,
                // then we need the previous data to check the status
                if (i < halfSize) {
                    if (previousDataList.size() >= halfSize) {
                        List<SensorData> subList = new ArrayList<>();
                        subList.addAll(previousDataList.subList(
                                previousDataList.size() - halfSize + i, previousDataList.size() - 1)
                        );
                        subList.addAll(dataList.subList(0, i + halfSize));
                        dataList.get(i).setIsValid(isValid(subList) ? 1 : 0);
                    }

                // Otherwise we could check the status only by this-time data.
                } else {
                    dataList.get(i).setIsValid(
                            isValid(dataList.subList(i - halfSize, i + halfSize)) ? 1 : 0);
                }
            }

            // Update the previous dataList by this-time dataList
            previousDataList.clear();
            previousDataList.addAll(dataList);

            // Store the data to the database
            sensorDataRepository.save(dataList.subList(0, dataList.size() - n / 2 - 1));
        } catch (IOException e) {
            e.printStackTrace();
            ComFuncs.printError(e.toString());
        }

        for (SensorData sensorData: dataList) {
            System.out.println(sensorData.toString());
        }
        dataList.clear();
    }
}
