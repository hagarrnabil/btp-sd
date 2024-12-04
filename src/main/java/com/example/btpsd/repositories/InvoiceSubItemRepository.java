package com.example.btpsd.repositories;

import com.example.btpsd.model.InvoiceSubItem;
import com.example.btpsd.model.ModelSpecificationsDetails;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface InvoiceSubItemRepository extends CrudRepository<InvoiceSubItem, Long> {

    @Query("select i from InvoiceSubItem i where lower(concat(i.total,' ',i.quantity,' ',i.description,' ',i.amountPerUnit)) like lower(concat('%', ?1,'%'))")
    public List<InvoiceSubItem> search(String keyword);

}
