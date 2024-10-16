package com.example.btpsd.controllers;

import com.example.btpsd.dtos.UserDto;
import com.example.btpsd.services.AccountsService;
import jakarta.annotation.security.PermitAll;
import org.apache.commons.codec.binary.Base64;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.nio.charset.StandardCharsets;
import java.util.List;
import java.util.Map;

import static org.springframework.web.bind.annotation.RequestMethod.*;

@CrossOrigin(origins = "*", allowedHeaders = "*" , methods = {  GET,
        HEAD,
        POST,
        PUT,
        PATCH,
        DELETE,
        OPTIONS,
        TRACE}
        , maxAge = 3600L)
@RestController
@RequestMapping("/accounts")
public class AccountsController {

    private final AccountsService accountsService;

    @Autowired
    public AccountsController(AccountsService accountsService) {
        this.accountsService = accountsService;
    }

    // Get all users
    @GetMapping
    public ResponseEntity<Map<String, Object>> getAllUsers() {
        Map<String, Object> users = accountsService.getAllUsers(); // Call the updated service method
        return ResponseEntity.ok(users); // Return the response entity with the map
    }

    @PostMapping
    public String createUser(@RequestBody UserDto userDto) {
        HttpURLConnection con = null;
        try {
            // Set up the connection
            URL url = new URL("https://aji26ufcs.trial-accounts.ondemand.com/service/scim/Users");
            con = (HttpURLConnection) url.openConnection();
            con.setRequestMethod("POST");
            con.setRequestProperty("Content-Type", "application/scim+json");

            // Set Authorization Header
            String user = "facbddf3-d119-4f36-bb81-0dc8a644a8cb";
            String password = "nY17h3A5/YsCGx0K3LsDGhzqp]FBJYZ?7o/";
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
                    return response.toString();  // Return the response content
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

//    @PutMapping("/{userId}")
//    public ResponseEntity<Map<String, Object>> updateUser(
//            @PathVariable String userId,
//            @RequestBody Map<String, Object> userDto
//    ) {
//        Map<String, Object> updatedUser = accountsService.updateUser(userId, userDto);
//        return ResponseEntity.ok(updatedUser);
//    }

    @PutMapping("/{userId}")
    public String updateUser(
            @PathVariable String userId,
            @RequestBody Map<String, Object> userDto
    ) {
        HttpURLConnection con = null;
        try {
            // Set up the connection
            URL url = new URL("https://aji26ufcs.trial-accounts.ondemand.com/service/scim/Users/" + userId);
            con = (HttpURLConnection) url.openConnection();
            con.setRequestMethod("PUT");
            con.setRequestProperty("Content-Type", "application/scim+json");

            // Set Authorization Header
            String user = "facbddf3-d119-4f36-bb81-0dc8a644a8cb";
            String password = "nY17h3A5/YsCGx0K3LsDGhzqp]FBJYZ?7o/";
            String auth = user + ":" + password;
            byte[] encodedAuth = Base64.encodeBase64(auth.getBytes(StandardCharsets.UTF_8));
            String authHeaderValue = "Basic " + new String(encodedAuth);
            con.setRequestProperty("Authorization", authHeaderValue);
            con.setRequestProperty("Accept", "application/scim+json");
            con.setDoOutput(true);

            // Construct the JSON payload using the userDto map
            String jsonInputString = "{\n" +
                    "  \"id\": \"" + userId + "\",\n" +  // Include the id field
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
                // If the response is 200 (OK) or 204 (No Content), read the input stream
                try (BufferedReader br = new BufferedReader(
                        new InputStreamReader(con.getInputStream(), StandardCharsets.UTF_8))) {
                    StringBuilder response = new StringBuilder();
                    String responseLine;
                    while ((responseLine = br.readLine()) != null) {
                        response.append(responseLine.trim());
                    }
                    return response.toString();  // Return the response content
                }
            } else {
                // If there's an error (not 200), read the error stream
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

    // Get a user by ID
    @GetMapping("/{userId}")
    public UserDto getUserById(@PathVariable String userId) {
        return accountsService.getUserById(userId);
    }

    // Delete a user by ID
    @DeleteMapping("/{userId}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteUser(@PathVariable String userId) {
        accountsService.deleteUser(userId);
    }
}
