package com.example.btpsd.services;

import com.example.btpsd.commands.ExecutionOrderMainCommand;
import com.example.btpsd.model.*;
import com.example.btpsd.repositories.ExecutionOrderMainRepository;
import org.apache.commons.codec.binary.Base64;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.json.JSONObject;
import org.springframework.transaction.annotation.Transactional;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import okhttp3.MediaType;
import okhttp3.RequestBody;
import org.apache.commons.codec.binary.Base64;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import java.nio.charset.StandardCharsets;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

public interface ExecutionOrderMainService {
    
    ExecutionOrderMainRepository executionOrderMainRepository = null;

    Set<ExecutionOrderMainCommand> getExecutionOrderMainCommands();

    Double getTotalHeader();

    ExecutionOrderMain findById(Long l);

    void deleteById(Long idToDelete);

    ExecutionOrderMainCommand saveExecutionOrderMainCommand(ExecutionOrderMainCommand command);

    ExecutionOrderMain updateExecutionOrderMain(ExecutionOrderMainCommand newExecutionOrderMainCommand, Long l);

    ExecutionOrderMainCommand findExecutionOrderMainCommandById(Long l);


    public default Optional<ExecutionOrderMain> findByCode(Long executionOrderMainCode) {
        return executionOrderMainRepository.findByExecutionOrderMainCode(executionOrderMainCode);
    }


    @Transactional
    public default void updateNonNullFields(ExecutionOrderMainCommand source, ExecutionOrderMain target) {
        // Update all fields as provided
        if (source.getCurrencyCode() != null) target.setCurrencyCode(source.getCurrencyCode());
        if (source.getMaterialGroupCode() != null) target.setMaterialGroupCode(source.getMaterialGroupCode());
        if (source.getDeletionIndicator() != null) target.setDeletionIndicator(source.getDeletionIndicator());
        if (source.getLineTypeCode() != null) target.setLineTypeCode(source.getLineTypeCode());
        if (source.getPersonnelNumberCode() != null) target.setPersonnelNumberCode(source.getPersonnelNumberCode());
        if (source.getUnitOfMeasurementCode() != null)
            target.setUnitOfMeasurementCode(source.getUnitOfMeasurementCode());
        if (source.getDescription() != null) target.setDescription(source.getDescription());
        if (source.getTotalQuantity() != null) target.setTotalQuantity(source.getTotalQuantity());
        if (source.getAmountPerUnit() != null) target.setAmountPerUnit(source.getAmountPerUnit());
        if (source.getTotal() != null) target.setTotal(source.getTotal());
        if (source.getActualQuantity() != null) target.setActualQuantity(source.getActualQuantity());
        if (source.getActualPercentage() != null) target.setActualPercentage(source.getActualPercentage());
        if (source.getOverFulfillmentPercentage() != null)
            target.setOverFulfillmentPercentage(source.getOverFulfillmentPercentage());
        if (source.getUnlimitedOverFulfillment() != null)
            target.setUnlimitedOverFulfillment(source.getUnlimitedOverFulfillment());
        if (source.getManualPriceEntryAllowed() != null)
            target.setManualPriceEntryAllowed(source.getManualPriceEntryAllowed());
        if (source.getExternalServiceNumber() != null)
            target.setExternalServiceNumber(source.getExternalServiceNumber());
        if (source.getServiceText() != null) target.setServiceText(source.getServiceText());
        if (source.getLineText() != null) target.setLineText(source.getLineText());
        if (source.getLineNumber() != null) target.setLineNumber(source.getLineNumber());
        if (source.getBiddersLine() != null) target.setBiddersLine(source.getBiddersLine());
        if (source.getSupplementaryLine() != null) target.setSupplementaryLine(source.getSupplementaryLine());
        if (source.getLotCostOne() != null) target.setLotCostOne(source.getLotCostOne());
        if (source.getDoNotPrint() != null) target.setDoNotPrint(source.getDoNotPrint());
        if (source.getServiceTypeCode() != null) target.setServiceTypeCode(source.getServiceTypeCode());

        if (source.getServiceNumberCode() != null) {
            ServiceNumber serviceNumber = new ServiceNumber();
            serviceNumber.setServiceNumberCode(source.getServiceNumberCode());
            target.setServiceNumber(serviceNumber);
            serviceNumber.addExecutionOrderMainItem(target);
        }
        if (target.getServiceInvoiceMain() != null) {
            target.getServiceInvoiceMain().updateFromExecutionOrder(target);
        }
    }

    public default ResponseEntity<String> callSalesOrderPricingAPI(String salesOrder,
                                                                   String salesOrderItem,
                                                                   Integer pricingProcedureStep,
                                                                   Integer pricingProcedureCounter,
                                                                   Double totalHeader) throws Exception {

        Logger log = LogManager.getLogger(this.getClass());
        try {
            // Step 1: Prepare the request body with totalHeader
            String requestBody = "{\n \"ConditionType\": \"PPR0\",\n \"ConditionRateValue\": \"" + totalHeader + "\"\n}";

            // Step 2: Fetch CSRF token
            OkHttpClient client = new OkHttpClient().newBuilder().build();
            MediaType mediaType = MediaType.parse("application/json");

            // Encode authorization using Base64 from Apache Commons Codec
            String credentials = "BTP_USER1:#yiVfheJbFolFxgkEwCBFcWvYkPzrQDENEArAXn5";
            String encodedCredentials = new String(Base64.encodeBase64(credentials.getBytes(StandardCharsets.UTF_8)));

            // First, fetch the CSRF token with a GET request
            String tokenURL = "https://my418629.s4hana.cloud.sap/sap/opu/odata/sap/API_SALES_ORDER_SRV/A_SalesOrderItem(SalesOrder='"
                    + salesOrder + "',SalesOrderItem='" + salesOrderItem + "')/to_PricingElement?%24inlinecount=allpages&%24top=50";

            Request tokenRequest = new Request.Builder()
                    .url(tokenURL)
                    .method("GET", null)
                    .addHeader("x-csrf-token", "Fetch")
                    .addHeader("Authorization", "Basic " + encodedCredentials)
                    .addHeader("Accept", "application/json")
                    .build();

            Response tokenResponse = client.newCall(tokenRequest).execute();
            String csrfToken = tokenResponse.header("x-csrf-token");
            String cookies = tokenResponse.header("Set-Cookie");

            if (csrfToken == null || csrfToken.isEmpty()) {
                return ResponseEntity.status(HttpStatus.FORBIDDEN).body("Failed to fetch CSRF token");
            }

            log.debug("Fetched CSRF token successfully: " + csrfToken);

            // Step 3: Make the PATCH request to update the Sales Order Pricing Element
            String patchURL = "https://my418629.s4hana.cloud.sap/sap/opu/odata/sap/API_SALES_ORDER_SRV/A_SalesOrderItemPrElement(SalesOrder='"
                    + salesOrder + "',SalesOrderItem='" + salesOrderItem + "',PricingProcedureStep='"
                    + pricingProcedureStep + "',PricingProcedureCounter='" + pricingProcedureCounter + "')";

            Request patchRequest = new Request.Builder()
                    .url(patchURL)
                    .method("PATCH", RequestBody.create(mediaType, requestBody))
                    .addHeader("Authorization", "Basic " + encodedCredentials)
                    .addHeader("x-csrf-token", csrfToken)
                    .addHeader("If-Match", "*")
                    .addHeader("Content-Type", "application/json")
                    .addHeader("Cookie", cookies)
                    .build();

            Response patchResponse = client.newCall(patchRequest).execute();
            int responseCode = patchResponse.code();
            String responseString = patchResponse.body().string();

            log.debug("Response Code: " + responseCode);

            if (responseCode >= 200 && responseCode < 300) {
                return ResponseEntity.status(responseCode).body(responseString);
            } else {
                return ResponseEntity.status(responseCode).body("Failed to update Sales Order Pricing Element. Response: " + responseString);
            }
        } catch (Exception e) {
            log.error("Error while calling Sales Order Pricing API: " + e.getMessage(), e);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Error occurred: " + e.getMessage());
        }
    }
}

