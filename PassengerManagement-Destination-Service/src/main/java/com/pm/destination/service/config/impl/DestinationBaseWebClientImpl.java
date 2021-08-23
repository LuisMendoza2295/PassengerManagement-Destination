package com.pm.destination.service.config.impl;

import com.pm.destination.service.config.DestinationBaseWebClient;
import com.pm.destination.service.exception.ExternalServiceException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.web.reactive.function.client.ClientResponse;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.net.URI;

@Slf4j
@RequiredArgsConstructor
public class DestinationBaseWebClientImpl implements DestinationBaseWebClient {

    private final WebClient webClient;

    @Override
    public <T> Mono<T> retrieveMono(HttpMethod httpMethod, URI uri, HttpHeaders httpHeaders, Object body, Class<T> tClass) {
        return Mono.deferWithContext(context -> retrieve(httpMethod, uri, httpHeaders, body)
                .flatMap(response -> response.bodyToMono(tClass)));
    }

    @Override
    public <T> Mono<T> retrieveMono(HttpMethod httpMethod, URI uri, HttpHeaders httpHeaders, Object body, ParameterizedTypeReference<T> pTypeReference) {
        return null;
    }

    @Override
    public <T> Flux<T> retrieveFlux(HttpMethod httpMethod, URI uri, HttpHeaders httpHeaders, Object body, Class<T> tClass) {
        return retrieve(httpMethod, uri, httpHeaders, body)
                .flatMapMany(clientResponse -> clientResponse.bodyToFlux(tClass));
    }

    @Override
    public <T> Flux<T> retrieveFlux(HttpMethod httpMethod, URI uri, HttpHeaders httpHeaders, Object body, ParameterizedTypeReference<T> pTypeReference) {
        return null;
    }

    private Mono<ClientResponse> retrieve(HttpMethod httpMethod, URI uri, HttpHeaders httpHeaders, Object body) {
        return webClient
                .method(httpMethod)
                .uri(uri)
                .headers(headers -> headers.addAll(httpHeaders))
                .bodyValue(body)
                .exchange()
                .flatMap(clientResponse -> {
                    if(clientResponse.statusCode().isError()) {
                        return Mono.error(new ExternalServiceException(clientResponse.statusCode()));
                    }
                    return Mono.just(clientResponse);
                })
                .doOnSuccess(clientResponse -> log.info("COMPLETED SERVICE CALL {} {}", httpMethod, uri))
                .doOnError(throwable -> log.error("ERROR ON SERVICE CALL {} {}", httpMethod, uri))
                .name(httpMethod + " - " + uri.getPath())
                .metrics();
    }
}
