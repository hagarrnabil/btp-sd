package com.example.btpsd.controllers;

import com.example.btpsd.commands.ServiceTypeCommand;
import com.example.btpsd.converters.ServiceTypeToServiceTypeCommand;
import com.example.btpsd.services.ServiceTypeService;
import jakarta.validation.constraints.NotNull;
import lombok.RequiredArgsConstructor;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;
import java.util.Set;

@RequiredArgsConstructor
@RestController
public class ServiceTypeController {

    private final ServiceTypeService serviceTypeService;

    private final ServiceTypeToServiceTypeCommand serviceTypeToServiceTypeCommand;

    @GetMapping("/servicetypes")
    Set<ServiceTypeCommand> all() {
        return serviceTypeService.getServiceTypeCommands();
    }

    @GetMapping("/servicetypes/{serviceTypeCode}")
    public Optional<ServiceTypeCommand> findByIds(@PathVariable @NotNull Long serviceTypeCode) {

        return Optional.ofNullable(serviceTypeService.findServiceTypeCommandById(serviceTypeCode));
    }

    @PostMapping("/servicetypes")
    ServiceTypeCommand newServiceTypeCommand(@RequestBody ServiceTypeCommand newServiceTypeCommand) {

        ServiceTypeCommand savedCommand = serviceTypeService.saveServiceTypeCommand(newServiceTypeCommand);
        return savedCommand;

    }

    @DeleteMapping("/servicetypes/{serviceTypeCode}")
    void deleteServiceTypeCommand(@PathVariable Long serviceTypeCode) {
        serviceTypeService.deleteById(serviceTypeCode);
    }

    @PutMapping
    @RequestMapping("/servicetypes/{serviceTypeCode}")
    @Transactional
    ServiceTypeCommand updateServiceTypeCommand(@RequestBody ServiceTypeCommand newServiceTypeCommand, @PathVariable Long serviceTypeCode) {

        ServiceTypeCommand command = serviceTypeToServiceTypeCommand.convert(serviceTypeService.updateServiceType(newServiceTypeCommand, serviceTypeCode));
        return command;
    }
}
