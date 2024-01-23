//package com.example.btpsd.converters;
//
//import com.example.btpsd.commands.ModelSpecificationsCommand;
//import com.example.btpsd.model.ModelSpecifications;
//import io.micrometer.common.lang.Nullable;
//import lombok.Synchronized;
//import org.springframework.core.convert.converter.Converter;
//import org.springframework.stereotype.Component;
//
//@Component
//public class ModelSpecificationsToModelSpecificationsCommand implements Converter<ModelSpecifications, ModelSpecificationsCommand> {
//
//    @Synchronized
//    @Nullable
//    @Override
//    public ModelSpecificationsCommand convert(ModelSpecifications source) {
//
//        if (source == null) {
//            return null;
//        }
//
//        final ModelSpecificationsCommand modelSpecificationsCommand = new ModelSpecificationsCommand();
//        modelSpecificationsCommand.setModelSpec(source.getModelSpec());
//        if (source.getModelSpecificationsDetails() != null) {
//            modelSpecificationsCommand.setModelSpecDetails(source.getModelSpecificationsDetails().getModelSpecDetails());
//        }
//        if (source.getCurrency() != null) {
//            modelSpecificationsCommand.setCurrency(source.getCurrency().getCurrency());
//        }
//        modelSpecificationsCommand.setModelSpec(source.getModelSpec());
//        modelSpecificationsCommand.setModelServSpec(source.getModelServSpec());
//        modelSpecificationsCommand.setBlockingIndicator(source.getBlockingIndicator());
//        modelSpecificationsCommand.setServiceSelection(source.getServiceSelection());
//        modelSpecificationsCommand.setDescription(source.getDescription());
//        modelSpecificationsCommand.setSearchTerm(source.getSearchTerm());
//        if (source.getBuildings() != null && source.getBuildings().size() > 0){
//            source.getBuildings()
//                    .forEach(building -> modelSpecificationsCommand.getBuildingCommands().add(buildingConverter.convert(building)));
//        }
//        return modelSpecificationsCommand;
//
//    }
//
//}
//
