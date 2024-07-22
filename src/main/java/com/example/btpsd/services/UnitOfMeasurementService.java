package com.example.btpsd.services;


import com.example.btpsd.commands.UnitOfMeasurementCommand;
import com.example.btpsd.model.UnitOfMeasurement;

import java.util.Set;

public interface UnitOfMeasurementService {

    Set<UnitOfMeasurementCommand> getUnitOfMeasurementCommands();

    UnitOfMeasurement findById(Long l);

    void deleteById(Long idToDelete);

    UnitOfMeasurementCommand saveUnitOfMeasurementCommand(UnitOfMeasurementCommand command);

//    UnitOfMeasurement updateUnitOfMeasurement(UnitOfMeasurementCommand newUnitOfMeasurementCommand, Long l);

    UnitOfMeasurementCommand findUnitOfMeasurementCommandById(Long l);

}
