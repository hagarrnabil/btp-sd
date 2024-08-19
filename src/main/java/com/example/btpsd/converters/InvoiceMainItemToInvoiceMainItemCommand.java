package com.example.btpsd.converters;

import com.example.btpsd.commands.InvoiceMainItemCommand;
import com.example.btpsd.model.InvoiceMainItem;
import io.micrometer.common.lang.Nullable;
import lombok.RequiredArgsConstructor;
import lombok.Synchronized;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;

@RequiredArgsConstructor
@Component
public class InvoiceMainItemToInvoiceMainItemCommand implements Converter<InvoiceMainItem, InvoiceMainItemCommand> {

    private final InvoiceSubItemToInvoiceSubItemCommand subItemConverter;

    @Synchronized
    @Nullable
    @Override
    public InvoiceMainItemCommand convert(InvoiceMainItem source) {

        if (source == null) {
            return null;
        }

        final InvoiceMainItemCommand invoiceMainItemCommand = new InvoiceMainItemCommand();
        invoiceMainItemCommand.setInvoiceMainItemCode(source.getInvoiceMainItemCode());
        invoiceMainItemCommand.setUnitOfMeasurementCode(source.getUnitOfMeasurementCode());
        invoiceMainItemCommand.setCurrencyCode(source.getCurrencyCode());
        invoiceMainItemCommand.setFormulaCode(source.getFormulaCode());
        invoiceMainItemCommand.setDescription(source.getDescription());
        invoiceMainItemCommand.setQuantity(source.getQuantity());
        invoiceMainItemCommand.setAmountPerUnit(source.getAmountPerUnit());
        invoiceMainItemCommand.setTotal(source.getQuantity() * source.getAmountPerUnit());
        invoiceMainItemCommand.setProfitMargin(source.getProfitMargin());
        invoiceMainItemCommand.setTotalWithProfit( ((invoiceMainItemCommand.getProfitMargin()/100) * invoiceMainItemCommand.getTotal()) + invoiceMainItemCommand.getTotal());
        invoiceMainItemCommand.setDoNotPrint(source.getDoNotPrint());
        invoiceMainItemCommand.setAmountPerUnitWithProfit( ((invoiceMainItemCommand.getProfitMargin()/100) * invoiceMainItemCommand.getAmountPerUnit()) + invoiceMainItemCommand.getAmountPerUnit());
        if (source.getServiceNumber() != null) {
            invoiceMainItemCommand.setServiceNumberCode(source.getServiceNumber().getServiceNumberCode());
        }
        if (source.getSubItemList() != null && source.getSubItemList().size() > 0) {
            source.getSubItemList()
                    .forEach(subItem -> invoiceMainItemCommand.getSubItems().add(subItemConverter.convert(subItem)));
        }

        return invoiceMainItemCommand;
    }

}
