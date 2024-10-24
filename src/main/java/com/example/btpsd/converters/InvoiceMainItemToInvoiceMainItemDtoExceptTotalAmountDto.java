package com.example.btpsd.converters;

import com.example.btpsd.dtos.InvoiceMainItemDtoExceptTotalAmountDto;
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
public class InvoiceMainItemToInvoiceMainItemDtoExceptTotalAmountDto 
        implements Converter<InvoiceMainItem, InvoiceMainItemDtoExceptTotalAmountDto> {
    private final InvoiceSubItemToInvoiceSubItemCommand subItemConverter;
    @Synchronized
    @Nullable
    @Override
    public InvoiceMainItemDtoExceptTotalAmountDto convert(InvoiceMainItem source) {

        if (source == null) {
            return null;
        }
        final InvoiceMainItemDtoExceptTotalAmountDto invoiceMainItemDtoExceptTotalAmountDto = new InvoiceMainItemDtoExceptTotalAmountDto();
        invoiceMainItemDtoExceptTotalAmountDto.setInvoiceMainItemCode(source.getInvoiceMainItemCode());
        invoiceMainItemDtoExceptTotalAmountDto.setUnitOfMeasurementCode(source.getUnitOfMeasurementCode());
        invoiceMainItemDtoExceptTotalAmountDto.setCurrencyCode(source.getCurrencyCode());
        invoiceMainItemDtoExceptTotalAmountDto.setFormulaCode(source.getFormulaCode());
        invoiceMainItemDtoExceptTotalAmountDto.setDescription(source.getDescription());
        invoiceMainItemDtoExceptTotalAmountDto.setQuantity(source.getQuantity());
        invoiceMainItemDtoExceptTotalAmountDto.setAmountPerUnit(source.getAmountPerUnit());
        invoiceMainItemDtoExceptTotalAmountDto.setProfitMargin(source.getProfitMargin());
        invoiceMainItemDtoExceptTotalAmountDto.setDoNotPrint(source.getDoNotPrint());
        if (source.getServiceNumber() != null) {
            invoiceMainItemDtoExceptTotalAmountDto.setServiceNumberCode(source.getServiceNumber().getServiceNumberCode());
        }
        if (source.getSubItemList() != null && source.getSubItemList().size() > 0) {
            source.getSubItemList()
                    .forEach(subItem -> invoiceMainItemDtoExceptTotalAmountDto.getSubItems().add(subItemConverter.convert(subItem)));
        }
        invoiceMainItemDtoExceptTotalAmountDto.setAmountPerUnit(new BigDecimal(invoiceMainItemDtoExceptTotalAmountDto.getAmountPerUnit()).setScale(2, RoundingMode.HALF_UP).doubleValue());

        if(invoiceMainItemDtoExceptTotalAmountDto.getProfitMargin() != null){
            invoiceMainItemDtoExceptTotalAmountDto.setAmountPerUnitWithProfit(((invoiceMainItemDtoExceptTotalAmountDto.getProfitMargin() / 100) * invoiceMainItemDtoExceptTotalAmountDto.getAmountPerUnit()) + invoiceMainItemDtoExceptTotalAmountDto.getAmountPerUnit());
            invoiceMainItemDtoExceptTotalAmountDto.setAmountPerUnit(new BigDecimal(invoiceMainItemDtoExceptTotalAmountDto.getAmountPerUnit()).setScale(2, RoundingMode.HALF_UP).doubleValue());
        }
        else {
            invoiceMainItemDtoExceptTotalAmountDto.setAmountPerUnitWithProfit(null);
        }
        return invoiceMainItemDtoExceptTotalAmountDto;
    }
}
