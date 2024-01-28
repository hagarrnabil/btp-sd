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
public class PersonnelNumberCommandToPersonnelNumber implements Converter<PersonnelNumberCommand, PersonnelNumber> {

    private final ModelSpecDetailsCommandToModelSpecDetails modelSpecDetailsConverter;

    @Synchronized
    @Nullable
    @Override
    public PersonnelNumber convert(PersonnelNumberCommand source) {

        if (source == null) {
            return null;
        }

        final PersonnelNumber personnelNumber = new PersonnelNumber();
        personnelNumber.setPersonnelNumberCode(source.getPersonnelNumberCode());
        personnelNumber.setCode(source.getCode());
        personnelNumber.setDescription(source.getDescription());
        if (source.getModelSpecificationsDetailsCommands() != null && source.getModelSpecificationsDetailsCommands().size() > 0) {
            source.getModelSpecificationsDetailsCommands()
                    .forEach(modelSpecificationsDetailsCommand -> personnelNumber.getModelSpecificationsDetails().add(modelSpecDetailsConverter.convert(modelSpecificationsDetailsCommand)));
        }
        return personnelNumber;
    }

}
