package com.example.btpsd.repositories;

import com.example.btpsd.model.ModelSpecificationsDetails;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface ModelSpecificationsDetailsRepository extends CrudRepository<ModelSpecificationsDetails, Long> {
    @Query("select m from ModelSpecificationsDetails m where lower(concat(m.modelSpecDetailsCode,' ',m.lineIndex,' ', m.shortText,' ',m.quantity,' ',m.grossPrice,' ',m.overFulfilmentPercentage,' '," +
            "m.pricePerUnitOfMeasurement,' ',m.overFulfilmentPercentage,' ',m.externalServiceNumber,' ', m.netValue,' ', m.serviceText,' ',m.lineText,' ',m.lineNumber,' ',m.alternatives" +
            ")) like lower(concat('%', ?1,'%'))")
    public List<ModelSpecificationsDetails> search(String keyword);
}
