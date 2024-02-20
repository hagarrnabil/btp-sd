package com.example.btpsd.converters;

import com.example.btpsd.commands.MaterialGroupCommand;
import com.example.btpsd.model.MaterialGroup;
import io.micrometer.common.lang.Nullable;
import lombok.RequiredArgsConstructor;
import lombok.Synchronized;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;

@RequiredArgsConstructor
@Component
public class MaterialGroupToMaterialGroupCommand implements Converter<MaterialGroup, MaterialGroupCommand> {

    private final ModelSpecDetailsToModelSpecDetailsCommand modelSpecDetailsConverter;

    private final ServiceNumberToServiceNumberCommand serviceNumberConverter;

    @Synchronized
    @Nullable
    @Override
    public MaterialGroupCommand convert(MaterialGroup source) {

        if (source == null) {
            return null;
        }

        final MaterialGroupCommand materialGroupCommand = new MaterialGroupCommand();
        materialGroupCommand.setMaterialGroupCode(source.getMaterialGroupCode());
        materialGroupCommand.setCode(source.getCode());
        materialGroupCommand.setDescription(source.getDescription());
        if (source.getModelSpecificationsDetails() != null && source.getModelSpecificationsDetails().size() > 0){
            source.getModelSpecificationsDetails()
                    .forEach(modelSpecificationsDetails -> materialGroupCommand.getModelSpecificationsDetailsCommands().add(modelSpecDetailsConverter.convert(modelSpecificationsDetails)));
        }
        if (source.getServiceNumbers() != null && source.getServiceNumbers().size() > 0){
            source.getServiceNumbers()
                    .forEach(serviceNumber -> materialGroupCommand.getServiceNumberCommands().add(serviceNumberConverter.convert(serviceNumber)));
        }
        return materialGroupCommand;
    }
}
