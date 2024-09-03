package com.example.btpsd.converters;

import com.example.btpsd.commands.ServiceInvoiceMainCommand;
import com.example.btpsd.model.ExecutionOrderMain;
import com.example.btpsd.model.ServiceInvoiceMain;
import com.example.btpsd.model.ServiceNumber;
import com.example.btpsd.services.ExecutionOrderMainService;
import io.micrometer.common.lang.Nullable;
import lombok.RequiredArgsConstructor;
import lombok.Synchronized;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;

@RequiredArgsConstructor
@Component
public class ServiceInvoiceCommandToServiceInvoice implements Converter<ServiceInvoiceMainCommand, ServiceInvoiceMain> {

    private final ExecutionOrderMainService executionOrderMainService;
    private final ExecutionOrderMainToExecutionOrderMainCommand executionOrderMainToExecutionOrderMainCommand;

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
        Integer calculatedActualQuantity = serviceInvoiceMain.getQuantity() +
                (serviceInvoiceMain.getExecutionOrderMain() != null ? serviceInvoiceMain.getExecutionOrderMain().getActualQuantity() : 0);
        serviceInvoiceMain.setActualQuantity(calculatedActualQuantity);
        if (source.getQuantity() != null) {
            serviceInvoiceMain.setTotal(source.getQuantity() * serviceInvoiceMain.getAmountPerUnit());
            serviceInvoiceMain.setRemainingQuantity(serviceInvoiceMain.getTotalQuantity() - serviceInvoiceMain.getActualQuantity());
        }
        if (serviceInvoiceMain.getExecutionOrderMain() != null) {
            ExecutionOrderMain executionOrderMain = serviceInvoiceMain.getExecutionOrderMain();
            executionOrderMain.setActualQuantity(calculatedActualQuantity); // Reflect back to ExecutionOrderMain

            // Make sure to save the executionOrderMain
            // This assumes you have access to an ExecutionOrderMain repository or service
            executionOrderMainService.saveExecutionOrderMainCommand(executionOrderMainToExecutionOrderMainCommand.convert(executionOrderMain));
        }
        // Synchronize with ExecutionOrderMain
        if (serviceInvoiceMain.getExecutionOrderMain() != null) {
            serviceInvoiceMain.updateFromExecutionOrder(serviceInvoiceMain.getExecutionOrderMain());
        }
        serviceInvoiceMain.setActualPercentage(source.getActualPercentage());
        serviceInvoiceMain.setOverFulfillmentPercentage(source.getOverFulfillmentPercentage());
        if(source.getLineTypeCode() != null){
            serviceInvoiceMain.setLineTypeCode(source.getLineTypeCode());
        }
        else {
            serviceInvoiceMain.setLineTypeCode("Standard line");
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
