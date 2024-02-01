package com.example.btpsd.repositories;

import com.example.btpsd.commands.CurrencyCommand;
import com.example.btpsd.model.Currency;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface CurrencyRepository extends CrudRepository<Currency, Long> {
    @Query("SELECT c FROM  c WHERE CONCAT(c.citiesID, ' ', c.cities_code, ' ', c.regionalLocation) LIKE %?1%")
    public List<CurrencyCommand> search(String keyword);
}
