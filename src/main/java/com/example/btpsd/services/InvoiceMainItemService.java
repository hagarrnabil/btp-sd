package com.example.btpsd.services;

import com.example.btpsd.commands.InvoiceMainItemCommand;
import com.example.btpsd.model.InvoiceMainItem;
import com.example.btpsd.repositories.InvoiceMainItemRepository;
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
import org.springframework.transaction.annotation.Transactional;

import java.nio.charset.StandardCharsets;
import java.util.Set;

public interface InvoiceMainItemService {

    InvoiceMainItemRepository invoiceMainItemRepository = null;

    Set<InvoiceMainItemCommand> getMainItemCommands();

    InvoiceMainItem findById(Long l);

    void deleteById(Long idToDelete);

    Double getTotalHeader();

    InvoiceMainItemCommand saveMainItemCommand(InvoiceMainItemCommand command);

    InvoiceMainItem updateMainItem(InvoiceMainItemCommand newInvoiceMainItemCommand, Long l);

    InvoiceMainItemCommand findMainItemCommandById(Long l);


    public default ResponseEntity<String> callInvoicePricingAPI(String salesQuotation, String salesQuotationItem, Integer pricingProcedureStep, Integer pricingProcedureCounter, Double totalHeader) throws Exception {
        Logger log = LogManager.getLogger(this.getClass());
        try {
            // Step 1: Prepare the request body with totalHeader as a String
            String requestBody = "{\n \"ConditionType\": \"PPR0\",\n \"ConditionRateValue\": \"" + totalHeader + "\"\n}";

            // Step 2: Fetch CSRF token
            OkHttpClient client = new OkHttpClient().newBuilder().build();
            MediaType mediaType = MediaType.parse("application/json");

            // Encode authorization using Base64 from Apache Commons Codec
            String credentials = "BTP_USER1:#yiVfheJbFolFxgkEwCBFcWvYkPzrQDENEArAXn5";
            String encodedCredentials = new String(Base64.encodeBase64(credentials.getBytes(StandardCharsets.UTF_8)));

            // First, we fetch the CSRF token by sending a GET request
            Request tokenRequest = new Request.Builder()
                    .url("https://my418629.s4hana.cloud.sap/sap/opu/odata/sap/API_SALES_QUOTATION_SRV/A_SalesQuotationItem(SalesQuotation='" + salesQuotation + "',SalesQuotationItem='" + salesQuotationItem + "')/to_PricingElement?%24inlinecount=allpages&%24top=50")
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

            // Step 3: Make the PATCH request to update the Sales Quotation Pricing Element
            String patchURL = "https://my418629.s4hana.cloud.sap/sap/opu/odata/sap/API_SALES_QUOTATION_SRV/A_SalesQuotationItemPrcgElmnt(SalesQuotation='" + salesQuotation + "',SalesQuotationItem='" + salesQuotationItem + "',PricingProcedureStep='" + pricingProcedureStep + "',PricingProcedureCounter='" + pricingProcedureCounter + "')";

            // Now, we make the PATCH request
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
                return ResponseEntity.status(responseCode).body("Failed to update Invoice Pricing Element. Response: " + responseString);
            }
        } catch (Exception e) {
            log.error("Error while calling Sales Quotation Pricing API: " + e.getMessage(), e);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Error occurred: " + e.getMessage());
        }
    }
}
