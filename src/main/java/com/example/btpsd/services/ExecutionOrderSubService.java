package com.example.btpsd.services;

import com.example.btpsd.commands.ExecutionOrderSubCommand;
import com.example.btpsd.model.ExecutionOrderSub;

import java.util.Set;

public interface ExecutionOrderSubService {

    Set<ExecutionOrderSubCommand> getExecutionOrderSubCommands();

    ExecutionOrderSub findById(Long l);

    void deleteById(Long idToDelete);

    ExecutionOrderSubCommand saveExecutionOrderSubCommand(ExecutionOrderSubCommand command);

    ExecutionOrderSub updateExecutionOrderSub(ExecutionOrderSubCommand newExecutionOrderSubCommand, Long l);

    ExecutionOrderSubCommand findExecutionOrderSubCommandById(Long l);

}
