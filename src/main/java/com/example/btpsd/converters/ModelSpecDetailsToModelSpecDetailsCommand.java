package com.example.btpsd.converters;

import com.example.btpsd.commands.ModelSpecificationsDetailsCommand;
import com.example.btpsd.model.Formula;
import com.example.btpsd.model.ModelSpecificationsDetails;
import com.example.btpsd.model.ServiceNumber;
import io.micrometer.common.lang.Nullable;
import lombok.RequiredArgsConstructor;
import lombok.Synchronized;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.math3.random.RandomDataGenerator;
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
        modelSpecificationsDetailsCommand.setPersonnelNumberCode(source.getPersonnelNumberCode());
        modelSpecificationsDetailsCommand.setFormulaCode(source.getFormulaCode());
        modelSpecificationsDetailsCommand.setMaterialGroupCode(source.getMaterialGroupCode());
        modelSpecificationsDetailsCommand.setLineTypeCode(source.getLineTypeCode());
        modelSpecificationsDetailsCommand.setCurrencyCode(source.getCurrencyCode());
        if (source.getServiceNumber() != null) {
            modelSpecificationsDetailsCommand.setServiceNumberCode(source.getServiceNumber().getServiceNumberCode());
        }
        else {
            RandomDataGenerator randomDataGenerator = new RandomDataGenerator();
            Long min = 1L;
            Long max = 100L;
            Long randomWithRandomDataGenerator = randomDataGenerator.nextLong(min, max);
            modelSpecificationsDetailsCommand.setNoServiceNumber(randomWithRandomDataGenerator);
        }
        modelSpecificationsDetailsCommand.setSelectionCheckBox(source.getSelectionCheckBox());
        modelSpecificationsDetailsCommand.setLineIndex(source.getLineIndex());
//        modelSpecificationsDetailsCommand.setDeletionIndicator(source.getDeletionIndicator());
        modelSpecificationsDetailsCommand.setShortText(source.getShortText());
        modelSpecificationsDetailsCommand.setGrossPrice(source.getGrossPrice());
        modelSpecificationsDetailsCommand.setOverFulfilmentPercentage(source.getOverFulfilmentPercentage());
        modelSpecificationsDetailsCommand.setPriceChangedAllowed(source.getPriceChangedAllowed());
        modelSpecificationsDetailsCommand.setUnlimitedOverFulfillment(source.getUnlimitedOverFulfillment());
        modelSpecificationsDetailsCommand.setPricePerUnitOfMeasurement(source.getPricePerUnitOfMeasurement());
        modelSpecificationsDetailsCommand.setExternalServiceNumber(source.getExternalServiceNumber());
        modelSpecificationsDetailsCommand.setUnitOfMeasurementCode(source.getUnitOfMeasurementCode());
        modelSpecificationsDetailsCommand.setServiceTypeCode(source.getServiceTypeCode());
        modelSpecificationsDetailsCommand.setQuantity(source.getQuantity());
        modelSpecificationsDetailsCommand.setNetValue(source.getGrossPrice() * modelSpecificationsDetailsCommand.getQuantity());
        modelSpecificationsDetailsCommand.setServiceText(source.getServiceText());
        modelSpecificationsDetailsCommand.setLineText(source.getLineText());
        modelSpecificationsDetailsCommand.setLineNumber(source.getLineNumber());
        modelSpecificationsDetailsCommand.setAlternatives(source.getAlternatives());
        modelSpecificationsDetailsCommand.setBiddersLine(source.getBiddersLine());
        modelSpecificationsDetailsCommand.setSupplementaryLine(source.getSupplementaryLine());
        modelSpecificationsDetailsCommand.setLotSizeForCostingIsOne(source.getLotSizeForCostingIsOne());
        modelSpecificationsDetailsCommand.setLastChangeDate(source.getLastChangeDate().now());
        if (source.getModelSpecifications() != null && source.getModelSpecifications().size() > 0) {
            source.getModelSpecifications()
                    .forEach(modelSpecifications -> modelSpecificationsDetailsCommand.getModelSpecificationsCommands().add(modelSpecConverter.convert(modelSpecifications)));
        }

        return modelSpecificationsDetailsCommand;

    }

}
