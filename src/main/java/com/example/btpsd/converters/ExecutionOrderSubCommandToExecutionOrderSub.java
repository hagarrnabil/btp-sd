package com.example.btpsd.converters;

import com.example.btpsd.commands.CurrencyCommand;
import com.example.btpsd.commands.ExecutionOrderSubCommand;
import com.example.btpsd.model.*;
import io.micrometer.common.lang.Nullable;
import lombok.RequiredArgsConstructor;
import lombok.Synchronized;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;

@RequiredArgsConstructor
@Component
public class ExecutionOrderSubCommandToExecutionOrderSub implements Converter<ExecutionOrderSubCommand, ExecutionOrderSub> {

    @Synchronized
    @Nullable
    @Override
    public ExecutionOrderSub convert(ExecutionOrderSubCommand source) {

        if (source == null) {
            return null;
        }

        final ExecutionOrderSub executionOrderSub = new ExecutionOrderSub();
        executionOrderSub.setExecutionOrderSubCode(source.getExecutionOrderSubCode());
        if (source.getServiceNumberCode() != null) {
            ServiceNumber serviceNumber = new ServiceNumber();
            serviceNumber.setServiceNumberCode(source.getServiceNumberCode());
            executionOrderSub.setServiceNumber(serviceNumber);
            serviceNumber.addExecutionOrderSubItem(executionOrderSub);
        }
        if (source.getExecutionOrderMainCode() != null) {
            ExecutionOrderMain executionOrderMain = new ExecutionOrderMain();
            executionOrderMain.setExecutionOrderMainCode(source.getExecutionOrderMainCode());
            executionOrderSub.setExecutionOrderMain(executionOrderMain);
            executionOrderMain.addExecutionOrderSub(executionOrderSub);  // Ensure bi-directional relationship
        }
        executionOrderSub.setDescription(source.getDescription());
        executionOrderSub.setUnitOfMeasurementCode(source.getUnitOfMeasurementCode());
        executionOrderSub.setCurrencyCode(source.getCurrencyCode());
        executionOrderSub.setServiceTypeCode(source.getServiceTypeCode());
        executionOrderSub.setMaterialGroupCode(source.getMaterialGroupCode());
        executionOrderSub.setPersonnelNumberCode(source.getPersonnelNumberCode());
//        executionOrderMainService.setLineTypeCodeByUserInput(executionOrderMain,source.getLineTypeCode());
        executionOrderSub.setTotalQuantity(source.getTotalQuantity());
        executionOrderSub.setExternalServiceNumber(source.getExternalServiceNumber());
        executionOrderSub.setAmountPerUnit(source.getAmountPerUnit());
        executionOrderSub.setServiceText(source.getServiceText());
        executionOrderSub.setLineText(source.getLineText());
        executionOrderSub.setLineNumber(source.getLineNumber());
        executionOrderSub.setBiddersLine(source.getBiddersLine());
        executionOrderSub.setSupplementaryLine(source.getSupplementaryLine());
//        executionOrderSub.setLotCostOne(source.getLotCostOne());
        executionOrderSub.setLotCostOne(source.getLotCostOne() != null ? source.getLotCostOne() : false);

        if (executionOrderSub.getLotCostOne()) {
            executionOrderSub.setTotal(executionOrderSub.getAmountPerUnit());
        }
        executionOrderSub.setDoNotPrint(source.getDoNotPrint());
        executionOrderSub.setTotal(executionOrderSub.getTotalQuantity() * executionOrderSub.getAmountPerUnit());
        return executionOrderSub;
    }
}
