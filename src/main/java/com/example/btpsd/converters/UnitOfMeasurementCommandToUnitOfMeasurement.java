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

    private final ModelSpecDetailsCommandToModelSpecDetails modelSpecDetailsConverter;

    private final FormulaCommandToFormula formulaConverter;

    private final ServiceNumberCommandToServiceNumber serviceNumberConverter;

    @Synchronized
    @Nullable
    @Override
    public UnitOfMeasurement convert(UnitOfMeasurementCommand source) {

        if (source == null) {
            return null;
        }

        final UnitOfMeasurement unitOfMeasurement = new UnitOfMeasurement();
        unitOfMeasurement.setUnitOfMeasurementCode(source.getUnitOfMeasurementCode());
        unitOfMeasurement.setCode(source.getCode());
        unitOfMeasurement.setDescription(source.getDescription());
        if (source.getModelSpecificationsDetailsCommands() != null && source.getModelSpecificationsDetailsCommands().size() > 0) {
            source.getModelSpecificationsDetailsCommands()
                    .forEach(modelSpecificationsDetailsCommand -> unitOfMeasurement.getModelSpecificationsDetails().add(modelSpecDetailsConverter.convert(modelSpecificationsDetailsCommand)));
        }
        if (source.getFormulaCommands() != null && source.getFormulaCommands().size() > 0) {
            source.getFormulaCommands()
                    .forEach(formulaCommand -> unitOfMeasurement.getFormulas().add(formulaConverter.convert(formulaCommand)));
        }
        if (source.getServiceNumberCommands() != null && source.getServiceNumberCommands().size() > 0) {
            source.getServiceNumberCommands()
                    .forEach(serviceNumberCommand -> unitOfMeasurement.getServiceNumbers().add(serviceNumberConverter.convert(serviceNumberCommand)));
        }
        return unitOfMeasurement;
    }

}
