package com.example.btpsd.controllers;

import com.example.btpsd.model.UnitOfMeasurementCloud;
import com.example.btpsd.repositories.UomCloudRepository;
import org.apache.commons.codec.binary.Base64;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.nio.charset.StandardCharsets;

@RestController
public class UnitOfMeasurementCloudController {

//    @Autowired
//    UomCloudRepository uomCloudRepository;
    private final String USER_AGENT = "PostmanRuntime/7.37.0";

    @GetMapping("/measurementsCloud")
    private StringBuilder sendingGetRequest() throws Exception {

        String urlString = "https://my405604-api.s4hana.cloud.sap/sap/opu/odata/sap/YY1_UOM_CDS/YY1_UOM?$format=json";

        URL url = new URL(urlString);
        HttpURLConnection con = (HttpURLConnection) url.openConnection();

        con.setRequestMethod("GET");
        con.setRequestProperty("Content-Type", "*/*");
        String user = "CU_UOM";
        String password = "CgAHz8WybHmmnybVCPMuYJlBjXMLjESQp\\YmNLAa";
        String auth = user + ":" + password;
        byte[] encodedAuth = Base64.encodeBase64(auth.getBytes(StandardCharsets.UTF_8));
        String authHeaderValue = "Basic " + new String(encodedAuth);
        con.setRequestProperty("Authorization", authHeaderValue);
        con.setRequestProperty("User-Agent", USER_AGENT);
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

//        unitOfMeasurementCloud.setUnitOfMeasure();
//        uomCloudRepository.save(unitOfMeasurementCloud);

//        JSONObject obj = new JSONObject();
//        obj.put("UnitOfMeasure", unitOfMeasurementCloud.)
        return response;

    }
}