package com.example.btpsd.services;

import com.example.btpsd.commands.SubItemCommand;
import com.example.btpsd.model.SubItem;

import java.util.Set;

public interface SubItemService {

    Set<SubItemCommand> getSubItemCommands();

    SubItem findById(Long l);

    void deleteById(Long idToDelete);

    SubItemCommand saveSubItemCommand(SubItemCommand command);

    SubItem updateSubItem(SubItemCommand newSubItemCommand, Long l);

    SubItemCommand findSubItemCommandById(Long l);

}
