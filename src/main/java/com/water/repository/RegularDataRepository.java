package com.water.repository;

import com.water.entity.RegularData;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface RegularDataRepository extends CrudRepository<RegularData, Long> {

    List<RegularData> findAllBySensorId(Long sensorId);
}
