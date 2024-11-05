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

    List<ServiceInvoiceMain> findAllByExecutionOrderMainIsNull();

    List<ServiceInvoiceMain> findAllByExecutionOrderMain_ExecutionOrderMainCode(Long executionOrderMainCode);

    @Query("SELECT i FROM ServiceInvoiceMain i WHERE LOWER(i.temporaryDeletion) = LOWER(:temporaryDeletion)")
    List<ServiceInvoiceMain> findByTemporaryDeletion(@Param("temporaryDeletion") String temporaryDeletion);
}
