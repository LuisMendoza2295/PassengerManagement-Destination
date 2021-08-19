package com.pm.destination.service.config.impl;

import com.pm.destination.service.config.DestinationBaseWebClient;
import com.pm.destination.service.config.DestinationSimpleWebClient;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import reactor.core.publisher.Flux;

import java.net.URI;

@RequiredArgsConstructor
public class DestinationSimpleWebClientImpl implements DestinationSimpleWebClient {

    private final DestinationBaseWebClient baseWebClient;

    @Override
    public <T> Flux<T> getRequestFlux(URI uri, Class<T> tClass) {
        return this.baseWebClient.retrieveFlux(HttpMethod.GET, uri, defineHeaders(), "{}", tClass);
    }

    public HttpHeaders defineHeaders() {
        HttpHeaders httpHeaders = new HttpHeaders();
        httpHeaders.setContentType(MediaType.APPLICATION_JSON);
        return httpHeaders;
    }
}
