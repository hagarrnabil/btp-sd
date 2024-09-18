package com.example.btpsd.repositories;

import com.example.btpsd.model.ExecutionOrderMain;
import com.example.btpsd.model.ServiceInvoiceMain;
import org.springframework.data.repository.CrudRepository;

import java.util.List;
import java.util.Optional;

public interface ServiceInvoiceMainRepository extends CrudRepository<ServiceInvoiceMain, Long> {

    Optional<ServiceInvoiceMain> findByExecutionOrderMain(ExecutionOrderMain executionOrderMain);

    List<ServiceInvoiceMain> findByLineNumber(String lineNumber);
}
