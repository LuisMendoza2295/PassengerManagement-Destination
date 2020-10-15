package com.pm.destination.domain.repository;

import com.pm.destination.domain.model.Destination;
import com.pm.destination.domain.vo.UserID;
import reactor.core.publisher.Flux;

public interface DestinationRepository {

    Flux<Destination> getAllByUserID(UserID userID);
}
