package com.example.btpsd.repositories;

import com.example.btpsd.model.UnitOfMeasurementCloud;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.CrudRepository;

public interface UomCloudRepository extends JpaRepository<UnitOfMeasurementCloud, Long> {
}
