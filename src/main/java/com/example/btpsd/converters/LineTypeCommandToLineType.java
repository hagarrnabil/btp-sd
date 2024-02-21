package com.example.btpsd.converters;

import com.example.btpsd.commands.LineTypeCommand;
import com.example.btpsd.model.LineType;
import io.micrometer.common.lang.Nullable;
import lombok.RequiredArgsConstructor;
import lombok.Synchronized;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;
@RequiredArgsConstructor
@Component
public class LineTypeCommandToLineType implements Converter<LineTypeCommand, LineType> {

    private final ModelSpecDetailsCommandToModelSpecDetails modelSpecDetailsConverter;

    @Synchronized
    @Nullable
    @Override
    public LineType convert(LineTypeCommand source) {

        if (source == null) {
            return null;
        }

        final LineType lineType = new LineType();
        lineType.setLineTypeCode(source.getLineTypeCode());
        lineType.setCode(source.getCode());
        lineType.setHZ(source.getHZ());
        lineType.setFZ(source.getFZ());
        lineType.setEZ(source.getEZ());
        lineType.setNZ(source.getNZ());
        lineType.setPZ(source.getPZ());
        lineType.setIZ(source.getIZ());
        lineType.setStandardLine(source.getStandardLine());
        lineType.setBlanketLine(source.getBlanketLine());
        lineType.setContingencyLine(source.getContingencyLine());
        lineType.setInformatoryLine(source.getInformatoryLine());
        lineType.setInternalLine(source.getInternalLine());
        lineType.setAtpQuantity(source.getAtpQuantity());
        if (source.getModelSpecificationsDetailsCommands() != null && source.getModelSpecificationsDetailsCommands().size() > 0) {
            source.getModelSpecificationsDetailsCommands()
                    .forEach(modelSpecificationsDetailsCommand -> lineType.getModelSpecificationsDetails().add(modelSpecDetailsConverter.convert(modelSpecificationsDetailsCommand)));
        }
        return lineType;
    }
}
