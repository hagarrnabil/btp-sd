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
        return serviceTypeCommand;
    }

}
