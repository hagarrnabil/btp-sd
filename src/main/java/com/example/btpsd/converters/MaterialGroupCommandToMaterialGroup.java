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
public class MaterialGroupCommandToMaterialGroup implements Converter<MaterialGroupCommand, MaterialGroup> {


    @Synchronized
    @Nullable
    @Override
    public MaterialGroup convert(MaterialGroupCommand source) {

        if (source == null) {
            return null;
        }

        final MaterialGroup materialGroup = new MaterialGroup();
        materialGroup.setMaterialGroupCode(source.getMaterialGroupCode());
        materialGroup.setCode(source.getCode());
        materialGroup.setDescription(source.getDescription());
        return materialGroup;
    }
}

