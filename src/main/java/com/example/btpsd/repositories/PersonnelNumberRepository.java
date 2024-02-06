package com.example.btpsd.repositories;

import com.example.btpsd.model.PersonnelNumber;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface PersonnelNumberRepository extends CrudRepository<PersonnelNumber, Long> {

    @Query("SELECT p FROM PersonnelNumber p WHERE CONCAT(p.description, ' ', p.code, ' ', p.personnelNumberCode) LIKE %?1%")
    public List<PersonnelNumber> search(String keyword);
}
