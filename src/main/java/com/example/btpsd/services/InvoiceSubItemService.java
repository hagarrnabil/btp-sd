package com.example.btpsd.services;

import com.example.btpsd.commands.InvoiceSubItemCommand;
import com.example.btpsd.model.InvoiceSubItem;

import java.util.Set;

public interface InvoiceSubItemService {

    Set<InvoiceSubItemCommand> getSubItemCommands();

    InvoiceSubItem findById(Long l);

    void deleteById(Long idToDelete);

    InvoiceSubItemCommand saveSubItemCommand(InvoiceSubItemCommand command);

    InvoiceSubItem updateSubItem(InvoiceSubItemCommand newSubItemCommand, Long l);

    InvoiceSubItemCommand findSubItemCommandById(Long l);

}
