package com.example.btpsd.converters;

import com.example.btpsd.commands.MaterialGroupCommand;
import com.example.btpsd.commands.UnitOfMeasurementCommand;
import com.example.btpsd.model.MaterialGroup;
import com.example.btpsd.model.UnitOfMeasurement;
import io.micrometer.common.lang.Nullable;
import lombok.RequiredArgsConstructor;
import lombok.Synchronized;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;

@RequiredArgsConstructor
@Component
public class UnitOfMeasurementToUnitOfMeasurementCommand implements Converter<UnitOfMeasurement, UnitOfMeasurementCommand> {

    private final ModelSpecDetailsToModelSpecDetailsCommand modelSpecDetailsConverter;

    @Synchronized
    @Nullable
    @Override
    public UnitOfMeasurementCommand convert(UnitOfMeasurement source) {

        if (source == null) {
            return null;
        }

        final UnitOfMeasurementCommand unitOfMeasurementCommand = new UnitOfMeasurementCommand();
        unitOfMeasurementCommand.setUnitOfMeasurement(source.getUnitOfMeasurement());
        unitOfMeasurementCommand.setCode(source.getCode());
        unitOfMeasurementCommand.setDescription(source.getDescription());
        if (source.getModelSpecificationsDetails() != null && source.getModelSpecificationsDetails().size() > 0){
            source.getModelSpecificationsDetails()
                    .forEach(modelSpecificationsDetails -> unitOfMeasurementCommand.getModelSpecificationsDetailsCommands().add(modelSpecDetailsConverter.convert(modelSpecificationsDetails)));
        }
        return unitOfMeasurementCommand;
    }

}
