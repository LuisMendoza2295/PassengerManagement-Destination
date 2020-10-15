package com.pm.destination.service;

import com.pm.destination.domain.model.Destination;
import com.pm.destination.domain.vo.UserID;
import reactor.core.publisher.Flux;

public interface DestinationService {

    Flux<Destination> findAllByUserID(UserID userID);
}
