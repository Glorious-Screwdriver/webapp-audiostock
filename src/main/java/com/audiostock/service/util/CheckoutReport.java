package com.audiostock.service.util;

public class CheckoutReport extends Report {
    CheckoutFailureReason checkoutFailureReason;

    public CheckoutReport(boolean successful) {
        super(successful);
    }

    public CheckoutReport(boolean successful, CheckoutFailureReason checkoutFailureReason, String message) {
        super(successful, message);
        this.checkoutFailureReason = checkoutFailureReason;
    }

    public CheckoutFailureReason getCheckoutFailureReason() {
        return checkoutFailureReason;
    }
}
