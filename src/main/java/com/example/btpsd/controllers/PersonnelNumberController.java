package com.example.btpsd.controllers;

import com.example.btpsd.commands.PersonnelNumberCommand;
import com.example.btpsd.converters.PersonnelNumberToPersonnelNumberCommand;
import com.example.btpsd.services.PersonnelNumberService;
import jakarta.validation.constraints.NotNull;
import lombok.RequiredArgsConstructor;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;
import java.util.Set;
@RequiredArgsConstructor
@RestController
public class PersonnelNumberController {

    private final PersonnelNumberService personnelNumberService;

    private final PersonnelNumberToPersonnelNumberCommand personnelNumberToPersonnelNumberCommand;

    @GetMapping("/personnelnumbers")
    Set<PersonnelNumberCommand> all() {
        return personnelNumberService.getPersonnelNumberCommands();
    }

    @GetMapping("/personnelnumbers/{personnelNumberCode}")
    public Optional<PersonnelNumberCommand> findByIds(@PathVariable @NotNull Long personnelNumberCode) {

        return Optional.ofNullable(personnelNumberService.findPersonnelNumberCommandById(personnelNumberCode));
    }

    @PostMapping("/personnelnumbers")
    PersonnelNumberCommand newPersonnelNumberCommand(@RequestBody PersonnelNumberCommand newPersonnelNumberCommand) {

        PersonnelNumberCommand savedCommand = personnelNumberService.savePersonnelNumberCommand(newPersonnelNumberCommand);
        return savedCommand;

    }

    @DeleteMapping("/personnelnumbers/{personnelNumberCode}")
    void deletePersonnelNumberCommand(@PathVariable Long personnelNumberCode) {
        personnelNumberService.deleteById(personnelNumberCode);
    }

    @PutMapping
    @RequestMapping("/personnelnumbers/{personnelNumberCode}")
    @Transactional
    PersonnelNumberCommand updatePersonnelNumberCommand(@RequestBody PersonnelNumberCommand newPersonnelNumberCommand, @PathVariable Long personnelNumberCode) {

        PersonnelNumberCommand command = personnelNumberToPersonnelNumberCommand.convert(personnelNumberService.updatePersonnelNumber(newPersonnelNumberCommand, personnelNumberCode));
        return command;
    }
}
