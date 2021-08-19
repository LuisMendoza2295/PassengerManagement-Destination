package com.pm.destination.service.config;

import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.net.URI;

public interface DestinationBaseWebClient {

    <T> Mono<T> retrieveMono(HttpMethod httpMethod, URI uri, HttpHeaders httpHeaders, Object body, Class<T> tClass);

    <T> Mono<T> retrieveMono(HttpMethod httpMethod, URI uri, HttpHeaders httpHeaders, Object body, ParameterizedTypeReference<T> pTypeReference);

    <T> Flux<T> retrieveFlux(HttpMethod httpMethod, URI uri, HttpHeaders httpHeaders, Object body, Class<T> tClass);

    <T> Flux<T> retrieveFlux(HttpMethod httpMethod, URI uri, HttpHeaders httpHeaders, Object body, ParameterizedTypeReference<T> pTypeReference);
}
