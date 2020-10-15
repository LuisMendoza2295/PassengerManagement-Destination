package com.pm.destination.webapp.rest;

import com.pm.destination.domain.vo.UserID;
import com.pm.destination.service.DestinationService;
import com.pm.destination.webapp.dto.DestinationDTO;
import com.pm.destination.webapp.mapper.DestinationDTOMapper;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@RestController
@RequestMapping("/destinations")
public class DestinationRestController {

    private final DestinationService destinationService;

    private final DestinationDTOMapper destinationDTOMapper;

    public DestinationRestController(DestinationService destinationService, DestinationDTOMapper destinationDTOMapper) {
        this.destinationService = destinationService;
        this.destinationDTOMapper = destinationDTOMapper;
    }

    @GetMapping("/user/{userId}")
    public Flux<DestinationDTO> findAllByUserId(@PathVariable("userId") String userId) {
        return this.destinationService.findAllByUserID(new UserID(userId))
                .flatMap(destination -> Mono.just(destinationDTOMapper.map(destination)))
                .onErrorResume(Flux::error);
    }
}
