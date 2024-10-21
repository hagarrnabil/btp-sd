//package com.example.btpsd.controllers;
//
//import com.example.btpsd.dtos.LoginDto;
//import com.example.btpsd.dtos.UserDto;
//import com.example.btpsd.services.AccountsService;
//import org.apache.commons.codec.binary.Base64;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.http.HttpStatus;
//import org.springframework.http.ResponseEntity;
//import org.springframework.web.bind.annotation.*;
//import org.springframework.web.client.RestTemplate;
//
//import org.springframework.http.HttpEntity;
//import org.springframework.http.HttpHeaders;
//import org.springframework.http.HttpMethod;
//import org.springframework.http.ResponseEntity;
//import org.springframework.web.bind.annotation.*;
//import org.springframework.web.client.RestTemplate;
//import org.springframework.beans.factory.annotation.Value;
//
//import java.nio.charset.StandardCharsets;
//import java.nio.charset.StandardCharsets;
//import java.io.BufferedReader;
//import java.io.IOException;
//import java.io.InputStreamReader;
//import java.io.OutputStream;
//import java.net.HttpURLConnection;
//import java.net.URL;
//import java.net.URLEncoder;
//import java.util.Map;
//
//import static org.springframework.web.bind.annotation.RequestMethod.*;
//
//@CrossOrigin(origins = "*", allowedHeaders = "*" , methods = {  GET,
//        HEAD,
//        POST,
//        PUT,
//        PATCH,
//        DELETE,
//        OPTIONS,
//        TRACE}
//        , maxAge = 3600L)
//@RestController
//@RequestMapping("/accounts")
//public class AccountsController {
//
//    private final AccountsService accountsService;
//
//    @Value("623a6227-8cde-424a-9d03-ee5fe8f6baba")
//    private String clientId;
//
//    @Value("2LI36vyU3TwvjdXmQ=VtpTpH7qz_espExD")
//    private String clientSecret;
//
//    @Value("https://aji26ufcs.trial-accounts.ondemand.com")
//    private String url;
//
//    @Value("https://aji26ufcs.trial-accounts.ondemand.com/oauth2/authorize")
//    private String authorizationEndpoint;
//
//    @Value("https://aji26ufcs.trial-accounts.ondemand.com/oauth2/logout")
//    private String endSessionEndpoint;
//
//    private final RestTemplate restTemplate;
//
//
//    @Autowired
//    public AccountsController(AccountsService accountsService,RestTemplate restTemplate) {
//        this.restTemplate = restTemplate;
//        this.accountsService = accountsService;
//    }
//
//    // Get all users
//    @GetMapping
//    public ResponseEntity<Map<String, Object>> getAllUsers() {
//        Map<String, Object> users = accountsService.getAllUsers(); // Call the updated service method
//        return ResponseEntity.ok(users); // Return the response entity with the map
//    }
//
//    @PostMapping
//    public String createUser(@RequestBody UserDto userDto) {
//        HttpURLConnection con = null;
//        try {
//            // Set up the connection
//            URL url = new URL("https://aji26ufcs.trial-accounts.ondemand.com/service/scim/Users");
//            con = (HttpURLConnection) url.openConnection();
//            con.setRequestMethod("POST");
//            con.setRequestProperty("Content-Type", "application/scim+json");
//
//            // Set Authorization Header
//            String user = "facbddf3-d119-4f36-bb81-0dc8a644a8cb";
//            String password = "nY17h3A5/YsCGx0K3LsDGhzqp]FBJYZ?7o/";
//            String auth = user + ":" + password;
//            byte[] encodedAuth = Base64.encodeBase64(auth.getBytes(StandardCharsets.UTF_8));
//            String authHeaderValue = "Basic " + new String(encodedAuth);
//            con.setRequestProperty("Authorization", authHeaderValue);
//            con.setRequestProperty("Accept", "application/scim+json");
//            con.setDoOutput(true);
//
//            // Construct the JSON payload
//            String jsonInputString = "{\n" +
//                    "  \"emails\": [\n" +
//                    "    {\n" +
//                    "      \"primary\": true,\n" +
//                    "      \"value\": \"" + userDto.getValue() + "\"\n" +
//                    "    }\n" +
//                    "  ],\n" +
//                    "  \"name\": {\n" +
//                    "    \"familyName\": \"" + userDto.getFamilyName() + "\",\n" +
//                    "    \"givenName\": \"" + userDto.getGivenName() + "\"\n" +
//                    "  },\n" +
//                    "  \"schemas\": [\n" +
//                    "    \"urn:ietf:params:scim:schemas:core:2.0:User\"\n" +
//                    "  ],\n" +
//                    "  \"userName\": \"" + userDto.getUserName() + "\"\n" +
//                    "}";
//
//            // Send the request payload
//            try (OutputStream os = con.getOutputStream()) {
//                byte[] input = jsonInputString.getBytes(StandardCharsets.UTF_8);
//                os.write(input, 0, input.length);
//            }
//
//            // Handle the response
//            int responseCode = con.getResponseCode();
//            if (responseCode == HttpURLConnection.HTTP_CREATED || responseCode == HttpURLConnection.HTTP_OK) {
//                // If the response is 200 or 201, read the input stream
//                try (BufferedReader br = new BufferedReader(
//                        new InputStreamReader(con.getInputStream(), StandardCharsets.UTF_8))) {
//                    StringBuilder response = new StringBuilder();
//                    String responseLine;
//                    while ((responseLine = br.readLine()) != null) {
//                        response.append(responseLine.trim());
//                    }
//                    return response.toString();  // Return the response content
//                }
//            } else {
//                // If there's an error (not 200 or 201), read the error stream
//                try (BufferedReader br = new BufferedReader(
//                        new InputStreamReader(con.getErrorStream(), StandardCharsets.UTF_8))) {
//                    StringBuilder errorResponse = new StringBuilder();
//                    String responseLine;
//                    while ((responseLine = br.readLine()) != null) {
//                        errorResponse.append(responseLine.trim());
//                    }
//                    // Log the error and return a meaningful message
//                    System.err.println("Error: " + errorResponse);
//                    return "Error from SCIM API: " + responseCode + " - " + errorResponse.toString();
//                }
//            }
//
//        } catch (IOException e) {
//            // Log and return the exception details if something goes wrong
//            e.printStackTrace();
//            return "Internal server error occurred: " + e.getMessage();
//        } finally {
//            if (con != null) {
//                con.disconnect();
//            }
//        }
//    }
//
//
//    @PutMapping("/{userId}")
//    public String updateUser(@PathVariable String userId, @RequestBody Map<String, Object> userDto
//    ) {
//        HttpURLConnection con = null;
//        try {
//            // Set up the connection
//            URL url = new URL("https://aji26ufcs.trial-accounts.ondemand.com/service/scim/Users/" + userId);
//            con = (HttpURLConnection) url.openConnection();
//            con.setRequestMethod("PUT");
//            con.setRequestProperty("Content-Type", "application/scim+json");
//
//            // Set Authorization Header
//            String user = "facbddf3-d119-4f36-bb81-0dc8a644a8cb";
//            String password = "nY17h3A5/YsCGx0K3LsDGhzqp]FBJYZ?7o/";
//            String auth = user + ":" + password;
//            byte[] encodedAuth = Base64.encodeBase64(auth.getBytes(StandardCharsets.UTF_8));
//            String authHeaderValue = "Basic " + new String(encodedAuth);
//            con.setRequestProperty("Authorization", authHeaderValue);
//            con.setRequestProperty("Accept", "application/scim+json");
//            con.setDoOutput(true);
//
//            // Construct the JSON payload using the userDto map
//            String jsonInputString = "{\n" +
//                    "  \"id\": \"" + userId + "\",\n" +  // Include the id field
//                    "  \"emails\": [\n" +
//                    "    {\n" +
//                    "      \"primary\": true,\n" +
//                    "      \"value\": \"" + userDto.get("email") + "\"\n" +
//                    "    }\n" +
//                    "  ],\n" +
//                    "  \"name\": {\n" +
//                    "    \"familyName\": \"" + userDto.get("familyName") + "\",\n" +
//                    "    \"givenName\": \"" + userDto.get("givenName") + "\"\n" +
//                    "  },\n" +
//                    "  \"schemas\": [\n" +
//                    "    \"urn:ietf:params:scim:schemas:core:2.0:User\"\n" +
//                    "  ],\n" +
//                    "  \"userName\": \"" + userDto.get("userName") + "\"\n" +
//                    "}";
//
//            // Send the request payload
//            try (OutputStream os = con.getOutputStream()) {
//                byte[] input = jsonInputString.getBytes(StandardCharsets.UTF_8);
//                os.write(input, 0, input.length);
//            }
//
//            // Handle the response
//            int responseCode = con.getResponseCode();
//            if (responseCode == HttpURLConnection.HTTP_OK || responseCode == HttpURLConnection.HTTP_NO_CONTENT) {
//                // If the response is 200 (OK) or 204 (No Content), read the input stream
//                try (BufferedReader br = new BufferedReader(
//                        new InputStreamReader(con.getInputStream(), StandardCharsets.UTF_8))) {
//                    StringBuilder response = new StringBuilder();
//                    String responseLine;
//                    while ((responseLine = br.readLine()) != null) {
//                        response.append(responseLine.trim());
//                    }
//                    return response.toString();  // Return the response content
//                }
//            } else {
//                // If there's an error (not 200), read the error stream
//                try (BufferedReader br = new BufferedReader(
//                        new InputStreamReader(con.getErrorStream(), StandardCharsets.UTF_8))) {
//                    StringBuilder errorResponse = new StringBuilder();
//                    String responseLine;
//                    while ((responseLine = br.readLine()) != null) {
//                        errorResponse.append(responseLine.trim());
//                    }
//                    // Log the error and return a meaningful message
//                    System.err.println("Error: " + errorResponse);
//                    return "Error from SCIM API: " + responseCode + " - " + errorResponse.toString();
//                }
//            }
//
//        } catch (IOException e) {
//            // Log and return the exception details if something goes wrong
//            e.printStackTrace();
//            return "Internal server error occurred: " + e.getMessage();
//        } finally {
//            if (con != null) {
//                con.disconnect();
//            }
//        }
//    }
//
//    // Get a user by ID
//    @GetMapping("/{userId}")
//    public UserDto getUserById(@PathVariable String userId) {
//        return accountsService.getUserById(userId);
//    }
//
//    // Delete a user by ID
//    @DeleteMapping("/{userId}")
//    @ResponseStatus(HttpStatus.NO_CONTENT)
//    public void deleteUser(@PathVariable String userId) {
//        accountsService.deleteUser(userId);
//    }
//
//
//
//
//    @PostMapping("/login")
//    public ResponseEntity<?> login(@RequestParam String username, @RequestParam String password) {
//        String tokenUrl = url + "/oauth2/token"; // Adjust based on your OAuth2 token endpoint
//        HttpHeaders headers = new HttpHeaders();
//        headers.setBasicAuth(clientId, clientSecret);
//        headers.set("Content-Type", "application/x-www-form-urlencoded");
//
//        String body = "grant_type=password&username=" + username + "&password=" + password;
//
//        HttpEntity<String> request = new HttpEntity<>(body, headers);
//
//        ResponseEntity<String> response = restTemplate.exchange(tokenUrl, HttpMethod.POST, request, String.class);
//        return ResponseEntity.ok(response.getBody());
//    }
//
//    @PostMapping("/logout")
//    public ResponseEntity<?> logout(@RequestParam String refreshToken) {
//        String logoutUrl = endSessionEndpoint + "?token=" + refreshToken; // Modify as per your logout requirements
//
//        HttpHeaders headers = new HttpHeaders();
//        headers.setBasicAuth(clientId, clientSecret);
//
//        HttpEntity<String> request = new HttpEntity<>(headers);
//        ResponseEntity<String> response = restTemplate.exchange(logoutUrl, HttpMethod.POST, request, String.class);
//        return ResponseEntity.ok("Logged out successfully");
//    }
//}
