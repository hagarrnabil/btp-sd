package com.example.btpsd.converters;

import com.example.btpsd.commands.ExecutionOrderSubCommand;
import com.example.btpsd.model.ExecutionOrderSub;
import io.micrometer.common.lang.Nullable;
import lombok.RequiredArgsConstructor;
import lombok.Synchronized;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;
import java.math.RoundingMode;

@RequiredArgsConstructor
@Component
public class ExecutionOrderSubToExecutionOrderSubCommand implements Converter<ExecutionOrderSub, ExecutionOrderSubCommand> {

    @Synchronized
    @Nullable
    @Override
    public ExecutionOrderSubCommand convert(ExecutionOrderSub source) {

        if (source == null) {
            return null;
        }

        final ExecutionOrderSubCommand executionOrderSubCommand = new ExecutionOrderSubCommand();
        executionOrderSubCommand.setExecutionOrderSubCode(source.getExecutionOrderSubCode());
        executionOrderSubCommand.setDescription(source.getDescription());
        executionOrderSubCommand.setUnitOfMeasurementCode(source.getUnitOfMeasurementCode());
        executionOrderSubCommand.setCurrencyCode(source.getCurrencyCode());
        executionOrderSubCommand.setMaterialGroupCode(source.getMaterialGroupCode());
        executionOrderSubCommand.setPersonnelNumberCode(source.getPersonnelNumberCode());
        executionOrderSubCommand.setServiceTypeCode(source.getServiceTypeCode());
        if(source.getLineTypeCode() != null){
            executionOrderSubCommand.setLineTypeCode(source.getLineTypeCode());
        }
        else {
            executionOrderSubCommand.setLineTypeCode("Standard line");
        }
        executionOrderSubCommand.setTotalQuantity(source.getTotalQuantity());
        executionOrderSubCommand.setAmountPerUnit(source.getAmountPerUnit());
        executionOrderSubCommand.setTotal(executionOrderSubCommand.getTotalQuantity() * executionOrderSubCommand.getAmountPerUnit());
       executionOrderSubCommand.setExternalServiceNumber(source.getExternalServiceNumber());
        executionOrderSubCommand.setServiceText(source.getServiceText());
        executionOrderSubCommand.setLineText(source.getLineText());
        executionOrderSubCommand.setLineNumber(source.getLineNumber());
        executionOrderSubCommand.setBiddersLine(source.getBiddersLine());
        executionOrderSubCommand.setSupplementaryLine(source.getSupplementaryLine());
        executionOrderSubCommand.setLotCostOne(source.getLotCostOne() != null ? source.getLotCostOne() : false);

        if (executionOrderSubCommand.getLotCostOne()) {
            executionOrderSubCommand.setTotal(executionOrderSubCommand.getAmountPerUnit());
        }
        executionOrderSubCommand.setDoNotPrint(source.getDoNotPrint());

        if (source.getServiceNumber() != null) {
            executionOrderSubCommand.setServiceNumberCode(source.getServiceNumber().getServiceNumberCode());
        }

        if (source.getExecutionOrderMain() != null) {
            executionOrderSubCommand.setExecutionOrderMainCode(source.getExecutionOrderMain().getExecutionOrderMainCode());
        }

        return executionOrderSubCommand;
    }
}
