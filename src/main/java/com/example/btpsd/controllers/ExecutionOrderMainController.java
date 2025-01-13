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

        // Step 1: Delete all existing ExecutionOrderMain records
        executionOrderMainRepository.deleteAll();

        List<ExecutionOrderMain> savedOrders = new ArrayList<>();

        // Step 2: Set reference ID and fetch Sales Order details
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

        // Step 3: Save each execution order and generate unique ID
        for (ExecutionOrderMainCommand command : executionOrderCommands) {
            ExecutionOrderMain executionOrder = executionOrderMainCommandToExecutionOrderMain.convert(command);

            // Save each execution order individually
            savedOrders.add(executionOrderMainRepository.save(executionOrder));
        }

        // Step 4: Calculate totalHeader
        Double totalHeader = executionOrderMainService.getTotalHeader(); // Ensure this method calculates correctly
        for (ExecutionOrderMain savedOrder : savedOrders) {
            savedOrder.setTotalHeader(totalHeader);
            executionOrderMainRepository.save(savedOrder); // Re-save after updating totalHeader
        }

        // Step 5: Call Sales Order Pricing API
        try {
            executionOrderMainService.callSalesOrderPricingAPI(salesOrder, salesOrderItem, totalHeader);
        } catch (Exception e) {
            throw new RuntimeException("Failed to update Sales Order Pricing Element. Response Code: " + e.getMessage());
        }

        // Step 6: Convert and return the saved orders as a list of command objects for the response
        List<ExecutionOrderMainCommand> response = new ArrayList<>();
        for (ExecutionOrderMain savedOrder : savedOrders) {
            response.add(executionOrderMainToExecutionOrderMainCommand.convert(savedOrder));
        }

        return response;
    }


    @GetMapping("/executionordermain/{salesOrder}/{salesOrderItem}")
    public StringBuilder findBySalesOrderAndItem(
            @PathVariable("salesOrder") String salesOrder,
            @PathVariable("salesOrderItem") String salesOrderItem) {

        return salesOrderCloudController.getSalesOrderItem(salesOrder, salesOrderItem);
    }

    @GetMapping("/executionordermain/{salesOrder}")
    public StringBuilder findItemsBySalesOrder(@PathVariable("salesOrder") String salesOrder) {
        return salesOrderCloudController.getSalesOrderItemsBySalesOrder(salesOrder);
    }

    @GetMapping("/executionordermain/referenceid")
    public ResponseEntity<List<ExecutionOrderMainCommand>> getExecutionOrderMainByReferenceId(@RequestParam String referenceId) throws Exception {
        // Fetch all ExecutionOrderMain items with the given salesOrderID (referenceId)
        List<ExecutionOrderMain> executionOrderMainList = executionOrderMainRepository.findByReferenceId(referenceId);

        // Check if the list is empty and return 404 if no items are found
        if (executionOrderMainList.isEmpty()) {
            return ResponseEntity.notFound().build(); // Return 404 if no items found
        }

        // SalesOrderItemNumber is fixed as "10"
//        String salesOrderItemNumber = "10";

        // Fetch Sales Order Item text from the S4 API for the given salesOrderID
        StringBuilder response = salesOrderCloudController.getSalesOrderItems(referenceId);
        ObjectMapper objectMapper = new ObjectMapper();
        JsonNode responseJson = objectMapper.readTree(response.toString());

        // Find the matching node for SalesOrderItemNumber "10"
        JsonNode matchingNode = findMatchingNode(responseJson);

        // If the matching node is found, update all ExecutionOrderMain records
        if (matchingNode != null) {
            String itemText = matchingNode.path("SalesOrderItemText").asText();

            for (ExecutionOrderMain item : executionOrderMainList) {
                // Update the salesOrderItemText field
                item.setSalesOrderItemText(itemText);

                // Optionally save the updated item in the database
                executionOrderMainRepository.save(item);
            }
        }

        // Convert the list of ExecutionOrderMain to ExecutionOrderMainCommand for the response
        List<ExecutionOrderMainCommand> responseItems = executionOrderMainList.stream()
                .map(executionOrderMainToExecutionOrderMainCommand::convert)
                .collect(Collectors.toList());

        return ResponseEntity.ok(responseItems);
    }

    private JsonNode findMatchingNode(JsonNode responseJson) {
        // Search for the specific SalesOrderItemNumber in the API response
        for (JsonNode node : responseJson.path("d").path("results")) {
                return node;
        }
        return null;
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
