package com.example.btpsd.services;

import com.example.btpsd.commands.PersonnelNumberCommand;
import com.example.btpsd.model.PersonnelNumber;

import java.util.Set;

public interface PersonnelNumberService {

    Set<PersonnelNumberCommand> getPersonnelNumberCommands();

    PersonnelNumber findById(Long l);

    void deleteById(Long idToDelete);

    PersonnelNumberCommand savePersonnelNumberCommand(PersonnelNumberCommand command);
    PersonnelNumber updatePersonnelNumber(PersonnelNumberCommand newPersonnelNumberCommand, Long l);

    PersonnelNumberCommand findPersonnelNumberCommandById(Long l);

}
