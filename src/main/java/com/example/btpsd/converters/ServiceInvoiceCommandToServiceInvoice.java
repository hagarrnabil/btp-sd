package com.example.btpsd.converters;

import com.example.btpsd.commands.ServiceInvoiceMainCommand;
import com.example.btpsd.model.ExecutionOrderMain;
import com.example.btpsd.model.ServiceInvoiceMain;
import com.example.btpsd.model.ServiceNumber;
import com.example.btpsd.repositories.ServiceInvoiceMainRepository;
import com.example.btpsd.services.ExecutionOrderMainService;
import com.example.btpsd.services.ServiceInvoiceMainService;
import io.micrometer.common.lang.Nullable;
import lombok.RequiredArgsConstructor;
import lombok.Synchronized;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;

@RequiredArgsConstructor
@Component
public class ServiceInvoiceCommandToServiceInvoice implements Converter<ServiceInvoiceMainCommand, ServiceInvoiceMain> {

    private final ExecutionOrderMainService executionOrderMainService;
    private final ServiceInvoiceMainRepository serviceInvoiceMainRepository;
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
        serviceInvoiceMain.setExecutionOrderMainCode(source.getExecutionOrderMainCode());
        serviceInvoiceMain.setReferenceId(source.getReferenceId());
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
        serviceInvoiceMain.setTemporaryDeletion(source.getTemporaryDeletion());
        serviceInvoiceMain.setReferenceSDDocument(source.getReferenceSDDocument());
        // Initialize actualQuantity
        Integer calculatedActualQuantity = source.getQuantity();


        if (source.getExecutionOrderMainCode() != null) {
            ExecutionOrderMain existingExecutionOrder = executionOrderMainService
                    .findById(source.getExecutionOrderMainCode());

            if (existingExecutionOrder != null) {
                // Handle null ActualQuantity with a default value of 0
                Integer existingActualQuantity = existingExecutionOrder.getActualQuantity();
                calculatedActualQuantity += (existingActualQuantity != null ? existingActualQuantity : 0);
            }
        }

        // Enforce overfulfillment logic
        Integer totalQuantity = source.getTotalQuantity() != null ? source.getTotalQuantity() : 0;
        if (calculatedActualQuantity > totalQuantity) {
            boolean canOverFulfill = Boolean.TRUE.equals(source.getUnlimitedOverFulfillment()) ||
                    (source.getOverFulfillmentPercentage() != null &&
                            calculatedActualQuantity <= totalQuantity + (totalQuantity * source.getOverFulfillmentPercentage() / 100));

            if (!canOverFulfill) {
                throw new IllegalArgumentException("Actual quantity exceeds total quantity without allowed overfulfillment.");
            }
        }

        serviceInvoiceMain.setActualQuantity(calculatedActualQuantity);

        // Calculate remaining quantity and total
        serviceInvoiceMain.setTotal(source.getQuantity() * serviceInvoiceMain.getAmountPerUnit());
        serviceInvoiceMain.setRemainingQuantity(totalQuantity - calculatedActualQuantity);

        // Calculate actualPercentage: (Actual Quantity * 100) / Total Quantity
        if (totalQuantity > 0) {
            Integer actualPercentage = (calculatedActualQuantity * 100) / totalQuantity;
            serviceInvoiceMain.setActualPercentage(actualPercentage);
        } else {
            serviceInvoiceMain.setActualPercentage(0);
        }

        // Ensure actualQuantity is not null before invoking intValue()
        Integer actualQuantity = serviceInvoiceMain.getExecutionOrderMain() != null
                ? serviceInvoiceMain.getExecutionOrderMain().getActualQuantity()
                : null;

        if (actualQuantity != null) {
            // Proceed with conversion using actualQuantity
            // Example: serviceInvoiceMain.setQuantity(actualQuantity.intValue());
        } else {
            // Handle case when actualQuantity is null, e.g., log or set a default value
            serviceInvoiceMain.setQuantity(0); // Set a default or log an error
        }

        serviceInvoiceMain.setOverFulfillmentPercentage(source.getOverFulfillmentPercentage());
        serviceInvoiceMain.setUnlimitedOverFulfillment(source.getUnlimitedOverFulfillment());
        serviceInvoiceMain.setExternalServiceNumber(source.getExternalServiceNumber());
        serviceInvoiceMain.setServiceText(source.getServiceText());
        serviceInvoiceMain.setLineText(source.getLineText());
        serviceInvoiceMain.setLineNumber(source.getLineNumber());
        serviceInvoiceMain.setBiddersLine(source.getBiddersLine());
        serviceInvoiceMain.setSupplementaryLine(source.getSupplementaryLine());
        serviceInvoiceMain.setDoNotPrint(source.getDoNotPrint());
        serviceInvoiceMain.setLotCostOne(source.getLotCostOne() != null ? source.getLotCostOne() : false);
        serviceInvoiceMain.setTotalHeader((double) 0);
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