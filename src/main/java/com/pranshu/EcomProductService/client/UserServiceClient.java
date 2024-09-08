package com.pranshu.EcomProductService.client;

import com.pranshu.EcomProductService.dto.ValidateTokenDTO;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.RestTemplate;

/**
 * Wrapper on EcomUserService APIs
 */
@Component
public class UserServiceClient {

    private RestTemplateBuilder restTemplateBuilder;
    private String userServiceAPIURL;

    @Value("${userservice.api.path.validate}")
    private String userServiceValidatePath;

    public UserServiceClient(RestTemplateBuilder restTemplateBuilder,
                             @Value("${userservice.api.url}") String userServiceAPIURL) {
        this.restTemplateBuilder = restTemplateBuilder;
        this.userServiceAPIURL = userServiceAPIURL;
    }

    public String validateToken(ValidateTokenDTO validateTokenDTO){
        String validateTokenURL  = userServiceAPIURL + userServiceValidatePath;
        RestTemplate restTemplate = restTemplateBuilder.build();
        ResponseEntity<String> validateTokenResponse = null;
        try {
            validateTokenResponse = restTemplate.postForEntity(validateTokenURL, validateTokenDTO, String.class);
        } catch (HttpClientErrorException.Forbidden e) {
            return e.getResponseBodyAsString();
        }
        return validateTokenResponse.getBody();
    }
}
