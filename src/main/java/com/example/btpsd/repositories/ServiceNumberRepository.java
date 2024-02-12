package com.example.btpsd.repositories;

import com.example.btpsd.model.ServiceNumber;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface ServiceNumberRepository extends CrudRepository<ServiceNumber, Long> {

    @Query("SELECT s FROM ServiceNumber s WHERE CONCAT(s.code, ' ', s.description, ' ', s.serviceNumberCode,' ',s.convertedNumber" +
            ",' ',s.deletionIndicator,' ',s.lastChangeDate,' ',s.mainItem,' ',s.numberToBeConverted,' ',s.shortTextChangeAllowed) LIKE %?1%")
    public List<ServiceNumber> search(String keyword);

}
