package com.example.btpsd.converters;

import com.example.btpsd.commands.ServiceNumberCommand;
import com.example.btpsd.commands.ServiceNumberIdCommand;
import com.example.btpsd.model.*;
import io.micrometer.common.lang.Nullable;
import lombok.RequiredArgsConstructor;
import lombok.Synchronized;
import org.apache.commons.math3.random.RandomDataGenerator;
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
//        if (source.getServiceNumberCode() == null) {
//            RandomDataGenerator randomDataGenerator = new RandomDataGenerator();
//            Long min = 1L;
//            Long max = 1000L;
//            Long randomWithRandomDataGenerator = randomDataGenerator.nextLong(min, max);
//            serviceNumberId.setNoServiceNumber(randomWithRandomDataGenerator);
//        }
//        else {
//            serviceNumberId.setServiceNumberCode(source.getServiceNumberCode());
//        }
        return serviceNumberId;
    }

}
