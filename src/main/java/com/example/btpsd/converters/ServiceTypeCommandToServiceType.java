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
public class ServiceTypeCommandToServiceType implements Converter<ServiceTypeCommand, ServiceType> {


    @Synchronized
    @Nullable
    @Override
    public ServiceType convert(ServiceTypeCommand source) {

        if (source == null) {
            return null;
        }

        final ServiceType serviceType = new ServiceType();
        serviceType.setServiceTypeCode(source.getServiceTypeCode());
        serviceType.setServiceId(source.getServiceId());
        serviceType.setDescription(source.getDescription());
        serviceType.setLastChangeDate(source.getLastChangeDate().now());
        return serviceType;
    }

}
