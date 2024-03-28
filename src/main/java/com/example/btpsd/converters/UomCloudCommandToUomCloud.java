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
public class UomCloudCommandToUomCloud implements Converter<UnitOfMeasurementCloudCommand, UnitOfMeasurementCloud> {

    @Synchronized
    @Nullable
    @Override
    public UnitOfMeasurementCloud convert(UnitOfMeasurementCloudCommand source) {

        if (source == null) {
            return null;
        }

        final UnitOfMeasurementCloud unitOfMeasurementCloud = new UnitOfMeasurementCloud();
        unitOfMeasurementCloud.setUnitOfMeasure(source.getUnitOfMeasure());
        unitOfMeasurementCloud.setUnitOfMeasureLongName(source.getUnitOfMeasureLongName());
        unitOfMeasurementCloud.setUnitOfMeasure_1(source.getUnitOfMeasure_1());
        unitOfMeasurementCloud.setUnitOfMeasureSAPCode(source.getUnitOfMeasureSAPCode());
        unitOfMeasurementCloud.setUnitOfMeasureName(source.getUnitOfMeasureName());
        unitOfMeasurementCloud.setUomCloud(source.getUomCloud());
        return unitOfMeasurementCloud;
    }

}
