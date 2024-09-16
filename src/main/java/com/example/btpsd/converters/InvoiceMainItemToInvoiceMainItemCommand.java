package com.example.btpsd.converters;

import com.example.btpsd.commands.InvoiceMainItemCommand;
import com.example.btpsd.model.InvoiceMainItem;
import io.micrometer.common.lang.Nullable;
import lombok.RequiredArgsConstructor;
import lombok.Synchronized;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;
import java.math.RoundingMode;

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
//        invoiceMainItemCommand.setTotalWithProfit( ((invoiceMainItemCommand.getProfitMargin()/100) * invoiceMainItemCommand.getTotal()) + invoiceMainItemCommand.getTotal());
        invoiceMainItemCommand.setDoNotPrint(source.getDoNotPrint());
//        invoiceMainItemCommand.setAmountPerUnitWithProfit( ((invoiceMainItemCommand.getProfitMargin()/100) * invoiceMainItemCommand.getAmountPerUnit()) + invoiceMainItemCommand.getAmountPerUnit());
        if (source.getServiceNumber() != null) {
            invoiceMainItemCommand.setServiceNumberCode(source.getServiceNumber().getServiceNumberCode());
        }
        if (source.getSubItemList() != null && source.getSubItemList().size() > 0) {
            source.getSubItemList()
                    .forEach(subItem -> invoiceMainItemCommand.getSubItems().add(subItemConverter.convert(subItem)));
        }

        invoiceMainItemCommand.setTotal(new BigDecimal(invoiceMainItemCommand.getTotal()).setScale(2, RoundingMode.HALF_UP).doubleValue());
        invoiceMainItemCommand.setAmountPerUnit(new BigDecimal(invoiceMainItemCommand.getAmountPerUnit()).setScale(2, RoundingMode.HALF_UP).doubleValue());

        if(invoiceMainItemCommand.getProfitMargin() != null){
            invoiceMainItemCommand.setTotalWithProfit(((invoiceMainItemCommand.getProfitMargin() / 100) * invoiceMainItemCommand.getTotal()) + invoiceMainItemCommand.getTotal());
            invoiceMainItemCommand.setAmountPerUnitWithProfit(((invoiceMainItemCommand.getProfitMargin() / 100) * invoiceMainItemCommand.getAmountPerUnit()) + invoiceMainItemCommand.getAmountPerUnit());
            invoiceMainItemCommand.setTotalWithProfit(new BigDecimal(invoiceMainItemCommand.getTotalWithProfit()).setScale(2, RoundingMode.HALF_UP).doubleValue());
            invoiceMainItemCommand.setAmountPerUnit(new BigDecimal(invoiceMainItemCommand.getAmountPerUnit()).setScale(2, RoundingMode.HALF_UP).doubleValue());
        }
        else {
            invoiceMainItemCommand.setTotalWithProfit(((0 / 100) * invoiceMainItemCommand.getTotal()) + invoiceMainItemCommand.getTotal());
            invoiceMainItemCommand.setAmountPerUnitWithProfit(((0 / 100) * invoiceMainItemCommand.getAmountPerUnit()) + invoiceMainItemCommand.getAmountPerUnit());
            invoiceMainItemCommand.setTotalWithProfit(new BigDecimal(invoiceMainItemCommand.getTotalWithProfit()).setScale(2, RoundingMode.HALF_UP).doubleValue());
            invoiceMainItemCommand.setAmountPerUnitWithProfit(new BigDecimal(invoiceMainItemCommand.getAmountPerUnitWithProfit()).setScale(2, RoundingMode.HALF_UP).doubleValue());
        }

        return invoiceMainItemCommand;
    }

}
