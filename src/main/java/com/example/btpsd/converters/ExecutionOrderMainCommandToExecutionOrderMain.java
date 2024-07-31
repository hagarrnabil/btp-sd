package com.example.btpsd.converters;

import com.example.btpsd.commands.CurrencyCommand;
import com.example.btpsd.commands.ExecutionOrderMainCommand;
import com.example.btpsd.commands.ExecutionOrderSubCommand;
import com.example.btpsd.commands.InvoiceSubItemCommand;
import com.example.btpsd.model.*;
import io.micrometer.common.lang.Nullable;
import lombok.RequiredArgsConstructor;
import lombok.Synchronized;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;

@RequiredArgsConstructor
@Component
public class ExecutionOrderMainCommandToExecutionOrderMain implements Converter<ExecutionOrderMainCommand, ExecutionOrderMain> {

    private final ExecutionOrderSubCommandToExecutionOrderSub executionOrderSubConverter;

    @Synchronized
    @Nullable
    @Override
    public ExecutionOrderMain convert(ExecutionOrderMainCommand source) {

        if (source == null) {
            return null;
        }

        final ExecutionOrderMain executionOrderMain = new ExecutionOrderMain();
        executionOrderMain.setInvoiceMainItemCode(source.getInvoiceMainItemCode());
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

        if (source.getExecutionOrderSub() != null && !source.getExecutionOrderSub().isEmpty()) {
            double totalAmountPerUnitFromSubItems = 0.0;

            for (ExecutionOrderSubCommand subItemCommand : source.getExecutionOrderSub()) {
                ExecutionOrderSub subItem = executionOrderSubConverter.convert(subItemCommand);
                if (subItem != null) {
                    totalAmountPerUnitFromSubItems += subItem.getAmountPerUnit();
                    subItem.setExecutionOrderMain(executionOrderMain);  // Ensure bi-directional relationship
                    executionOrderMain.addExecutionOrderSub(subItem);
                }
            }

            executionOrderMain.setAmountPerUnit(totalAmountPerUnitFromSubItems);
        } else {
            // Use the manually entered amountPerUnit if no subItems are present
            executionOrderMain.setAmountPerUnit(source.getAmountPerUnit());
        }

        executionOrderMain.setTotal(executionOrderMain.getTotalQuantity() * executionOrderMain.getAmountPerUnit());
        executionOrderMain.setActualQuantity(source.getActualQuantity());
        executionOrderMain.setActualPercentage(source.getActualPercentage());
        return executionOrderMain;
    }
}
