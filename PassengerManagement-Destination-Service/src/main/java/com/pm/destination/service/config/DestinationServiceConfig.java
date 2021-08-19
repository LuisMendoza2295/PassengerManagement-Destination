package com.pm.destination.service.config;

import com.pm.destination.service.config.impl.DestinationBaseWebClientImpl;
import com.pm.destination.service.config.impl.DestinationSimpleWebClientImpl;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.reactive.function.client.WebClient;

@Configuration
public class DestinationServiceConfig {

    @Bean
    public WebClient userWebClient() {
        return WebClient.builder()
                .build();
    }

    @Bean
    public DestinationBaseWebClient destinationBaseWebClient(WebClient webClient) {
        return new DestinationBaseWebClientImpl(webClient);
    }

    @Bean
    public DestinationSimpleWebClient destinationSimpleWebClient(DestinationBaseWebClient baseWebClient) {
        return new DestinationSimpleWebClientImpl(baseWebClient);
    }
}
