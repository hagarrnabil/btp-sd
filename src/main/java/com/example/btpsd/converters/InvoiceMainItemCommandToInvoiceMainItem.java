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
        mainItem.setQuantity(source.getQuantity());
        mainItem.setProfitMargin(source.getProfitMargin());
        mainItem.setDoNotPrint(source.getDoNotPrint());

        if (source.getServiceNumberCode() != null) {
            ServiceNumber serviceNumber = new ServiceNumber();
            serviceNumber.setServiceNumberCode(source.getServiceNumberCode());
            mainItem.setServiceNumber(serviceNumber);
            serviceNumber.addMainItem(mainItem);
        }

        if (source.getSubItems() != null && !source.getSubItems().isEmpty()) {
            double totalAmountPerUnitFromSubItems = 0.0;

            for (InvoiceSubItemCommand subItemCommand : source.getSubItems()) {
                InvoiceSubItem subItem = subItemConverter.convert(subItemCommand);
                if (subItem != null) {
                    totalAmountPerUnitFromSubItems += subItem.getAmountPerUnit();
                    subItem.setMainItem(mainItem);  // Ensure bi-directional relationship
                    mainItem.addSubItem(subItem);
                }
            }

            mainItem.setAmountPerUnit(totalAmountPerUnitFromSubItems);
        } else {
            // Use the manually entered amountPerUnit if no subItems are present
            mainItem.setAmountPerUnit(source.getAmountPerUnit());
        }

        mainItem.setTotal(mainItem.getQuantity() * mainItem.getAmountPerUnit());
        mainItem.setTotalWithProfit((mainItem.getProfitMargin() / 100) * mainItem.getTotal());
        mainItem.setAmountPerUnitWithProfit((mainItem.getProfitMargin() / 100) * mainItem.getAmountPerUnit());
        ExecutionOrderMain executionOrderMain = new ExecutionOrderMain(mainItem);
        mainItem.setExecutionOrderMain(executionOrderMain);
        return mainItem;
    }
}
