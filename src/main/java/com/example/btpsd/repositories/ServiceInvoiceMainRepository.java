package com.example.btpsd.repositories;

import com.example.btpsd.model.ExecutionOrderMain;
import com.example.btpsd.model.InvoiceMainItem;
import com.example.btpsd.model.ServiceInvoiceMain;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;
import java.util.Set;

public interface ServiceInvoiceMainRepository extends CrudRepository<ServiceInvoiceMain, Long> {


    List<ServiceInvoiceMain> findByLineNumber(String lineNumber);

    Optional<ServiceInvoiceMain> findTopByExecutionOrderMainOrderByServiceInvoiceCodeDesc(ExecutionOrderMain executionOrderMain);

    Optional<ServiceInvoiceMain> findTopByOrderByServiceInvoiceCodeDesc();

    ServiceInvoiceMain findTopByExecutionOrderMainExecutionOrderMainCodeOrderByServiceInvoiceCodeDesc(Long executionOrderMainCode);

    Optional<ServiceInvoiceMain> findByReferenceId(String referenceId);

    @Query("SELECT COALESCE(SUM(s.actualQuantity), 0) FROM ServiceInvoiceMain s WHERE s.executionOrderMain.executionOrderMainCode = :executionOrderMainCode")
    double findPreviousActualQuantityByExecutionOrderMainCode(@Param("executionOrderMainCode") Long executionOrderMainCode);

    @Query("SELECT COALESCE(SUM(s.remainingQuantity), 0) FROM ServiceInvoiceMain s WHERE s.executionOrderMain.executionOrderMainCode = :executionOrderMainCode")
    double findPreviousRemainingQuantityByExecutionOrderMainCode(@Param("executionOrderMainCode") Long executionOrderMainCode);


}
