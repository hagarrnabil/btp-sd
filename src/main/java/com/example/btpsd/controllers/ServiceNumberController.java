package com.example.btpsd.controllers;

import com.example.btpsd.commands.ServiceNumberCommand;
import com.example.btpsd.converters.ServiceNumberToServiceNumberCommand;
import com.example.btpsd.model.Currency;
import com.example.btpsd.model.ServiceNumber;
import com.example.btpsd.repositories.ServiceNumberRepository;
import com.example.btpsd.services.ServiceNumberService;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.validation.constraints.NotNull;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;

import java.util.HashSet;
import java.util.List;
import java.util.Optional;
import java.util.Set;

@RequiredArgsConstructor
@RestController
public class ServiceNumberController {

    private final ServiceNumberRepository serviceNumberRepository;

    private final ServiceNumberService serviceNumberService;

    private final ProductCloudController productCloudController;

    private final ServiceNumberToServiceNumberCommand serviceNumberToServiceNumberCommand;


    @GetMapping("/servicenumbers")
    public ResponseEntity<List<ServiceNumber>> fetchAndUpdateServiceNumbers() throws Exception {
        // Fetch all existing service numbers
        List<ServiceNumber> existingServiceNumbers = (List<ServiceNumber>) serviceNumberRepository.findAll();
        Set<Long> existingProductCodes = new HashSet<>();

        // Populate the set with existing product codes
        for (ServiceNumber serviceNumber : existingServiceNumbers) {
            existingProductCodes.add(serviceNumber.getServiceNumberCode());
        }

        // Check if we need to fetch new products from the cloud
        if (shouldFetchFromCloud(existingServiceNumbers.size())) {
            // Fetch combined product data from the cloud
            StringBuilder combinedProductDetails = productCloudController.getCombinedProductDetails();
            ObjectMapper objectMapper = new ObjectMapper();
            JsonNode combinedProducts = objectMapper.readTree(combinedProductDetails.toString());

            for (JsonNode productNode : combinedProducts) {
                String productCode = productNode.path("Product").asText();
                Long productCodeLong = Long.valueOf(productCode);

                // Check if the product code already exists
                if (!existingProductCodes.contains(productCodeLong)) {
                    String productDescription = productNode.path("ProductDescription").asText();
                    String productType = productNode.path("ProductType").asText();
                    String baseUnit = productNode.path("BaseUnit").asText();

                    // If not, create a new ServiceNumber
                    ServiceNumber newServiceNumber = new ServiceNumber();
                    newServiceNumber.setServiceNumberCode(productCodeLong);
                    newServiceNumber.setDescription(productDescription);
                    newServiceNumber.setMaterialGroupCode(productType); // Map productType to materialGroupCode
                    newServiceNumber.setUnitOfMeasurementCode(baseUnit);

                    // Save the new service number to the database
                    serviceNumberRepository.save(newServiceNumber);
                    // Optionally add the new code to the existing set
                    existingProductCodes.add(productCodeLong);
                }
            }
        }

        // Return all existing service numbers
        List<ServiceNumber> allServiceNumbers = (List<ServiceNumber>) serviceNumberRepository.findAll();
        return ResponseEntity.ok(allServiceNumbers); // Return the list of service numbers
    }

    private boolean shouldFetchFromCloud(int existingCount) {
        // Define logic to decide if we should fetch from the cloud
        // For example, fetch only if the existing count is less than a threshold
        int expectedCount = 50; // Set your expected count based on your requirements
        return existingCount < expectedCount; // Fetch if we have less than expected
    }


    @GetMapping("/servicenumbers/{serviceNumberCode}")
    public Optional<ServiceNumberCommand> findByIds(@PathVariable @NotNull Long serviceNumberCode) {

        return Optional.ofNullable(serviceNumberService.findServiceNumberCommandById(serviceNumberCode));
    }

    @PostMapping("/servicenumbers")
    ServiceNumberCommand newServiceNumberCommand(@RequestBody ServiceNumberCommand newServiceNumberCommand) {

        ServiceNumberCommand savedCommand = serviceNumberService.saveServiceNumberCommand(newServiceNumberCommand);
        return savedCommand;

    }

    @DeleteMapping("/servicenumbers/{serviceNumberCode}")
    void deleteServiceNumberCommand(@PathVariable Long serviceNumberCode) {
        serviceNumberService.deleteById(serviceNumberCode);
    }

    @PatchMapping
    @RequestMapping("/servicenumbers/{serviceNumberCode}")
    @Transactional
    ServiceNumberCommand updateServiceNumberCommand(@RequestBody ServiceNumberCommand newServiceNumberCommand, @PathVariable Long serviceNumberCode) {

        ServiceNumberCommand command = serviceNumberToServiceNumberCommand.convert(serviceNumberService.updateServiceNumber(newServiceNumberCommand, serviceNumberCode));
        return command;
    }


    @RequestMapping(method = RequestMethod.GET, value = "/servicenumbers/search")
    @ResponseBody
    public List<ServiceNumber> Search(@RequestParam String keyword) {

        return serviceNumberRepository.search(keyword);
    }
}
