package com.example.btpsd.controllers;

import com.example.btpsd.commands.ServiceInvoiceMainCommand;
import com.example.btpsd.converters.ServiceInvoiceCommandToServiceInvoice;
import com.example.btpsd.converters.ServiceInvoiceToServiceInvoiceCommand;
import com.example.btpsd.model.CalculatedQuantitiesResponse;
import com.example.btpsd.model.ExecutionOrderMain;
import com.example.btpsd.model.ServiceInvoiceMain;
import com.example.btpsd.repositories.ExecutionOrderMainRepository;
import com.example.btpsd.repositories.ServiceInvoiceMainRepository;
import com.example.btpsd.services.ServiceInvoiceMainService;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.*;
import java.util.stream.Collectors;

@RequiredArgsConstructor
@RestController
public class ServiceInvoiceMainController {

    Logger log = LogManager.getLogger(this.getClass());

    private final SalesOrderCloudController salesOrderCloudController;

    private final ServiceInvoiceMainRepository serviceInvoiceMainRepository;

    private final ExecutionOrderMainRepository executionOrderMainRepository;

    private final ServiceInvoiceMainService serviceInvoiceMainService;

    private final ServiceInvoiceToServiceInvoiceCommand serviceInvoiceToServiceInvoiceCommand;

    private final ServiceInvoiceCommandToServiceInvoice serviceInvoiceCommandToServiceInvoice;

    @GetMapping("/serviceinvoice/all")
    Set<ServiceInvoiceMainCommand> all() {
        return serviceInvoiceMainService.getServiceInvoiceMainCommands();
    }

    @GetMapping("/serviceinvoice/id")
    public Optional<ServiceInvoiceMainCommand> getSrvInvById(@RequestParam Long serviceInvoiceCode) {
        return Optional.ofNullable(serviceInvoiceMainService.findServiceInvoiceMainCommandById(serviceInvoiceCode));
    }

    @GetMapping("/serviceinvoice/{debitMemoRequest}/{debitMemoRequestItem}")
    public StringBuilder findByDebitMemoRequestAndItem(
            @PathVariable("debitMemoRequest") String debitMemoRequest,
            @PathVariable("debitMemoRequestItem") String debitMemoRequestItem) {

        // Call the method to fetch Debit Memo Request Item based on path variables
        return salesOrderCloudController.getDebitMemoRequestItem(debitMemoRequest, debitMemoRequestItem);
    }

    @GetMapping("/serviceinvoice/referenceid")
    public ResponseEntity<List<ServiceInvoiceMainCommand>> getServiceInvoiceItemsByReferenceId(@RequestParam String referenceId) throws Exception {
        // Fetch all ServiceInvoiceMain items with the given referenceId
        List<ServiceInvoiceMain> serviceInvoiceMains = serviceInvoiceMainRepository.findByReferenceId(referenceId);

        // Check if the list is empty and return 404 if no items are found
        if (serviceInvoiceMains.isEmpty()) {
            return ResponseEntity.notFound().build(); // Return 404 if no items found
        }

        // Fetch Debit Memo Item text from the S4 API for the given debit memo request (referenceId)
        StringBuilder response = salesOrderCloudController.getDebitMemoRequestItems(referenceId);
        ObjectMapper objectMapper = new ObjectMapper();
        JsonNode responseJson = objectMapper.readTree(response.toString());

        // DebitMemoRequestItemNumber is fixed as "10"
        String debitMemoItemNumber = "10";

        // Find the matching node for DebitMemoRequestItemNumber "10"
        JsonNode matchingNode = findMatchingNodeForDebitMemo(responseJson);

        // If the matching node is found, update all ServiceInvoiceMain records
        if (matchingNode != null) {
            String itemText = matchingNode.path("DebitMemoRequestItemText").asText();

            for (ServiceInvoiceMain item : serviceInvoiceMains) {
                // Update the debitMemoRequestItemText field
                item.setDebitMemoRequestItemText(itemText);

                // Optionally save the updated item in the database
                serviceInvoiceMainRepository.save(item);
            }
        }

        // Convert the list of ServiceInvoiceMain to ServiceInvoiceMainCommand for the response
        List<ServiceInvoiceMainCommand> responseItems = serviceInvoiceMains.stream()
                .map(serviceInvoiceToServiceInvoiceCommand::convert)
                .collect(Collectors.toList());

        return ResponseEntity.ok(responseItems);
    }

    private JsonNode findMatchingNodeForDebitMemo(JsonNode responseJson) {
        // Search for the specific DebitMemoRequestItemNumber in the API response
        for (JsonNode node : responseJson.path("d").path("results")) {
                return node;
        }
        return null;
    }


    @DeleteMapping("/serviceinvoice/{serviceInvoiceCode}")
    void deleteServiceInvoiceCommand(@PathVariable Long serviceInvoiceCode) {
        serviceInvoiceMainService.deleteById(serviceInvoiceCode);
    }

    @GetMapping("/totalheadersrv")
    public ResponseEntity<Double> calculateTotalHeader() {
        List<ServiceInvoiceMain> allItems = (List<ServiceInvoiceMain>) serviceInvoiceMainRepository.findAll();
        Double totalHeader = ServiceInvoiceMain.calculateTotalHeader(allItems);
        return ResponseEntity.ok(totalHeader);
    }

    @PostMapping("/totalsrv")
    public ResponseEntity<Map<String, Double>> calculateTotal(@RequestBody ServiceInvoiceMainCommand serviceInvoiceMainCommand) {
        ServiceInvoiceMain serviceInvoiceMain = new ServiceInvoiceMain();
        serviceInvoiceMain.setQuantity(serviceInvoiceMainCommand.getQuantity());
        serviceInvoiceMain.setAmountPerUnit(serviceInvoiceMainCommand.getAmountPerUnit());

        // Get total and amount per unit from the calculation
        Map<String, Double> resultMap = serviceInvoiceMain.calculateTotal();

        return ResponseEntity.ok(resultMap);
    }


    @PostMapping("/quantities")
    @Transactional
    public ResponseEntity<?> calculateQuantities(@RequestBody ServiceInvoiceMainCommand command) {
        // Convert the command to an entity
        ServiceInvoiceMain newServiceInvoice = serviceInvoiceCommandToServiceInvoice.convert(command);

        // Fetch all existing service invoices for the provided executionOrderMainCode
        List<ServiceInvoiceMain> previousServiceInvoices = serviceInvoiceMainRepository
                .findByExecutionOrderMainCode(newServiceInvoice.getExecutionOrderMainCode());

        // Determine the latest actualQuantity from previous records
        int latestActualQuantity = previousServiceInvoices.stream()
                .mapToInt(ServiceInvoiceMain::getActualQuantity)
                .max()
                .orElse(0); // Default to 0 if no previous records exist

        // Fetch over-fulfillment logic parameters from the command
        boolean unlimitedOverFulfillment = command.getUnlimitedOverFulfillment(); // Assuming a getter method exists
        Integer overFulfillmentLimit = command.getOverFulfillmentPercentage();       // Assuming a getter method exists

        // Calculate the new actual quantity
        int newActualQuantity = latestActualQuantity + newServiceInvoice.getQuantity();

        // Validation logic
        if (!unlimitedOverFulfillment) {
            // If over-fulfillment is disabled, check limits
            if (overFulfillmentLimit != null && overFulfillmentLimit > 0) {
                int maxAllowedQuantity = newServiceInvoice.getTotalQuantity() + overFulfillmentLimit;
                if (newActualQuantity > maxAllowedQuantity) {
                    return ResponseEntity.badRequest()
                            .body("Error: Quantity exceeds the allowed over-fulfillment limit of " + overFulfillmentLimit);
                }
            } else {
                // No over-fulfillment allowed
                if (newActualQuantity > newServiceInvoice.getTotalQuantity()) {
                    return ResponseEntity.badRequest()
                            .body("Error: Quantity exceeds the total allowed quantity.");
                }
            }
        }

        // Recalculate actualQuantity after validation
        newServiceInvoice.setActualQuantity(newActualQuantity);

        // Calculate remaining quantity
        int remainingQuantity = newServiceInvoice.getTotalQuantity() - newServiceInvoice.getActualQuantity();
        newServiceInvoice.setRemainingQuantity(Math.max(remainingQuantity, 0)); // Ensure non-negative remaining quantity

        // Calculate the total for the current transaction
        double currentTotal = newServiceInvoice.getQuantity() * newServiceInvoice.getAmountPerUnit();
        newServiceInvoice.setTotal(currentTotal);

        // Update the total header by adding the current total to the latest total header
        double latestTotalHeader = previousServiceInvoices.stream()
                .mapToDouble(ServiceInvoiceMain::getTotalHeader)
                .max()
                .orElse(0.0); // Default to 0 if no previous records exist
        double totalHeader = latestTotalHeader + currentTotal;
        newServiceInvoice.setTotalHeader(totalHeader);

        // Calculate the actual percentage of total quantity fulfilled (no cap at 100%)
        double actualPercentage = ((double) newServiceInvoice.getActualQuantity() / newServiceInvoice.getTotalQuantity()) * 100;
        newServiceInvoice.setActualPercentage((int) actualPercentage); // Removed the cap

        // Save the new service invoice record
        serviceInvoiceMainRepository.save(newServiceInvoice);

        // Prepare the response
        CalculatedQuantitiesResponse response = new CalculatedQuantitiesResponse(
                newServiceInvoice.getActualQuantity(),   // actualQuantity
                newServiceInvoice.getRemainingQuantity(), // remainingQuantity
                newServiceInvoice.getTotal(),            // current total
                newServiceInvoice.getActualPercentage(), // actualPercentage
                newServiceInvoice.getTotalHeader()       // totalHeader
        );

        return ResponseEntity.ok(response);
    }


    @PostMapping("/calculatequantities")
    public ResponseEntity<?> calculateQuantitiesWithoutAccumulation(@RequestBody ServiceInvoiceMainCommand command) {
        // Initialize a temporary service invoice object for calculations
        ServiceInvoiceMain tempServiceInvoice = new ServiceInvoiceMain();
        tempServiceInvoice.setQuantity(command.getQuantity());
        tempServiceInvoice.setAmountPerUnit(command.getAmountPerUnit());
        tempServiceInvoice.setTotalQuantity(command.getTotalQuantity());

        // Calculate total (quantity * amount per unit)
        double total = command.getQuantity() * command.getAmountPerUnit();
        tempServiceInvoice.setTotal(total);

        // Set AQ as the currently entered quantity
        int actualQuantity = command.getQuantity();
        tempServiceInvoice.setActualQuantity(actualQuantity);

        // Calculate RQ (remaining quantity)
        int remainingQuantity = command.getTotalQuantity() - command.getQuantity();
        tempServiceInvoice.setRemainingQuantity(Math.max(remainingQuantity, 0)); // Ensure non-negative RQ

        // Calculate AP (percentage of AQ from TQ)
        double actualPercentage = (command.getTotalQuantity() > 0)
                ? ((double) command.getQuantity() / command.getTotalQuantity()) * 100
                : 0.0;
        tempServiceInvoice.setActualPercentage((int) actualPercentage);

        // Prepare the response
        CalculatedQuantitiesResponse response = new CalculatedQuantitiesResponse(
                tempServiceInvoice.getActualQuantity(),      // AQ
                tempServiceInvoice.getRemainingQuantity(),   // RQ
                tempServiceInvoice.getTotal(),              // Total
                tempServiceInvoice.getActualPercentage(),   // AP
                null // No totalHeader as accumulation is not required
        );

        return ResponseEntity.ok(response);
    }

    @PostMapping("/serviceinvoice")
    public List<ServiceInvoiceMainCommand> saveOrUpdateServiceInvoiceCommands(
            @RequestBody List<ServiceInvoiceMainCommand> serviceInvoiceMainCommands,
            @RequestParam(required = false) String debitMemoRequest,
            @RequestParam(required = false) String debitMemoRequestItem,
            @RequestParam(required = false) Integer pricingProcedureStep,
            @RequestParam(required = false) Integer pricingProcedureCounter,
            @RequestParam(required = false) String customerNumber) throws Exception {

        List<ServiceInvoiceMainCommand> savedCommands = new ArrayList<>();
        ObjectMapper objectMapper = new ObjectMapper();

        // Step 1: Fetch ReferenceSDDocument if debitMemoRequest is provided
        String referenceSDDocument = null;
        if (debitMemoRequest != null) {
            try {
                String salesQuotationApiResponse = salesOrderCloudController.getDebitMemo().toString();
                JsonNode responseJson = objectMapper.readTree(salesQuotationApiResponse);
                JsonNode salesQuotationResults = responseJson.path("d").path("results");

                for (JsonNode quotation : salesQuotationResults) {
                    String quotationID = quotation.path("DebitMemoRequest").asText();
                    if (quotationID.equals(debitMemoRequest)) {
                        referenceSDDocument = quotation.path("ReferenceSDDocument").asText();
                        break;
                    }
                }
            } catch (JsonProcessingException e) {
                throw new RuntimeException("Error processing Debit Memo API response", e);
            }
        }

        // Step 2: Process each ServiceInvoiceMainCommand
        for (ServiceInvoiceMainCommand command : serviceInvoiceMainCommands) {
            // Fetch ExecutionOrderMain by its code
            ExecutionOrderMain executionOrderMain = executionOrderMainRepository
                    .findById(command.getExecutionOrderMainCode())
                    .orElseThrow(() -> new EntityNotFoundException(
                            "ExecutionOrderMain not found with code: " + command.getExecutionOrderMainCode()));

            // Fetch all related invoices and calculate the current totalHeader
            List<ServiceInvoiceMain> relatedInvoices = serviceInvoiceMainRepository
                    .findByExecutionOrderMainCode(command.getExecutionOrderMainCode());

            double totalHeader = relatedInvoices.stream()
                    .mapToDouble(ServiceInvoiceMain::getTotal)
                    .sum();

            // Set totalHeader in the command
            command.setTotalHeader(totalHeader);

            // Set additional fields in the command
            command.setReferenceId(debitMemoRequest);
            command.setReferenceSDDocument(referenceSDDocument);

            // Save or update using the service layer
            ServiceInvoiceMainCommand savedCommand = serviceInvoiceMainService.saveServiceInvoiceMainCommand(command);

            // Update the totalHeader after saving
            totalHeader += savedCommand.getTotal();
            savedCommand.setTotalHeader(new BigDecimal(totalHeader).setScale(2, RoundingMode.HALF_UP).doubleValue());

            // Call Debit Memo Pricing API
            try {
                serviceInvoiceMainService.callDebitMemoPricingAPI(
                        debitMemoRequest, debitMemoRequestItem, pricingProcedureStep, pricingProcedureCounter,
                        savedCommand.getTotalHeader());
            } catch (Exception e) {
                log.error("Error while calling Debit Memo Pricing API: " + e.getMessage(), e);
                throw new RuntimeException("Failed to update Invoice Pricing Element. Response Code: " + e.getMessage());
            }

            // Add the saved command to the response list
            savedCommands.add(savedCommand);
        }

        return savedCommands;
    }


    @PatchMapping
    @RequestMapping("/serviceinvoice/{serviceInvoiceCode}")
    @Transactional
    ServiceInvoiceMainCommand updateServiceInvoiceCommand(@RequestBody ServiceInvoiceMain newServiceInvoiceCommand, @PathVariable Long serviceInvoiceCode) {

        ServiceInvoiceMainCommand command = serviceInvoiceToServiceInvoiceCommand.convert(serviceInvoiceMainService.updateServiceInvoiceMain(newServiceInvoiceCommand, serviceInvoiceCode));
        return command;
    }



    @RequestMapping(method = RequestMethod.GET, value = "/serviceinvoice/linenumber")
    @ResponseBody
    public List<ServiceInvoiceMain> findByLineNumber(@RequestParam String lineNumber) {

        return serviceInvoiceMainRepository.findByLineNumber(lineNumber);
    }

}
