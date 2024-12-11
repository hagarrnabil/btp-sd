package com.example.btpsd.converters;

import com.example.btpsd.commands.InvoiceMainItemCommand;
import com.example.btpsd.commands.InvoiceSubItemCommand;
import com.example.btpsd.model.*;
import com.example.btpsd.repositories.ExecutionOrderMainRepository;
import io.micrometer.common.lang.Nullable;
import lombok.RequiredArgsConstructor;
import lombok.Synchronized;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;
import java.math.RoundingMode;

@RequiredArgsConstructor
@Component
public class InvoiceMainItemCommandToInvoiceMainItem implements Converter<InvoiceMainItemCommand, InvoiceMainItem> {


    private final InvoiceSubItemCommandToInvoiceSubItem subItemConverter;

    @Synchronized
    @Nullable
    @Override
    public InvoiceMainItem convert(InvoiceMainItemCommand source) {

        if (source == null) {
            return null;
        }

        final InvoiceMainItem mainItem = new InvoiceMainItem();
        mainItem.setInvoiceMainItemCode(source.getInvoiceMainItemCode());
        mainItem.setUnitOfMeasurementCode(source.getUnitOfMeasurementCode());
        mainItem.setCurrencyCode(source.getCurrencyCode());
        mainItem.setFormulaCode(source.getFormulaCode());
        mainItem.setDescription(source.getDescription());

        // Ensure quantity is not null
        if (source.getQuantity() != null) {
            mainItem.setQuantity(source.getQuantity());
        } else {
            // Handle null quantity appropriately, e.g., set to 0 or throw an exception
            mainItem.setQuantity(0); // or throw new IllegalArgumentException("Quantity cannot be null");
        }

        mainItem.setProfitMargin(source.getProfitMargin());
        mainItem.setDoNotPrint(source.getDoNotPrint());

        if (source.getServiceNumberCode() != null) {
            ServiceNumber serviceNumber = new ServiceNumber();
            serviceNumber.setServiceNumberCode(source.getServiceNumberCode());
            mainItem.setServiceNumber(serviceNumber);
            serviceNumber.addMainItem(mainItem);
        }

        if (source.getSubItems() != null && !source.getSubItems().isEmpty()) {
            double totalFromSubItems = 0.0;

            for (InvoiceSubItemCommand subItemCommand : source.getSubItems()) {
                InvoiceSubItem subItem = subItemConverter.convert(subItemCommand);
                if (subItem != null) {
                    totalFromSubItems += subItem.getTotal(); // Sum the total of each sub-item
                    mainItem.addSubItem(subItem);
                }
            }

            // Set amountPerUnit to the total from sub-items divided by the quantity
            mainItem.setAmountPerUnit(totalFromSubItems);
        } else {
            // Use the manually entered amountPerUnit if no subItems are present
            mainItem.setAmountPerUnit(source.getAmountPerUnit());
        }

        // Ensure amountPerUnit is not null
        double amountPerUnit = mainItem.getAmountPerUnit() != null ? mainItem.getAmountPerUnit() : 0;

        // Calculate total
        mainItem.setTotal(mainItem.getQuantity() * amountPerUnit);
        mainItem.setTotal(new BigDecimal(mainItem.getTotal()).setScale(2, RoundingMode.HALF_UP).doubleValue());
        mainItem.setAmountPerUnit(new BigDecimal(amountPerUnit).setScale(2, RoundingMode.HALF_UP).doubleValue());

        mainItem.setReferenceSDDocument(source.getReferenceSDDocument());
        if (mainItem.getProfitMargin() != null) {
            mainItem.setTotalWithProfit(((mainItem.getProfitMargin() / 100) * mainItem.getTotal()) + mainItem.getTotal());
            mainItem.setAmountPerUnitWithProfit(((mainItem.getProfitMargin() / 100) * amountPerUnit) + amountPerUnit);
            mainItem.setTotalWithProfit(new BigDecimal(mainItem.getTotalWithProfit()).setScale(2, RoundingMode.HALF_UP).doubleValue());
            mainItem.setAmountPerUnitWithProfit(new BigDecimal(mainItem.getAmountPerUnitWithProfit()).setScale(2, RoundingMode.HALF_UP).doubleValue());
        } else {
            mainItem.setTotalWithProfit(mainItem.getTotal());
            mainItem.setAmountPerUnitWithProfit(amountPerUnit);
        }

        ExecutionOrderMain executionOrderMain = new ExecutionOrderMain(mainItem);
        mainItem.setExecutionOrderMain(executionOrderMain);
        // Set total header (this will be recalculated later in the save method)
        mainItem.setTotalHeader(0.0);
        mainItem.setUniqueId(source.getUniqueId());
        mainItem.setSalesQuotationItem(source.getSalesQuotationItem());
        mainItem.setSalesQuotationItemText(source.getSalesQuotationItemText());
        mainItem.setReferenceId(source.getReferenceId());
        return mainItem;
        }
}
