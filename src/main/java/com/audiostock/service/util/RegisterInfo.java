package com.audiostock.service.util;

public class RegisterInfo {
    private boolean successful;

    private String failureReason;

    public RegisterInfo(boolean successful) {
        this.successful = successful;
    }

    public RegisterInfo(boolean successful, String failureReason) {
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
