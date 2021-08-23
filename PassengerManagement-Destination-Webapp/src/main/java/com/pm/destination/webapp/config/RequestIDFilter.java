package com.pm.destination.webapp.config;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.slf4j.MDC;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.server.ServerWebExchange;
import org.springframework.web.server.WebFilter;
import org.springframework.web.server.WebFilterChain;
import reactor.core.publisher.Mono;

@Slf4j
@Configuration
@AllArgsConstructor
public class RequestIDFilter implements WebFilter {

    @Override
    public Mono<Void> filter(ServerWebExchange exchange, WebFilterChain chain) {
        return chain.filter(exchange)
                .doOnSuccess(unused -> log.info("COMPLETED {}", MDC.get("X-B3-TraceId")))
                .doOnError(throwable -> log.info("ERROR {} {}", throwable.getMessage(), MDC.get("X-B3-TraceId")));
    }
}
