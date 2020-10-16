package com.pm.destination.webapp.exception;

import com.pm.destination.domain.exception.DestinationNotFoundException;
import com.pm.destination.service.exception.ExternalServiceException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class ExceptionHandlers {

    private static final Logger LOGGER = LoggerFactory.getLogger(ExceptionHandlers.class);

    @ExceptionHandler(ExternalServiceException.class)
    @ResponseStatus(HttpStatus.CONFLICT)
    public String externalServiceExceptionHandler(ExternalServiceException e) {
        LOGGER.error(e.getMessage());
        return e.getMessage();
    }

    @ExceptionHandler(DestinationNotFoundException.class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    public String destinationNotFoundExceptionHandler(DestinationNotFoundException e) {
        LOGGER.error(e.getMessage());
        return e.getMessage();
    }
}
