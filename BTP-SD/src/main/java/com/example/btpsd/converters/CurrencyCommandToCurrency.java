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
public class CurrencyCommandToCurrency implements Converter<CurrencyCommand, Currency> {


    @Synchronized
    @Nullable
    @Override
    public Currency convert(CurrencyCommand source) {

        if (source == null) {
            return null;
        }

        final Currency currency = new Currency();
        currency.setCurrencyCode(source.getCurrencyCode());
        currency.setCode(source.getCode());
        currency.setDescription(source.getDescription());
        return currency;
    }
}
