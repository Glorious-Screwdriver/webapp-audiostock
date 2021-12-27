package com.audiostock.service.exceptions;

public class LoginIsAlreadyTakenException extends Exception {
    public LoginIsAlreadyTakenException(String message) {
        super(message);
    }
}
