package com.example.btpsd.services;

import com.example.btpsd.commands.ServiceTypeCommand;
import com.example.btpsd.converters.ServiceTypeCommandToServiceType;
import com.example.btpsd.converters.ServiceTypeToServiceTypeCommand;
import com.example.btpsd.model.ServiceType;
import com.example.btpsd.repositories.ServiceTypeRepository;
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
public class ServiceTypeServiceImpl implements ServiceTypeService{

    private final ServiceTypeRepository serviceTypeRepository;
    private final ServiceTypeToServiceTypeCommand serviceTypeToServiceTypeCommand;
    private final ServiceTypeCommandToServiceType serviceTypeCommandToServiceType;


    @Override
    @Transactional
    public Set<ServiceTypeCommand> getServiceTypeCommands() {

        return StreamSupport.stream(serviceTypeRepository.findAll()
                        .spliterator(), false)
                .map(serviceTypeToServiceTypeCommand::convert)
                .collect(Collectors.toSet());

    }

    @Override
    public ServiceType findById(Long l) {

        Optional<ServiceType> serviceTypeOptional = serviceTypeRepository.findById(l);

        if (!serviceTypeOptional.isPresent()) {
            throw new RuntimeException("Service Type Not Found!");
        }

        return serviceTypeOptional.get();


    }

    @Override
    public void deleteById(Long idToDelete) {

        serviceTypeRepository.deleteById(idToDelete);

    }

    @Override
    @Transactional
    public ServiceTypeCommand saveServiceTypeCommand(ServiceTypeCommand command) {

        ServiceType detachedServiceType = serviceTypeCommandToServiceType.convert(command);
        ServiceType savedServiceType = serviceTypeRepository.save(detachedServiceType);
        log.debug("Saved Service Type Id:" + savedServiceType.getServiceTypeCode());
        return serviceTypeToServiceTypeCommand.convert(savedServiceType);


    }

    @Override
    public ServiceType updateServiceType(ServiceTypeCommand newServiceTypeCommand, Long l) {

        return serviceTypeRepository.findById(l).map(oldServiceType -> {
            if (newServiceTypeCommand.getServiceId() != oldServiceType.getServiceId()) oldServiceType.setServiceId(newServiceTypeCommand.getServiceId());
            if (newServiceTypeCommand.getDescription() != oldServiceType.getDescription()) oldServiceType.setDescription(newServiceTypeCommand.getDescription());
            return serviceTypeRepository.save(oldServiceType);
        }).orElseThrow(() -> new RuntimeException("Service Type not found"));


    }

    @Override
    public ServiceTypeCommand findServiceTypeCommandById(Long l) {

        return serviceTypeToServiceTypeCommand.convert(findById(l));

    }
}
