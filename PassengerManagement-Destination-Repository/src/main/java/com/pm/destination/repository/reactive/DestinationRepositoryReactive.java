package com.pm.destination.repository.reactive;

import com.pm.destination.repository.entities.DestinationEntity;
import org.springframework.data.repository.reactive.ReactiveCrudRepository;
import reactor.core.publisher.Flux;

public interface DestinationRepositoryReactive extends ReactiveCrudRepository<DestinationEntity, Long> {

    Flux<DestinationEntity> findAllByUserUUID(String userId);
}
