package com.example.btpsd.converters;

import com.example.btpsd.commands.ModelSpecificationsDetailsCommand;
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
        modelSpecificationsDetailsCommand.setModelSpecDetailsCode(source.getModelSpecDetailsCode());
        if (source.getPersonnelNumber() != null) {
            modelSpecificationsDetailsCommand.setPersonnelNumberCode(source.getPersonnelNumber().getPersonnelNumberCode());
        }
        if (source.getCurrency() != null) {
            modelSpecificationsDetailsCommand.setCurrencyCode(source.getCurrency().getCurrencyCode());
        }
        if (source.getFormula() != null) {
            modelSpecificationsDetailsCommand.setFormulaCode(source.getFormula().getFormulaCode());
        }
        if (source.getServiceType() != null) {
            modelSpecificationsDetailsCommand.setServiceTypeCode(source.getServiceType().getServiceTypeCode());
        }
        if (source.getMaterialGroup() != null) {
            modelSpecificationsDetailsCommand.setMaterialGroupCode(source.getMaterialGroup().getMaterialGroupCode());
        }
        if (source.getUnitOfMeasurement() != null) {
            modelSpecificationsDetailsCommand.setUnitOfMeasurementCode(source.getUnitOfMeasurement().getUnitOfMeasurementCode());
        }
        if (source.getServiceNumber() != null) {
            modelSpecificationsDetailsCommand.setServiceNumberCode(source.getServiceNumber().getServiceNumberCode());
        }
        modelSpecificationsDetailsCommand.setSelectionCheckBox(source.getSelectionCheckBox());
        modelSpecificationsDetailsCommand.setLineIndex(source.getLineIndex());
        modelSpecificationsDetailsCommand.setDeletionIndicator(source.getDeletionIndicator());
        modelSpecificationsDetailsCommand.setShortText(source.getShortText());
        modelSpecificationsDetailsCommand.setQuantity(source.getQuantity());
        modelSpecificationsDetailsCommand.setGrossPrice(source.getGrossPrice());
        modelSpecificationsDetailsCommand.setOverFulfilmentPercentage(source.getOverFulfilmentPercentage());
        modelSpecificationsDetailsCommand.setPriceChangedAllowed(source.getPriceChangedAllowed());
        modelSpecificationsDetailsCommand.setUnlimitedOverFulfillment(source.getUnlimitedOverFulfillment());
        modelSpecificationsDetailsCommand.setPricePerUnitOfMeasurement(source.getPricePerUnitOfMeasurement());
        modelSpecificationsDetailsCommand.setExternalServiceNumber(source.getExternalServiceNumber());
        modelSpecificationsDetailsCommand.setNetValue(source.getNetValue());
        modelSpecificationsDetailsCommand.setServiceText(source.getServiceText());
        modelSpecificationsDetailsCommand.setLineText(source.getLineText());
//        modelSpecificationsDetailsCommand.setFormula(source.getFormula());
        modelSpecificationsDetailsCommand.setLineNumber(source.getLineNumber());
        modelSpecificationsDetailsCommand.setAlternatives(source.getAlternatives());
        modelSpecificationsDetailsCommand.setBiddersLine(source.getBiddersLine());
        modelSpecificationsDetailsCommand.setSupplementaryLine(source.getSupplementaryLine());
        modelSpecificationsDetailsCommand.setLotSizeForCostingIsOne(source.getLotSizeForCostingIsOne());

        if (source.getModelSpecifications() != null && source.getModelSpecifications().size() > 0) {
            source.getModelSpecifications()
                    .forEach(modelSpecifications -> modelSpecificationsDetailsCommand.getModelSpecificationsCommands().add(modelSpecConverter.convert(modelSpecifications)));
        }

        return modelSpecificationsDetailsCommand;

    }

}
