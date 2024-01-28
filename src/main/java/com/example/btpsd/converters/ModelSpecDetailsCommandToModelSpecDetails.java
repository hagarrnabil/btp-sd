package com.example.btpsd.converters;

import com.example.btpsd.commands.ModelSpecificationsDetailsCommand;
import com.example.btpsd.model.*;
import io.micrometer.common.lang.Nullable;
import lombok.RequiredArgsConstructor;
import lombok.Synchronized;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;

@RequiredArgsConstructor
@Component
public class ModelSpecDetailsCommandToModelSpecDetails implements Converter<ModelSpecificationsDetailsCommand, ModelSpecificationsDetails> {

    private final ModelSpecificationsCommandToModelSpecifications modelSpecConverter;

    @Synchronized
    @Nullable
    @Override
    public ModelSpecificationsDetails convert(ModelSpecificationsDetailsCommand source) {

        if (source == null) {
            return null;
        }

        final ModelSpecificationsDetails modelSpecificationsDetails = new ModelSpecificationsDetails();
        modelSpecificationsDetails.setModelSpecDetailsCode(source.getModelSpecDetailsCode());
        if (source.getCurrencyCode() != null) {
            Currency currency = new Currency();
            currency.setCurrencyCode(source.getCurrencyCode());
            modelSpecificationsDetails.setCurrency(currency);
            currency.addModelSpecDetails(modelSpecificationsDetails);
        }
        if (source.getPersonnelNumberCode() != null) {
            PersonnelNumber personnelNumber = new PersonnelNumber();
            personnelNumber.setPersonnelNumberCode(source.getPersonnelNumberCode());
            modelSpecificationsDetails.setPersonnelNumber(personnelNumber);
            personnelNumber.addModelSpecDetails(modelSpecificationsDetails);
        }
        if (source.getServiceTypeCode() != null) {
            ServiceType serviceType = new ServiceType();
            serviceType.setServiceTypeCode(source.getServiceTypeCode());
            modelSpecificationsDetails.setServiceType(serviceType);
            serviceType.addModelSpecDetails(modelSpecificationsDetails);
        }
        if (source.getMaterialGroupCode() != null) {
            MaterialGroup materialGroup = new MaterialGroup();
            materialGroup.setMaterialGroupCode(source.getMaterialGroupCode());
            modelSpecificationsDetails.setMaterialGroup(materialGroup);
            materialGroup.addModelSpecDetails(modelSpecificationsDetails);
        }
        if (source.getUnitOfMeasurementCode() != null) {
            UnitOfMeasurement unitOfMeasurement = new UnitOfMeasurement();
            unitOfMeasurement.setUnitOfMeasurementCode(source.getUnitOfMeasurementCode());
            modelSpecificationsDetails.setUnitOfMeasurement(unitOfMeasurement);
            unitOfMeasurement.addModelSpecDetails(modelSpecificationsDetails);
        }
        if (source.getServiceNumberCode() != null) {
            ServiceNumber serviceNumber = new ServiceNumber();
            serviceNumber.setServiceNumberCode(source.getServiceNumberCode());
            modelSpecificationsDetails.setServiceNumber(serviceNumber);
            serviceNumber.addModelSpecDetails(modelSpecificationsDetails);
        }

        modelSpecificationsDetails.setSelectionCheckBox(source.getSelectionCheckBox());
        modelSpecificationsDetails.setLineIndex(source.getLineIndex());
        modelSpecificationsDetails.setDeletionIndicator(source.getDeletionIndicator());
        modelSpecificationsDetails.setShortText(source.getShortText());
        modelSpecificationsDetails.setQuantity(source.getQuantity());
        modelSpecificationsDetails.setGrossPrice(modelSpecificationsDetails.getGrossPrice());
        modelSpecificationsDetails.setOverFulfilmentPercentage(modelSpecificationsDetails.getOverFulfilmentPercentage());
        modelSpecificationsDetails.setPriceChangedAllowed(modelSpecificationsDetails.getPriceChangedAllowed());
        modelSpecificationsDetails.setUnlimitedOverFulfillment(modelSpecificationsDetails.getUnlimitedOverFulfillment());
        modelSpecificationsDetails.setPricePerUnitOfMeasurement(modelSpecificationsDetails.getPricePerUnitOfMeasurement());
        modelSpecificationsDetails.setExternalServiceNumber(modelSpecificationsDetails.getExternalServiceNumber());
        modelSpecificationsDetails.setNetValue(modelSpecificationsDetails.getNetValue());
        modelSpecificationsDetails.setServiceText(modelSpecificationsDetails.getServiceText());
        modelSpecificationsDetails.setLineText(modelSpecificationsDetails.getLineText());
        modelSpecificationsDetails.setFormula(modelSpecificationsDetails.getFormula());
        modelSpecificationsDetails.setLineNumber(modelSpecificationsDetails.getLineNumber());
        modelSpecificationsDetails.setAlternatives(modelSpecificationsDetails.getAlternatives());
        modelSpecificationsDetails.setBiddersLine(modelSpecificationsDetails.getBiddersLine());
        modelSpecificationsDetails.setSupplementaryLine(modelSpecificationsDetails.getSupplementaryLine());
        modelSpecificationsDetails.setLotSizeForCostingIsOne(modelSpecificationsDetails.getLotSizeForCostingIsOne());

        if (source.getModelSpecificationsCommands() != null && source.getModelSpecificationsCommands().size() > 0) {
        source.getModelSpecificationsCommands()
                .forEach(modelSpecificationsCommand -> modelSpecificationsDetails.getModelSpecifications().add(modelSpecConverter.convert(modelSpecificationsCommand)));
    }

        return modelSpecificationsDetails;

    }
}
