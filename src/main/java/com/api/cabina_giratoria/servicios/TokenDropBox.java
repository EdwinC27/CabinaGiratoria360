package com.api.cabina_giratoria.servicios;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.*;
import org.springframework.stereotype.Service;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestTemplate;

@Service
public class TokenDropBox {
    @Value("${dropbox.appKey}")
    private String Dropbox_appID;
    @Value("${dropbox.appSecret}")
    private String Dropbox_appSecret;
    @Value("${dropbox.urlPeticion}")
    private String urlPeticionToken;

    private final RestTemplate restTemplate;

    @Autowired
    public TokenDropBox(RestTemplate restTemplate) {
        this.restTemplate = restTemplate;
    }

    public String getAccessToken(String authorizationCode) {
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_FORM_URLENCODED);

        MultiValueMap<String, String> body = new LinkedMultiValueMap<>();
        body.add("grant_type", "authorization_code");
        body.add("code", authorizationCode);
        body.add("client_id", Dropbox_appID);
        body.add("client_secret", Dropbox_appSecret);
        body.add("redirect_uri", "http://localhost:8080/dropbox/auth");

        HttpEntity<MultiValueMap<String, String>> requestEntity = new HttpEntity<>(body, headers);
        ResponseEntity<String> responseEntity = restTemplate.postForEntity(
                urlPeticionToken,
                requestEntity,
                String.class
        );

        if (responseEntity.getStatusCode().is2xxSuccessful()) {
            return responseEntity.getBody();
        } else {
            throw new RuntimeException("Failed to obtain access token from Dropbox.");
        }
    }
}

