package com.pm.destination.repository.mapper;

import com.pm.destination.domain.model.Destination;
import com.pm.destination.domain.vo.DestinationID;
import com.pm.destination.domain.vo.UserID;
import com.pm.destination.repository.entities.DestinationEntity;
import org.springframework.stereotype.Component;
import reactor.core.publisher.Flux;

@Component
public class DestinationRepositoryMapper {

    public Destination map(DestinationEntity destinationEntity) {
        Destination destination = new Destination(
                new DestinationID(destinationEntity.getUuid()),
                new UserID(destinationEntity.getUserUUID()),
                destinationEntity.getFrom(),
                destinationEntity.getTo());

        return destination;
    }
}
