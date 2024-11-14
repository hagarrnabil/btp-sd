package com.example.btpsd.controllers;

import com.example.btpsd.commands.ExecutionOrderMainCommand;
import com.example.btpsd.commands.InvoiceMainItemCommand;
import com.example.btpsd.converters.CurrencyCommandToCurrency;
import com.example.btpsd.converters.ExecutionOrderMainToExecutionOrderMainCommand;
import com.example.btpsd.converters.InvoiceMainItemToInvoiceMainItemCommand;
import com.example.btpsd.model.ExecutionOrderMain;
import com.example.btpsd.model.InvoiceMainItem;
import com.example.btpsd.repositories.ExecutionOrderMainRepository;
import com.example.btpsd.repositories.InvoiceMainItemRepository;
import com.example.btpsd.services.ExecutionOrderMainService;
import com.example.btpsd.services.InvoiceMainItemService;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import org.apache.commons.codec.binary.Base64;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
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

    private ExecutionOrderMainRepository executionOrderMainRepository;
    private ExecutionOrderMainToExecutionOrderMainCommand executionOrderMainToExecutionOrderMainCommand;
    private static final Logger logger = LoggerFactory.getLogger(SalesOrderCloudController.class);
    @Autowired
    private ExecutionOrderMainService executionOrderMainService;
    @Autowired
    private InvoiceMainItemService invoiceMainItemService;

    public ExecutionOrderMainCommand getExcOrderWithTotalHeader(Long id) {
        ExecutionOrderMain executionOrderMain = executionOrderMainRepository.findById(id).orElseThrow(() -> new RuntimeException("Invoice not found"));

        // Calculate the total header and set it
        Double totalHeader = executionOrderMainService.getTotalHeader();
        ExecutionOrderMainCommand command = executionOrderMainToExecutionOrderMainCommand.convert(executionOrderMain);
        command.setTotalHeader(totalHeader);

        return command;
    }



    @RequestMapping(value = "/salesordercloud", method = RequestMethod.GET)
    public StringBuilder getAllSalesOrders() throws Exception {

        final int BLOCK_SIZE = 1024;
        final int BUFFER_SIZE = 8 * BLOCK_SIZE;
        BufferedReader in = null;

        // API endpoint
        String url = "https://my405604-api.s4hana.cloud.sap/sap/opu/odata/sap/API_SALES_ORDER_SRV/A_SalesOrder?%24inlinecount=allpages&%24top=50";

        URL urlObj = new URL(url);
        HttpURLConnection connection = (HttpURLConnection) urlObj.openConnection();
        String user = "BTP_USER1";
        String password = "Gw}tDHMrhuAWnzRWkwEbpcguYKsxugDuoKMeJ8Lt";
        String auth = user + ":" + password;
        byte[] encodedAuth = Base64.encodeBase64(auth.getBytes(StandardCharsets.UTF_8));
        String authHeaderValue = "Basic " + new String(encodedAuth);

        // Setting request method and headers
        connection.setRequestMethod("GET");
        connection.setRequestProperty("Authorization", authHeaderValue);
        connection.setRequestProperty("Accept", "application/json");

        // Reading response
        int responseCode = connection.getResponseCode();
        if (responseCode != HttpURLConnection.HTTP_OK) {
            throw new RuntimeException("Failed to get sales orders: HTTP code " + responseCode);
        }

        in = new BufferedReader(new InputStreamReader(connection.getInputStream()));
        StringBuilder response = new StringBuilder();
        char[] charArray = new char[BUFFER_SIZE];
        int charsCount;
        while ((charsCount = in.read(charArray)) != -1) {
            response.append(charArray, 0, charsCount);
        }

        // Printing response for debugging
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

    public StringBuilder getSalesOrderItem(String salesOrder, String salesOrderItem) {
        logger.debug("Entered getSalesOrderItem method with SalesOrder: {} and SalesOrderItem: {}", salesOrder, salesOrderItem);

        final int BLOCK_SIZE = 1024;
        final int BUFFER_SIZE = 8 * BLOCK_SIZE;

        // Construct API endpoint with dynamic SalesOrder and SalesOrderItem
        String url = "https://my405604-api.s4hana.cloud.sap/sap/opu/odata/sap/API_SALES_ORDER_SRV/A_SalesOrderItem(SalesOrder='"
                + salesOrder + "',SalesOrderItem='" + salesOrderItem + "')/to_SalesOrder";

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
        logger.debug("Exiting getSalesOrderItem method");
        return response;
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
    public StringBuilder getSalesQuotation() throws Exception {

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

    public StringBuilder getSalesQuotationItemById(String salesQuotation, String salesQuotationItem) {
        logger.debug("Entered getSalesQuotationItem method with SalesQuotation: {} and SalesQuotationItem: {}", salesQuotation, salesQuotationItem);

        final int BLOCK_SIZE = 1024;
        final int BUFFER_SIZE = 8 * BLOCK_SIZE;

        // Construct API endpoint with dynamic SalesQuotation and SalesQuotationItem
        String url = "https://my405604-api.s4hana.cloud.sap/sap/opu/odata/sap/API_SALES_QUOTATION_SRV/A_SalesQuotationItem(SalesQuotation='"
                + salesQuotation + "',SalesQuotationItem='" + salesQuotationItem + "')/to_SalesQuotation";

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
        logger.debug("Exiting getSalesQuotationItem method");
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
    public StringBuilder getDebitMemo() throws Exception {

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

    public StringBuilder getDebitMemoRequestItem(String debitMemoRequest, String debitMemoRequestItem) {
        logger.debug("Entered getDebitMemoRequestItem method with DebitMemoRequest: {} and DebitMemoRequestItem: {}", debitMemoRequest, debitMemoRequestItem);

        final int BLOCK_SIZE = 1024;
        final int BUFFER_SIZE = 8 * BLOCK_SIZE;

        // Construct API endpoint with dynamic DebitMemoRequest and DebitMemoRequestItem
        String url = "https://my405604-api.s4hana.cloud.sap/sap/opu/odata/sap/API_DEBIT_MEMO_REQUEST_SRV/A_DebitMemoRequestItem(DebitMemoRequest='"
                + debitMemoRequest + "',DebitMemoRequestItem='" + debitMemoRequestItem + "')/to_DebitMemoRequest";

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
        logger.debug("Exiting getDebitMemoRequestItem method");
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

//    @PostMapping("/debitmemopostcloud")
//    public ResponseEntity<String> postDebitMemo(@RequestBody String requestBody) throws Exception {
//
//        String url = "https://my405604-api.s4hana.cloud.sap/sap/opu/odata/sap/API_DEBIT_MEMO_REQUEST_SRV/A_DebitMemoRequest";
//        String tokenURL = "https://my405604-api.s4hana.cloud.sap/sap/opu/odata/sap/API_DEBIT_MEMO_REQUEST_SRV/A_DebitMemoRequest?%24inlinecount=allpages&%24top=50";
//
//        // Step 1: Fetch CSRF Token with a GET request
//        HttpURLConnection tokenConn = (HttpURLConnection) new URL(tokenURL).openConnection();
//        String user = "BTP_USER1";
//        String password = "Gw}tDHMrhuAWnzRWkwEbpcguYKsxugDuoKMeJ8Lt";
//        String auth = user + ":" + password;
//        byte[] encodedAuth = Base64.encodeBase64(auth.getBytes(StandardCharsets.UTF_8));
//        String authHeaderValue = "Basic " + new String(encodedAuth);
//
//        tokenConn.setRequestMethod("GET");
//        tokenConn.setRequestProperty("Authorization", authHeaderValue);
//        tokenConn.setRequestProperty("x-csrf-token", "Fetch");
//        tokenConn.setRequestProperty("Accept", "application/json");
//
//        // Get session cookies for CSRF validation
//        String cookies = tokenConn.getHeaderField("Set-Cookie");
//
//        // Read the CSRF token from the response headers
//        String csrfToken = tokenConn.getHeaderField("x-csrf-token");
//
//        System.out.println("CSRF Token: " + csrfToken);
//        System.out.println("Cookies: " + cookies);
//
//        if (csrfToken == null || csrfToken.isEmpty()) {
//            return ResponseEntity.status(HttpStatus.FORBIDDEN).body("Failed to fetch CSRF token");
//        }
//
//        // Step 2: Send POST request with CSRF token and session cookies
//        HttpURLConnection postConn = (HttpURLConnection) new URL(url).openConnection();
//        postConn.setRequestMethod("POST");
//        postConn.setRequestProperty("Authorization", authHeaderValue);
//        postConn.setRequestProperty("x-csrf-token", csrfToken);
//        postConn.setRequestProperty("Content-Type", "application/json");
//
//        // Attach session cookies to maintain the session
//        if (cookies != null) {
//            postConn.setRequestProperty("Cookie", cookies);
//        }
//
//        postConn.setDoOutput(true);
//
//        // Write the request body (sales order details) to the output stream
//        try (OutputStream os = postConn.getOutputStream()) {
//            byte[] input = requestBody.getBytes(StandardCharsets.UTF_8);
//            os.write(input, 0, input.length);
//        }
//
//        int responseCode = postConn.getResponseCode();
//        System.out.println("Response Code: " + responseCode);
//        StringBuilder response = new StringBuilder();
//
//        try (BufferedReader br = new BufferedReader(new InputStreamReader(
//                responseCode >= 200 && responseCode < 300 ?
//                        postConn.getInputStream() : postConn.getErrorStream(), StandardCharsets.UTF_8))) {
//            String responseLine;
//            while ((responseLine = br.readLine()) != null) {
//                response.append(responseLine.trim());
//            }
//        } catch (Exception e) {
//            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Error reading response: " + e.getMessage());
//        }
//
//        if (responseCode == HttpURLConnection.HTTP_OK) {
//            return ResponseEntity.ok(response.toString());
//        } else {
//            return ResponseEntity.status(responseCode).body("Error: " + response.toString());
//        }
//    }

    @RequestMapping(value = "/salesorderitempricingcloudpost/{SalesOrder}/{SalesOrderItem}", method = RequestMethod.POST)
    public ResponseEntity<String> postSalesOrderItemPricing(@PathVariable("SalesOrder") String SalesOrder,
                                                            @PathVariable("SalesOrderItem") String SalesOrderItem,
                                                            @RequestBody String requestBody) throws Exception {
        logger.info("Received SalesOrder: {}, SalesOrderItem: {}", SalesOrder, SalesOrderItem);

        // Step 1: Fetch InvoiceMainItemCommand to get totalHeader
        ExecutionOrderMainCommand executionOrderMainCommand = getExcOrderWithTotalHeader(Long.valueOf(SalesOrder));


        // Get the totalHeader from the command
        Double totalHeader = executionOrderMainCommand.getTotalHeader();
        if (totalHeader == null) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Total header is null for SalesOrder: " + SalesOrder);
        }

        // Step 2: Modify the request body to include 'ConditionType' which maps to totalHeader
        JSONObject requestBodyJson = new JSONObject(requestBody);
        requestBodyJson.put("ConditionRateAmount", totalHeader);

        String modifiedRequestBody = requestBodyJson.toString();

        // URL to get the CSRF Token
        String tokenURL = "https://my405604-api.s4hana.cloud.sap/sap/opu/odata/sap/API_SALES_ORDER_SRV/A_SalesOrderItem(SalesOrder='"
                + SalesOrder + "',SalesOrderItem='" + SalesOrderItem + "')/to_PricingElement";

        // URL to post the data after fetching the CSRF token
        String postURL = "https://my405604-api.s4hana.cloud.sap/sap/opu/odata4/sap/api_salesorder/srvd_a2x/sap/salesorder/0001/SalesOrderItem/"
                + SalesOrder + "/" + SalesOrderItem + "/_ItemPricingElement";

        // Step 3: Fetch CSRF Token
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

        // Fetch the CSRF token from response headers
        String csrfToken = tokenConn.getHeaderField("x-csrf-token");

        logger.info("CSRF Token: {}", csrfToken);
        logger.info("Cookies: {}", cookies);

        if (csrfToken == null || csrfToken.isEmpty()) {
            return ResponseEntity.status(HttpStatus.FORBIDDEN).body("Failed to fetch CSRF token");
        }

        // Step 4: Send POST request with CSRF token and session cookies
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
        try (OutputStream os = postConn.getOutputStream()) {
            byte[] input = modifiedRequestBody.getBytes(StandardCharsets.UTF_8);
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

