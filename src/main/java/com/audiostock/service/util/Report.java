package com.audiostock.service.util;

public class Report {
    private boolean successful;

    private String message;

    public Report(boolean successful) {
        this.successful = successful;
    }

    public Report(boolean successful, String message) {
        this.successful = successful;
        this.message = message;
    }

    public boolean isSuccessful() {
        return successful;
    }

    public String getMessage() {
        return message;
    }
}
