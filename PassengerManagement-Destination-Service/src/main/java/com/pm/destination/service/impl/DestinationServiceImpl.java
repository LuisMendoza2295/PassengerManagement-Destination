package com.pm.destination.service.impl;

import com.pm.destination.domain.model.Destination;
import com.pm.destination.domain.repository.DestinationRepository;
import com.pm.destination.domain.vo.UserID;
import com.pm.destination.service.DestinationService;
import com.pm.destination.service.config.DestinationSimpleWebClient;
import com.pm.destination.service.dto.UserDTO;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.util.UriComponentsBuilder;
import reactor.core.publisher.Flux;

import java.net.URI;

@Service
@RequiredArgsConstructor
public class DestinationServiceImpl implements DestinationService {

    @Value("${pm.userservice.url}")
    private URI userUrl;

    private final DestinationRepository destinationRepository;
    private final DestinationSimpleWebClient webClient;

    @Override
    public Flux<Destination> findAllByUserID(UserID userID) {
        URI uri = UriComponentsBuilder.newInstance()
                .uri(this.userUrl)
                .pathSegment("users", userID.getValue())
                .build()
                .toUri();
        return this.webClient
                .getRequestFlux(uri, UserDTO.class)
                .flatMap(userDTO -> destinationRepository.getAllByUserID(new UserID(userDTO.getId())));
    }
}
