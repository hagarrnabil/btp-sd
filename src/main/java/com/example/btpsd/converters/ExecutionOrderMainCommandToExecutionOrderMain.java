package com.example.btpsd.converters;

import com.example.btpsd.commands.ExecutionOrderMainCommand;
import com.example.btpsd.model.*;
import io.micrometer.common.lang.Nullable;
import lombok.RequiredArgsConstructor;
import lombok.Synchronized;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
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
        if (source.getServiceNumberCode() != null) {
            ServiceNumber serviceNumber = new ServiceNumber();
            serviceNumber.setServiceNumberCode(source.getServiceNumberCode());
            executionOrderMain.setServiceNumber(serviceNumber);
            serviceNumber.addExecutionOrderMainItem(executionOrderMain);
        }
        executionOrderMain.setExecutionOrderMainCode(source.getExecutionOrderMainCode());
        executionOrderMain.setSalesOrderItemText(source.getSalesOrderItemText());
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
        executionOrderMain.setDeletionIndicator(source.getDeletionIndicator());
        if (source.getLineTypeCode() != null) {
            executionOrderMain.setLineTypeCode(source.getLineTypeCode());
        } else {
            executionOrderMain.setLineTypeCode("Standard line");
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
        executionOrderMain.setAmountPerUnit(source.getAmountPerUnit());
        executionOrderMain.setTotal(executionOrderMain.getTotalQuantity() * executionOrderMain.getAmountPerUnit());

        executionOrderMain.setReferenceSDDocument(source.getReferenceSDDocument());
//        if (executionOrderMain.getServiceInvoiceMain() == null) {
//            executionOrderMain.setServiceInvoiceMain(new ServiceInvoiceMain(executionOrderMain));
//        } else {
//            executionOrderMain.getServiceInvoiceMain().updateFromExecutionOrder(executionOrderMain);
//        }
        executionOrderMain.setReferenceId(source.getReferenceId());
        executionOrderMain.setTotalHeader(0.0);
        return executionOrderMain;

    }
}