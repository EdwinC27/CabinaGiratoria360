package com.api.cabina_giratoria.servicios;

import net.minidev.json.JSONObject;
import net.minidev.json.parser.JSONParser;
import net.minidev.json.parser.ParseException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.*;
import org.springframework.stereotype.Service;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestTemplate;

@Service
public class TokenDropBox {
    private static final Logger LOGGER = LoggerFactory.getLogger(TokenDropBox.class);

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

        JSONObject jsonObject;

        if (responseEntity.getStatusCode().is2xxSuccessful()) {
            JSONParser parser = new JSONParser();
            try {
                jsonObject = (JSONObject) parser.parse(responseEntity.getBody());
                String accessToken = (String) jsonObject.get("access_token");
                return accessToken;
            } catch (ParseException e) {
                return e.getMessage();
            }
        } else {
            return "Failed to obtain access token from Dropbox";
        }
    }
}

