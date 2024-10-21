package com.example.btpsd.services;

import com.example.btpsd.commands.ExecutionOrderMainCommand;
import com.example.btpsd.model.*;
import org.springframework.transaction.annotation.Transactional;

import java.util.Set;

public interface ExecutionOrderMainService {

    Set<ExecutionOrderMainCommand> getExecutionOrderMainCommands();

    ExecutionOrderMain findById(Long l);

    void deleteById(Long idToDelete);

    ExecutionOrderMainCommand saveExecutionOrderMainCommand(ExecutionOrderMainCommand command);

    ExecutionOrderMain updateExecutionOrderMain(ExecutionOrderMainCommand newExecutionOrderMainCommand, Long l);

    ExecutionOrderMainCommand findExecutionOrderMainCommandById(Long l);

    @Transactional
    default void updateNonNullFields(ExecutionOrderMainCommand source, ExecutionOrderMain target) {
        if (source.getCurrencyCode() != null) target.setCurrencyCode(source.getCurrencyCode());
        if (source.getMaterialGroupCode() != null) target.setMaterialGroupCode(source.getMaterialGroupCode());
        if (source.getLineTypeCode() != null) target.setLineTypeCode(source.getLineTypeCode());
        if (source.getPersonnelNumberCode() != null) target.setPersonnelNumberCode(source.getPersonnelNumberCode());
        if (source.getUnitOfMeasurementCode() != null) target.setUnitOfMeasurementCode(source.getUnitOfMeasurementCode());
        if (source.getDescription() != null) target.setDescription(source.getDescription());
        if (source.getTotalQuantity() != null) target.setTotalQuantity(source.getTotalQuantity());
        if (source.getAmountPerUnit() != null) target.setAmountPerUnit(source.getAmountPerUnit());
        if (source.getTotal() != null) target.setTotal(source.getTotal());
        if (source.getActualQuantity() != null) target.setActualQuantity(source.getActualQuantity());
        if (source.getActualPercentage() != null) target.setActualPercentage(source.getActualPercentage());
        if (source.getOverFulfillmentPercentage() != null) target.setOverFulfillmentPercentage(source.getOverFulfillmentPercentage());
        if (source.getUnlimitedOverFulfillment() != null) target.setUnlimitedOverFulfillment(source.getUnlimitedOverFulfillment());
        if (source.getManualPriceEntryAllowed() != null) target.setManualPriceEntryAllowed(source.getManualPriceEntryAllowed());
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
            serviceNumber.addExecutionOrderMainItem(target);
        }
        if (source.getAmountPerUnit() != null) target.setAmountPerUnit(source.getAmountPerUnit());

        // Update the corresponding ExecutionOrderMain
        if (target.getServiceInvoiceMain() != null) {
            target.getServiceInvoiceMain().updateFromExecutionOrder(target);
        }

    }
}
