package com.pm.destination.webapp.config;

import com.pm.destination.service.util.MDCUtil;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.server.reactive.ServerHttpRequest;
import org.springframework.web.server.ServerWebExchange;
import org.springframework.web.server.WebFilter;
import org.springframework.web.server.WebFilterChain;
import reactor.core.publisher.Mono;
import reactor.util.context.Context;

@Slf4j
@Configuration
@AllArgsConstructor
public class RequestIDFilter implements WebFilter {

    @Override
    public Mono<Void> filter(ServerWebExchange exchange, WebFilterChain chain) {
        ServerHttpRequest request = exchange.getRequest();
        String requestID = MDCUtil.getRequestId(request.getHeaders());

        return chain.filter(exchange)
                .doOnEach(MDCUtil.logOnComplete(data -> log.info("COMPLETED {} {}", request.getMethod(), request.getURI())))
                .doOnEach(MDCUtil.logOnError(error -> log.error("ERROR ON {} {}", request.getMethod(), request.getURI())))
                .subscriberContext(Context.of("CONTEXT_KEY", requestID));
    }
}
