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

//    @Override
//    @Transactional
//    public ServiceInvoiceMainCommand saveServiceInvoiceMainCommand(ServiceInvoiceMainCommand command) {
//        ServiceInvoiceMain detachedServiceInvoiceMain = serviceInvoiceCommandToServiceInvoice.convert(command);
//        log.debug("Converted ServiceInvoiceMain: " + detachedServiceInvoiceMain.getQuantity());
//        ServiceInvoiceMain savedServiceInvoiceMain = serviceInvoiceMainRepository.save(detachedServiceInvoiceMain);
//        log.debug("Saved Execution Order Main Id:" + savedServiceInvoiceMain.getServiceInvoiceCode());
//        return serviceInvoiceToServiceInvoiceCommand.convert(savedServiceInvoiceMain);
//    }

    @Override
    @Transactional
    public ServiceInvoiceMainCommand saveServiceInvoiceMainCommand(ServiceInvoiceMainCommand command) {
        // Convert the command object to the entity
        ServiceInvoiceMain detachedServiceInvoiceMain = serviceInvoiceCommandToServiceInvoice.convert(command);
        ExecutionOrderMain executionOrderMain = detachedServiceInvoiceMain.getExecutionOrderMain();

        // Fetch all existing service invoices associated with the same execution order
        List<ServiceInvoiceMain> existingInvoices = serviceInvoiceMainRepository
                .findAllByExecutionOrderMain_ExecutionOrderMainCode(executionOrderMain.getExecutionOrderMainCode());

        if (existingInvoices != null && !existingInvoices.isEmpty()) {
            // If there are existing invoices, accumulate AQ and RQ values
            double accumulatedAQ = existingInvoices.stream()
                    .mapToDouble(ServiceInvoiceMain::getActualQuantity)
                    .sum();

            double accumulatedRQ = existingInvoices.stream()
                    .mapToDouble(ServiceInvoiceMain::getRemainingQuantity)
                    .sum();

            // Update AQ and RQ of the new invoice
            detachedServiceInvoiceMain.setActualQuantity((int) (accumulatedAQ + detachedServiceInvoiceMain.getQuantity()));
            detachedServiceInvoiceMain.setRemainingQuantity((int) Math.max(0, accumulatedRQ - detachedServiceInvoiceMain.getQuantity()));

            // Calculate AP as (actualQuantity / totalQuantity) * 100
            double totalQuantity = executionOrderMain.getTotalQuantity();
            double actualPercentage = (totalQuantity > 0)
                    ? (detachedServiceInvoiceMain.getActualQuantity() / totalQuantity) * 100
                    : 0.0;
            detachedServiceInvoiceMain.setActualPercentage((int) actualPercentage);
        } else {
            // If no existing invoices, apply the default logic
            detachedServiceInvoiceMain.setActualQuantity(detachedServiceInvoiceMain.getQuantity());
            detachedServiceInvoiceMain.setRemainingQuantity(Math.max(0, executionOrderMain.getTotalQuantity() - detachedServiceInvoiceMain.getQuantity()));

            // Calculate AP as (actualQuantity / totalQuantity) * 100
            double totalQuantity = executionOrderMain.getTotalQuantity();
            double actualPercentage = (totalQuantity > 0)
                    ? (detachedServiceInvoiceMain.getActualQuantity() / totalQuantity) * 100
                    : 0.0;
            detachedServiceInvoiceMain.setActualPercentage((int) actualPercentage);
        }

        // Save the Service Invoice Main
        ServiceInvoiceMain savedServiceInvoiceMain = serviceInvoiceMainRepository.save(detachedServiceInvoiceMain);
        return serviceInvoiceToServiceInvoiceCommand.convert(savedServiceInvoiceMain);
    }

    @Override
    @Transactional
    public ServiceInvoiceMain updateServiceInvoiceMain(ServiceInvoiceMain updatedInvoice, Long id) {
        ServiceInvoiceMain existingInvoice = serviceInvoiceMainRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("ServiceInvoiceMain not found with ID: " + id));

        // Update non-null fields
        if (updatedInvoice.getCurrencyCode() != null) existingInvoice.setCurrencyCode(updatedInvoice.getCurrencyCode());
        if (updatedInvoice.getMaterialGroupCode() != null) existingInvoice.setMaterialGroupCode(updatedInvoice.getMaterialGroupCode());
        if (updatedInvoice.getLineTypeCode() != null) existingInvoice.setLineTypeCode(updatedInvoice.getLineTypeCode());
        if (updatedInvoice.getPersonnelNumberCode() != null) existingInvoice.setPersonnelNumberCode(updatedInvoice.getPersonnelNumberCode());
        if (updatedInvoice.getUnitOfMeasurementCode() != null) existingInvoice.setUnitOfMeasurementCode(updatedInvoice.getUnitOfMeasurementCode());
        if (updatedInvoice.getDescription() != null) existingInvoice.setDescription(updatedInvoice.getDescription());
        if (updatedInvoice.getTotalQuantity() != null) existingInvoice.setTotalQuantity(updatedInvoice.getTotalQuantity());

        // Handle quantity update
        if (updatedInvoice.getQuantity() != null) {
            if (!updatedInvoice.getQuantity().equals(existingInvoice.getQuantity())) {
                // Update the quantity and recalculate AQ, AP, and RQ
                existingInvoice.setQuantity(updatedInvoice.getQuantity());
                Integer newActualQuantity = existingInvoice.getQuantity();

                if (existingInvoice.getExecutionOrderMain() != null) {
                    ExecutionOrderMain existingExecutionOrder = existingInvoice.getExecutionOrderMain();
                    newActualQuantity += existingExecutionOrder.getActualQuantity();
                }

                existingInvoice.setActualQuantity(newActualQuantity);

                // Update total amount
                if (updatedInvoice.getAmountPerUnit() != null) {
                    existingInvoice.setAmountPerUnit(updatedInvoice.getAmountPerUnit());
                    existingInvoice.setTotal(existingInvoice.getQuantity() * updatedInvoice.getAmountPerUnit());
                }

                // Calculate remaining quantity and actual percentage
                Integer totalQuantity = existingInvoice.getTotalQuantity() != null ? existingInvoice.getTotalQuantity() : 0;
                existingInvoice.setRemainingQuantity(totalQuantity - newActualQuantity);

                if (totalQuantity > 0) {
                    existingInvoice.setActualPercentage((newActualQuantity * 100) / totalQuantity);
                } else {
                    existingInvoice.setActualPercentage(0);
                }
            }
        }

        if (updatedInvoice.getAmountPerUnit() != null) existingInvoice.setAmountPerUnit(updatedInvoice.getAmountPerUnit());
        if (updatedInvoice.getTotal() != null) existingInvoice.setTotal(updatedInvoice.getTotal());
        if (updatedInvoice.getActualPercentage() != null) existingInvoice.setActualPercentage(updatedInvoice.getActualPercentage());
        if (updatedInvoice.getOverFulfillmentPercentage() != null) existingInvoice.setOverFulfillmentPercentage(updatedInvoice.getOverFulfillmentPercentage());
        if (updatedInvoice.getUnlimitedOverFulfillment() != null) existingInvoice.setUnlimitedOverFulfillment(updatedInvoice.getUnlimitedOverFulfillment());
        if (updatedInvoice.getExternalServiceNumber() != null) existingInvoice.setExternalServiceNumber(updatedInvoice.getExternalServiceNumber());
        if (updatedInvoice.getServiceText() != null) existingInvoice.setServiceText(updatedInvoice.getServiceText());
        if (updatedInvoice.getLineText() != null) existingInvoice.setLineText(updatedInvoice.getLineText());
        if (updatedInvoice.getLineNumber() != null) existingInvoice.setLineNumber(updatedInvoice.getLineNumber());
        if (updatedInvoice.getBiddersLine() != null) existingInvoice.setBiddersLine(updatedInvoice.getBiddersLine());
        if (updatedInvoice.getSupplementaryLine() != null) existingInvoice.setSupplementaryLine(updatedInvoice.getSupplementaryLine());
        if (updatedInvoice.getLotCostOne() != null) existingInvoice.setLotCostOne(updatedInvoice.getLotCostOne());
        if (updatedInvoice.getDoNotPrint() != null) existingInvoice.setDoNotPrint(updatedInvoice.getDoNotPrint());
        if (updatedInvoice.getServiceTypeCode() != null) existingInvoice.setServiceTypeCode(updatedInvoice.getServiceTypeCode());

        return serviceInvoiceMainRepository.save(existingInvoice);
    }


    private boolean isNewExecutionOrder(Long existingOrderCode, Long updatedOrderCode) {
        // Check if either of the order codes is null
        if (existingOrderCode == null || updatedOrderCode == null) {
            return false; // Consider it not a new order if one is null (or adjust based on your logic)
        }

        // Compare the existing order code with the updated one
        return !existingOrderCode.equals(updatedOrderCode);
    }

    // Fetches the accumulated quantity for all service invoices that do not have an associated execution order
    private double getPreviousActualQuantityWithoutExecutionOrder() {
        // Example: Sum up the quantities from ServiceInvoiceMain where ExecutionOrderMain is null
        return serviceInvoiceMainRepository
                .findAllByExecutionOrderMainIsNull()
                .stream()
                .mapToDouble(ServiceInvoiceMain::getQuantity)
                .sum();
    }

    // Fetches the accumulated quantity for a new execution order
    private double getPreviousActualQuantity(Long executionOrderCode) {
        // Example: Sum up the quantities from ServiceInvoiceMain for the given execution order code
        return serviceInvoiceMainRepository
                .findAllByExecutionOrderMain_ExecutionOrderMainCode(executionOrderCode)
                .stream()
                .mapToDouble(ServiceInvoiceMain::getQuantity)
                .sum();
    }

    // Fetches the accumulated quantity for the same execution order
    private double getPreviousActualQuantityForSameExecutionOrder(Long executionOrderCode) {
        // Example: Sum up the quantities from ServiceInvoiceMain for the given execution order code
        // to ensure it accumulates only for the same execution order
        return serviceInvoiceMainRepository
                .findAllByExecutionOrderMain_ExecutionOrderMainCode(executionOrderCode)
                .stream()
                .mapToDouble(ServiceInvoiceMain::getActualQuantity)
                .sum();
    }

    // Fetches the remaining quantity for the same execution order
    private double getPreviousRemainingQuantityForSameExecutionOrder(Long executionOrderCode) {
        // Example: Sum up the remaining quantities from ServiceInvoiceMain for the given execution order code
        return serviceInvoiceMainRepository
                .findAllByExecutionOrderMain_ExecutionOrderMainCode(executionOrderCode)
                .stream()
                .mapToDouble(ServiceInvoiceMain::getRemainingQuantity)
                .sum();
    }


    @Override
    @Transactional
    public ServiceInvoiceMainCommand findServiceInvoiceMainCommandById(Long l) {

        return serviceInvoiceToServiceInvoiceCommand.convert(findById(l));
    }
}