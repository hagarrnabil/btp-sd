package com.example.btpsd.converters;

import com.example.btpsd.commands.CurrencyCommand;
import com.example.btpsd.commands.LineTypeCommand;
import com.example.btpsd.model.Currency;
import com.example.btpsd.model.LineType;
import io.micrometer.common.lang.Nullable;
import lombok.RequiredArgsConstructor;
import lombok.Synchronized;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;
@RequiredArgsConstructor
@Component
public class LineTypeToLineTypeCommand implements Converter<LineType, LineTypeCommand> {

    private final ModelSpecDetailsToModelSpecDetailsCommand modelSpecDetailsConverter;

    @Synchronized
    @Nullable
    @Override
    public LineTypeCommand convert(LineType source) {

        if (source == null) {
            return null;
        }

        final LineTypeCommand lineTypeCommand = new LineTypeCommand();
        lineTypeCommand.setLineTypeCode(source.getLineTypeCode());
        lineTypeCommand.setCode(source.getCode());
        lineTypeCommand.setDescription(source.getDescription());
        return lineTypeCommand;
    }
}
