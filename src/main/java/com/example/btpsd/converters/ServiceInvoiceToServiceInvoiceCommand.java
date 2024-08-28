package com.example.btpsd.converters;

import com.example.btpsd.commands.ExecutionOrderMainCommand;
import com.example.btpsd.commands.ServiceInvoiceMainCommand;
import com.example.btpsd.model.ExecutionOrderMain;
import com.example.btpsd.model.ServiceInvoiceMain;
import lombok.RequiredArgsConstructor;
import lombok.Synchronized;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;

@RequiredArgsConstructor
@Component
public class ServiceInvoiceToServiceInvoiceCommand implements Converter<ServiceInvoiceMain, ServiceInvoiceMainCommand> {

    @Synchronized
    @Override
    public ServiceInvoiceMainCommand convert(ServiceInvoiceMain source) {

        if (source == null) {
            return null;
        }

        final ServiceInvoiceMainCommand serviceInvoiceMainCommand = new ServiceInvoiceMainCommand();
        serviceInvoiceMainCommand.setServiceInvoiceCode(source.getServiceInvoiceCode());
        serviceInvoiceMainCommand.setDescription(source.getDescription());
        serviceInvoiceMainCommand.setUnitOfMeasurementCode(source.getUnitOfMeasurementCode());
        serviceInvoiceMainCommand.setCurrencyCode(source.getCurrencyCode());
        serviceInvoiceMainCommand.setMaterialGroupCode(source.getMaterialGroupCode());
        serviceInvoiceMainCommand.setPersonnelNumberCode(source.getPersonnelNumberCode());
        serviceInvoiceMainCommand.setServiceTypeCode(source.getServiceTypeCode());
        serviceInvoiceMainCommand.setQuantity(source.getQuantity());
//        serviceInvoiceMainCommand.setRemainingQuantity();
        serviceInvoiceMainCommand.setAlternatives(source.getAlternatives());
        serviceInvoiceMainCommand.setTotalQuantity(source.getTotalQuantity());
        serviceInvoiceMainCommand.setAmountPerUnit(source.getAmountPerUnit());
        serviceInvoiceMainCommand.setTotal(source.getTotalQuantity() * source.getAmountPerUnit());
        serviceInvoiceMainCommand.setActualQuantity(source.getActualQuantity());
        serviceInvoiceMainCommand.setActualPercentage(source.getActualPercentage());
        serviceInvoiceMainCommand.setOverFulfillmentPercentage(source.getOverFulfillmentPercentage());
        if(source.getLineTypeCode() != null){
            serviceInvoiceMainCommand.setLineTypeCode(source.getLineTypeCode());
        }
        else {
            serviceInvoiceMainCommand.setLineTypeCode("Standard line");
        }
        if (serviceInvoiceMainCommand.getActualQuantity() != null) {
            serviceInvoiceMainCommand.setActualQuantity(serviceInvoiceMainCommand.getActualQuantity() + serviceInvoiceMainCommand.getOverFulfillmentPercentage() / 100);
        }
        serviceInvoiceMainCommand.setUnlimitedOverFulfillment(source.getUnlimitedOverFulfillment());
        serviceInvoiceMainCommand.setExternalServiceNumber(source.getExternalServiceNumber());
        serviceInvoiceMainCommand.setServiceText(source.getServiceText());
        serviceInvoiceMainCommand.setLineText(source.getLineText());
        serviceInvoiceMainCommand.setLineNumber(source.getLineNumber());
        serviceInvoiceMainCommand.setBiddersLine(source.getBiddersLine());
        serviceInvoiceMainCommand.setSupplementaryLine(source.getSupplementaryLine());
        serviceInvoiceMainCommand.setDoNotPrint(source.getDoNotPrint());
        serviceInvoiceMainCommand.setLotCostOne(source.getLotCostOne() != null ? source.getLotCostOne() : false);
        if (serviceInvoiceMainCommand.getLotCostOne()) {
            serviceInvoiceMainCommand.setTotal(serviceInvoiceMainCommand.getAmountPerUnit());
        }
        if (source.getServiceNumber() != null) {
            serviceInvoiceMainCommand.setServiceNumberCode(source.getServiceNumber().getServiceNumberCode());
        }
        return serviceInvoiceMainCommand;
    }
}
