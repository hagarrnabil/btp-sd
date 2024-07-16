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

    private final SubItemToSubItemCommand subItemConverter;

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
        mainItemCommand.setDescription(source.getDescription());
        mainItemCommand.setQuantity(source.getQuantity());
        mainItemCommand.setAmountPerUnit(source.getAmountPerUnit());
        mainItemCommand.setTotal(source.getQuantity() * source.getAmountPerUnit());
        mainItemCommand.setProfitMargin(source.getProfitMargin());
        mainItemCommand.setTotalWithProfit( (mainItemCommand.getProfitMargin()/100) * mainItemCommand.getTotal() );
        mainItemCommand.setDoNotPrint(source.getDoNotPrint());
        mainItemCommand.setAmountPerUnitWithProfit( (mainItemCommand.getProfitMargin()/100) * mainItemCommand.getAmountPerUnit() );
        if (source.getServiceNumber() != null) {
            mainItemCommand.setServiceNumberCode(source.getServiceNumber().getServiceNumberCode());
        }
        if (source.getSubItemList() != null && source.getSubItemList().size() > 0) {
            source.getSubItemList()
                    .forEach(subItem -> mainItemCommand.getSubItems().add(subItemConverter.convert(subItem)));
        }

        return mainItemCommand;
    }

}
