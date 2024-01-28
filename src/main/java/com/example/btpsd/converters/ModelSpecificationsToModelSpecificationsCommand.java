package com.example.btpsd.converters;

import com.example.btpsd.commands.ModelSpecificationsCommand;
import com.example.btpsd.model.ModelSpecifications;
import io.micrometer.common.lang.Nullable;
import lombok.Synchronized;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;

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
        if (source.getModelSpecificationsDetails() != null) {
            modelSpecificationsCommand.setModelSpecCode(source.getModelSpecificationsDetails().getModelSpecDetailsCode());
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

