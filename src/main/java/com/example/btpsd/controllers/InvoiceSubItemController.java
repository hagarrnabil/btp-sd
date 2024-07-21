package com.example.btpsd.controllers;

import com.example.btpsd.commands.InvoiceSubItemCommand;
import com.example.btpsd.converters.InvoiceSubItemToInvoiceSubItemCommand;
import com.example.btpsd.model.InvoiceSubItem;
import com.example.btpsd.model.ModelSpecificationsDetails;
import com.example.btpsd.repositories.InvoiceSubItemRepository;
import com.example.btpsd.services.InvoiceSubItemService;
import jakarta.validation.constraints.NotNull;
import lombok.RequiredArgsConstructor;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;
import java.util.Set;

@RequiredArgsConstructor
@RestController
public class InvoiceSubItemController {

    private final InvoiceSubItemRepository invoiceSubItemRepository;

    private final InvoiceSubItemService invoiceSubItemService;

    private final InvoiceSubItemToInvoiceSubItemCommand invoiceSubItemToInvoiceSubItemCommand;

    @GetMapping("/subitems")
    Set<InvoiceSubItemCommand> all() {
        return invoiceSubItemService.getSubItemCommands();
    }

    @GetMapping("/subitems/{subItemCode}")
    public Optional<InvoiceSubItemCommand> findByIds(@PathVariable @NotNull Long subItemCode) {

        return Optional.ofNullable(invoiceSubItemService.findSubItemCommandById(subItemCode));
    }

    @PostMapping("/subitems")
    InvoiceSubItemCommand newSubItemCommand(@RequestBody InvoiceSubItemCommand newSubItemCommand) {

        InvoiceSubItemCommand savedCommand = invoiceSubItemService.saveSubItemCommand(newSubItemCommand);
        return savedCommand;

    }

    @DeleteMapping("/subitems/{subItemCode}")
    void deleteSubItemCommand(@PathVariable Long subItemCode) {
        invoiceSubItemService.deleteById(subItemCode);
    }

    @PatchMapping
    @RequestMapping("/subitems/{subItemCode}")
    @Transactional
    InvoiceSubItemCommand updateSubItemCommand(@RequestBody InvoiceSubItemCommand newSubItemCommand, @PathVariable Long subItemCode) {

        InvoiceSubItemCommand command = invoiceSubItemToInvoiceSubItemCommand.convert(invoiceSubItemService.updateSubItem(newSubItemCommand, subItemCode));
        return command;
    }

    @RequestMapping(method = RequestMethod.GET, value = "/subitems/search")
    @ResponseBody
    public List<InvoiceSubItem> Search(@RequestParam String keyword) {

        return invoiceSubItemRepository.search(keyword);
    }
}
