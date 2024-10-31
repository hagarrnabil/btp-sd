package com.example.btpsd.controllers;

import com.example.btpsd.commands.ExecutionOrderMainCommand;
import com.example.btpsd.commands.InvoiceMainItemCommand;
import com.example.btpsd.commands.ServiceInvoiceMainCommand;
import com.example.btpsd.converters.ExecutionOrderMainToExecutionOrderMainCommand;
import com.example.btpsd.converters.ServiceInvoiceCommandToServiceInvoice;
import com.example.btpsd.converters.ServiceInvoiceToServiceInvoiceCommand;
import com.example.btpsd.model.InvoiceMainItem;
import com.example.btpsd.model.ServiceInvoiceMain;
import com.example.btpsd.model.ServiceNumber;
import com.example.btpsd.repositories.ServiceInvoiceMainRepository;
import com.example.btpsd.repositories.ServiceNumberRepository;
import com.example.btpsd.services.ExecutionOrderMainService;
import com.example.btpsd.services.ServiceInvoiceMainService;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.validation.constraints.NotNull;
import lombok.RequiredArgsConstructor;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;

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

    private final ServiceInvoiceMainService serviceInvoiceMainService;

    private final ServiceInvoiceToServiceInvoiceCommand serviceInvoiceToServiceInvoiceCommand;

    private final ServiceInvoiceCommandToServiceInvoice serviceInvoiceCommandToServiceInvoice;

    @GetMapping("/serviceinvoice")
    Set<ServiceInvoiceMainCommand> all() {
        return serviceInvoiceMainService.getServiceInvoiceMainCommands();
    }

    @GetMapping("/serviceinvoice/{debitMemoRequest}/{debitMemoRequestItem}")
    public StringBuilder findByDebitMemoRequestAndItem(
            @PathVariable("debitMemoRequest") String debitMemoRequest,
            @PathVariable("debitMemoRequestItem") String debitMemoRequestItem) {

        // Call the method to fetch Debit Memo Request Item based on path variables
        return salesOrderCloudController.getDebitMemoRequestItem(debitMemoRequest, debitMemoRequestItem);
    }

    @GetMapping("/serviceinvoice/{referenceId}")
    public ResponseEntity<List<ServiceInvoiceMainCommand>> getInvoiceMainItemsByReferenceId(@PathVariable String referenceId) {
        Optional<ServiceInvoiceMain> serviceInvoiceMain = serviceInvoiceMainRepository.findByReferenceId(referenceId);

        if (serviceInvoiceMain.isEmpty()) {
            return ResponseEntity.notFound().build(); // Return 404 if no items found
        }

        // Convert the list of InvoiceMainItem to InvoiceMainItemCommand for the response
        List<ServiceInvoiceMainCommand> responseItems = serviceInvoiceMain.stream()
                .map(serviceInvoiceToServiceInvoiceCommand::convert)
                .collect(Collectors.toList());

        return ResponseEntity.ok(responseItems);
    }

    @DeleteMapping("/serviceinvoice/{serviceInvoiceCode}")
    void deleteServiceInvoiceCommand(@PathVariable Long serviceInvoiceCode) {
        serviceInvoiceMainService.deleteById(serviceInvoiceCode);
    }


    @PatchMapping("/serviceinvoice/{debitMemoRequest}/{debitMemoRequestItem}/{pricingProcedureStep}/{pricingProcedureCounter}/{customerNumber}")
    public ServiceInvoiceMainCommand updateServiceInvoiceCommand(
            @RequestBody ServiceInvoiceMainCommand updatedServiceInvoiceMainCommand,
            @PathVariable String debitMemoRequest,
            @PathVariable String debitMemoRequestItem,
            @PathVariable Integer pricingProcedureStep,
            @PathVariable Integer pricingProcedureCounter, @PathVariable String customerNumber) throws Exception {

        updatedServiceInvoiceMainCommand.setReferenceId(debitMemoRequest);

        Optional<ServiceInvoiceMain> optionalServiceInvoiceMain = serviceInvoiceMainRepository.findByReferenceId(updatedServiceInvoiceMainCommand.getReferenceId());
        ServiceInvoiceMain savedServiceInvoice;

        if (optionalServiceInvoiceMain.isPresent()) {
            savedServiceInvoice = serviceInvoiceCommandToServiceInvoice.convert(updatedServiceInvoiceMainCommand);
            savedServiceInvoice = serviceInvoiceMainService.updateServiceInvoiceMain(savedServiceInvoice, optionalServiceInvoiceMain.get().getServiceInvoiceCode());
        } else {
            // Create a new InvoiceMainItem
            savedServiceInvoice = serviceInvoiceCommandToServiceInvoice.convert(updatedServiceInvoiceMainCommand);
            savedServiceInvoice = serviceInvoiceMainRepository.save(savedServiceInvoice);
        }

        Double totalHeader = serviceInvoiceMainService.getTotalHeader();
        savedServiceInvoice.setTotalHeader(totalHeader);
        serviceInvoiceMainRepository.save(savedServiceInvoice);

        // Step 4: Call the Sales Quotation Pricing API with the updated total header
        try {
            serviceInvoiceMainService.callDebitMemoPricingAPI(
                    debitMemoRequest, debitMemoRequestItem, pricingProcedureStep, pricingProcedureCounter, totalHeader);
        } catch (Exception e) {
            log.error("Error while calling Debit Mmo Pricing API: " + e.getMessage(), e);
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
