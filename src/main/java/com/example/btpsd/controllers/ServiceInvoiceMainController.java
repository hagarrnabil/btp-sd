package com.example.btpsd.controllers;

import com.example.btpsd.commands.ExecutionOrderMainCommand;
import com.example.btpsd.commands.ServiceInvoiceMainCommand;
import com.example.btpsd.converters.ExecutionOrderMainToExecutionOrderMainCommand;
import com.example.btpsd.converters.ServiceInvoiceToServiceInvoiceCommand;
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
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;
import java.util.Set;

@RequiredArgsConstructor
@RestController
public class ServiceInvoiceMainController {

    Logger log = LogManager.getLogger(this.getClass());

    @Autowired
    private final ServiceNumberRepository serviceNumberRepository;

    private final ProductCloudController productCloudController;

    private final BusinessPartnerCloudController businessPartnerCloudController;

    private final SalesOrderCloudController salesOrderCloudController;

    private final ServiceInvoiceMainRepository serviceInvoiceMainRepository;

    private final ServiceInvoiceMainService serviceInvoiceMainService;

    private final ServiceInvoiceToServiceInvoiceCommand serviceInvoiceToServiceInvoiceCommand;

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

    @PostMapping("/serviceinvoice")
    ServiceInvoiceMainCommand newServiceInvoiceCommand(@RequestBody ServiceInvoiceMainCommand newServiceInvoiceCommand) {

        ServiceInvoiceMainCommand savedCommand = serviceInvoiceMainService.saveServiceInvoiceMainCommand(newServiceInvoiceCommand);
        // You can access executionOrderMainCode like this:
        Long executionOrderMainCode = savedCommand.getExecutionOrderMain().getExecutionOrderMainCode();

        return savedCommand;

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

        // Fetch data from the required controller (similar to productCloudController)
        String productApiResponse = productCloudController.getAllProducts().toString();
        String productDescApiResponse = productCloudController.getAllProductsDesc().toString();

        ObjectMapper objectMapper = new ObjectMapper();
        try {
            JsonNode productJson = objectMapper.readTree(productApiResponse);
            JsonNode productDescJson = objectMapper.readTree(productDescApiResponse);

            JsonNode productsArray = productJson.path("d").path("results");
            JsonNode productDescriptionsArray = productDescJson.path("d").path("results");

            for (int i = 0; i < productsArray.size(); i++) {
//                JsonNode product = productsArray.get(i);
//                JsonNode productDesc = productDescriptionsArray.get(i);
//
//                String serviceNumberCode = product.path("Product").asText();
//                String unitOfMeasurementCode = product.path("BaseUnit").asText();
//                String description = productDesc.path("ProductDescription").asText();
//
//                // Check if serviceNumberCode exists in ServiceNumber table
//                Optional<ServiceNumber> existingServiceNumber = serviceNumberRepository.findByServiceNumberCode(Long.valueOf(serviceNumberCode));
//
//                ServiceNumber serviceNumber;
//                if (!existingServiceNumber.isPresent()) {
//                    serviceNumber = new ServiceNumber();
//                    serviceNumber.setServiceNumberCode(Long.valueOf(serviceNumberCode));
//                    serviceNumber = serviceNumberRepository.save(serviceNumber);
//                } else {
//                    serviceNumber = existingServiceNumber.get();
//                }
//
//                updatedServiceInvoiceMainCommand.setServiceNumberCode(serviceNumber.getServiceNumberCode());
//                updatedServiceInvoiceMainCommand.setUnitOfMeasurementCode(unitOfMeasurementCode);
//                updatedServiceInvoiceMainCommand.setDescription(description);
//
//                // Step 9: Fetch currency from business partner's sales area based on customer number
//                String businessPartnerApiResponse = businessPartnerCloudController.getBusinessPartnerSalesArea(customerNumber).toString();
//                JsonNode businessPartnerJson = objectMapper.readTree(businessPartnerApiResponse);
//
//                JsonNode salesAreaArray = businessPartnerJson.path("d").path("results");
//                if (salesAreaArray.size() > 0) {
//                    String currency = salesAreaArray.get(0).path("Currency").asText();
//                    updatedServiceInvoiceMainCommand.setCurrencyCode(currency);
//                } else {
//                    throw new RuntimeException("Currency not found for customer number: " + customerNumber);
//                }

                ServiceInvoiceMainCommand savedCommand = serviceInvoiceMainService.saveServiceInvoiceMainCommand(updatedServiceInvoiceMainCommand);
                if (savedCommand == null) {
                    throw new RuntimeException("Failed to update Service Invoice Main.");
                }

                Double totalHeader = savedCommand.getTotalHeader();

                log.debug("Calling Debit Memo Pricing API with data: ");
                log.debug("Debit Memo Request: " + debitMemoRequest);
                log.debug("Debit Memo Request Item: " + debitMemoRequestItem);
                log.debug("Pricing Procedure Step: " + pricingProcedureStep);
                log.debug("Pricing Procedure Counter: " + pricingProcedureCounter);

                try {
                    serviceInvoiceMainService.callDebitMemoPricingAPI(
                            debitMemoRequest, debitMemoRequestItem, pricingProcedureStep, pricingProcedureCounter, totalHeader);
                } catch (Exception e) {
                    log.error("Error while calling Debit Memo Pricing API: " + e.getMessage(), e);
                    throw new RuntimeException("Failed to update Debit Memo Pricing Element. Response Code: " + e.getMessage());
                }

                return savedCommand;
            }

        } catch (JsonProcessingException e) {
            throw new RuntimeException("Error processing product API response", e);
        }

        return updatedServiceInvoiceMainCommand;
    }

//    @PatchMapping
//    @RequestMapping("/serviceinvoice/{serviceInvoiceCode}")
//    @Transactional
//    ServiceInvoiceMainCommand updateServiceInvoiceCommand(@RequestBody ServiceInvoiceMain newServiceInvoiceCommand , @PathVariable Long serviceInvoiceCode) {
//
//        ServiceInvoiceMainCommand command = serviceInvoiceToServiceInvoiceCommand.convert(serviceInvoiceMainService.updateServiceInvoiceMain(newServiceInvoiceCommand, serviceInvoiceCode));
//        return command;
//    }

    @RequestMapping(method = RequestMethod.GET, value = "/serviceinvoice/linenumber")
    @ResponseBody
    public List<ServiceInvoiceMain> findByLineNumber(@RequestParam String lineNumber) {

        return serviceInvoiceMainRepository.findByLineNumber(lineNumber);
    }

}
