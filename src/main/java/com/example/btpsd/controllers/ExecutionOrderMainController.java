package com.example.btpsd.controllers;

import com.example.btpsd.commands.ExecutionOrderMainCommand;
import com.example.btpsd.commands.InvoiceMainItemCommand;
import com.example.btpsd.converters.ExecutionOrderMainToExecutionOrderMainCommand;
import com.example.btpsd.model.ExecutionOrderMain;
import com.example.btpsd.model.ServiceInvoiceMain;
import com.example.btpsd.model.ServiceNumber;
import com.example.btpsd.repositories.ExecutionOrderMainRepository;
import com.example.btpsd.repositories.ServiceNumberRepository;
import com.example.btpsd.services.ExecutionOrderMainService;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.validation.constraints.NotNull;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;
import java.util.Set;

@RequiredArgsConstructor
@RestController
public class
ExecutionOrderMainController {

    private final ExecutionOrderMainRepository executionOrderMainRepository;

    private final ExecutionOrderMainService executionOrderMainService;

    private final ProductCloudController productCloudController;

    private final BusinessPartnerCloudController businessPartnerCloudController;

    private final SalesOrderCloudController salesOrderCloudController;

    @Autowired
    private final ServiceNumberRepository serviceNumberRepository;

    private final ExecutionOrderMainToExecutionOrderMainCommand executionOrderMainToExecutionOrderMainCommand;

    @GetMapping("/executionordermain")
    Set<ExecutionOrderMainCommand> all() {
        return executionOrderMainService.getExecutionOrderMainCommands();
    }

    @GetMapping("/executionordermain/{salesOrder}/{salesOrderItem}")
    public StringBuilder findBySalesOrderAndItem(
            @PathVariable("salesOrder") String salesOrder,
            @PathVariable("salesOrderItem") String salesOrderItem) {

        // Use the method to fetch Sales Order Item based on path variables
        return salesOrderCloudController.getSalesOrderItem(salesOrder, salesOrderItem);
    }


    @PostMapping("/executionordermain/{salesOrder}/{salesOrderItem}/{customerNumber}")
    public ExecutionOrderMainCommand newExecutionOrderCommand(
            @RequestBody ExecutionOrderMainCommand newExecutionOrderCommand,
            @PathVariable String salesOrder,
            @PathVariable String salesOrderItem,
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
                JsonNode product = productsArray.get(i);
                JsonNode productDesc = productDescriptionsArray.get(i);

                String serviceNumberCode = product.path("Product").asText();
                String unitOfMeasurementCode = product.path("BaseUnit").asText();
                String description = productDesc.path("ProductDescription").asText();
                String materialGroup = product.path("MaterialGroup").asText();  // Assuming material group exists

                // Step 3: Check if serviceNumberCode exists in the ServiceNumber table
                Optional<ServiceNumber> existingServiceNumber = serviceNumberRepository.findByServiceNumberCode(Long.valueOf(serviceNumberCode));

                ServiceNumber serviceNumber;
                if (!existingServiceNumber.isPresent()) {
                    // Step 4: Create and save new ServiceNumber if it doesn't exist
                    serviceNumber = new ServiceNumber();
                    serviceNumber.setServiceNumberCode(Long.valueOf(serviceNumberCode));
                    serviceNumber = serviceNumberRepository.save(serviceNumber);
                } else {
                    serviceNumber = existingServiceNumber.get();
                }
                // Step 9: Fetch currency from business partner's sales area based on customer number
                String businessPartnerApiResponse = businessPartnerCloudController.getBusinessPartnerSalesArea(customerNumber).toString();
                JsonNode businessPartnerJson = objectMapper.readTree(businessPartnerApiResponse);

                JsonNode salesAreaArray = businessPartnerJson.path("d").path("results");
                if (salesAreaArray.size() > 0) {
                    String currency = salesAreaArray.get(0).path("Currency").asText();
                    newExecutionOrderCommand.setCurrencyCode(currency);
                } else {
                    throw new RuntimeException("Currency not found for customer number: " + customerNumber);
                }


                // Step 5: Set the extracted values to the ExecutionOrderMainCommand
                newExecutionOrderCommand.setServiceNumberCode(serviceNumber.getServiceNumberCode());
                newExecutionOrderCommand.setUnitOfMeasurementCode(unitOfMeasurementCode);
                newExecutionOrderCommand.setDescription(description);
                newExecutionOrderCommand.setMaterialGroupCode(materialGroup);  // Assuming the field exists

                // Step 6: Save the ExecutionOrderMain
                ExecutionOrderMainCommand savedCommand = executionOrderMainService.saveExecutionOrderMainCommand(newExecutionOrderCommand);

                if (savedCommand == null) {
                    throw new RuntimeException("Failed to save Execution Order.");
                }

                // Step 7: Extract the totalHeader from the saved Main Item
                Double totalHeader = savedCommand.getTotalHeader();

                // Step 8: Call the Sales Order Pricing API with salesOrder and salesOrderItem from the URL
                try {
                    executionOrderMainService.callSalesOrderPricingAPI(salesOrder, salesOrderItem, totalHeader);
                } catch (Exception e) {
                    throw new RuntimeException("Error while calling Sales Order Pricing API: " + e.getMessage());
                }

                return savedCommand;
            }

        } catch (JsonProcessingException e) {
            throw new RuntimeException("Error processing product API response", e);
        }

        return newExecutionOrderCommand;
    }
    @DeleteMapping("/executionordermain/{executionOrderMainCode}")
    void deleteExecutionOrderMainItemCommand(@PathVariable Long executionOrderMainCode) {
        executionOrderMainService.deleteById(executionOrderMainCode);
    }

    @PatchMapping
    @RequestMapping("/executionordermain/{executionOrderMainCode}")
    @Transactional
    ExecutionOrderMainCommand updateExecutionOrderMainCommand(@RequestBody ExecutionOrderMainCommand newExecutionOrderMainItemCommand, @PathVariable Long executionOrderMainCode) {

        ExecutionOrderMainCommand command = executionOrderMainToExecutionOrderMainCommand.convert(executionOrderMainService.updateExecutionOrderMain(newExecutionOrderMainItemCommand, executionOrderMainCode));
        return command;
    }

    @RequestMapping(method = RequestMethod.GET, value = "/executionordermain/linenumber")
    @ResponseBody
    public List<ExecutionOrderMain> findByLineNumber(@RequestParam String lineNumber) {

        return executionOrderMainRepository.findByLineNumber(lineNumber);
    }
}
