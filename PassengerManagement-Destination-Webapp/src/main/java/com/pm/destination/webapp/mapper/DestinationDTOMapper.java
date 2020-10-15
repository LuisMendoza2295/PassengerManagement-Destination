package com.pm.destination.webapp.mapper;

import com.pm.destination.domain.model.Destination;
import com.pm.destination.webapp.dto.DestinationDTO;
import org.springframework.stereotype.Component;

@Component
public class DestinationDTOMapper {

    public DestinationDTO map(Destination destination) {
        DestinationDTO destinationDTO = new DestinationDTO();
        destinationDTO.setDestinationID(destination.getDestinationID().getValue());
        destinationDTO.setUserID(destination.getUserID().getValue());
        destinationDTO.setFrom(destination.getFrom());
        destinationDTO.setTo(destination.getTo());

        return destinationDTO;
    }
}
