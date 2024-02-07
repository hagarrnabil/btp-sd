package com.example.btpsd.repositories;

import com.example.btpsd.model.ModelSpecifications;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface ModelSpecificationRepository extends CrudRepository<ModelSpecifications, Long> {

    @Query("SELECT m FROM ModelSpecifications m WHERE CONCAT(m.searchTerm,' ',m.modelSpecCode,' ',m.description,' ',m.modelServSpec) LIKE %?1%")
    public List<ModelSpecifications> search(String keyword);

}
