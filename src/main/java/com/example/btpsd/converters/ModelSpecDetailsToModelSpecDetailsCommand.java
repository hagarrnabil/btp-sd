package com.example.btpsd.converters;

import com.example.btpsd.commands.ModelSpecificationsCommand;
import com.example.btpsd.commands.ModelSpecificationsDetailsCommand;
import com.example.btpsd.model.ModelSpecifications;
import com.example.btpsd.model.ModelSpecificationsDetails;
import io.micrometer.common.lang.Nullable;
import lombok.RequiredArgsConstructor;
import lombok.Synchronized;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;

@RequiredArgsConstructor
@Component
public class ModelSpecDetailsToModelSpecDetailsCommand implements Converter<ModelSpecificationsDetails, ModelSpecificationsDetailsCommand> {

    private final ModelSpecificationsToModelSpecificationsCommand modelSpecConverter;

    @Synchronized
    @Nullable
    @Override
    public ModelSpecificationsDetailsCommand convert(ModelSpecificationsDetails source) {

        if (source == null) {
            return null;
        }

        final ModelSpecificationsDetailsCommand modelSpecificationsDetailsCommand = new ModelSpecificationsDetailsCommand();
        modelSpecificationsDetailsCommand.setModelSpecDetails(source.getModelSpecDetails());
        if (source.getPersonnelNumber() != null) {
            modelSpecificationsDetailsCommand.setPersonnelNumber(source.getPersonnelNumber().getPersonnelNumber());
        }
        if (source.getCurrency() != null) {
            modelSpecificationsDetailsCommand.setCurrency(source.getCurrency().getCurrency());
        }
        if (source.getServiceType() != null) {
            modelSpecificationsDetailsCommand.setServiceType(source.getServiceType().getServiceType());
        }
        if (source.getMaterialGroup() != null) {
            modelSpecificationsDetailsCommand.setMaterialGroup(source.getMaterialGroup().getMaterialGroup());
        }
        if (source.getUnitOfMeasurement() != null) {
            modelSpecificationsDetailsCommand.setUnitOfMeasurement(source.getUnitOfMeasurement().getUnitOfMeasurement());
        }
        if (source.getServiceNumber() != null) {
            modelSpecificationsDetailsCommand.setServiceNumber(source.getServiceNumber().getServiceNumber());
        }

        modelSpecificationsDetailsCommand.setSelectionCheckBox(source.getSelectionCheckBox());
        modelSpecificationsDetailsCommand.setLineIndex(source.getLineIndex());
        modelSpecificationsDetailsCommand.setDeletionIndicator(source.getDeletionIndicator());
        modelSpecificationsDetailsCommand.setShortText(source.getShortText());
        modelSpecificationsDetailsCommand.setQuantity(source.getQuantity());
        modelSpecificationsDetailsCommand.setGrossPrice(modelSpecificationsDetailsCommand.getGrossPrice());
        modelSpecificationsDetailsCommand.setOverFulfilmentPercentage(modelSpecificationsDetailsCommand.getOverFulfilmentPercentage());
        modelSpecificationsDetailsCommand.setPriceChangedAllowed(modelSpecificationsDetailsCommand.getPriceChangedAllowed());
        modelSpecificationsDetailsCommand.setUnlimitedOverFulfillment(modelSpecificationsDetailsCommand.getUnlimitedOverFulfillment());
        modelSpecificationsDetailsCommand.setPricePerUnitOfMeasurement(modelSpecificationsDetailsCommand.getPricePerUnitOfMeasurement());
        modelSpecificationsDetailsCommand.setExternalServiceNumber(modelSpecificationsDetailsCommand.getExternalServiceNumber());
        modelSpecificationsDetailsCommand.setNetValue(modelSpecificationsDetailsCommand.getNetValue());
        modelSpecificationsDetailsCommand.setServiceText(modelSpecificationsDetailsCommand.getServiceText());
        modelSpecificationsDetailsCommand.setLineText(modelSpecificationsDetailsCommand.getLineText());
        modelSpecificationsDetailsCommand.setFormula(modelSpecificationsDetailsCommand.getFormula());
        modelSpecificationsDetailsCommand.setLineNumber(modelSpecificationsDetailsCommand.getLineNumber());
        modelSpecificationsDetailsCommand.setAlternatives(modelSpecificationsDetailsCommand.getAlternatives());
        modelSpecificationsDetailsCommand.setBiddersLine(modelSpecificationsDetailsCommand.getBiddersLine());
        modelSpecificationsDetailsCommand.setSupplementaryLine(modelSpecificationsDetailsCommand.getSupplementaryLine());
        modelSpecificationsDetailsCommand.setLotSizeForCostingIsOne(modelSpecificationsDetailsCommand.getLotSizeForCostingIsOne());

        if (source.getModelSpecifications() != null && source.getModelSpecifications().size() > 0) {
            source.getModelSpecifications()
                    .forEach(modelSpecifications -> modelSpecificationsDetailsCommand.getModelSpecificationsCommands().add(modelSpecConverter.convert(modelSpecifications)));
        }

        return modelSpecificationsDetailsCommand;

    }

}
