package com.ilya.db.dao;

import com.ilya.db.domain.PaymentFailureReason;

import java.util.List;

public interface PaymentFailureReasonDAO {

    void insertPaymentFailureReason(PaymentFailureReason paymentFailureReason);

    boolean isPaymentFailureReasonNonExists(String id);

    boolean isMappingExists(String genericId, String reasonId);

    boolean isPaymentFailureReasonWithCodeExists(String code, String processorId);

    PaymentFailureReason getPaymentFailureReason(String id);

    void deletePaymentFailureReason(String id);

    void updatePaymentFailureReason(String id, PaymentFailureReason paymentFailureReason);

    List<PaymentFailureReason> getAllPaymentFailureReasonsByPaymentProcessor(String paymentProcessorId, Long pageNumber, Long pageSize);

    List<PaymentFailureReason> getAllPaymentFailureReasonsByGenericReason(String genericReasonId, Long pageNumber, Long pageSize);

    void createReasonMapping(String genericReasonId, String reasonId);

    void batchInsertMapping(String genericReasonId, List<String> reasonId);

    void deleteReasonMappingByGenericIdAndReasonId(String genericReasonId, String reasonId);
}
