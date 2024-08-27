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

        if(mainItem.getProfitMargin() != null){
            mainItem.setTotalWithProfit(new BigDecimal(mainItem.getTotal()).setScale(2, RoundingMode.HALF_UP).doubleValue());
            mainItem.setAmountPerUnitWithProfit(new BigDecimal(mainItem.getAmountPerUnitWithProfit()).setScale(2, RoundingMode.HALF_UP).doubleValue());
        }
        else {
            mainItem.setTotalWithProfit(null);
            mainItem.setAmountPerUnitWithProfit(null);
        }

        mainItem.setTotal(mainItem.getQuantity() * mainItem.getAmountPerUnit());
        ExecutionOrderMain executionOrderMain = new ExecutionOrderMain(mainItem);
        mainItem.setExecutionOrderMain(executionOrderMain);
        mainItem.setTotal(new BigDecimal(mainItem.getTotal()).setScale(2, RoundingMode.HALF_UP).doubleValue());
//        mainItem.setTotalWithProfit(new BigDecimal(mainItem.getTotalWithProfit()).setScale(2, RoundingMode.HALF_UP).doubleValue());
        mainItem.setAmountPerUnit(new BigDecimal(mainItem.getAmountPerUnit()).setScale(2, RoundingMode.HALF_UP).doubleValue());
//        mainItem.setAmountPerUnitWithProfit(new BigDecimal(mainItem.getAmountPerUnitWithProfit()).setScale(2, RoundingMode.HALF_UP).doubleValue());
        return mainItem;
    }
}
