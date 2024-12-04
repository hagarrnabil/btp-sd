package com.example.btpsd.repositories;

import com.example.btpsd.model.ServiceType;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface ServiceTypeRepository extends CrudRepository<ServiceType, Long> {
    @Query("select s from ServiceType s where lower(concat(s.description,' ', s.serviceTypeCode,' ', s.serviceId)) like lower(concat('%', ?1,'%'))")
    public List<ServiceType> search(String keyword);
}
