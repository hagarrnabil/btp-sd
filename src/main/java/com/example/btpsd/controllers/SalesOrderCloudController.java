package com.example.btpsd.controllers;

import lombok.RequiredArgsConstructor;
import org.apache.commons.codec.binary.Base64;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.io.*;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.util.LinkedHashMap;
import java.util.Map;

@RequiredArgsConstructor
@RestController
public class SalesOrderCloudController {

    @RequestMapping(value = "/salesordercloud", method = RequestMethod.GET)
    private StringBuilder getAllSalesOrders() throws Exception {

        final int BLOCK_SIZE = 1024;
        final int BUFFER_SIZE = 8 * BLOCK_SIZE;
        DataOutputStream dataOut = null;
        BufferedReader in = null;


        //API endpoint
        String url = "https://my405604-api.s4hana.cloud.sap/sap/opu/odata/sap/API_SALES_ORDER_SRV/A_SalesOrder?%24inlinecount=allpages&%24top=50";


        URL urlObj = new URL(url);
        HttpURLConnection connection = (HttpURLConnection) urlObj.openConnection();
        String user = "BTP_USER1";
        String password = "Gw}tDHMrhuAWnzRWkwEbpcguYKsxugDuoKMeJ8Lt";
        String auth = user + ":" + password;
        byte[] encodedAuth = Base64.encodeBase64(auth.getBytes(StandardCharsets.UTF_8));
        String authHeaderValue = "Basic " + new String(encodedAuth);

        //setting request method
        connection.setRequestMethod("GET");

        //adding headers
        connection.setRequestProperty("Authorization", authHeaderValue);
        connection.setRequestProperty("Accept", "application/json");


        connection.setDoInput(true);

        int responseCode = connection.getResponseCode();
        in = new BufferedReader(new InputStreamReader(connection.getInputStream()));
        StringBuilder response = new StringBuilder();
        char[] charArray = new char[BUFFER_SIZE];
        int charsCount = 0;
        while ((charsCount = in.read(charArray)) != -1) {
            response.append(String.valueOf(charArray, 0, charsCount));
        }

        //printing response
        System.out.println(response.toString());


        return response;
    }


    @PostMapping("/salesorderpostcloud")
    public ResponseEntity<String> postSalesOrder(@RequestBody String requestBody) throws Exception {
        String url = "https://my405604-api.s4hana.cloud.sap/sap/opu/odata4/sap/api_salesorder/srvd_a2x/sap/salesorder/0001/SalesOrder";
        String tokenURL = "https://my405604-api.s4hana.cloud.sap/sap/opu/odata/sap/API_SALES_ORDER_SRV/A_SalesOrder?%24inlinecount=allpages&%24top=50";

        // Step 1: Fetch CSRF Token with a GET request
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

        // Get session cookies for CSRF validation
        String cookies = tokenConn.getHeaderField("Set-Cookie");

        // Read the CSRF token from the response headers
        String csrfToken = tokenConn.getHeaderField("x-csrf-token");

        System.out.println("CSRF Token: " + csrfToken);
        System.out.println("Cookies: " + cookies);

        if (csrfToken == null || csrfToken.isEmpty()) {
            return ResponseEntity.status(HttpStatus.FORBIDDEN).body("Failed to fetch CSRF token");
        }

        // Step 2: Send POST request with CSRF token and session cookies
        HttpURLConnection postConn = (HttpURLConnection) new URL(url).openConnection();
        postConn.setRequestMethod("POST");
        postConn.setRequestProperty("Authorization", authHeaderValue);
        postConn.setRequestProperty("x-csrf-token", csrfToken);
        postConn.setRequestProperty("Content-Type", "application/json");

        // Attach session cookies to maintain the session
        if (cookies != null) {
            postConn.setRequestProperty("Cookie", cookies);
        }

        postConn.setDoOutput(true);

        // Write the request body (sales order details) to the output stream
        try (OutputStream os = postConn.getOutputStream()) {
            byte[] input = requestBody.getBytes(StandardCharsets.UTF_8);
            os.write(input, 0, input.length);
        }

        int responseCode = postConn.getResponseCode();
        System.out.println("Response Code: " + responseCode);
        StringBuilder response = new StringBuilder();

        try (BufferedReader br = new BufferedReader(new InputStreamReader(
                responseCode >= 200 && responseCode < 300 ?
                        postConn.getInputStream() : postConn.getErrorStream(), StandardCharsets.UTF_8))) {
            String responseLine;
            while ((responseLine = br.readLine()) != null) {
                response.append(responseLine.trim());
            }
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Error reading response: " + e.getMessage());
        }

        if (responseCode == HttpURLConnection.HTTP_OK) {
            return ResponseEntity.ok(response.toString());
        } else {
            return ResponseEntity.status(responseCode).body("Error: " + response.toString());
        }}

}

