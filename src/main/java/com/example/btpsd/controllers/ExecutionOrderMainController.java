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

    @GetMapping("/executionordermain/{executionOrderMainCode}")
    public Optional<ExecutionOrderMainCommand> findByIds(@PathVariable @NotNull Long executionOrderMainCode) {

        return Optional.ofNullable(executionOrderMainService.findExecutionOrderMainCommandById(executionOrderMainCode));
    }

    @PostMapping("/executionordermain")
    ExecutionOrderMainCommand newExecutionOrderMainItemCommand(@RequestBody ExecutionOrderMainCommand newExecutionOrderMainItemCommand) {

        ExecutionOrderMainCommand savedCommand = executionOrderMainService.saveExecutionOrderMainCommand(newExecutionOrderMainItemCommand);
        return savedCommand;

    }

    @DeleteMapping("/executionordermain/{executionOrderMainCode}")
    void deleteExecutionOrderMainItemCommand(@PathVariable Long executionOrderMainCode) {
        executionOrderMainService.deleteById(executionOrderMainCode);
    }

    @PatchMapping
    @RequestMapping("/executionordermain/{executionOrderMainCode}")
    @Transactional
    ExecutionOrderMainCommand updateExecutionOrderMainCommand(@RequestBody ExecutionOrderMainCommand newExecutionOrderMainItemCommand, @PathVariable Long executionOrderMainCode) {

        ExecutionOrderMainCommand command = executionOrderMainToExecutionOrderMainCommand.convert(executionOrderMainService.updateExecutionOrderMain(newExecutionOrderMainItemCommand, executionOrderMainCode));
        return command;
    }

//    @RequestMapping(method = RequestMethod.GET, value = "/mainitems/search")
//    @ResponseBody
//    public List<InvoiceMainItem> Search(@RequestParam String keyword) {
//
//        return invoiceMainItemRepository.search(keyword);
//    }
}
