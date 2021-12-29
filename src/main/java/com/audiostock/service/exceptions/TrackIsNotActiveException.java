package com.audiostock.service.exceptions;

public class TrackIsNotActiveException extends Exception {
    public TrackIsNotActiveException(String message) {
        super(message);
    }
}
