package com.audiostock.service.util;

public class Report {
    private boolean successful;

    private String failureReason;

    public Report(boolean successful) {
        this.successful = successful;
    }

    public Report(boolean successful, String failureReason) {
        this.successful = successful;
        this.failureReason = failureReason;
    }

    public boolean isSuccessful() {
        return successful;
    }

    public String getFailureReason() {
        return failureReason;
    }
}
