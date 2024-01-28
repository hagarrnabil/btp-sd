package com.example.btpsd.controllers;

import com.example.btpsd.commands.ServiceNumberCommand;
import com.example.btpsd.converters.ServiceNumberToServiceNumberCommand;
import com.example.btpsd.services.ServiceNumberService;
import jakarta.validation.constraints.NotNull;
import lombok.RequiredArgsConstructor;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;
import java.util.Set;

@RequiredArgsConstructor
@RestController
public class ServiceNumberController {

    private final ServiceNumberService serviceNumberService;

    private final ServiceNumberToServiceNumberCommand serviceNumberToServiceNumberCommand;

    @GetMapping("/servicenumbers")
    Set<ServiceNumberCommand> all() {
        return serviceNumberService.getServiceNumberCommands();
    }

    @GetMapping("/servicenumbers/{serviceNumberCode}")
    public Optional<ServiceNumberCommand> findByIds(@PathVariable @NotNull Long serviceNumberCode) {

        return Optional.ofNullable(serviceNumberService.findServiceNumberCommandById(serviceNumberCode));
    }

    @PostMapping("/servicenumbers")
    ServiceNumberCommand newServiceNumberCommand(@RequestBody ServiceNumberCommand newServiceNumberCommand) {

        ServiceNumberCommand savedCommand = serviceNumberService.saveServiceNumberCommand(newServiceNumberCommand);
        return savedCommand;

    }

    @DeleteMapping("/servicenumbers/{serviceNumberCode}")
    void deleteServiceNumberCommand(@PathVariable Long serviceNumberCode) {
        serviceNumberService.deleteById(serviceNumberCode);
    }

    @PutMapping
    @RequestMapping("/servicenumbers/{serviceNumberCode}")
    @Transactional
    ServiceNumberCommand updateServiceNumberCommand(@RequestBody ServiceNumberCommand newServiceNumberCommand, @PathVariable Long serviceNumberCode) {

        ServiceNumberCommand command = serviceNumberToServiceNumberCommand.convert(serviceNumberService.updateServiceNumber(newServiceNumberCommand, serviceNumberCode));
        return command;
    }
}
