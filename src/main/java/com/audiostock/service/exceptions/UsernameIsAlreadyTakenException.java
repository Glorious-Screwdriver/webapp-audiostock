package com.audiostock.service.exceptions;

public class UsernameIsAlreadyTakenException extends Exception {
    public UsernameIsAlreadyTakenException(String message) {
        super(message);
    }
}
