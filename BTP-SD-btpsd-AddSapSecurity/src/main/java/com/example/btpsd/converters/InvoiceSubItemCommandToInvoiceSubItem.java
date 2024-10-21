package com.example.btpsd.converters;

import com.example.btpsd.commands.InvoiceSubItemCommand;
import com.example.btpsd.model.*;
import io.micrometer.common.lang.Nullable;
import lombok.RequiredArgsConstructor;
import lombok.Synchronized;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;
import java.math.RoundingMode;

@RequiredArgsConstructor
@Component
public class InvoiceSubItemCommandToInvoiceSubItem implements Converter<InvoiceSubItemCommand, InvoiceSubItem> {


    @Synchronized
    @Nullable
    @Override
    public InvoiceSubItem convert(InvoiceSubItemCommand source) {

        if (source == null) {
            return null;
        }

        final InvoiceSubItem subItem = new InvoiceSubItem();
        subItem.setInvoiceSubItemCode(source.getInvoiceSubItemCode());
        subItem.setUnitOfMeasurementCode(source.getUnitOfMeasurementCode());
        subItem.setCurrencyCode(source.getCurrencyCode());
        subItem.setFormulaCode(source.getFormulaCode());
        subItem.setDescription(source.getDescription());
        subItem.setQuantity(source.getQuantity());
        subItem.setAmountPerUnit(source.getAmountPerUnit());
        subItem.setTotal(source.getAmountPerUnit() * source.getQuantity());
        if (source.getServiceNumberCode() != null) {
            ServiceNumber serviceNumber = new ServiceNumber();
            serviceNumber.setServiceNumberCode(source.getServiceNumberCode());
            subItem.setServiceNumber(serviceNumber);
            serviceNumber.addSubItem(subItem);
        }
        if (source.getInvoiceMainItemCode() != null) {
            InvoiceMainItem mainItem = new InvoiceMainItem();
            mainItem.setInvoiceMainItemCode(source.getInvoiceMainItemCode());
            subItem.setMainItem(mainItem);
            mainItem.addSubItem(subItem);  // Ensure bi-directional relationship
        }
        subItem.setTotal(new BigDecimal(subItem.getTotal()).setScale(2, RoundingMode.HALF_UP).doubleValue());
        subItem.setAmountPerUnit(new BigDecimal(subItem.getAmountPerUnit()).setScale(2, RoundingMode.HALF_UP).doubleValue());
        return subItem;
    }
}
