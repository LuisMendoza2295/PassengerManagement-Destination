package com.pm.destination.service.impl;

import com.pm.destination.domain.exception.DestinationNotFoundException;
import com.pm.destination.domain.model.Destination;
import com.pm.destination.domain.repository.DestinationRepository;
import com.pm.destination.domain.vo.UserID;
import com.pm.destination.service.DestinationService;
import com.pm.destination.service.dto.UserDTO;
import com.pm.destination.service.exception.ExternalServiceException;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Service
public class DestinationServiceImpl implements DestinationService {

    private final DestinationRepository destinationRepository;

    private final WebClient userWebClient;

    public DestinationServiceImpl(DestinationRepository destinationRepository, @Qualifier("userWebClient") WebClient userWebClient) {
        this.destinationRepository = destinationRepository;
        this.userWebClient = userWebClient;
    }

    @Override
    public Flux<Destination> findAllByUserID(UserID userID) {
        return this.userWebClient
                .get()
                .uri("/users/{userId}", userID.getValue())
                .retrieve()
                .onStatus(
                        httpStatus -> !httpStatus.equals(HttpStatus.OK),
                        clientResponse -> Mono.error(new ExternalServiceException(clientResponse.statusCode())))
                .bodyToFlux(UserDTO.class)
                .onErrorMap(ExternalServiceException::new)
                .flatMap(userDTO -> destinationRepository.getAllByUserID(new UserID(userDTO.getId())))
                .switchIfEmpty(Mono.error(new DestinationNotFoundException(userID)));
    }
}
