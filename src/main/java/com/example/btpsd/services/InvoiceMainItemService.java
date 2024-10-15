package com.example.btpsd.services;

import com.example.btpsd.commands.InvoiceMainItemCommand;
import com.example.btpsd.dto.SalesOrderToMainitemDTO;
import com.example.btpsd.model.InvoiceMainItem;
import com.example.btpsd.repositories.InvoiceMainItemRepository;
import org.apache.commons.codec.binary.Base64;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.json.JSONObject;
import org.springframework.transaction.annotation.Transactional;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
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

    public default void sendToInvoiceMainItem(SalesOrderToMainitemDTO dto) {
        InvoiceMainItem invoiceMainItemCommand = new InvoiceMainItem();

        // Map SalesOrder fields to InvoiceMainItem fields
        invoiceMainItemCommand.setInvoiceMainItemCode(Long.valueOf(dto.getSalesOrder()));
        invoiceMainItemCommand.setCurrencyCode(dto.getTransactionCurrency());

        // Save to the database through the repository or another service
        invoiceMainItemRepository.save(invoiceMainItemCommand);  // Assuming InvoiceMainItemCommand is the entity

    }

    default void callSalesOrderPricingAPI(String salesOrder, String salesOrderItem, Double totalHeader) throws Exception {

        // Initialize the logger
        Logger log = LogManager.getLogger(this.getClass());

        // Step 1: Prepare the request body with totalHeader
        JSONObject requestBodyJson = new JSONObject();
        requestBodyJson.put("ConditionType", "PPR0"); // Set ConditionType to "PPR0"
        requestBodyJson.put("ConditionRateAmount", totalHeader); // Set ConditionRateAmount to totalHeader

        String requestBody = requestBodyJson.toString();

        // Step 2: Fetch CSRF token
        String tokenURL = "https://my405604-api.s4hana.cloud.sap/sap/opu/odata/sap/API_SALES_ORDER_SRV/A_SalesOrderItem(SalesOrder='"
                + salesOrder + "',SalesOrderItem='" + salesOrderItem + "')/to_PricingElement";

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
            log.error("Failed to fetch CSRF token");
            throw new RuntimeException("Failed to fetch CSRF token");
        }

        log.debug("Fetched CSRF token successfully: " + csrfToken);

        // Step 3: Make the POST request to update the Sales Order Pricing Element
        String postURL = "https://my405604-api.s4hana.cloud.sap/sap/opu/odata4/sap/api_salesorder/srvd_a2x/sap/salesorder/0001/SalesOrderItem/"
                + salesOrder + "/" + salesOrderItem + "/_ItemPricingElement";

        HttpURLConnection postConn = (HttpURLConnection) new URL(postURL).openConnection();
        postConn.setRequestMethod("POST");
        postConn.setRequestProperty("Authorization", authHeaderValue);
        postConn.setRequestProperty("x-csrf-token", csrfToken);
        postConn.setRequestProperty("Content-Type", "application/json");

        if (cookies != null) {
            postConn.setRequestProperty("Cookie", cookies);
        }

        postConn.setDoOutput(true);
        try (OutputStream os = postConn.getOutputStream()) {
            byte[] input = requestBody.getBytes(StandardCharsets.UTF_8);
            os.write(input, 0, input.length);
        }

        int responseCode = postConn.getResponseCode();
        log.debug("Response Code: " + responseCode);

        // Enhanced error handling for various response codes
        if (responseCode != HttpURLConnection.HTTP_OK && responseCode != HttpURLConnection.HTTP_CREATED) {
            String errorMessage = "Failed to post Sales Order Pricing Element. Response Code: " + responseCode;
            String responseBody = new String(postConn.getErrorStream().readAllBytes(), StandardCharsets.UTF_8);
            log.error("Error Response Body: " + responseBody);
            throw new RuntimeException(errorMessage);
        }

        // Handle the successful response
        InputStream responseStream = postConn.getInputStream();
        String response = new BufferedReader(new InputStreamReader(responseStream)).lines().collect(Collectors.joining("\n"));
        log.info("Successfully posted Sales Order Pricing Element. Response: " + response);
    }

}
