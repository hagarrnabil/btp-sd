package com.example.btpsd.converters;

import com.example.btpsd.commands.InvoiceSubItemCommand;
import com.example.btpsd.model.InvoiceSubItem;
import io.micrometer.common.lang.Nullable;
import lombok.RequiredArgsConstructor;
import lombok.Synchronized;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;

@RequiredArgsConstructor
@Component
public class InvoiceSubItemToInvoiceSubItemCommand implements Converter<InvoiceSubItem, InvoiceSubItemCommand> {


    @Synchronized
    @Nullable
    @Override
    public InvoiceSubItemCommand convert(InvoiceSubItem source) {

        if (source == null) {
            return null;
        }

        final InvoiceSubItemCommand subItemCommand = new InvoiceSubItemCommand();
        subItemCommand.setInvoiceSubItemCode(source.getInvoiceSubItemCode());
        subItemCommand.setUnitOfMeasurementCode(source.getUnitOfMeasurementCode());
        subItemCommand.setCurrencyCode(source.getCurrencyCode());
        subItemCommand.setFormulaCode(source.getFormulaCode());
        subItemCommand.setDescription(source.getDescription());
        subItemCommand.setQuantity(source.getQuantity());
        subItemCommand.setAmountPerUnit(source.getAmountPerUnit());
        subItemCommand.setTotal(source.getAmountPerUnit() * source.getQuantity());
        if (source.getServiceNumber() != null) {
            subItemCommand.setServiceNumberCode(source.getServiceNumber().getServiceNumberCode());
        }
        if (source.getMainItem() != null) {
            subItemCommand.setInvoiceMainItemCode(source.getMainItem().getInvoiceMainItemCode());
        }
        return subItemCommand;
    }

}
