package com.example.btpsd.converters;

import com.example.btpsd.commands.ExecutionOrderMainCommand;
import com.example.btpsd.commands.ExecutionOrderSubCommand;
import com.example.btpsd.commands.ServiceInvoiceMainCommand;
import com.example.btpsd.model.ExecutionOrderMain;
import com.example.btpsd.model.ExecutionOrderSub;
import com.example.btpsd.model.ServiceInvoiceMain;
import com.example.btpsd.model.ServiceNumber;
import io.micrometer.common.lang.Nullable;
import lombok.RequiredArgsConstructor;
import lombok.Synchronized;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;

@RequiredArgsConstructor
@Component
public class ServiceInvoiceCommandToServiceInvoice implements Converter<ServiceInvoiceMainCommand, ServiceInvoiceMain> {

    @Synchronized
    @Nullable
    @Override
    public ServiceInvoiceMain convert(ServiceInvoiceMainCommand source) {

        if (source == null) {
            return null;
        }

        final ServiceInvoiceMain serviceInvoiceMain = new ServiceInvoiceMain();
        serviceInvoiceMain.setServiceInvoiceCode(source.getServiceInvoiceCode());
        serviceInvoiceMain.setDescription(source.getDescription());
        serviceInvoiceMain.setUnitOfMeasurementCode(source.getUnitOfMeasurementCode());
        serviceInvoiceMain.setCurrencyCode(source.getCurrencyCode());
        serviceInvoiceMain.setMaterialGroupCode(source.getMaterialGroupCode());
        serviceInvoiceMain.setPersonnelNumberCode(source.getPersonnelNumberCode());
        serviceInvoiceMain.setServiceTypeCode(source.getServiceTypeCode());
        serviceInvoiceMain.setQuantity(source.getQuantity());
        serviceInvoiceMain.setAlternatives(source.getAlternatives());
        serviceInvoiceMain.setTotalQuantity(source.getTotalQuantity());
        serviceInvoiceMain.setAmountPerUnit(source.getAmountPerUnit());
        if (serviceInvoiceMain.getTotalQuantity() != null){
            serviceInvoiceMain.setTotal(serviceInvoiceMain.getTotalQuantity() * serviceInvoiceMain.getAmountPerUnit());
        }
        if (serviceInvoiceMain.getQuantity() != null)
        {
            serviceInvoiceMain.setTotal(serviceInvoiceMain.getQuantity() * serviceInvoiceMain.getAmountPerUnit());
//            serviceInvoiceMain.setActualQuantity(source.getQuantity() + serviceInvoiceMain.getActualQuantity());
//            serviceInvoiceMain.setRemainingQuantity(source.getQuantity() - source.getActualQuantity());

        }
        serviceInvoiceMain.setActualPercentage(source.getActualPercentage());
        serviceInvoiceMain.setOverFulfillmentPercentage(source.getOverFulfillmentPercentage());
        if(source.getLineTypeCode() != null){
            serviceInvoiceMain.setLineTypeCode(source.getLineTypeCode());
        }
        else {
            serviceInvoiceMain.setLineTypeCode("Standard line");
        }
        if (serviceInvoiceMain.getActualQuantity() != null) {
            serviceInvoiceMain.setActualQuantity(serviceInvoiceMain.getActualQuantity() + serviceInvoiceMain.getOverFulfillmentPercentage() / 100);
        }
        serviceInvoiceMain.setUnlimitedOverFulfillment(source.getUnlimitedOverFulfillment());
        serviceInvoiceMain.setExternalServiceNumber(source.getExternalServiceNumber());
        serviceInvoiceMain.setServiceText(source.getServiceText());
        serviceInvoiceMain.setLineText(source.getLineText());
        serviceInvoiceMain.setLineNumber(source.getLineNumber());
        serviceInvoiceMain.setBiddersLine(source.getBiddersLine());
        serviceInvoiceMain.setSupplementaryLine(source.getSupplementaryLine());
        serviceInvoiceMain.setDoNotPrint(source.getDoNotPrint());
        serviceInvoiceMain.setLotCostOne(source.getLotCostOne() != null ? source.getLotCostOne() : false);
        if (serviceInvoiceMain.getLotCostOne()) {
            serviceInvoiceMain.setTotal(serviceInvoiceMain.getAmountPerUnit());
        }
        if (source.getServiceNumberCode() != null) {
            ServiceNumber serviceNumber = new ServiceNumber();
            serviceNumber.setServiceNumberCode(source.getServiceNumberCode());
            serviceInvoiceMain.setServiceNumber(serviceNumber);
            serviceNumber.addServiceInvoiceMain(serviceInvoiceMain);
        }
        return serviceInvoiceMain;

    }

}
