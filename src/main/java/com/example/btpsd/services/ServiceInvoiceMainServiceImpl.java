package com.example.btpsd.services;

import com.example.btpsd.commands.ServiceInvoiceMainCommand;
import com.example.btpsd.converters.ExecutionOrderMainToExecutionOrderMainCommand;
import com.example.btpsd.converters.ServiceInvoiceCommandToServiceInvoice;
import com.example.btpsd.converters.ServiceInvoiceToServiceInvoiceCommand;
import com.example.btpsd.model.ExecutionOrderMain;
import com.example.btpsd.model.InvoiceMainItem;
import com.example.btpsd.model.ServiceInvoiceMain;
import com.example.btpsd.model.ServiceNumber;
import com.example.btpsd.repositories.ExecutionOrderMainRepository;
import com.example.btpsd.repositories.LineTypeRepository;
import com.example.btpsd.repositories.ServiceInvoiceMainRepository;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

@RequiredArgsConstructor
@Slf4j
@Service
public class ServiceInvoiceMainServiceImpl implements ServiceInvoiceMainService {

    private final ServiceInvoiceMainRepository serviceInvoiceMainRepository;
    private final ExecutionOrderMainRepository executionOrderMainRepository;
    private ServiceInvoiceMainService serviceInvoiceMainService;
    private final ExecutionOrderMainService executionOrderMainService;
    private final ExecutionOrderMainToExecutionOrderMainCommand executionOrderMainToExecutionOrderMainCommand;
    private final LineTypeRepository lineTypeRepository;
    private final ServiceInvoiceCommandToServiceInvoice serviceInvoiceCommandToServiceInvoice;
    private final ServiceInvoiceToServiceInvoiceCommand serviceInvoiceToServiceInvoiceCommand;

    @Override
    @Transactional
    public Set<ServiceInvoiceMainCommand> getServiceInvoiceMainCommands() {

        Double totalHeader = getTotalHeader();  // Call the method directly
        return StreamSupport.stream(serviceInvoiceMainRepository.findAll().spliterator(), false)
                .map(serviceInvoiceMain -> {
                    ServiceInvoiceMainCommand command = serviceInvoiceToServiceInvoiceCommand.convert(serviceInvoiceMain);
                    command.setTotalHeader(totalHeader);
                    return command;
                })
                .collect(Collectors.toSet());

    }

    public Double getTotalHeader() {
        List<ServiceInvoiceMain> allItems = (List<ServiceInvoiceMain>) serviceInvoiceMainRepository.findAll();
        Double totalHeader = 0.0;

        for (ServiceInvoiceMain item : allItems) {
            log.debug("Item ID: " + item.getServiceInvoiceCode() + ", total: " + item.getTotal());
            totalHeader += item.getTotal();
        }

        log.debug("Final Total Header: " + totalHeader);

        return totalHeader;
    }

    public ServiceInvoiceMainCommand getSrvMainItemWithTotalHeader(Long id) {
        ServiceInvoiceMain serviceInvoiceMain = serviceInvoiceMainRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Invoice not found"));

        Double totalHeader = serviceInvoiceMainService.getTotalHeader();
        log.debug("Total Header set in command: " + totalHeader);

        ServiceInvoiceMainCommand command = serviceInvoiceToServiceInvoiceCommand.convert(serviceInvoiceMain);
        command.setTotalHeader(totalHeader);

        return command;
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
        // Convert the command to an entity
        ServiceInvoiceMain serviceInvoiceMain = serviceInvoiceCommandToServiceInvoice.convert(command);

        // Save the new ServiceInvoiceMain to the repository (initial save)
        ServiceInvoiceMain savedInvoice = serviceInvoiceMainRepository.save(serviceInvoiceMain);

        Double totalHeader = getTotalHeader();
        savedInvoice.setTotalHeader(totalHeader);

        // Calculate the totalHeader after the invoice is saved
        ExecutionOrderMain executionOrderMain = savedInvoice.getExecutionOrderMain();
        if (executionOrderMain != null) {
            executionOrderMain.setTotalHeader(
                    executionOrderMain.getServiceInvoices().stream().mapToDouble(ServiceInvoiceMain::getTotal).sum()
            );
            executionOrderMainRepository.save(executionOrderMain); // Save the updated ExecutionOrderMain
        }

        serviceInvoiceMainRepository.save(savedInvoice);

        // Convert back to command for return
        return serviceInvoiceToServiceInvoiceCommand.convert(savedInvoice);
    }

    @Override
    @Transactional
    public ServiceInvoiceMain updateServiceInvoiceMain(ServiceInvoiceMain updatedInvoice, Long id) {
        return serviceInvoiceMainRepository.findById(id).map(existingInvoice -> {
            // Update non-null fields
            if (updatedInvoice.getCurrencyCode() != null) existingInvoice.setCurrencyCode(updatedInvoice.getCurrencyCode());
            if (updatedInvoice.getMaterialGroupCode() != null) existingInvoice.setMaterialGroupCode(updatedInvoice.getMaterialGroupCode());
            if (updatedInvoice.getLineTypeCode() != null) existingInvoice.setLineTypeCode(updatedInvoice.getLineTypeCode());
            if (updatedInvoice.getPersonnelNumberCode() != null) existingInvoice.setPersonnelNumberCode(updatedInvoice.getPersonnelNumberCode());
            if (updatedInvoice.getUnitOfMeasurementCode() != null) existingInvoice.setUnitOfMeasurementCode(updatedInvoice.getUnitOfMeasurementCode());
            if (updatedInvoice.getDescription() != null) existingInvoice.setDescription(updatedInvoice.getDescription());
            if (updatedInvoice.getQuantity() != null) existingInvoice.setQuantity(updatedInvoice.getQuantity());
            if (updatedInvoice.getAmountPerUnit() != null) existingInvoice.setAmountPerUnit(updatedInvoice.getAmountPerUnit());
            if (updatedInvoice.getReferenceId() != null) existingInvoice.setReferenceId(updatedInvoice.getReferenceId());
            if (updatedInvoice.getReferenceSDDocument() != null) existingInvoice.setReferenceSDDocument(updatedInvoice.getReferenceSDDocument());

            // Handle ServiceNumber association
            if (updatedInvoice.getServiceNumber() != null) {
                existingInvoice.setServiceNumber(updatedInvoice.getServiceNumber());
            }

            // Calculate total and total with profit
            existingInvoice.setTotal(existingInvoice.getQuantity() * existingInvoice.getAmountPerUnit());


            // Update ExecutionOrderMain totals if necessary
            ExecutionOrderMain executionOrderMain = existingInvoice.getExecutionOrderMain();
            if (executionOrderMain != null) {
                executionOrderMain.setTotalHeader(
                        executionOrderMain.getServiceInvoices().stream().mapToDouble(ServiceInvoiceMain::getTotal).sum()
                );
                executionOrderMainRepository.save(executionOrderMain);
            }

            return serviceInvoiceMainRepository.save(existingInvoice);
        }).orElseThrow(() -> new EntityNotFoundException("ServiceInvoiceMain not found with ID: " + id));
    }

    @Override
    @Transactional
    public ServiceInvoiceMainCommand findServiceInvoiceMainCommandById(Long l) {

        return serviceInvoiceToServiceInvoiceCommand.convert(findById(l));
    }
}