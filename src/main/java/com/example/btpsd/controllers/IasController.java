package com.example.btpsd.controllers;

import com.example.btpsd.model.IasUser;
import org.apache.commons.codec.binary.Base64;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.io.*;
import java.net.HttpURLConnection;
import java.net.URL;
import java.nio.charset.StandardCharsets;

@RestController
@CrossOrigin(origins = "http://localhost:4200", maxAge = 3600)
public class IasController {

    @PostMapping("/iasusers")
    public String createUser(@RequestBody IasUser ias) throws IOException {

        //URL url = new URL("https://ajwgvqm4q.trial-accounts.ondemand.com/service/scim/Users");
        URL url = new URL("https://asarh4ioh.accounts.cloud.sap/service/scim/Users");
        HttpURLConnection con = (HttpURLConnection) url.openConnection();
        con.setRequestMethod("POST");
        con.setRequestProperty("Content-Type", "application/scim+json");
//        String user = "71dba67b-6b23-4d5c-b6b0-348fe579485a";
//        String password = "Ku7q4rKEj@fjhNGfmR7T@VDg93R2[Ls8ww";
        String user = "8eca5612-16a7-4920-a011-3b5ecee0c11b";
        String password = "fTKClQWu5[L[r:[Q@UiR0d1D10H-[9Ns";
        String auth = user + ":" + password;
        byte[] encodedAuth = Base64.encodeBase64(auth.getBytes(StandardCharsets.UTF_8));
        String authHeaderValue = "Basic " + new String(encodedAuth);
        con.setRequestProperty("Authorization", authHeaderValue);
        con.setRequestProperty("Accept", "application/scim+json");
        con.setDoOutput(true);
        String jsonInputString = "{\n" +
                "  \"emails\": [\n" +
                "    {\n" +
                "      \"primary\": true,\n" +
                "      \"value\": \"" + ias.getValue() + "\"\n" +
                "    }\n" +
                "  ],\n" +
                "  \"name\": {\n" +
                "    \"familyName\": \"" + ias.getFamilyName() + "\",\n" +
                "    \"givenName\": \"" + ias.getGivenName() + "\"\n" +
                "  },\n" +
                "  \"schemas\": [\n" +
                "    \"urn:ietf:params:scim:schemas:core:2.0:User\"\n" +
                "  ],\n" +
                "  \"userName\": \"" + ias.getUserName() + "\"\n" +
                "}";

        try (OutputStream os = con.getOutputStream()) {
            byte[] input = jsonInputString.getBytes("utf-8");
            os.write(input, 0, input.length);
        }
        try (BufferedReader br = new BufferedReader(
                new InputStreamReader(con.getInputStream(), "utf-8"))) {
            StringBuilder response = new StringBuilder();
            String responseLine = null;
            while ((responseLine = br.readLine()) != null) {
                response.append(responseLine.trim());
            }
            System.out.println(response.toString());
        }
        return jsonInputString;
    }
}
