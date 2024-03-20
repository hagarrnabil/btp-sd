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

    private final ModelSpecDetailsToModelSpecDetailsCommand modelSpecDetailsConverter;

    private final FormulaToFormulaCommand formulaConverter;


    @Synchronized
    @Nullable
    @Override
    public UnitOfMeasurementCommand convert(UnitOfMeasurement source) {

        if (source == null) {
            return null;
        }

        final UnitOfMeasurementCommand unitOfMeasurementCommand = new UnitOfMeasurementCommand();
        unitOfMeasurementCommand.setUnitOfMeasurementCode(source.getUnitOfMeasurementCode());
        unitOfMeasurementCommand.setCode(source.getCode());
        unitOfMeasurementCommand.setDescription(source.getDescription());
        if (source.getModelSpecificationsDetails() != null && source.getModelSpecificationsDetails().size() > 0){
            source.getModelSpecificationsDetails()
                    .forEach(modelSpecificationsDetails -> unitOfMeasurementCommand.getModelSpecificationsDetailsCommands().add(modelSpecDetailsConverter.convert(modelSpecificationsDetails)));
        }
        if (source.getFormulas() != null && source.getFormulas().size() > 0) {
            source.getFormulas()
                    .forEach(formula -> unitOfMeasurementCommand.getFormulaCommands().add(formulaConverter.convert(formula)));
        }
        return unitOfMeasurementCommand;
    }

}