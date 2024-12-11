package com.example.btpsd.controllers;

import java.util.List;
import java.util.Optional;
import java.util.Set;

import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.example.btpsd.commands.InvoiceMainItemCommand;
import com.example.btpsd.converters.InvoiceMainItemToInvoiceMainItemCommand;
import com.example.btpsd.model.InvoiceMainItem;
import com.example.btpsd.repositories.InvoiceMainItemRepository;
import com.example.btpsd.services.InvoiceMainItemService;

import jakarta.validation.constraints.NotNull;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@RestController
public class InvoiceMainItemController {

    private final InvoiceMainItemRepository invoiceMainItemRepository;

    private final InvoiceMainItemService invoiceMainItemService;

    private final InvoiceMainItemToInvoiceMainItemCommand invoiceMainItemToInvoiceMainItemCommand;
    @GetMapping("/mainitems")
    public ResponseEntity<Set<InvoiceMainItemCommand>> getAll(){
        return ResponseEntity.ok(invoiceMainItemService.getMainItemCommands());
    }
    //(Authentication authentication) {

        //if (authentication.getAuthorities().stream()
                //.anyMatch(grantedAuthority -> grantedAuthority.getAuthority().equals("Read-All"))) {
            // Return full invoice items for Admin role
        // } else if (authentication.getAuthorities().stream()
        //         .anyMatch(grantedAuthority -> grantedAuthority.getAuthority().equals("Read-All-Except-Total"))) {
        //     // Return restricted invoice items for InvoiceViewer role
        //     return invoiceMainItemService.getMainItemsExceptTotal();
        // } else {
        //     throw new AccessDeniedException("You do not have permission to access this resource.");
        // }
    //}

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

    @RequestMapping(method = RequestMethod.GET, value = "/mainitems/search")
    @ResponseBody
    public List<InvoiceMainItem> Search(@RequestParam String keyword) {

        return invoiceMainItemRepository.search(keyword);
    }
}
