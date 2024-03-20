package com.example.btpsd.converters;

import com.example.btpsd.commands.ServiceNumberIdCommand;
import com.example.btpsd.commands.UnitOfMeasurementCommand;
import com.example.btpsd.model.ServiceNumberId;
import com.example.btpsd.model.UnitOfMeasurement;
import io.micrometer.common.lang.Nullable;
import lombok.RequiredArgsConstructor;
import lombok.Synchronized;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;

@RequiredArgsConstructor
@Component
public class ServiceNumberIdToServiceNumberIdCommand implements Converter<ServiceNumberId, ServiceNumberIdCommand> {

    @Synchronized
    @Nullable
    @Override
    public ServiceNumberIdCommand convert(ServiceNumberId source) {

        if (source == null) {
            return null;
        }

        final ServiceNumberIdCommand serviceNumberIdCommand = new ServiceNumberIdCommand();
        serviceNumberIdCommand.setServiceNumberCode(source.getServiceNumberCode());
        serviceNumberIdCommand.setNoServiceNumber(source.getNoServiceNumber());
        return serviceNumberIdCommand;
    }

}
