package com.example.btpsd.services;

import com.example.btpsd.commands.ServiceInvoiceMainCommand;
import com.example.btpsd.converters.ExecutionOrderMainCommandToExecutionOrderMain;
import com.example.btpsd.converters.ExecutionOrderMainToExecutionOrderMainCommand;
import com.example.btpsd.converters.ServiceInvoiceCommandToServiceInvoice;
import com.example.btpsd.converters.ServiceInvoiceToServiceInvoiceCommand;
import com.example.btpsd.model.ExecutionOrderMain;
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
        log.debug("Converted ServiceInvoiceMain: " + detachedServiceInvoiceMain.getQuantity());

        // Check if the service invoice is related to an execution order
        ExecutionOrderMain executionOrder = detachedServiceInvoiceMain.getExecutionOrderMain();

        if (executionOrder != null) {
            // Fetch the execution order and current AQ for the related service invoice
            Integer currentActualQuantity = executionOrder.getActualQuantity() != null ? executionOrder.getActualQuantity() : 0;

            // New quantity to be added
            Integer newQuantity = detachedServiceInvoiceMain.getQuantity() != null ? detachedServiceInvoiceMain.getQuantity() : 0;

            // Calculate the total AQ after this service invoice is saved
            Integer updatedActualQuantity = currentActualQuantity + newQuantity;

            // Fetch total quantity from ExecutionOrderMain
            Integer totalQuantity = executionOrder.getTotalQuantity() != null ? executionOrder.getTotalQuantity() : 0;

            // Overfulfillment logic
            if (updatedActualQuantity > totalQuantity) {
                boolean canOverFulfill = Boolean.TRUE.equals(detachedServiceInvoiceMain.getUnlimitedOverFulfillment()) ||
                        (detachedServiceInvoiceMain.getOverFulfillmentPercentage() != null &&
                                updatedActualQuantity <= totalQuantity + (totalQuantity * detachedServiceInvoiceMain.getOverFulfillmentPercentage() / 100));

                if (!canOverFulfill) {
                    throw new IllegalArgumentException("Actual quantity exceeds total quantity without allowed overfulfillment.");
                }
            }

            // Update AQ, AP, and RQ (Actual Quantity, Actual Percentage, Remaining Quantity)
            executionOrder.setActualQuantity(updatedActualQuantity);

            // Calculate actual percentage
            Integer actualPercentage = 0;
            if (totalQuantity > 0) {
                actualPercentage = (updatedActualQuantity * 100) / totalQuantity;
            }
            executionOrder.setActualPercentage(actualPercentage);

            // Calculate remaining quantity
            Integer remainingQuantity = totalQuantity - updatedActualQuantity;
            detachedServiceInvoiceMain.setRemainingQuantity(Math.max(remainingQuantity, 0));
        } else {
            log.debug("No Execution Order Main associated with this Service Invoice");
        }

        // Save the service invoice (whether it has an execution order or not)
        ServiceInvoiceMain savedServiceInvoiceMain = serviceInvoiceMainRepository.save(detachedServiceInvoiceMain);
        log.debug("Saved Service Invoice Main Id:" + savedServiceInvoiceMain.getServiceInvoiceCode());

        return serviceInvoiceToServiceInvoiceCommand.convert(savedServiceInvoiceMain);
    }

    @Override
    @Transactional
    public ServiceInvoiceMain updateServiceInvoiceMain(ServiceInvoiceMain updatedInvoice, Long l) {
        // Fetch the existing ServiceInvoiceMain entity
        ServiceInvoiceMain existingInvoice = serviceInvoiceMainRepository.findById(l)
                .orElseThrow(() -> new EntityNotFoundException("ServiceInvoiceMain not found with ID: " + l));

        // Update non-null fields in the existing entity (all other fields except AQ, AP, RQ)
        if (updatedInvoice.getCurrencyCode() != null) existingInvoice.setCurrencyCode(updatedInvoice.getCurrencyCode());
        if (updatedInvoice.getMaterialGroupCode() != null) existingInvoice.setMaterialGroupCode(updatedInvoice.getMaterialGroupCode());
        if (updatedInvoice.getLineTypeCode() != null) existingInvoice.setLineTypeCode(updatedInvoice.getLineTypeCode());
        if (updatedInvoice.getPersonnelNumberCode() != null) existingInvoice.setPersonnelNumberCode(updatedInvoice.getPersonnelNumberCode());
        if (updatedInvoice.getUnitOfMeasurementCode() != null) existingInvoice.setUnitOfMeasurementCode(updatedInvoice.getUnitOfMeasurementCode());
        if (updatedInvoice.getDescription() != null) existingInvoice.setDescription(updatedInvoice.getDescription());
        if (updatedInvoice.getTotalQuantity() != null) existingInvoice.setTotalQuantity(updatedInvoice.getTotalQuantity());

        // Handle quantity update without triggering AQ recalculation
        if (updatedInvoice.getQuantity() != null && !updatedInvoice.getQuantity().equals(existingInvoice.getQuantity())) {
            existingInvoice.setQuantity(updatedInvoice.getQuantity());
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

        // Handle ServiceNumber association
        if (updatedInvoice.getServiceNumberCode() != null) {
            ServiceNumber serviceNumber = existingInvoice.getServiceNumber();
            if (serviceNumber == null) {
                serviceNumber = new ServiceNumber();
            }
            serviceNumber.setServiceNumberCode(updatedInvoice.getServiceNumberCode());
            existingInvoice.setServiceNumber(serviceNumber);
            serviceNumber.addServiceInvoiceMain(existingInvoice);
        }

        // Save and return the updated ServiceInvoiceMain
        return serviceInvoiceMainRepository.save(existingInvoice);
    }


    @Override
    @Transactional
    public ServiceInvoiceMainCommand findServiceInvoiceMainCommandById(Long l) {

        return serviceInvoiceToServiceInvoiceCommand.convert(findById(l));
    }
}
