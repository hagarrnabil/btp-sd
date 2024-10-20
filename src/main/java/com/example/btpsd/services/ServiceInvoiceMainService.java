package com.example.btpsd.services;

import com.example.btpsd.commands.ServiceInvoiceMainCommand;
import com.example.btpsd.model.ServiceInvoiceMain;
import com.example.btpsd.model.ServiceNumber;
import org.springframework.transaction.annotation.Transactional;
import org.apache.commons.codec.binary.Base64;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.json.JSONObject;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.nio.charset.StandardCharsets;
import java.util.Set;

public interface ServiceInvoiceMainService {

    Set<ServiceInvoiceMainCommand> getServiceInvoiceMainCommands();

    ServiceInvoiceMain findById(Long l);

    void deleteById(Long idToDelete);

    ServiceInvoiceMainCommand saveServiceInvoiceMainCommand(ServiceInvoiceMainCommand command);

    ServiceInvoiceMain updateServiceInvoiceMain(ServiceInvoiceMain updatedInvoice, Long l);

    ServiceInvoiceMainCommand findServiceInvoiceMainCommandById(Long l);

    @Transactional
    default void updateNonNullFields(ServiceInvoiceMain source, ServiceInvoiceMain target) {
        if (source.getCurrencyCode() != null) target.setCurrencyCode(source.getCurrencyCode());
        if (source.getMaterialGroupCode() != null) target.setMaterialGroupCode(source.getMaterialGroupCode());
        if (source.getLineTypeCode() != null) target.setLineTypeCode(source.getLineTypeCode());
        if (source.getPersonnelNumberCode() != null) target.setPersonnelNumberCode(source.getPersonnelNumberCode());
        if (source.getUnitOfMeasurementCode() != null) target.setUnitOfMeasurementCode(source.getUnitOfMeasurementCode());
        if (source.getDescription() != null) target.setDescription(source.getDescription());
        if (source.getTotalQuantity() != null) target.setTotalQuantity(source.getTotalQuantity());
        if (source.getQuantity() != null) target.setQuantity(source.getQuantity());
        if (source.getAmountPerUnit() != null) target.setAmountPerUnit(source.getAmountPerUnit());
        if (source.getTotal() != null) target.setTotal(source.getTotal());
        if (source.getActualQuantity() != null) target.setActualQuantity(source.getActualQuantity());
        if (source.getActualPercentage() != null) target.setActualPercentage(source.getActualPercentage());
        if (source.getOverFulfillmentPercentage() != null) target.setOverFulfillmentPercentage(source.getOverFulfillmentPercentage());
        if (source.getUnlimitedOverFulfillment() != null) target.setUnlimitedOverFulfillment(source.getUnlimitedOverFulfillment());
        if (source.getExternalServiceNumber() != null) target.setExternalServiceNumber(source.getExternalServiceNumber());
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
            serviceNumber.addServiceInvoiceMain(target);
        }
    }

    public default ResponseEntity<String> callDebitMemoPricingAPI(String debitMemoRequest,
                                                                  String debitMemoRequestItem, Integer pricingProcedureStep, Integer pricingProcedureCounter,
                                                                  Double totalHeader) throws Exception {

        Logger log = LogManager.getLogger(this.getClass());

        // Prepare request body with totalHeader as String
        JSONObject requestBodyJson = new JSONObject();
        requestBodyJson.put("ConditionType", "PPR0"); // Set ConditionType to "PPR0"
        requestBodyJson.put("ConditionRateValue", String.valueOf(totalHeader)); // Convert totalHeader to String

        String requestBody = requestBodyJson.toString();

        // Fetch CSRF token
        String tokenURL = "https://my405604-api.s4hana.cloud.sap/sap/opu/odata/sap/API_DEBIT_MEMO_REQUEST_SRV/A_DebitMemoRequest?%24inlinecount=allpages&%24top=50";
        HttpURLConnection tokenConn = (HttpURLConnection) new URL(tokenURL).openConnection();
        String user = "BTP_USER1";
        String password = "Gw}tDHMrhuAWnzRWkwEbpcguYKsxugDuoKMeJ8Lt";
        String auth = user + ":" + password;
        byte[] encodedAuth = Base64.encodeBase64(auth.getBytes(StandardCharsets.UTF_8));
        String authHeaderValue = "Basic " + new String(encodedAuth);

        tokenConn.setRequestMethod("GET");
        tokenConn.setRequestProperty("Authorization", authHeaderValue);
        tokenConn.setRequestProperty("x-csrf-token", "Fetch");
        tokenConn.setRequestProperty("Accept", "application/json");

        // Fetch CSRF Token and cookies
        String csrfToken = tokenConn.getHeaderField("x-csrf-token");
        String cookies = tokenConn.getHeaderField("Set-Cookie");

        if (csrfToken == null || csrfToken.isEmpty()) {
            return ResponseEntity.status(HttpStatus.FORBIDDEN).body("Failed to fetch CSRF token");
        }

        log.debug("Fetched CSRF token successfully: " + csrfToken);

        // Make the PATCH request to update the Debit Memo Request Pricing Element
        String patchURL = "https://my405604-api.s4hana.cloud.sap/sap/opu/odata/sap/API_DEBIT_MEMO_REQUEST_SRV/A_DebitMemoReqItemPrcgElmnt(DebitMemoRequest='"
                + debitMemoRequest + "',DebitMemoRequestItem='" + debitMemoRequestItem
                + "',PricingProcedureStep='" + pricingProcedureStep
                + "',PricingProcedureCounter='" + pricingProcedureCounter + "')";

        HttpURLConnection patchConn = (HttpURLConnection) new URL(patchURL).openConnection();
        patchConn.setRequestMethod("POST"); // Use POST for PATCH
        patchConn.setRequestProperty("X-HTTP-Method-Override", "PATCH");
        patchConn.setRequestProperty("Authorization", authHeaderValue);
        patchConn.setRequestProperty("x-csrf-token", csrfToken);
        patchConn.setRequestProperty("If-Match", "*");
        patchConn.setRequestProperty("Content-Type", "application/json");

        if (cookies != null) {
            patchConn.setRequestProperty("Cookie", cookies);
        }

        patchConn.setDoOutput(true);
        try (OutputStream os = patchConn.getOutputStream()) {
            byte[] input = requestBody.getBytes(StandardCharsets.UTF_8);
            os.write(input, 0, input.length);
        }

        int responseCode = patchConn.getResponseCode();
        log.debug("Response Code: " + responseCode);

        StringBuilder response = new StringBuilder();

        try (BufferedReader br = new BufferedReader(new InputStreamReader(
                responseCode >= 200 && responseCode < 300 ? patchConn.getInputStream() : patchConn.getErrorStream(),
                StandardCharsets.UTF_8))) {
            String responseLine;
            while ((responseLine = br.readLine()) != null) {
                response.append(responseLine.trim());
            }
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Error reading response: " + e.getMessage());
        }

        return ResponseEntity.status(responseCode).body(response.toString());
    }
}