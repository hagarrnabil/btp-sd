package com.example.btpsd.controllers;

import org.apache.commons.codec.binary.Base64;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.nio.charset.StandardCharsets;

@RestController
@CrossOrigin(origins = "http://localhost:4200", maxAge = 3600)
public class UnitOfMeasurementCloudController {


    @GetMapping("/measurementsCloud")
    private StringBuilder sendingGetRequest() throws Exception {

        String urlString = "https://my418629.s4hana.cloud.sap/sap/opu/odata/sap/YY1_UOM4_CDS/YY1_UOM4?$format=json";

        URL url = new URL(urlString);
        HttpURLConnection con = (HttpURLConnection) url.openConnection();

        con.setRequestMethod("GET");
        con.setRequestProperty("Content-Type", "*/*");
        String user = "UOM_USER4";
        String password = "s3ZhGnQXEymrUcgCPXR\\ZBPgDAeKYbxLEaozZQPv";
        String auth = user + ":" + password;
        byte[] encodedAuth = Base64.encodeBase64(auth.getBytes(StandardCharsets.UTF_8));
        String authHeaderValue = "Basic " + new String(encodedAuth);
        con.setRequestProperty("Authorization", authHeaderValue);
        con.setRequestProperty("Accept", "*/*");

        int responseCode = con.getResponseCode();
        System.out.println("Sending get request : " + url);
        System.out.println("Response code : " + responseCode);


        StringBuilder response = new StringBuilder();
        try (BufferedReader br = new BufferedReader(
                new InputStreamReader(con.getInputStream(), "utf-8"))) {
            String responseLine = null;
            while ((responseLine = br.readLine()) != null) {
                response.append(responseLine.trim());
            }
            System.out.println(response.toString());
        }

        return response;

    }
}