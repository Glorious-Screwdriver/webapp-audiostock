package com.audiostock.service.util;

public class ChangeProfileInfoReport extends Report {
    public ChangeProfileInfoReport(boolean successful) {
        super(successful);
    }

    public ChangeProfileInfoReport(boolean successful, String failureReason) {
        super(successful, failureReason);
    }
}
