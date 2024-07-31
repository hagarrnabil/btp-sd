package com.example.btpsd.controllers;

import com.example.btpsd.commands.ExecutionOrderMainCommand;
import com.example.btpsd.converters.ExecutionOrderMainToExecutionOrderMainCommand;
import com.example.btpsd.services.ExecutionOrderMainService;
import jakarta.validation.constraints.NotNull;
import lombok.RequiredArgsConstructor;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;
import java.util.Set;

@RequiredArgsConstructor
@RestController
public class ExecutionOrderMainController {

//    private final InvoiceMainItemRepository invoiceMainItemRepository;

    private final ExecutionOrderMainService executionOrderMainService;

    private final ExecutionOrderMainToExecutionOrderMainCommand executionOrderMainToExecutionOrderMainCommand;

    @GetMapping("/executionordermain")
    Set<ExecutionOrderMainCommand> all() {
        return executionOrderMainService.getExecutionOrderMainCommands();
    }

    @GetMapping("/executionordermain/{mainItemCode}")
    public Optional<ExecutionOrderMainCommand> findByIds(@PathVariable @NotNull Long mainItemCode) {

        return Optional.ofNullable(executionOrderMainService.findExecutionOrderMainCommandById(mainItemCode));
    }

    @PostMapping("/executionordermain")
    ExecutionOrderMainCommand newExecutionOrderMainItemCommand(@RequestBody ExecutionOrderMainCommand newExecutionOrderMainItemCommand) {

        ExecutionOrderMainCommand savedCommand = executionOrderMainService.saveExecutionOrderMainCommand(newExecutionOrderMainItemCommand);
        return savedCommand;

    }

    @DeleteMapping("/executionordermain/{mainItemCode}")
    void deleteExecutionOrderMainItemCommand(@PathVariable Long mainItemCode) {
        executionOrderMainService.deleteById(mainItemCode);
    }

    @PatchMapping
    @RequestMapping("/executionordermain/{mainItemCode}")
    @Transactional
    ExecutionOrderMainCommand updateExecutionOrderMainCommand(@RequestBody ExecutionOrderMainCommand newExecutionOrderMainItemCommand, @PathVariable Long mainItemCode) {

        ExecutionOrderMainCommand command = executionOrderMainToExecutionOrderMainCommand.convert(executionOrderMainService.updateExecutionOrderMain(newExecutionOrderMainItemCommand, mainItemCode));
        return command;
    }

//    @RequestMapping(method = RequestMethod.GET, value = "/mainitems/search")
//    @ResponseBody
//    public List<InvoiceMainItem> Search(@RequestParam String keyword) {
//
//        return invoiceMainItemRepository.search(keyword);
//    }
}
