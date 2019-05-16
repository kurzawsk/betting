package com.kk.betting.web.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.RestTemplate;

/**
 * Created by KK on 2017-07-18.
 */
@Configuration
@PropertySource("file:${env.config.dir}//env.properties")
public class BettingServicesConfig {

    @Value("${betting.application.log.directory}")
    private String bettingApplicationLogDirectory;

    @Value("${system.health.log.directory}")
    private String systemHealthLogDirectory;

    @Value("${betting.services.url}")
    private String bettingServicesUrl;

    public String getBettingApplicationLogDirectory() {
        return bettingApplicationLogDirectory;
    }

    public String getSystemHealthLogDirectory() {
        return systemHealthLogDirectory;
    }

    public String getBettingServicesUrl() {
        return bettingServicesUrl;
    }

    @Bean
    public RestTemplate restTemplate(){
        return new RestTemplate();
    }
}
