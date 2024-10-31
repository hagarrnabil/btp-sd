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

    @GetMapping("/executionordermain/{referenceId}")
    public ResponseEntity<List<ExecutionOrderMainCommand>> getInvoiceMainItemsByReferenceId(@PathVariable String referenceId) {
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

        // Step 2: Check if a new ExecutionOrderMainCommand should be created
        ExecutionOrderMainCommand savedCommand = executionOrderMainService.saveExecutionOrderMainCommand(newExecutionOrderCommand);

        // Step 3: Ensure the command was saved successfully
        if (savedCommand == null) {
            throw new RuntimeException("Failed to save Execution Order.");
        }

        // Step 4: Extract the totalHeader from the saved Main Item
        Double totalHeader = savedCommand.getTotalHeader();

        // Step 5: Call the Sales Order Pricing API with salesOrder and salesOrderItem from the URL
        try {
            executionOrderMainService.callSalesOrderPricingAPI(salesOrder, salesOrderItem, totalHeader);
        } catch (Exception e) {
            throw new RuntimeException("Error while calling Sales Order Pricing API: " + e.getMessage());
        }

        // Return the saved command
        return savedCommand;
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
