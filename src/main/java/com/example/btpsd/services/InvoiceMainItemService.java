package com.example.btpsd.services;

import com.example.btpsd.commands.InvoiceMainItemCommand;
import com.example.btpsd.dto.SalesOrderToMainitemDTO;
import com.example.btpsd.model.InvoiceMainItem;
import com.example.btpsd.repositories.InvoiceMainItemRepository;

import java.util.Set;

public interface InvoiceMainItemService {

    InvoiceMainItemRepository invoiceMainItemRepository = null;

    Set<InvoiceMainItemCommand> getMainItemCommands();

    InvoiceMainItem findById(Long l);

    void deleteById(Long idToDelete);

    InvoiceMainItemCommand saveMainItemCommand(InvoiceMainItemCommand command);

    InvoiceMainItem updateMainItem(InvoiceMainItemCommand newInvoiceMainItemCommand, Long l);

    InvoiceMainItemCommand findMainItemCommandById(Long l);

    public default void sendToInvoiceMainItem(SalesOrderToMainitemDTO dto) {
        InvoiceMainItem invoiceMainItemCommand = new InvoiceMainItem();

        // Map SalesOrder fields to InvoiceMainItem fields
        invoiceMainItemCommand.setInvoiceMainItemCode(Long.valueOf(dto.getSalesOrder()));
        invoiceMainItemCommand.setCurrencyCode(dto.getTransactionCurrency());

        // Save to the database through the repository or another service
        invoiceMainItemRepository.save(invoiceMainItemCommand);  // Assuming InvoiceMainItemCommand is the entity

    }

}
