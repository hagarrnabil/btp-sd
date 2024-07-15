package com.example.btpsd.controllers;

import com.example.btpsd.commands.InvoiceCommand;
import com.example.btpsd.commands.SubItemCommand;
import com.example.btpsd.converters.InvoiceToInvoiceCommand;
import com.example.btpsd.repositories.InvoiceRepository;
import com.example.btpsd.services.InvoiceService;
import jakarta.validation.constraints.NotNull;
import lombok.RequiredArgsConstructor;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;
import java.util.Set;

@RequiredArgsConstructor
@RestController
@CrossOrigin(origins = "http://localhost:4200", maxAge = 3600)
public class InvoiceController {

    private final InvoiceRepository invoiceRepository;

    private final InvoiceService invoiceService;

    private final InvoiceToInvoiceCommand invoiceToInvoiceCommand;

    @GetMapping("/invoices")
    Set<InvoiceCommand> all() {
        return invoiceService.getInvoiceCommands();
    }

    @GetMapping("/invoices/{invoiceCode}")
    public Optional<InvoiceCommand> findByIds(@PathVariable @NotNull Long invoiceCode) {

        return Optional.ofNullable(invoiceService.findInvoiceCommandById(invoiceCode));
    }

    @PostMapping("/invoices")
    InvoiceCommand newInvoiceCommand(@RequestBody InvoiceCommand newInvoiceCommand) {

        InvoiceCommand savedCommand = invoiceService.saveInvoiceCommand(newInvoiceCommand);
        return savedCommand;

    }

    @DeleteMapping("/invoices/{invoiceCode}")
    void deleteInvoiceCommand(@PathVariable Long invoiceCode) {
        invoiceService.deleteById(invoiceCode);
    }

    @PatchMapping
    @RequestMapping("/invoices/{invoiceCode}")
    @Transactional
    InvoiceCommand updateInvoiceCommand(@RequestBody InvoiceCommand newInvoiceCommand, @PathVariable Long invoiceCode) {

        InvoiceCommand command = invoiceToInvoiceCommand.convert(invoiceService.updateInvoice(newInvoiceCommand, invoiceCode));
        return command;
    }

}
