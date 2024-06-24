package com.example.btpsd.controllers;

import com.example.btpsd.commands.ServiceTypeCommand;
import com.example.btpsd.converters.ServiceTypeToServiceTypeCommand;
import com.example.btpsd.model.ServiceType;
import com.example.btpsd.repositories.ServiceTypeRepository;
import com.example.btpsd.services.ServiceTypeService;
import jakarta.validation.constraints.NotNull;
import lombok.RequiredArgsConstructor;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;
import java.util.Set;

@RequiredArgsConstructor
@RestController
public class ServiceTypeController {

    private final ServiceTypeRepository serviceTypeRepository;

    private final ServiceTypeService serviceTypeService;

    private final ServiceTypeToServiceTypeCommand serviceTypeToServiceTypeCommand;

    @CrossOrigin(origins = "http://localhost:4200", maxAge = 3600)
    @GetMapping("/servicetypes")
    Set<ServiceTypeCommand> all() {
        return serviceTypeService.getServiceTypeCommands();
    }

    @CrossOrigin(origins = "http://localhost:4200", maxAge = 3600)
    @GetMapping("/servicetypes/{serviceTypeCode}")
    public Optional<ServiceTypeCommand> findByIds(@PathVariable @NotNull Long serviceTypeCode) {

        return Optional.ofNullable(serviceTypeService.findServiceTypeCommandById(serviceTypeCode));
    }

    @CrossOrigin(origins = "http://localhost:4200", maxAge = 3600)
    @PostMapping("/servicetypes")
    ServiceTypeCommand newServiceTypeCommand(@RequestBody ServiceTypeCommand newServiceTypeCommand) {

        ServiceTypeCommand savedCommand = serviceTypeService.saveServiceTypeCommand(newServiceTypeCommand);
        return savedCommand;

    }

    @CrossOrigin(origins = "http://localhost:4200", maxAge = 3600)
    @DeleteMapping("/servicetypes/{serviceTypeCode}")
    void deleteServiceTypeCommand(@PathVariable Long serviceTypeCode) {
        serviceTypeService.deleteById(serviceTypeCode);
    }

    @CrossOrigin(origins = "http://localhost:4200", maxAge = 3600)
    @PutMapping
    @RequestMapping("/servicetypes/{serviceTypeCode}")
    @Transactional
    ServiceTypeCommand updateServiceTypeCommand(@RequestBody ServiceTypeCommand newServiceTypeCommand, @PathVariable Long serviceTypeCode) {

        ServiceTypeCommand command = serviceTypeToServiceTypeCommand.convert(serviceTypeService.updateServiceType(newServiceTypeCommand, serviceTypeCode));
        return command;
    }


    @CrossOrigin(origins = "http://localhost:4200", maxAge = 3600)
    @RequestMapping(method = RequestMethod.GET, value = "/servicetypes/search")
    @ResponseBody
    public List<ServiceType> Search(@RequestParam String keyword) {

        return serviceTypeRepository.search(keyword);
    }
}
