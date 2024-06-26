package com.example.btpsd.repositories;

import com.example.btpsd.model.Invoice;
import org.springframework.data.repository.CrudRepository;

public interface InvoiceRepository extends CrudRepository<Invoice, Long> {
}
