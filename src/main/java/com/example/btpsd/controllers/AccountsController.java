package com.example.btpsd.controllers;

import com.example.btpsd.dtos.UserDto;
import org.apache.commons.codec.binary.Base64;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestTemplate;

import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.beans.factory.annotation.Value;

import java.nio.charset.StandardCharsets;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import org.springframework.http.MediaType;
import java.util.Map;

import static org.springframework.web.bind.annotation.RequestMethod.*;

@CrossOrigin(origins = "*", allowedHeaders = "*", methods = { GET,
        HEAD,
        POST,
        PUT,
        PATCH,
        DELETE,
        OPTIONS,
        TRACE }, maxAge = 3600L)
@RestController
@RequestMapping("/accounts")
public class AccountsController {

    @Value("d14597bc-7f22-4c03-abde-b73d0daa17a2")
    private String clientId;

    @Value("G7CYTJ]C/hAXJBYtJhwG71Ggglwx?L7K")
    private String clientSecret;

    @Value("https://avirxf4ow.trial-accounts.ondemand.com")
    private String url;

    @Value("https://avirxf4ow.trial-accounts.ondemand.com/oauth2/authorize")
    private String authorizationEndpoint;

    @Value("https://avirxf4ow.trial-accounts.ondemand.com/oauth2/logout")
    private String endSessionEndpoint;

    private final RestTemplate restTemplate;

    public AccountsController(RestTemplate restTemplate) {
        this.restTemplate = restTemplate;
    }

    @GetMapping
    public ResponseEntity<Map<String, Object>> getAllUsers() {
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);

        // Set Authorization Header
        String auth = "d14597bc-7f22-4c03-abde-b73d0daa17a2" + ":" + "G7CYTJ]C/hAXJBYtJhwG71Ggglwx?L7K";
        byte[] encodedAuth = Base64.encodeBase64(auth.getBytes(StandardCharsets.UTF_8));
        String authHeader = "Basic " + new String(encodedAuth);
        headers.set("Authorization", authHeader);

        HttpEntity<String> request = new HttpEntity<>(headers);

        // Call SCIM API to get all users
        RestTemplate restTemplate = new RestTemplate();
        String scimApiUrl = "https://avirxf4ow.trial-accounts.ondemand.com/service/scim/Users";

        ResponseEntity<Map<String, Object>> response = restTemplate.exchange(
                scimApiUrl,
                HttpMethod.GET,
                request,
                new ParameterizedTypeReference<>() {
                });

        // Check response
        if (!response.getStatusCode().is2xxSuccessful()) {
            throw new RuntimeException("Request failed with status: " + response.getStatusCode());
        }

        return ResponseEntity.ok(response.getBody());
    }

    @PostMapping
    public String createUser(@RequestBody UserDto userDto) {
        HttpURLConnection con = null;
        try {
            // Set up the connection
            URL url = new URL("https://avirxf4ow.trial-accounts.ondemand.com/service/scim/Users");
            con = (HttpURLConnection) url.openConnection();
            con.setRequestMethod("POST");
            con.setRequestProperty("Content-Type", "application/scim+json");

            // Set Authorization Header
            String user = "d14597bc-7f22-4c03-abde-b73d0daa17a2";
            String password = "G7CYTJ]C/hAXJBYtJhwG71Ggglwx?L7K";
            String auth = user + ":" + password;
            byte[] encodedAuth = Base64.encodeBase64(auth.getBytes(StandardCharsets.UTF_8));
            String authHeaderValue = "Basic " + new String(encodedAuth);
            con.setRequestProperty("Authorization", authHeaderValue);
            con.setRequestProperty("Accept", "application/scim+json");
            con.setDoOutput(true);

            // Construct the JSON payload
            String jsonInputString = "{\n" +
                    "  \"emails\": [\n" +
                    "    {\n" +
                    "      \"primary\": true,\n" +
                    "      \"value\": \"" + userDto.getValue() + "\"\n" +
                    "    }\n" +
                    "  ],\n" +
                    "  \"name\": {\n" +
                    "    \"familyName\": \"" + userDto.getFamilyName() + "\",\n" +
                    "    \"givenName\": \"" + userDto.getGivenName() + "\"\n" +
                    "  },\n" +
                    "  \"schemas\": [\n" +
                    "    \"urn:ietf:params:scim:schemas:core:2.0:User\"\n" +
                    "  ],\n" +
                    "  \"userName\": \"" + userDto.getUserName() + "\"\n" +
                    "}";

            // Send the request payload
            try (OutputStream os = con.getOutputStream()) {
                byte[] input = jsonInputString.getBytes(StandardCharsets.UTF_8);
                os.write(input, 0, input.length);
            }

            // Handle the response
            int responseCode = con.getResponseCode();
            if (responseCode == HttpURLConnection.HTTP_CREATED || responseCode == HttpURLConnection.HTTP_OK) {
                // If the response is 200 or 201, read the input stream
                try (BufferedReader br = new BufferedReader(
                        new InputStreamReader(con.getInputStream(), StandardCharsets.UTF_8))) {
                    StringBuilder response = new StringBuilder();
                    String responseLine;
                    while ((responseLine = br.readLine()) != null) {
                        response.append(responseLine.trim());
                    }
                    return response.toString(); // Return the response content
                }
            } else {
                // If there's an error (not 200 or 201), read the error stream
                try (BufferedReader br = new BufferedReader(
                        new InputStreamReader(con.getErrorStream(), StandardCharsets.UTF_8))) {
                    StringBuilder errorResponse = new StringBuilder();
                    String responseLine;
                    while ((responseLine = br.readLine()) != null) {
                        errorResponse.append(responseLine.trim());
                    }
                    // Log the error and return a meaningful message
                    System.err.println("Error: " + errorResponse);
                    return "Error from SCIM API: " + responseCode + " - " + errorResponse.toString();
                }
            }

        } catch (IOException e) {
            // Log and return the exception details if something goes wrong
            e.printStackTrace();
            return "Internal server error occurred: " + e.getMessage();
        } finally {
            if (con != null) {
                con.disconnect();
            }
        }
    }

    @PutMapping("/{userId}")
    public String updateUser(@PathVariable String userId, @RequestBody Map<String, Object> userDto) {
        HttpURLConnection con = null;
        try {
            URL url = new URL("https://avirxf4ow.trial-accounts.ondemand.com/service/scim/Users/" + userId);
            con = (HttpURLConnection) url.openConnection();
            con.setRequestMethod("PUT");
            con.setRequestProperty("Content-Type", "application/scim+json");

            // Set Authorization Header
            String user = "d14597bc-7f22-4c03-abde-b73d0daa17a2";
            String password = "G7CYTJ]C/hAXJBYtJhwG71Ggglwx?L7K";
            String auth = user + ":" + password;
            byte[] encodedAuth = Base64.encodeBase64(auth.getBytes(StandardCharsets.UTF_8));
            String authHeaderValue = "Basic " + new String(encodedAuth);
            con.setRequestProperty("Authorization", authHeaderValue);
            con.setRequestProperty("Accept", "application/scim+json");
            con.setDoOutput(true);

            // Construct JSON payload using userDto
            String jsonInputString = "{\n" +
                    "  \"id\": \"" + userId + "\",\n" +
                    "  \"emails\": [\n" +
                    "    {\n" +
                    "      \"primary\": true,\n" +
                    "      \"value\": \"" + userDto.get("email") + "\"\n" +
                    "    }\n" +
                    "  ],\n" +
                    "  \"name\": {\n" +
                    "    \"familyName\": \"" + userDto.get("familyName") + "\",\n" +
                    "    \"givenName\": \"" + userDto.get("givenName") + "\"\n" +
                    "  },\n" +
                    "  \"schemas\": [\n" +
                    "    \"urn:ietf:params:scim:schemas:core:2.0:User\"\n" +
                    "  ],\n" +
                    "  \"userName\": \"" + userDto.get("userName") + "\"\n" +
                    "}";

            // Send the request payload
            try (OutputStream os = con.getOutputStream()) {
                byte[] input = jsonInputString.getBytes(StandardCharsets.UTF_8);
                os.write(input, 0, input.length);
            }

            // Handle the response
            int responseCode = con.getResponseCode();
            if (responseCode == HttpURLConnection.HTTP_OK || responseCode == HttpURLConnection.HTTP_NO_CONTENT) {
                try (BufferedReader br = new BufferedReader(
                        new InputStreamReader(con.getInputStream(), StandardCharsets.UTF_8))) {
                    StringBuilder response = new StringBuilder();
                    String responseLine;
                    while ((responseLine = br.readLine()) != null) {
                        response.append(responseLine.trim());
                    }
                    return response.toString();
                }
            } else {
                try (BufferedReader br = new BufferedReader(
                        new InputStreamReader(con.getErrorStream(), StandardCharsets.UTF_8))) {
                    StringBuilder errorResponse = new StringBuilder();
                    String responseLine;
                    while ((responseLine = br.readLine()) != null) {
                        errorResponse.append(responseLine.trim());
                    }
                    return "Error from SCIM API: " + responseCode + " - " + errorResponse.toString();
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
            return "Internal server error occurred: " + e.getMessage();
        } finally {
            if (con != null) {
                con.disconnect();
            }
        }
    }

    // Get a user by ID
    @GetMapping("/{userId}")
    public ResponseEntity<UserDto> getUserById(@PathVariable String userId) {
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);

        // Set Authorization Header
        String auth = "d14597bc-7f22-4c03-abde-b73d0daa17a2" + ":" + "G7CYTJ]C/hAXJBYtJhwG71Ggglwx?L7K";
        byte[] encodedAuth = Base64.encodeBase64(auth.getBytes(StandardCharsets.UTF_8));
        String authHeader = "Basic " + new String(encodedAuth);
        headers.set("Authorization", authHeader);

        HttpEntity<String> request = new HttpEntity<>(headers);

        // Call SCIM API to get user by ID
        RestTemplate restTemplate = new RestTemplate();
        String scimApiUrl = "https://avirxf4ow.trial-accounts.ondemand.com/service/scim/Users/" + userId;

        ResponseEntity<UserDto> response = restTemplate.exchange(
                scimApiUrl,
                HttpMethod.GET,
                request,
                new ParameterizedTypeReference<>() {
                });

        // Check response
        if (!response.getStatusCode().is2xxSuccessful()) {
            throw new RuntimeException("Request failed with status: " + response.getStatusCode());
        }

        return ResponseEntity.ok(response.getBody());
    }

    // Delete a user by ID
    @DeleteMapping("/{userId}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteUser(@PathVariable String userId) {
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);

        // Set Authorization Header
        String auth = "d14597bc-7f22-4c03-abde-b73d0daa17a2" + ":" + "G7CYTJ]C/hAXJBYtJhwG71Ggglwx?L7K";
        byte[] encodedAuth = Base64.encodeBase64(auth.getBytes(StandardCharsets.UTF_8));
        String authHeader = "Basic " + new String(encodedAuth);
        headers.set("Authorization", authHeader);

        HttpEntity<String> request = new HttpEntity<>(headers);

        // Call SCIM API to delete user by ID
        RestTemplate restTemplate = new RestTemplate();
        String scimApiUrl = "https://avirxf4ow.trial-accounts.ondemand.com/service/scim/Users/" + userId;

        ResponseEntity<Void> response = restTemplate.exchange(
                scimApiUrl,
                HttpMethod.DELETE,
                request,
                Void.class);

        // Check response
        if (!response.getStatusCode().is2xxSuccessful()) {
            throw new RuntimeException("Request failed with status: " + response.getStatusCode());
        }
    }

    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestParam String username, @RequestParam String password) {
        String tokenUrl = url + "/oauth2/token"; // Adjust based on your OAuth2 token endpoint
        HttpHeaders headers = new HttpHeaders();
        headers.setBasicAuth(clientId, clientSecret);
        headers.set("Content-Type", "application/x-www-form-urlencoded");

        String body = "grant_type=password&username=" + username + "&password=" + password;

        HttpEntity<String> request = new HttpEntity<>(body, headers);

        ResponseEntity<String> response = restTemplate.exchange(tokenUrl, HttpMethod.POST, request, String.class);
        return ResponseEntity.ok(response.getBody());
    }

    @PostMapping("/logout")
    public ResponseEntity<?> logout(@RequestParam String refreshToken) {
        String logoutUrl = endSessionEndpoint + "?token=" + refreshToken; // Modify as per your logout requirements

        HttpHeaders headers = new HttpHeaders();
        headers.setBasicAuth(clientId, clientSecret);

        HttpEntity<String> request = new HttpEntity<>(headers);
        ResponseEntity<String> response = restTemplate.exchange(logoutUrl, HttpMethod.POST, request, String.class);
        return ResponseEntity.ok("Logged out successfully");
    }
}
