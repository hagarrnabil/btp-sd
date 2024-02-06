package com.example.btpsd.repositories;

import com.example.btpsd.model.ServiceType;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface ServiceTypeRepository extends CrudRepository<ServiceType, Long> {

    @Query("SELECT s FROM ServiceType s WHERE CONCAT(s.description, ' ', s.serviceId, ' ', s.serviceTypeCode) LIKE %?1%")
    public List<ServiceType> search(String keyword);
}
