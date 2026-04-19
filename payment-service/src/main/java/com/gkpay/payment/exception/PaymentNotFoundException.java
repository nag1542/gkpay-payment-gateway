package com.gkpay.payment.exception;

public class PaymentNotFoundException extends RuntimeException {
    public PaymentNotFoundException(String paymentId) {
        super("Payment not found with paymentId: " + paymentId);
    }
}
