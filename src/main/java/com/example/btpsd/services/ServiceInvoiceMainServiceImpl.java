package com.example.btpsd.services;

import com.example.btpsd.commands.ServiceInvoiceMainCommand;
import com.example.btpsd.converters.ExecutionOrderMainToExecutionOrderMainCommand;
import com.example.btpsd.converters.ServiceInvoiceCommandToServiceInvoice;
import com.example.btpsd.converters.ServiceInvoiceToServiceInvoiceCommand;
import com.example.btpsd.model.ExecutionOrderMain;
import com.example.btpsd.model.InvoiceMainItem;
import com.example.btpsd.model.ServiceInvoiceMain;
import com.example.btpsd.repositories.ExecutionOrderMainRepository;
import com.example.btpsd.repositories.LineTypeRepository;
import com.example.btpsd.repositories.ServiceInvoiceMainRepository;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
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

    @Transactional
    @Override
    public void deleteByTemporaryStatus() {
        // Fetch all records with temporary deletion status
        List<ServiceInvoiceMain> temporaryItems = serviceInvoiceMainRepository.findByTemporaryDeletion("temporary");

        // Loop through and delete each temporary item
        for (ServiceInvoiceMain item : temporaryItems) {
            serviceInvoiceMainRepository.delete(item);
        }
    }

    @Transactional
    @Override
    public void updateAllTemporaryToPermanent() {
        // Fetch all items with 'temporary' status
        List<ServiceInvoiceMain> itemsToUpdate = serviceInvoiceMainRepository.findByTemporaryDeletion("temporary");

        // Loop over each item and update the status
        for (ServiceInvoiceMain item : itemsToUpdate) {
            item.setTemporaryDeletion("permanent");
        }

        // Save all updated items
        serviceInvoiceMainRepository.saveAll(itemsToUpdate);
    }

    @Override
    @Transactional
    public ServiceInvoiceMainCommand saveServiceInvoiceMainCommand(ServiceInvoiceMainCommand command) {
        // Convert the command object to the entity
        ServiceInvoiceMain detachedServiceInvoiceMain = serviceInvoiceCommandToServiceInvoice.convert(command);
        ExecutionOrderMain executionOrderMain = detachedServiceInvoiceMain.getExecutionOrderMain();

        // Fetch all existing service invoices associated with the same execution order
        List<ServiceInvoiceMain> existingInvoices = serviceInvoiceMainRepository
                .findAllByExecutionOrderMain_ExecutionOrderMainCode(executionOrderMain.getExecutionOrderMainCode());

        double accumulatedAQ = existingInvoices.stream().mapToDouble(ServiceInvoiceMain::getActualQuantity).sum();
        double accumulatedRQ = existingInvoices.stream().mapToDouble(ServiceInvoiceMain::getRemainingQuantity).sum();

        // Update AQ, RQ, and AP of the new invoice based on previous invoices
        double currentQuantity = detachedServiceInvoiceMain.getQuantity();
        double totalQuantity = executionOrderMain.getTotalQuantity();

        if (!existingInvoices.isEmpty()) {
            detachedServiceInvoiceMain.setActualQuantity((int) (accumulatedAQ + currentQuantity));
            detachedServiceInvoiceMain.setRemainingQuantity((int) Math.max(0, accumulatedRQ - currentQuantity));
        } else {
            detachedServiceInvoiceMain.setActualQuantity((int) currentQuantity);
            detachedServiceInvoiceMain.setRemainingQuantity((int) Math.max(0, totalQuantity - currentQuantity));
        }

        double actualPercentage = (totalQuantity > 0) ? (detachedServiceInvoiceMain.getActualQuantity() / totalQuantity) * 100 : 0.0;
        detachedServiceInvoiceMain.setActualPercentage((int) actualPercentage);

        // Update the execution order's AQ, AP, and RQ fields based on new calculations
        executionOrderMain.setActualQuantity(detachedServiceInvoiceMain.getActualQuantity());
        executionOrderMain.setActualPercentage(detachedServiceInvoiceMain.getActualPercentage());
        executionOrderMain.setRemainingQuantity(detachedServiceInvoiceMain.getRemainingQuantity());

        // Save the Service Invoice Main
        ServiceInvoiceMain savedServiceInvoiceMain = serviceInvoiceMainRepository.save(detachedServiceInvoiceMain);
        executionOrderMainRepository.save(executionOrderMain);  // Ensure ExecutionOrderMain is updated

        return serviceInvoiceToServiceInvoiceCommand.convert(savedServiceInvoiceMain);
    }

    @Override
    @Transactional
    public ServiceInvoiceMain updateServiceInvoiceMain(ServiceInvoiceMain updatedInvoice, Long id) {
        ServiceInvoiceMain existingInvoice = serviceInvoiceMainRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("ServiceInvoiceMain not found with ID: " + id));

        // Update existing fields
        updateNonNullFields(updatedInvoice, existingInvoice);

        if (updatedInvoice.getQuantity() != null) {
            existingInvoice.setQuantity(updatedInvoice.getQuantity());

            ExecutionOrderMain executionOrderMain = existingInvoice.getExecutionOrderMain();
            double accumulatedAQ = executionOrderMain.getServiceInvoices().stream()
                    .filter(inv -> !inv.getServiceInvoiceCode().equals(id))
                    .mapToDouble(ServiceInvoiceMain::getActualQuantity)
                    .sum() + existingInvoice.getQuantity();

            double totalQuantity = executionOrderMain.getTotalQuantity();
            existingInvoice.setActualQuantity((int) accumulatedAQ);
            existingInvoice.setRemainingQuantity((int) Math.max(0, totalQuantity - accumulatedAQ));
            existingInvoice.setActualPercentage((int) ((accumulatedAQ / totalQuantity) * 100));

            executionOrderMain.setActualQuantity(existingInvoice.getActualQuantity());
            executionOrderMain.setRemainingQuantity(existingInvoice.getRemainingQuantity());
            executionOrderMain.setActualPercentage(existingInvoice.getActualPercentage());
        }

        return serviceInvoiceMainRepository.save(existingInvoice);
    }

    @Override
    @Transactional
    public ServiceInvoiceMainCommand findServiceInvoiceMainCommandById(Long l) {

        return serviceInvoiceToServiceInvoiceCommand.convert(findById(l));
    }
}