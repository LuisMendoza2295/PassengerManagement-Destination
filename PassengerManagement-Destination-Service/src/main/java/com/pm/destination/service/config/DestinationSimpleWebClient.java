package com.pm.destination.service.config;

import reactor.core.publisher.Flux;

import java.net.URI;

public interface DestinationSimpleWebClient {

    <T> Flux<T> getRequestFlux(URI uri, Class<T> tClass);
}
