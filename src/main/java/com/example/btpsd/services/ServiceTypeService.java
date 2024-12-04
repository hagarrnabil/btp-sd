package com.example.btpsd.services;

import com.example.btpsd.commands.ServiceTypeCommand;
import com.example.btpsd.model.ServiceType;

import java.util.Set;

public interface ServiceTypeService {

    Set<ServiceTypeCommand> getServiceTypeCommands();

    ServiceType findById(Long l);

    void deleteById(Long idToDelete);

    ServiceTypeCommand saveServiceTypeCommand(ServiceTypeCommand command);
    ServiceType updateServiceType(ServiceTypeCommand newServiceTypeCommand, Long l);

    ServiceTypeCommand findServiceTypeCommandById(Long l);

}
