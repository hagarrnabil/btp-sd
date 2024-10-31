package com.example.btpsd.controllers;

import com.example.btpsd.commands.InvoiceMainItemCommand;
import com.example.btpsd.converters.InvoiceMainItemCommandToInvoiceMainItem;
import com.example.btpsd.converters.InvoiceMainItemToInvoiceMainItemCommand;
import com.example.btpsd.model.InvoiceMainItem;
import com.example.btpsd.model.ModelSpecificationsDetails;
import com.example.btpsd.model.ServiceNumber;
import com.example.btpsd.repositories.InvoiceMainItemRepository;
import com.example.btpsd.repositories.ServiceNumberRepository;
import com.example.btpsd.services.InvoiceMainItemService;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.validation.constraints.NotNull;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

@RequiredArgsConstructor
@RestController
public class InvoiceMainItemController {

    // Initialize the logger
    Logger log = LogManager.getLogger(this.getClass());

    private final InvoiceMainItemRepository invoiceMainItemRepository;

    private final InvoiceMainItemCommandToInvoiceMainItem invoiceMainItemCommandToInvoiceMainItem;

    private final InvoiceMainItemService invoiceMainItemService;

    private final ProductCloudController productCloudController;

    private final BusinessPartnerCloudController businessPartnerCloudController;

    private final SalesOrderCloudController salesOrderCloudController;

    private final InvoiceMainItemToInvoiceMainItemCommand invoiceMainItemToInvoiceMainItemCommand;

    @GetMapping("/mainitems")
    Set<InvoiceMainItemCommand> all() {
        return invoiceMainItemService.getMainItemCommands();
    }

    @GetMapping("/mainitems/{salesQuotation}/{salesQuotationItem}")
    public StringBuilder findBySalesQuotationAndItem(
            @PathVariable("salesQuotation") String salesQuotation,
            @PathVariable("salesQuotationItem") String salesQuotationItem) {

        // Use the method to fetch Sales Quotation Item based on path variables
        return salesOrderCloudController.getSalesQuotationItemById(salesQuotation, salesQuotationItem);
    }

    @GetMapping("/mainitems/{referenceId}")
    public ResponseEntity<List<InvoiceMainItemCommand>> getInvoiceMainItemsByReferenceId(@PathVariable String referenceId) {
        Optional<InvoiceMainItem> invoiceMainItems = invoiceMainItemRepository.findByReferenceId(referenceId);

        if (invoiceMainItems.isEmpty()) {
            return ResponseEntity.notFound().build(); // Return 404 if no items found
        }

        // Convert the list of InvoiceMainItem to InvoiceMainItemCommand for the response
        List<InvoiceMainItemCommand> responseItems = invoiceMainItems.stream()
                .map(invoiceMainItemToInvoiceMainItemCommand::convert)
                .collect(Collectors.toList());

        return ResponseEntity.ok(responseItems);
    }


    @PatchMapping("/mainitems/{salesQuotation}/{salesQuotationItem}/{pricingProcedureStep}/{pricingProcedureCounter}/{customerNumber}")
    public InvoiceMainItemCommand updateMainItemCommand(
            @RequestBody InvoiceMainItemCommand updatedInvoiceMainItemCommand,
            @PathVariable String salesQuotation,
            @PathVariable String salesQuotationItem,
            @PathVariable Integer pricingProcedureStep,
            @PathVariable Integer pricingProcedureCounter,
            @PathVariable String customerNumber) throws Exception {

        // Step 1: Set the referenceId for the command
        updatedInvoiceMainItemCommand.setReferenceId(salesQuotation);

        // Step 2: Check if an InvoiceMainItem with the same referenceId exists
        Optional<InvoiceMainItem> existingInvoiceOpt = invoiceMainItemRepository.findByReferenceId(updatedInvoiceMainItemCommand.getReferenceId());
        InvoiceMainItem savedInvoiceMainItem;

        if (existingInvoiceOpt.isPresent()) {
            // Update existing InvoiceMainItem using the update method
            savedInvoiceMainItem = invoiceMainItemService.updateMainItem(updatedInvoiceMainItemCommand, existingInvoiceOpt.get().getInvoiceMainItemCode());
        } else {
            // Create a new InvoiceMainItem
            savedInvoiceMainItem = invoiceMainItemCommandToInvoiceMainItem.convert(updatedInvoiceMainItemCommand);
            savedInvoiceMainItem = invoiceMainItemRepository.save(savedInvoiceMainItem);
        }

        // Step 3: Calculate the totalHeader
        Double totalHeader = invoiceMainItemService.getTotalHeader();
        savedInvoiceMainItem.setTotalHeader(totalHeader);
        invoiceMainItemRepository.save(savedInvoiceMainItem);

        // Step 4: Call the Sales Quotation Pricing API with the updated total header
        try {
            invoiceMainItemService.callInvoicePricingAPI(
                    salesQuotation, salesQuotationItem, pricingProcedureStep, pricingProcedureCounter, totalHeader);
        } catch (Exception e) {
            log.error("Error while calling Sales Quotation Pricing API: " + e.getMessage(), e);
            throw new RuntimeException("Failed to update Invoice Pricing Element. Response Code: " + e.getMessage());
        }

        // Convert back to command for return
        return invoiceMainItemToInvoiceMainItemCommand.convert(savedInvoiceMainItem);
    }

    @DeleteMapping("/mainitems/{mainItemCode}")
    void deleteMainItemCommand(@PathVariable Long mainItemCode) {
        invoiceMainItemService.deleteById(mainItemCode);
    }


    @RequestMapping(method = RequestMethod.GET, value = "/mainitems/search")
    @ResponseBody
    public List<InvoiceMainItem> Search(@RequestParam String keyword) {

        return invoiceMainItemRepository.search(keyword);
    }
}
