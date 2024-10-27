package com.example.btpsd.controllers;

import com.example.btpsd.commands.InvoiceMainItemCommand;
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

    @Autowired
    private final ServiceNumberRepository serviceNumberRepository;

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


    @PatchMapping("/mainitems/{salesQuotation}/{salesQuotationItem}/{pricingProcedureStep}/{pricingProcedureCounter}/{customerNumber}")
    public InvoiceMainItemCommand updateMainItemCommand(
            @RequestBody InvoiceMainItemCommand updatedInvoiceMainItemCommand,
            @PathVariable String salesQuotation,
            @PathVariable String salesQuotationItem,
            @PathVariable Integer pricingProcedureStep,
            @PathVariable Integer pricingProcedureCounter,
            @PathVariable String customerNumber) throws Exception {

        // Step 1: Fetch product and product description from ProductCloudController
        String productApiResponse = productCloudController.getAllProducts().toString();
        String productDescApiResponse = productCloudController.getAllProductsDesc().toString();

        // Step 2: Extract fields from the product API response
        ObjectMapper objectMapper = new ObjectMapper();
        try {
            JsonNode productJson = objectMapper.readTree(productApiResponse);
            JsonNode productDescJson = objectMapper.readTree(productDescApiResponse);

            JsonNode productsArray = productJson.path("d").path("results");
            JsonNode productDescriptionsArray = productDescJson.path("d").path("results");

            for (int i = 0; i < productsArray.size(); i++) {
//                JsonNode product = productsArray.get(i);
//                JsonNode productDesc = productDescriptionsArray.get(i);
//
//                String serviceNumberCode = product.path("Product").asText();
//                String unitOfMeasurementCode = product.path("BaseUnit").asText();
//                String description = productDesc.path("ProductDescription").asText();
//
//                // Step 3: Check if serviceNumberCode exists in the ServiceNumber table
//                Optional<ServiceNumber> existingServiceNumber = serviceNumberRepository.findByServiceNumberCode(Long.valueOf(serviceNumberCode));
//
//                ServiceNumber serviceNumber;
//                if (!existingServiceNumber.isPresent()) {
//                    // Step 4: Create and save new ServiceNumber if not exists
//                    serviceNumber = new ServiceNumber();
//                    serviceNumber.setServiceNumberCode(Long.valueOf(serviceNumberCode));
//                    serviceNumber = serviceNumberRepository.save(serviceNumber);
//                } else {
//                    serviceNumber = existingServiceNumber.get();
//                }
//
//                // Step 5: Set the extracted values to the InvoiceMainItemCommand
//                updatedInvoiceMainItemCommand.setServiceNumberCode(serviceNumber.getServiceNumberCode());
//                updatedInvoiceMainItemCommand.setUnitOfMeasurementCode(unitOfMeasurementCode);
//                updatedInvoiceMainItemCommand.setDescription(description);
//
//                // Step 9: Fetch currency from business partner's sales area based on customer number
//                String businessPartnerApiResponse = businessPartnerCloudController.getBusinessPartnerSalesArea(customerNumber).toString();
//                JsonNode businessPartnerJson = objectMapper.readTree(businessPartnerApiResponse);
//
//                JsonNode salesAreaArray = businessPartnerJson.path("d").path("results");
//                if (salesAreaArray.size() > 0) {
//                    String currency = salesAreaArray.get(0).path("Currency").asText();
//                    updatedInvoiceMainItemCommand.setCurrencyCode(currency);
//                } else {
//                    throw new RuntimeException("Currency not found for customer number: " + customerNumber);
//                }


                // Step 6: Save the updated InvoiceMainItem
                InvoiceMainItemCommand savedCommand = invoiceMainItemService.saveMainItemCommand(updatedInvoiceMainItemCommand);
                if (savedCommand == null) {
                    throw new RuntimeException("Failed to update Invoice Main Item.");
                }

                // Step 7: Extract the totalHeader from the saved Main Item
                Double totalHeader = savedCommand.getTotalHeader();

                // Step 8: Convert totalHeader to string and call the Sales Quotation Pricing API
                try {
//                    String conditionRateAmount = String.valueOf(totalHeader); // Convert totalHeader to a string

                    // Call the Sales Quotation Pricing API
                    invoiceMainItemService.callInvoicePricingAPI(
                            salesQuotation, salesQuotationItem, pricingProcedureStep, pricingProcedureCounter, totalHeader);
                } catch (Exception e) {
                    log.error("Error while calling Sales Quotation Pricing API: " + e.getMessage(), e);
                    throw new RuntimeException("Failed to update Invoice Pricing Element. Response Code: " + e.getMessage());
                }


                return savedCommand;
            }

        } catch (JsonProcessingException e) {
            throw new RuntimeException("Error processing product API response", e);
        }

        return updatedInvoiceMainItemCommand;
    }

    @DeleteMapping("/mainitems/{mainItemCode}")
    void deleteMainItemCommand(@PathVariable Long mainItemCode) {
        invoiceMainItemService.deleteById(mainItemCode);
    }

//    @PatchMapping
//    @RequestMapping("/mainitems/{mainItemCode}")
//    @Transactional
//    InvoiceMainItemCommand updateMainItemCommand(@RequestBody InvoiceMainItemCommand newInvoiceMainItemCommand, @PathVariable Long mainItemCode) {
//
//        InvoiceMainItemCommand command = invoiceMainItemToInvoiceMainItemCommand.convert(invoiceMainItemService.updateMainItem(newInvoiceMainItemCommand, mainItemCode));
//        return command;
//    }

    @RequestMapping(method = RequestMethod.GET, value = "/mainitems/search")
    @ResponseBody
    public List<InvoiceMainItem> Search(@RequestParam String keyword) {

        return invoiceMainItemRepository.search(keyword);
    }
}
