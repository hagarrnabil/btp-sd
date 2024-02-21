package com.example.btpsd.services;

import com.example.btpsd.commands.ServiceNumberCommand;
import com.example.btpsd.converters.ServiceNumberCommandToServiceNumber;
import com.example.btpsd.converters.ServiceNumberToServiceNumberCommand;
import com.example.btpsd.model.ServiceNumber;
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
    public ServiceNumber updateServiceNumber(ServiceNumberCommand newServiceNumberCommand, Long l) {

        return serviceNumberRepository.findById(l).map(oldServiceNumber -> {
            if (newServiceNumberCommand.getCode() != oldServiceNumber.getCode())
                oldServiceNumber.setCode(newServiceNumberCommand.getCode());
            if (newServiceNumberCommand.getDescription() != oldServiceNumber.getDescription())
                oldServiceNumber.setDescription(newServiceNumberCommand.getDescription());
            if (newServiceNumberCommand.getFormulaCode() != oldServiceNumber.getFormulaCode())
                oldServiceNumber.setFormulaCode(newServiceNumberCommand.getFormulaCode());
            if (newServiceNumberCommand.getShortTextChangeAllowed() != oldServiceNumber.getShortTextChangeAllowed())
                oldServiceNumber.setShortTextChangeAllowed(newServiceNumberCommand.getShortTextChangeAllowed());
            if (newServiceNumberCommand.getDeletionIndicator() != oldServiceNumber.getDeletionIndicator())
                oldServiceNumber.setDeletionIndicator(newServiceNumberCommand.getDeletionIndicator());
            if (newServiceNumberCommand.getMainItem() != oldServiceNumber.getMainItem())
                oldServiceNumber.setMainItem(newServiceNumberCommand.getMainItem());
            if (newServiceNumberCommand.getServiceText() != oldServiceNumber.getServiceText())
                oldServiceNumber.setServiceText(newServiceNumberCommand.getServiceText());
            if (newServiceNumberCommand.getNumberToBeConverted() != oldServiceNumber.getNumberToBeConverted())
                oldServiceNumber.setNumberToBeConverted(newServiceNumberCommand.getNumberToBeConverted());
            if (newServiceNumberCommand.getConvertedNumber() != oldServiceNumber.getConvertedNumber())
                oldServiceNumber.setConvertedNumber(newServiceNumberCommand.getConvertedNumber());
            return serviceNumberRepository.save(oldServiceNumber);
        }).orElseThrow(() -> new RuntimeException("Service Number not found"));


    }

    @Override
    @Transactional
    public ServiceNumberCommand findServiceNumberCommandById(Long l) {

        return serviceNumberToServiceNumberCommand.convert(findById(l));

    }
}
