package com.example.btpsd.converters;

import com.example.btpsd.commands.ServiceNumberCommand;
import com.example.btpsd.model.ServiceNumber;
import io.micrometer.common.lang.Nullable;
import lombok.RequiredArgsConstructor;
import lombok.Synchronized;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;

@RequiredArgsConstructor
@Component
public class ServiceNumberToServiceNumberCommand implements Converter<ServiceNumber, ServiceNumberCommand> {

    private final ModelSpecDetailsToModelSpecDetailsCommand modelSpecDetailsConverter;


    @Synchronized
    @Nullable
    @Override
    public ServiceNumberCommand convert(ServiceNumber source) {

        if (source == null) {
            return null;
        }

        final ServiceNumberCommand serviceNumberCommand = new ServiceNumberCommand();
        serviceNumberCommand.setServiceNumberCode(source.getServiceNumberCode());
        serviceNumberCommand.setCode(source.getCode());
        serviceNumberCommand.setDescription(source.getDescription());
        serviceNumberCommand.setConvertedNumber(source.getConvertedNumber());
        serviceNumberCommand.setNumberToBeConverted(source.getNumberToBeConverted());
        serviceNumberCommand.setDeletionIndicator(source.getDeletionIndicator());
        serviceNumberCommand.setShortTextChangeAllowed(source.getShortTextChangeAllowed());
        serviceNumberCommand.setMainItem(source.getMainItem());
        serviceNumberCommand.setLastChangeDate(source.getLastChangeDate());
        serviceNumberCommand.setServiceText(source.getServiceText());
        serviceNumberCommand.setBaseUnitOfMeasurement(source.getBaseUnitOfMeasurement());
        serviceNumberCommand.setToBeConvertedUnitOfMeasurement(source.getToBeConvertedUnitOfMeasurement());
        serviceNumberCommand.setDefaultUnitOfMeasurement(source.getDefaultUnitOfMeasurement());
        serviceNumberCommand.setConversionRule(source.getConversionRule());
        if (source.getFormula() != null) {
            serviceNumberCommand.setFormulaCode(source.getFormula().getFormulaCode());
        }
        if (source.getMaterialGroup() != null) {
            serviceNumberCommand.setMaterialGroupCode(source.getMaterialGroup().getMaterialGroupCode());
        }
        if (source.getServiceType() != null) {
            serviceNumberCommand.setServiceTypeCode(source.getServiceType().getServiceTypeCode());
        }
        if (source.getModelSpecificationsDetails() != null && source.getModelSpecificationsDetails().size() > 0){
            source.getModelSpecificationsDetails()
                    .forEach(modelSpecificationsDetails -> serviceNumberCommand.getModelSpecificationsDetailsCommands().add(modelSpecDetailsConverter.convert(modelSpecificationsDetails)));
        }
        return serviceNumberCommand;
    }

}