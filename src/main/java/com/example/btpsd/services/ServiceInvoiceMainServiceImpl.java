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
import jakarta.persistence.EntityNotFoundException;
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
    private final ExecutionOrderMainRepository executionOrderMainRepository;
    private final ExecutionOrderMainService executionOrderMainService;
    private final ExecutionOrderMainToExecutionOrderMainCommand executionOrderMainToExecutionOrderMainCommand;
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
    @Transactional
    public ServiceInvoiceMain updateServiceInvoiceMain(ServiceInvoiceMain updatedInvoice, Long l) {

        ServiceInvoiceMain existingInvoice = serviceInvoiceMainRepository.findById(l)
                .orElseThrow(() -> new EntityNotFoundException("ServiceInvoiceMain not found with ID: " + l));

        // Ensure the ID is correctly set
        updatedInvoice.setServiceInvoiceCode(existingInvoice.getServiceInvoiceCode());

        updateNonNullFields(updatedInvoice, existingInvoice);
        updatedInvoice.setLineTypeCode(lineTypeRepository.findLineTypeCodeByCode(updatedInvoice.getLineTypeCode()));


        // Calculate actual quantity by adding quantity from ServiceInvoiceMain and actualQuantity from ExecutionOrderMain
        Integer calculatedActualQuantity = updatedInvoice.getQuantity();
        if (existingInvoice.getExecutionOrderMain() != null) {
            calculatedActualQuantity += existingInvoice.getExecutionOrderMain().getActualQuantity() != null
                    ? existingInvoice.getExecutionOrderMain().getActualQuantity()
                    : 0;
        }
        updatedInvoice.setActualQuantity(calculatedActualQuantity);

        // Synchronize the actualQuantity back to ExecutionOrderMain
        if (existingInvoice.getExecutionOrderMain() != null) {
            ExecutionOrderMain executionOrderMain = existingInvoice.getExecutionOrderMain();
            executionOrderMain.setActualQuantity(calculatedActualQuantity);

            executionOrderMainService.saveExecutionOrderMainCommand(executionOrderMainToExecutionOrderMainCommand.convert(executionOrderMain));
        }

        return serviceInvoiceMainRepository.save(updatedInvoice);
    }


    @Override
    @Transactional
    public ServiceInvoiceMainCommand findServiceInvoiceMainCommandById(Long l) {

        return serviceInvoiceToServiceInvoiceCommand.convert(findById(l));
    }
}
