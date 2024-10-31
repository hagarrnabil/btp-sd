package com.example.btpsd.repositories;

import com.example.btpsd.model.ExecutionOrderMain;
import com.example.btpsd.model.InvoiceMainItem;
import com.example.btpsd.model.ServiceInvoiceMain;
import org.springframework.data.repository.CrudRepository;

import java.util.List;
import java.util.Optional;

public interface ExecutionOrderMainRepository extends CrudRepository<ExecutionOrderMain, Long> {

    List<ExecutionOrderMain> findByLineNumber(String lineNumber);

    Optional<ExecutionOrderMain> findByServiceNumberCode(Long serviceNumberCode);

    List<ExecutionOrderMain> findByReferenceId(String referenceId);
}
