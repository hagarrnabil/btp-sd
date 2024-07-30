package com.example.btpsd.converters;

import com.example.btpsd.commands.CurrencyCommand;
import com.example.btpsd.commands.ExecutionOrderSubCommand;
import com.example.btpsd.model.Currency;
import com.example.btpsd.model.ExecutionOrderSub;
import io.micrometer.common.lang.Nullable;
import lombok.RequiredArgsConstructor;
import lombok.Synchronized;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;

@RequiredArgsConstructor
@Component
public class ExecutionOrderSubCommandToExecutionOrderSub implements Converter<ExecutionOrderSubCommand, ExecutionOrderSub> {

    @Synchronized
    @Nullable
    @Override
    public ExecutionOrderSub convert(ExecutionOrderSubCommand source) {

        if (source == null) {
            return null;
        }

        final ExecutionOrderSub executionOrderSub = new ExecutionOrderSub();
//        executionOrderSub.setExecutionOrderSubCode(source.getExecutionOrderSubCode());
        return executionOrderSub;
    }
}
