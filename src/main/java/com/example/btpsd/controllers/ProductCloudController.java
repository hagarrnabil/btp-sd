package com.example.btpsd.controllers;

import com.example.btpsd.commands.InvoiceMainItemCommand;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ArrayNode;
import com.fasterxml.jackson.databind.node.ObjectNode;
import lombok.RequiredArgsConstructor;
import org.apache.commons.codec.binary.Base64;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.nio.charset.StandardCharsets;

@RequiredArgsConstructor
@RestController
public class ProductCloudController {

    @RequestMapping(value = "/productcloud", method = RequestMethod.GET)
    public StringBuilder getAllProducts() throws Exception {

        final int BLOCK_SIZE = 1024;
        final int BUFFER_SIZE = 8 * BLOCK_SIZE;
        DataOutputStream dataOut = null;
        BufferedReader in = null;


        //API endpoint
        String url = "https://my418629.s4hana.cloud.sap/sap/opu/odata/sap/API_PRODUCT_SRV/A_Product?%24inlinecount=allpages&%24";


        URL urlObj = new URL(url);
        HttpURLConnection connection = (HttpURLConnection) urlObj.openConnection();
        String user = "BTP_USER1";
        String password = "#yiVfheJbFolFxgkEwCBFcWvYkPzrQDENEArAXn5";
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

        System.out.println(response.toString());


        return response;
    }

    @RequestMapping(value = "/productdescriptioncloud", method = RequestMethod.GET)
    public StringBuilder getAllProductsDesc() throws Exception {

        final int BLOCK_SIZE = 1024;
        final int BUFFER_SIZE = 8 * BLOCK_SIZE;
        DataOutputStream dataOut = null;
        BufferedReader in = null;


        //API endpoint
        String url = "https://my418629.s4hana.cloud.sap/sap/opu/odata/sap/API_PRODUCT_SRV/A_ProductDescription?%24inlinecount=allpages&%24";


        URL urlObj = new URL(url);
        HttpURLConnection connection = (HttpURLConnection) urlObj.openConnection();
        String user = "BTP_USER1";
        String password = "#yiVfheJbFolFxgkEwCBFcWvYkPzrQDENEArAXn5";
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

    @RequestMapping(value = "/allproductscloud", method = RequestMethod.GET)
    public StringBuilder getCombinedProductDetails() throws Exception {
        final int BLOCK_SIZE = 1024;
        final int BUFFER_SIZE = 8 * BLOCK_SIZE;

        // API endpoints
        String productUrl = "https://my418629.s4hana.cloud.sap/sap/opu/odata/sap/API_PRODUCT_SRV/A_Product?%24inlinecount=allpages&%24";
        String productDescUrl = "https://my418629.s4hana.cloud.sap/sap/opu/odata/sap/API_PRODUCT_SRV/A_ProductDescription?%24inlinecount=allpages&%24";

        // Fetch product data
        StringBuilder productResponse = fetchDataFromAPI(productUrl, BUFFER_SIZE);
        // Fetch product description data
        StringBuilder productDescResponse = fetchDataFromAPI(productDescUrl, BUFFER_SIZE);

        // Combine the responses here
        // (Assuming JSON responses, you would parse and combine product and product description arrays)

        ObjectMapper objectMapper = new ObjectMapper();

        // Parse product response
        JsonNode productJson = objectMapper.readTree(productResponse.toString());
        JsonNode productsArray = productJson.path("d").path("results");

        // Parse product description response
        JsonNode productDescJson = objectMapper.readTree(productDescResponse.toString());
        JsonNode productDescriptionsArray = productDescJson.path("d").path("results");

        // Combine product and product descriptions
        ArrayNode combinedArray = objectMapper.createArrayNode();

        for (int i = 0; i < productsArray.size(); i++) {
            JsonNode product = productsArray.get(i);
            JsonNode productDesc = productDescriptionsArray.get(i);

            ObjectNode combinedProduct = objectMapper.createObjectNode();
            combinedProduct.put("Product", product.path("Product").asText());
            combinedProduct.put("ProductType", product.path("ProductType").asText());
            combinedProduct.put("BaseUnit", product.path("BaseUnit").asText());
            combinedProduct.put("ProductDescription", productDesc.path("ProductDescription").asText());

            combinedArray.add(combinedProduct);
        }

        // Return the combined result
        String result = objectMapper.writeValueAsString(combinedArray);
        return new StringBuilder(result);
    }

    private StringBuilder fetchDataFromAPI(String url, final int BUFFER_SIZE) throws Exception {
        // Common logic for making an API call and returning the response
        URL urlObj = new URL(url);
        HttpURLConnection connection = (HttpURLConnection) urlObj.openConnection();
        String user = "BTP_USER1";
        String password = "#yiVfheJbFolFxgkEwCBFcWvYkPzrQDENEArAXn5";
        String auth = user + ":" + password;
        byte[] encodedAuth = Base64.encodeBase64(auth.getBytes(StandardCharsets.UTF_8));
        String authHeaderValue = "Basic " + new String(encodedAuth);

        connection.setRequestMethod("GET");
        connection.setRequestProperty("Authorization", authHeaderValue);
        connection.setRequestProperty("Accept", "application/json");

        connection.setDoInput(true);

        BufferedReader in = new BufferedReader(new InputStreamReader(connection.getInputStream()));
        StringBuilder response = new StringBuilder();
        char[] charArray = new char[BUFFER_SIZE];
        int charsCount;
        while ((charsCount = in.read(charArray)) != -1) {
            response.append(String.valueOf(charArray, 0, charsCount));
        }
        in.close();
        return response;
    }

}
