//package com.example.btpsd.converters;
//
//import com.example.btpsd.commands.ModelSpecificationsCommand;
//import com.example.btpsd.model.Currency;
//import com.example.btpsd.model.ModelSpecifications;
//import com.example.btpsd.model.ModelSpecificationsDetails;
//import io.micrometer.common.lang.Nullable;
//import lombok.Synchronized;
//import org.springframework.core.convert.converter.Converter;
//import org.springframework.stereotype.Component;
//
//@Component
//public class ModelSpecificationsCommandToModelSpecifications implements Converter<ModelSpecificationsCommand, ModelSpecifications> {
//
//    @Synchronized
//    @Nullable
//    @Override
//    public ModelSpecifications convert (ModelSpecificationsCommand source) {
//
//        if (source == null) {
//            return null;
//        }
//
//        final ModelSpecifications modelSpecifications = new ModelSpecifications();
//        modelSpecifications.setModelSpec(source.getModelSpec());
//        if (source.getModelSpecDetails() != null) {
//            ModelSpecificationsDetails modelSpecificationsDetails = new ModelSpecificationsDetails();
//            modelSpecificationsDetails.setModelSpecDetails(source.getModelSpecDetails());
//            modelSpecifications.setModelSpecificationsDetails(modelSpecificationsDetails);
//            modelSpecificationsDetails.addModelSpecifications(modelSpecifications);
//        }
//        if (source.getCurrency() != null) {
//            Currency currency = new Currency();
//            currency.setCurrency(source.getCurrency());
//            modelSpecifications.setCurrency(currency);
//            currency.addModelSpecifications(modelSpecifications);
//        }
//        modelSpecifications.setProjectId(source.getProjectId());
//        modelSpecifications.setProjectDescription(source.getProjectDescription());
//        modelSpecifications.setValidFrom(source.getValidFrom());
//        modelSpecifications.setProfit(source.getProfit());
//        if (source.getBuildingCommands() != null && source.getBuildingCommands().size() > 0) {
//            source.getBuildingCommands()
//                    .forEach(buildingCommand -> modelSpecifications.getBuildings().add(buildingConverter.convert(buildingCommand)));
//        }
//        return modelSpecifications;
//
//    }
//
//}
