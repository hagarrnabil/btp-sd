package com.example.btpsd.controllers;

import com.example.btpsd.commands.ExecutionOrderMainCommand;
import com.example.btpsd.commands.InvoiceMainItemCommand;
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

    @PostMapping("/executionordermain")
    public ExecutionOrderMainCommand newExecutionOrderCommand(
            @RequestBody ExecutionOrderMainCommand newCommand,
            @RequestParam(required = false) String salesOrder,
            @RequestParam(required = false) String salesOrderItem,
            @RequestParam(required = false) String customerNumber) throws Exception {

        // Step 1: Set the salesOrder as the reference ID for the new execution order
        newCommand.setReferenceId(salesOrder);

        // Step 2: Fetch Sales Order details and set ReferenceSDDocument if applicable
        String salesOrderApiResponse = salesOrderCloudController.getAllSalesOrders().toString();
        ObjectMapper objectMapper = new ObjectMapper();

        try {
            JsonNode responseJson = objectMapper.readTree(salesOrderApiResponse);
            JsonNode salesOrderResults = responseJson.path("d").path("results");

            for (JsonNode order : salesOrderResults) {
                String orderID = order.path("SalesOrder").asText();
                if (orderID.equals(salesOrder)) {
                    String referenceSDDocument = order.path("ReferenceSDDocument").asText();
                    newCommand.setReferenceSDDocument(referenceSDDocument);
                    break;  // Exit loop once ReferenceSDDocument is found
                }
            }
        } catch (JsonProcessingException e) {
            throw new RuntimeException("Error processing Sales Order API response", e);
        }

        // Step 3: Call Sales Order Pricing API to set Total Header in newCommand
        executionOrderMainService.callSalesOrderPricingAPI(salesOrder, salesOrderItem, newCommand.getTotalHeader());

        // Step 4: Save and return the new ExecutionOrderMainCommand once
        return executionOrderMainService.saveExecutionOrderMainCommand(newCommand);
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
