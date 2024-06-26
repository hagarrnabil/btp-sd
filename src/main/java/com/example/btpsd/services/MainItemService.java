package com.example.btpsd.services;

import com.example.btpsd.commands.InvoiceCommand;
import com.example.btpsd.commands.MainItemCommand;
import com.example.btpsd.model.Invoice;
import com.example.btpsd.model.MainItem;

import java.util.Set;

public interface MainItemService {

    Set<MainItemCommand> getMainItemCommands();

    MainItem findById(Long l);

    void deleteById(Long idToDelete);

    MainItemCommand saveMainItemCommand(MainItemCommand command);

    MainItem updateMainItem(MainItemCommand newMainItemCommand, Long l);

    MainItemCommand findMainItemCommandById(Long l);

}
