package com.example.btpsd.repositories;

import com.example.btpsd.model.LineType;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface LineTypeRepository extends CrudRepository<LineType, Long> {

    @Query("select l from LineType l where lower(concat(l.code,' ', l.lineTypeCode,' ', l.description)) like lower(concat('%', ?1,'%'))")
    public List<LineType> search(String keyword);

}
