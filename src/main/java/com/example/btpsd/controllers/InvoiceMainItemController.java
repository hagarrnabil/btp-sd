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

    @PostMapping("/mainitems")
    InvoiceMainItemCommand newMainItemCommand(@RequestBody InvoiceMainItemCommand newInvoiceMainItemCommand) {

        InvoiceMainItemCommand savedCommand = invoiceMainItemService.saveMainItemCommand(newInvoiceMainItemCommand);
        return savedCommand;

    }

    @PatchMapping
    @RequestMapping("/mainitems/{mainItemCode}")
    @Transactional
    InvoiceMainItemCommand updateMainItemCommand(@RequestBody InvoiceMainItemCommand newInvoiceMainItemCommand, @PathVariable Long mainItemCode) {

        InvoiceMainItemCommand command = invoiceMainItemToInvoiceMainItemCommand.convert(invoiceMainItemService.updateMainItem(newInvoiceMainItemCommand, mainItemCode));
        return command;
    }

    @PatchMapping("/mainitems/{salesQuotation}/{salesQuotationItem}/{pricingProcedureStep}/{pricingProcedureCounter}/{customerNumber}")
    public InvoiceMainItemCommand updateMainItemCommand(
            @RequestBody InvoiceMainItemCommand updatedInvoiceMainItemCommand,
            @PathVariable String salesQuotation,
            @PathVariable String salesQuotationItem,
            @PathVariable Integer pricingProcedureStep,
            @PathVariable Integer pricingProcedureCounter,
            @PathVariable String customerNumber) throws Exception {

        // Step 1: Set the referenceId for the InvoiceMainItemCommand
        updatedInvoiceMainItemCommand.setReferenceId(salesQuotation);

        // Step 2: Fetch the Sales Quotation details from SalesOrderCloudController
        String salesQuotationApiResponse = salesOrderCloudController.getSalesQuotation().toString();
        ObjectMapper objectMapper = new ObjectMapper();

        try {
            // Parse the API response to extract the array of sales quotations
            JsonNode responseJson = objectMapper.readTree(salesQuotationApiResponse);
            JsonNode salesQuotationResults = responseJson.path("d").path("results");

            // Loop through the results to find the relevant sales quotation
            for (JsonNode quotation : salesQuotationResults) {
                String quotationID = quotation.path("SalesQuotation").asText();
                if (quotationID.equals(salesQuotation)) {
                    // Extract ReferenceSDDocument if the SalesQuotation matches
                    String referenceSDDocument = quotation.path("ReferenceSDDocument").asText();
                    updatedInvoiceMainItemCommand.setReferenceSDDocument(referenceSDDocument);
                    break; // Exit the loop once we've found the correct quotation
                }
            }

        } catch (JsonProcessingException e) {
            throw new RuntimeException("Error processing Sales Quotation API response", e);
        }

        // Step 4: Check if an InvoiceMainItem with the same referenceId already exists
        Optional<InvoiceMainItem> existingInvoiceOpt =
                invoiceMainItemRepository.findByReferenceId(updatedInvoiceMainItemCommand.getReferenceId());
        InvoiceMainItem savedInvoiceMainItem;

        if (existingInvoiceOpt.isPresent()) {
            // If it exists, update the existing InvoiceMainItem
            savedInvoiceMainItem = invoiceMainItemService.updateMainItem(
                    updatedInvoiceMainItemCommand, existingInvoiceOpt.get().getInvoiceMainItemCode());
        } else {
            // If not, create and save a new InvoiceMainItem
            savedInvoiceMainItem = invoiceMainItemCommandToInvoiceMainItem.convert(updatedInvoiceMainItemCommand);
            savedInvoiceMainItem = invoiceMainItemRepository.save(savedInvoiceMainItem);
        }

        // Step 5: Calculate the totalHeader and set it in the saved InvoiceMainItem
        Double totalHeader = invoiceMainItemService.getTotalHeader();
        savedInvoiceMainItem.setTotalHeader(totalHeader);
        invoiceMainItemRepository.save(savedInvoiceMainItem);

        // Step 6: Make an API call to update the Sales Quotation Pricing
        try {
            invoiceMainItemService.callInvoicePricingAPI(
                    salesQuotation, salesQuotationItem, pricingProcedureStep, pricingProcedureCounter, totalHeader);
        } catch (Exception e) {
            log.error("Error while calling Sales Quotation Pricing API: " + e.getMessage(), e);
            throw new RuntimeException("Failed to update Invoice Pricing Element. Response Code: " + e.getMessage());
        }

        // Step 7: Convert the saved InvoiceMainItem back to a command object for the response
        return invoiceMainItemToInvoiceMainItemCommand.convert(savedInvoiceMainItem);
    }

    @PatchMapping("/mainitems")
    public ResponseEntity<String> updateTemporaryToPermanent() {
        invoiceMainItemService.updateAllTemporaryToPermanent();
        return ResponseEntity.ok("All temporary statuses updated to permanent.");
    }

    @DeleteMapping("/mainitems/{mainItemCode}")
    void deleteMainItemCommand(@PathVariable Long mainItemCode) {
        invoiceMainItemService.deleteById(mainItemCode);
    }


    @DeleteMapping("/mainitems")
    public void deleteTemporaryItems() {
        invoiceMainItemService.deleteByTemporaryStatus();
    }

    @RequestMapping(method = RequestMethod.GET, value = "/mainitems/search")
    @ResponseBody
    public List<InvoiceMainItem> Search(@RequestParam String keyword) {

        return invoiceMainItemRepository.search(keyword);
    }
}
