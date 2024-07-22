package com.example.btpsd.services;

import com.example.btpsd.commands.ExecutionOrderMainCommand;
import com.example.btpsd.model.ExecutionOrderMain;

import java.util.Set;

public interface ExecutionOrderMainService {

    Set<ExecutionOrderMainCommand> getExecutionOrderMainCommands();

    ExecutionOrderMain findById(Long l);

    void deleteById(Long idToDelete);

    ExecutionOrderMainCommand saveExecutionOrderMainCommand(ExecutionOrderMainCommand command);

    ExecutionOrderMain updateExecutionOrderMain(ExecutionOrderMainCommand newExecutionOrderMainCommand, Long l);

    ExecutionOrderMainCommand findExecutionOrderMainCommandById(Long l);

}
