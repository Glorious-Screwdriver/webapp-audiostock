package com.audiostock.service.util;

public class RegisterReport extends Report {
    public RegisterReport(boolean successful) {
        super(successful);
    }

    public RegisterReport(boolean successful, String failureReason) {
        super(successful, failureReason);
    }
}
