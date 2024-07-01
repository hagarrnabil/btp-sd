package com.example.btpsd.converters;

import com.example.btpsd.commands.SubItemCommand;
import com.example.btpsd.model.SubItem;
import io.micrometer.common.lang.Nullable;
import lombok.RequiredArgsConstructor;
import lombok.Synchronized;
import org.apache.commons.math3.random.RandomDataGenerator;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;

@RequiredArgsConstructor
@Component
public class SubItemToSubItemCommand implements Converter<SubItem, SubItemCommand> {

    private final InvoiceToInvoiceCommand invoiceConverter;

    private final MainItemToMainItemCommand mainItemConverter;

    @Synchronized
    @Nullable
    @Override
    public SubItemCommand convert(SubItem source) {

        if (source == null) {
            return null;
        }

        final SubItemCommand subItemCommand = new SubItemCommand();
        subItemCommand.setSubItemCode(source.getSubItemCode());
        subItemCommand.setUnitOfMeasurementCode(source.getUnitOfMeasurementCode());
        subItemCommand.setCurrencyCode(source.getCurrencyCode());
        subItemCommand.setFormulaCode(source.getFormulaCode());
        subItemCommand.setQuantity(source.getQuantity());
        subItemCommand.setAmountPerUnit(source.getAmountPerUnit());
        subItemCommand.setTotal(source.getAmountPerUnit() * source.getQuantity());
        if (source.getServiceNumber() != null) {
            subItemCommand.setServiceNumberCode(source.getServiceNumber().getServiceNumberCode());
        }
        if (source.getInvoiceList() != null && source.getInvoiceList().size() > 0) {
            source.getInvoiceList()
                    .forEach(invoice -> subItemCommand.getInvoiceCommandList().add(invoiceConverter.convert(invoice)));
        }
        if (source.getMainItemList() != null && source.getMainItemList().size() > 0) {
            source.getMainItemList()
                    .forEach(mainItem -> subItemCommand.getMainItemCommandList().add(mainItemConverter.convert(mainItem)));
        }
        return subItemCommand;
    }

}
