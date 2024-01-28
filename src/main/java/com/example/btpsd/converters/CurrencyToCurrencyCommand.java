package com.example.btpsd.converters;

import com.example.btpsd.commands.CurrencyCommand;
import com.example.btpsd.model.Currency;
import io.micrometer.common.lang.Nullable;
import lombok.RequiredArgsConstructor;
import lombok.Synchronized;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;

@RequiredArgsConstructor
@Component
public class CurrencyToCurrencyCommand implements Converter<Currency, CurrencyCommand> {

    private final ModelSpecificationsToModelSpecificationsCommand modelSpecConverter;
    private final ModelSpecDetailsToModelSpecDetailsCommand modelSpecDetailsConverter;

    @Synchronized
    @Nullable
    @Override
    public CurrencyCommand convert(Currency source) {

        if (source == null) {
            return null;
        }

        final CurrencyCommand currencyCommand = new CurrencyCommand();
        currencyCommand.setCurrencyCode(source.getCurrencyCode());
        currencyCommand.setCode(source.getCode());
        currencyCommand.setDescription(source.getDescription());
        if (source.getModelSpecifications() != null && source.getModelSpecifications().size() > 0){
            source.getModelSpecifications()
                    .forEach(modelSpecifications -> currencyCommand.getModelSpecificationsCommands().add(modelSpecConverter.convert(modelSpecifications)));
        }
        if (source.getModelSpecificationsDetails() != null && source.getModelSpecificationsDetails().size() > 0){
            source.getModelSpecificationsDetails()
                    .forEach(modelSpecificationsDetails -> currencyCommand.getModelSpecificationsDetailsCommands().add(modelSpecDetailsConverter.convert(modelSpecificationsDetails)));
        }
        return currencyCommand;
    }
}
