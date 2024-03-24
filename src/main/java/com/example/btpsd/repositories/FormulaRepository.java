package com.example.btpsd.repositories;

import com.example.btpsd.model.Currency;
import com.example.btpsd.model.Formula;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface FormulaRepository extends CrudRepository<Formula, Long> {
    @Query("select f from Formula f where lower(concat(f.description,' ', f.formulaCode,' ', f.formula,' ',f.numberOfParameters,' ',f.formulaLogic,' ',f.result)) like lower(concat('%', ?1,'%'))")
    public List<Formula> search(String keyword);

}
