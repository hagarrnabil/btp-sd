package com.example.btpsd.services;

import com.example.btpsd.commands.ModelSpecificationsCommand;
import com.example.btpsd.model.ModelSpecifications;

import java.util.Set;

public interface ModelSpecsService {

    Set<ModelSpecificationsCommand> getModelSpecsCommands();

    ModelSpecifications findById(Long l);

    void deleteById(Long idToDelete);

    ModelSpecificationsCommand saveModelSpecsCommand(ModelSpecificationsCommand command);
    ModelSpecifications updateModelSpecs(ModelSpecifications newModelSpecs, Long l);

    ModelSpecificationsCommand findModelSpecsCommandById(Long l);


}
