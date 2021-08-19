package com.pm.destination.webapp.exception;

import com.pm.destination.domain.exception.DestinationNotFoundException;
import com.pm.destination.service.exception.ExternalServiceException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;

@Slf4j
//@RestControllerAdvice
public class ExceptionHandlers {

    @ExceptionHandler(ExternalServiceException.class)
    @ResponseStatus(HttpStatus.CONFLICT)
    public String externalServiceExceptionHandler(ExternalServiceException e) {
//        log.error(e.getMessage());
        return e.getMessage();
    }

    @ExceptionHandler(DestinationNotFoundException.class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    public String destinationNotFoundExceptionHandler(DestinationNotFoundException e) {
//        log.error(e.getMessage());
        return e.getMessage();
    }
}
