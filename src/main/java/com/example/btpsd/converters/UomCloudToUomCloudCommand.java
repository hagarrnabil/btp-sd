package com.example.btpsd.converters;

import com.example.btpsd.commands.UnitOfMeasurementCloudCommand;
import com.example.btpsd.model.UnitOfMeasurementCloud;
import io.micrometer.common.lang.Nullable;
import lombok.RequiredArgsConstructor;
import lombok.Synchronized;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;

@RequiredArgsConstructor
@Component
public class UomCloudToUomCloudCommand implements Converter<UnitOfMeasurementCloud, UnitOfMeasurementCloudCommand> {

    @Synchronized
    @Nullable
    @Override
    public UnitOfMeasurementCloudCommand convert(UnitOfMeasurementCloud source) {

        if (source == null) {
            return null;
        }

        final UnitOfMeasurementCloudCommand unitOfMeasurementCloudCommand = new UnitOfMeasurementCloudCommand();
        unitOfMeasurementCloudCommand.setUnitOfMeasure(source.getUnitOfMeasure());
        unitOfMeasurementCloudCommand.setUnitOfMeasureLongName(source.getUnitOfMeasureLongName());
        unitOfMeasurementCloudCommand.setUnitOfMeasure_1(source.getUnitOfMeasure_1());
        unitOfMeasurementCloudCommand.setUnitOfMeasureSAPCode(source.getUnitOfMeasureSAPCode());
        unitOfMeasurementCloudCommand.setUnitOfMeasureName(source.getUnitOfMeasureName());
        unitOfMeasurementCloudCommand.setUomCloud(source.getUomCloud());
        return unitOfMeasurementCloudCommand;
    }

}
