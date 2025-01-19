package com.example.btpsd.converters;

import com.example.btpsd.commands.UnitOfMeasurementCommand;
import com.example.btpsd.model.UnitOfMeasurement;
import io.micrometer.common.lang.Nullable;
import lombok.RequiredArgsConstructor;
import lombok.Synchronized;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;

@RequiredArgsConstructor
@Component
public class UnitOfMeasurementToUnitOfMeasurementCommand implements Converter<UnitOfMeasurement, UnitOfMeasurementCommand> {


    @Synchronized
    @Nullable
    @Override
    public UnitOfMeasurementCommand convert(UnitOfMeasurement source) {

        if (source == null) {
            return null;
        }

        final UnitOfMeasurementCommand unitOfMeasurementCommand = new UnitOfMeasurementCommand();
        unitOfMeasurementCommand.setUnit(source.getUnit());
        unitOfMeasurementCommand.setDescription(source.getDescription());
        return unitOfMeasurementCommand;
    }

}