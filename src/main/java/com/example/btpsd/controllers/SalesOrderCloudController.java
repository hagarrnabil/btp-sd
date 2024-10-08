package com.example.btpsd.controllers;

import lombok.RequiredArgsConstructor;
import org.apache.commons.codec.binary.Base64;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

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

    private static final Logger logger = LoggerFactory.getLogger(SalesOrderCloudController.class);

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

    @RequestMapping(value = "/salesorderitemscloud", method = RequestMethod.GET)
    private StringBuilder getAllSalesOrderItems() throws Exception {

        final int BLOCK_SIZE = 1024;
        final int BUFFER_SIZE = 8 * BLOCK_SIZE;
        DataOutputStream dataOut = null;
        BufferedReader in = null;


        //API endpoint
        String url = "https://my405604-api.s4hana.cloud.sap/sap/opu/odata/sap/API_SALES_ORDER_SRV/A_SalesOrderItem?%24inlinecount=allpages&%24top=50";


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


    @RequestMapping(value = "/salesorderitemcloud/{SalesOrderID}", method = RequestMethod.GET)
    private StringBuilder getSalesOrderItems(@PathVariable("SalesOrderID") String salesOrderID) throws Exception {

        final int BLOCK_SIZE = 1024;
        final int BUFFER_SIZE = 8 * BLOCK_SIZE;
        BufferedReader in = null;

        // API endpoint with dynamic SalesOrderID
        String url = "https://my405604-api.s4hana.cloud.sap/sap/opu/odata/sap/API_SALES_ORDER_SRV/A_SalesOrder('" + salesOrderID + "')/to_Item?%24inlinecount=allpages&%24top=50";

        URL urlObj = new URL(url);
        HttpURLConnection connection = (HttpURLConnection) urlObj.openConnection();

        String user = "BTP_USER1";
        String password = "Gw}tDHMrhuAWnzRWkwEbpcguYKsxugDuoKMeJ8Lt";
        String auth = user + ":" + password;
        byte[] encodedAuth = Base64.encodeBase64(auth.getBytes(StandardCharsets.UTF_8));
        String authHeaderValue = "Basic " + new String(encodedAuth);

        // Setting request method to GET
        connection.setRequestMethod("GET");

        // Adding headers
        connection.setRequestProperty("Authorization", authHeaderValue);
        connection.setRequestProperty("Accept", "application/json");
        connection.setDoInput(true);

        int responseCode = connection.getResponseCode();

        if (responseCode == HttpURLConnection.HTTP_OK) {
            // Read the response
            in = new BufferedReader(new InputStreamReader(connection.getInputStream()));
            StringBuilder response = new StringBuilder();
            char[] charArray = new char[BUFFER_SIZE];
            int charsCount;
            while ((charsCount = in.read(charArray)) != -1) {
                response.append(String.valueOf(charArray, 0, charsCount));
            }

            // Print and return the response
            System.out.println(response.toString());
            return response;

        } else {
            // Handle error
            throw new Exception("Error in API call, Response Code: " + responseCode);
        }
    }


    @RequestMapping(value = "/salesorderallpricingcloud", method = RequestMethod.GET)
    private StringBuilder getSalesOrderPricingElement() throws Exception {

        final int BLOCK_SIZE = 1024;
        final int BUFFER_SIZE = 8 * BLOCK_SIZE;
        DataOutputStream dataOut = null;
        BufferedReader in = null;


        //API endpoint
        String url = "https://my405604-api.s4hana.cloud.sap/sap/opu/odata/sap/API_SALES_ORDER_SRV/A_SalesOrderItemPrElement?%24inlinecount=allpages&%24top=50";


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

    @RequestMapping(value = "/salesorderpricingcloud/{SalesOrder}/{SalesOrderItem}", method = RequestMethod.GET)
    public StringBuilder getSalesOrderPricing(@PathVariable("SalesOrder") String salesOrder,
                                              @PathVariable("SalesOrderItem") String salesOrderItem) throws Exception {

        final int BLOCK_SIZE = 1024;
        final int BUFFER_SIZE = 8 * BLOCK_SIZE;


        // API endpoint with dynamic SalesOrder and SalesOrderItem
        String url = "https://my405604-api.s4hana.cloud.sap/sap/opu/odata/sap/API_SALES_ORDER_SRV/A_SalesOrderItem(SalesOrder='" + salesOrder + "',SalesOrderItem='" + salesOrderItem + "')/to_PricingElement?%24inlinecount=allpages&%24top=50";

        URL urlObj = new URL(url);
        HttpURLConnection connection = (HttpURLConnection) urlObj.openConnection();

        String user = "BTP_USER1";
        String password = "Gw}tDHMrhuAWnzRWkwEbpcguYKsxugDuoKMeJ8Lt";
        String auth = user + ":" + password;
        byte[] encodedAuth = Base64.encodeBase64(auth.getBytes(StandardCharsets.UTF_8));
        String authHeaderValue = "Basic " + new String(encodedAuth);

        // Setting request method to GET
        connection.setRequestMethod("GET");

        // Adding headers
        connection.setRequestProperty("Authorization", authHeaderValue);
        connection.setRequestProperty("Accept", "application/json");

        int responseCode = connection.getResponseCode();
        logger.info("Response code: {}", responseCode);

        BufferedReader in = new BufferedReader(new InputStreamReader(connection.getInputStream()));
        StringBuilder response = new StringBuilder();
        char[] charArray = new char[BUFFER_SIZE];
        int charsCount;
        while ((charsCount = in.read(charArray)) != -1) {
            response.append(String.valueOf(charArray, 0, charsCount));
        }

        logger.info("Response received: {}", response);

        return response;
    }

    @RequestMapping(value = "/salesquotationcloud", method = RequestMethod.GET)
    private StringBuilder getSalesQuotation() throws Exception {

        final int BLOCK_SIZE = 1024;
        final int BUFFER_SIZE = 8 * BLOCK_SIZE;
        DataOutputStream dataOut = null;
        BufferedReader in = null;


        //API endpoint
        String url = "https://my405604-api.s4hana.cloud.sap/sap/opu/odata/sap/API_SALES_QUOTATION_SRV/A_SalesQuotation?%24inlinecount=allpages&%24top=50";


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

    @RequestMapping(value = "/salesquotationitemcloud/{SalesQuotationID}", method = RequestMethod.GET)
    private StringBuilder getSalesQuotationItem(@PathVariable("SalesQuotationID") String salesQuotationID) throws Exception {

        final int BLOCK_SIZE = 1024;
        final int BUFFER_SIZE = 8 * BLOCK_SIZE;
        BufferedReader in = null;

        // API endpoint with dynamic SalesOrderID
        String url = "https://my405604-api.s4hana.cloud.sap/sap/opu/odata/sap/API_SALES_QUOTATION_SRV/A_SalesQuotation('" + salesQuotationID + "')/to_Item?%24inlinecount=allpages&%24top=50";

        URL urlObj = new URL(url);
        HttpURLConnection connection = (HttpURLConnection) urlObj.openConnection();

        String user = "BTP_USER1";
        String password = "Gw}tDHMrhuAWnzRWkwEbpcguYKsxugDuoKMeJ8Lt";
        String auth = user + ":" + password;
        byte[] encodedAuth = Base64.encodeBase64(auth.getBytes(StandardCharsets.UTF_8));
        String authHeaderValue = "Basic " + new String(encodedAuth);

        // Setting request method to GET
        connection.setRequestMethod("GET");

        // Adding headers
        connection.setRequestProperty("Authorization", authHeaderValue);
        connection.setRequestProperty("Accept", "application/json");
        connection.setDoInput(true);

        int responseCode = connection.getResponseCode();

        if (responseCode == HttpURLConnection.HTTP_OK) {
            // Read the response
            in = new BufferedReader(new InputStreamReader(connection.getInputStream()));
            StringBuilder response = new StringBuilder();
            char[] charArray = new char[BUFFER_SIZE];
            int charsCount;
            while ((charsCount = in.read(charArray)) != -1) {
                response.append(String.valueOf(charArray, 0, charsCount));
            }

            // Print and return the response
            System.out.println(response.toString());
            return response;

        } else {
            // Handle error
            throw new Exception("Error in API call, Response Code: " + responseCode);
        }
    }

    @RequestMapping(value = "/salesquotationitemscloud", method = RequestMethod.GET)
    private StringBuilder getSalesQuotationItems() throws Exception {

        final int BLOCK_SIZE = 1024;
        final int BUFFER_SIZE = 8 * BLOCK_SIZE;
        DataOutputStream dataOut = null;
        BufferedReader in = null;


        //API endpoint
        String url = "https://my405604-api.s4hana.cloud.sap/sap/opu/odata/sap/API_SALES_QUOTATION_SRV/A_SalesQuotationItem?%24inlinecount=allpages&%24top=50";


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

    @RequestMapping(value = "/salesquotationpricingcloud/{SalesQuotation}/{SalesQuotationItem}", method = RequestMethod.GET)
    public StringBuilder getSalesQuotationPricing(@PathVariable("SalesQuotation") String salesQuotation,
                                              @PathVariable("SalesQuotationItem") String salesQuotationItem) {
        logger.debug("Entered getSalesOrderPricing method with SalesQuotation: {} and SalesQuotationItem: {}", salesQuotation, salesQuotationItem);

        final int BLOCK_SIZE = 1024;
        final int BUFFER_SIZE = 8 * BLOCK_SIZE;

        // Construct API endpoint with dynamic SalesQuotation and SalesQuotationItem
        String url = "https://my405604-api.s4hana.cloud.sap/sap/opu/odata/sap/API_SALES_QUOTATION_SRV/A_SalesQuotationItem(SalesQuotation='"
                + salesQuotation + "',SalesQuotationItem='" + salesQuotationItem + "')/to_PricingElement?%24inlinecount=allpages&%24top=50";

        logger.debug("Constructed URL: {}", url);

        HttpURLConnection connection = null;
        StringBuilder response = new StringBuilder();

        try {
            URL urlObj = new URL(url);
            connection = (HttpURLConnection) urlObj.openConnection();

            String user = "BTP_USER1";
            String password = "Gw}tDHMrhuAWnzRWkwEbpcguYKsxugDuoKMeJ8Lt";
            String auth = user + ":" + password;
            byte[] encodedAuth = Base64.encodeBase64(auth.getBytes(StandardCharsets.UTF_8));
            String authHeaderValue = "Basic " + new String(encodedAuth);

            // Setting request method to GET
            connection.setRequestMethod("GET");

            // Adding headers
            connection.setRequestProperty("Authorization", authHeaderValue);
            connection.setRequestProperty("Accept", "application/json");

            int responseCode = connection.getResponseCode();
            logger.info("Response code: {}", responseCode);

            if (responseCode == HttpURLConnection.HTTP_OK) {
                // Read the response
                BufferedReader in = new BufferedReader(new InputStreamReader(connection.getInputStream()));
                char[] charArray = new char[BUFFER_SIZE];
                int charsCount;
                while ((charsCount = in.read(charArray)) != -1) {
                    response.append(String.valueOf(charArray, 0, charsCount));
                }
                in.close();
                logger.info("Response received: {}", response);
            } else {
                // Handle non-OK response codes
                logger.error("Failed to fetch data. Response Code: {}", responseCode);
                return new StringBuilder("Error: " + responseCode);
            }
        } catch (IOException e) {
            logger.error("IOException occurred: ", e);
            return new StringBuilder("IOException: " + e.getMessage());
        } catch (Exception e) {
            logger.error("An unexpected error occurred: ", e);
            return new StringBuilder("Error: " + e.getMessage());
        } finally {
            if (connection != null) {
                connection.disconnect();
            }
        }
        logger.debug("Exiting getSalesOrderPricing method");
        return response;
    }



    @RequestMapping(value = "/debitmemocloud", method = RequestMethod.GET)
    private StringBuilder getDebitMemo() throws Exception {

        final int BLOCK_SIZE = 1024;
        final int BUFFER_SIZE = 8 * BLOCK_SIZE;
        DataOutputStream dataOut = null;
        BufferedReader in = null;


        //API endpoint
        String url = "https://my405604-api.s4hana.cloud.sap/sap/opu/odata/sap/API_DEBIT_MEMO_REQUEST_SRV/A_DebitMemoRequest?%24inlinecount=allpages&%24top=50";


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

    @RequestMapping(value = "/debitmemoitemscloud", method = RequestMethod.GET)
    private StringBuilder getDebitMemoPricing() throws Exception {

        final int BLOCK_SIZE = 1024;
        final int BUFFER_SIZE = 8 * BLOCK_SIZE;
        DataOutputStream dataOut = null;
        BufferedReader in = null;


        //API endpoint
        String url = "https://my405604-api.s4hana.cloud.sap/sap/opu/odata/sap/API_DEBIT_MEMO_REQUEST_SRV/A_DebitMemoReqItemPrcgElmnt?%24inlinecount=allpages&%24top=50";


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
        }
    }

    @PostMapping("/salesquotationpostcloud")
    public ResponseEntity<String> postSalesQuotation(@RequestBody String requestBody) throws Exception {

        String url = "https://my405604-api.s4hana.cloud.sap/sap/opu/odata/sap/API_SALES_QUOTATION_SRV/A_SalesQuotation";
        String tokenURL = "https://my405604-api.s4hana.cloud.sap/sap/opu/odata/sap/API_SALES_QUOTATION_SRV/A_SalesQuotation?%24inlinecount=allpages&%24top=50";

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
        }
    }

    @PostMapping("/debitmemopostcloud")
    public ResponseEntity<String> postDebitMemo(@RequestBody String requestBody) throws Exception {

        String url = "https://my405604-api.s4hana.cloud.sap/sap/opu/odata/sap/API_DEBIT_MEMO_REQUEST_SRV/A_DebitMemoRequest";
        String tokenURL = "https://my405604-api.s4hana.cloud.sap/sap/opu/odata/sap/API_DEBIT_MEMO_REQUEST_SRV/A_DebitMemoRequest?%24inlinecount=allpages&%24top=50";

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
        }
    }

    @RequestMapping(value = "/salesorderitempricingcloudpost/{SalesOrder}/{SalesOrderItem}", method = RequestMethod.POST)
    public ResponseEntity<String> postSalesOrderItemPricing(@PathVariable("SalesOrder") String SalesOrder,
                                                            @PathVariable("SalesOrderItem") String SalesOrderItem,
                                                            @RequestBody String requestBody) throws Exception {

        logger.info("Received SalesOrder: {}, SalesOrderItem: {}", SalesOrder, SalesOrderItem);

        // URL to get the CSRF Token (assuming you use the same for token fetching)
        String tokenURL = "https://my405604-api.s4hana.cloud.sap/sap/opu/odata/sap/API_SALES_ORDER_SRV/A_SalesOrderItem(SalesOrder='" + SalesOrder + "',SalesOrderItem='" + SalesOrderItem + "')/to_PricingElement";

        // The URL where you will post the data after fetching the token
        String postURL = "https://my405604-api.s4hana.cloud.sap/sap/opu/odata4/sap/api_salesorder/srvd_a2x/sap/salesorder/0001/SalesOrderItem/"
                + SalesOrder + "/" + SalesOrderItem +  "/_ItemPricingElement";

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

        logger.info("CSRF Token: {}", csrfToken);
        logger.info("Cookies: {}", cookies);

        if (csrfToken == null || csrfToken.isEmpty()) {
            return ResponseEntity.status(HttpStatus.FORBIDDEN).body("Failed to fetch CSRF token");
        }

        // Step 2: Send POST request with CSRF token and session cookies
        HttpURLConnection postConn = (HttpURLConnection) new URL(postURL).openConnection();
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
        logger.info("Response Code: {}", responseCode);

        StringBuilder response = new StringBuilder();

        try (BufferedReader br = new BufferedReader(new InputStreamReader(
                responseCode >= 200 && responseCode < 300 ? postConn.getInputStream() : postConn.getErrorStream(),
                StandardCharsets.UTF_8))) {
            String responseLine;
            while ((responseLine = br.readLine()) != null) {
                response.append(responseLine.trim());
            }
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Error reading response: " + e.getMessage());
        }

        // Check the response code and return the result
        if (responseCode == HttpURLConnection.HTTP_OK) {
            return ResponseEntity.ok(response.toString());
        } else {
            return ResponseEntity.status(responseCode).body("Error: " + response.toString());
        }
    }

    @RequestMapping(value = "/salesquotationricingcloudpatch/{SalesQuotation}/{SalesQuotationItem}/{PricingProcedureStep}/{PricingProcedureCounter}", method = RequestMethod.PATCH)
    public ResponseEntity<String> patchSalesQuotationItemPricing(@PathVariable("SalesQuotation") String SalesQuotation,
                                                                 @PathVariable("SalesQuotationItem") String SalesQuotationItem,
                                                                 @PathVariable("PricingProcedureStep") String PricingProcedureStep,
                                                                 @PathVariable("PricingProcedureCounter") String PricingProcedureCounter,
                                                                 @RequestBody String requestBody) throws Exception {

        logger.info("Received SalesQuotation: {}, SalesQuotationItem: {}, PricingProcedureStep: {}, PricingProcedureCounter: {}",
                SalesQuotation, SalesQuotationItem, PricingProcedureStep, PricingProcedureCounter);

        // URL to get the CSRF Token (assuming you use the same for token fetching)
        String tokenURL = "https://my405604-api.s4hana.cloud.sap/sap/opu/odata/sap/API_SALES_QUOTATION_SRV/A_SalesQuotationItem(SalesQuotation='"
                + SalesQuotation + "',SalesQuotationItem='" + SalesQuotationItem + "')/to_PricingElement?%24inlinecount=allpages&%24top=50";

        // The URL where you will PATCH the data after fetching the token
        String patchURL = "https://my405604-api.s4hana.cloud.sap/sap/opu/odata/sap/API_SALES_QUOTATION_SRV/A_SalesQuotationItemPrcgElmnt(SalesQuotation='"
                + SalesQuotation + "',SalesQuotationItem='" + SalesQuotationItem + "',PricingProcedureStep='"
                + PricingProcedureStep + "',PricingProcedureCounter='" + PricingProcedureCounter + "')";

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

        logger.info("CSRF Token: {}", csrfToken);
        logger.info("Cookies: {}", cookies);

        if (csrfToken == null || csrfToken.isEmpty()) {
            return ResponseEntity.status(HttpStatus.FORBIDDEN).body("Failed to fetch CSRF token");
        }

        // Step 2: Send PATCH request with CSRF token and session cookies
        HttpURLConnection patchConn = (HttpURLConnection) new URL(patchURL).openConnection();
        patchConn.setRequestMethod("POST"); // Use POST instead of PATCH
        patchConn.setRequestProperty("X-HTTP-Method-Override", "PATCH"); // This tells the server to treat it as PATCH
        patchConn.setRequestProperty("Authorization", authHeaderValue);
        patchConn.setRequestProperty("x-csrf-token", csrfToken);
        patchConn.setRequestProperty("Content-Type", "application/json");

        // Attach session cookies to maintain the session
        if (cookies != null) {
            patchConn.setRequestProperty("Cookie", cookies);
        }

        patchConn.setDoOutput(true);

        // Write the request body (quotation details) to the output stream
        try (OutputStream os = patchConn.getOutputStream()) {
            byte[] input = requestBody.getBytes(StandardCharsets.UTF_8);
            os.write(input, 0, input.length);
        }

        int responseCode = patchConn.getResponseCode();
        logger.info("Response Code: {}", responseCode);

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

        // Check the response code and return the result
        if (responseCode == HttpURLConnection.HTTP_OK || responseCode == HttpURLConnection.HTTP_NO_CONTENT) {
            return ResponseEntity.ok(response.toString());
        } else {
            return ResponseEntity.status(responseCode).body("Error: " + response.toString());
        }
    }

}

