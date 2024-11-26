package com.example.btpsd.services;

import com.example.btpsd.commands.ServiceNumberCommand;
import com.example.btpsd.model.ServiceNumber;

import java.util.Set;

public interface ServiceNumberService {

    Set<ServiceNumberCommand> getServiceNumberCommands();

    ServiceNumber findById(Long l);

    void deleteById(Long idToDelete);

    ServiceNumberCommand saveServiceNumberCommand(ServiceNumberCommand command);

    ServiceNumber updateServiceNumber(ServiceNumberCommand newServiceNumberCommand, Long l);
//
//    ServiceNumberCommand findServiceNumberCommandById(Long id, ServiceControl serviceControl);
}
