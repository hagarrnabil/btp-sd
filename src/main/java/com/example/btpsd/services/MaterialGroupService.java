package com.example.btpsd.services;

import com.example.btpsd.commands.MaterialGroupCommand;
import com.example.btpsd.model.MaterialGroup;

import java.util.Set;

public interface MaterialGroupService {

    Set<MaterialGroupCommand> getMaterialGroupCommands();

    MaterialGroup findById(Long l);

    void deleteById(Long idToDelete);

    MaterialGroupCommand saveMaterialGroupCommand(MaterialGroupCommand command);

    MaterialGroup updateMaterialGroup(MaterialGroupCommand newMaterialGroupCommand, Long l);

    MaterialGroupCommand findMaterialGroupCommandById(Long l);

}
