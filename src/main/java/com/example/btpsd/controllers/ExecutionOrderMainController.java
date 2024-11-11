package com.example.btpsd.controllers;

import com.example.btpsd.commands.ExecutionOrderMainCommand;
import com.example.btpsd.commands.InvoiceMainItemCommand;
import com.example.btpsd.converters.ExecutionOrderMainToExecutionOrderMainCommand;
import com.example.btpsd.model.ExecutionOrderMain;
import com.example.btpsd.model.InvoiceMainItem;
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
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

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

    @GetMapping("/executionordermain/all")
    Set<ExecutionOrderMainCommand> all() {
        return executionOrderMainService.getExecutionOrderMainCommands();
    }

    @GetMapping("/executionordermain/id")
    public Optional<ExecutionOrderMainCommand> getExecutionOrderMainById(@RequestParam Long executionOrderMainCode) {
        return Optional.ofNullable(executionOrderMainService.findExecutionOrderMainCommandById(executionOrderMainCode));
    }

    @GetMapping("/executionordermain/{salesOrder}/{salesOrderItem}")
    public StringBuilder findBySalesOrderAndItem(
            @PathVariable("salesOrder") String salesOrder,
            @PathVariable("salesOrderItem") String salesOrderItem) {

        // Use the method to fetch Sales Order Item based on path variables
        return salesOrderCloudController.getSalesOrderItem(salesOrder, salesOrderItem);
    }

    @GetMapping("/executionordermain/referenceid")
    public ResponseEntity<List<ExecutionOrderMainCommand>> getInvoiceMainItemsByReferenceId(@RequestParam String referenceId) {
        List<ExecutionOrderMain> executionOrderMain = executionOrderMainRepository.findByReferenceId(referenceId);

        if (executionOrderMain.isEmpty()) {
            return ResponseEntity.notFound().build(); // Return 404 if no items found
        }

        List<ExecutionOrderMainCommand> responseItems = executionOrderMain.stream()
                .map(executionOrderMainToExecutionOrderMainCommand::convert)
                .collect(Collectors.toList());

        return ResponseEntity.ok(responseItems);
    }

    @PostMapping("/executionordermain/{salesOrder}/{salesOrderItem}/{customerNumber}")
    public ExecutionOrderMainCommand newExecutionOrderCommand(
            @RequestBody ExecutionOrderMainCommand newExecutionOrderCommand,
            @PathVariable String salesOrder,
            @PathVariable String salesOrderItem,
            @PathVariable String customerNumber) throws Exception {

        // Step 1: Set the reference ID to the sales order number
        newExecutionOrderCommand.setReferenceId(salesOrder);

        // Step 2: Fetch sales order details using SalesOrderCloudController
        String salesOrderApiResponse = salesOrderCloudController.getAllSalesOrders().toString();
        ObjectMapper objectMapper = new ObjectMapper();

        try {
            // Parse the API response
            JsonNode salesOrderJson = objectMapper.readTree(salesOrderApiResponse);
            JsonNode salesOrderArray = salesOrderJson.path("d").path("results");

            // Step 3: Find the matching sales order and extract ReferenceSDDocument
            for (JsonNode salesOrderNode : salesOrderArray) {
                String salesOrderId = salesOrderNode.path("SalesOrder").asText();
                if (salesOrderId.equals(salesOrder)) {
                    String referenceSDDocument = salesOrderNode.path("ReferenceSDDocument").asText();
                    newExecutionOrderCommand.setReferenceSDDocument(referenceSDDocument);
                    break;
                }
            }

        } catch (JsonProcessingException e) {
            throw new RuntimeException("Error processing sales order API response", e);
        }

        // Step 4: Save the ExecutionOrderMain
        ExecutionOrderMainCommand savedCommand = executionOrderMainService.saveExecutionOrderMainCommand(newExecutionOrderCommand);

        if (savedCommand == null) {
            throw new RuntimeException("Failed to save Execution Order.");
        }

        // Step 5: Extract the totalHeader from the saved Main Item
        Double totalHeader = savedCommand.getTotalHeader();

        // Step 6: Call the Sales Order Pricing API with salesOrder and salesOrderItem from the URL
        try {
            executionOrderMainService.callSalesOrderPricingAPI(salesOrder, salesOrderItem, totalHeader);
        } catch (Exception e) {
            throw new RuntimeException("Error while calling Sales Order Pricing API: " + e.getMessage());
        }

        savedCommand = executionOrderMainService.saveExecutionOrderMainCommand(newExecutionOrderCommand);
        if (savedCommand == null) {
            throw new RuntimeException("Failed to save Execution Order.");
        }
        return ResponseEntity.status(HttpStatus.CREATED).body(savedCommand).getBody();

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
