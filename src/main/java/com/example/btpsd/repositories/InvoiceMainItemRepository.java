package com.example.btpsd.repositories;

import com.example.btpsd.model.InvoiceMainItem;
import com.example.btpsd.model.ModelSpecificationsDetails;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface InvoiceMainItemRepository extends CrudRepository<InvoiceMainItem, Long> {

    @Query("select i from InvoiceMainItem i where lower(concat(i.amountPerUnit,' ',i.description,' ',i.profitMargin,' ',i.quantity)) like lower(concat('%', ?1,'%'))")
    public List<InvoiceMainItem> search(String keyword);
}
