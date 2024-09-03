package com.example.btpsd.converters;

import com.example.btpsd.commands.ExecutionOrderMainCommand;
import com.example.btpsd.model.ExecutionOrderMain;
import com.example.btpsd.model.LineType;
import com.example.btpsd.repositories.LineTypeRepository;
import com.example.btpsd.services.ExecutionOrderMainService;
import lombok.RequiredArgsConstructor;
import lombok.Synchronized;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;

@RequiredArgsConstructor
@Component
public class ExecutionOrderMainToExecutionOrderMainCommand implements Converter<ExecutionOrderMain, ExecutionOrderMainCommand> {

    @Synchronized
    @Override
    public ExecutionOrderMainCommand convert(ExecutionOrderMain source) {

        if (source == null) {
            return null;
        }

        final ExecutionOrderMainCommand executionOrderMainCommand = new ExecutionOrderMainCommand();
        executionOrderMainCommand.setExecutionOrderMainCode(source.getExecutionOrderMainCode());
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
        if(source.getLineTypeCode() != null){
            executionOrderMainCommand.setLineTypeCode(source.getLineTypeCode());
        }
        else {
            executionOrderMainCommand.setLineTypeCode("Standard line");
        }
//        if (executionOrderMainCommand.getActualQuantity() != null) {
//            executionOrderMainCommand.setActualQuantity(executionOrderMainCommand.getActualQuantity() + executionOrderMainCommand.getOverFulfillmentPercentage() / 100);
//        }
        executionOrderMainCommand.setUnlimitedOverFulfillment(source.getUnlimitedOverFulfillment());
        executionOrderMainCommand.setManualPriceEntryAllowed(source.getManualPriceEntryAllowed());
        executionOrderMainCommand.setExternalServiceNumber(source.getExternalServiceNumber());
        executionOrderMainCommand.setServiceText(source.getServiceText());
        executionOrderMainCommand.setLineText(source.getLineText());
        executionOrderMainCommand.setLineNumber(source.getLineNumber());
        executionOrderMainCommand.setBiddersLine(source.getBiddersLine());
        executionOrderMainCommand.setSupplementaryLine(source.getSupplementaryLine());
        executionOrderMainCommand.setLotCostOne(source.getLotCostOne() != null ? source.getLotCostOne() : false);

        if (executionOrderMainCommand.getLotCostOne()) {
            executionOrderMainCommand.setTotal(executionOrderMainCommand.getAmountPerUnit());
        }
        executionOrderMainCommand.setDoNotPrint(source.getDoNotPrint());
        if (source.getServiceNumber() != null) {
            executionOrderMainCommand.setServiceNumberCode(source.getServiceNumber().getServiceNumberCode());
        }
//        if (source.getExecutionOrderSubList() != null && source.getExecutionOrderSubList().size() > 0) {
//            source.getExecutionOrderSubList()
//                    .forEach(executionOrderSub -> executionOrderMainCommand.getExecutionOrderSub().add(executionOrderSubConverter.convert(executionOrderSub)));
//        }

        return executionOrderMainCommand;
    }
}
