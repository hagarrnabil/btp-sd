package com.example.btpsd.controllers;

import com.example.btpsd.commands.ExecutionOrderMainCommand;
import com.example.btpsd.commands.InvoiceMainItemCommand;
import com.example.btpsd.converters.ExecutionOrderMainCommandToExecutionOrderMain;
import com.example.btpsd.converters.ExecutionOrderMainToExecutionOrderMainCommand;
import com.example.btpsd.model.ExecutionOrderMain;
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

import java.util.ArrayList;
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

    private final ExecutionOrderMainCommandToExecutionOrderMain executionOrderMainCommandToExecutionOrderMain;

    @GetMapping("/executionordermain/all")
    Set<ExecutionOrderMainCommand> all() {
        return executionOrderMainService.getExecutionOrderMainCommands();
    }

    @GetMapping("/executionordermain/id")
    public Optional<ExecutionOrderMainCommand> getExecutionOrderMainById(@RequestParam Long executionOrderMainCode) {
        return Optional.ofNullable(executionOrderMainService.findExecutionOrderMainCommandById(executionOrderMainCode));
    }

    @PostMapping("/executionordermain")
    public List<ExecutionOrderMainCommand> saveOrUpdateExecutionOrders(
            @RequestBody List<ExecutionOrderMainCommand> executionOrderCommands,
            @RequestParam(required = false) String salesOrder,
            @RequestParam(required = false) String salesOrderItem,
            @RequestParam(required = false) String customerNumber) throws Exception {

        List<ExecutionOrderMain> savedExecutionOrders = new ArrayList<>();

        // Step 1: If salesOrder is provided, set it as the reference ID for each execution order
        if (salesOrder != null) {
            for (ExecutionOrderMainCommand command : executionOrderCommands) {
                command.setReferenceId(salesOrder);

                // Fetch Sales Order details and set ReferenceSDDocument
                String salesOrderApiResponse = salesOrderCloudController.getAllSalesOrders().toString();
                ObjectMapper objectMapper = new ObjectMapper();

                try {
                    JsonNode responseJson = objectMapper.readTree(salesOrderApiResponse);
                    JsonNode salesOrderResults = responseJson.path("d").path("results");

                    for (JsonNode order : salesOrderResults) {
                        String orderID = order.path("SalesOrder").asText();
                        if (orderID.equals(salesOrder)) {
                            String referenceSDDocument = order.path("ReferenceSDDocument").asText();
                            command.setReferenceSDDocument(referenceSDDocument);
                            break; // Exit loop once ReferenceSDDocument is found
                        }
                    }
                } catch (JsonProcessingException e) {
                    throw new RuntimeException("Error processing Sales Order API response", e);
                }
            }
        }

        // Step 2: Save each execution order and ensure they all get unique IDs
        for (ExecutionOrderMainCommand command : executionOrderCommands) {
            ExecutionOrderMain executionOrder = executionOrderMainCommandToExecutionOrderMain.convert(command);

            // Save each execution order individually, which will trigger the auto-generation of unique IDs
            savedExecutionOrders.add(executionOrderMainRepository.save(executionOrder));
        }

        // Step 3: Calculate totalHeader and update each saved execution order
        Double totalHeader = executionOrderMainService.getTotalHeader();
        for (ExecutionOrderMain savedOrder : savedExecutionOrders) {
            savedOrder.setTotalHeader(totalHeader);
            executionOrderMainRepository.save(savedOrder); // Re-save after updating totalHeader
        }

        // Step 4: Call Sales Order Pricing API for each saved execution order
        try {
            for (ExecutionOrderMain savedOrder : savedExecutionOrders) {
                executionOrderMainService.callSalesOrderPricingAPI(
                        savedOrder.getReferenceId(), salesOrderItem, savedOrder.getTotalHeader());
            }
        } catch (Exception e) {
            throw new RuntimeException("Failed to update Sales Order Pricing. Response Code: " + e.getMessage());
        }

        // Step 5: Convert and return the saved execution orders as a list of command objects
        List<ExecutionOrderMainCommand> response = new ArrayList<>();
        for (ExecutionOrderMain savedOrder : savedExecutionOrders) {
            response.add(executionOrderMainToExecutionOrderMainCommand.convert(savedOrder));
        }

        return response;
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
