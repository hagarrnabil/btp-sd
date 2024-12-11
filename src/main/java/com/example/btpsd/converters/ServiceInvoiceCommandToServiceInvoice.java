package com.example.btpsd.converters;

import com.example.btpsd.commands.ServiceInvoiceMainCommand;
import com.example.btpsd.model.ExecutionOrderMain;
import com.example.btpsd.model.ServiceInvoiceMain;
import com.example.btpsd.model.ServiceNumber;
import com.example.btpsd.repositories.ExecutionOrderMainRepository;
import com.example.btpsd.repositories.ServiceInvoiceMainRepository;
import com.example.btpsd.services.ExecutionOrderMainService;
import com.example.btpsd.services.ServiceInvoiceMainService;
import io.micrometer.common.lang.Nullable;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import lombok.Synchronized;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;
import java.math.RoundingMode;

@RequiredArgsConstructor
@Component
public class ServiceInvoiceCommandToServiceInvoice implements Converter<ServiceInvoiceMainCommand, ServiceInvoiceMain> {

    private final ExecutionOrderMainRepository executionOrderMainRepository;
    private final ServiceInvoiceMainRepository serviceInvoiceMainRepository;

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
        serviceInvoiceMain.setDebitMemoRequestItemText(source.getDebitMemoRequestItemText());
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

        // Calculate actualQuantity based on the serviceInvoiceMain's quantity and ExecutionOrderMain's actualQuantity
        Integer calculatedActualQuantity = serviceInvoiceMain.getQuantity() +
                (serviceInvoiceMain.getExecutionOrderMain() != null ? serviceInvoiceMain.getExecutionOrderMain().getActualQuantity() : 0);
        serviceInvoiceMain.setActualQuantity(calculatedActualQuantity);

        // Set quantity, defaulting to 0 if null
        Integer quantity = source.getQuantity() != null ? source.getQuantity() : 0;
        serviceInvoiceMain.setQuantity(quantity);

        // Set amountPerUnit, defaulting to 0.0 if null
        double amountPerUnit = source.getAmountPerUnit() != null ? source.getAmountPerUnit() : 0.0;
        serviceInvoiceMain.setAmountPerUnit(amountPerUnit);

        // Calculate total as quantity * amountPerUnit
        double total = quantity * amountPerUnit;
        serviceInvoiceMain.setTotal(new BigDecimal(total).setScale(2, RoundingMode.HALF_UP).doubleValue());

        // Set remaining and actual quantities
        serviceInvoiceMain.setTotalQuantity(source.getTotalQuantity());
        serviceInvoiceMain.setRemainingQuantity(source.getTotalQuantity() - quantity);
        serviceInvoiceMain.setActualQuantity(quantity);

        // Calculate actualPercentage if totalQuantity is set
        if (source.getTotalQuantity() != null && source.getTotalQuantity() > 0) {
            int actualPercentage = (quantity * 100) / source.getTotalQuantity();
            serviceInvoiceMain.setActualPercentage(actualPercentage);
        } else {
            serviceInvoiceMain.setActualPercentage(0);
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

        if (serviceInvoiceMain.getLotCostOne()) {
            serviceInvoiceMain.setTotal(serviceInvoiceMain.getAmountPerUnit());
        }

        if (source.getServiceNumberCode() != null) {
            ServiceNumber serviceNumber = new ServiceNumber();
            serviceNumber.setServiceNumberCode(source.getServiceNumberCode());
            serviceInvoiceMain.setServiceNumber(serviceNumber);
            serviceNumber.addServiceInvoiceMain(serviceInvoiceMain);
        }

        if (serviceInvoiceMain.getExecutionOrderMain() != null) {
            serviceInvoiceMain.updateFromExecutionOrder(serviceInvoiceMain.getExecutionOrderMain());
        }


        serviceInvoiceMain.setReferenceSDDocument(source.getReferenceSDDocument());
        serviceInvoiceMain.setTotalHeader(0.0); // TotalHeader will be recalculated during save
        return serviceInvoiceMain;
    }
}