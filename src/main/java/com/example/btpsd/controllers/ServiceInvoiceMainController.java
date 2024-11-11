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

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.Set;
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
    public ResponseEntity<Double> calculateTotal(@RequestBody ServiceInvoiceMainCommand serviceInvoiceMainCommand) {
        ServiceInvoiceMain serviceInvoiceMain = new ServiceInvoiceMain();
        serviceInvoiceMain.setQuantity(serviceInvoiceMainCommand.getQuantity());
        serviceInvoiceMain.setAmountPerUnit(serviceInvoiceMainCommand.getAmountPerUnit());
        Double total = serviceInvoiceMain.calculateTotal();
        return ResponseEntity.ok(total);
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

        for (ServiceInvoiceMainCommand serviceInvoiceMainCommand : serviceInvoiceMainCommands) {

            // Step 1: Fetch ExecutionOrderMain and validate its existence
            if (serviceInvoiceMainCommand.getExecutionOrderMainCode() != null) {
                ExecutionOrderMain executionOrderMain = executionOrderMainRepository
                        .findById(serviceInvoiceMainCommand.getExecutionOrderMainCode())
                        .orElseThrow(() -> new EntityNotFoundException("ExecutionOrderMain not found with code: "
                                + serviceInvoiceMainCommand.getExecutionOrderMainCode()));

                // Link ExecutionOrderMain to ServiceInvoiceMainCommand
                serviceInvoiceMainCommand.setExecutionOrderMain(executionOrderMain);
            } else {
                throw new RuntimeException("ExecutionOrderMainCode is required.");
            }

            // Step 2: Process debit memo if provided
            if (debitMemoRequest != null) {
                serviceInvoiceMainCommand.setReferenceId(debitMemoRequest);
                String serviceQuotationApiResponse = salesOrderCloudController.getDebitMemo().toString();
                ObjectMapper objectMapper = new ObjectMapper();

                try {
                    JsonNode responseJson = objectMapper.readTree(serviceQuotationApiResponse);
                    JsonNode serviceQuotationResults = responseJson.path("d").path("results");

                    for (JsonNode quotation : serviceQuotationResults) {
                        String quotationID = quotation.path("DebitMemoRequest").asText();
                        if (quotationID.equals(debitMemoRequest)) {
                            String referenceSDDocument = quotation.path("ReferenceSDDocument").asText();
                            serviceInvoiceMainCommand.setReferenceSDDocument(referenceSDDocument);
                            break;
                        }
                    }
                } catch (JsonProcessingException e) {
                    throw new RuntimeException("Error processing Service Debit Memo API response", e);
                }
            }

            // Step 3: Save or update ServiceInvoiceMain
            List<ServiceInvoiceMain> existingServiceInvoices = serviceInvoiceMainRepository
                    .findByReferenceId(serviceInvoiceMainCommand.getReferenceId());
            ServiceInvoiceMain savedServiceInvoiceMain;

            if (!existingServiceInvoices.isEmpty()) {
                // Update the existing ServiceInvoiceMain
                savedServiceInvoiceMain = serviceInvoiceCommandToServiceInvoice.convert(serviceInvoiceMainCommand);
                savedServiceInvoiceMain = serviceInvoiceMainService.updateServiceInvoiceMain(
                        savedServiceInvoiceMain, existingServiceInvoices.get(0).getServiceInvoiceCode());
            } else {
                // Save a new ServiceInvoiceMain
                savedServiceInvoiceMain = serviceInvoiceCommandToServiceInvoice.convert(serviceInvoiceMainCommand);
                savedServiceInvoiceMain = serviceInvoiceMainRepository.save(savedServiceInvoiceMain);
            }

            // Step 4: Calculate `totalHeader` and update the saved invoice
            Double totalHeader = serviceInvoiceMainService.getTotalHeader();
            savedServiceInvoiceMain.setTotalHeader(totalHeader);
            serviceInvoiceMainRepository.save(savedServiceInvoiceMain);

            // Step 5: Call the Service Quotation Pricing API if needed
            try {
                serviceInvoiceMainService.callDebitMemoPricingAPI(
                        debitMemoRequest, debitMemoRequestItem, pricingProcedureStep, pricingProcedureCounter, totalHeader);
            } catch (Exception e) {
                log.error("Error while calling Service Quotation Pricing API: " + e.getMessage(), e);
                throw new RuntimeException("Failed to update Service Invoice Pricing Element. Response Code: " + e.getMessage());
            }

            // Convert and add the saved ServiceInvoiceMain to the response list
            ServiceInvoiceMainCommand savedCommand = serviceInvoiceToServiceInvoiceCommand.convert(savedServiceInvoiceMain);
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
