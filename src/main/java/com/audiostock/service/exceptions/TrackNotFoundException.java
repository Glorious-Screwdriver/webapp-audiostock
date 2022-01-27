package com.audiostock.service.exceptions;

import com.audiostock.entities.Track;

public class TrackNotFoundException extends Exception {
    public TrackNotFoundException(String message) {
        super(message);
    }

    public TrackNotFoundException(Track track, String message) {
        super(message + ": " + track.getId());
    }

    public TrackNotFoundException(Throwable cause) {
        super(cause);
    }
}
