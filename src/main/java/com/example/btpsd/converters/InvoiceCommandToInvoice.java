package com.example.btpsd.converters;

import com.example.btpsd.commands.InvoiceCommand;
import com.example.btpsd.model.Invoice;
import com.example.btpsd.model.MainItem;
import com.example.btpsd.model.ServiceNumber;
import com.example.btpsd.model.SubItem;
import io.micrometer.common.lang.Nullable;
import lombok.RequiredArgsConstructor;
import lombok.Synchronized;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;

@RequiredArgsConstructor
@Component
public class InvoiceCommandToInvoice implements Converter<InvoiceCommand, Invoice> {

    @Synchronized
    @Nullable
    @Override
    public Invoice convert(InvoiceCommand source) {

        if (source == null) {
            return null;
        }

        final Invoice invoice = new Invoice();
        invoice.setInvoiceCode(source.getInvoiceCode());
        invoice.setUnitOfMeasurementCode(source.getUnitOfMeasurementCode());
        invoice.setCurrencyCode(source.getCurrencyCode());
        invoice.setFormulaCode(source.getFormulaCode());
        invoice.setQuantity(source.getQuantity());
        invoice.setAmountPerUnit(source.getAmountPerUnit());
        invoice.setTotal(source.getSubItemCommand().getAmountPerUnit());
        invoice.setProfitMargin(source.getProfitMargin());
        invoice.setTotalWithProfit( (invoice.getProfitMargin()/100) * invoice.getTotal() );
        if (source.getServiceNumberCode() != null) {
            ServiceNumber serviceNumber = new ServiceNumber();
            serviceNumber.setServiceNumberCode(source.getServiceNumberCode());
            invoice.setServiceNumber(serviceNumber);
            serviceNumber.addInvoice(invoice);
        }
        if (source.getSubItemCode() != null) {
            SubItem subItem = new SubItem();
            subItem.setSubItemCode(source.getSubItemCode());
            invoice.setSubItem(subItem);
            subItem.addInvoice(invoice);
        }
        if (source.getMainItemCode() != null) {
            MainItem mainItem = new MainItem();
            mainItem.setMainItemCode(source.getMainItemCode());
            invoice.setMainItem(mainItem);
            mainItem.addInvoice(invoice);
        }
        return invoice;
    }
}
