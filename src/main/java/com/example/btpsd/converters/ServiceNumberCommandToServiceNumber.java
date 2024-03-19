package com.example.btpsd.converters;

import com.example.btpsd.commands.ServiceNumberCommand;
import com.example.btpsd.model.*;
import io.micrometer.common.lang.Nullable;
import lombok.RequiredArgsConstructor;
import lombok.Synchronized;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;

@RequiredArgsConstructor
@Component
public class ServiceNumberCommandToServiceNumber implements Converter<ServiceNumberCommand, ServiceNumber> {

    private final ModelSpecDetailsCommandToModelSpecDetails modelSpecDetailsConverter;

//    private final UnitOfMeasurementCommandToUnitOfMeasurement unitOfMeasurementConverter;

    @Synchronized
    @Nullable
    @Override
    public ServiceNumber convert(ServiceNumberCommand source) {

        if (source == null) {
            return null;
        }

        final ServiceNumber serviceNumber = new ServiceNumber();
        serviceNumber.setServiceNumberCode(source.getServiceNumberCode());
        serviceNumber.setCode(source.getCode());
        serviceNumber.setDescription(source.getDescription());
        serviceNumber.setConvertedNumber(source.getConvertedNumber());
        serviceNumber.setNumberToBeConverted(source.getNumberToBeConverted());
        serviceNumber.setDeletionIndicator(source.getDeletionIndicator());
        serviceNumber.setShortTextChangeAllowed(source.getShortTextChangeAllowed());
        serviceNumber.setMainItem(source.getMainItem());
        serviceNumber.setLastChangeDate(source.getLastChangeDate());
        serviceNumber.setServiceText(source.getServiceText());
        if (source.getFormulaCode() != null) {
            Formula formula = new Formula();
            formula.setFormulaCode(source.getFormulaCode());
            serviceNumber.setFormula(formula);
            formula.addServiceNumbers(serviceNumber);
        }
        if (source.getServiceTypeCode() != null) {
            ServiceType serviceType = new ServiceType();
            serviceType.setServiceTypeCode(source.getServiceTypeCode());
            serviceNumber.setServiceType(serviceType);
            serviceType.addServiceNumbers(serviceNumber);
        }
        if (source.getBaseUnitOfMeasurement() != null) {
            UnitOfMeasurement unitOfMeasurement = new UnitOfMeasurement();
            serviceNumber.setBaseUnitOfMeasurement(source.getBaseUnitOfMeasurement());
            serviceNumber.setBaseUnitOfMeasurement(unitOfMeasurement);
            unitOfMeasurement.addBaseServiceNumbers(serviceNumber);
        }
        if (source.getToBeConvertedUnitOfMeasurement() != null) {
            UnitOfMeasurement unitOfMeasurement = new UnitOfMeasurement();
            serviceNumber.setToBeConvertedUnitOfMeasurement(source.getToBeConvertedUnitOfMeasurement());
            serviceNumber.setToBeConvertedUnitOfMeasurement(unitOfMeasurement);
            unitOfMeasurement.addToBeConvertedServiceNumbers(serviceNumber);
        }
        if (source.getConvertedUnitOfMeasurement() != null) {
            UnitOfMeasurement unitOfMeasurement = new UnitOfMeasurement();
            serviceNumber.setConvertedUnitOfMeasurement(source.getConvertedUnitOfMeasurement());
            serviceNumber.setConvertedUnitOfMeasurement(unitOfMeasurement);
            unitOfMeasurement.addConvertedServiceNumbers(serviceNumber);
        }
        if (source.getMaterialGroupCode() != null) {
            MaterialGroup materialGroup= new MaterialGroup();
            materialGroup.setMaterialGroupCode(source.getMaterialGroupCode());
            serviceNumber.setMaterialGroup(materialGroup);
            materialGroup.addServiceNumbers(serviceNumber);
        }
        if (source.getModelSpecificationsDetailsCommands() != null && source.getModelSpecificationsDetailsCommands().size() > 0) {
            source.getModelSpecificationsDetailsCommands()
                    .forEach(modelSpecificationsDetailsCommand -> serviceNumber.getModelSpecificationsDetails().add(modelSpecDetailsConverter.convert(modelSpecificationsDetailsCommand)));
        }
        return serviceNumber;
    }

}