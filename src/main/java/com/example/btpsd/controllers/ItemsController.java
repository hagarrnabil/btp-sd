package com.example.btpsd.controllers;

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
public class ItemsController {

    @RequestMapping(value = "/salesorderitems", method = RequestMethod.GET)
    private StringBuilder getAllSalesOrderItems() throws Exception {

        final int BLOCK_SIZE = 1024;
        final int BUFFER_SIZE = 8 * BLOCK_SIZE;
        DataOutputStream dataOut = null;
        BufferedReader in = null;


        //API endpoint
        String url = "https://my418629.s4hana.cloud.sap/sap/opu/odata/sap/API_SALES_ORDER_SRV/A_SalesOrder('1')/to_Item?%24inlinecount=allpages&%24top=50";


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

}
