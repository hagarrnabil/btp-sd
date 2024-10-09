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

import java.util.Map;

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

    public Map<String, Object> createUser(Map<String, Object> userPayload) {
        try {
            HttpHeaders headers = createHeaders();
            headers.setContentType(MediaType.valueOf("application/scim+json"));  // Set the correct Content-Type

            HttpEntity<Map<String, Object>> request = new HttpEntity<>(userPayload, headers);

            ResponseEntity<Map<String, Object>> response = restTemplate.exchange(
                    scimApiUrl,
                    HttpMethod.POST,
                    request,
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
        HttpHeaders headers = createHeaders();
        HttpEntity<Map<String, Object>> request = new HttpEntity<>(userPayload, headers);

        ResponseEntity<Map<String, Object>> response = restTemplate.exchange(
                scimApiUrl + "/" + userId,
                HttpMethod.PUT,
                request,
                (Class<Map<String, Object>>) (Class<?>) Map.class
        );

        checkResponse(response);
        return response.getBody();
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
        headers.setBasicAuth(username, password);
        return headers;
    }

    private void checkResponse(ResponseEntity<?> response) {
        if (!response.getStatusCode().is2xxSuccessful()) {
            throw new RuntimeException("Failed to execute request: " + response.getStatusCode());
        }
        logger.info("Response Status: {}", response.getStatusCode());
    }
}



