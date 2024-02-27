package com.example.btpsd.converters;

import com.example.btpsd.commands.ModelSpecificationsDetailsCommand;
import com.example.btpsd.model.Formula;
import com.example.btpsd.model.ModelSpecificationsDetails;
import io.micrometer.common.lang.Nullable;
import lombok.RequiredArgsConstructor;
import lombok.Synchronized;
import lombok.extern.slf4j.Slf4j;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;
@Slf4j
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
        if (source.getLineType() != null) {
            modelSpecificationsDetailsCommand.setLineTypeCode(source.getLineType().getLineTypeCode());
        }
        modelSpecificationsDetailsCommand.setSelectionCheckBox(source.getSelectionCheckBox());
        modelSpecificationsDetailsCommand.setLineIndex(source.getLineIndex());
        modelSpecificationsDetailsCommand.setDeletionIndicator(source.getDeletionIndicator());
        modelSpecificationsDetailsCommand.setShortText(source.getShortText());
        modelSpecificationsDetailsCommand.setDontUseFormula(source.getDontUseFormula());
        if (source.getDontUseFormula() == false){
            modelSpecificationsDetailsCommand.setQuantity(source.getFormula().getResult());
        }
        else {
            modelSpecificationsDetailsCommand.setQuantity(source.getQuantity());
        }
        modelSpecificationsDetailsCommand.setGrossPrice(source.getGrossPrice());
        modelSpecificationsDetailsCommand.setOverFulfilmentPercentage(source.getOverFulfilmentPercentage());
        modelSpecificationsDetailsCommand.setPriceChangedAllowed(source.getPriceChangedAllowed());
        modelSpecificationsDetailsCommand.setUnlimitedOverFulfillment(source.getUnlimitedOverFulfillment());
        modelSpecificationsDetailsCommand.setPricePerUnitOfMeasurement(source.getPricePerUnitOfMeasurement());
        modelSpecificationsDetailsCommand.setExternalServiceNumber(source.getExternalServiceNumber());
//        modelSpecificationsDetailsCommand.setNetValue(source.getGrossPrice() * source.getQuantity());
        modelSpecificationsDetailsCommand.setServiceText(source.getServiceText());
        modelSpecificationsDetailsCommand.setLineText(source.getLineText());
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
