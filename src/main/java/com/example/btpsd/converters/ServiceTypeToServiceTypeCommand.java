package com.example.btpsd.converters;

import com.example.btpsd.commands.ServiceTypeCommand;
import com.example.btpsd.model.ServiceType;
import io.micrometer.common.lang.Nullable;
import lombok.RequiredArgsConstructor;
import lombok.Synchronized;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;

@RequiredArgsConstructor
@Component
public class ServiceTypeToServiceTypeCommand implements Converter<ServiceType, ServiceTypeCommand> {

    private final ModelSpecDetailsToModelSpecDetailsCommand modelSpecDetailsConverter;

    private final ServiceNumberToServiceNumberCommand serviceNumberConverter;

    @Synchronized
    @Nullable
    @Override
    public ServiceTypeCommand convert(ServiceType source) {

        if (source == null) {
            return null;
        }

        final ServiceTypeCommand serviceTypeCommand = new ServiceTypeCommand();
        serviceTypeCommand.setServiceTypeCode(source.getServiceTypeCode());
        serviceTypeCommand.setServiceId(source.getServiceId());
        serviceTypeCommand.setDescription(source.getDescription());
        serviceTypeCommand.setLastChangeDate(source.getLastChangeDate().now());
        if (source.getModelSpecificationsDetails() != null && source.getModelSpecificationsDetails().size() > 0){
            source.getModelSpecificationsDetails()
                    .forEach(modelSpecificationsDetails -> serviceTypeCommand.getModelSpecificationsDetailsCommands().add(modelSpecDetailsConverter.convert(modelSpecificationsDetails)));
        }
//        if (source.getServiceNumbers() != null && source.getServiceNumbers().size() > 0){
//            source.getServiceNumbers()
//                    .forEach(serviceNumber -> serviceTypeCommand.getServiceNumberCommands().add(serviceNumberConverter.convert(serviceNumber)));
//        }
        return serviceTypeCommand;
    }

}
