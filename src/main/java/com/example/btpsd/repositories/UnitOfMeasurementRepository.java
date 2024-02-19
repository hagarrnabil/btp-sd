package com.example.btpsd.repositories;

import com.example.btpsd.model.UnitOfMeasurement;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface UnitOfMeasurementRepository extends CrudRepository<UnitOfMeasurement, Long>{

    @Query("SELECT u FROM UnitOfMeasurement u WHERE CONCAT(u.description, ' ', u.unitOfMeasurementCode, ' ', u.code) LIKE %?1%")
    public List<UnitOfMeasurement> search(String keyword);

}
