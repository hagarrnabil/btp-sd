package com.example.btpsd.converters;

import com.example.btpsd.commands.MainItemCommand;
import com.example.btpsd.commands.ServiceNumberCommand;
import com.example.btpsd.model.MainItem;
import com.example.btpsd.model.ServiceNumber;
import io.micrometer.common.lang.Nullable;
import lombok.RequiredArgsConstructor;
import lombok.Synchronized;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;

@RequiredArgsConstructor
@Component
public class MainItemToMainItemCommand implements Converter<MainItem, MainItemCommand> {

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

        return mainItemCommand;
    }

}
