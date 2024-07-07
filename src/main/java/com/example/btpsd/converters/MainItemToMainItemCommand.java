package com.example.btpsd.converters;

import com.example.btpsd.commands.MainItemCommand;
import com.example.btpsd.commands.ServiceNumberCommand;
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
public class MainItemToMainItemCommand implements Converter<MainItem, MainItemCommand> {

    private final InvoiceToInvoiceCommand invoiceConverter;

    @Synchronized
    @Nullable
    @Override
    public MainItemCommand convert(MainItem source) {

        if (source == null) {
            return null;
        }

        final MainItemCommand mainItemCommand = new MainItemCommand();
        mainItemCommand.setMainItemCode(source.getMainItemCode());
        mainItemCommand.setUnitOfMeasurementCode(source.getUnitOfMeasurementCode());
        mainItemCommand.setCurrencyCode(source.getCurrencyCode());
        mainItemCommand.setFormulaCode(source.getFormulaCode());
        mainItemCommand.setQuantity(source.getQuantity());
        mainItemCommand.setAmountPerUnit(source.getAmountPerUnit());
        mainItemCommand.setTotal(source.getQuantity() * source.getAmountPerUnit());
        mainItemCommand.setProfitMargin(source.getProfitMargin());
        mainItemCommand.setTotalWithProfit( (mainItemCommand.getProfitMargin()/100) * mainItemCommand.getTotal() );
        if (source.getServiceNumber() != null) {
            mainItemCommand.setServiceNumberCode(source.getServiceNumber().getServiceNumberCode());
        }
//        if (source.getSubItemCode().isEmpty()){
//            mainItemCommand.setAmountPerUnit(source.getAmountPerUnit());
//            mainItemCommand.setTotal(source.getQuantity() * source.getAmountPerUnit());
//            mainItemCommand.setTotalWithProfit( (mainItemCommand.getProfitMargin()/100) * mainItemCommand.getTotal() );
//        }
//        else {
//            for (int i = 0; i < source.getSubItemCode().size(); i++) {
//                mainItemCommand.setSubItemCode(source.getSubItemCode());
//                mainItemCommand.setAmountPerUnit(source.getSubItem().getAmountPerUnit());
//                mainItemCommand.setTotal(source.getQuantity() * source.getSubItem().getAmountPerUnit());
//                mainItemCommand.setTotalWithProfit( (mainItemCommand.getProfitMargin()/100) * mainItemCommand.getTotal() );
//            }
//        }
        for (int i = 0; i < source.getSubItemCode().size(); i++) {
            mainItemCommand.setSubItemCode(source.getSubItemCode());
        }
        if (source.getInvoiceList() != null && source.getInvoiceList().size() > 0) {
            source.getInvoiceList()
                    .forEach(invoice -> mainItemCommand.getInvoiceCommandList().add(invoiceConverter.convert(invoice)));
        }
        return mainItemCommand;
    }

}
