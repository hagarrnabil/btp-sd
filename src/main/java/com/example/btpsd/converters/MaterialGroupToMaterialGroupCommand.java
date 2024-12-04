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
        return materialGroupCommand;
    }
}
