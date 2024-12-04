package com.example.btpsd.services;

import com.example.btpsd.commands.ModelSpecificationsDetailsCommand;
import com.example.btpsd.model.ModelSpecificationsDetails;

import java.util.Set;

public interface ModelSpecsDetailsService {

    Set<ModelSpecificationsDetailsCommand> getModelSpecDetailsCommands();

    ModelSpecificationsDetails findById(Long l);

    void deleteById(Long idToDelete);

    ModelSpecificationsDetailsCommand saveModelSpecDetailsCommand(ModelSpecificationsDetailsCommand command);

    ModelSpecificationsDetails updateModelSpecDetails(ModelSpecificationsDetails newModelSpecDetails, Long modelSpecDetailsCode);

    ModelSpecificationsDetailsCommand findModelSpecDetailsCommandById(Long l);

}
