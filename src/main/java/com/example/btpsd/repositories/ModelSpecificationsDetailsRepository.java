package com.example.btpsd.repositories;

import com.example.btpsd.model.ModelSpecificationsDetails;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface ModelSpecificationsDetailsRepository extends CrudRepository<ModelSpecificationsDetails, Long> {

    @Query("SELECT m FROM ModelSpecificationsDetails m WHERE CONCAT(m.modelSpecDetailsCode,' ',m.lineIndex,' ',m.shortText,' ',m.quantity" +
            ",' ',m.grossPrice,' ',m.overFulfilmentPercentage,' ',m.pricePerUnitOfMeasurement,' ',m.externalServiceNumber,' ',m.netValue" +
            ",' ',m.serviceText,' ',m.lineText,' ',m.lineNumber,' ',m.alternatives) LIKE %?1%")
    public List<ModelSpecificationsDetails> search(String keyword);
}
