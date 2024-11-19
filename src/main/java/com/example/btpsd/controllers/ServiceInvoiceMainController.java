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

    private void calculateQuantities(ServiceInvoiceMain invoice) {
        // Fetch all service invoices with the same ExecutionOrderMain code
        List<ServiceInvoiceMain> previousServiceInvoices = serviceInvoiceMainRepository
                .findByExecutionOrderMainCode(invoice.getExecutionOrderMain().getExecutionOrderMainCode());

        // Calculate the accumulated actualQuantity (excluding the current invoice's quantity)
        int accumulatedActualQuantity = previousServiceInvoices.stream()
                .mapToInt(ServiceInvoiceMain::getActualQuantity)
                .sum();

        // Update actualQuantity to include the current invoice's quantity
        int newActualQuantity = accumulatedActualQuantity + invoice.getQuantity();
        invoice.setActualQuantity(newActualQuantity);

        // Calculate remaining quantity
        int remainingQuantity = invoice.getTotalQuantity() - newActualQuantity;
        invoice.setRemainingQuantity(remainingQuantity);

        // Calculate total (amountPerUnit * actualQuantity)
        double total = newActualQuantity * invoice.getAmountPerUnit();
        invoice.setTotal(new BigDecimal(total).setScale(2, RoundingMode.HALF_UP).doubleValue());

        // Calculate actual percentage
        double actualPercentage = (double) newActualQuantity / invoice.getTotalQuantity() * 100;
        invoice.setActualPercentage((int) new BigDecimal(actualPercentage).setScale(2, RoundingMode.HALF_UP).doubleValue());

        // Calculate totalHeader (sum of the total for all service invoices)
        double totalHeader = previousServiceInvoices.stream()
                .mapToDouble(ServiceInvoiceMain::getTotal)
                .sum() + invoice.getTotal();
        invoice.setTotalHeader(new BigDecimal(totalHeader).setScale(2, RoundingMode.HALF_UP).doubleValue());
    }

    @PostMapping("/quantities")
    public ResponseEntity<CalculatedQuantitiesResponse> calculateQuantities(@RequestBody ServiceInvoiceMainCommand command) {
        // Fetch the ExecutionOrderMain entity
        ExecutionOrderMain executionOrderMain = executionOrderMainRepository
                .findByExecutionOrderMainCode(command.getExecutionOrderMainCode())
                .orElseThrow(() -> new IllegalArgumentException("ExecutionOrderMain not found for code: " + command.getExecutionOrderMainCode()));

        // Fetch all service invoices with the same ExecutionOrderMain code
        List<ServiceInvoiceMain> previousServiceInvoices = serviceInvoiceMainRepository
                .findByExecutionOrderMainCode(executionOrderMain.getExecutionOrderMainCode());

        // Calculate accumulated actualQuantity from previous invoices (excluding the current one)
        int accumulatedActualQuantity = previousServiceInvoices.stream()
                .mapToInt(ServiceInvoiceMain::getActualQuantity)
                .sum();

        // Calculate remaining quantity
        int remainingQuantity = command.getTotalQuantity() - accumulatedActualQuantity - command.getQuantity();

        // Calculate actualQuantity (accumulated + current)
        int newActualQuantity = accumulatedActualQuantity + command.getQuantity();

        // Calculate total (amountPerUnit * actualQuantity)
        double total = newActualQuantity * command.getAmountPerUnit();

        // Calculate actual percentage
        double actualPercentage = (double) newActualQuantity / command.getTotalQuantity() * 100;

        // Calculate totalHeader (sum of the total for all service invoices)
        double totalHeader = previousServiceInvoices.stream()
                .mapToDouble(ServiceInvoiceMain::getTotal)
                .sum() + total;

        // Prepare response
        CalculatedQuantitiesResponse response = new CalculatedQuantitiesResponse(
                newActualQuantity,   // actualQuantity
                remainingQuantity,   // remainingQuantity
                total,               // total
                (int) new BigDecimal(actualPercentage).setScale(2, RoundingMode.HALF_UP).doubleValue(), // actualPercentage
                totalHeader          // totalHeader
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

        List<ServiceInvoiceMain> savedItems = new ArrayList<>();
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

        // Step 2: Save each new service invoice and ensure unique IDs
        for (ServiceInvoiceMainCommand command : serviceInvoiceMainCommands) {
            // Step 2.1: Retrieve ExecutionOrderMain by its code
            ExecutionOrderMain executionOrderMain = executionOrderMainRepository
                    .findById(command.getExecutionOrderMainCode())
                    .orElseThrow(() -> new EntityNotFoundException(
                            "ExecutionOrderMain not found with code: " + command.getExecutionOrderMainCode()));

            // Step 2.2: Set additional fields in the command
            command.setReferenceId(debitMemoRequest);
            command.setReferenceSDDocument(referenceSDDocument);

            // Step 2.3: Convert the command to entity and save
            ServiceInvoiceMain item = serviceInvoiceCommandToServiceInvoice.convert(command);
            item.setExecutionOrderMain(executionOrderMain);
            savedItems.add(serviceInvoiceMainRepository.save(item));
        }

        // Step 3: Calculate totalHeader and update each saved item
        Double totalHeader = serviceInvoiceMainService.getTotalHeader();
        for (ServiceInvoiceMain savedItem : savedItems) {
            savedItem.setTotalHeader(totalHeader);
            serviceInvoiceMainRepository.save(savedItem);  // Re-save after updating totalHeader
        }

        // Step 4: Call Debit Memo Pricing API
        try {
            serviceInvoiceMainService.callDebitMemoPricingAPI(
                    debitMemoRequest, debitMemoRequestItem, pricingProcedureStep, pricingProcedureCounter, totalHeader);
        } catch (Exception e) {
            log.error("Error while calling Debit Memo Pricing API: " + e.getMessage(), e);
            throw new RuntimeException("Failed to update Debit Memo Pricing Element. Response Code: " + e.getMessage());
        }

        // Step 5: Convert and return the saved items as a list of command objects
        List<ServiceInvoiceMainCommand> response = new ArrayList<>();
        for (ServiceInvoiceMain savedItem : savedItems) {
            response.add(serviceInvoiceToServiceInvoiceCommand.convert(savedItem));
        }

        return response;
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
