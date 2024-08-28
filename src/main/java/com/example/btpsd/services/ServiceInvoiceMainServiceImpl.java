package com.example.btpsd.services;

import com.example.btpsd.commands.ServiceInvoiceMainCommand;
import com.example.btpsd.converters.ExecutionOrderMainCommandToExecutionOrderMain;
import com.example.btpsd.converters.ExecutionOrderMainToExecutionOrderMainCommand;
import com.example.btpsd.converters.ServiceInvoiceCommandToServiceInvoice;
import com.example.btpsd.converters.ServiceInvoiceToServiceInvoiceCommand;
import com.example.btpsd.model.ExecutionOrderMain;
import com.example.btpsd.model.ServiceInvoiceMain;
import com.example.btpsd.repositories.ExecutionOrderMainRepository;
import com.example.btpsd.repositories.LineTypeRepository;
import com.example.btpsd.repositories.ServiceInvoiceMainRepository;
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
public class ServiceInvoiceMainServiceImpl implements ServiceInvoiceMainService{

    private final ServiceInvoiceMainRepository serviceInvoiceMainRepository;
    private final LineTypeRepository lineTypeRepository;
    private final ServiceInvoiceCommandToServiceInvoice serviceInvoiceCommandToServiceInvoice;
    private final ServiceInvoiceToServiceInvoiceCommand serviceInvoiceToServiceInvoiceCommand;

    @Override
    @Transactional
    public Set<ServiceInvoiceMainCommand> getServiceInvoiceMainCommands() {

        return StreamSupport.stream(serviceInvoiceMainRepository.findAll()
                        .spliterator(), false)
                .map(serviceInvoiceToServiceInvoiceCommand::convert)
                .collect(Collectors.toSet());

    }

    @Override
    public ServiceInvoiceMain findById(Long l) {

        Optional<ServiceInvoiceMain> serviceInvoiceMainOptional = serviceInvoiceMainRepository.findById(l);

        if (!serviceInvoiceMainOptional.isPresent()) {
            throw new RuntimeException("Service Invoice Main Not Found!");
        }

        return serviceInvoiceMainOptional.get();


    }

    @Override
    public void deleteById(Long idToDelete) {

        serviceInvoiceMainRepository.deleteById(idToDelete);

    }

    @Override
    @Transactional
    public ServiceInvoiceMainCommand saveServiceInvoiceMainCommand(ServiceInvoiceMainCommand command) {

        ServiceInvoiceMain detachedServiceInvoiceMain = serviceInvoiceCommandToServiceInvoice.convert(command);
        ServiceInvoiceMain savedServiceInvoiceMain = serviceInvoiceMainRepository.save(detachedServiceInvoiceMain);
        log.debug("Saved Execution Order Main Id:" + savedServiceInvoiceMain.getServiceInvoiceCode());
        return serviceInvoiceToServiceInvoiceCommand.convert(savedServiceInvoiceMain);

    }

    @Override
    public ServiceInvoiceMain updateServiceInvoiceMain(ServiceInvoiceMainCommand newServiceInvoiceMainCommand, Long l) {

        return serviceInvoiceMainRepository.findById(l).map(oldServiceInvoiceMain -> {
            updateNonNullFields(newServiceInvoiceMainCommand, oldServiceInvoiceMain);
            oldServiceInvoiceMain.setLineTypeCode(lineTypeRepository.findLineTypeCodeByCode(newServiceInvoiceMainCommand.getLineTypeCode()));

            return serviceInvoiceMainRepository.save(oldServiceInvoiceMain);
        }).orElseThrow(() -> new RuntimeException("Service Invoice Main not found"));


    }

    @Override
    @Transactional
    public ServiceInvoiceMainCommand findServiceInvoiceMainCommandById(Long l) {

        return serviceInvoiceToServiceInvoiceCommand.convert(findById(l));
    }
}
