package com.example.btpsd.converters;

import com.example.btpsd.commands.ExecutionOrderMainCommand;
import com.example.btpsd.commands.ExecutionOrderSubCommand;
import com.example.btpsd.commands.InvoiceSubItemCommand;
import com.example.btpsd.model.*;
import com.example.btpsd.repositories.LineTypeRepository;
import io.micrometer.common.lang.Nullable;
import lombok.RequiredArgsConstructor;
import lombok.Synchronized;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;
import java.math.RoundingMode;

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
        if (source.getServiceNumberCode() != null) {
            ServiceNumber serviceNumber = new ServiceNumber();
            serviceNumber.setServiceNumberCode(source.getServiceNumberCode());
            executionOrderMain.setServiceNumber(serviceNumber);
            serviceNumber.addExecutionOrderMainItem(executionOrderMain);
        }
        executionOrderMain.setExecutionOrderMainCode(source.getExecutionOrderMainCode());
        executionOrderMain.setDescription(source.getDescription());
        executionOrderMain.setUnitOfMeasurementCode(source.getUnitOfMeasurementCode());
        executionOrderMain.setCurrencyCode(source.getCurrencyCode());
        executionOrderMain.setMaterialGroupCode(source.getMaterialGroupCode());
        executionOrderMain.setPersonnelNumberCode(source.getPersonnelNumberCode());
        executionOrderMain.setServiceTypeCode(source.getServiceTypeCode());
        executionOrderMain.setTotalQuantity(source.getTotalQuantity());
        executionOrderMain.setActualQuantity(source.getActualQuantity());
        executionOrderMain.setActualPercentage(source.getActualPercentage());
        executionOrderMain.setOverFulfillmentPercentage(source.getOverFulfillmentPercentage());
        if(source.getLineTypeCode() != null){
            executionOrderMain.setLineTypeCode(source.getLineTypeCode());
        }
        else {
            executionOrderMain.setLineTypeCode("Standard line");
        }
        if (executionOrderMain.getActualQuantity() != null) {
            executionOrderMain.setActualQuantity(executionOrderMain.getActualQuantity() + executionOrderMain.getOverFulfillmentPercentage() / 100);
        }
        executionOrderMain.setUnlimitedOverFulfillment(source.getUnlimitedOverFulfillment());
        executionOrderMain.setManualPriceEntryAllowed(source.getManualPriceEntryAllowed());
        executionOrderMain.setExternalServiceNumber(source.getExternalServiceNumber());
        executionOrderMain.setServiceText(source.getServiceText());
        executionOrderMain.setLineText(source.getLineText());
        executionOrderMain.setLineNumber(source.getLineNumber());
        executionOrderMain.setBiddersLine(source.getBiddersLine());
        executionOrderMain.setSupplementaryLine(source.getSupplementaryLine());
        executionOrderMain.setLotCostOne(source.getLotCostOne() != null ? source.getLotCostOne() : false);
        if (executionOrderMain.getLotCostOne()) {
            executionOrderMain.setTotal(executionOrderMain.getAmountPerUnit());
        }
        executionOrderMain.setDoNotPrint(source.getDoNotPrint());

        if (source.getExecutionOrderSub() != null && !source.getExecutionOrderSub().isEmpty()) {
            double totalFromSubItems = 0.0;

            for (ExecutionOrderSubCommand subItemCommand : source.getExecutionOrderSub()) {
                ExecutionOrderSub subItem = executionOrderSubConverter.convert(subItemCommand);
                if (subItem != null) {
                    totalFromSubItems += subItem.getTotal(); // Sum the total of each sub-item
                    executionOrderMain.addExecutionOrderSub(subItem);
                }
            }

            // Set amountPerUnit to the total from sub-items divided by the quantity
            executionOrderMain.setAmountPerUnit(totalFromSubItems);
        } else {
            // Use the manually entered amountPerUnit if no subItems are present
            executionOrderMain.setAmountPerUnit(source.getAmountPerUnit());
        }

        executionOrderMain.setTotal(executionOrderMain.getTotalQuantity() * executionOrderMain.getAmountPerUnit());

        ServiceInvoiceMain serviceInvoiceMain = new ServiceInvoiceMain(executionOrderMain);
        executionOrderMain.setServiceInvoiceMain(serviceInvoiceMain);

        return executionOrderMain;

    }
}