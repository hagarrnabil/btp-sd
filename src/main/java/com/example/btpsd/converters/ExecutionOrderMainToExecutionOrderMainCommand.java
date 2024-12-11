package com.example.btpsd.converters;

import com.example.btpsd.commands.ExecutionOrderMainCommand;
import com.example.btpsd.model.ExecutionOrderMain;
import com.example.btpsd.model.LineType;
import com.example.btpsd.repositories.LineTypeRepository;
import com.example.btpsd.services.ExecutionOrderMainService;
import lombok.RequiredArgsConstructor;
import lombok.Synchronized;
import org.hibernate.validator.internal.util.stereotypes.Lazy;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;

@RequiredArgsConstructor
@Component
public class ExecutionOrderMainToExecutionOrderMainCommand implements Converter<ExecutionOrderMain, ExecutionOrderMainCommand> {

//    @Lazy
//    @Autowired
//    private ServiceInvoiceToServiceInvoiceCommand serviceInvoiceConverter;

    @Synchronized
    @Override
    public ExecutionOrderMainCommand convert(ExecutionOrderMain source) {

        if (source == null) {
            return null;
        }

        final ExecutionOrderMainCommand executionOrderMainCommand = new ExecutionOrderMainCommand();
        executionOrderMainCommand.setExecutionOrderMainCode(source.getExecutionOrderMainCode());
        executionOrderMainCommand.setSalesOrderItemText(source.getSalesOrderItemText());
        executionOrderMainCommand.setDescription(source.getDescription());
        executionOrderMainCommand.setUnitOfMeasurementCode(source.getUnitOfMeasurementCode());
        executionOrderMainCommand.setCurrencyCode(source.getCurrencyCode());
        executionOrderMainCommand.setMaterialGroupCode(source.getMaterialGroupCode());
        executionOrderMainCommand.setPersonnelNumberCode(source.getPersonnelNumberCode());
        executionOrderMainCommand.setServiceTypeCode(source.getServiceTypeCode());
        executionOrderMainCommand.setTotalQuantity(source.getTotalQuantity());
        executionOrderMainCommand.setAmountPerUnit(source.getAmountPerUnit());
        executionOrderMainCommand.setTotal(source.getTotalQuantity() * source.getAmountPerUnit());
        executionOrderMainCommand.setActualQuantity(source.getActualQuantity());
        executionOrderMainCommand.setActualPercentage(source.getActualPercentage());
        executionOrderMainCommand.setOverFulfillmentPercentage(source.getOverFulfillmentPercentage());
        executionOrderMainCommand.setDeletionIndicator(source.getDeletionIndicator());
        if(source.getLineTypeCode() != null){
            executionOrderMainCommand.setLineTypeCode(source.getLineTypeCode());
        }
        else {
            executionOrderMainCommand.setLineTypeCode("Standard line");
        }
        executionOrderMainCommand.setUnlimitedOverFulfillment(source.getUnlimitedOverFulfillment());
        executionOrderMainCommand.setManualPriceEntryAllowed(source.getManualPriceEntryAllowed());
        executionOrderMainCommand.setExternalServiceNumber(source.getExternalServiceNumber());
        executionOrderMainCommand.setServiceText(source.getServiceText());
        executionOrderMainCommand.setLineText(source.getLineText());
        executionOrderMainCommand.setLineNumber(source.getLineNumber());
        executionOrderMainCommand.setBiddersLine(source.getBiddersLine());
        executionOrderMainCommand.setSupplementaryLine(source.getSupplementaryLine());
        executionOrderMainCommand.setLotCostOne(source.getLotCostOne() != null ? source.getLotCostOne() : false);

        executionOrderMainCommand.setReferenceSDDocument(source.getReferenceSDDocument());
        if (executionOrderMainCommand.getLotCostOne()) {
            executionOrderMainCommand.setTotal(executionOrderMainCommand.getAmountPerUnit());
        }
        executionOrderMainCommand.setDoNotPrint(source.getDoNotPrint());
        if (source.getServiceNumber() != null) {
            executionOrderMainCommand.setServiceNumberCode(source.getServiceNumber().getServiceNumberCode());
        }
        executionOrderMainCommand.setReferenceId(source.getReferenceId());
        executionOrderMainCommand.setTotalHeader(source.getTotalHeader());
//        if (source.getServiceInvoices() != null && !source.getServiceInvoices().isEmpty()) {
//            source.getServiceInvoices()
//                    .forEach(invoice -> executionOrderMainCommand.getServiceInvoiceMain().add(serviceInvoiceConverter.convert(invoice)));
//        }
        return executionOrderMainCommand;
    }
}