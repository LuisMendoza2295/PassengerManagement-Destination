package com.pm.destination.domain.model;

import com.pm.destination.domain.vo.DestinationID;
import com.pm.destination.domain.vo.UserID;

import java.time.LocalDateTime;
import java.util.UUID;

public class Destination {

    private DestinationID destinationID;
    private UserID userID;
    private String from;
    private String to;

    public Destination(UserID userID, String from, String to) {
        this.destinationID = new DestinationID(UUID.randomUUID().toString());
        this.userID = userID;
        this.from = from;
        this.to = to;
    }

    public Destination(DestinationID destinationID, UserID userID, String from, String to) {
        this.destinationID = destinationID;
        this.userID = userID;
        this.from = from;
        this.to = to;
    }

    public DestinationID getDestinationID() {
        return destinationID;
    }

    public UserID getUserID() {
        return userID;
    }

    public String getFrom() {
        return from;
    }

    public String getTo() {
        return to;
    }
}
