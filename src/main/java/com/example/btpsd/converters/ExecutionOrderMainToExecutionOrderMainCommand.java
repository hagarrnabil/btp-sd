package com.example.btpsd.converters;

import com.example.btpsd.commands.ExecutionOrderMainCommand;
import com.example.btpsd.model.ExecutionOrderMain;
import io.micrometer.common.lang.Nullable;
import lombok.RequiredArgsConstructor;
import lombok.Synchronized;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;

@RequiredArgsConstructor
@Component
public class ExecutionOrderMainToExecutionOrderMainCommand implements Converter<ExecutionOrderMain, ExecutionOrderMainCommand> {

    @Synchronized
    @Nullable
    @Override
    public ExecutionOrderMainCommand convert(ExecutionOrderMain source) {

        if (source == null) {
            return null;
        }

        final ExecutionOrderMainCommand executionOrderMainCommand = new ExecutionOrderMainCommand();
//        executionOrderMainCommand.setExecutionOrderMainCode(source.getExecutionOrderMainCode());
        if (source.getServiceNumber() != null) {
            executionOrderMainCommand.setServiceNumberCode(source.getServiceNumber().getServiceNumberCode());
        }
        executionOrderMainCommand.setDescription(source.getDescription());
        executionOrderMainCommand.setUnitOfMeasurementCode(source.getUnitOfMeasurementCode());
        executionOrderMainCommand.setCurrencyCode(source.getCurrencyCode());
        executionOrderMainCommand.setMaterialGroupCode(source.getMaterialGroupCode());
        executionOrderMainCommand.setPersonnelNumberCode(source.getPersonnelNumberCode());
        executionOrderMainCommand.setLineTypeCode(source.getLineTypeCode());
        executionOrderMainCommand.setTotalQuantity(source.getTotalQuantity());
        executionOrderMainCommand.setAmountPerUnit(source.getAmountPerUnit());
        executionOrderMainCommand.setTotal(source.getTotal());
        executionOrderMainCommand.setActualQuantity(source.getActualQuantity());
        executionOrderMainCommand.setActualPercentage(source.getActualPercentage());
        executionOrderMainCommand.setOverFulfillmentPercentage(source.getOverFulfillmentPercentage());
        executionOrderMainCommand.setUnlimitedOverFulfillment(source.getUnlimitedOverFulfillment());
        executionOrderMainCommand.setManualPriceEntryAllowed(source.getManualPriceEntryAllowed());
        executionOrderMainCommand.setExternalServiceNumber(source.getExternalServiceNumber());
        executionOrderMainCommand.setServiceText(source.getServiceText());
        executionOrderMainCommand.setLineText(source.getLineText());
        executionOrderMainCommand.setLineNumber(source.getLineNumber());
        executionOrderMainCommand.setBiddersLine(source.getBiddersLine());
        executionOrderMainCommand.setSupplementaryLine(source.getSupplementaryLine());
        executionOrderMainCommand.setLotCostOne(source.getLotCostOne());
        executionOrderMainCommand.setDoNotPrint(source.getDoNotPrint());
        return executionOrderMainCommand;
    }
}
