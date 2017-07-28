package com.water.repository;

import com.water.entity.SensorData;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

/**
 * Created by guaguagua-mac on 7/15/17.
 */

public interface SensorDataRepository extends CrudRepository<SensorData, Long> {

    List<SensorData> findAllBySensorId(Long sensorId);

    List<SensorData> findAll();

}
