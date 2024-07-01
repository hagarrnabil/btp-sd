package com.example.btpsd.converters;

import com.example.btpsd.commands.ModelSpecificationsDetailsCommand;
import com.example.btpsd.commands.SubItemCommand;
import com.example.btpsd.model.*;
import io.micrometer.common.lang.Nullable;
import lombok.RequiredArgsConstructor;
import lombok.Synchronized;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;

@RequiredArgsConstructor
@Component
public class SubItemCommandToSubItem implements Converter<SubItemCommand, SubItem> {

    private final InvoiceCommandToInvoice invoiceConverter;

    private final MainItemCommandToMainItem mainItemConverter;

    @Synchronized
    @Nullable
    @Override
    public SubItem convert(SubItemCommand source) {

        if (source == null) {
            return null;
        }

        final SubItem subItem = new SubItem();
        subItem.setSubItemCode(source.getSubItemCode());
        subItem.setUnitOfMeasurementCode(source.getUnitOfMeasurementCode());
        subItem.setCurrencyCode(source.getCurrencyCode());
        subItem.setFormulaCode(source.getFormulaCode());
        subItem.setQuantity(source.getQuantity());
        subItem.setAmountPerUnit(source.getAmountPerUnit());
        subItem.setTotal(source.getAmountPerUnit() * source.getQuantity());
        if (source.getServiceNumberCode() != null) {
            ServiceNumber serviceNumber = new ServiceNumber();
            serviceNumber.setServiceNumberCode(source.getServiceNumberCode());
            subItem.setServiceNumber(serviceNumber);
            serviceNumber.addSubItem(subItem);
        }
        if (source.getInvoiceCommandList() != null && source.getInvoiceCommandList().size() > 0) {
            source.getInvoiceCommandList()
                    .forEach(invoiceCommand -> subItem.getInvoiceList().add(invoiceConverter.convert(invoiceCommand)));
        }
        if (source.getMainItemCommandList() != null && source.getMainItemCommandList().size() > 0) {
            source.getMainItemCommandList()
                    .forEach(mainItemCommand -> subItem.getMainItemList().add(mainItemConverter.convert(mainItemCommand)));
        }
        return subItem;
    }
}
