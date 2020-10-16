package com.pm.destination.domain.exception;

import com.pm.destination.domain.vo.DestinationID;
import com.pm.destination.domain.vo.UserID;

public class DestinationNotFoundException extends Exception {

    public DestinationNotFoundException(DestinationID destinationID) {
        super("Destination not found with id: " + destinationID.getValue());
    }

    public DestinationNotFoundException(UserID userID) {
        super("No destinations found for user with id: " + userID.getValue());
    }
}
