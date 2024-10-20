package com.example.btpsd.services;

import com.example.btpsd.commands.InvoiceMainItemCommand;
import com.example.btpsd.model.InvoiceMainItem;
import com.example.btpsd.repositories.InvoiceMainItemRepository;
import org.apache.commons.codec.binary.Base64;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.json.JSONObject;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.io.*;
import java.net.HttpURLConnection;
import java.net.URL;
import java.nio.charset.StandardCharsets;
import java.util.Set;
import java.util.stream.Collectors;

public interface InvoiceMainItemService {

    InvoiceMainItemRepository invoiceMainItemRepository = null;

    Set<InvoiceMainItemCommand> getMainItemCommands();

    InvoiceMainItem findById(Long l);

    void deleteById(Long idToDelete);

    Double getTotalHeader();

    InvoiceMainItemCommand saveMainItemCommand(InvoiceMainItemCommand command);

    InvoiceMainItem updateMainItem(InvoiceMainItemCommand newInvoiceMainItemCommand, Long l);

    InvoiceMainItemCommand findMainItemCommandById(Long l);

//    public default void sendToInvoiceMainItem(SalesOrderToMainitemDTO dto) {
//        InvoiceMainItem invoiceMainItemCommand = new InvoiceMainItem();
//
//        // Map SalesOrder fields to InvoiceMainItem fields
//        invoiceMainItemCommand.setInvoiceMainItemCode(Long.valueOf(dto.getSalesOrder()));
//        invoiceMainItemCommand.setCurrencyCode(dto.getTransactionCurrency());
//
//        // Save to the database through the repository or another service
//        invoiceMainItemRepository.save(invoiceMainItemCommand);  // Assuming InvoiceMainItemCommand is the entity
//
//    }

    public default ResponseEntity<String> callInvoicePricingAPI(String salesQuotation,
                                                                String salesQuotationItem, Integer pricingProcedureStep, Integer pricingProcedureCounter,
                                                                Double totalHeader) throws Exception {

        // Initialize the logger
        Logger log = LogManager.getLogger(this.getClass());

        // Step 1: Prepare the request body with totalHeader as a String
        JSONObject requestBodyJson = new JSONObject();
        requestBodyJson.put("ConditionType", "PPR0"); // Set ConditionType to "PPR0"
        requestBodyJson.put("ConditionRateValue", String.valueOf(totalHeader)); // Convert totalHeader to String

        String requestBody = requestBodyJson.toString();

        // Step 2: Fetch CSRF token
        String tokenURL = "https://my405604-api.s4hana.cloud.sap/sap/opu/odata/sap/API_SALES_QUOTATION_SRV/A_SalesQuotationItem(SalesQuotation='"
                + salesQuotation + "',SalesQuotationItem='" + salesQuotationItem + "')/to_PricingElement?%24inlinecount=allpages&%24top=50";

        HttpURLConnection tokenConn = (HttpURLConnection) new URL(tokenURL).openConnection();
        String user = "BTP_USER1";
        String password = "Gw}tDHMrhuAWnzRWkwEbpcguYKsxugDuoKMeJ8Lt"; // Keep this secure
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

        // Step 3: Make the PATCH request to update the Sales Quotation Pricing Element
        String patchURL = "https://my405604-api.s4hana.cloud.sap/sap/opu/odata4/sap/API_SALES_QUOTATION_SRV/A_SalesQuotationItemPrcgElmnt(SalesQuotation='"
                + salesQuotation + "',SalesQuotationItem='" + salesQuotationItem
                + "',PricingProcedureStep='" + pricingProcedureStep
                + "',PricingProcedureCounter='" + pricingProcedureCounter + "')";

        HttpURLConnection patchConn = (HttpURLConnection) new URL(patchURL).openConnection();
        patchConn.setRequestMethod("POST"); // Use POST instead of PATCH
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
        } catch (IOException e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Error sending request body: " + e.getMessage());
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

        // Return the response along with the response code
        return ResponseEntity.status(responseCode).body(response.toString());
    }

}
