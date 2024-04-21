package com.example.btpsd.converters;

import com.example.btpsd.commands.ServiceNumberCommand;
import com.example.btpsd.model.ServiceNumber;
import io.micrometer.common.lang.Nullable;
import lombok.RequiredArgsConstructor;
import lombok.Synchronized;
import org.apache.commons.math3.random.RandomDataGenerator;
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
        serviceNumberCommand.setNoServiceNumber(source.getNoServiceNumber());
        serviceNumberCommand.setSearchTerm(source.getSearchTerm());
        serviceNumberCommand.setDescription(source.getDescription());
        serviceNumberCommand.setConvertedNumber(source.getConvertedNumber());
        serviceNumberCommand.setNumberToBeConverted(source.getNumberToBeConverted());
        serviceNumberCommand.setDeletionIndicator(source.getDeletionIndicator());
        serviceNumberCommand.setShortTextChangeAllowed(source.getShortTextChangeAllowed());
        serviceNumberCommand.setMainItem(source.getMainItem());
        serviceNumberCommand.setLastChangeDate(source.getLastChangeDate().now());
        serviceNumberCommand.setServiceText(source.getServiceText());
        serviceNumberCommand.setBaseUnitOfMeasurement(source.getBaseUnitOfMeasurement());
        serviceNumberCommand.setToBeConvertedUnitOfMeasurement(source.getToBeConvertedUnitOfMeasurement());
        serviceNumberCommand.setDefaultUnitOfMeasurement(source.getDefaultUnitOfMeasurement());
        serviceNumberCommand.setServiceTypeCode(source.getServiceTypeCode());
        serviceNumberCommand.setMaterialGroupCode(source.getMaterialGroupCode());
        if (source.getModelSpecificationsDetails() != null && source.getModelSpecificationsDetails().size() > 0){
            source.getModelSpecificationsDetails()
                    .forEach(modelSpecificationsDetails -> serviceNumberCommand.getModelSpecificationsDetailsCommands().add(modelSpecDetailsConverter.convert(modelSpecificationsDetails)));
        }
        return serviceNumberCommand;
    }

}