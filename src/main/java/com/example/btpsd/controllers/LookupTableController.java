package com.example.btpsd.controllers;

import com.example.datamodel.odata.namespaces.maintenancelookuptabledata.LookupTables;
import com.example.datamodel.odata.services.DefaultMaintenanceLookupTableDataService;
import com.google.gson.Gson;
import com.sap.cloud.sdk.cloudplatform.connectivity.DefaultDestination;
import com.sap.cloud.sdk.cloudplatform.connectivity.HttpDestination;
import io.vavr.collection.List;
import lombok.RequiredArgsConstructor;
import org.apache.commons.codec.binary.Base64;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.nio.charset.StandardCharsets;

@RequiredArgsConstructor
@RestController
public class LookupTableController {


    @RequestMapping(value = "/lookuptables", method = RequestMethod.GET)
    private StringBuilder getAllLookupTables() throws Exception {

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

}

//        String urlString = "https://my405604-api.s4hana.cloud.sap/sap/opu/odata/sap/API_SALES_ORDER_SRV";
//
//        URL url = new URL(urlString);
//        HttpURLConnection con = (HttpURLConnection) url.openConnection();
//
//        con.setRequestMethod("GET");
//        con.setRequestProperty("Content-Type", "*/*");
//        String user = "BTP_USER1";
//        String password = "Gw}tDHMrhuAWnzRWkwEbpcguYKsxugDuoKMeJ8Lt";
//        String auth = user + ":" + password;
//        byte[] encodedAuth = Base64.encodeBase64(auth.getBytes(StandardCharsets.UTF_8));
//        String authHeaderValue = "Basic " + new String(encodedAuth);
//        con.setRequestProperty("Authorization", authHeaderValue);
//        con.setRequestProperty("Accept", "*/*");

//        int responseCode = con.getResponseCode();
//        System.out.println("Sending get request : " + url);
//        System.out.println("Response code : " + responseCode);
//
//
//        StringBuilder response = new StringBuilder();
//        try (BufferedReader br = new BufferedReader(
//                new InputStreamReader(con.getInputStream(), "utf-8"))) {
//            String responseLine = null;
//            while ((responseLine = br.readLine()) != null) {
//                response.append(responseLine.trim());
//            }
//            System.out.println(response.toString());
//        }
//
//        return response;


//        // DESTINATION 3:  Destination to a SAP S/4HANA Cloud (public edition) tenant
//        // Uncomment this section to test with actual SAP S/4HANA Cloud
//        final HttpDestination destination = DefaultDestination.builder()
//                                                 .property("Name", "OData_connection")
//                                                 .property("URL", "https://my405604-api.s4hana.cloud.sap/sap/opu/odata/sap/API_SALES_ORDER_SRV")
//                                                 .property("Type", "HTTP")
//                                                 .property("Authentication", "BasicAuthentication")
//                                                 .property("User", "BTP_USER1")
//                                                 .property("Password", "Gw}tDHMrhuAWnzRWkwEbpcguYKsxugDuoKMeJ8Lt\n")
//                                                 .build().asHttp();
//
//        final List<LookupTables> lookupTablesList =
//                (List<LookupTables>) new DefaultMaintenanceLookupTableDataService()
//                        .getAllLookupTables()
//                        .top(2)
//                        .execute(destination);
//
//
//        return new Gson().toJson(lookupTablesList);

//    }

