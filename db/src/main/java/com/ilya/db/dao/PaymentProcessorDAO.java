package com.ilya.db.dao;

import com.ilya.db.domain.PaymentProcessor;

import java.util.List;

public interface PaymentProcessorDAO {

    void insertPaymentProcessor(PaymentProcessor paymentProcessor);

    boolean isPaymentProcessorNonExists(String id);

    boolean isPaymentProcessorWithKeyExists(String key);

    PaymentProcessor getPaymentProcessor(String id);

    List<PaymentProcessor> getAllPaymentProcessors(Long pageNumber, Long pageSize);

    void deletePaymentProcessor(String id);

    void updatePaymentProcessor(String id, PaymentProcessor paymentProcessor);
}
