package com.example.btpsd.repositories;

import com.example.btpsd.model.PersonnelNumber;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface PersonnelNumberRepository extends CrudRepository<PersonnelNumber, Long> {

    @Query("select p from PersonnelNumber p where lower(concat(p.description,' ', p.code,' ', p.personnelNumberCode)) like lower(concat('%', ?1,'%'))")
    public List<PersonnelNumber> search(String keyword);
}
