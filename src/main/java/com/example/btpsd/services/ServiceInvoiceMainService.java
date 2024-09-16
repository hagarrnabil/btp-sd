package com.example.btpsd.services;

import com.example.btpsd.commands.ServiceInvoiceMainCommand;
import com.example.btpsd.converters.ExecutionOrderMainToExecutionOrderMainCommand;
import com.example.btpsd.model.ExecutionOrderMain;
import com.example.btpsd.model.ServiceInvoiceMain;
import com.example.btpsd.model.ServiceNumber;
import org.springframework.transaction.annotation.Transactional;

import java.util.Set;

public interface ServiceInvoiceMainService {

    ExecutionOrderMainService executionOrderMainService = null;
    ExecutionOrderMainToExecutionOrderMainCommand executionOrderMainToExecutionOrderMainCommand = new ExecutionOrderMainToExecutionOrderMainCommand();

    Set<ServiceInvoiceMainCommand> getServiceInvoiceMainCommands();

    ServiceInvoiceMain findById(Long l);

    void deleteById(Long idToDelete);

    ServiceInvoiceMainCommand saveServiceInvoiceMainCommand(ServiceInvoiceMainCommand command);

    ServiceInvoiceMain updateServiceInvoiceMain(ServiceInvoiceMain updatedInvoice, Long l);

    ServiceInvoiceMainCommand findServiceInvoiceMainCommandById(Long l);

    @Transactional
    default void updateNonNullFields(ServiceInvoiceMain source, ServiceInvoiceMain target) {
        if (source.getCurrencyCode() != null) target.setCurrencyCode(source.getCurrencyCode());
        if (source.getMaterialGroupCode() != null) target.setMaterialGroupCode(source.getMaterialGroupCode());
        if (source.getLineTypeCode() != null) target.setLineTypeCode(source.getLineTypeCode());
        if (source.getPersonnelNumberCode() != null) target.setPersonnelNumberCode(source.getPersonnelNumberCode());
        if (source.getUnitOfMeasurementCode() != null) target.setUnitOfMeasurementCode(source.getUnitOfMeasurementCode());
        if (source.getDescription() != null) target.setDescription(source.getDescription());
        if (source.getTotalQuantity() != null) target.setTotalQuantity(source.getTotalQuantity());
        if (source.getQuantity() != null) target.setQuantity(source.getQuantity());
        if (source.getAmountPerUnit() != null) target.setAmountPerUnit(source.getAmountPerUnit());
        if (source.getTotal() != null) target.setTotal(source.getTotal());
        if (source.getActualQuantity() != null) target.setActualQuantity(source.getActualQuantity());
        if (source.getActualPercentage() != null) target.setActualPercentage(source.getActualPercentage());
        if (source.getOverFulfillmentPercentage() != null) target.setOverFulfillmentPercentage(source.getOverFulfillmentPercentage());
        if (source.getUnlimitedOverFulfillment() != null) target.setUnlimitedOverFulfillment(source.getUnlimitedOverFulfillment());
        if (source.getExternalServiceNumber() != null) target.setExternalServiceNumber(source.getExternalServiceNumber());
        if (source.getServiceText() != null) target.setServiceText(source.getServiceText());
        if (source.getLineText() != null) target.setLineText(source.getLineText());
        if (source.getLineNumber() != null) target.setLineNumber(source.getLineNumber());
        if (source.getBiddersLine() != null) target.setBiddersLine(source.getBiddersLine());
        if (source.getSupplementaryLine() != null) target.setSupplementaryLine(source.getSupplementaryLine());
        if (source.getLotCostOne() != null) target.setLotCostOne(source.getLotCostOne());
        if (source.getDoNotPrint() != null) target.setDoNotPrint(source.getDoNotPrint());
        if (source.getServiceTypeCode() != null) target.setServiceTypeCode(source.getServiceTypeCode());
        if (source.getServiceNumberCode() != null) {
            ServiceNumber serviceNumber = new ServiceNumber();
            serviceNumber.setServiceNumberCode(source.getServiceNumberCode());
            target.setServiceNumber(serviceNumber);
            serviceNumber.addServiceInvoiceMain(target);
        }
    }

    default void applyOverfulfillmentLogic(ServiceInvoiceMain serviceInvoiceMain) {
        // Calculate actualQuantity
        Integer calculatedActualQuantity = serviceInvoiceMain.getQuantity() +
                (serviceInvoiceMain.getExecutionOrderMain() != null ? serviceInvoiceMain.getExecutionOrderMain().getActualQuantity() : 0);
        serviceInvoiceMain.setActualQuantity(calculatedActualQuantity);

        // Enforce overfulfillment logic
        Integer totalQuantity = serviceInvoiceMain.getTotalQuantity() != null ? serviceInvoiceMain.getTotalQuantity() : 0;
        if (calculatedActualQuantity > totalQuantity) {
            boolean canOverFulfill = Boolean.TRUE.equals(serviceInvoiceMain.getUnlimitedOverFulfillment()) ||
                    (serviceInvoiceMain.getOverFulfillmentPercentage() != null &&
                            calculatedActualQuantity <= totalQuantity + (totalQuantity * serviceInvoiceMain.getOverFulfillmentPercentage() / 100));

            if (!canOverFulfill) {
                throw new IllegalArgumentException("Actual quantity exceeds total quantity without allowed overfulfillment.");
            }
        }

        // Calculate actualPercentage
        if (totalQuantity > 0) {
            Integer actualPercentage = (calculatedActualQuantity * 100) / totalQuantity;
            serviceInvoiceMain.setActualPercentage(actualPercentage);
        } else {
            serviceInvoiceMain.setActualPercentage(0);
        }

        // Update the remainingQuantity
        serviceInvoiceMain.setRemainingQuantity(totalQuantity - serviceInvoiceMain.getActualQuantity());

        // Synchronize with ExecutionOrderMain if present
        if (serviceInvoiceMain.getExecutionOrderMain() != null) {
            ExecutionOrderMain executionOrderMain = serviceInvoiceMain.getExecutionOrderMain();
            executionOrderMain.setActualQuantity(serviceInvoiceMain.getActualQuantity());
            executionOrderMain.setActualPercentage(serviceInvoiceMain.getActualPercentage());

            // Save ExecutionOrderMain
            executionOrderMainService.saveExecutionOrderMainCommand(executionOrderMainToExecutionOrderMainCommand.convert(executionOrderMain));
        }
    }

}
