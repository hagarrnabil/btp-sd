package com.example.btpsd.repositories;

import com.example.btpsd.model.InvoiceMainItem;
import com.example.btpsd.model.ModelSpecificationsDetails;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

public interface InvoiceMainItemRepository extends CrudRepository<InvoiceMainItem, Long> {

    @Query("select i from InvoiceMainItem i where lower(concat(i.amountPerUnit,' ',i.description,' ',i.profitMargin,' ',i.quantity)) like lower(concat('%', ?1,'%'))")
    public List<InvoiceMainItem> search(String keyword);

    @Query("SELECT SUM(i.total) FROM InvoiceMainItem i")
    Double sumAllTotals();

    List<InvoiceMainItem> findByReferenceId(String referenceId);

//    @Query("SELECT i FROM InvoiceMainItem i WHERE LOWER(i.temporaryDeletion) = LOWER(:temporaryDeletion)")
//    List<InvoiceMainItem> findByTemporaryDeletion(@Param("temporaryDeletion") String temporaryDeletion);

}
