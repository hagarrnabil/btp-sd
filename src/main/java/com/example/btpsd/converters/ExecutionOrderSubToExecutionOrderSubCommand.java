package com.example.btpsd.converters;

import com.example.btpsd.commands.ExecutionOrderSubCommand;
import com.example.btpsd.model.ExecutionOrderSub;
import io.micrometer.common.lang.Nullable;
import lombok.RequiredArgsConstructor;
import lombok.Synchronized;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;

@RequiredArgsConstructor
@Component
public class ExecutionOrderSubToExecutionOrderSubCommand implements Converter<ExecutionOrderSub, ExecutionOrderSubCommand> {

    @Synchronized
    @Nullable
    @Override
    public ExecutionOrderSubCommand convert(ExecutionOrderSub source) {

        if (source == null) {
            return null;
        }

        final ExecutionOrderSubCommand executionOrderSubCommand = new ExecutionOrderSubCommand();
        executionOrderSubCommand.setExecutionOrderSubCode(source.getExecutionOrderSubCode());
        return executionOrderSubCommand;
    }
}
