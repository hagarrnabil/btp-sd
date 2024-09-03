package com.example.btpsd.repositories;

import com.example.btpsd.model.ServiceInvoiceMain;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface ServiceInvoiceMainRepository extends CrudRepository<ServiceInvoiceMain, Long> {

    List<ServiceInvoiceMain> findByLineNumber(String lineNumber);
}
