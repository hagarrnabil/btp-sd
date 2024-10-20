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
        // Convert the command to the ServiceInvoiceMain entity
        ServiceInvoiceMain newServiceInvoice = serviceInvoiceCommandToServiceInvoice.convert(command);

        // Check if the service invoice has an execution order
        if (newServiceInvoice.getExecutionOrderMain() == null) {
            // Case 1: No execution order present, calculate based on the previous invoice, if any.
            Optional<ServiceInvoiceMain> lastInvoiceOpt = serviceInvoiceMainRepository.findTopByOrderByServiceInvoiceCodeDesc();

            if (lastInvoiceOpt.isPresent()) {
                ServiceInvoiceMain lastInvoice = lastInvoiceOpt.get();
                // Update AQ = Q + Previous AQ
                newServiceInvoice.setActualQuantity(lastInvoice.getActualQuantity() + newServiceInvoice.getQuantity());
                // Update RQ = TQ - Q
                newServiceInvoice.setRemainingQuantity(lastInvoice.getTotalQuantity() - newServiceInvoice.getQuantity());
                // Update AP = AQ / TQ * 100
                newServiceInvoice.setActualPercentage((int) ((newServiceInvoice.getActualQuantity() / (float) lastInvoice.getTotalQuantity()) * 100));
            } else {
                // If no previous invoice exists, initialize AQ, RQ, and AP with the new values.
                newServiceInvoice.setActualQuantity(newServiceInvoice.getQuantity());
                newServiceInvoice.setRemainingQuantity(newServiceInvoice.getTotalQuantity() - newServiceInvoice.getQuantity());
                newServiceInvoice.setActualPercentage((int) ((newServiceInvoice.getActualQuantity() / (float) newServiceInvoice.getTotalQuantity()) * 100));
            }
        } else {
            // Case 2 and Case 3: Execution order exists, check if it's a new or existing order.

            // Find the last invoice for the same execution order
            Optional<ServiceInvoiceMain> lastInvoiceOpt = serviceInvoiceMainRepository.findTopByExecutionOrderMainOrderByServiceInvoiceCodeDesc(newServiceInvoice.getExecutionOrderMain());

            if (lastInvoiceOpt.isPresent()) {
                ServiceInvoiceMain lastInvoice = lastInvoiceOpt.get();

                // Case 3: Same execution order
                // Update AQ = Q + AQ of the last service invoice with the same execution order
                newServiceInvoice.setActualQuantity(lastInvoice.getActualQuantity() + newServiceInvoice.getQuantity());

                // Update RQ = last RQ for the same execution order - Q
                newServiceInvoice.setRemainingQuantity(lastInvoice.getRemainingQuantity() - newServiceInvoice.getQuantity());

                // Calculate AP = AQ / TQ * 100
                newServiceInvoice.setActualPercentage((int) ((newServiceInvoice.getActualQuantity() / (float) lastInvoice.getTotalQuantity()) * 100));

            } else {
                // Case 2: New execution order
                // Update AQ = Q (since it's the first invoice for this execution order)
                newServiceInvoice.setActualQuantity(newServiceInvoice.getQuantity());

                // Update RQ = TQ - Q (initial values for this new execution order)
                newServiceInvoice.setRemainingQuantity(newServiceInvoice.getTotalQuantity() - newServiceInvoice.getQuantity());

                // Calculate AP = AQ / TQ * 100
                newServiceInvoice.setActualPercentage((int) ((newServiceInvoice.getActualQuantity() / (float) newServiceInvoice.getTotalQuantity()) * 100));
            }
        }

        // Save the new ServiceInvoiceMain entity
        ServiceInvoiceMain savedServiceInvoice = serviceInvoiceMainRepository.save(newServiceInvoice);
        log.debug("Saved Service Invoice Main Id: " + savedServiceInvoice.getServiceInvoiceCode());

        return serviceInvoiceToServiceInvoiceCommand.convert(savedServiceInvoice);
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