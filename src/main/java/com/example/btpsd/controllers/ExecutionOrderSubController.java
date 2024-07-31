package com.example.btpsd.controllers;

import com.example.btpsd.commands.ExecutionOrderMainCommand;
import com.example.btpsd.commands.ExecutionOrderSubCommand;
import com.example.btpsd.converters.ExecutionOrderSubToExecutionOrderSubCommand;
import com.example.btpsd.services.ExecutionOrderSubService;
import jakarta.validation.constraints.NotNull;
import lombok.RequiredArgsConstructor;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;
import java.util.Set;

@RestController
@RequiredArgsConstructor
public class ExecutionOrderSubController {

    private final ExecutionOrderSubService executionOrderSubService;
    private final ExecutionOrderSubToExecutionOrderSubCommand executionOrderSubToExecutionOrderSubCommand;


    @GetMapping("/executionordersub")
    Set<ExecutionOrderSubCommand> all() {
        return executionOrderSubService.getExecutionOrderSubCommands();
    }

    @GetMapping("/executionordersub/{mainItemCode}")
    public Optional<ExecutionOrderSubCommand> findByIds(@PathVariable @NotNull Long mainItemCode) {

        return Optional.ofNullable(executionOrderSubService.findExecutionOrderSubCommandById(mainItemCode));
    }

    @PostMapping("/executionordersub")
    ExecutionOrderSubCommand newExecutionOrderSubItemCommand(@RequestBody ExecutionOrderSubCommand newExecutionOrderSubItemCommand) {

        ExecutionOrderSubCommand savedCommand = executionOrderSubService.saveExecutionOrderSubCommand(newExecutionOrderSubItemCommand);
        return savedCommand;

    }

    @DeleteMapping("/executionordersub/{mainItemCode}")
    void deleteExecutionOrderSubItemCommand(@PathVariable Long mainItemCode) {
        executionOrderSubService.deleteById(mainItemCode);
    }

    @PatchMapping
    @RequestMapping("/executionordersub/{mainItemCode}")
    @Transactional
    ExecutionOrderSubCommand updateExecutionOrderSubCommand(@RequestBody ExecutionOrderSubCommand newExecutionOrderSubItemCommand, @PathVariable Long mainItemCode) {

        ExecutionOrderSubCommand command = executionOrderSubToExecutionOrderSubCommand.convert(executionOrderSubService.updateExecutionOrderSub(newExecutionOrderSubItemCommand, mainItemCode));
        return command;
    }

}