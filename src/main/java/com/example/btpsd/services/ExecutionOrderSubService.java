package com.example.btpsd.services;

import com.example.btpsd.commands.ExecutionOrderSubCommand;
import com.example.btpsd.model.ExecutionOrderMain;
import com.example.btpsd.model.ExecutionOrderSub;
import com.example.btpsd.model.ServiceNumber;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.Set;

public interface ExecutionOrderSubService {

    Set<ExecutionOrderSubCommand> getExecutionOrderSubCommands();

    ExecutionOrderSub findById(Long l);

    void deleteById(Long idToDelete);

    ExecutionOrderSubCommand saveExecutionOrderSubCommand(ExecutionOrderSubCommand command);

    ExecutionOrderSub updateExecutionOrderSub(ExecutionOrderSubCommand newExecutionOrderSubCommand, Long l);

    ExecutionOrderSubCommand findExecutionOrderSubCommandById(Long l);

    default void updateSubNonNullFiels(ExecutionOrderSubCommand source, ExecutionOrderSub target){

        if (source.getCurrencyCode() != null) target.setCurrencyCode(source.getCurrencyCode());
        if (source.getMaterialGroupCode() != null) target.setMaterialGroupCode(source.getMaterialGroupCode());
        if (source.getLineTypeCode() != null) target.setLineTypeCode(source.getLineTypeCode());
        if (source.getPersonnelNumberCode() != null) target.setPersonnelNumberCode(source.getPersonnelNumberCode());
        if (source.getUnitOfMeasurementCode() != null) target.setUnitOfMeasurementCode(source.getUnitOfMeasurementCode());
        if (source.getDescription() != null) target.setDescription(source.getDescription());
        if (source.getTotalQuantity() != null) target.setTotalQuantity(source.getTotalQuantity());
        if (source.getAmountPerUnit() != null) target.setAmountPerUnit(source.getAmountPerUnit());
        if (source.getTotal() != null) target.setTotal(source.getTotal());
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
            serviceNumber.addExecutionOrderSubItem(target);
        }
        if (source.getExecutionOrderMainCode() != null) {
            ExecutionOrderMain executionOrderMain = new ExecutionOrderMain();
            executionOrderMain.setExecutionOrderMainCode(source.getExecutionOrderMainCode());
            target.setExecutionOrderMain(executionOrderMain);
            executionOrderMain.addExecutionOrderSub(target);
        }
    }

}
