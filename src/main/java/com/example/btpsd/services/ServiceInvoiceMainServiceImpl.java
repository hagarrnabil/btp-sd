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
        // Convert the command object to a detached ServiceInvoiceMain entity
        ServiceInvoiceMain detachedServiceInvoiceMain = serviceInvoiceCommandToServiceInvoice.convert(command);

        // Ensure the execution order is not null
        if (detachedServiceInvoiceMain.getExecutionOrderMain() == null) {
            throw new IllegalArgumentException("ExecutionOrderMain cannot be null for this Service Invoice.");
        }

        Long executionOrderId = detachedServiceInvoiceMain.getExecutionOrderMain().getExecutionOrderMainCode();

        // Fetch the latest service invoice for the same execution order (if exists)
        ServiceInvoiceMain latestServiceInvoiceMain = serviceInvoiceMainRepository
                .findTopByExecutionOrderMainExecutionOrderMainCodeOrderByServiceInvoiceCodeDesc(executionOrderId);

        if (latestServiceInvoiceMain != null) {
            // If there is a previous service invoice, accumulate the actual quantities and percentages
            detachedServiceInvoiceMain.setActualQuantity(
                    latestServiceInvoiceMain.getActualQuantity() + detachedServiceInvoiceMain.getQuantity());

            detachedServiceInvoiceMain.setActualPercentage(
                    latestServiceInvoiceMain.getActualPercentage() + calculateActualPercentage(detachedServiceInvoiceMain, latestServiceInvoiceMain));

            detachedServiceInvoiceMain.setRemainingQuantity(
                    latestServiceInvoiceMain.getRemainingQuantity() - detachedServiceInvoiceMain.getQuantity());
        } else {
            // First service invoice for this execution order, set initial values
            detachedServiceInvoiceMain.setActualQuantity(detachedServiceInvoiceMain.getQuantity());
            detachedServiceInvoiceMain.setActualPercentage(calculateActualPercentage(detachedServiceInvoiceMain, null));
            detachedServiceInvoiceMain.setRemainingQuantity(
                    detachedServiceInvoiceMain.getExecutionOrderMain().getTotalQuantity() - detachedServiceInvoiceMain.getQuantity());
        }

        // Save the service invoice
        ServiceInvoiceMain savedServiceInvoiceMain = serviceInvoiceMainRepository.save(detachedServiceInvoiceMain);
        log.debug("Saved Execution Order Main Id:" + savedServiceInvoiceMain.getServiceInvoiceCode());

        // Convert the saved entity back to the command object and return
        return serviceInvoiceToServiceInvoiceCommand.convert(savedServiceInvoiceMain);
    }

    // Helper method to calculate actual percentage based on the current invoice's values
    private int calculateActualPercentage(ServiceInvoiceMain currentInvoice, ServiceInvoiceMain previousInvoice) {
        int totalQuantity = currentInvoice.getExecutionOrderMain().getTotalQuantity();

        // If there is a previous invoice, calculate percentage cumulatively, else base it on the current invoice
        int accumulatedQuantity = (previousInvoice != null)
                ? previousInvoice.getActualQuantity() + currentInvoice.getQuantity()
                : currentInvoice.getQuantity();

        return (accumulatedQuantity / totalQuantity) * 100;
    }



    @Override
    @Transactional
    public ServiceInvoiceMain updateServiceInvoiceMain(ServiceInvoiceMain updatedInvoice, Long l) {
        // Fetch the existing ServiceInvoiceMain entity
        ServiceInvoiceMain existingInvoice = serviceInvoiceMainRepository.findById(l)
                .orElseThrow(() -> new EntityNotFoundException("ServiceInvoiceMain not found with ID: " + l));

        // Update non-null fields in the existing entity
        if (updatedInvoice.getCurrencyCode() != null) existingInvoice.setCurrencyCode(updatedInvoice.getCurrencyCode());
        if (updatedInvoice.getMaterialGroupCode() != null) existingInvoice.setMaterialGroupCode(updatedInvoice.getMaterialGroupCode());
        if (updatedInvoice.getLineTypeCode() != null) existingInvoice.setLineTypeCode(updatedInvoice.getLineTypeCode());
        if (updatedInvoice.getPersonnelNumberCode() != null) existingInvoice.setPersonnelNumberCode(updatedInvoice.getPersonnelNumberCode());
        if (updatedInvoice.getUnitOfMeasurementCode() != null) existingInvoice.setUnitOfMeasurementCode(updatedInvoice.getUnitOfMeasurementCode());
        if (updatedInvoice.getDescription() != null) existingInvoice.setDescription(updatedInvoice.getDescription());
        if (updatedInvoice.getTotalQuantity() != null) existingInvoice.setTotalQuantity(updatedInvoice.getTotalQuantity());

        // Update total if quantity or amountPerUnit are updated
        if (updatedInvoice.getQuantity() != null) {
            existingInvoice.setQuantity(updatedInvoice.getQuantity());
            if (updatedInvoice.getAmountPerUnit() != null) {
                existingInvoice.setTotal(updatedInvoice.getQuantity() * updatedInvoice.getAmountPerUnit());
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

        // No changes to AQ, AP, and RQ here; those only happen during save

        // Save the updated ServiceInvoiceMain to the database
        ServiceInvoiceMain savedServiceInvoiceMain = serviceInvoiceMainRepository.save(existingInvoice);

        // Return the saved entity
        return savedServiceInvoiceMain;
    }


    @Override
    @Transactional
    public ServiceInvoiceMainCommand findServiceInvoiceMainCommandById(Long l) {

        return serviceInvoiceToServiceInvoiceCommand.convert(findById(l));
    }
}