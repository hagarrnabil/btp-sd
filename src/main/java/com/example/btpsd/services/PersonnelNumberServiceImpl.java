package com.example.btpsd.services;

import com.example.btpsd.commands.PersonnelNumberCommand;
import com.example.btpsd.converters.PersonnelNumberCommandToPersonnelNumber;
import com.example.btpsd.converters.PersonnelNumberToPersonnelNumberCommand;
import com.example.btpsd.model.PersonnelNumber;
import com.example.btpsd.repositories.PersonnelNumberRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

@RequiredArgsConstructor
@Slf4j
@Service
public class PersonnelNumberServiceImpl implements PersonnelNumberService{

    private final PersonnelNumberRepository personnelNumberRepository;
    private final PersonnelNumberToPersonnelNumberCommand personnelNumberToPersonnelNumberCommand;
    private final PersonnelNumberCommandToPersonnelNumber personnelNumberCommandToPersonnelNumber;


    @Override
    @Transactional
    public Set<PersonnelNumberCommand> getPersonnelNumberCommands() {

        return StreamSupport.stream(personnelNumberRepository.findAll()
                        .spliterator(), false)
                .map(personnelNumberToPersonnelNumberCommand::convert)
                .collect(Collectors.toSet());

    }

    @Override
    public PersonnelNumber findById(Long l) {

        Optional<PersonnelNumber> personnelNumberOptional = personnelNumberRepository.findById(l);

        if (!personnelNumberOptional.isPresent()) {
            throw new RuntimeException("Personnel Number Not Found!");
        }

        return personnelNumberOptional.get();


    }

    @Override
    public void deleteById(Long idToDelete) {

        personnelNumberRepository.deleteById(idToDelete);

    }

    @Override
    @Transactional
    public PersonnelNumberCommand savePersonnelNumberCommand(PersonnelNumberCommand command) {

        PersonnelNumber detachedPersonnelNumber = personnelNumberCommandToPersonnelNumber.convert(command);
        PersonnelNumber savedPersonnelNumber = personnelNumberRepository.save(detachedPersonnelNumber);
        log.debug("Saved Personnel Number Id:" + savedPersonnelNumber.getPersonnelNumberCode());
        return personnelNumberToPersonnelNumberCommand.convert(savedPersonnelNumber);


    }

    @Override
    public PersonnelNumber updatePersonnelNumber(PersonnelNumberCommand newPersonnelNumberCommand, Long l) {

        return personnelNumberRepository.findById(l).map(oldPersonnelNumber -> {
            if (newPersonnelNumberCommand.getCode() != oldPersonnelNumber.getCode()) oldPersonnelNumber.setCode(newPersonnelNumberCommand.getCode());
            if (newPersonnelNumberCommand.getDescription() != oldPersonnelNumber.getDescription()) oldPersonnelNumber.setDescription(newPersonnelNumberCommand.getDescription());
            return personnelNumberRepository.save(oldPersonnelNumber);
        }).orElseThrow(() -> new RuntimeException("Personnel Number not found"));


    }

    @Override
    @Transactional
    public PersonnelNumberCommand findPersonnelNumberCommandById(Long l) {

        return personnelNumberToPersonnelNumberCommand.convert(findById(l));

    }
}
