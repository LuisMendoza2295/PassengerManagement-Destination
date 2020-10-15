package com.pm.destination.service.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.reactive.function.client.WebClient;

@Configuration
public class DestinationServiceConfig {

    @Bean
    public WebClient userWebClient(@Value("${pm.userservice.url}") String userUrl) {
        return WebClient.create(userUrl);
    }
}
