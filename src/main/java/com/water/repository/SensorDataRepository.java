package com.water.repository;

import com.water.entity.SensorData;
import org.springframework.data.repository.CrudRepository;

import java.sql.Timestamp;
import java.util.List;

/**
 * Created by guaguagua-mac on 7/15/17.
 */

public interface SensorDataRepository extends CrudRepository<SensorData, Long> {

    List<SensorData> findAllBySensorId(Long sensorId);

    List<SensorData> findAll();

    List<SensorData> findSensorDataByTimeAfter(Timestamp timestamp);

    SensorData findFirstBySensorIdOrderByTimeDesc(Long sensorId);

}
