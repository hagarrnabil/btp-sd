package com.example.btpsd.repositories;

import com.example.btpsd.model.ModelSpecifications;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface ModelSpecificationRepository extends CrudRepository<ModelSpecifications, Long> {
    @Query("select m from ModelSpecifications m where lower(concat(m.searchTerm,' ', m.modelSpecCode,' ', m.description,' ',m.modelServSpec,' ',m.currencyCode,' ',m.modelSpecDetailsCode)) " +
            "like lower(concat('%', ?1,'%'))")
    public List<ModelSpecifications> search(String keyword);

}
