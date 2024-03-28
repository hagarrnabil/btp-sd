package com.example.btpsd.repositories;

import com.example.btpsd.model.ModelSpecificationsDetails;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface ModelSpecificationsDetailsRepository extends CrudRepository<ModelSpecificationsDetails, Long> {

    @Query("select m from ModelSpecificationsDetails m where lower(concat(m.serviceText,' ',m.alternatives,' ',m.lineNumber,' ',m.lineText,' ',m.externalServiceNumber,' '," +
            "m.shortText,' ',m.lineIndex,' ',m.unitOfMeasurementCode)) like lower(concat('%', ?1,'%'))")
    public List<ModelSpecificationsDetails> search(String keyword);
}
