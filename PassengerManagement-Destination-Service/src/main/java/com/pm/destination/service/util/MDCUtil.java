package com.pm.destination.service.util;

import lombok.experimental.UtilityClass;
import org.slf4j.MDC;
import org.springframework.http.HttpHeaders;
import reactor.core.publisher.Signal;

import java.util.List;
import java.util.Optional;
import java.util.UUID;
import java.util.function.Consumer;

@UtilityClass
public class MDCUtil {

    public final String MDC_ID = "X-Request-ID";
    public final String MDC_KEY = "MDC_KEY";
    public final String CONTEXT_KEY = "CONTEXT_KEY";

    public String getRequestId(HttpHeaders headers) {
        List<String> requestIdHeaders = headers.get(MDC_ID);
        return requestIdHeaders == null || requestIdHeaders.isEmpty()
                ? UUID.randomUUID().toString()
                : requestIdHeaders.get(0);
    }

    public <T> Consumer<Signal<T>> logOnComplete(Consumer<T> logStatement) {
        return signal -> {
            if (!signal.isOnComplete()) return;
            Optional<String> toPutInMdc = signal.getContext().getOrEmpty(CONTEXT_KEY);

            toPutInMdc.ifPresentOrElse(tpim -> {
                        try (MDC.MDCCloseable cMdc = MDC.putCloseable(MDC_KEY, tpim)) {
                            logStatement.accept(signal.get());
                        }
                    },
                    () -> logStatement.accept(signal.get()));
        };
    }

    public Consumer<Signal<?>> logOnError(Consumer<Throwable> errorLogStatement) {
        return signal -> {
            if (!signal.isOnError()) return;
            Optional<String> toPutInMdc = signal.getContext().getOrEmpty(CONTEXT_KEY);

            toPutInMdc.ifPresentOrElse(tpim -> {
                        try (MDC.MDCCloseable cMdc = MDC.putCloseable(MDC_KEY, tpim)) {
                            errorLogStatement.accept(signal.getThrowable());
                        }
                    },
                    () -> errorLogStatement.accept(signal.getThrowable()));
        };
    }
}
