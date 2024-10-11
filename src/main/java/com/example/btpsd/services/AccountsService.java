package com.example.btpsd.services;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.RestClientException;
import org.springframework.web.client.RestTemplate;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.nio.charset.StandardCharsets;
import java.util.Map;
import com.example.btpsd.model.IasUser;
import org.apache.commons.codec.binary.Base64;
import org.springframework.web.bind.annotation.RequestBody;

@Service
public class AccountsService {

    private static final Logger logger = LoggerFactory.getLogger(AccountsService.class);
    private final RestTemplate restTemplate;

    @Value("${scim.api.url}")
    private String scimApiUrl;

    @Value("${scim.api.username}")
    private String username;

    @Value("${scim.api.password}")
    private String password;

    public AccountsService(RestTemplate restTemplate) {
        this.restTemplate = restTemplate;
    }

    public Map<String, Object> getAllUsers() {
        HttpHeaders headers = createHeaders();
        HttpEntity<String> request = new HttpEntity<>(headers);

        ResponseEntity<Map<String, Object>> response = restTemplate.exchange(
                scimApiUrl,
                HttpMethod.GET,
                request,
                (Class<Map<String, Object>>) (Class<?>) Map.class
        );

        checkResponse(response);
        return response.getBody();
    }
//    public String createUser(@RequestBody IasUser ias) throws IOException {
//
//        URL url = new URL(scimApiUrl);
//        HttpURLConnection con = (HttpURLConnection) url.openConnection();
//        con.setRequestMethod("POST");
//        con.setRequestProperty("Content-Type", "application/scim+json");
////        String user = username;
////        String password = this.password;
//        String auth = this.username + ":" + this.password;
//        byte[] encodedAuth = Base64.encodeBase64(auth.getBytes(StandardCharsets.UTF_8));
//        String authHeaderValue = "Basic " + new String(encodedAuth);
//        con.setRequestProperty("Authorization", authHeaderValue);
//        con.setRequestProperty("Accept", "application/scim+json");
//        con.setDoOutput(true);
//        String jsonInputString = "{\n" +
//                "  \"emails\": [\n" +
//                "    {\n" +
//                "      \"primary\": true,\n" +
//                "      \"value\": \"" + ias.getValue() + "\"\n" +
//                "    }\n" +
//                "  ],\n" +
//                "  \"name\": {\n" +
//                "    \"familyName\": \"" + ias.getFamilyName() + "\",\n" +
//                "    \"givenName\": \"" + ias.getGivenName() + "\"\n" +
//                "  },\n" +
//                "  \"schemas\": [\n" +
//                "    \"urn:ietf:params:scim:schemas:core:2.0:User\"\n" +
//                "  ],\n" +
//                "  \"userName\": \"" + ias.getUserName() + "\"\n" +
//                "}";
//
//        try (OutputStream os = con.getOutputStream()) {
//            byte[] input = jsonInputString.getBytes("utf-8");
//            os.write(input, 0, input.length);
//        }
//        try (BufferedReader br = new BufferedReader(
//                new InputStreamReader(con.getInputStream(), "utf-8"))) {
//            StringBuilder response = new StringBuilder();
//            String responseLine = null;
//            while ((responseLine = br.readLine()) != null) {
//                response.append(responseLine.trim());
//            }
//            System.out.println(response.toString());
//        }
//        return jsonInputString;
//    }
public Map<String, Object> createUser(Map<String, Object> userPayload) {
    try {
        HttpHeaders headers = createHeaders();
        headers.setContentType(MediaType.valueOf("application/scim+json"));  // Ensure correct Content-Type

        HttpEntity<Map<String, Object>> request = new HttpEntity<>(userPayload, headers);

        ResponseEntity<Map<String, Object>> response = restTemplate.exchange(
                scimApiUrl,           // POST to the base SCIM API URL
                HttpMethod.POST,      // Use POST method
                request,              // Request body and headers
                new ParameterizedTypeReference<Map<String, Object>>() {}
        );

        if (!response.getStatusCode().is2xxSuccessful()) {
            throw new RuntimeException("Failed to create user: " + response.getStatusCode());
        }

        return response.getBody();
    } catch (HttpClientErrorException e) {
        throw new RuntimeException("Error creating user: " + e.getResponseBodyAsString(), e);
    } catch (RestClientException e) {
        throw new RuntimeException("Error creating user: " + e.getMessage(), e);
    }
}

    public Map<String, Object> updateUser(String userId, Map<String, Object> userPayload) {
        try {
            HttpHeaders headers = createHeaders();
            headers.setContentType(MediaType.valueOf("application/scim+json"));  // Ensure correct Content-Type

            HttpEntity<Map<String, Object>> request = new HttpEntity<>(userPayload, headers);

            ResponseEntity<Map<String, Object>> response = restTemplate.exchange(
                    scimApiUrl + "/" + userId,  // PUT to specific user ID
                    HttpMethod.PUT,             // Use PUT method
                    request,                    // Request body and headers
                    new ParameterizedTypeReference<Map<String, Object>>() {}
            );

            if (!response.getStatusCode().is2xxSuccessful()) {
                throw new RuntimeException("Failed to update user: " + response.getStatusCode());
            }

            return response.getBody();
        } catch (HttpClientErrorException e) {
            throw new RuntimeException("Error updating user: " + e.getResponseBodyAsString(), e);
        } catch (RestClientException e) {
            throw new RuntimeException("Error updating user: " + e.getMessage(), e);
        }
    }

    public Map<String, Object> getUserById(String userId) {
        HttpHeaders headers = createHeaders();
        HttpEntity<String> request = new HttpEntity<>(headers);

        ResponseEntity<Map<String, Object>> response = restTemplate.exchange(
                scimApiUrl + "/" + userId,
                HttpMethod.GET,
                request,
                (Class<Map<String, Object>>) (Class<?>) Map.class
        );

        checkResponse(response);
        return response.getBody();
    }

    public void deleteUser(String userId) {
        HttpHeaders headers = createHeaders();
        HttpEntity<String> request = new HttpEntity<>(headers);

        ResponseEntity<Void> response = restTemplate.exchange(
                scimApiUrl + "/" + userId,
                HttpMethod.DELETE,
                request,
                Void.class
        );

        checkResponse(response);
    }

    private HttpHeaders createHeaders() {
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);

        String auth = username + ":" + password;
        byte[] encodedAuth = Base64.encodeBase64(auth.getBytes(StandardCharsets.UTF_8));
        String authHeaderValue = "Basic " + new String(encodedAuth);

        headers.set("Authorization", authHeaderValue);  // Setting Authorization header manually

        return headers;
    }


    private void checkResponse(ResponseEntity<?> response) {
        if (!response.getStatusCode().is2xxSuccessful()) {
            throw new RuntimeException("Failed to execute request: " + response.getStatusCode());
        }
        logger.info("Response Status: {}", response.getStatusCode());
    }
}



