package com.example.btpsd.converters;

import com.example.btpsd.commands.ServiceNumberCommand;
import com.example.btpsd.commands.ServiceNumberIdCommand;
import com.example.btpsd.model.*;
import io.micrometer.common.lang.Nullable;
import lombok.RequiredArgsConstructor;
import lombok.Synchronized;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;

@RequiredArgsConstructor
@Component
public class ServiceNumberIdCommandToServiceNumberId implements Converter<ServiceNumberIdCommand, ServiceNumberId> {

    @Synchronized
    @Nullable
    @Override
    public ServiceNumberId convert(ServiceNumberIdCommand source) {

        if (source == null) {
            return null;
        }

        final ServiceNumberId serviceNumberId = new ServiceNumberId();
        serviceNumberId.setServiceNumberCode(source.getServiceNumberCode());
        serviceNumberId.setNoServiceNumber(source.getNoServiceNumber());
        return serviceNumberId;
    }

}
