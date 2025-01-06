package com.example.btpsd.controllers;

import com.example.btpsd.commands.InvoiceMainItemCommand;
import com.example.btpsd.converters.InvoiceMainItemCommandToInvoiceMainItem;
import com.example.btpsd.converters.InvoiceMainItemToInvoiceMainItemCommand;
import com.example.btpsd.model.ExecutionOrderMain;
import com.example.btpsd.model.InvoiceMainItem;
import com.example.btpsd.model.ModelSpecificationsDetails;
import com.example.btpsd.model.ServiceNumber;
import com.example.btpsd.repositories.ExecutionOrderMainRepository;
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

import java.util.*;
import java.util.stream.Collectors;

@RequiredArgsConstructor
@RestController
public class InvoiceMainItemController {

    // Initialize the logger
    Logger log = LogManager.getLogger(this.getClass());

    private final InvoiceMainItemRepository invoiceMainItemRepository;

    private final ExecutionOrderMainRepository executionOrderMainRepository;

    private final InvoiceMainItemCommandToInvoiceMainItem invoiceMainItemCommandToInvoiceMainItem;

    private final InvoiceMainItemService invoiceMainItemService;

    private final ProductCloudController productCloudController;

    private final BusinessPartnerCloudController businessPartnerCloudController;

    private final SalesOrderCloudController salesOrderCloudController;

    private final InvoiceMainItemToInvoiceMainItemCommand invoiceMainItemToInvoiceMainItemCommand;

    @GetMapping("/mainitems")
    public List<InvoiceMainItemCommand> fetchInvoiceMainItemsBySalesOrder(
            @RequestParam String salesOrder) {

        System.out.println("Fetching Invoice using Sales Order: " + salesOrder);

        // Step 1: Fetch sales order details from S4
        String salesOrderApiResponse;
        try {
            salesOrderApiResponse = salesOrderCloudController.getAllSalesOrders().toString();
        } catch (Exception e) {
            throw new RuntimeException("Failed to fetch sales orders from S4", e);
        }

        ObjectMapper objectMapper = new ObjectMapper();
        String referenceSDDocument = null;

        try {
            JsonNode responseJson = objectMapper.readTree(salesOrderApiResponse);
            JsonNode salesOrderResults = responseJson.path("d").path("results");

            if (!salesOrderResults.isArray() || salesOrderResults.isEmpty()) {
                throw new RuntimeException("No sales order results found in the API response.");
            }

            // Step 2: Find the ReferenceSDDocument for the given sales order
            for (JsonNode order : salesOrderResults) {
                String orderID = order.path("SalesOrder").asText();

                System.out.println("Checking Sales Order: " + orderID);

                if (orderID.equals(salesOrder)) {
                    referenceSDDocument = order.path("ReferenceSDDocument").asText();
                    System.out.println("Match found: ReferenceSDDocument = " + referenceSDDocument);
                    break;
                }
            }
        } catch (JsonProcessingException e) {
            throw new RuntimeException("Error processing Sales Order API response", e);
        }

        if (referenceSDDocument == null || referenceSDDocument.isEmpty()) {
            throw new RuntimeException("No ReferenceSDDocument found for Sales Order: " + salesOrder +
                    ". Ensure the SalesOrder is correct.");
        }

        List<InvoiceMainItem> invoiceItems = invoiceMainItemRepository.findByReferenceId(referenceSDDocument);

        if (invoiceItems.isEmpty()) {
            throw new RuntimeException("No Invoice Main Item found with ReferenceSDDocument: " +
                    referenceSDDocument);
        }

        System.out.println("Fetched Invoice Items: " + invoiceItems);

        // Step 5: Convert InvoiceMainItem entities to command objects to return in response
        List<InvoiceMainItemCommand> response = new ArrayList<>();
        for (InvoiceMainItem item : invoiceItems) {
            response.add(invoiceMainItemToInvoiceMainItemCommand.convert(item));
        }

        return response;
    }

    @GetMapping("/mainitems/all")
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

    @GetMapping("/mainitems/referenceid")
    public ResponseEntity<List<InvoiceMainItemCommand>> getInvoiceMainItemsByReferenceId(@RequestParam String referenceId) throws Exception {
        // Fetch all InvoiceMainItem items with the given salesQuotationId (referenceId)
        List<InvoiceMainItem> invoiceMainItems = invoiceMainItemRepository.findByReferenceId(referenceId);

        // Check if the list is empty and return 404 if no items are found
        if (invoiceMainItems.isEmpty()) {
            return ResponseEntity.notFound().build(); // Return 404 if no items found
        }

        // SalesQuotationItemNumber is fixed as "10"
//        String salesQuotationItemNumber = "10";

        // Fetch Sales Quotation Item text from the S4 API for the given salesQuotationId
        StringBuilder response = salesOrderCloudController.getSalesQuotationItem(referenceId);
        ObjectMapper objectMapper = new ObjectMapper();
        JsonNode responseJson = objectMapper.readTree(response.toString());

        // Find the matching node for SalesQuotationItemNumber "10"
        JsonNode matchingNode = findMatchingNode(responseJson);

        // If the matching node is found, update all InvoiceMainItem records
        if (matchingNode != null) {
            String itemText = matchingNode.path("SalesQuotationItemText").asText();

            for (InvoiceMainItem item : invoiceMainItems) {
                // Update the salesQuotationItemText field
                item.setSalesQuotationItemText(itemText);

                // Optionally save the updated item in the database
                invoiceMainItemRepository.save(item);
            }
        }

        // Convert the list of InvoiceMainItem to InvoiceMainItemCommand for the response
        List<InvoiceMainItemCommand> responseItems = invoiceMainItems.stream()
                .map(invoiceMainItemToInvoiceMainItemCommand::convert)
                .collect(Collectors.toList());

        return ResponseEntity.ok(responseItems);
    }

    private JsonNode findMatchingNode(JsonNode responseJson) {
        // Search for the specific SalesQuotationItemNumber in the API response
        for (JsonNode node : responseJson.path("d").path("results")) {
                return node;
        }
        return null;
    }


    @GetMapping("/mainitems/id")
    public Optional<InvoiceMainItemCommand> findByIds(@RequestParam Long invoiceMainItemCode) {

        return Optional.ofNullable(invoiceMainItemService.findMainItemCommandById(invoiceMainItemCode));
    }

    @PostMapping("/total")
    public ResponseEntity<Map<String, Double>> calculateTotal(@RequestBody InvoiceMainItemCommand invoiceMainItemCommand) {
        InvoiceMainItem invoiceMainItem = new InvoiceMainItem();
        invoiceMainItem.setQuantity(invoiceMainItemCommand.getQuantity());
        invoiceMainItem.setAmountPerUnit(invoiceMainItemCommand.getAmountPerUnit());
        invoiceMainItem.setProfitMargin(invoiceMainItemCommand.getProfitMargin());

        // Get the total and amount per unit with profit
        Map<String, Double> resultMap = invoiceMainItem.calculateTotal();

        return ResponseEntity.ok(resultMap);
    }

    @GetMapping("/totalheader")
    public ResponseEntity<Double> calculateTotalHeader() {
        List<InvoiceMainItem> allItems = (List<InvoiceMainItem>) invoiceMainItemRepository.findAll();
        Double totalHeader = InvoiceMainItem.calculateTotalHeader(allItems);
        return ResponseEntity.ok(totalHeader);
    }

    @PostMapping("/mainitems")
    public List<InvoiceMainItemCommand> saveOrUpdateMainItems(
            @RequestBody List<InvoiceMainItemCommand> invoiceMainItemCommands,
            @RequestParam(required = false) String salesQuotation,
            @RequestParam(required = false) String salesQuotationItem,
            @RequestParam(required = false) Integer pricingProcedureStep,
            @RequestParam(required = false) Integer pricingProcedureCounter,
            @RequestParam(required = false) String customerNumber) throws Exception {

        List<InvoiceMainItem> savedItems = new ArrayList<>();

        // Step 1: Set reference ID and unique ID
        if (salesQuotation != null && salesQuotationItem != null) {
            for (InvoiceMainItemCommand item : invoiceMainItemCommands) {
                item.setReferenceId(salesQuotation);
                item.setSalesQuotationItem(salesQuotationItem);

                // Fetch Sales Quotation details and set ReferenceSDDocument
                String salesQuotationApiResponse = salesOrderCloudController.getSalesQuotation().toString();
                ObjectMapper objectMapper = new ObjectMapper();
                try {
                    JsonNode responseJson = objectMapper.readTree(salesQuotationApiResponse);
                    JsonNode salesQuotationResults = responseJson.path("d").path("results");

                    for (JsonNode quotation : salesQuotationResults) {
                        String quotationID = quotation.path("SalesQuotation").asText();
                        if (quotationID.equals(salesQuotation)) {
                            String referenceSDDocument = quotation.path("ReferenceSDDocument").asText();
                            item.setReferenceSDDocument(referenceSDDocument);
                            break;
                        }
                    }
                } catch (JsonProcessingException e) {
                    throw new RuntimeException("Error processing Sales Quotation API response", e);
                }
            }
        }

        // Step 2: Save each item with generated uniqueId
        for (InvoiceMainItemCommand itemCommand : invoiceMainItemCommands) {
            InvoiceMainItem item = invoiceMainItemCommandToInvoiceMainItem.convert(itemCommand);

            // Generate uniqueId
            item.generateUniqueId(salesQuotation, salesQuotationItem);

            // Save each item individually
            savedItems.add(invoiceMainItemRepository.save(item));
        }

        // Step 3: Calculate totalHeader
        Double totalHeader = invoiceMainItemService.getTotalHeader();
        for (InvoiceMainItem savedItem : savedItems) {
            savedItem.setTotalHeader(totalHeader);
            invoiceMainItemRepository.save(savedItem);  // Re-save after updating totalHeader
        }

        // Step 4: Call Invoice Pricing API
        try {
            invoiceMainItemService.callInvoicePricingAPI(
                    salesQuotation, salesQuotationItem, pricingProcedureStep, pricingProcedureCounter, totalHeader);
        } catch (Exception e) {
            log.error("Error while calling Sales Quotation Pricing API: " + e.getMessage(), e);
            throw new RuntimeException("Failed to update Invoice Pricing Element. Response Code: " + e.getMessage());
        }

        // Step 5: Convert and return the saved items as a list of command objects for the response
        List<InvoiceMainItemCommand> response = new ArrayList<>();
        for (InvoiceMainItem savedItem : savedItems) {
            response.add(invoiceMainItemToInvoiceMainItemCommand.convert(savedItem));
        }

        return response;
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
        List<InvoiceMainItem> existingInvoiceOpt =
                invoiceMainItemRepository.findByReferenceId(updatedInvoiceMainItemCommand.getReferenceId());
        InvoiceMainItem savedInvoiceMainItem;

        if (!existingInvoiceOpt.isEmpty()) {
            // If it exists, update the existing InvoiceMainItem
            savedInvoiceMainItem = invoiceMainItemService.updateMainItem(
                    updatedInvoiceMainItemCommand, existingInvoiceOpt.get(0).getInvoiceMainItemCode());
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


    @DeleteMapping("/mainitems/{invoiceMainItemCode}")
    void deleteMainItemCommand(@PathVariable Long invoiceMainItemCode) {
        invoiceMainItemService.deleteById(invoiceMainItemCode);
    }


    @RequestMapping(method = RequestMethod.GET, value = "/mainitems/search")
    @ResponseBody
    public List<InvoiceMainItem> Search(@RequestParam String keyword) {

        return invoiceMainItemRepository.search(keyword);
    }
}
