package com.pm.destination.service.exception;

import org.springframework.http.HttpStatus;

public class ExternalServiceException extends Exception {

    private HttpStatus httpStatus;

    public ExternalServiceException(HttpStatus httpStatus) {
        super("Service error");
        this.httpStatus = httpStatus;
    }

    public ExternalServiceException(Throwable throwable) {
        super("Service error");
        this.httpStatus = HttpStatus.CONFLICT;
    }
}
