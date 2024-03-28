package com.example.btpsd.repositories;

import com.example.btpsd.model.ServiceNumber;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface ServiceNumberRepository extends CrudRepository<ServiceNumber, Long> {

    @Query("select s from ServiceNumber s where lower(concat(s.description,' ',s.numberToBeConverted,' ',s.serviceNumberCode,' ',s.searchTerm,' ',s.baseUnitOfMeasurement,' ',s.convertedNumber" +
            ",' ',s.defaultUnitOfMeasurement,' ',s.toBeConvertedUnitOfMeasurement)) like lower(concat('%', ?1,'%'))")
    public List<ServiceNumber> search(String keyword);

}
