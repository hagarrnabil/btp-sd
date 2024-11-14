package com.example.btpsd.converters;

import com.example.btpsd.commands.ExecutionOrderMainCommand;
import com.example.btpsd.commands.ServiceInvoiceMainCommand;
import com.example.btpsd.model.ExecutionOrderMain;
import com.example.btpsd.model.ServiceInvoiceMain;
import lombok.RequiredArgsConstructor;
import lombok.Synchronized;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;
import java.math.RoundingMode;

@RequiredArgsConstructor
@Component
public class ServiceInvoiceToServiceInvoiceCommand implements Converter<ServiceInvoiceMain, ServiceInvoiceMainCommand> {

    @Synchronized
    @Override
    public ServiceInvoiceMainCommand convert(ServiceInvoiceMain source) {
        if (source == null) {
            return null;
        }

        final ServiceInvoiceMainCommand command = new ServiceInvoiceMainCommand();
        command.setServiceInvoiceCode(source.getServiceInvoiceCode());
        command.setExecutionOrderMainCode(source.getExecutionOrderMainCode());
        command.setDescription(source.getDescription());
        command.setUnitOfMeasurementCode(source.getUnitOfMeasurementCode());
        command.setCurrencyCode(source.getCurrencyCode());
        command.setMaterialGroupCode(source.getMaterialGroupCode());
        command.setPersonnelNumberCode(source.getPersonnelNumberCode());
        command.setServiceTypeCode(source.getServiceTypeCode());
        command.setTotalQuantity(source.getTotalQuantity());
        command.setAlternatives(source.getAlternatives());

        // Set quantity and amountPerUnit with null checks
        command.setQuantity(source.getQuantity() != null ? source.getQuantity() : 0);
        command.setAmountPerUnit(source.getAmountPerUnit() != null ? source.getAmountPerUnit() : 0.0);

        // Calculate and set total as quantity * amountPerUnit
        double total = command.getQuantity() * command.getAmountPerUnit();
        command.setTotal(new BigDecimal(total).setScale(2, RoundingMode.HALF_UP).doubleValue());

        command.setRemainingQuantity(source.getRemainingQuantity());
        command.setActualQuantity(source.getActualQuantity());
        command.setActualPercentage(source.getActualPercentage());
        command.setOverFulfillmentPercentage(source.getOverFulfillmentPercentage());
        command.setLineTypeCode(source.getLineTypeCode() != null ? source.getLineTypeCode() : "Standard line");
        command.setTotalHeader(source.getTotalHeader());
        command.setReferenceId(source.getReferenceId());
        command.setReferenceSDDocument(source.getReferenceSDDocument());
        command.setUnlimitedOverFulfillment(source.getUnlimitedOverFulfillment());
        command.setExternalServiceNumber(source.getExternalServiceNumber());
        command.setServiceText(source.getServiceText());
        command.setLineText(source.getLineText());
        command.setLineNumber(source.getLineNumber());
        command.setBiddersLine(source.getBiddersLine());
        command.setSupplementaryLine(source.getSupplementaryLine());
        command.setDoNotPrint(source.getDoNotPrint());
        command.setLotCostOne(source.getLotCostOne() != null ? source.getLotCostOne() : false);

        if (command.getLotCostOne()) {
            command.setTotal(command.getAmountPerUnit());
        }

        if (source.getServiceNumber() != null) {
            command.setServiceNumberCode(source.getServiceNumber().getServiceNumberCode());
        }

        return command;
    }
}