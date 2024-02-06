package com.example.btpsd.repositories;

import com.example.btpsd.model.Currency;
import com.example.btpsd.model.MaterialGroup;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface MaterialGroupRepository extends CrudRepository<MaterialGroup, Long> {

    @Query("SELECT m FROM MaterialGroup m WHERE CONCAT(m.code, ' ', m.description, ' ', m.materialGroupCode) LIKE %?1%")
    public List<MaterialGroup> search(String keyword);
}
