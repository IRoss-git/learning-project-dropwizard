package com.ilya.db.domain;

public class PaymentFailureReason extends AbstractFailureReason {

    private PaymentProcessor paymentProcessor;

    public PaymentProcessor getPaymentProcessor() {
        return paymentProcessor;
    }

    public void setPaymentProcessor(PaymentProcessor paymentProcessor) {
        this.paymentProcessor = paymentProcessor;
    }
}
