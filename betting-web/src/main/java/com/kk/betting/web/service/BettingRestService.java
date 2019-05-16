package com.kk.betting.web.service;

import com.kk.betting.web.config.BettingServicesConfig;
import com.kk.betting.web.config.util.AuthorizationProvider;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestClientException;
import org.springframework.web.client.RestTemplate;

/**
 * Created by KK on 2017-07-18.
 */
@Service
public class BettingRestService {

    private RestTemplate restTemplate;
    private BettingServicesConfig bettingServicesConfig;
    private AuthorizationProvider authorizationProvider;

    @Autowired
    public BettingRestService(RestTemplate restTemplate, BettingServicesConfig bettingServicesConfig, AuthorizationProvider authorizationProvider) {
        this.restTemplate = restTemplate;
        this.bettingServicesConfig = bettingServicesConfig;
        this.authorizationProvider = authorizationProvider;
    }


    public <T> ResponseEntity<T> getForEntity(String url, Class<T> responseType)
            throws RestClientException {
        return restTemplate.exchange(bettingServicesConfig.getBettingServicesUrl() + url, HttpMethod.GET, new HttpEntity<>(authorizationProvider.getAuthorizationHeader()), responseType);
    }

    public <T> ResponseEntity<T> postForEntity(String url, Class<T> responseType)
            throws RestClientException {
        return restTemplate.exchange(bettingServicesConfig.getBettingServicesUrl() + url, HttpMethod.POST, new HttpEntity<>(authorizationProvider.getAuthorizationHeader()), responseType);
    }

    public <T, S> ResponseEntity<T> postForEntity(String url, Class<T> responseType, S body)
            throws RestClientException {
        return restTemplate.postForEntity(bettingServicesConfig.getBettingServicesUrl() + url, new HttpEntity<>(body, authorizationProvider.getAuthorizationHeader()), responseType);


    }
}
