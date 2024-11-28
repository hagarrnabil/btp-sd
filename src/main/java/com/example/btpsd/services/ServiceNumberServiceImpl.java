package com.example.btpsd.services;

import com.example.btpsd.commands.ServiceNumberCommand;
import com.example.btpsd.converters.ServiceNumberCommandToServiceNumber;
import com.example.btpsd.converters.ServiceNumberToServiceNumberCommand;
import com.example.btpsd.model.*;
import com.example.btpsd.repositories.ServiceNumberRepository;
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
public class ServiceNumberServiceImpl implements ServiceNumberService{

    private final ServiceNumberRepository serviceNumberRepository;
    private final ServiceNumberToServiceNumberCommand serviceNumberToServiceNumberCommand;
    private final ServiceNumberCommandToServiceNumber serviceNumberCommandToServiceNumber;


    @Override
    @Transactional
    public Set<ServiceNumberCommand> getServiceNumberCommands() {

        return StreamSupport.stream(serviceNumberRepository.findAll()
                        .spliterator(), false)
                .map(serviceNumberToServiceNumberCommand::convert)
                .collect(Collectors.toSet());

    }

    @Override
    public ServiceNumber findById(Long l) {

        Optional<ServiceNumber> serviceNumberOptional = serviceNumberRepository.findById(l);

        if (!serviceNumberOptional.isPresent()) {
            throw new RuntimeException("Service Number Not Found!");
        }

        return serviceNumberOptional.get();


    }

    @Override
    public void deleteById(Long idToDelete) {

        serviceNumberRepository.deleteById(idToDelete);

    }

    @Override
    @Transactional
    public ServiceNumberCommand saveServiceNumberCommand(ServiceNumberCommand command) {

        ServiceNumber detachedServiceNumber = serviceNumberCommandToServiceNumber.convert(command);
        ServiceNumber savedServiceNumber = serviceNumberRepository.save(detachedServiceNumber);
        log.debug("Saved Service Number Id:" + savedServiceNumber.getServiceNumberCode());
        return serviceNumberToServiceNumberCommand.convert(savedServiceNumber);


    }

    @Override
    public ServiceNumber updateServiceNumber(ServiceNumberCommand newServiceNumberCommand, Long id) {
        return serviceNumberRepository.findById(id).map(existingServiceNumber -> {
            // Update unrestricted fields directly
            if (newServiceNumberCommand.getSearchTerm() != null) {
                existingServiceNumber.setSearchTerm(newServiceNumberCommand.getSearchTerm());
            }
            if (newServiceNumberCommand.getShortTextChangeAllowed() != null) {
                existingServiceNumber.setShortTextChangeAllowed(newServiceNumberCommand.getShortTextChangeAllowed());
            }
            if (newServiceNumberCommand.getDeletionIndicator() != null) {
                existingServiceNumber.setDeletionIndicator(newServiceNumberCommand.getDeletionIndicator());
            }
            if (newServiceNumberCommand.getMainItem() != null) {
                existingServiceNumber.setMainItem(newServiceNumberCommand.getMainItem());
            }
            if (newServiceNumberCommand.getServiceText() != null) {
                existingServiceNumber.setServiceText(newServiceNumberCommand.getServiceText());
            }
            if (newServiceNumberCommand.getNumberToBeConverted() != null) {
                existingServiceNumber.setNumberToBeConverted(newServiceNumberCommand.getNumberToBeConverted());
            }
            if (newServiceNumberCommand.getConvertedNumber() != null) {
                existingServiceNumber.setConvertedNumber(newServiceNumberCommand.getConvertedNumber());
            }
            if (newServiceNumberCommand.getBaseUnitOfMeasurement() != null) {
                existingServiceNumber.setBaseUnitOfMeasurement(newServiceNumberCommand.getBaseUnitOfMeasurement());
            }
            if (newServiceNumberCommand.getToBeConvertedUnitOfMeasurement() != null) {
                existingServiceNumber.setToBeConvertedUnitOfMeasurement(newServiceNumberCommand.getToBeConvertedUnitOfMeasurement());
            }
            if (newServiceNumberCommand.getDefaultUnitOfMeasurement() != null) {
                existingServiceNumber.setDefaultUnitOfMeasurement(newServiceNumberCommand.getDefaultUnitOfMeasurement());
            }
            if (newServiceNumberCommand.getServiceTypeCode() != null) {
                existingServiceNumber.setServiceTypeCode(newServiceNumberCommand.getServiceTypeCode());
            }
            if (newServiceNumberCommand.getMaterialGroupCode() != null) {
                existingServiceNumber.setMaterialGroupCode(newServiceNumberCommand.getMaterialGroupCode());
            }

            // Update restricted fields (requires access checks in setters)
            if (newServiceNumberCommand.getDescription() != null) {
                try {
                    existingServiceNumber.setDescription(newServiceNumberCommand.getDescription());
                } catch (SecurityException e) {
                    throw new SecurityException("Access denied to update description field.");
                }
            }
            if (newServiceNumberCommand.getUnitOfMeasurementCode() != null) {
                try {
                    existingServiceNumber.setUnitOfMeasurementCode(newServiceNumberCommand.getUnitOfMeasurementCode());
                } catch (SecurityException e) {
                    throw new SecurityException("Access denied to update unit of measurement field.");
                }
            }

            return serviceNumberRepository.save(existingServiceNumber);
        }).orElseThrow(() -> new RuntimeException("Service Number not found"));
    }

    @Override
    @Transactional
    public ServiceNumberCommand findServiceNumberCommandById(Long l) {

        return serviceNumberToServiceNumberCommand.convert(findById(l));

    }
}