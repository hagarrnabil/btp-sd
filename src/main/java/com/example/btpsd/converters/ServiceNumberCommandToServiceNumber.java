package com.example.btpsd.converters;

import com.example.btpsd.commands.ServiceNumberCommand;
import com.example.btpsd.model.Formula;
import com.example.btpsd.model.ModelSpecificationsDetails;
import com.example.btpsd.model.ServiceNumber;
import io.micrometer.common.lang.Nullable;
import lombok.RequiredArgsConstructor;
import lombok.Synchronized;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;

@RequiredArgsConstructor
@Component
public class ServiceNumberCommandToServiceNumber implements Converter<ServiceNumberCommand, ServiceNumber> {

    private final ModelSpecDetailsCommandToModelSpecDetails modelSpecDetailsConverter;

    @Synchronized
    @Nullable
    @Override
    public ServiceNumber convert(ServiceNumberCommand source) {

        if (source == null) {
            return null;
        }

        final ServiceNumber serviceNumber = new ServiceNumber();
        serviceNumber.setServiceNumberCode(source.getServiceNumberCode());
        serviceNumber.setCode(source.getCode());
        serviceNumber.setDescription(source.getDescription());
        serviceNumber.setConvertedNumber(source.getConvertedNumber());
        serviceNumber.setNumberToBeConverted(source.getNumberToBeConverted());
        serviceNumber.setDeletionIndicator(source.getDeletionIndicator());
        serviceNumber.setShortTextChangeAllowed(source.getShortTextChangeAllowed());
        serviceNumber.setMainItem(source.getMainItem());
//        serviceNumber.setCheckBox(source.getCheckBox());
        serviceNumber.setLastChangeDate(source.getLastChangeDate());
        if (source.getFormulaCode() != null) {
            Formula formula = new Formula();
            formula.setFormulaCode(source.getFormulaCode());
            serviceNumber.setFormula(formula);
            formula.addServiceNumbers(serviceNumber);
        }
        if (source.getModelSpecificationsDetailsCommands() != null && source.getModelSpecificationsDetailsCommands().size() > 0) {
            source.getModelSpecificationsDetailsCommands()
                    .forEach(modelSpecificationsDetailsCommand -> serviceNumber.getModelSpecificationsDetails().add(modelSpecDetailsConverter.convert(modelSpecificationsDetailsCommand)));
        }
        return serviceNumber;
    }

}
