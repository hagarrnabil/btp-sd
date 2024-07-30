package com.example.btpsd.controllers;

import com.example.btpsd.commands.ExecutionOrderMainCommand;
import com.example.btpsd.commands.InvoiceMainItemCommand;
import com.example.btpsd.converters.ExecutionOrderMainToExecutionOrderMainCommand;
import com.example.btpsd.converters.InvoiceMainItemToInvoiceMainItemCommand;
import com.example.btpsd.model.InvoiceMainItem;
import com.example.btpsd.repositories.InvoiceMainItemRepository;
import com.example.btpsd.services.ExecutionOrderMainService;
import com.example.btpsd.services.InvoiceMainItemService;
import jakarta.validation.constraints.NotNull;
import lombok.RequiredArgsConstructor;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;
import java.util.Set;

@RequiredArgsConstructor
@RestController
public class ExecutionOrderMainController {

//    private final InvoiceMainItemRepository invoiceMainItemRepository;

    private final ExecutionOrderMainService executionOrderMainService;

    private final ExecutionOrderMainToExecutionOrderMainCommand executionOrderMainToExecutionOrderMainCommand;

    @GetMapping("/executionorders")
    Set<ExecutionOrderMainCommand> all() {
        return executionOrderMainService.getExecutionOrderMainCommands();
    }

    @GetMapping("/executionorders/{mainItemCode}")
    public Optional<ExecutionOrderMainCommand> findByIds(@PathVariable @NotNull Long mainItemCode) {

        return Optional.ofNullable(executionOrderMainService.findExecutionOrderMainCommandById(mainItemCode));
    }

    @PostMapping("/executionorders")
    ExecutionOrderMainCommand newExecutionOrderMainItemCommand(@RequestBody ExecutionOrderMainCommand newExecutionOrderMainItemCommand) {

        ExecutionOrderMainCommand savedCommand = executionOrderMainService.saveExecutionOrderMainCommand(newExecutionOrderMainItemCommand);
        return savedCommand;

    }

    @DeleteMapping("/executionorders/{mainItemCode}")
    void deleteExecutionOrderMainItemCommand(@PathVariable Long mainItemCode) {
        executionOrderMainService.deleteById(mainItemCode);
    }

    @PatchMapping
    @RequestMapping("/executionorders/{mainItemCode}")
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
