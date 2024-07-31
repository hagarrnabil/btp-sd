package com.example.btpsd.converters;

import com.example.btpsd.commands.CurrencyCommand;
import com.example.btpsd.commands.ExecutionOrderSubCommand;
import com.example.btpsd.model.Currency;
import com.example.btpsd.model.ExecutionOrderSub;
import com.example.btpsd.model.ServiceNumber;
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
        executionOrderSub.setInvoiceMainItemCode(source.getInvoiceMainItemCode());
        if (source.getServiceNumberCode() != null) {
            ServiceNumber serviceNumber = new ServiceNumber();
            serviceNumber.setServiceNumberCode(source.getServiceNumberCode());
            executionOrderSub.setServiceNumber(serviceNumber);
            serviceNumber.addExecutionOrderSubItem(executionOrderSub);
        }
        executionOrderSub.setDescription(source.getDescription());
        executionOrderSub.setUnitOfMeasurementCode(source.getUnitOfMeasurementCode());
        executionOrderSub.setCurrencyCode(source.getCurrencyCode());
        executionOrderSub.setMaterialGroupCode(source.getMaterialGroupCode());
        executionOrderSub.setPersonnelNumberCode(source.getPersonnelNumberCode());
        executionOrderSub.setLineTypeCode(source.getLineTypeCode());
        executionOrderSub.setTotalQuantity(source.getTotalQuantity());
        executionOrderSub.setExternalServiceNumber(source.getExternalServiceNumber());
        executionOrderSub.setAmountPerUnit(source.getAmountPerUnit());
        executionOrderSub.setServiceText(source.getServiceText());
        executionOrderSub.setLineText(source.getLineText());
        executionOrderSub.setLineNumber(source.getLineNumber());
        executionOrderSub.setBiddersLine(source.getBiddersLine());
        executionOrderSub.setSupplementaryLine(source.getSupplementaryLine());
        executionOrderSub.setLotCostOne(source.getLotCostOne());
        executionOrderSub.setDoNotPrint(source.getDoNotPrint());
        executionOrderSub.setTotal(executionOrderSub.getTotalQuantity() * executionOrderSub.getAmountPerUnit());
        return executionOrderSub;
    }
}
