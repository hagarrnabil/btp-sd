package com.example.btpsd.services;

import com.example.btpsd.commands.ExecutionOrderMainCommand;
import com.example.btpsd.commands.ExecutionOrderSubCommand;
import com.example.btpsd.commands.InvoiceSubItemCommand;
import com.example.btpsd.converters.ExecutionOrderSubCommandToExecutionOrderSub;
import com.example.btpsd.model.ExecutionOrderMain;
import com.example.btpsd.model.ExecutionOrderSub;
import com.example.btpsd.model.InvoiceSubItem;
import com.example.btpsd.model.ServiceNumber;

import java.util.Set;

public interface ExecutionOrderMainService {

    ExecutionOrderSubCommandToExecutionOrderSub executionConverter = new ExecutionOrderSubCommandToExecutionOrderSub();

    Set<ExecutionOrderMainCommand> getExecutionOrderMainCommands();

    ExecutionOrderMain findById(Long l);

    void deleteById(Long idToDelete);

    ExecutionOrderMainCommand saveExecutionOrderMainCommand(ExecutionOrderMainCommand command);

    ExecutionOrderMain updateExecutionOrderMain(ExecutionOrderMainCommand newExecutionOrderMainCommand, Long l);

    ExecutionOrderMainCommand findExecutionOrderMainCommandById(Long l);

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
        if (source.getExecutionOrderSub() != null && !source.getExecutionOrderSub().isEmpty() &&
                !source.getExecutionOrderSub().equals(target.getExecutionOrderSubList())) {

            double totalAmountPerUnitFromSubItems = 0.0;

            // Clear existing subitems to avoid duplicates
            target.getExecutionOrderSubList().clear();

            for (ExecutionOrderSubCommand subItemCommand : source.getExecutionOrderSub()) {
                ExecutionOrderSub subItem = executionConverter.convert(subItemCommand);
                if (subItem != null) {
                    totalAmountPerUnitFromSubItems += subItem.getAmountPerUnit();
                    target.addExecutionOrderSub(subItem);
                }
            }

            target.setAmountPerUnit(totalAmountPerUnitFromSubItems);
        } else {
            // Use the manually entered amountPerUnit if no subItems are present
            target.setAmountPerUnit(source.getAmountPerUnit());
        }

    }


}
