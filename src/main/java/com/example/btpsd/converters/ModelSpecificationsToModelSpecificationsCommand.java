package com.example.btpsd.converters;

import com.example.btpsd.commands.ModelSpecificationsCommand;
import com.example.btpsd.model.ModelSpecifications;
import com.example.btpsd.model.ModelSpecificationsDetails;
import io.micrometer.common.lang.Nullable;
import lombok.Synchronized;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;

import java.util.Collections;

@Component
public class ModelSpecificationsToModelSpecificationsCommand implements Converter<ModelSpecifications, ModelSpecificationsCommand> {

    @Synchronized
    @Nullable
    @Override
    public ModelSpecificationsCommand convert(ModelSpecifications source) {

        if (source == null) {
            return null;
        }

        final ModelSpecificationsCommand modelSpecificationsCommand = new ModelSpecificationsCommand();
        modelSpecificationsCommand.setModelSpecCode(source.getModelSpecCode());
//        for (int i = 0; i < source.getModelSpecDetailsCode().size(); i++) {
//            if (!source.getModelSpecDetailsCode().isEmpty()) {
//                modelSpecificationsCommand.setModelSpecDetailsCode(Collections.singletonList(source.getModelSpecificationsDetails().getModelSpecDetailsCode()));
//            }
//        }
        if (source.getModelSpecificationsDetails() != null) {
            modelSpecificationsCommand.setModelSpecDetailsCode(source.getModelSpecificationsDetails().getModelSpecDetailsCode());
        }
        if (source.getCurrency() != null) {
            modelSpecificationsCommand.setCurrencyCode(source.getCurrency().getCurrencyCode());
        }
        modelSpecificationsCommand.setModelServSpec(source.getModelServSpec());
        modelSpecificationsCommand.setBlockingIndicator(source.getBlockingIndicator());
        modelSpecificationsCommand.setServiceSelection(source.getServiceSelection());
        modelSpecificationsCommand.setDescription(source.getDescription());
        modelSpecificationsCommand.setSearchTerm(source.getSearchTerm());

        return modelSpecificationsCommand;

    }

}

