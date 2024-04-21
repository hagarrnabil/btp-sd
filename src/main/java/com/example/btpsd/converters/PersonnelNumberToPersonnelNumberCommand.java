package com.example.btpsd.converters;

import com.example.btpsd.commands.PersonnelNumberCommand;
import com.example.btpsd.model.PersonnelNumber;
import io.micrometer.common.lang.Nullable;
import lombok.RequiredArgsConstructor;
import lombok.Synchronized;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;

@RequiredArgsConstructor
@Component
public class PersonnelNumberToPersonnelNumberCommand implements Converter<PersonnelNumber, PersonnelNumberCommand> {

    private final ModelSpecDetailsToModelSpecDetailsCommand modelSpecDetailsConverter;

    @Synchronized
    @Nullable
    @Override
    public PersonnelNumberCommand convert(PersonnelNumber source) {

        if (source == null) {
            return null;
        }

        final PersonnelNumberCommand personnelNumberCommand = new PersonnelNumberCommand();
        personnelNumberCommand.setPersonnelNumberCode(source.getPersonnelNumberCode());
        personnelNumberCommand.setCode(source.getCode());
        personnelNumberCommand.setDescription(source.getDescription());
        return personnelNumberCommand;
    }

}
