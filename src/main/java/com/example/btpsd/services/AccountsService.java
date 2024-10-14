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

import org.apache.commons.codec.binary.Base64;

@Service
public class AccountsService {

    private final RestTemplate restTemplate;

    @Value("${scim.api.url}")
    private String scimApiUrl;

    public AccountsService(RestTemplate restTemplate) {
        this.restTemplate = restTemplate;
    }

    public List<UserDto> getAllUsers() {
        HttpHeaders headers = createHeaders();
        HttpEntity<String> request = new HttpEntity<>(headers);

        ResponseEntity<List<UserDto>> response = restTemplate.exchange(
                scimApiUrl,
                HttpMethod.GET,
                request,
                new ParameterizedTypeReference<>() {
                }
        );

        checkResponse(response);
        return response.getBody();
    }


    public UserDto updateUser(String userId, UserDto userPayload) {

            HttpHeaders headers = createHeaders();
            headers.setContentType(MediaType.valueOf("application/scim+json"));

            HttpEntity<UserDto> request = new HttpEntity<>(userPayload, headers);

            ResponseEntity<UserDto> response = restTemplate.exchange(
                    scimApiUrl + "/" + userId,
                    HttpMethod.PUT,
                    request,
                    new ParameterizedTypeReference<>() {
                    }
            );

            if (!response.getStatusCode().is2xxSuccessful()) {
                throw new RuntimeException("Failed to update user: " + response.getStatusCode());
            }

            return response.getBody();

    }

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



