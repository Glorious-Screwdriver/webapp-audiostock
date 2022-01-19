package com.audiostock.service.reports;

public class Report {
    private final boolean successful;

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
