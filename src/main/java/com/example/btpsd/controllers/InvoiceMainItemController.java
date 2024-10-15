package com.example.btpsd.controllers;

import com.example.btpsd.commands.InvoiceMainItemCommand;
import com.example.btpsd.converters.InvoiceMainItemToInvoiceMainItemCommand;
import com.example.btpsd.model.InvoiceMainItem;
import com.example.btpsd.model.ModelSpecificationsDetails;
import com.example.btpsd.repositories.InvoiceMainItemRepository;
import com.example.btpsd.services.InvoiceMainItemService;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.validation.constraints.NotNull;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import lombok.RequiredArgsConstructor;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;
import java.util.Set;

@RequiredArgsConstructor
@RestController
public class InvoiceMainItemController {

    // Initialize the logger
    Logger log = LogManager.getLogger(this.getClass());

    private final InvoiceMainItemRepository invoiceMainItemRepository;

    private final InvoiceMainItemService invoiceMainItemService;

    private final ProductCloudController productCloudController;

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
    public InvoiceMainItemCommand newMainItemCommand(
            @RequestBody InvoiceMainItemCommand newInvoiceMainItemCommand) throws Exception {

        // Step 1: Fetch product and product description from ProductCloudController
        String productApiResponse = productCloudController.getAllProducts().toString();
        String productDescApiResponse = productCloudController.getAllProductsDesc().toString();

        // Step 2: Extract fields from the product API response (assuming a JSON format)
        ObjectMapper objectMapper = new ObjectMapper();
        try {
            JsonNode productJson = objectMapper.readTree(productApiResponse);
            JsonNode productDescJson = objectMapper.readTree(productDescApiResponse);

            String serviceNumberCode = productJson.path("d").path("results").get(0).path("Product").asText();  // Adjust index as needed
            String unitOfMeasurementCode = productJson.path("d").path("results").get(0).path("BaseUnit").asText();
            String description = productDescJson.path("d").path("results").get(0).path("ProductDescription").asText();

            // Step 3: Set these values in the new InvoiceMainItemCommand
            newInvoiceMainItemCommand.setServiceNumberCode(Long.valueOf(serviceNumberCode));
            newInvoiceMainItemCommand.setUnitOfMeasurementCode(unitOfMeasurementCode);
            newInvoiceMainItemCommand.setDescription(description);

        } catch (JsonProcessingException e) {
            throw new RuntimeException("Error processing product API response", e);
        }

        // Step 4: Save the Main Item
        InvoiceMainItemCommand savedCommand = invoiceMainItemService.saveMainItemCommand(newInvoiceMainItemCommand);
        if (savedCommand == null) {
            throw new RuntimeException("Failed to save Invoice Main Item.");
        }


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
