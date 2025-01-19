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
public class UnitOfMeasurementCommandToUnitOfMeasurement implements Converter<UnitOfMeasurementCommand, UnitOfMeasurement> {



    @Synchronized
    @Nullable
    @Override
    public UnitOfMeasurement convert(UnitOfMeasurementCommand source) {

        if (source == null) {
            return null;
        }

        final UnitOfMeasurement unitOfMeasurement = new UnitOfMeasurement();
        unitOfMeasurement.setUnit(source.getUnit());
        unitOfMeasurement.setDescription(source.getDescription());
        return unitOfMeasurement;
    }

}