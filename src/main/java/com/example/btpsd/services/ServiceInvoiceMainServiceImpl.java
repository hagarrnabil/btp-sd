package com.example.btpsd.services;

import com.example.btpsd.commands.InvoiceMainItemCommand;
import com.example.btpsd.commands.ServiceInvoiceMainCommand;
import com.example.btpsd.converters.ExecutionOrderMainCommandToExecutionOrderMain;
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
        ServiceInvoiceMain newServiceInvoice = serviceInvoiceCommandToServiceInvoice.convert(command);

        if (newServiceInvoice.getExecutionOrderMain() == null) {
            // Initialize with default values for a new execution order
            newServiceInvoice.setActualQuantity(newServiceInvoice.getQuantity());
            newServiceInvoice.setRemainingQuantity(newServiceInvoice.getTotalQuantity() - newServiceInvoice.getQuantity());
            newServiceInvoice.setActualPercentage(
                    (int) ((newServiceInvoice.getActualQuantity() / (float) newServiceInvoice.getTotalQuantity()) * 100)
            );
        } else {
            // Check for the last invoice for the same execution order
            Optional<ServiceInvoiceMain> lastInvoiceOpt = serviceInvoiceMainRepository
                    .findTopByExecutionOrderMainOrderByServiceInvoiceCodeDesc(newServiceInvoice.getExecutionOrderMain());

            if (lastInvoiceOpt.isPresent()) {
                ServiceInvoiceMain lastInvoice = lastInvoiceOpt.get();
                if (!lastInvoice.getServiceInvoiceCode().equals(newServiceInvoice.getServiceInvoiceCode())) {
                    // Accumulate values from the previous invoice
                    newServiceInvoice.setActualQuantity(
                            lastInvoice.getActualQuantity() + newServiceInvoice.getQuantity()
                    );
                    newServiceInvoice.setRemainingQuantity(
                            lastInvoice.getRemainingQuantity() - newServiceInvoice.getQuantity()
                    );
                } else {
                    // If the same service invoice code, set initial values
                    newServiceInvoice.setActualQuantity(newServiceInvoice.getQuantity());
                    newServiceInvoice.setRemainingQuantity(newServiceInvoice.getTotalQuantity() - newServiceInvoice.getQuantity());
                }
            } else {
                // If there are no previous invoices, set initial values
                newServiceInvoice.setActualQuantity(newServiceInvoice.getQuantity());
                newServiceInvoice.setRemainingQuantity(newServiceInvoice.getTotalQuantity() - newServiceInvoice.getQuantity());
            }

            // Calculate the actual percentage
            newServiceInvoice.setActualPercentage(
                    (int) ((newServiceInvoice.getActualQuantity() / (float) newServiceInvoice.getTotalQuantity()) * 100)
            );
        }

        // Save the new service invoice
        ServiceInvoiceMain savedServiceInvoice = serviceInvoiceMainRepository.save(newServiceInvoice);

        // Calculate and set the total header value
        Double totalHeader = getTotalHeader();
        savedServiceInvoice.setTotalHeader(totalHeader);
        savedServiceInvoice = serviceInvoiceMainRepository.save(savedServiceInvoice);
        log.debug("Total Header after save: " + totalHeader);

        return serviceInvoiceToServiceInvoiceCommand.convert(savedServiceInvoice);
    }

    @Override
    @Transactional
    public ServiceInvoiceMain updateServiceInvoiceMain(ServiceInvoiceMain updatedInvoice, Long id) {
        // Retrieve the existing invoice from the database
        ServiceInvoiceMain existingInvoice = serviceInvoiceMainRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Service Invoice not found"));

        // Check and load ExecutionOrderMain if it's not set
        if (existingInvoice.getExecutionOrderMain() == null) {
            throw new IllegalStateException("ExecutionOrderMain must not be null for the existing invoice");
        }

        // If updatedInvoice does not have ExecutionOrderMain, use the one from existingInvoice
        if (updatedInvoice.getExecutionOrderMain() == null) {
            updatedInvoice.setExecutionOrderMain(existingInvoice.getExecutionOrderMain());
        }

        // Proceed with the logic to check if it's a new execution order
        if (isNewExecutionOrder(
                existingInvoice.getExecutionOrderMain().getExecutionOrderMainCode(),
                updatedInvoice.getExecutionOrderMain().getExecutionOrderMainCode())) {
            // New service invoice for a new execution order
            updatedInvoice.setActualQuantity(
                    (int) (updatedInvoice.getQuantity() + getPreviousActualQuantity(updatedInvoice.getExecutionOrderMain().getExecutionOrderMainCode()))
            );
            updatedInvoice.setActualPercentage(
                    (int) ((updatedInvoice.getActualQuantity() / (double) updatedInvoice.getTotalQuantity()) * 100)
            );
            updatedInvoice.setRemainingQuantity(
                    updatedInvoice.getTotalQuantity() - updatedInvoice.getQuantity()
            );
        } else {
            // New service invoice for the same execution order
            double previousActualQuantity = getPreviousActualQuantity(updatedInvoice.getExecutionOrderMain().getExecutionOrderMainCode());
            double previousRemainingQuantity = getPreviousRemainingQuantity(updatedInvoice.getExecutionOrderMain().getExecutionOrderMainCode());

            updatedInvoice.setActualQuantity(
                    (int) (updatedInvoice.getQuantity() + previousActualQuantity)
            );
            updatedInvoice.setActualPercentage(
                    (int) ((updatedInvoice.getActualQuantity() / (double) updatedInvoice.getTotalQuantity()) * 100)
            );
            updatedInvoice.setRemainingQuantity(
                    (int) (updatedInvoice.getTotalQuantity() - (updatedInvoice.getQuantity() + previousRemainingQuantity))
            );
        }

        // Update only non-null fields in the existing invoice
        updateNonNullFields(existingInvoice, updatedInvoice);

        // Save the updated invoice
        existingInvoice = serviceInvoiceMainRepository.save(existingInvoice);
        return existingInvoice;
    }

    private boolean isNewExecutionOrder(Long existingOrderId, Long updatedOrderId) {
        return !existingOrderId.equals(updatedOrderId);
    }

    private double getPreviousActualQuantity(Long executionOrderId) {
        // Fetch the previous actual quantity from the repository based on the execution order ID
        return serviceInvoiceMainRepository.findPreviousActualQuantityByExecutionOrderMainCode(executionOrderId);
    }

    private double getPreviousRemainingQuantity(Long executionOrderId) {
        // Fetch the previous remaining quantity from the repository based on the execution order ID
        return serviceInvoiceMainRepository.findPreviousRemainingQuantityByExecutionOrderMainCode(executionOrderId);
    }

    @Override
    @Transactional
    public ServiceInvoiceMainCommand findServiceInvoiceMainCommandById(Long l) {

        return serviceInvoiceToServiceInvoiceCommand.convert(findById(l));
    }
}