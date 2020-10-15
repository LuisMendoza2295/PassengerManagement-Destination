package com.pm.destination.repository.impl;

import com.pm.destination.domain.model.Destination;
import com.pm.destination.domain.repository.DestinationRepository;
import com.pm.destination.domain.vo.UserID;
import com.pm.destination.repository.mapper.DestinationRepositoryMapper;
import com.pm.destination.repository.reactive.DestinationRepositoryReactive;
import org.springframework.stereotype.Repository;
import reactor.core.publisher.Flux;

@Repository
public class DestinationRepositoryImpl implements DestinationRepository {

    private final DestinationRepositoryReactive destinationRepositoryReactive;

    private final DestinationRepositoryMapper destinationRepositoryMapper;

    public DestinationRepositoryImpl(DestinationRepositoryReactive destinationRepositoryReactive, DestinationRepositoryMapper destinationRepositoryMapper) {
        this.destinationRepositoryReactive = destinationRepositoryReactive;
        this.destinationRepositoryMapper = destinationRepositoryMapper;
    }

    @Override
    public Flux<Destination> getAllByUserID(UserID userID) {
        return this.destinationRepositoryReactive.findAllByUserUUID(userID.getValue())
                .map(this.destinationRepositoryMapper::map);
    }
}
