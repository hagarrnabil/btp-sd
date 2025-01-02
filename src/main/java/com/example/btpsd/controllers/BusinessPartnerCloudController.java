package com.example.btpsd.controllers;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.nio.charset.StandardCharsets;
import java.util.Base64;

@RequiredArgsConstructor
@RestController
public class BusinessPartnerCloudController {


    @RequestMapping(value = "/businesspartner/{customerNumber}", method = RequestMethod.GET)
    public StringBuilder getBusinessPartnerSalesArea(@PathVariable String customerNumber) throws Exception {

        final int BLOCK_SIZE = 1024;
        final int BUFFER_SIZE = 8 * BLOCK_SIZE;
        BufferedReader in = null;

        // API endpoint with customer number variable
        String url = "https://my418629.s4hana.cloud.sap/sap/opu/odata/sap/API_BUSINESS_PARTNER/A_Customer('" + customerNumber + "')/to_CustomerSalesArea?$inlinecount=allpages&$top=50";

        URL urlObj = new URL(url);
        HttpURLConnection connection = (HttpURLConnection) urlObj.openConnection();
        String user = "BTP_USER1";
        String password = "#yiVfheJbFolFxgkEwCBFcWvYkPzrQDENEArAXn5";
        String auth = user + ":" + password;
        byte[] encodedAuth = Base64.getEncoder().encode(auth.getBytes(StandardCharsets.UTF_8));
        String authHeaderValue = "Basic " + new String(encodedAuth);

        // Setting request method
        connection.setRequestMethod("GET");

        // Adding headers
        connection.setRequestProperty("Authorization", authHeaderValue);
        connection.setRequestProperty("Accept", "application/json");

        connection.setDoInput(true);

        int responseCode = connection.getResponseCode();
        if (responseCode != HttpURLConnection.HTTP_OK) {
            throw new RuntimeException("Failed : HTTP error code : " + responseCode);
        }

        in = new BufferedReader(new InputStreamReader(connection.getInputStream()));
        StringBuilder response = new StringBuilder();
        char[] charArray = new char[BUFFER_SIZE];
        int charsCount = 0;
        while ((charsCount = in.read(charArray)) != -1) {
            response.append(String.valueOf(charArray, 0, charsCount));
        }

        // Return the response as a string
        return response;
    }
}
