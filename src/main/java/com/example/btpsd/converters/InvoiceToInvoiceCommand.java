package com.example.btpsd.converters;

import com.example.btpsd.commands.InvoiceCommand;
import com.example.btpsd.model.Invoice;
import io.micrometer.common.lang.Nullable;
import lombok.RequiredArgsConstructor;
import lombok.Synchronized;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;

@RequiredArgsConstructor
@Component
public class InvoiceToInvoiceCommand implements Converter<Invoice, InvoiceCommand> {

    @Synchronized
    @Nullable
    @Override
    public InvoiceCommand convert(Invoice source) {

        if (source == null) {
            return null;
        }

        final InvoiceCommand invoiceCommand = new InvoiceCommand();
        invoiceCommand.setInvoiceCode(source.getInvoiceCode());
        invoiceCommand.setUnitOfMeasurementCode(source.getUnitOfMeasurementCode());
        invoiceCommand.setCurrencyCode(source.getCurrencyCode());
        invoiceCommand.setFormulaCode(source.getFormulaCode());
        invoiceCommand.setQuantity(source.getQuantity());
        invoiceCommand.setAmountPerUnit(source.getAmountPerUnit());
        invoiceCommand.setTotal(source.getQuantity() * source.getAmountPerUnit());
        invoiceCommand.setProfitMargin(source.getProfitMargin());
        invoiceCommand.setTotalWithProfit( (invoiceCommand.getProfitMargin()/100) * invoiceCommand.getTotal() );
        if (source.getServiceNumber() != null) {
            invoiceCommand.setServiceNumberCode(source.getServiceNumber().getServiceNumberCode());
        }
        for (int i = 0; i < source.getSubItemCode().size(); i++) {
            invoiceCommand.setSubItemCode(source.getSubItemCode());
        }
        for (int i = 0; i < source.getMainItemCode().size(); i++) {
            invoiceCommand.setMainItemCode(source.getMainItemCode());
        }
        return invoiceCommand;

    }
}
