package com.example.btpsd.controllers;

import com.example.btpsd.commands.ServiceInvoiceMainCommand;
import com.example.btpsd.converters.ServiceInvoiceCommandToServiceInvoice;
import com.example.btpsd.converters.ServiceInvoiceToServiceInvoiceCommand;
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
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;

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
    public ResponseEntity<List<ServiceInvoiceMainCommand>> getInvoiceMainItemsByReferenceId(@RequestParam String referenceId) {
        // Fetch all ServiceInvoiceMain items with the given referenceId
        List<ServiceInvoiceMain> serviceInvoiceMains = serviceInvoiceMainRepository.findByReferenceId(referenceId);

        // Check if the list is empty and return 404 if no items are found
        if (serviceInvoiceMains.isEmpty()) {
            return ResponseEntity.notFound().build(); // Return 404 if no items found
        }

        // Convert the list of ServiceInvoiceMain to ServiceInvoiceMainCommand for the response
        List<ServiceInvoiceMainCommand> responseItems = serviceInvoiceMains.stream()
                .map(serviceInvoiceToServiceInvoiceCommand::convert)
                .collect(Collectors.toList());

        return ResponseEntity.ok(responseItems);
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



//    @PostMapping("/serviceinvoice")
//    ServiceInvoiceMainCommand newServiceInvoiceCommand(@RequestBody ServiceInvoiceMainCommand newServiceInvoiceCommand) {
//
//        ServiceInvoiceMainCommand savedCommand = serviceInvoiceMainService.saveServiceInvoiceMainCommand(newServiceInvoiceCommand);
//        return savedCommand;
//
//    }


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

        // Step 1: If `debitMemoRequest` is provided, fetch details once for all commands
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

        // Step 2: Process each ServiceInvoiceMainCommand individually
        for (ServiceInvoiceMainCommand command : serviceInvoiceMainCommands) {
            ExecutionOrderMain executionOrderMain = executionOrderMainRepository
                    .findById(command.getExecutionOrderMainCode())
                    .orElseThrow(() -> new EntityNotFoundException(
                            "ExecutionOrderMain not found with code: " + command.getExecutionOrderMainCode()));

            command.setExecutionOrderMain(executionOrderMain);
            command.setReferenceId(debitMemoRequest);
            command.setReferenceSDDocument(referenceSDDocument);  // Set fetched ReferenceSDDocument

            ServiceInvoiceMain savedServiceInvoice;
            if (command.getServiceInvoiceCode() == null) {
                // Create new ServiceInvoiceMain
                savedServiceInvoice = serviceInvoiceCommandToServiceInvoice.convert(command);
                savedServiceInvoice = serviceInvoiceMainRepository.save(savedServiceInvoice);
            } else {
                // Update existing ServiceInvoiceMain
                savedServiceInvoice = serviceInvoiceCommandToServiceInvoice.convert(command);
                savedServiceInvoice = serviceInvoiceMainService.updateServiceInvoiceMain(
                        savedServiceInvoice, command.getServiceInvoiceCode());
            }

            // Step 3: Calculate total header and apply it to the saved invoice
            Double totalHeader = serviceInvoiceMainService.getTotalHeader();
            savedServiceInvoice.setTotalHeader(totalHeader);
            savedServiceInvoice = serviceInvoiceMainRepository.save(savedServiceInvoice);  // Save with updated total header

            // Step 4: Call Debit Memo Pricing API with total header
            try {
                serviceInvoiceMainService.callDebitMemoPricingAPI(
                        debitMemoRequest, debitMemoRequestItem, pricingProcedureStep, pricingProcedureCounter, totalHeader);
            } catch (Exception e) {
                log.error("Error while calling Debit Memo Pricing API: " + e.getMessage(), e);
                throw new RuntimeException("Failed to update Invoice Pricing Element. Response Code: " + e.getMessage());
            }

            // Step 5: Convert back to command object and add to response list
            savedCommands.add(serviceInvoiceToServiceInvoiceCommand.convert(savedServiceInvoice));
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


    @PatchMapping("/serviceinvoice/{debitMemoRequest}/{debitMemoRequestItem}/{pricingProcedureStep}/{pricingProcedureCounter}/{customerNumber}")
    public ServiceInvoiceMainCommand updateServiceInvoiceCommand(
            @RequestBody ServiceInvoiceMainCommand updatedServiceInvoiceMainCommand,
            @PathVariable String debitMemoRequest,
            @PathVariable String debitMemoRequestItem,
            @PathVariable Integer pricingProcedureStep,
            @PathVariable Integer pricingProcedureCounter,
            @PathVariable String customerNumber) throws Exception {

        // Step 1: Set the referenceId for the command
        updatedServiceInvoiceMainCommand.setReferenceId(debitMemoRequest);

        // Step 2: Fetch the Debit Memo details from SalesOrderCloudController
        String debitMemoApiResponse = salesOrderCloudController.getDebitMemo().toString();
        ObjectMapper objectMapper = new ObjectMapper();

        try {
            // Parse the API response to extract the array of debit memos
            JsonNode responseJson = objectMapper.readTree(debitMemoApiResponse);
            JsonNode debitMemoResults = responseJson.path("d").path("results");

            // Loop through the results to find the relevant debit memo
            for (JsonNode memo : debitMemoResults) {
                String memoID = memo.path("DebitMemoRequest").asText(); // Adjust the field name as per your API response
                if (memoID.equals(debitMemoRequest)) {
                    // Extract ReferenceSDDocument if the DebitMemoRequest matches
                    String referenceSDDocument = memo.path("ReferenceSDDocument").asText();
                    updatedServiceInvoiceMainCommand.setReferenceSDDocument(referenceSDDocument);
                    break; // Exit the loop once we've found the correct memo
                }
            }

        } catch (JsonProcessingException e) {
            throw new RuntimeException("Error processing Debit Memo API response", e);
        }

        // Step 4: Check if a ServiceInvoiceMain with the same referenceId exists
        List<ServiceInvoiceMain> optionalServiceInvoiceMain = serviceInvoiceMainRepository.findByReferenceId(updatedServiceInvoiceMainCommand.getReferenceId());
        ServiceInvoiceMain savedServiceInvoice;

        if (!optionalServiceInvoiceMain.isEmpty()) {
            // Update existing ServiceInvoiceMain using the update method
            savedServiceInvoice = serviceInvoiceCommandToServiceInvoice.convert(updatedServiceInvoiceMainCommand);
            savedServiceInvoice = serviceInvoiceMainService.updateServiceInvoiceMain(savedServiceInvoice, optionalServiceInvoiceMain.get(0).getServiceInvoiceCode());
        } else {
            // Create a new ServiceInvoiceMain
            savedServiceInvoice = serviceInvoiceCommandToServiceInvoice.convert(updatedServiceInvoiceMainCommand);
            savedServiceInvoice = serviceInvoiceMainRepository.save(savedServiceInvoice);
        }

        // Step 5: Calculate the totalHeader
        Double totalHeader = serviceInvoiceMainService.getTotalHeader();
        savedServiceInvoice.setTotalHeader(totalHeader);
        serviceInvoiceMainRepository.save(savedServiceInvoice);

        // Step 6: Call the Debit Memo Pricing API with the updated total header
        try {
            serviceInvoiceMainService.callDebitMemoPricingAPI(
                    debitMemoRequest, debitMemoRequestItem, pricingProcedureStep, pricingProcedureCounter, totalHeader);
        } catch (Exception e) {
            log.error("Error while calling Debit Memo Pricing API: " + e.getMessage(), e);
            throw new RuntimeException("Failed to update Invoice Pricing Element. Response Code: " + e.getMessage());
        }

        // Convert back to command for return
        return serviceInvoiceToServiceInvoiceCommand.convert(savedServiceInvoice);
    }


    @RequestMapping(method = RequestMethod.GET, value = "/serviceinvoice/linenumber")
    @ResponseBody
    public List<ServiceInvoiceMain> findByLineNumber(@RequestParam String lineNumber) {

        return serviceInvoiceMainRepository.findByLineNumber(lineNumber);
    }

}
