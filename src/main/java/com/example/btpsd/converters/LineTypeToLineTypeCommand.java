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
        lineTypeCommand.setHZ(source.getHZ());
        lineTypeCommand.setFZ(source.getFZ());
        lineTypeCommand.setEZ(source.getEZ());
        lineTypeCommand.setNZ(source.getNZ());
        lineTypeCommand.setPZ(source.getPZ());
        lineTypeCommand.setIZ(source.getIZ());
        lineTypeCommand.setStandardLine(source.getStandardLine());
        lineTypeCommand.setBlanketLine(source.getBlanketLine());
        lineTypeCommand.setContingencyLine(source.getContingencyLine());
        lineTypeCommand.setInformatoryLine(source.getInformatoryLine());
        lineTypeCommand.setInternalLine(source.getInternalLine());
        lineTypeCommand.setAtpQuantity(source.getAtpQuantity());
        if (source.getModelSpecificationsDetails() != null && source.getModelSpecificationsDetails().size() > 0){
            source.getModelSpecificationsDetails()
                    .forEach(modelSpecificationsDetails -> lineTypeCommand.getModelSpecificationsDetailsCommands().add(modelSpecDetailsConverter.convert(modelSpecificationsDetails)));
        }
        return lineTypeCommand;
    }
}
