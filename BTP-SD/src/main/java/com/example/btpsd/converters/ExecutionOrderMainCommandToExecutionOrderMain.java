package com.example.btpsd.converters;

import com.example.btpsd.commands.CurrencyCommand;
import com.example.btpsd.commands.ExecutionOrderMainCommand;
import com.example.btpsd.model.Currency;
import com.example.btpsd.model.ExecutionOrderMain;
import com.example.btpsd.model.ServiceNumber;
import io.micrometer.common.lang.Nullable;
import lombok.RequiredArgsConstructor;
import lombok.Synchronized;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;

@RequiredArgsConstructor
@Component
public class ExecutionOrderMainCommandToExecutionOrderMain implements Converter<ExecutionOrderMainCommand, ExecutionOrderMain> {

    @Synchronized
    @Nullable
    @Override
    public ExecutionOrderMain convert(ExecutionOrderMainCommand source) {

        if (source == null) {
            return null;
        }

        final ExecutionOrderMain executionOrderMain = new ExecutionOrderMain();
        executionOrderMain.setExecutionOrderMainCode(source.getExecutionOrderMainCode());
        if (source.getServiceNumberCode() != null) {
            ServiceNumber serviceNumber = new ServiceNumber();
            serviceNumber.setServiceNumberCode(source.getServiceNumberCode());
            executionOrderMain.setServiceNumber(serviceNumber);
            serviceNumber.addExecutionOrderMainItem(executionOrderMain);
        }
        executionOrderMain.setDescription(source.getDescription());
        executionOrderMain.setUnitOfMeasurementCode(source.getUnitOfMeasurementCode());
        executionOrderMain.setCurrencyCode(source.getCurrencyCode());
        executionOrderMain.setMaterialGroupCode(source.getMaterialGroupCode());
        executionOrderMain.setPersonnelNumberCode(source.getPersonnelNumberCode());
        executionOrderMain.setLineTypeCode(source.getLineTypeCode());
        executionOrderMain.setTotalQuantity(source.getTotalQuantity());
        executionOrderMain.setAmountPerUnit(source.getAmountPerUnit());
        executionOrderMain.setTotal(source.getTotal());
        executionOrderMain.setActualQuantity(source.getActualQuantity());
        executionOrderMain.setActualPercentage(source.getActualPercentage());
        executionOrderMain.setOverFulfillmentPercentage(source.getOverFulfillmentPercentage());
        executionOrderMain.setUnlimitedOverFulfillment(source.getUnlimitedOverFulfillment());
        executionOrderMain.setManualPriceEntryAllowed(source.getManualPriceEntryAllowed());
        executionOrderMain.setExternalServiceNumber(source.getExternalServiceNumber());
        executionOrderMain.setServiceText(source.getServiceText());
        executionOrderMain.setLineText(source.getLineText());
        executionOrderMain.setLineNumber(source.getLineNumber());
        executionOrderMain.setBiddersLine(source.getBiddersLine());
        executionOrderMain.setSupplementaryLine(source.getSupplementaryLine());
        executionOrderMain.setLotCostOne(source.getLotCostOne());
        executionOrderMain.setDoNotPrint(source.getDoNotPrint());
        return executionOrderMain;
    }
}