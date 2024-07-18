package com.example.btpsd.controllers;

import com.example.btpsd.commands.InvoiceMainItemCommand;
import com.example.btpsd.converters.InvoiceMainItemToInvoiceMainItemCommand;
import com.example.btpsd.repositories.InvoiceMainItemRepository;
import com.example.btpsd.services.InvoiceMainItemService;
import jakarta.validation.constraints.NotNull;
import lombok.RequiredArgsConstructor;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;
import java.util.Set;

@RequiredArgsConstructor
@RestController
public class InvoiceMainItemController {

    private final InvoiceMainItemRepository invoiceMainItemRepository;

    private final InvoiceMainItemService invoiceMainItemService;

    private final InvoiceMainItemToInvoiceMainItemCommand invoiceMainItemToInvoiceMainItemCommand;

    @GetMapping("/mainitems")
    Set<InvoiceMainItemCommand> all() {
        return invoiceMainItemService.getMainItemCommands();
    }

    @GetMapping("/mainitems/{mainItemCode}")
    public Optional<InvoiceMainItemCommand> findByIds(@PathVariable @NotNull Long mainItemCode) {

        return Optional.ofNullable(invoiceMainItemService.findMainItemCommandById(mainItemCode));
    }

    @PostMapping("/mainitems")
    InvoiceMainItemCommand newMainItemCommand(@RequestBody InvoiceMainItemCommand newInvoiceMainItemCommand) {

        InvoiceMainItemCommand savedCommand = invoiceMainItemService.saveMainItemCommand(newInvoiceMainItemCommand);
        return savedCommand;

    }

    @DeleteMapping("/mainitems/{mainItemCode}")
    void deleteMainItemCommand(@PathVariable Long mainItemCode) {
        invoiceMainItemService.deleteById(mainItemCode);
    }

    @PatchMapping
    @RequestMapping("/mainitems/{mainItemCode}")
    @Transactional
    InvoiceMainItemCommand updateMainItemCommand(@RequestBody InvoiceMainItemCommand newInvoiceMainItemCommand, @PathVariable Long mainItemCode) {

        InvoiceMainItemCommand command = invoiceMainItemToInvoiceMainItemCommand.convert(invoiceMainItemService.updateMainItem(newInvoiceMainItemCommand, mainItemCode));
        return command;
    }

}
