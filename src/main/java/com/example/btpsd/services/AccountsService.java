package com.example.btpsd.services;

import com.example.btpsd.dtos.UserDto;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;


import java.nio.charset.StandardCharsets;
import java.util.List;
import java.util.Map;

import org.apache.commons.codec.binary.Base64;

@Service
public class AccountsService {

    private final RestTemplate restTemplate;

    @Value("${scim.api.url}")
    private String scimApiUrl;

    public AccountsService(RestTemplate restTemplate) {
        this.restTemplate = restTemplate;
    }

    public Map<String, Object> getAllUsers() {
        HttpHeaders headers = createHeaders();
        HttpEntity<String> request = new HttpEntity<>(headers);

        // Update the response type to Map<String, Object>
        ResponseEntity<Map<String, Object>> response = restTemplate.exchange(
                scimApiUrl,
                HttpMethod.GET,
                request,
                new ParameterizedTypeReference<Map<String, Object>>() {
                }
        );

        checkResponse(response); // Check if response is OK
        return response.getBody(); // Return the body as a Map
    }


//    public Map<String, Object> updateUser(String userId, Map<String, Object> userDto) {
//        HttpHeaders headers = createHeaders();  // Ensure this contains proper auth
//        HttpEntity<Map<String, Object>> request = new HttpEntity<>(userDto, headers);
//
//        String url = scimApiUrl + "/" + userId;  // Make sure to use the correct user ID in the path
//
//        ResponseEntity<Map<String, Object>> response = restTemplate.exchange(
//                url,
//                HttpMethod.PUT,
//                request,
//                new ParameterizedTypeReference<>() {
//                }
//        );
//
//        checkResponse(response);  // Check if the response is successful (200 OK)
//        return response.getBody();
//    }

    public UserDto getUserById(String userId) {
        HttpHeaders headers = createHeaders();
        HttpEntity<String> request = new HttpEntity<>(headers);

        ResponseEntity<UserDto> response = restTemplate.exchange(
                scimApiUrl + "/" + userId,
                HttpMethod.GET,
                request,
                new ParameterizedTypeReference<>() {
                }
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

        String auth = "facbddf3-d119-4f36-bb81-0dc8a644a8cb" + ":" + "nY17h3A5/YsCGx0K3LsDGhzqp]FBJYZ?7o/";
        byte[] encodedAuth = Base64.encodeBase64(auth.getBytes(StandardCharsets.UTF_8));
        String authHeader = "Basic " + new String(encodedAuth);

        headers.set("Authorization", authHeader);
        return headers;
    }



    private void checkResponse(ResponseEntity<?> response) {
        if (!response.getStatusCode().is2xxSuccessful()) {
            throw new RuntimeException("Request failed with status: " + response.getStatusCode());
        }
    }
}



