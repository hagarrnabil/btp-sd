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

import java.math.BigDecimal;
import java.math.RoundingMode;
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

        // Convert the ServiceInvoiceMainCommand to a ServiceInvoiceMain entity
        ServiceInvoiceMain detachedServiceInvoiceMain = serviceInvoiceCommandToServiceInvoice.convert(command);
        log.debug("Converted ServiceInvoiceMain: " + detachedServiceInvoiceMain.getQuantity());

        // Fetch all service invoices with the same ExecutionOrderMain code
        List<ServiceInvoiceMain> previousServiceInvoices = serviceInvoiceMainRepository
                .findByExecutionOrderMainCode(detachedServiceInvoiceMain.getExecutionOrderMainCode());

        // Calculate the accumulated quantities (actualQuantity and remainingQuantity)
        int accumulatedActualQuantity = previousServiceInvoices.stream()
                .mapToInt(ServiceInvoiceMain::getActualQuantity)
                .sum();

        // Calculate remaining quantity
        int remainingQuantity = detachedServiceInvoiceMain.getTotalQuantity() - accumulatedActualQuantity - detachedServiceInvoiceMain.getQuantity();

        detachedServiceInvoiceMain.setRemainingQuantity(remainingQuantity);

        // Accumulate actualQuantity
        int newActualQuantity = accumulatedActualQuantity + detachedServiceInvoiceMain.getQuantity();
        detachedServiceInvoiceMain.setActualQuantity(newActualQuantity);

        // Calculate total (amountPerUnit * actualQuantity)
        double total = newActualQuantity * detachedServiceInvoiceMain.getAmountPerUnit();
        detachedServiceInvoiceMain.setTotal(new BigDecimal(total).setScale(2, RoundingMode.HALF_UP).doubleValue());

        // Calculate actual percentage based on the latest actualQuantity and totalQuantity
        double actualPercentage = (double) newActualQuantity / detachedServiceInvoiceMain.getTotalQuantity() * 100;
        detachedServiceInvoiceMain.setActualPercentage((int) new BigDecimal(actualPercentage).setScale(2, RoundingMode.HALF_UP).doubleValue());

        // Calculate totalHeader (sum of the total for all service invoices)
        double totalHeader = previousServiceInvoices.stream()
                .mapToDouble(ServiceInvoiceMain::getTotal)
                .sum() + detachedServiceInvoiceMain.getTotal();
        detachedServiceInvoiceMain.setTotalHeader(new BigDecimal(totalHeader).setScale(2, RoundingMode.HALF_UP).doubleValue());

        // Save the service invoice
        ServiceInvoiceMain savedServiceInvoiceMain = serviceInvoiceMainRepository.save(detachedServiceInvoiceMain);
        log.debug("Saved ServiceInvoiceMain with code: " + savedServiceInvoiceMain.getServiceInvoiceCode());

        return serviceInvoiceToServiceInvoiceCommand.convert(savedServiceInvoiceMain);
    }


    @Override
    @Transactional
    public ServiceInvoiceMain updateServiceInvoiceMain(ServiceInvoiceMain updatedInvoice, Long id) {
        // Fetch the existing ServiceInvoiceMain entity
        ServiceInvoiceMain existingInvoice = serviceInvoiceMainRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("ServiceInvoiceMain not found with ID: " + id));

        // Fetch all service invoices with the same ExecutionOrderMain code to accumulate quantities
        List<ServiceInvoiceMain> previousServiceInvoices = serviceInvoiceMainRepository
                .findByExecutionOrderMainCode(existingInvoice.getExecutionOrderMain().getExecutionOrderMainCode());

        // Calculate the accumulated actualQuantity and remainingQuantity
        int accumulatedActualQuantity = previousServiceInvoices.stream()
                .mapToInt(ServiceInvoiceMain::getActualQuantity)
                .sum();

        // Update actualQuantity
        int updatedActualQuantity = accumulatedActualQuantity + updatedInvoice.getQuantity();
        existingInvoice.setActualQuantity(updatedActualQuantity);

        // Calculate remainingQuantity
        int remainingQuantity = existingInvoice.getTotalQuantity() - updatedActualQuantity;
        existingInvoice.setRemainingQuantity(remainingQuantity);

        // Update total based on the new actualQuantity
        double total = updatedActualQuantity * updatedInvoice.getAmountPerUnit();
        existingInvoice.setTotal(new BigDecimal(total).setScale(2, RoundingMode.HALF_UP).doubleValue());

        // Calculate actual percentage based on the latest actualQuantity and totalQuantity
        double actualPercentage = (double) updatedActualQuantity / existingInvoice.getTotalQuantity() * 100;
        existingInvoice.setActualPercentage((int) new BigDecimal(actualPercentage).setScale(2, RoundingMode.HALF_UP).doubleValue());

        // Calculate totalHeader (sum of the total for all service invoices)
        double totalHeader = previousServiceInvoices.stream()
                .mapToDouble(ServiceInvoiceMain::getTotal)
                .sum() + existingInvoice.getTotal();
        existingInvoice.setTotalHeader(new BigDecimal(totalHeader).setScale(2, RoundingMode.HALF_UP).doubleValue());

        // Update other fields as necessary
        if (updatedInvoice.getQuantity() != null) {
            existingInvoice.setQuantity(updatedInvoice.getQuantity());
        }

        if (updatedInvoice.getAmountPerUnit() != null) {
            existingInvoice.setAmountPerUnit(updatedInvoice.getAmountPerUnit());
        }

        // Save the updated invoice
        ServiceInvoiceMain savedServiceInvoiceMain = serviceInvoiceMainRepository.save(existingInvoice);
        return savedServiceInvoiceMain;
    }


    private void updateInvoiceFields(ServiceInvoiceMain existingInvoice, ServiceInvoiceMain updatedInvoice) {
        if (updatedInvoice.getCurrencyCode() != null) existingInvoice.setCurrencyCode(updatedInvoice.getCurrencyCode());
        if (updatedInvoice.getMaterialGroupCode() != null) existingInvoice.setMaterialGroupCode(updatedInvoice.getMaterialGroupCode());
        if (updatedInvoice.getLineTypeCode() != null) existingInvoice.setLineTypeCode(updatedInvoice.getLineTypeCode());
        if (updatedInvoice.getPersonnelNumberCode() != null) existingInvoice.setPersonnelNumberCode(updatedInvoice.getPersonnelNumberCode());
        if (updatedInvoice.getUnitOfMeasurementCode() != null) existingInvoice.setUnitOfMeasurementCode(updatedInvoice.getUnitOfMeasurementCode());
        if (updatedInvoice.getDescription() != null) existingInvoice.setDescription(updatedInvoice.getDescription());
        if (updatedInvoice.getTotalQuantity() != null) existingInvoice.setTotalQuantity(updatedInvoice.getTotalQuantity());
        if (updatedInvoice.getQuantity() != null) existingInvoice.setQuantity(updatedInvoice.getQuantity());
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
        if (updatedInvoice.getReferenceId() != null) existingInvoice.setReferenceId(updatedInvoice.getReferenceId());
        if (updatedInvoice.getReferenceSDDocument() != null) existingInvoice.setReferenceSDDocument(updatedInvoice.getReferenceSDDocument());
    }

    @Override
    @Transactional
    public ServiceInvoiceMainCommand findServiceInvoiceMainCommandById(Long l) {

        return serviceInvoiceToServiceInvoiceCommand.convert(findById(l));
    }
}