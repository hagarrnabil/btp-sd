package com.example.btpsd.services;

import com.example.btpsd.commands.InvoiceMainItemCommand;
import com.example.btpsd.dtos.InvoiceMainItemDtoExceptTotalAmountDto;
import com.example.btpsd.model.InvoiceMainItem;

import java.util.Set;

public interface InvoiceMainItemService {

    Set<InvoiceMainItemCommand> getMainItemCommands();

    Set<InvoiceMainItemDtoExceptTotalAmountDto> getMainItemsExceptTotal();

    InvoiceMainItem findById(Long l);

    void deleteById(Long idToDelete);

    InvoiceMainItemCommand saveMainItemCommand(InvoiceMainItemCommand command);

    InvoiceMainItem updateMainItem(InvoiceMainItemCommand newInvoiceMainItemCommand, Long l);

    InvoiceMainItemCommand findMainItemCommandById(Long l);

}
