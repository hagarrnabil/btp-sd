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
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import okhttp3.MediaType;
import okhttp3.RequestBody;

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

    Double getTotalHeader();

    public default ResponseEntity<String> callDebitMemoPricingAPI(String debitMemoRequest, String debitMemoRequestItem,
                                                                  Integer pricingProcedureStep, Integer pricingProcedureCounter,
                                                                  Double totalHeader) throws Exception {

        Logger log = LogManager.getLogger(this.getClass());
        try {
            // Step 1: Prepare the request body with totalHeader as a String
            String requestBody = "{\n" +
                    "  \"ConditionType\": \"PPR0\",\n" +
                    "  \"ConditionRateValue\": \"" + totalHeader + "\"\n" +
                    "}";

            // Step 2: Fetch CSRF token
            OkHttpClient client = new OkHttpClient().newBuilder().build();
            MediaType mediaType = MediaType.parse("application/json");

            // Encode credentials using Base64
            String credentials = "BTP_USER1:#yiVfheJbFolFxgkEwCBFcWvYkPzrQDENEArAXn5";
            String encodedCredentials = new String(Base64.encodeBase64(credentials.getBytes(StandardCharsets.UTF_8)));

            // Fetch CSRF token by sending a GET request
            String tokenUrl = "https://my418629.s4hana.cloud.sap/sap/opu/odata/sap/API_DEBIT_MEMO_REQUEST_SRV/A_DebitMemoRequest";
            Request tokenRequest = new Request.Builder()
                    .url(tokenUrl)
                    .method("GET", null)
                    .addHeader("x-csrf-token", "Fetch")
                    .addHeader("Authorization", "Basic " + encodedCredentials)
                    .addHeader("Accept", "application/json")
                    .build();

            Response tokenResponse = client.newCall(tokenRequest).execute();
            String csrfToken = tokenResponse.header("x-csrf-token");
            String cookies = tokenResponse.header("Set-Cookie");

            if (csrfToken == null || csrfToken.isEmpty()) {
                log.error("Failed to fetch CSRF token.");
                return ResponseEntity.status(HttpStatus.FORBIDDEN).body("Failed to fetch CSRF token");
            }

            log.debug("Fetched CSRF token successfully: " + csrfToken);

            // Step 3: Make the PATCH request to update the Debit Memo Pricing Element
            String patchUrl = "https://my418629.s4hana.cloud.sap/sap/opu/odata/sap/API_DEBIT_MEMO_REQUEST_SRV/A_DebitMemoReqItemPrcgElmnt(" +
                    "DebitMemoRequest='" + debitMemoRequest + "'," +
                    "DebitMemoRequestItem='" + debitMemoRequestItem + "'," +
                    "PricingProcedureStep='" + pricingProcedureStep + "'," +
                    "PricingProcedureCounter='" + pricingProcedureCounter + "')";

            Request patchRequest = new Request.Builder()
                    .url(patchUrl)
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

            log.debug("PATCH Response Code: " + responseCode);
            log.debug("PATCH Response Body: " + responseString);

            if (responseCode >= 200 && responseCode < 300) {
                return ResponseEntity.status(responseCode).body(responseString);
            } else {
                log.error("Failed to update Debit Memo Pricing Element. Response: " + responseString);
                return ResponseEntity.status(responseCode).body("Failed to update Debit Memo Pricing Element. Response: " + responseString);
            }
        } catch (Exception e) {
            log.error("Error while calling Debit Memo Pricing API: " + e.getMessage(), e);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Error occurred: " + e.getMessage());
        }
    }

}