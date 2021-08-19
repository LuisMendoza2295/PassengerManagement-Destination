package com.pm.destination.service.config.impl;

import com.pm.destination.service.config.DestinationBaseWebClient;
import com.pm.destination.service.util.MDCUtil;
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
                .flatMap(response -> response.bodyToMono(tClass))
                .doOnError(throwable -> log.error("ERROR ON SERVICE CALL")));
    }

    @Override
    public <T> Mono<T> retrieveMono(HttpMethod httpMethod, URI uri, HttpHeaders httpHeaders, Object body, ParameterizedTypeReference<T> pTypeReference) {
        return null;
    }

    @Override
    public <T> Flux<T> retrieveFlux(HttpMethod httpMethod, URI uri, HttpHeaders httpHeaders, Object body, Class<T> tClass) {
        return Flux.deferWithContext(ctx -> {
            httpHeaders.add(MDCUtil.MDC_ID, ctx.get("CONTEXT_KEY").toString());
            return retrieve(httpMethod, uri, httpHeaders, body)
                    .flatMapMany(clientResponse -> clientResponse.bodyToFlux(tClass))
                    .doOnEach(MDCUtil.logOnComplete(data -> log.info("COMPLETED SERVICE CALL {} {}", httpMethod, uri)))
                    .doOnEach(MDCUtil.logOnError(error -> log.error("ERROR ON SERVICE CALL {} {}", httpMethod, uri)));
        });
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
//                .onErrorResume(throwable -> wrapException(httpMethod, uri, throwable))
//                .flatMap(clientResponse -> checkResponse(httpMethod, uri, body, clientResponse))
                .name(httpMethod + " - " + uri.getPath())
                .metrics();
    }
}
