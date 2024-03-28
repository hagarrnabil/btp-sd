package com.example.btpsd.converters;

import com.example.btpsd.commands.ModelSpecificationsCommand;
import com.example.btpsd.model.Currency;
import com.example.btpsd.model.ModelSpecifications;
import com.example.btpsd.model.ModelSpecificationsDetails;
import io.micrometer.common.lang.Nullable;
import lombok.Synchronized;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;

@Component
public class ModelSpecificationsCommandToModelSpecifications implements Converter<ModelSpecificationsCommand, ModelSpecifications> {

    @Synchronized
    @Nullable
    @Override
    public ModelSpecifications convert (ModelSpecificationsCommand source) {

        if (source == null) {
            return null;
        }

        final ModelSpecifications modelSpecifications = new ModelSpecifications();
        modelSpecifications.setModelSpecCode(source.getModelSpecCode());
//        for (int i = 0; i < source.getModelSpecDetailsCode().size(); i++) {
//            ModelSpecificationsDetails modelSpecificationsDetails = new ModelSpecificationsDetails();
//            if (!source.getModelSpecDetailsCode().isEmpty()) {
//                modelSpecificationsDetails.setModelSpecDetailsCode(Long.parseLong(source.getModelSpecDetailsCode().toString().trim()));
//                modelSpecifications.setModelSpecificationsDetails(modelSpecificationsDetails);
//                modelSpecificationsDetails.addModelSpecifications(modelSpecifications);
//            }
//        }
        if (source.getModelSpecDetailsCode() != null) {
            ModelSpecificationsDetails modelSpecificationsDetails = new ModelSpecificationsDetails();
            modelSpecificationsDetails.setModelSpecDetailsCode(source.getModelSpecDetailsCode());
            modelSpecifications.setModelSpecificationsDetails(modelSpecificationsDetails);
            modelSpecificationsDetails.addModelSpecifications(modelSpecifications);
        }
        if (source.getCurrencyCode() != null) {
            Currency currency = new Currency();
            currency.setCurrencyCode(source.getCurrencyCode());
            modelSpecifications.setCurrency(currency);
            currency.addModelSpecifications(modelSpecifications);
        }
        modelSpecifications.setModelServSpec(source.getModelServSpec());
        modelSpecifications.setBlockingIndicator(source.getBlockingIndicator());
        modelSpecifications.setServiceSelection(source.getServiceSelection());
        modelSpecifications.setDescription(source.getDescription());
        modelSpecifications.setSearchTerm(source.getSearchTerm());

        return modelSpecifications;

    }

}
